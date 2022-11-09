package per.lx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Scanner;

public class Script {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String account, password;
        System.out.println("---请输入你的账号---");
        account = scanner.nextLine();
        System.out.println("---请输入你的密码---");
        password = scanner.nextLine();
        password = new Encrypt().encrypt("u2oh6Vu^HWe40fj", password);

        //登录
        ResponseEntity<String> loginEntity = Utils.login(account, password);

        String body = loginEntity.getBody();

        int indexStatus = body.indexOf("status");
        String status = body.substring(indexStatus + 8, indexStatus + 13);

        if ("false".equals(status)) {
            System.out.println("用户登录失败, 账号或者密码错误");
            return;
        }

        List<String> cookies = loginEntity.getHeaders().get("Set-Cookie");

        //查找所有课程
        String courses = Utils.findCourses(cookies);

        while (true) {
            int start = courses.indexOf("courseId");
            if (start == -1) {
                break;
            }
            String courseId = courses.substring(start + 9, start + 18);

            start = courses.indexOf("classId");
            if (start == -1) {
                break;
            }
            String classId = courses.substring(start + 8, start + 16);

            courses = courses.substring(start + 17);

            //查找所有活动
            String content = Utils.getActiveList(courseId, classId, cookies).getBody();
            JSONObject jsonObject = JSONObject.parseObject(content);
            if (jsonObject.get("data") == null) {
                continue;
            }
            JSONObject data = JSONObject.parseObject(jsonObject.get("data").toString());
            JSONArray activeList = JSONObject.parseArray(data.get("activeList").toString());

            for (int i = 0; i < activeList.size(); i++) {
                JSONObject obj = activeList.getJSONObject(i);
                //活动id
                String otherId = "";
                if (obj.get("otherId") != null) {
                    otherId = obj.get("otherId").toString();
                }
                String activeId = obj.get("id").toString();
                String activeStatus = obj.get("status").toString();
                if ("4".equals(otherId) && "1".equals(activeStatus)) {
                    System.out.println("---查到了一个位置签到---");
                    System.out.println("---请输入经纬度----");
                    String coordinate = scanner.nextLine();

                    int index = coordinate.indexOf(",");
                    String longitude = coordinate.substring(0, index), latitude = coordinate.substring(index + 1);

                    System.out.println("---请输入姓名---");
                    String name = scanner.nextLine();
                    System.out.println("---请输入地址---");
                    String address = scanner.nextLine();
                    Utils.preSign(activeId, cookies);
                    //位置签到
                    String pptSign = Utils.pptSign(latitude, longitude, activeId, name, address, cookies);
                    System.out.println(pptSign);
                    return;

                } else if ("2".equals(otherId) && "1".equals(activeStatus)) {
                    System.out.println("---查到了一个二维码签到---");
                    System.out.println("---输入enc---");
                    String enc = scanner.nextLine();
                    System.out.println("---输入姓名---");
                    String name = scanner.nextLine();
                    Utils.preSign(activeId, cookies);
                    //二维码签到
                    String qrCodeSign = Utils.QRCodeSign(enc, name, activeId, cookies);
                    System.out.println(qrCodeSign);
                    return;
                }else if("0".equals(otherId) && "1".equals(activeStatus)) {
                    System.out.println("---查到了一个普通签到---");
                    System.out.println("开始为您签到...");
                    String ordinarySign = Utils.OrdinarySIGN(activeId, cookies);
                    JSONObject jsonObject1 = JSON.parseObject(ordinarySign);
                    String msg = jsonObject1.get("msg").toString();
                    System.out.println(msg);
                    return;
                }
            }
        }
        System.out.println("无签到课程");
    }
}