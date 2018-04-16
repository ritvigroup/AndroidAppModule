package com.ritvi.kaajneeti.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.erikagtierrez.multiple_media_picker.Gallery;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.activity.ApplicationSubmittedActivity;
import com.ritvi.kaajneeti.activity.TagPeopleActivity;
import com.ritvi.kaajneeti.adapter.CustomAutoCompleteAdapter;
import com.ritvi.kaajneeti.adapter.MediaListAdapter;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.ResponseListCallback;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponseList;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;
import com.ritvi.kaajneeti.webservice.WebUploadService;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 15-04-2018.
 */

@SuppressLint("ValidFragment")
public class CreateComplaintFragment extends Fragment {

    List<UserInfoPOJO> taggeduserInfoPOJOS = new ArrayList<>();

    static final int OPEN_MEDIA_PICKER = 1;

    @BindView(R.id.tv_profile_description)
    TextView tv_profile_description;
    @BindView(R.id.et_subject)
    EditText et_subject;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.spinner_privpub)
    Spinner spinner_privpub;
    private static final int TAG_PEOPLE = 105;
    @BindView(R.id.tv_tag)
    TextView tv_tag;
    @BindView(R.id.btn_attach)
    Button btn_attach;
    @BindView(R.id.rv_attachments)
    RecyclerView rv_attachments;
    @BindView(R.id.iv_favorite_leader_add)
    ImageView iv_favorite_leader_add;
    @BindView(R.id.auto_fav_list)
    AutoCompleteTextView auto_fav_list;
    @BindView(R.id.tv_post)
    TextView tv_post;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_complaint_add, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    String privPublic;
    String complaint_name;
    List<String> mediaFiles = new ArrayList<>();

    public CreateComplaintFragment(List<String> mediaFiles, String privPublic, String complaint_name) {
        this.complaint_name = complaint_name;
        this.privPublic = privPublic;
        this.mediaFiles.addAll(mediaFiles);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserProfilePOJO userProfilePOJO = UtilityFunction.getUserProfilePOJO(Constants.userInfoPOJO);
        String profile_description = "<b>" + userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName() + "</b> ";


        Glide.with(getActivity().getApplicationContext())
                .load(Constants.userInfoPOJO.getProfilePhotoPath())
                .error(R.drawable.ic_default_profile_pic)
                .placeholder(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(cv_profile_pic);


        if (privPublic.length() > 0) {
            if (privPublic.equalsIgnoreCase("private")) {
                spinner_privpub.setSelection(1);
            }
        }
        tv_profile_description.setText(Html.fromHtml(profile_description));
        et_subject.setText(complaint_name);

        tv_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), TagPeopleActivity.class).putExtra("taggedpeople", (Serializable) taggeduserInfoPOJOS), TAG_PEOPLE);
            }
        });

        btn_attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectMedia();
            }
        });
        attachMediaAdapter();

        auto_fav_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (leaderPOJOS.size() > 0) {
                    leader_id = leaderPOJOS.get(i).getUserProfileLeader().getUserProfileId();
                }
            }
        });

        tv_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveComplaint();
            }
        });


        callLeaderAPI();
    }

    public String leader_id = "";
    List<UserInfoPOJO> leaderPOJOS = new ArrayList<>();
    CustomAutoCompleteAdapter adapter = null;

    public void callLeaderAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userInfoPOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));
