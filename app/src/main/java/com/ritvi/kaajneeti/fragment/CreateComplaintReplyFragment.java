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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.erikagtierrez.multiple_media_picker.Gallery;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.adapter.MediaListAdapter;
import com.ritvi.kaajneeti.pojo.analyze.ComplaintPOJO;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 18-04-2018.
 */

@SuppressLint("ValidFragment")
public class CreateComplaintReplyFragment extends Fragment {

    static final int OPEN_MEDIA_PICKER = 1;

    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.et_description)
    EditText et_description;
    @BindView(R.id.spinner_progress)
    Spinner spinner_progress;
    @BindView(R.id.tv_attach)
    TextView tv_attach;
    @BindView(R.id.rv_attachments)
    RecyclerView rv_attachments;
    @BindView(R.id.tv_post)
    TextView tv_post;

    List<String> mediaFiles=new ArrayList<>();

    ComplaintPOJO complaintPOJO;
    public CreateComplaintReplyFragment(ComplaintPOJO complaintPOJO){
        this.complaintPOJO=complaintPOJO;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_create_complaint_reply, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachMediaAdapter();
        tv_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveComplaint();
            }
        });
        tv_attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectMedia();
            }
        });

        tv_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveComplaint();
            }
        });
    }


    public void selectMedia() {
        Intent intent = new Intent(getActivity(), Gallery.class);
        intent.putExtra("title", "Select media");
        // Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
        intent.putExtra("mode", 1);
        intent.putExtra("maxSelection", 5 - mediaFiles.size()); // Optional
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


    public void saveComplaint() {
        try {

            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("", ""));
            reqEntity.addPart("user_profile_id", new StringBody(Constants.userProfilePOJO.getUserProfileId()));
            reqEntity.addPart("complaint_id", new StringBody(complaintPOJO.getComplaintId()));
            reqEntity.addPart("history_id", new StringBody(""));
            reqEntity.addPart("title", new StringBody(et_title.getText().toString()));
            reqEntity.addPart("description", new StringBody(et_description.getText().toString()));
            if(spinner_progress.getSelectedItemPosition()==0) {
                reqEntity.addPart("current_status", new StringBody("0"));
            }else{
                reqEntity.addPart("current_status", new StringBody("4"));
            }

            int count = 0;

            for (String file_path : mediaFiles) {
                reqEntity.addPart("file[" + (count) + "]", new FileBody(new File(file_path)));
                reqEntity.addPart("thumb[" + (count) + "]", new StringBody(""));
            }

            for (int i = 0; i < mediaFiles.size(); i++) {
                reqEntity.addPart("file[" + (i) + "]", new FileBody(new File(mediaFiles.get(i))));
                reqEntity.addPart("thumb[" + (i) + "]", new StringBody(""));
            }


            new WebUploadService(reqEntity, getActivity(), new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    Log.d(TagUtils.getTag(), apicall + " :- " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                        if (jsonObject.optString("status").equals("success")) {

                            if(getActivity() instanceof HomeActivity){
                                HomeActivity homeActivity= (HomeActivity) getActivity();
                                homeActivity.refreshComplaintTrack();
                            }

                            getActivity().onBackPressed();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "CREATE_EVENT", true).execute(WebServicesUrls.SAVE_COMPLAINT_HISTORY);
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
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
        }
    }
}
