package com.ritvi.kaajneeti.fragment.profile;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.FileUtils;
import com.ritvi.kaajneeti.Util.Pref;
import com.ritvi.kaajneeti.Util.SetViews;
import com.ritvi.kaajneeti.Util.StringUtils;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.adapter.FriendGridAdapter;
import com.ritvi.kaajneeti.adapter.HomeFeedAdapter;
import com.ritvi.kaajneeti.adapter.SummaryAdapter;
import com.ritvi.kaajneeti.fragment.contribute.ContributeAmountFragment;
import com.ritvi.kaajneeti.fragment.profile.friends.FriendsListFragment;
import com.ritvi.kaajneeti.fragment.search.SearchFragment;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.pojo.home.FeedPOJO;
import com.ritvi.kaajneeti.pojo.profile.FullProfilePOJO;
import com.ritvi.kaajneeti.pojo.profile.SummaryPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.pojo.userdetail.AddressPOJO;
import com.ritvi.kaajneeti.pojo.userdetail.EducationPOJO;
import com.ritvi.kaajneeti.pojo.userdetail.ProfilePOJO;
import com.ritvi.kaajneeti.pojo.userdetail.ProfileResultPOJO;
import com.ritvi.kaajneeti.pojo.userdetail.WorkPOJO;
import com.ritvi.kaajneeti.webservice.ResponseCallBack;
import com.ritvi.kaajneeti.webservice.ResponseListCallback;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponse;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponseList;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;
import com.ritvi.kaajneeti.webservice.WebUploadService;
import com.yalantis.ucrop.UCrop;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 21-04-2018.
 */

