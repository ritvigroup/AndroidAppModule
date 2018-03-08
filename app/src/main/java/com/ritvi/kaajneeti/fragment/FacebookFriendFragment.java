package com.ritvi.kaajneeti.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.adapter.FacebookFriendsAdapter;
import com.ritvi.kaajneeti.pojo.facebook.FacebookDataPOJO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 04-03-2018.
 */

public class FacebookFriendFragment extends Fragment{
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private Button btn_get_friends;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.ll_facebook_friends)
    LinearLayout ll_facebook_friends;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();
        View view=inflater.inflate(R.layout.frag_facebook_friends,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        btn_get_friends = (Button) view.findViewById(R.id.btn_get_friends);
        loginButton.setHeight(100);
        loginButton.setTextColor(Color.WHITE);
        loginButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        loginButton.setCompoundDrawablePadding(0);


        FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getPosts();
                btn_next.setEnabled(false);
            }

            @Override
            public void onCancel() {
                Log.d(TagUtils.getTag(),"facebook login cancel");
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
                getPosts();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getPosts() {
        Log.d(TagUtils.getTag(), "access token:-" + AccessToken.getCurrentAccessToken().getToken());
        Log.d(TagUtils.getTag(), "user id:-" + AccessToken.getCurrentAccessToken().getUserId());
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
            try {
                JSONObject paginationJsonObject = jsonObject.optJSONObject("paging");
                final String next_url = paginationJsonObject.optString("next");
                Log.d(TagUtils.getTag(), "next url:-" + next_url);
                if (next_url.length() > 0) {
                    btn_next.setEnabled(true);
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
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_facebook_friends, null);

            ImageView iv_image = view.findViewById(R.id.iv_image);
            TextView tv_friend_name = view.findViewById(R.id.tv_friend_name);

            Glide.with(getActivity().getApplicationContext())
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
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(req);
    }
}
