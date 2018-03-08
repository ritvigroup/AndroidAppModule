package com.ritvi.kaajneeti.testing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.TagUtils;
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

import retrofit2.Call;

public class TwitterLoginTest extends AppCompatActivity {
    TwitterLoginButton loginButton;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_login_test);
        btn_login = findViewById(R.id.btn_login);
//        Twitter.initialize(this);

        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                Log.e("result", "result " + result);
                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
                AccountService accountService = twitterApiClient.getAccountService();
                Call<User> call = accountService.verifyCredentials(true, true);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> result) {
                        //here we go User details
                        Log.d(TagUtils.getTag(), "result user " + result);
                        String imageUrl = result.data.profileImageUrl;
                        String email = result.data.email;
                        String userName = result.data.name;

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

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TwitterAuthClient mTwitterAuthClient = new TwitterAuthClient();
                mTwitterAuthClient.authorize(TwitterLoginTest.this, new Callback<TwitterSession>() {

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
    }

    public void login(Result<TwitterSession> result) {

        //Creating a twitter session with result's data
        final TwitterSession session = result.data;

//        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;
        final TwitterAuthClient authClient = new TwitterAuthClient();
        Call<User> userResult = Twitter.getApiClient(session).getAccountService().verifyCredentials(true, false);
        userResult.enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> result) {
                User user = result.data;
                String user_name = user.email;
                String description = user.description;
                int followersCount = user.followersCount;
                Log.d(TagUtils.getTag(), "name:-" + user.name);
                Log.d(TagUtils.getTag(), "description:-" + user.description);
                String profileImage = user.profileImageUrl.replace("_normal", "");
                Log.d(TagUtils.getTag(), "profile image:-" + profileImage);


                authClient.requestEmail(session, new Callback<String>() {
                    @Override
                    public void success(Result<String> result) {
                        // Do something with the result, which provides the email address
                        String email = result.data;
                        Log.d(TagUtils.getTag(), "email:-" + email);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}