@SuppressLint("ValidFragment")
public class UserProfileFragment extends Fragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_profile_name)
    TextView tv_profile_name;
    @BindView(R.id.tv_more)
    TextView tv_more;
    @BindView(R.id.tv_complaint)
    TextView tv_complaint;
    @BindView(R.id.tv_suggestion)
    TextView tv_suggestion;
    @BindView(R.id.tv_information)
    TextView tv_information;
    @BindView(R.id.rv_post)
    RecyclerView rv_post;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.ll_more_info)
    LinearLayout ll_more_info;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_mobile)
    TextView tv_mobile;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_work)
    TextView tv_work;
    @BindView(R.id.tv_education)
    TextView tv_education;
    @BindView(R.id.ll_transition)
    LinearLayout ll_transition;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_edit)
    ImageView iv_edit;
    @BindView(R.id.iv_profile_image_edit)
    ImageView iv_profile_image_edit;
    @BindView(R.id.tv_bio)
    TextView tv_bio;
    @BindView(R.id.rv_friends)
    RecyclerView rv_friends;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rv_activity)
    RecyclerView rv_activity;
    @BindView(R.id.frame_search)
    FrameLayout frame_search;
    @BindView(R.id.iv_leader_icon)
    ImageView iv_leader_icon;
    @BindView(R.id.ll_contribute)
    LinearLayout ll_contribute;
    @BindView(R.id.ll_friend)
    LinearLayout ll_friend;
    @BindView(R.id.ll_message)
    LinearLayout ll_message;
    @BindView(R.id.ll_block)
    LinearLayout ll_block;
    @BindView(R.id.tv_add_friend)
    TextView tv_add_friend;
    @BindView(R.id.iv_qr_code)
    ImageView iv_qr_code;
    @BindView(R.id.ll_edit_profile)
    LinearLayout ll_edit_profile;
    @BindView(R.id.ll_connect)
    LinearLayout ll_connect;
    @BindView(R.id.ll_view_as_menu)
    LinearLayout ll_view_as_menu;
    @BindView(R.id.ll_mine_menu)
    LinearLayout ll_mine_menu;
    @BindView(R.id.iv_cover_edit)
    ImageView iv_cover_edit;
    @BindView(R.id.ic_cover_pic)
    ImageView ic_cover_pic;
    @BindView(R.id.cv_connect)
    CardView cv_connect;

    private static final int PICK_IMAGE_REQUEST = 101;
    private static final int CAMERA_REQUEST = 102;

    boolean is_profle_pic=true;


    String user_id;
    String profile_id;

    public UserProfileFragment(String user_id, String profile_id) {
        this.user_id = user_id;
        this.profile_id = profile_id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_user_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();

        if (profile_id.equals(Constants.userProfilePOJO.getUserProfileId())) {
            iv_edit.setVisibility(View.VISIBLE);
            iv_profile_image_edit.setVisibility(View.VISIBLE);
        } else {
            iv_edit.setVisibility(View.GONE);
            iv_profile_image_edit.setVisibility(View.GONE);
        }

        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    homeActivity.showProfileEditFragment(user_id, profile_id);
                }
            }
        });
        ll_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_edit.callOnClick();
            }
        });


        iv_qr_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showQrCode();
            }
        });

        iv_profile_image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(getActivity(), view);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuitem) {
                        switch (menuitem.getItemId()) {
                            case R.id.popup_camera:
                                is_profle_pic=true;
                                startCamera();
                                break;
                            case R.id.popup_gallery:
                                is_profle_pic=true;
                                selectProfilePic();
                                break;
                            case R.id.popup_remove:
                                is_profle_pic=true;
                                removeProfilePic();
                                break;
                        }
                        return false;
                    }
                });
                menu.inflate(R.menu.menu_profile_pic_option);
                menu.show();
            }
        });

        iv_cover_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu menu = new PopupMenu(getActivity(), v);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuitem) {
                        switch (menuitem.getItemId()) {
                            case R.id.popup_camera:
                                is_profle_pic=false;
                                startCamera();
                                break;
                            case R.id.popup_gallery:
                                is_profle_pic=false;
                                selectProfilePic();
                                break;
                            case R.id.popup_remove:
                                is_profle_pic=false;
                                removeProfilePic();
                                break;
                        }
                        return false;
                    }
                });
                menu.inflate(R.menu.menu_profile_pic_option);
                menu.show();
            }
        });


        ll_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    FriendsListFragment friendsListFragment = new FriendsListFragment(fullProfilePOJO.getProfilePOJO().getUserId(), fullProfilePOJO.getProfilePOJO().getUserProfileId());
                    homeActivity.replaceFragmentinFrameHome(friendsListFragment, "friendsListFragment");
                }
            }
        });

        getAllProfileData();
        getFriendSummary();
        getAllHomeData();

        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll_more_info.getVisibility() == View.VISIBLE) {
                    ll_more_info.setVisibility(View.GONE);
                } else {
                    ll_more_info.setVisibility(View.VISIBLE);
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        frame_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    SearchFragment searchFragment = new SearchFragment();
                    homeActivity.replaceFragmentinFrameHome(searchFragment, "searchFragment");
                }
            }
        });
        int minHeight = UtilityFunction.convertedDP(getActivity().getApplicationContext(), UtilityFunction.screenDimensions(getActivity().getApplicationContext())[0]);
        rv_post.setMinimumHeight(minHeight);

        ll_mine_menu.setVisibility(View.GONE);
        ll_view_as_menu.setVisibility(View.GONE);

        if (Constants.userProfilePOJO.getUserProfileId().equalsIgnoreCase(profile_id)) {
            ll_mine_menu.setVisibility(View.VISIBLE);
        } else {
            ll_view_as_menu.setVisibility(View.VISIBLE);
        }

    }

    private void showQrCode() {
        final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_qr_code);
        dialog1.setTitle("");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btn_close = dialog1.findViewById(R.id.btn_close);
        ImageView iv_qr_code = dialog1.findViewById(R.id.iv_qr_code);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(user_id + "," + profile_id, BarcodeFormat.QR_CODE, 400, 400);
            iv_qr_code.setImageBitmap(bitmap);
        } catch (Exception e) {

        }
    }


    public void removeProfilePic() {
        String remove_url="";
        if(is_profle_pic){
            remove_url=WebServicesUrls.REMOVE_PROFILE_PIC;
        }else{
            remove_url=WebServicesUrls.REMOVE_COVER_PROFILE_PIC;
        }
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", profile_id));
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("success")) {
                        Gson gson = new Gson();
                        UserProfilePOJO userProfilePOJO = gson.fromJson(jsonObject.optJSONObject("result").toString(), UserProfilePOJO.class);
                        image_path_string = "";
                        if(is_profle_pic) {
                            cv_profile_pic.setImageResource(R.drawable.ic_default_profile_pic);
                        }else{
                            ic_cover_pic.setImageResource(R.drawable.ic_default_profile_pic);
                        }
                        fullProfilePOJO.setProfilePOJO(userProfilePOJO);
                        setUpProfileData(fullProfilePOJO);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "REMOVE_PROFILE_API", true).execute(remove_url);
    }

    String pictureImagePath = "";

    public void startCamera() {
        String strMyImagePath = Environment.getExternalStorageDirectory() + File.separator + "temp.png";

        pictureImagePath = strMyImagePath;
        File file = new File(pictureImagePath);
        Uri outputFileUri = Uri.fromFile(file);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getActivity().getApplicationContext(), getActivity().getPackageName() + ".fileProvider", file);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

        } else {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        }
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }


    public void selectProfilePic() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    String image_path_string = "";


    public void getAllHomeData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id", profile_id));

        new WebServiceBaseResponseList<FeedPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<FeedPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<FeedPOJO> responseListPOJO) {
                try {
                    feedPOJOS.clear();
                    if (responseListPOJO.isSuccess()) {

                        feedPOJOS.addAll(responseListPOJO.getResultList());
                        homeFeedAdapter.notifyDataSetChanged();

                    } else {
                        ToastClass.showShortToast(getActivity(), responseListPOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, FeedPOJO.class, "CALL_FEED_DATA", true).execute(WebServicesUrls.FRIEND_HOME_PAGE_DATA);
    }

    public void getFriendSummary() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id", profile_id));
        new WebServiceBaseResponseList<SummaryPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<SummaryPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<SummaryPOJO> responseListPOJO) {
                try {
                    if (responseListPOJO.isSuccess()) {
                        SummaryAdapter summaryAdapter = new SummaryAdapter(getActivity(), UserProfileFragment.this, responseListPOJO.getResultList(), fullProfilePOJO.getProfilePOJO().getUserId(), fullProfilePOJO.getProfilePOJO().getUserProfileId());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        rv_activity.setHasFixedSize(true);
                        rv_activity.setAdapter(summaryAdapter);
                        rv_activity.setLayoutManager(linearLayoutManager);
                        rv_activity.setItemAnimator(new DefaultItemAnimator());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, SummaryPOJO.class, "USER SUMMARY", true).execute(WebServicesUrls.GET_MY_FRIEND_TOTAL_SUMMARY);
    }

    FullProfilePOJO fullProfilePOJO;

    public void getAllProfileData() {
        Log.d(TagUtils.getTag(), "profile refresh called");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id", profile_id));
        new WebServiceBaseResponse<FullProfilePOJO>(nameValuePairs, getActivity(), new ResponseCallBack<FullProfilePOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<FullProfilePOJO> responsePOJO) {
                if (responsePOJO.isSuccess()) {
                    fullProfilePOJO = responsePOJO.getResult();
                    setUpProfileData(responsePOJO.getResult());
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), responsePOJO.getMessage());
                }
            }
        }, FullProfilePOJO.class, "CALL_PROFILE_API", true).execute(WebServicesUrls.FULL_PROFILE_URL);
    }

    public void setUpProfileData(final FullProfilePOJO fullProfilePOJO) {
        if (fullProfilePOJO != null) {

            Glide.with(getActivity().getApplicationContext())
                    .load(fullProfilePOJO.getProfilePOJO().getProfilePhotoPath())
                    .placeholder(R.drawable.ic_default_profile_pic)
                    .error(R.drawable.ic_default_profile_pic)
                    .dontAnimate()
                    .into(cv_profile_pic);

            Glide.with(getActivity().getApplicationContext())
                    .load(fullProfilePOJO.getProfilePOJO().getCoverPhotoPath())
                    .placeholder(R.drawable.ic_default_profile_pic)
                    .error(R.drawable.ic_default_profile_pic)
                    .dontAnimate()
                    .into(ic_cover_pic);

            if (!Constants.userProfilePOJO.getProfilePhotoPath().equals(fullProfilePOJO.getProfilePOJO().getProfilePhotoPath())) {
                SetViews.changeProfilePics(getActivity().getApplicationContext(), fullProfilePOJO.getProfilePOJO().getProfilePhotoPath());
            }

            if (fullProfilePOJO.getProfilePOJO().getFirstName().equals("") ||
                    fullProfilePOJO.getProfilePOJO().getLastName().equals("")) {
                tv_profile_name.setText(fullProfilePOJO.getProfilePOJO().getFirstName());
                tv_title.setText(fullProfilePOJO.getProfilePOJO().getFirstName());
            } else {
                tv_profile_name.setText(fullProfilePOJO.getProfilePOJO().getFirstName() + " " + fullProfilePOJO.getProfilePOJO().getLastName());
                tv_title.setText(fullProfilePOJO.getProfilePOJO().getFirstName() + " " + fullProfilePOJO.getProfilePOJO().getLastName());
            }

            tv_email.setText(fullProfilePOJO.getProfilePOJO().getEmail());
            tv_mobile.setText(fullProfilePOJO.getProfilePOJO().getMobile());
            tv_address.setText(fullProfilePOJO.getProfilePOJO().getAddress());

            if (fullProfilePOJO.getAddressPOJOS().size() > 0) {
                AddressPOJO addressPOJO = fullProfilePOJO.getAddressPOJOS().get(fullProfilePOJO.getAddressPOJOS().size() - 1);
                tv_address.setText(addressPOJO.getAddress() + " , " + addressPOJO.getCity() + " , " + addressPOJO.getState() + " , " + addressPOJO.getCountry());
            }

            if (fullProfilePOJO.getWorkPOJOList().size() > 0) {
                WorkPOJO workPOJO = fullProfilePOJO.getWorkPOJOList().get(fullProfilePOJO.getWorkPOJOList().size() - 1);
                tv_work.setText(workPOJO.getWorkPosition() + " , " + workPOJO.getWorkPlace() + " , " + workPOJO.getWorkLocation());
            }

            if (fullProfilePOJO.getEducationPOJOS().size() > 0) {
                EducationPOJO educationPOJO = fullProfilePOJO.getEducationPOJOS().get(fullProfilePOJO.getEducationPOJOS().size() - 1);
                tv_education.setText(educationPOJO.getQualification() + " , " + educationPOJO.getQualificationUniversity() + " , " + educationPOJO.getQualificationLocation());
            }
            if (fullProfilePOJO.getProfilePOJO().getUserBio().length() > 0) {
                tv_bio.setText(fullProfilePOJO.getProfilePOJO().getUserBio());
            }
            if (fullProfilePOJO.getFriendsProfilePOJOS().size() > 0) {

                int friend_list_size = fullProfilePOJO.getFriendsProfilePOJOS().size();
//                if(friend_list_size<=3){
//                    rv_friends.setMinimumHeight(UtilityFunction.convertDpToPx(getActivity().getApplicationContext(),150));
//                }else if(friend_list_size>3&&friend_list_size<=6){
//                    rv_friends.setMinimumHeight(UtilityFunction.convertDpToPx(getActivity().getApplicationContext(),300));
//                }else{
//                    rv_friends.setMinimumHeight(UtilityFunction.convertDpToPx(getActivity().getApplicationContext(),450));
//                }
                friendProfilePOJOS.clear();
                friendProfilePOJOS.addAll(fullProfilePOJO.getFriendsProfilePOJOS());
                attachFriendAdapter();
            }

            if (fullProfilePOJO.getProfilePOJO().getUserTypeId() != null) {
                if (fullProfilePOJO.getProfilePOJO().getUserTypeId().equalsIgnoreCase("2")) {
                    iv_leader_icon.setVisibility(View.VISIBLE);
                } else {
                    iv_leader_icon.setVisibility(View.GONE);
                }
            }

            if (fullProfilePOJO.getProfilePOJO().getUserProfileId().equalsIgnoreCase(Constants.userProfilePOJO.getUserProfileId())) {
                ll_contribute.setVisibility(View.GONE);
            } else {
                ll_contribute.setVisibility(View.VISIBLE);
            }

            ll_contribute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getActivity() instanceof HomeActivity) {
                        HomeActivity homeActivity = (HomeActivity) getActivity();
                        homeActivity.replaceFragmentinFrameHome(new ContributeAmountFragment(fullProfilePOJO.getProfilePOJO()), "ContributeAmountFragment");
                    }
                }
            });

            tv_bio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateBIO(fullProfilePOJO.getProfilePOJO().getUserBio());
                }
            });
            ll_incoming_request.setVisibility(View.GONE);
            switch (fullProfilePOJO.getProfilePOJO().getMyFriend()) {
                case 0:
                    tv_add_friend.setText("Add Friend");
                    break;
                case 1:
                    tv_add_friend.setText("Cancel Request");
                    break;
                case 2:
                    ll_incoming_request.setVisibility(View.VISIBLE);
                    tv_add_friend.setText("Respond");
                    break;
                case 3:
                    tv_add_friend.setText("Unfriend");
                    break;
                case 4:
                    tv_add_friend.setText("Follow");
                    break;
                case -1:
                    break;
            }

            ll_friend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (fullProfilePOJO.getProfilePOJO().getMyFriend()) {
                        case 0:
                            sendFriendRequest(fullProfilePOJO.getProfilePOJO());
                            break;
                        case 1:
                            undoFriendRequest(fullProfilePOJO.getProfilePOJO());
                            break;
                        case 2:
                            break;
                        case 3:
                            sendFriendRequest(fullProfilePOJO.getProfilePOJO());
                            break;
                        case 4:
                            break;
                        case -1:
                            break;
                    }
                }
            });

            btn_confirm_friend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    acceptRequest(fullProfilePOJO.getProfilePOJO());
                }
            });

            btn_cancel_request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancelFriendRequest(fullProfilePOJO.getProfilePOJO());
                }
            });

            if(fullProfilePOJO.getFriendsProfilePOJOS()!=null&&fullProfilePOJO.getFriendsProfilePOJOS().size()>0){
                cv_connect.setVisibility(View.VISIBLE);
            }else{
                cv_connect.setVisibility(View.GONE);
            }
        }
    }


    public void acceptRequest(final UserProfilePOJO userProfilePOJO) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id", userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                        userProfilePOJO.setMyFriend(3);
                        getAllProfileData();
                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CALL_ADD_FRIEND_API", true).execute(WebServicesUrls.SEND_FRIEND_REQUEST);
    }


    public void cancelFriendRequest(final UserProfilePOJO userProfilePOJO) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id", userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                        getAllProfileData();
                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, "CALL_ADD_FRIEND_API", true).execute(WebServicesUrls.CANCEL_FRIEND_REQUEST);
    }


    public void undoFriendRequest(final UserProfilePOJO userProfilePOJO) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id", userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                        getAllProfileData();
                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, "CALL_ADD_FRIEND_API", true).execute(WebServicesUrls.UNDO_FRIEND_REQUEST);
    }

    public void sendFriendRequest(final UserProfilePOJO userProfilePOJO) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id", userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                        int friend = jsonObject.optInt("friend");
                        if (friend == -1) {

                        }
                        getAllProfileData();

                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CALL_ADD_FRIEND_API", true).execute(WebServicesUrls.SEND_FRIEND_REQUEST);
    }

    @BindView(R.id.ll_incoming_request)
    LinearLayout ll_incoming_request;
    @BindView(R.id.btn_confirm_friend)
    Button btn_confirm_friend;
    @BindView(R.id.btn_cancel_request)
    Button btn_cancel_request;

    List<UserProfilePOJO> friendProfilePOJOS = new ArrayList<>();
    FriendGridAdapter friendGridAdapter;

    public void attachFriendAdapter() {
        friendGridAdapter = new FriendGridAdapter(getActivity(), this, friendProfilePOJOS);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rv_friends.setHasFixedSize(true);
        rv_friends.setAdapter(friendGridAdapter);
        rv_friends.setLayoutManager(gridLayoutManager);
        rv_friends.setNestedScrollingEnabled(false);
        rv_friends.setItemAnimator(new DefaultItemAnimator());
    }

    public void updateBIO(String bio) {
        final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_update_bio);
        dialog1.setTitle("Select");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btn_update = dialog1.findViewById(R.id.btn_update);
        final EditText et_bio = dialog1.findViewById(R.id.et_bio);
        et_bio.setText(bio);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_bio.getText().toString().length() > 0) {
                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                    nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
                    nameValuePairs.add(new BasicNameValuePair("user_profile_id", profile_id));
                    nameValuePairs.add(new BasicNameValuePair("user_bio", et_bio.getText().toString()));

                    new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
                        @Override
                        public void onGetMsg(String apicall, String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optString("status").equals("success")) {
                                    tv_bio.setText(et_bio.getText().toString());
                                    dialog1.dismiss();
                                } else {
                                    ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, "UPDAT_BIO", true).execute(WebServicesUrls.UPDATE_BIO);
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Enter Biod");
                }
            }
        });

    }

    ProfileResultPOJO profileResultPOJO;

    public void getAllProfileDetails() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        new WebServiceBaseResponse<ProfileResultPOJO>(nameValuePairs, getActivity(), new ResponseCallBack<ProfileResultPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<ProfileResultPOJO> responsePOJO) {
                try {
                    if (responsePOJO.isSuccess()) {
                        profileResultPOJO = responsePOJO.getResult();
                        setValues();
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), responsePOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, ProfileResultPOJO.class, "CALL_PROFILE_API", true).execute(WebServicesUrls.GET_MORE_PROFILE_DATA);
    }

    public void setValues() {
        if (profileResultPOJO != null && profileResultPOJO.getProfilePOJO() != null) {
            ProfilePOJO profilePOJO = profileResultPOJO.getProfilePOJO();
            tv_profile_name.setText(profilePOJO.getFirstName() + " " + profilePOJO.getLastName());
            tv_email.setText(profilePOJO.getEmail());
            tv_mobile.setText(profilePOJO.getMobile());
            String address = profilePOJO.getCity() + " " + profilePOJO.getState() + " " + profilePOJO.getCountry();
            tv_address.setText(address);
            String work = "";
            if (profileResultPOJO.getWorkPOJOList().size() > 0) {
                work = "Worked at " + profileResultPOJO.getWorkPOJOList().get(0).getWorkPosition();
                tv_work.setText(work);
            }

            String education = "";
            if (profileResultPOJO.getEducationPOJOS().size() > 0) {
                education = "Studied " + profileResultPOJO.getEducationPOJOS().get(0).getQualification() + " at " + profileResultPOJO.getEducationPOJOS().get(0).getQualificationUniversity();
                tv_education.setText(education);
            }
        }
    }


    public void callAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));

        new WebServiceBaseResponseList<FeedPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<FeedPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO responseListPOJO) {
                feedPOJOS.clear();
                try {
                    if (responseListPOJO.isSuccess()) {
                        feedPOJOS.addAll(responseListPOJO.getResultList());
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), responseListPOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                homeFeedAdapter.notifyDataSetChanged();
            }
        }, FeedPOJO.class, "call_complaint_list_api", true).execute(WebServicesUrls.ALL_POST);
    }


    HomeFeedAdapter homeFeedAdapter;
    List<FeedPOJO> feedPOJOS = new ArrayList<>();

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_post.setLayoutManager(linearLayoutManager);
        homeFeedAdapter = new HomeFeedAdapter(rv_post, getActivity(), this, feedPOJOS, getChildFragmentManager());
        rv_post.setHasFixedSize(true);
        rv_post.setAdapter(homeFeedAdapter);
        rv_post.setItemAnimator(new DefaultItemAnimator());

    }


    public void getAnalysisData(UserProfilePOJO userProfilePOJO) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                        JSONObject resultJsonObject = jsonObject.optJSONObject("result");
                        String TotalEvent = resultJsonObject.optString("TotalEvent");
                        String TotalPoll = resultJsonObject.optString("TotalPoll");
                        String TotalPost = resultJsonObject.optString("TotalPost");
                        String TotalSuggestion = resultJsonObject.optString("TotalSuggestion");
                        String TotalInformation = resultJsonObject.optString("TotalInformation");
                        String TotalComplaint = resultJsonObject.optString("TotalComplaint");

                        tv_complaint.setText(TotalComplaint);
                        tv_information.setText(TotalInformation);
                        tv_suggestion.setText(TotalSuggestion);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "GET_ALL_DATA", false).execute(WebServicesUrls.ALL_SUMMARY_DATA);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (null == data)
                    return;
                Uri selectedImageUri = data.getData();
                System.out.println(selectedImageUri.toString());
                // MEDIA GALLERY
                String selectedImagePath = getPath(
                        getActivity(), selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if (selectedImagePath != null && selectedImagePath != "") {
                    image_path_string = selectedImagePath;
                    Log.d(TagUtils.getTag(), "selected path:-" + selectedImagePath);
//                    setProfilePic();
                    cropProfilePic(image_path_string);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Selected File is Corrupted", Toast.LENGTH_LONG).show();
                }
                System.out.println("Image Path =" + selectedImagePath);
            }
            return;
        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            File imgFile = new File(pictureImagePath);
            if (imgFile.exists()) {
                Bitmap bmp = BitmapFactory.decodeFile(pictureImagePath);
                bmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth() / 4, bmp.getHeight() / 4, false);
                String strMyImagePath = FileUtils.getChatDir();
                File file_name = new File(strMyImagePath + File.separator + System.currentTimeMillis() + ".png");
                FileOutputStream fos = null;

                try {
                    fos = new FileOutputStream(file_name);
                    Log.d(TagUtils.getTag(), "taking photos");
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                    image_path_string = file_name.toString();
//                    setProfilePic();
                    cropProfilePic(image_path_string);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return;
        }if (resultCode == Activity.RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            setProfilePic(resultUri.getPath());
        }
    }

    public void cropProfilePic(String source) {
        if (new File(source).exists()) {
            Uri uri = Uri.fromFile(new File(source));
            String destPath = FileUtils.getPhotoFolder() + File.separator + System.currentTimeMillis() + ".png";
            Uri destUri = Uri.fromFile(new File(destPath));
            UCrop.of(uri, destUri)
                    .start(getActivity());
        } else {
            ToastClass.showShortToast(getActivity().getApplicationContext(), "File is corrupted");
        }
    }

    public void setProfilePic(String image_path_string) {

        Log.d(TagUtils.getTag(),"image path string:-"+image_path_string);
        if (image_path_string.length() > 0 && new File(image_path_string).exists()) {
            if(is_profle_pic) {
                setPicture(image_path_string);
            }else{
                setCoverPic(image_path_string);
            }
        }
    }


    public void setPicture(String image_path_string){
        Glide.with(getActivity().getApplicationContext())
                .load(image_path_string)
                .error(R.drawable.ic_default_profile_pic)
                .placeholder(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(cv_profile_pic);


        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            if (image_path_string.length() > 0 && new File(image_path_string).exists()) {
                FileBody bin1 = new FileBody(new File(image_path_string));
                reqEntity.addPart("photo", bin1);
            } else {
                reqEntity.addPart("photo", new StringBody(""));
            }

            reqEntity.addPart("user_id", new StringBody(user_id));
            reqEntity.addPart("user_profile_id", new StringBody(profile_id));

            new WebUploadService(reqEntity, getActivity(), new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    Log.d(TagUtils.getTag(), apicall + ":-" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optString("status").equals("success")) {
                            Gson gson = new Gson();
                            Pref.SetStringPref(getActivity().getApplicationContext(), StringUtils.USER_PROFILE, jsonObject.optJSONObject("result").toString());
                            UserProfilePOJO userProfilePOJO = gson.fromJson(jsonObject.optJSONObject("result").toString(), UserProfilePOJO.class);
                            SetViews.changeProfilePics(getActivity().getApplicationContext(), userProfilePOJO.getProfilePhotoPath());
                            Constants.userProfilePOJO = userProfilePOJO;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, "CALL_SAVE_PROFILE_API", true).execute(WebServicesUrls.UPDATE_USER_PROFILE_PHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setCoverPic(String image_path_string){

        Glide.with(getActivity().getApplicationContext())
                .load(image_path_string)
                .error(R.drawable.ic_default_profile_pic)
                .placeholder(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(ic_cover_pic);

        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            if (image_path_string.length() > 0 && new File(image_path_string).exists()) {
                FileBody bin1 = new FileBody(new File(image_path_string));
                reqEntity.addPart("cover", bin1);
            } else {
                reqEntity.addPart("cover", new StringBody(""));
            }

            reqEntity.addPart("user_id", new StringBody(user_id));
            reqEntity.addPart("user_profile_id", new StringBody(profile_id));

            new WebUploadService(reqEntity, getActivity(), new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    Log.d(TagUtils.getTag(), apicall + ":-" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optString("status").equals("success")) {
                            Gson gson = new Gson();
                            Pref.SetStringPref(getActivity().getApplicationContext(), StringUtils.USER_PROFILE, jsonObject.optJSONObject("result").toString());
                            UserProfilePOJO userProfilePOJO = gson.fromJson(jsonObject.optJSONObject("result").toString(), UserProfilePOJO.class);
                            SetViews.changeProfilePics(getActivity().getApplicationContext(), userProfilePOJO.getProfilePhotoPath());
                            Constants.userProfilePOJO = userProfilePOJO;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, "CALL_SAVE_PROFILE_API", true).execute(WebServicesUrls.UPDATE_USER_PROFILE_COVER_PHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }


}
