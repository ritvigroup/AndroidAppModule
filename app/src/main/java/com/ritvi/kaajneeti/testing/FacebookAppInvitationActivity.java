package com.ritvi.kaajneeti.testing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.ProfileTracker;
import com.facebook.applinks.AppLinkData;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.facebook.share.widget.GameRequestDialog;
import com.google.gson.Gson;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.adapter.FacebookFriendsAdapter;
import com.ritvi.kaajneeti.pojo.facebook.FacebookDataPOJO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import bolts.AppLinks;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FacebookAppInvitationActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private LoginButton loginButton;
    private String firstName, lastName, email, birthday, gender;
    private URL profilePicture;
    private String userId;
    private String TAG = "LoginActivity";
    private Button btn_get_friends;
    @BindView(R.id.rv_friends)
    RecyclerView rv_friends;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.ll_facebook_friends)
    LinearLayout ll_facebook_friends;
    GameRequestDialog requestDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_app_invitation);
        ButterKnife.bind(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        requestDialog = new GameRequestDialog(this);
        requestDialog.registerCallback(callbackManager,
                new FacebookCallback<GameRequestDialog.Result>() {
                    public void onSuccess(GameRequestDialog.Result result) {
//                        String id = result.getId();
                        Log.d(TagUtils.getTag(),"request id:-"+result.getRequestId());
                        for(String s:result.getRequestRecipients()){
                            Log.d(TagUtils.getTag(),"sender:-"+s);
                        }
                    }
                    public void onCancel() {}
                    public void onError(FacebookException error) {}
                }
        );

        loginButton = (LoginButton) findViewById(R.id.login_button);
        btn_get_friends = (Button) findViewById(R.id.btn_get_friends);
        loginButton.setHeight(100);
        loginButton.setTextColor(Color.WHITE);
        loginButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        loginButton.setCompoundDrawablePadding(0);


        FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.e(TAG, object.toString());
                        Log.e(TAG, response.toString());

                        try {
                            userId = object.getString("id");
                            profilePicture = new URL("https://graph.facebook.com/" + userId + "/picture?width=500&height=500");
                            if (object.has("first_name"))
                                firstName = object.getString("first_name");
                            if (object.has("last_name"))
                                lastName = object.getString("last_name");
                            if (object.has("email"))
                                email = object.getString("email");
                            if (object.has("birthday"))
                                birthday = object.getString("birthday");
                            if (object.has("gender"))
                                gender = object.getString("gender");

                            Intent main = new Intent(FacebookAppInvitationActivity.this, FacebookMainActivity.class);
                            main.putExtra("name", firstName);
                            main.putExtra("surname", lastName);
                            main.putExtra("imageUrl", profilePicture.toString());
                            startActivity(main);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, birthday, gender , location");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
                e.printStackTrace();
            }
        };

        loginButton.setReadPermissions("email", "user_birthday", "user_posts", "user_friends");
        loginButton.registerCallback(callbackManager, callback);

        btn_get_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getPosts();
//                openDialogInvite(FacebookAppInvitationActivity.this);
                InviteFbFriends();
            }
        });


