package com.ritvi.kaajneeti.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.FileUtils;
import com.ritvi.kaajneeti.Util.Pref;
import com.ritvi.kaajneeti.Util.StringUtils;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;
import com.ritvi.kaajneeti.webservice.WebUploadService;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.AccountService;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class LoginActivity extends LocalizationActivity implements GoogleApiClient.OnConnectionFailedListener, WebServicesCallBack {


    @BindView(R.id.iv_facebook_login)
    ImageView iv_facebook_login;
    @BindView(R.id.iv_google_login)
    ImageView iv_google_login;
    @BindView(R.id.twitterLogin)
    TwitterLoginButton twitterLoginButton;
    @BindView(R.id.tv_new_user)
    TextView tv_new_user;
    @BindView(R.id.iv_twitter_login)
    ImageView iv_twitter_login;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.et_mobile_number)
    EditText et_mobile_number;
    @BindView(R.id.et_mpin)
    EditText et_mpin;
    @BindView(R.id.tv_login_otp)
    TextView tv_login_otp;
    @BindView(R.id.tv_forgot_mpin)
    TextView tv_forgot_mpin;
    @BindView(R.id.login_button)
    TwitterLoginButton tw_login_button;

    GoogleApiClient mGoogleApiClient;
    GoogleSignInOptions gso;
    CallbackManager callbackManager;

    private int RC_SIGN_IN = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.setApplicationId(getResources().getString(R.string.facebook_app_id));
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        RequestData();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initializeGoogleSignIn();


        tw_login_button.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.e("result", "result " + result);
                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
                AccountService accountService = twitterApiClient.getAccountService();
                Call<User> call = accountService.verifyCredentials(true, true);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> result) {
                        //here we go User details
                        Log.d(TagUtils.getTag(), "result user " + result);

                        Log.d(TagUtils.getTag(), "id:-" + result.data.id);
                        Log.d(TagUtils.getTag(), "name:-" + result.data.name);
                        Log.d(TagUtils.getTag(), "email:-" + result.data.email);
                        Log.d(TagUtils.getTag(), "description:-" + result.data.description);
                        Log.d(TagUtils.getTag(), "image url:-" + result.data.profileImageUrl);


                    }

                    @Override
                    public void failure(TwitterException exception) {
                        exception.printStackTrace();
                    }
                });
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                exception.printStackTrace();
            }
        });

        iv_twitter_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TwitterAuthClient mTwitterAuthClient = new TwitterAuthClient();
                mTwitterAuthClient.authorize(LoginActivity.this, new Callback<TwitterSession>() {

                    @Override
                    public void success(Result<TwitterSession> twitterSessionResult) {
                        // Success
                        login(twitterSessionResult);
                    }

                    @Override
                    public void failure(TwitterException e) {
                        e.printStackTrace();
                        Log.d(TagUtils.getTag(), "twitter error:-" + e.toString());
                    }
                });
            }
        });

        iv_facebook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FbIntegration();
            }
        });

        iv_google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleIntegration();
            }
        });

        tv_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callLoginAPI();
            }
        });

        et_mpin.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btn_login.callOnClick();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_mpin.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        tv_login_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, LoginWithOTPActivity.class));
            }
        });

        tv_forgot_mpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotMpinActivity.class));
            }
        });
    }

    public void callLoginAPI() {
        if (et_mobile_number.getText().toString().length() > 0 && et_mpin.getText().toString().length() > 0) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("request_action", "LOGIN_WITH_MPIN"));
            nameValuePairs.add(new BasicNameValuePair("mobile", "+91" + et_mobile_number.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("mpin", et_mpin.getText().toString()));
            new WebServiceBase(nameValuePairs, this, this, Constants.CALL_LOGIN_API, true).execute(WebServicesUrls.LOGIN_MPIN);
        } else {
            ToastClass.showShortToast(getApplicationContext(), "Please Enter required fields properly");
        }
    }


    public void login(Result<TwitterSession> result) {

        final TwitterSession session = result.data;

        TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;
        final TwitterAuthClient authClient = new TwitterAuthClient();
        Call<User> userResult = Twitter.getApiClient(session).getAccountService().verifyCredentials(true, false);
        userResult.enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> result) {
                final User user = result.data;
                String user_name = user.email;
                String description = user.description;
                int followersCount = user.followersCount;
                Log.d(TagUtils.getTag(), "name:-" + user.name);
                Log.d(TagUtils.getTag(), "description:-" + user.description);
                final String profileImage = user.profileImageUrl.replace("_normal", "");
                Log.d(TagUtils.getTag(), "profile image:-" + profileImage);


                authClient.requestEmail(session, new Callback<String>() {
                    @Override
                    public void success(Result<String> result) {
                        // Do something with the result, which provides the email address
                        String email = result.data;
                        Log.d(TagUtils.getTag(), "email:-" + email);

                        saveImageFromUrl("twitter", String.valueOf(user.id), user.name, email, profileImage, "");

                    }

                    @Override
                    public void failure(TwitterException exception) {
                        // Do something on failure
                        Log.d(TagUtils.getTag(), "twitter login failure");
                    }
                });
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("sunil", "failed");
            }
        });
