package com.ritvi.kaajneeti.webservice;

/**
 * Created by sunil on 20-01-2017.
 */

public class WebServicesUrls {

//    public static final String BASE_URL = "http://10.0.2.2/ritvigroup.com/ritvigroup/api/V1/";
//    public static final String BASE_URL = "http://rajesh1may.000webhostapp.com/ritvigroup/api_old/V1/";
    public static final String BASE_URL = "http://ritvigroup.com/ritvigroup.com/ritvigroup/api/V1/";


    public static final String NEWS_API = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=311c1e78e72c4bb9a64542528d871871";

    public static final String REGISTER_URL = BASE_URL + "userregister/registerMobile";
    public static final String REGISTER_VALIDATE_MOBILE_OTP = BASE_URL + "userregister/validateMobileOtp";
    public static final String REGISTER_SET_MPIN = BASE_URL + "userregister/setMobileMpin";
    public static final String LOGIN_URL= BASE_URL + "userlogin/loginMobile";
    public static final String LOGIN_MPIN= BASE_URL + "userlogin/loginMobileMpin";
    public static final String LOGIN_WITH_SOCIAL = BASE_URL + "userlogin/loginWithSocial";
    public static final String SAVE_EVENT = BASE_URL + "event/saveMyEvent";


    public static final String EDIT_PROFILE= BASE_URL + "editprofile.php";
    public static final String USER_ADMIN_PROCESS= BASE_URL + "user_admin_process.php";
    public static final String ADMIN_PROCESS= BASE_URL + "admin_process.php";
    public static final String COMPLAINT= BASE_URL + "complaint.php";
    public static final String CITIZEN_PROCESS= BASE_URL + "citizen_process.php";
    public static final String CITIZEN_PROFILE= BASE_URL + "citizen-profile.php";


    public static final String SEARCH_USER_PROFILE= BASE_URL + "userprofile/searchAllUserProfiles";
    public static final String SAVE_MY_POLL= BASE_URL + "poll/saveMyPoll";


}
