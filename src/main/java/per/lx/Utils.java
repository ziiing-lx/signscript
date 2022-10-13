package per.lx;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    static RestTemplate restTemplate = new RestTemplate();

    public static ResponseEntity<String> login(String account, String password) {
        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<Object, Object> map = new LinkedMultiValueMap<>();
        map.add("fid", -1);
        map.add("uname", account);
        map.add("password", password);
        map.add("refer", "http%253A%252F%252Fi.chaoxing.com");
        map.add("t", true);
        map.add("forbidotherlogin", 0);
        map.add("validate", null);
        map.add("doubleFactorLogin", 0);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(map, headers);

        ResponseEntity<String> entity = restTemplate.postForEntity(XxtApi.LOGIN, request, String.class);
        return entity;
    }

    public static String findCourses(List<String> cookies) {
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.COOKIE, cookies);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(XxtApi.FIND_COURSE, HttpMethod.GET, httpEntity, String.class);

        return responseEntity.toString();

    }

    //查找活动
    public static ResponseEntity<String> getActiveList(String courseId, String classId, List<String> cookies) {
        String url = XxtApi.GET_ACTIVE_LIST + courseId + "&classId=" + classId + "&showNotStartedActive=0";
        Map<String, Object> params = new HashMap<>();
        params.put("fid", -1);
        params.put("courseId", courseId);
        params.put("classId", classId);
        params.put("showNotStartedActive", 0);

        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.COOKIE, cookies);
        Map<String, Object> paramMap = new HashMap<>();
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paramMap, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        return responseEntity;
    }

    public static void preSign(String activeId, List<String> cookies) {
        String url = XxtApi.PRE_SIGN + activeId;
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.COOKIE, cookies);
        Map<String, Object> paramMap = new HashMap<>();
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paramMap, headers);
        restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
    }

    //位置签到
    public static String pptSign(String latitude, String longitude, String activeId, String name, String address, List<String> cookies) {
        String url = XxtApi.LOCATION_SIGN + name + "&address=" + address + "&activeId=" + activeId + "&latitude=" + latitude + "&longitude=" + longitude;
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.COOKIE, cookies);
        Map<String, Object> paramMap = new HashMap<>();
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> entity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        return entity.getBody();
    }

    public static String QRCodeSign(String enc, String name, String activeId, List<String> cookies) {
        String url = XxtApi.QRCODE_SIGN + enc + "&name=" + name + "&activeId=" + activeId + "&clientip=&useragent=&latitude=-1&longitude=-1&appType=15 ";
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.COOKIE, cookies);
        Map<String, Object> paramMap = new HashMap<>();
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> entity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        return entity.getBody();
    }
}
