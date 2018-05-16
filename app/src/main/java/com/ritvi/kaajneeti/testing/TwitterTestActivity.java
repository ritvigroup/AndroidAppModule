package com.ritvi.kaajneeti.testing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.AccountService;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;

public class TwitterTestActivity extends AppCompatActivity {

    //And it would be added automatically while the configuration
    private static final String TWITTER_KEY = "YOUR TWITTER KEY";
    private static final String TWITTER_SECRET = "YOUR TWITTER SECRET";

    //Tags to send the username and image url to next activity using intent
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PROFILE_IMAGE_URL = "image_url";
    TwitterAuthClient mTwitterAuthClient= new TwitterAuthClient();
    //Twitter Login Button
    TwitterLoginButton twitterLoginButton;
    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));


        setContentView(R.layout.activity_twitter_test);

        //Initializing twitter login button
        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitterLogin);
        btn_login = (Button) findViewById(R.id.btn_login);

        //Adding callback to the button
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                //If login succeeds passing the Calling the login method and passing Result object
                login(result);
            }

            @Override
            public void failure(TwitterException exception) {
                //If failure occurs while login handle it here
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTwitterAuthClient.authorize(TwitterTestActivity.this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {

                    @Override
                    public void success(Result<TwitterSession> twitterSessionResult) {
                        // Success
                        login(twitterSessionResult);
                    }

                    @Override
                    public void failure(TwitterException e) {
                        e.printStackTrace();
                    }
                });

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Adding the login result back to the button
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    //The login function accepting the result object
    public void login(Result<TwitterSession> result) {

        final TwitterSession session = result.data;
        final String username = session.getUserName();
        final TwitterAuthClient authClient = new TwitterAuthClient();
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

                authClient.requestEmail(session, new Callback<String>() {
                    @Override
                    public void success(Result<String> result) {
                        // Do something with the result, which provides the email address
                        String email = result.data;
                        Log.d(TagUtils.getTag(), "email:-" + email);

//                        saveImageFromUrl("twitter", String.valueOf(user.id), user.name, email, profileImage, "");

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
                exception.printStackTrace();
            }
        });

    }
}
