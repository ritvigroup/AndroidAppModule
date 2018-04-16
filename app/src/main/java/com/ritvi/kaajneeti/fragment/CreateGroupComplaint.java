package com.ritvi.kaajneeti.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.activity.TagPeopleActivity;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 15-04-2018.
 */

@SuppressLint("ValidFragment")
public class CreateGroupComplaint extends Fragment{


    List<UserInfoPOJO> taggeduserInfoPOJOS=new ArrayList<>();
    private static final int TAG_PEOPLE = 105;
    @BindView(R.id.rv_attachments)
    RecyclerView rv_attachments;
    @BindView(R.id.tv_profile_description)
    TextView tv_profile_description;
    @BindView(R.id.et_subject)
    EditText et_subject;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.spinner_privpub)
    Spinner spinner_privpub;
    @BindView(R.id.tv_tag)
    TextView tv_tag;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_group_complaint_add,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    String privPublic;
    String complaint_name;
    List<String> mediaFiles;

    public CreateGroupComplaint(List<String> mediaFiles, String privPublic, String complaint_name) {
        this.complaint_name = complaint_name;
        this.privPublic = privPublic;
        this.mediaFiles=mediaFiles;
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
        et_subject.setText(Html.fromHtml(profile_description));

        tv_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), TagPeopleActivity.class).putExtra("taggedpeople", (Serializable) taggeduserInfoPOJOS), TAG_PEOPLE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            taggeduserInfoPOJOS = (List<UserInfoPOJO>) data.getSerializableExtra("taggedpeople");
            updateTaggingDesc();
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            //Write your code if there's no result
        }
    }

    public void updateTaggingDesc(){
        String users="";
        for(int i=0;i<taggeduserInfoPOJOS.size();i++){
            UserProfilePOJO userProfilePOJO=UtilityFunction.getUserProfilePOJO(taggeduserInfoPOJOS.get(i));
            if(i==(taggeduserInfoPOJOS.size()-1)){
                users+=userProfilePOJO.getFirstName()+" "+userProfilePOJO.getLastName();
            }else{
                users+=userProfilePOJO.getFirstName()+" "+userProfilePOJO.getLastName()+",";
            }
        }

        tv_tag.setText(users);
    }
}
