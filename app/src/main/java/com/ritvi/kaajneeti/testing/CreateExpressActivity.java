package com.ritvi.kaajneeti.testing;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.ritvi.kaajneeti.adapter.MediaListAdapter;
import com.ritvi.kaajneeti.fragment.CreateComplaintFragment;
import com.ritvi.kaajneeti.fragment.CreateEventFragment;
import com.ritvi.kaajneeti.fragment.CreateGroupComplaintFragment;
import com.ritvi.kaajneeti.fragment.CreateInformationFragment;
import com.ritvi.kaajneeti.fragment.CreatePollFragment;
import com.ritvi.kaajneeti.fragment.CreateSuggestionFragment;
import com.ritvi.kaajneeti.fragment.complaint.CreateOtherComplaintFragment;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;
import com.ritvi.kaajneeti.webservice.WebUploadService;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

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

public class CreateExpressActivity extends AppCompatActivity {

    static final int OPEN_MEDIA_PICKER = 1;
    private static final int TAG_PEOPLE = 105;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 101;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingUpPanelLayout;

    @BindView(R.id.ll_photo)
    LinearLayout ll_photo;
    @BindView(R.id.ll_tag_friends)
    LinearLayout ll_tag_friends;
    @BindView(R.id.ll_feeling)
    LinearLayout ll_feeling;
    @BindView(R.id.ll_check_in)
    LinearLayout ll_check_in;
    @BindView(R.id.ll_poll)
    LinearLayout ll_poll;
    @BindView(R.id.ll_event)
    LinearLayout ll_event;
    @BindView(R.id.ll_complaint)
    LinearLayout ll_complaint;
    @BindView(R.id.ll_suggestion)
    LinearLayout ll_suggestion;
    @BindView(R.id.ll_information)
    LinearLayout ll_information;
    @BindView(R.id.tv_profile_description)
    TextView tv_profile_description;
    @BindView(R.id.tv_post)
    TextView tv_post;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_media)
    RecyclerView rv_media;
    @BindView(R.id.spinner_privpub)
    Spinner spinner_privpub;
    @BindView(R.id.et_whats)
    EditText et_whats;

    List<UserProfilePOJO> taggeduserInfoPOJOS = new ArrayList<>();

    String profile_description = "";
    String tagging_description = "";
    String place_description = "";

    String check_in_place = "";


    List<String> mediaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_test);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        UserProfilePOJO userProfilePOJO = Constants.userProfilePOJO;
        profile_description = "<b>" + userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName() + "</b> ";
        updateProfileStatus();

        Glide.with(getApplicationContext())
                .load(Constants.userProfilePOJO.getProfilePhotoPath())
                .error(R.drawable.ic_default_profile_pic)
                .placeholder(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(cv_profile_pic);

        ll_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectMedia();
            }
        });
        ll_tag_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(CreateExpressActivity.this, TagPeopleActivity.class).putExtra("taggedpeople", (Serializable) taggeduserInfoPOJOS), TAG_PEOPLE);
            }
        });
        ll_check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPlace();
            }
        });

        ll_poll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPollFragment();
            }
        });

        ll_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEventFragment();
            }
        });

        tv_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePost();
            }
        });

        ll_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openComplaintDialog();
            }
        });

        ll_suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSuggestionFragment();
            }
        });

        ll_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInformationFragment();
            }
        });

        attachMediaAdapter();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String type = bundle.getString("type");
            if (type != null) {
                switch (type) {
                    case "issue":
                        break;
                    case "complaint":
                        ll_complaint.callOnClick();
                        break;
                    case "suggestion":
                        ll_suggestion.callOnClick();
                        break;
                    case "information":
                        ll_information.callOnClick();
                        break;
                    case "event":
                        ll_event.callOnClick();
                        break;
                    case "poll":
                        ll_poll.callOnClick();
                        break;
                }
            }
        }

    }

    public void openPollFragment() {
        CreatePollFragment createPollFragment = new CreatePollFragment(taggeduserInfoPOJOS, check_in_place, spinner_privpub.getSelectedItem().toString(), et_whats.getText().toString());
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, createPollFragment, "createPollFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openSuggestionFragment() {
        CreateSuggestionFragment createSuggestionFragment = new CreateSuggestionFragment(mediaList, spinner_privpub.getSelectedItem().toString(), et_whats.getText().toString());
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, createSuggestionFragment, "createSuggestionFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openInformationFragment() {
        CreateInformationFragment createInformationFragment = new CreateInformationFragment(mediaList, spinner_privpub.getSelectedItem().toString(), et_whats.getText().toString());
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, createInformationFragment, "createInformationFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openComplaintDialog() {

        final Dialog dialog1 = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_complaint_type);
        dialog1.setTitle("Complaint Type");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final RadioGroup rg_complaint_type = dialog1.findViewById(R.id.rg_complaint_type);
        RadioButton rb_self = dialog1.findViewById(R.id.rb_self);
        RadioButton rb_other = dialog1.findViewById(R.id.rb_other);
        RadioButton rb_group = dialog1.findViewById(R.id.rb_group);

        Button btn_cancel = (Button) dialog1.findViewById(R.id.btn_cancel);

        rg_complaint_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = rg_complaint_type.getCheckedRadioButtonId();
                if (id != -1) {
                    switch (id) {
                        case R.id.rb_self:
                            createSelfComplaint();
                            dialog1.dismiss();
                            break;
                        case R.id.rb_other:
                            createOtherComplaint();
                            dialog1.dismiss();
                            break;
                        case R.id.rb_group:
                            createGroupComplaint();
                            dialog1.dismiss();
                            break;
                    }
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
    }

    public void createSelfComplaint() {
        CreateComplaintFragment createComplaintFragment = new CreateComplaintFragment(mediaList, spinner_privpub.getSelectedItem().toString(), et_whats.getText().toString());
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, createComplaintFragment, "createComplaintFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void createOtherComplaint() {
        CreateOtherComplaintFragment createOtherComplaintFragment= new CreateOtherComplaintFragment(mediaList, spinner_privpub.getSelectedItem().toString(), et_whats.getText().toString());
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main,createOtherComplaintFragment, "createOtherComplaintFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void createGroupComplaint() {
        CreateGroupComplaintFragment createGroupComplaint = new CreateGroupComplaintFragment(taggeduserInfoPOJOS, mediaList, spinner_privpub.getSelectedItem().toString(), et_whats.getText().toString());
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, createGroupComplaint, "createGroupComplaint");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openEventFragment() {
        CreateEventFragment createEventFragment = new CreateEventFragment(taggeduserInfoPOJOS, check_in_place, spinner_privpub.getSelectedItem().toString(), et_whats.getText().toString(), mediaList);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, createEventFragment, "createEventFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    MediaListAdapter mediaListAdapter;

    public void attachMediaAdapter() {
        mediaListAdapter = new MediaListAdapter(this, null, mediaList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_media.setHasFixedSize(true);
        rv_media.setAdapter(mediaListAdapter);
        rv_media.setLayoutManager(linearLayoutManager);
        rv_media.setItemAnimator(new DefaultItemAnimator());
    }

    public void selectMedia() {
        Intent intent = new Intent(this, Gallery.class);
        // Set the title
        intent.putExtra("title", "Select media");
        // Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
        intent.putExtra("mode", 1);
        intent.putExtra("maxSelection", 5); // Optional
        startActivityForResult(intent, OPEN_MEDIA_PICKER);
    }

    ArrayList<String> files_selected = new ArrayList<>();


    public void savePost() {
        try {

            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            String activity = "";
//            if(spinner_feeling.getSelectedItemPosition()!=0){
//                activity=feelingPOJOArrayList.get(spinner_feeling.getSelectedItemPosition()-1).getFeelingId();
//            }

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("", ""));
            reqEntity.addPart("user_profile_id", new StringBody(Constants.userProfilePOJO.getUserProfileId()));
            reqEntity.addPart("title", new StringBody(et_whats.getText().toString()));
            reqEntity.addPart("location", new StringBody(check_in_place));
            reqEntity.addPart("description", new StringBody(et_whats.getText().toString()));
            reqEntity.addPart("url", new StringBody(""));
            reqEntity.addPart("feeling", new StringBody(activity));

            for (int i = 0; i < taggeduserInfoPOJOS.size(); i++) {
                reqEntity.addPart("post_tag[" + i + "]", new StringBody(taggeduserInfoPOJOS.get(i).getUserProfileId()));
            }

            int count = 0;

//            for (EventAttachment eventAttachment : attachFragment.getEventAttachments()) {
            for (String file_path : mediaList) {
//                Log.d(TagUtils.getTag(), "attachment:-" + eventAttachment.toString());

//                if (eventAttachment.getType().equals(Constants.EVENT_IMAGE_ATTACH)) {

                if (file_path.contains(".mp4")
                        || file_path.contains(".MP4")) {
                    reqEntity.addPart("file[" + (count) + "]", new FileBody(new File(file_path)));
                    reqEntity.addPart("thumb[" + (count) + "]", new FileBody(new File(UtilityFunction.saveThumbFile(new File(file_path)))));
                }else{
                    reqEntity.addPart("file[" + (count) + "]", new FileBody(new File(file_path)));
                    reqEntity.addPart("thumb[" + (count) + "]", new StringBody(""));
                }
//                } else if (eventAttachment.getType().equals(Constants.EVENT_VIDEO_ATTACH)) {
//                    reqEntity.addPart("file[" + (count) + "]", new FileBody(new File(eventAttachment.getFile_path())));
//                    reqEntity.addPart("thumb[" + (count) + "]", new FileBody(new File(eventAttachment.getThumb_path())));
//                }
                count++;
            }

            new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    Log.d(TagUtils.getTag(), apicall + " :- " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        ToastClass.showShortToast(getApplicationContext(), jsonObject.optString("message"));
                        if (jsonObject.optString("status").equals("success")) {
//                            startActivity(new Intent(CreatePostActivity.this,ApplicationSubmittedActivity.class).putExtra("comp_type","information"));
                            ToastClass.showShortToast(getApplicationContext(), "Posted Successfully");
                            startActivity(new Intent(CreateExpressActivity.this, HomeActivity.class));
                            finishAffinity();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "CREATE_POST", true).execute(WebServicesUrls.SAVE_POST);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        cancelPost(this);
    }

    public void cancelPost(final Activity activity) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Do you want to discard Post?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(activity, HomeActivity.class));
                finishAffinity();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_MEDIA_PICKER) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK && data != null) {
                files_selected.clear();
                ArrayList<String> selectionResult = data.getStringArrayListExtra("result");
                files_selected.addAll(selectionResult);
                mediaList.addAll(files_selected);
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
        } else if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TagUtils.getTag(), "Place: " + place.getName());
                check_in_place = (String) place.getName();
                checkPlaceDescription(check_in_place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TagUtils.getTag(), status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public void checkPlaceDescription(String place_name) {
        place_description = " - at <b>" + place_name + "</b>";
        updateProfileStatus();
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

    public void updateProfileStatus() {
        String profile = "";
        if (tagging_description.length() == 0 && place_description.length() == 0) {
            profile = profile_description;
        } else {
            profile = profile_description + " is " + tagging_description + place_description;
        }
        tv_profile_description.setText(Html.fromHtml(profile));
    }

    public void findPlace() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
