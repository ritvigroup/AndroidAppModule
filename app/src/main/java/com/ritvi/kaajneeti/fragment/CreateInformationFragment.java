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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.erikagtierrez.multiple_media_picker.Gallery;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.adapter.MediaListAdapter;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 16-04-2018.
 */

@SuppressLint("ValidFragment")
public class CreateInformationFragment extends Fragment{

    private static final int TAG_PEOPLE = 105;
    static final int OPEN_MEDIA_PICKER = 1;

    @BindView(R.id.tv_profile_description)
    TextView tv_profile_description;
    @BindView(R.id.et_subject)
    EditText et_subject;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.spinner_privpub)
    Spinner spinner_privpub;
    @BindView(R.id.btn_attach)
    Button btn_attach;
    @BindView(R.id.rv_attachments)
    RecyclerView rv_attachments;


    String privPublic;
    String suggestion_subject;
    List<String> mediaFiles=new ArrayList<>();

    public CreateInformationFragment(List<String> mediaFiles, String privPublic, String suggestion_subject) {
        this.suggestion_subject=suggestion_subject;
        this.privPublic = privPublic;
        this.mediaFiles.addAll(mediaFiles);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_create_information,container,false);
        ButterKnife.bind(this,view);
        return view;
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


        if(privPublic.length()>0){
            if(privPublic.equalsIgnoreCase("private")){
                spinner_privpub.setSelection(1);
            }
        }
        tv_profile_description.setText(Html.fromHtml(profile_description));

        et_subject.setText(suggestion_subject);

        btn_attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectMedia();
            }
        });
        attachMediaAdapter();

    }


    public void selectMedia(){
        Intent intent= new Intent(getActivity(), Gallery.class);
        // Set the title
        intent.putExtra("title","Select media");
        // Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
        intent.putExtra("mode",1);
        intent.putExtra("maxSelection",5); // Optional
        startActivityForResult(intent,OPEN_MEDIA_PICKER);
    }



    MediaListAdapter mediaListAdapter;
    public void attachMediaAdapter() {
        mediaListAdapter = new MediaListAdapter(getActivity(), this, mediaFiles);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
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
                ArrayList<String> selectionResult=data.getStringArrayListExtra("result");
                mediaFiles.addAll(selectionResult);
                mediaListAdapter.notifyDataSetChanged();
            }
        }
    }

}