//        TwitterAuthClient authClient = new TwitterAuthClient();


    }

    public void GoogleIntegration() {
        signIn();
    }

    private void signIn() {
        //Creating an intent
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void initializeGoogleSignIn() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }


    public void FbIntegration() {
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends", "email"));
    }

    public void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d(TagUtils.getTag(), "facebook response:-" + response.toString());
                JSONObject json = response.getJSONObject();
                try {
                    if (json != null) {
                        String id = "";
                        String name = "";
                        String email = "";
                        String photo = "";
                        String phone = "";

                        if (json.has("id")) {
                            id = json.optString("id");
                        }
                        if (json.has("name")) {
                            name = json.optString("name");
                        }
                        if (json.has("email")) {
                            email = json.optString("email");
                        }
                        if (json.has("phone")) {
                            phone = json.optString("phone");
                        }


                        Log.d(TagUtils.getTag(), "name:-" + json.getString("name"));
                        Log.d(TagUtils.getTag(), "link:-" + json.getString("link"));
                        Log.d(TagUtils.getTag(), "id:-" + json.getString("id"));
                        Log.d(TagUtils.getTag(), "email:-" + json.getString("email"));
                        String profile_url = "https://graph.facebook.com/" + json.getString("id") + "/picture?type=large";
                        Log.d(TagUtils.getTag(), "facebook profile url:-" + profile_url);

                        saveImageFromUrl("facebook", id, name, email, profile_url, phone);

                    }

                } catch (JSONException e) {
                    Log.d("profile", e.toString());

                    try {
                        Log.d(TagUtils.getTag(), e.toString());
                        Log.d(TagUtils.getTag(), "id:-" + json.getString("id"));
                        Log.d(TagUtils.getTag(), "name:-" + json.getString("name"));
//                        String email="temp_"+json.getString("id")+"@bjain.com";
//                        FacebookLoginAPI(email);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        }
//        tw_login_button.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();
            try {

                Log.d(TagUtils.getTag(), "name:-" + acct.getDisplayName());
                Log.d(TagUtils.getTag(), "email:-" + acct.getEmail());
                Log.d(TagUtils.getTag(), "image:-" + acct.getPhotoUrl().toString());
                Log.d(TagUtils.getTag(), "id:-" + acct.getId());

                saveImageFromUrl("google", acct.getId(), acct.getDisplayName(), acct.getEmail(), acct.getPhotoUrl().toString(), "");
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Your Google account is not configured with google plus account", Toast.LENGTH_SHORT).show();
            }

        } else {
            //If login fails
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onGetMsg(String apicall, String response) {
        Log.d(TagUtils.getTag(), apicall + ":-" + response);
        if(apicall.equals(Constants.CALL_LOGIN_API)){
            parseLoginResponse(response);
        }else if(apicall.equals(Constants.CALL_UPLOAD_SOCIAL_DATA)){
            parseLoginResponse(response);
        }
    }

    public void parseLoginResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString(Constants.API_STATUS).equals(Constants.API_SUCCESS)) {
                String user_profile = jsonObject.optJSONObject("result").toString();
                Gson gson = new Gson();
                UserInfoPOJO userProfilePOJO = gson.fromJson(user_profile, UserInfoPOJO.class);
//                Pref.SaveUserProfile(LoginActivity.this, user_profile);
                Pref.SetStringPref(getApplicationContext(),StringUtils.USER_PROFILE,user_profile);
                Pref.SetBooleanPref(LoginActivity.this, StringUtils.IS_LOGIN, true);
                Constants.userInfoPOJO =userProfilePOJO;
                if(userProfilePOJO.getUserName().equals("")||userProfilePOJO.getUserEmail().equals("")||
                        userProfilePOJO.getGender().equals("0")||userProfilePOJO.getDateOfBirth().equals("")){
                    Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_PROFILE_COMPLETED,false);

                    Pref.SetBooleanPref(LoginActivity.this, StringUtils.IS_PROFILE_COMPLETED, false);
                    startActivity(new Intent(LoginActivity.this, ProfileInfoActivity.class));
                    finishAffinity();

                } else {
                    Pref.SetBooleanPref(LoginActivity.this, StringUtils.IS_PROFILE_COMPLETED, true);
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finishAffinity();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TagUtils.getTag(), "error:-" + e.toString());
        } finally {

        }
    }

    public void saveImageFromUrl(final String social_type, final String id, final String name, final String email, final String picture, final String mobile) {
        new AsyncTask<Void, Void, Void>() {
            File file = new File(FileUtils.getSocialDir() + File.separator + social_type + "_" + System.currentTimeMillis() + ".png");

            @Override
            protected Void doInBackground(Void... voids) {
                InputStream input = null;
                OutputStream output=null;
                try {

                    URL url = new URL(picture);
                    input = url.openStream();
                    output = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int bytesRead = 0;
                    while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                        output.write(buffer, 0, bytesRead);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if(input!=null){
                        try {
                            input.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(output!=null){
                        try {
                            output.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                sendDataToServer(social_type, id, name, email, file.toString(), mobile);
            }
        }.execute();
    }

    public void sendDataToServer(String social_type, String id, String name, String email, String picture, String mobile) {

        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            if (picture.length() > 0 && new File(picture).exists()) {
                FileBody bin1 = new FileBody(new File(picture));
                reqEntity.addPart("photo", bin1);
            } else {
                reqEntity.addPart("photo", new StringBody(""));
            }
            reqEntity.addPart("request_action", new StringBody("LOGIN_WITH_SOCIAL"));
            reqEntity.addPart("social_type", new StringBody(social_type));
            reqEntity.addPart("id", new StringBody(id, Charset.forName(HTTP.UTF_8)));
            reqEntity.addPart("name", new StringBody(name, Charset.forName(HTTP.UTF_8)));
            reqEntity.addPart("email", new StringBody(email, Charset.forName(HTTP.UTF_8)));
            reqEntity.addPart("mobile", new StringBody(mobile, Charset.forName(HTTP.UTF_8)));
            new WebUploadService(reqEntity, this, this, Constants.CALL_UPLOAD_SOCIAL_DATA, false).execute(WebServicesUrls.LOGIN_WITH_SOCIAL);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
