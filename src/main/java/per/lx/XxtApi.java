package per.lx;

public interface XxtApi {
    String LOGIN = "http://passport2.chaoxing.com/fanyalogin";
    String FIND_COURSE = "https://mooc1-api.chaoxing.com/mycourse/backclazzdata?view=json&mcode=";
    String GET_ACTIVE_LIST = "https://mobilelearn.chaoxing.com/v2/apis/active/student/activelist?fid=-1&courseId=";
    String PRE_SIGN = "https://mobilelearn.chaoxing.com/newsign/preSign?activePrimaryId=";
    String LOCATION_SIGN = "https://mobilelearn.chaoxing.com/pptSign/stuSignajax?name=";
    String QRCODE_SIGN = "https://mobilelearn.chaoxing.com/pptSign/stuSignajax?enc=";
    String Ordinary_SIGN = "https://mobilelearn.chaoxing.com/v2/apis/sign/signIn?activeId=";
}