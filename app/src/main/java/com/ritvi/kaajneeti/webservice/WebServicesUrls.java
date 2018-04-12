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
    public static final String LOGIN_URL = BASE_URL + "userlogin/loginMobile";
    public static final String LOGIN_MPIN = BASE_URL + "userlogin/loginMobileMpin";
    public static final String LOGIN_WITH_SOCIAL = BASE_URL + "userlogin/loginWithSocial";
    public static final String SAVE_EVENT = BASE_URL + "event/saveMyEvent";
    public static final String POST_COMPLAINT = BASE_URL + "complaint/postMyComplaint";
    public static final String UPDATE_PROFILE_AFTER_LOGIN = BASE_URL + "userprofile/updateProfileAfterLogin";
    public static final String GET_MY_FAVORITE_LEADER = BASE_URL + "userprofile/getMyAllFavouriteLeader";
    public static final String SET_MY_FAVORITE_LEADER = BASE_URL + "userprofile/setLeaderAsFavourite";
    public static final String UPDATE_PROFILE = BASE_URL + "userprofile/updateProfile";
    public static final String GET_MORE_PROFILE_DATA = BASE_URL + "userprofile/getMoreDetailAboutUserProfile";

    public static final String COMPLAINT = BASE_URL + "complaint.php";

    public static final String SEARCH_USER_PROFILE = BASE_URL + "userprofile/searchAllUserProfiles";
    public static final String SEARCH_LEADER_PROFILE = BASE_URL + "userprofile/searchLeaderProfiles";

    public static final String SAVE_MY_POLL = BASE_URL + "poll/saveMyPoll";
    public static final String SAVE_SUGGESTION = BASE_URL + "suggestion/postMySuggestion";
    public static final String SAVE_INFORMATION = BASE_URL + "information/postMyInformation";
    public static final String SAVE_POST = BASE_URL + "post/postMyStatus";
    public static final String GET_FEELINGS = BASE_URL + "post/getAllFeelings";

    public static final String SEND_FRIEND_REQUEST = BASE_URL + "userconnect/sendUserProfileFriendRequest";
    public static final String CANCEL_FRIEND_REQUEST = BASE_URL + "userconnect/cancelUserProfileFriendRequest";
    public static final String UNDO_FRIEND_REQUEST = BASE_URL + "userconnect/undoUserProfileFriendRequest";
    public static final String OUTGOING_FRIEND_REQUEST = BASE_URL + "userconnect/getMyAllRequestToFriends";
    public static final String INCOMING_FRIEND_REQUEST = BASE_URL + "userconnect/getMyAllFriendRequest";
    public static final String MY_FRIENDS = BASE_URL + "userconnect/getMyAllFriends";
    public static final String HOME_PAGE_DATA = BASE_URL + "leader/getAllHomePageData";
    public static final String COMPLAINT_LIST = BASE_URL + "complaint/getMyAllComplaint";
    public static final String SUGGESTION_LIST = BASE_URL + "suggestion/getMyAllSuggestion";
    public static final String INFORMATION_LIST = BASE_URL + "information/getMyAllInformation";
    public static final String ALL_POST = BASE_URL + "post/getMyAllPost";
    public static final String ALL_EVENT = BASE_URL + "event/getMyAllEvent";
    public static final String ALL_POLL = BASE_URL + "poll/getMyAllPoll";
    public static final String ALL_SUMMARY_DATA = BASE_URL + "citizen/getMyAllSummaryTotal";
    public static final String SAVE_COMPLAINT_HISTORY = BASE_URL + "complaint/saveComplaintHistory";

    public static final String COMPLAINT_DETAIL = BASE_URL+"complaint/getComplaintHistory";
    public static final String PAYMENT_GATEWAY_API_DETAILS= BASE_URL+"payment/getPaymentGatewayApiDetail";
    public static final String ALL_PAYMENT_GATEWAY= BASE_URL+"payment/getAllPaymentGateway";
    public static final String SAVE_PAYMENT_TRANSACTIONS = BASE_URL+"payment/savePaymentTransactionLog";
    public static final String GET_PAYMENT_TRANS_LOGS = BASE_URL+"payment/getMyAllPaymentTransactionLog";
    public static final String WALLET_DETAIL_API = BASE_URL+"payment/getMyTotalWalletAmount";
    public static final String DEBIT_TRANS_LOGS = BASE_URL+"payment/getMyAllPaymentDebitTransactionLog";
    public static final String CREDIT_TRANS_LOGS = BASE_URL+"payment/getMyAllPaymentCreditTransactionLog";
    public static final String UPDATE_PROFILE_WORK = BASE_URL+"userprofile/updateProfileWork";
    public static final String UPDATE_PROFILE_EDUCATION = BASE_URL+"userprofile/updateProfileEducation";
}
