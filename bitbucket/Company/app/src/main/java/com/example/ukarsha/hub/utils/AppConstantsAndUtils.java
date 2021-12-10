package com.example.ukarsha.hub.utils;

public class AppConstantsAndUtils {

    public static final String STATUS_SUCCESS = "1";
    //public static final String STATUS_SUCCESS = "true";
    public static final Integer REQUEST_TIMEOUT = 640000; // 2Min
    /*************************************************************************PREFERENCE****************************************************/
    /*  SHARED PREFERENCE*/
    public static final String AMT = "amt_to_invst";
    public static final String USER = "USER";
    public static final String USER_ID = "user_id";
    public static final String COMPANY_NAME = "cmpy_name";
    public static final String COMPANY_ID = "comp_id";
    public static final String STUDENT_ID = "student_id";
    public static final String NAME = "name";
    public static final String MOBILE = "contact_no";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String SHARED_PREFERENCE_NAME = "name";
    public static final String SHARED_PREFERENCE_NAME_BACK_UP = "back_up_token";
    public static final String PREF_HEADER_REQ_TOKEN = "header_token";
    public static final String LOADING = "Loading";
    public static final String PLAYER_ID = "player_id";
    public static final String PREF_USER_LOGE_IN = "pref_user_loge_in";
    public static final String LAST_SIGNIN_TIME = "last_signin_time";
    public static final String JOB_TYPE = "job_type";
    public static final String CAMPUS_ID = "job_type";
    public static final String INDUSTRIAL_ID = "industrial_id";
    public static final String JOB_POSTING_ID = "posting_id";

    /***************************---------------------------------------------------------*******************************/

    //Local url
    //private static final String BASE_URL = "http://192.168.0.52:78/Cyberathon/AndroidApis/index.php/";

    //local_new URL
    private static final String BASE_URL = "http://192.168.0.52:78/consultustoday/AndroidApis/index.php/";
    //online URL
   // private static final String BASE_URL  =  "http://cut.siotams.com/AndroidApis/index.php/";


    public static final String URL_LOGIN = BASE_URL + "/login_hub";
    public static final String URL_REGISTER = BASE_URL + "/registration_hub";
    //public static final String URL_FORGOT_PASSWORD = BASE_URL + "/forgotpassword";
    public static final String URL_PROFILE = BASE_URL + "/profile_hub";
    public static final String URL_CMPNY_LIST = BASE_URL + "/user_list_hub";
    public static final String URL_UPDATE_PROFILE = BASE_URL + "/update_profile_hub";