//        AppInviteDialog appInviteDialog=new AppInviteDialog(this);
//        appInviteDialog.registerCallback(callbackManager,
//                new FacebookCallback<AppInviteDialog.Result>() {
//
//                    @Override
//                    public void onSuccess(AppInviteDialog.Result result) {
////                        NetworkController.showCustomToast(
////                                InviteFriendsActivity.this,
////                                "Invitation Sent Successfully!");
//                        finish();
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        Log.d("Result", "Cancelled");
////                        NetworkController.showCustomToast(
////                                InviteFriendsActivity.this, "Cancelled");
//                        finish();
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//                        Log.d("Result", "Error " + exception.getMessage());
////                        NetworkController.showCustomToast(
////                                InviteFriendsActivity.this,
////                                "Error while inviting friends");
//                        finish();
//                    }
//                });
    }

    public void openDialogInvite(final Activity activity) {
        String AppURl = "https://fb.me/421570...5709";  //Generated from //fb developers

        String previewImageUrl = "http://someurl/13_dp.png";


        if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(AppURl).setPreviewImageUrl(previewImageUrl)
                    .build();

            AppInviteDialog appInviteDialog = new AppInviteDialog(activity);
            appInviteDialog.registerCallback(callbackManager,
                    new FacebookCallback<AppInviteDialog.Result>() {
                        @Override
                        public void onSuccess(AppInviteDialog.Result result) {
                            Log.d("Invitation", "Invitation Sent Successfully");
                            finish();
                        }

                        @Override
                        public void onCancel() {
                        }

                        @Override
                        public void onError(FacebookException e) {
                            Log.d("Invitation", "Error Occured");
                        }
                    });

            appInviteDialog.show(content);
        }else{
            Log.d(TagUtils.getTag(),"app invitation could not be shown");
        }
    }

    private void InviteFbFriends()
    {

        Log.d(TagUtils.getTag(),"invite facebook friends");
        String appLinkUrl, previewImageUrl;

//        appLinkUrl = "https://fb.me/144567099675348";
//        previewImageUrl = "http://caprispine.in/testing/uploads/ic_web.png";
//
//        AppInviteContent content = new AppInviteContent.Builder()
//                .setApplinkUrl(appLinkUrl)
//                .setPreviewImageUrl(previewImageUrl)
//                .build();
//        AppInviteDialog.show(this, content);


//        GameRequestContent content = new GameRequestContent.Builder()
//                .setMessage("Come play this level with me")
//                .build();
//        requestDialog.show(content);

        Uri targetUrl =
                AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        if (targetUrl != null) {
            Log.i("ActivityName", "App Link Target URL: " + targetUrl.toString());
        } else {
            AppLinkData.fetchDeferredAppLinkData(
                    this,
                    new AppLinkData.CompletionHandler() {
                        @Override
                        public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
                            //process applink data
                        }
                    });
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        callbackManager.onActivityResult(requestCode, responseCode, intent);
    }

    private void getPosts() {
        Log.d(TagUtils.getTag(), "access token:-" + AccessToken.getCurrentAccessToken().getToken());
        Log.d(TagUtils.getTag(), "user id:-" + AccessToken.getCurrentAccessToken().getUserId());
        attachAdapter();
        new GraphRequest(
                AccessToken.getCurrentAccessToken(), "/" + AccessToken.getCurrentAccessToken().getUserId() + "/taggable_friends", null, HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.d(TagUtils.getTag(), "graph response:-" + response.getJSONObject().toString());
                        parseData(response.getJSONObject().toString());
                    }
                }
        ).executeAsync();
    }

    public void attachAdapter() {
        facebookFriendsAdapter = new FacebookFriendsAdapter(this, null, facebookDataPOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_friends.setHasFixedSize(true);
        rv_friends.setAdapter(facebookFriendsAdapter);
        rv_friends.setLayoutManager(linearLayoutManager);
        rv_friends.setItemAnimator(new DefaultItemAnimator());
    }

    FacebookFriendsAdapter facebookFriendsAdapter;
    List<FacebookDataPOJO> facebookDataPOJOS = new ArrayList<>();

    public void parseData(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.optJSONArray("data");
            List<FacebookDataPOJO> newfacebookDataPOJOS = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                Log.d(TagUtils.getTag(), "jsonobject:-" + jsonObject1.toString());
                Gson gson = new Gson();
                FacebookDataPOJO facebookDataPOJO = gson.fromJson(jsonObject1.toString(), FacebookDataPOJO.class);
                newfacebookDataPOJOS.add(facebookDataPOJO);
            }

            if (newfacebookDataPOJOS.size() > 0) {
                inflateFriendLayout(newfacebookDataPOJOS);
            }

//            facebookFriendsAdapter.notifyDataSetChanged();

//            Gson gson=new Gson();
//            FacebookPOJO facebookPOJO=gson.fromJson(response,FacebookPOJO.class);
//            List<FacebookDataPOJO> facebookDataPOJOS=facebookPOJO.getFacebookDataPOJOS();

            try {
                JSONObject paginationJsonObject = jsonObject.optJSONObject("paging");
                final String next_url = paginationJsonObject.optString("next");
                Log.d(TagUtils.getTag(), "next url:-" + next_url);
                if (next_url.length() > 0) {
                    btn_next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callNextUsersAPI(next_url);
                        }
                    });
                } else {
                    btn_next.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inflateFriendLayout(List<FacebookDataPOJO> facebookDataPOJOS) {

        for (FacebookDataPOJO facebookDataPOJO : facebookDataPOJOS) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_facebook_friends, null);

            ImageView iv_image = view.findViewById(R.id.iv_image);
            TextView tv_friend_name = view.findViewById(R.id.tv_friend_name);

            Glide.with(getApplicationContext())
                    .load(facebookDataPOJO.getFacebookPicturePOJO().getFacebookPictureDataPOJO().getUrl())
                    .placeholder(R.drawable.ic_default_profile_pic)
                    .error(R.drawable.ic_default_profile_pic)
                    .dontAnimate()
                    .into(iv_image);

            tv_friend_name.setText(facebookDataPOJO.getName());

            ll_facebook_friends.addView(view);
        }
    }

    public void callNextUsersAPI(String next_url) {
        StringRequest req = new StringRequest(next_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TagUtils.getTag(), response.toString());
//                        parseHomeNewsResponse(response);
                        parseData(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TagUtils.getTag(), "api error:-" + error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(req);
    }
}