//        new WebServiceBase(nameValuePairs, this, this, CALL_ALL_LEADER, true).execute(WebServicesUrls.GET_MY_FAVORITE_LEADER);
        new WebServiceBaseResponseList<UserInfoPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<UserInfoPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<UserInfoPOJO> responseListPOJO) {
                leaderPOJOS.clear();
                if (responseListPOJO.isSuccess()) {
                    leaderPOJOS.addAll(responseListPOJO.getResultList());
                    adapter = new CustomAutoCompleteAdapter(getActivity(), (ArrayList<UserInfoPOJO>) leaderPOJOS);
                    auto_fav_list.setAdapter(adapter);
                }
            }
        }, UserInfoPOJO.class, "CALL_LEADER_API", true).execute(WebServicesUrls.GET_MY_FAVORITE_LEADER);
    }

    public void selectMedia() {
        Intent intent = new Intent(getActivity(), Gallery.class);
        intent.putExtra("title", "Select media");
        // Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
        intent.putExtra("mode", 1);
        intent.putExtra("maxSelection", 5); // Optional
        startActivityForResult(intent, OPEN_MEDIA_PICKER);
    }

    MediaListAdapter mediaListAdapter;

    public void attachMediaAdapter() {
        mediaListAdapter = new MediaListAdapter(getActivity(), this, mediaFiles);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_attachments.setHasFixedSize(true);
        rv_attachments.setAdapter(mediaListAdapter);
        rv_attachments.setLayoutManager(linearLayoutManager);
        rv_attachments.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_MEDIA_PICKER) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK && data != null) {
                ArrayList<String> selectionResult = data.getStringArrayListExtra("result");
                mediaFiles.addAll(selectionResult);
                mediaListAdapter.notifyDataSetChanged();
            }
        } else if (resultCode == Activity.RESULT_OK) {
            taggeduserInfoPOJOS = (List<UserInfoPOJO>) data.getSerializableExtra("taggedpeople");
            updateTaggingDesc();
        }
    }

    public void updateTaggingDesc() {
        String users = "";
        for (int i = 0; i < taggeduserInfoPOJOS.size(); i++) {
            UserProfilePOJO userProfilePOJO = UtilityFunction.getUserProfilePOJO(taggeduserInfoPOJOS.get(i));
            if (i == (taggeduserInfoPOJOS.size() - 1)) {
                users += userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName();
            } else {
                users += userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName() + ",";
            }
        }

        tv_tag.setText(users);
    }


    public void saveComplaint() {
        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("", ""));
            reqEntity.addPart("user_profile_id", new StringBody(Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));
            reqEntity.addPart("complaint_subject", new StringBody(et_subject.getText().toString()));
            reqEntity.addPart("complaint_description", new StringBody(""));
            UserProfilePOJO userProfilePOJO = UtilityFunction.getUserProfilePOJO(Constants.userInfoPOJO);
            reqEntity.addPart("applicant_name", new StringBody(userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName()));
            reqEntity.addPart("applicant_father_name", new StringBody(""));
            reqEntity.addPart("applicant_mobile", new StringBody(userProfilePOJO.getMobile()));
            reqEntity.addPart("assign_to_profile_id", new StringBody(leader_id));
            reqEntity.addPart("complaint_type_id", new StringBody("1"));


            int count = 0;

            for (String file_path : mediaFiles) {
                reqEntity.addPart("file[" + (count) + "]", new FileBody(new File(file_path)));
                reqEntity.addPart("thumb[" + (count) + "]", new StringBody(""));
            }


//            for (EventAttachment eventAttachment : eventAttachments) {
//                Log.d(TagUtils.getTag(), "event attachment:-" + eventAttachment.toString());
//                if (eventAttachment.getType().equals(Constants.EVENT_IMAGE_ATTACH)) {
//                    reqEntity.addPart("file[" + (count) + "]", new FileBody(new File(eventAttachment.getFile_path())));
//                    reqEntity.addPart("thumb[" + (count) + "]", new StringBody(""));
//                } else if (eventAttachment.getType().equals(Constants.EVENT_VIDEO_ATTACH)) {
//                    reqEntity.addPart("file[" + (count) + "]", new FileBody(new File(eventAttachment.getFile_path())));
//                    reqEntity.addPart("thumb[" + (count) + "]", new FileBody(new File(eventAttachment.getThumb_path())));
//                }
//                count++;
//            }


            new WebUploadService(reqEntity, getActivity(), new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    Log.d(TagUtils.getTag(), apicall + " :- " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                        if (jsonObject.optString("status").equals("success")) {
                            startActivity(new Intent(getActivity(), ApplicationSubmittedActivity.class).putExtra("comp_type", "complaint"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "CREATE_EVENT", true).execute(WebServicesUrls.POST_COMPLAINT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