    //URL DIFFERENTjob_posting
    public static final String URL_LOGIN_NEW = BASE_URL + "Welcome/login_hub";
    public static final String URL_REGISTRATION_NEW = BASE_URL + "Welcome/registration_hub";
    public static final String URL_PROFILE_NEW = BASE_URL + "Welcome/profile_hub";
    public static final String URL_FORGOT_PASSWORD = BASE_URL + "Welcome/forgotpassword";
    public static final String URL_UPDATE_PROFILE_NEW = BASE_URL + "Welcome/update_profile_hub";
    public static final String URL_CAMPUS_DRIVE = BASE_URL + "Student/campus_drive";
    public static final String URL_INDUSTRIAL_DRIVE = BASE_URL + "Student/industrial_visit";
    public static final String URL_JOB_POSTING = BASE_URL + "Student/job_posting";
    public static final String URL_JOB_EXPIRED = BASE_URL + "Student/job_expired";
    public static final String URL_JOB_TYPE = BASE_URL + "Student/jobtype_list";
    public static final String URL_CAMPUS_DRIVE_LIST = BASE_URL + "Student/campus_list";
    public static final String URL_IDUSTRIAL_LIST = BASE_URL + "Student/industrial_visit_list";
    public static final String URL_JOB_POSTING_LIST = BASE_URL + "Student/job_posting_list";
    public static final String URL_COMPANY_AGAINST_STUDENT_LIST = BASE_URL + "Student/cmpny_agnst_stud_list";
    public static final String URL_STUDENT_AGAINST_DETAILS = BASE_URL + "Student/student_agnst_detail";
    public static final String URL_COMPANY_LIST = BASE_URL + "Student/company_list";
    public static final String URL_JOB_APPLICATION_LIST = BASE_URL + "Student/company_list";
    public static final String URL_SAVE_CAMPUS_APPLIED_LIST = BASE_URL + "Student/campus_appliedlist";
    public static final String URL_SAVE_INDUSTRIAL_APPLIED_LIST = BASE_URL + "Student/industrial_appliedlist";
    public static final String URL_CAMPUS_DRIVE_UPDATE = BASE_URL + "Student/campus_drive_update";
    public static final String URL_INDUSTRIAL_VISIT_UPDATE = BASE_URL + "Student/industrial_visit_update";
    public static final String URL_JOB_POSTING_UPDATE = BASE_URL + "Student/job_posting_update";
    public static final String URL_STUDENT_APPLIED_LIST_AGAINST_COMPANY = BASE_URL + "Student/student_applied_list_against_company";
    public static final String URL_CAMPUS_DRIVE_VIEW = BASE_URL + "Student/campus_drive_view";
    public static final String URL_INDUSTRIAL_VISIT_VIEW = BASE_URL + "Student/industrial_visit_view";
    public static final String URL_JOB_POSTING_VIEW = BASE_URL + "Student/job_posting_view";
    public static final String URL_JOB_APPLICATION_VIEW = BASE_URL + "Student/student_applied_view";
    public static final String URL_NOTIFICATION = BASE_URL + "Student/sendOneSignalNotification";
    public static final String URL_FEEDBACK = BASE_URL + "Student/comp_feedback";

   /*  //Online URL
    private static final String BASE_URL = " http://cyberathon.com/AndroidApis/index.php/";

    public static final String URL_LOGIN_NEW = BASE_URL + "Welcome/login_hub";
    public static final String URL_REGISTRATION_NEW = BASE_URL + "Welcome/registration_hub";
    public static final String URL_PROFILE_NEW = BASE_URL + "profile_hub";
    public static final String URL_CAMPUS_DRIVE = BASE_URL + "Student/campus_drive";
    public static final String URL_INDUSTRIAL_DRIVE = BASE_URL + "Student/industrial_visit";
    public static final String URL_JOB_POSTING = BASE_URL +"Student/job_posting";
    public static final String URL_JOB_EXPIRED = BASE_URL + "Student/job_expired";
    public static final String URL_JOB_TYPE = BASE_URL + "Student/jobtype_list";
    public static final String URL_CAMPUS_DRIVE_LIST = BASE_URL + "Student/campus_list";
    public static final String URL_IDUSTRIAL_LIST = BASE_URL + "Student/industrial_visit_list";
    public static final String URL_JOB_POSTING_LIST = BASE_URL + "Student/job_posting_list";
    public static final String URL_COMPANY_AGAINST_STUDENT_LIST = BASE_URL + "Student/cmpny_agnst_stud_list";
    public static final String URL_STUDENT_AGAINST_DETAILS = BASE_URL + "Student/student_agnst_detail";
    public static final String URL_COMPANY_LIST = BASE_URL + "Student/company_list";
    public static final String URL_SAVE_CAMPUS_APPLIED_LIST = BASE_URL + "Student/campus_appliedlist";
    public static final String URL_SAVE_INDUSTRIAL_APPLIED_LIST = BASE_URL + "Student/industrial_appliedlist";
    public static final String URL_CAMPUS_DRIVE_UPDATE = BASE_URL + "Student/campus_drive_update";
    public static final String URL_INDUSTRIAL_VISIT_UPDATE = BASE_URL + "Student/industrial_visit_update";
    public static final String URL_JOB_POSTING_UPDATE = BASE_URL + "Student/job_posting_update";
    public static final String URL_STUDENT_APPLIED_LIST_AGAINST_COMPANY = BASE_URL + "Student/student_applied_list_against_company";
*/
}
