package com.ritvi.kaajneeti.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.erikagtierrez.multiple_media_picker.Gallery;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.activity.TagPeopleActivity;
import com.ritvi.kaajneeti.adapter.PollAnsAdapter;
import com.ritvi.kaajneeti.pojo.PollMediaAns;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;
import com.ritvi.kaajneeti.webservice.WebUploadService;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 13-04-2018.
 */
@SuppressLint("ValidFragment")
public class CreatePollFragment extends Fragment {

    private static final int OPEN_MEDIA_PICKER = 1;
    private static final int TAG_PEOPLE = 105;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 101;

    @BindView(R.id.et_whats)
    EditText et_whats;
    @BindView(R.id.tv_profile_description)
    TextView tv_profile_description;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.spinner_privpub)
    Spinner spinner_privpub;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.ll_check_in)
    LinearLayout ll_check_in;
    @BindView(R.id.ll_tag_friends)
    LinearLayout ll_tag_friends;
    @BindView(R.id.iv_add_ans)
    ImageView iv_add_ans;
    @BindView(R.id.rv_poll_media)
    RecyclerView rv_poll_media;
    @BindView(R.id.tv_remove_images)
    TextView tv_remove_images;
    @BindView(R.id.tv_add_ans)
    TextView tv_add_ans;
    @BindView(R.id.tv_post)
    TextView tv_post;
    @BindView(R.id.iv_poll_question)
    ImageView iv_poll_question;
    @BindView(R.id.iv_question_image)
    ImageView iv_question_image;
    @BindView(R.id.iv_remove_question_image)
    ImageView iv_remove_question_image;
    @BindView(R.id.frame_question_image)
    FrameLayout frame_question_image;

    List<UserProfilePOJO> taggeduserInfoPOJOS;
    String check_in;
    String privPublic = "";
    String question = "";

    String profile_description = "";
    String tagging_description = "";
    String place_description = "";

    boolean is_text_ans = true;
    String selected_question_image_path="";

    boolean is_question=true;

    public CreatePollFragment(List<UserProfilePOJO> userInfoPOJOS, String check_in, String privPublic, String question) {
        this.taggeduserInfoPOJOS = userInfoPOJOS;
        this.check_in = check_in;
        this.privPublic = privPublic;
        this.question = question;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_create_poll, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserProfilePOJO userProfilePOJO = Constants.userProfilePOJO;
        profile_description = "<b>" + userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName() + "</b> ";
        tagging_description = getTaggedDescription(taggeduserInfoPOJOS);
        updateProfileStatus();
        checkPlaceDescription(check_in);

        Glide.with(getActivity().getApplicationContext())
                .load(Constants.userProfilePOJO.getProfilePhotoPath())
                .error(R.drawable.ic_default_profile_pic)
                .placeholder(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(cv_profile_pic);


        if (privPublic.length() > 0) {
            if (privPublic.equalsIgnoreCase("private")) {
                spinner_privpub.setSelection(1);
            }
        }
        et_whats.setText(question);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelPoll();
            }
        });

        ll_check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPlace();
            }
        });

        ll_tag_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), TagPeopleActivity.class).putExtra("taggedpeople", (Serializable) taggeduserInfoPOJOS), TAG_PEOPLE);
            }
        });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i(TagUtils.getTag(), "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i(TagUtils.getTag(), "onKey Back listener is working!!!");
                    cancelPoll();
                    return true;
                }
                return false;
            }
        });

        iv_add_ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pollMediaAnsList.add(new PollMediaAns());
                pollAnsAdapter.notifyDataSetChanged();
            }
        });

        attachAdapter(false);
        iv_add_ans.callOnClick();
        iv_add_ans.callOnClick();

        tv_remove_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (PollMediaAns pollMediaAns : pollMediaAnsList) {
                    pollMediaAns.setFile_path("");
                }
                if (is_media_adapter) {
                    attachAdapter(false);
                } else {
                    pollAnsAdapter.notifyDataSetChanged();
                }
            }
        });

        tv_add_ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pollMediaAnsList.add(new PollMediaAns());
                pollAnsAdapter.notifyDataSetChanged();
            }
        });

        tv_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                createPoll();'
                checkData();
            }
        });

        iv_poll_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_question=true;
                selectMedia();
            }
        });

        iv_remove_question_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_question_image_path="";
                frame_question_image.setVisibility(View.GONE);
            }
        });
    }

    public void checkData() {
        Log.d(TagUtils.getTag(), "poll ans:-" + pollMediaAnsList.toString());
        boolean is_images_present = false;

        for (PollMediaAns pollMediaAns : pollMediaAnsList) {
            if(pollMediaAns.getFile_path().length()>0){
                is_images_present=true;
            }
        }

        if(is_images_present){
            boolean all_images_present=true;

            for(PollMediaAns pollMediaAns:pollMediaAnsList){
                if(pollMediaAns.getFile_path().length()==0){
                    all_images_present=false;
                }
            }

            if(all_images_present){
                createPoll();
            }else{
                ToastClass.showShortToast(getActivity().getApplicationContext(),"Please select all images");
            }
        }else{
            int ans_count=0;
            for(PollMediaAns pollMediaAns:pollMediaAnsList){
                if(pollMediaAns.getAns().length()!=0){
                    ans_count++;
                }
            }

            if(ans_count<2){
                ToastClass.showShortToast(getActivity().getApplicationContext(),"Please Enter atleast 2 ans");
            }else{
                createPoll();
            }
        }

    }

    public void createPoll() {
        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("user_profile_id", new StringBody(Constants.userProfilePOJO.getUserProfileId()));
            reqEntity.addPart("poll_question", new StringBody(et_whats.getText().toString()));
            if (spinner_privpub.getSelectedItemPosition() == 1) {
                reqEntity.addPart("poll_privacy", new StringBody("1"));
            } else {
                reqEntity.addPart("poll_privacy", new StringBody("0"));
            }
            reqEntity.addPart("valid_from_date", new StringBody(UtilityFunction.getCurrentDate()));
            reqEntity.addPart("valid_end_date", new StringBody("2018-05-30"));

            int count=0;
            for(PollMediaAns pollMediaAns:pollMediaAnsList){
                if(pollMediaAns.getFile_path().length()>0&&new File(pollMediaAns.getFile_path()).exists()){
                    reqEntity.addPart("file["+count+"]", new FileBody(new File(pollMediaAns.getFile_path())));
                }else{
                    reqEntity.addPart("file["+count+"]", new StringBody(""));
                }
                reqEntity.addPart("poll_answer["+count+"]", new StringBody(pollMediaAns.getAns()));
                count++;
            }

            if (selected_question_image_path.length() > 0&&new File(selected_question_image_path).exists()) {
                reqEntity.addPart("question", new FileBody(new File(selected_question_image_path)));
            }else{
                reqEntity.addPart("question", new StringBody(""));
            }

            new WebUploadService(reqEntity, getActivity(), new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    Log.d(TagUtils.getTag(), apicall + " :- " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                        if (jsonObject.optString("status").equals("success")) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                            getActivity().finishAffinity();
                        } else {
                            ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString(jsonObject.optString("message")));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "CREATE_POLL", true).execute(WebServicesUrls.SAVE_MY_POLL);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<String> getAllAns() {
        List<String> pollAnsList = new ArrayList<>();
        try {
            for (int i = 0; i < pollMediaAnsList.size(); i++) {
                if (pollMediaAnsList.get(i).getAns().length() > 0) {
                    pollAnsList.add(pollMediaAnsList.get(i).getAns());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pollAnsList;
    }


    public void cancelPoll() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Do you discard Poll?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
                getActivity().finishAffinity();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    int adapterPosition = -1;

    public void selectMediaMethod(int position){
        this.is_question=false;
        this.adapterPosition=position;
        selectMedia();
    }

    public void selectMedia() {
        Intent intent = new Intent(getActivity(), Gallery.class);
        // Set the title
        intent.putExtra("title", "Select media");
        // Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
        intent.putExtra("mode", 2);
        intent.putExtra("maxSelection", 1); // Optional
        startActivityForResult(intent, OPEN_MEDIA_PICKER);
    }

    public void findPlace() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    public void updateProfileStatus() {
        String profile = "";
        if (tagging_description.length() == 0 && place_description.length() == 0) {
            profile = profile_description;
        } else {
            profile = profile_description + " is " + tagging_description + place_description;
        }
        tv_profile_description.setText(Html.fromHtml(profile));
    }


    public String getTaggedDescription(List<UserProfilePOJO> stringList) {
        String description = "";
        if (stringList.size() == 1) {
            UserProfilePOJO userProfilePOJO = stringList.get(0);
            description = " with <b>" + userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName() + "</b> ";
        } else if (stringList.size() == 2) {
            UserProfilePOJO userProfilePOJO1 = stringList.get(0);
            UserProfilePOJO userProfilePOJO2 = stringList.get(1);
            description = " with <b>" + userProfilePOJO1.getFirstName() + " " + userProfilePOJO1.getLastName() + "</b> and <b>" +
                    userProfilePOJO2.getFirstName() + " " + userProfilePOJO2.getLastName() + "</b>";
        } else if (stringList.size() > 2) {
            UserProfilePOJO userProfilePOJO = stringList.get(0);
            description = " with <b>" + userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName() + "</b> and <b>" + (stringList.size() - 1) + " others";
        }
        return description;
    }

    public void checkPlaceDescription(String place_name) {
        if (place_name.length() > 0) {
            place_description = " - at <b>" + place_name + "</b>";
            updateProfileStatus();
        }
    }

    PollAnsAdapter pollAnsAdapter;
    boolean is_media_adapter = false;

    public void attachAdapter(boolean is_media) {
        this.is_media_adapter = is_media;
        if (!is_media) {
            pollAnsAdapter = new PollAnsAdapter(getActivity(), this, pollMediaAnsList);
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);

            rv_poll_media.setHasFixedSize(true);
            rv_poll_media.setAdapter(pollAnsAdapter);
            rv_poll_media.setLayoutManager(layoutManager);
            rv_poll_media.setNestedScrollingEnabled(false);
            rv_poll_media.setItemAnimator(new DefaultItemAnimator());
        } else {

            Log.d(TagUtils.getTag(), "poll media list:-" + pollMediaAnsList.toString());
            pollAnsAdapter = new PollAnsAdapter(getActivity(), this, pollMediaAnsList);
            GridLayoutManager layoutManager
                    = new GridLayoutManager(getActivity().getApplicationContext(), 2);

            rv_poll_media.setHasFixedSize(true);
            rv_poll_media.setAdapter(pollAnsAdapter);
            rv_poll_media.setLayoutManager(layoutManager);
            rv_poll_media.setNestedScrollingEnabled(false);
            rv_poll_media.setItemAnimator(new DefaultItemAnimator());
        }
    }

    List<PollMediaAns> pollMediaAnsList = new ArrayList<>();
    boolean is_txt_image = true;

    public void setAnsImage(String file_path) {
        if (is_media_adapter) {
            pollMediaAnsList.get(adapterPosition).setFile_path(file_path);
            pollAnsAdapter.notifyDataSetChanged();
        } else {
            pollMediaAnsList.get(adapterPosition).setFile_path(file_path);
            attachAdapter(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OPEN_MEDIA_PICKER) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK && data != null) {
                ArrayList<String> selectionResult = data.getStringArrayListExtra("result");
                if (selectionResult.size() > 0) {
                    if(is_question){
                        frame_question_image.setVisibility(View.VISIBLE);
                        selected_question_image_path=selectionResult.get(0);
                        Glide.with(getActivity().getApplicationContext())
                                .load(selectionResult.get(0))
                                .placeholder(R.drawable.ic_default_pic)
                                .error(R.drawable.ic_default_pic)
                                .into(iv_question_image);
                    }else {
                        setAnsImage(selectionResult.get(0));
                    }
                }
            }
        } else if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                Log.i(TagUtils.getTag(), "Place: " + place.getName());
                check_in = (String) place.getName();
                checkPlaceDescription(check_in);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i(TagUtils.getTag(), status.getStatusMessage());

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        } else if (requestCode == TAG_PEOPLE) {
            if (resultCode == Activity.RESULT_OK) {
                taggeduserInfoPOJOS = (List<UserProfilePOJO>) data.getSerializableExtra("taggedpeople");

                tagging_description = getTaggedDescription(taggeduserInfoPOJOS);
                updateProfileStatus();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
