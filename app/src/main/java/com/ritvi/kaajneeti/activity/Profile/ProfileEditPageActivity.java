package com.ritvi.kaajneeti.activity.Profile;

import android.annotation.TargetApi;
import android.app.Activity;
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
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.FileUtils;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.ritvi.kaajneeti.pojo.userdetail.EducationPOJO;
import com.ritvi.kaajneeti.pojo.userdetail.ProfileResultPOJO;
import com.ritvi.kaajneeti.pojo.userdetail.WorkPOJO;
import com.ritvi.kaajneeti.webservice.ResponseCallBack;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponse;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;
import com.ritvi.kaajneeti.webservice.WebUploadService;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEditPageActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

//    @BindView(R.id.ll_work)
//    LinearLayout ll_work;
//    @BindView(R.id.ll_add_work)
//    LinearLayout ll_add_work;
//    @BindView(R.id.ll_add_education)
//    LinearLayout ll_add_education;
//    @BindView(R.id.ll_add_address)
//    LinearLayout ll_add_address;
//    @BindView(R.id.ll_profile_info)
//    LinearLayout ll_profile_info;

    private static final int PICK_IMAGE_REQUEST = 101;
    private static final int CAMERA_REQUEST = 102;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 103;

    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.iv_calendar)
    ImageView iv_calendar;
    @BindView(R.id.et_first_name)
    EditText et_first_name;
    @BindView(R.id.et_last_name)
    EditText et_last_name;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_alt_phone)
    EditText et_alt_phone;
    @BindView(R.id.et_dob)
    EditText et_dob;
    @BindView(R.id.rg_gender)
    RadioGroup rg_gender;
    @BindView(R.id.rb_male)
    RadioButton rb_male;
    @BindView(R.id.rb_female)
    RadioButton rb_female;
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.et_city)
    EditText et_city;
    @BindView(R.id.et_state)
    EditText et_state;
    @BindView(R.id.et_country)
    EditText et_country;
    @BindView(R.id.et_pincode)
    EditText et_pincode;

    @BindView(R.id.et_college_name)
    EditText et_college_name;
    @BindView(R.id.et_qualification)
    EditText et_qualification;
    @BindView(R.id.et_college_place)
    EditText et_college_place;
    @BindView(R.id.tv_college_from)
    TextView tv_college_from;
    @BindView(R.id.ll_college_to)
    LinearLayout ll_college_to;
    @BindView(R.id.tv_college_to)
    TextView tv_college_to;
    @BindView(R.id.view_college_to)
    View view_college_to;
    @BindView(R.id.check_college_currently)
    CheckBox check_college_currently;
    @BindView(R.id.btn_save_college)
    Button btn_save_college;

    @BindView(R.id.et_work)
    EditText et_work;
    @BindView(R.id.et_work_position)
    EditText et_work_position;
    @BindView(R.id.et_work_location)
    EditText et_work_location;
    @BindView(R.id.tv_work_to)
    TextView tv_work_to;
    @BindView(R.id.tv_work_from)
    TextView tv_work_from;
    @BindView(R.id.btn_update_work)
    Button btn_update_work;
    @BindView(R.id.check_currently_work)
    CheckBox check_currently_work;
    @BindView(R.id.view_work_to)
    View view_work_to;
    @BindView(R.id.ll_work_to)
    LinearLayout ll_work_to;
    @BindView(R.id.til_work_location)
    TextInputLayout til_work_location;


    @BindView(R.id.iv_personal_view)
    ImageView iv_personal_view;
    @BindView(R.id.ll_profile_info)
    LinearLayout ll_profile_info;

    @BindView(R.id.iv_relation_view)
    ImageView iv_relation_view;
    @BindView(R.id.ll_relationship)
    LinearLayout ll_relationship;

    @BindView(R.id.iv_address_view)
    ImageView iv_address_view;
    @BindView(R.id.ll_address)
    LinearLayout ll_address;

    @BindView(R.id.iv_work_view)
    ImageView iv_work_view;
    @BindView(R.id.ll_work)
    LinearLayout ll_work;

    @BindView(R.id.iv_education_view)
    ImageView iv_education_view;
    @BindView(R.id.ll_education)
    LinearLayout ll_education;
    @BindView(R.id.transition)
    ViewGroup transition;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    UserInfoPOJO userInfoPOJO;

    private static final String WORK_FROM = "work_from";
    private static final String WORK_TO = "work_to";
    private static final String EDUCATION_FROM = "education_from";
    private static final String EDUCATION_TO = "education_to";

    private static String CALENDAR_TYPE = WORK_FROM;


    private static final String WORK_LOCATION = "work_location";
    private static final String EDUCATION_LOCATION = "education_location";

    private static String LOCATION_TYPE = WORK_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_page);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Profile Edit");

        userInfoPOJO = (UserInfoPOJO) getIntent().getSerializableExtra("userInfo");

        Glide.with(getApplicationContext())
                .load(userInfoPOJO.getProfilePhotoPath())
                .into(cv_profile_pic);

//        ll_add_work.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ProfileEditPageActivity.this,AddWorkActivity.class));
//            }
//        });
//
//        ll_add_education.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ProfileEditPageActivity.this,AddEducationActivity.class));
//            }
//        });
//
//        ll_add_address.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ProfileEditPageActivity.this,AddAddressActivity.class));
//            }
//        });
//
//        ll_profile_info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ProfileEditPageActivity.this,AddPersonalInfoActivity.class));
//            }
//        });
        iv_personal_view.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(transition);
                if (ll_profile_info.getVisibility() == View.VISIBLE) {
                    ll_profile_info.setVisibility(View.GONE);
//                    setGoneAnimation(ll_profile_info);
                } else {
                    ll_profile_info.setVisibility(View.VISIBLE);
//                    setVisibleAnimation(ll_profile_info);
                }
            }
        });

        iv_relation_view.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(transition);
                if (ll_relationship.getVisibility() == View.VISIBLE) {
                    ll_relationship.setVisibility(View.GONE);
                } else {
                    ll_relationship.setVisibility(View.VISIBLE);
                }
            }
        });

        iv_address_view.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(transition);
                if (ll_address.getVisibility() == View.VISIBLE) {
                    ll_address.setVisibility(View.GONE);
                } else {
                    ll_address.setVisibility(View.VISIBLE);
                }
            }
        });
        iv_work_view.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(transition);
                if (ll_work.getVisibility() == View.VISIBLE) {
                    ll_work.setVisibility(View.GONE);
                } else {
                    ll_work.setVisibility(View.VISIBLE);
                }
            }
        });

        iv_education_view.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(transition);
                if (ll_education.getVisibility() == View.VISIBLE) {
                    ll_education.setVisibility(View.GONE);
                } else {
                    ll_education.setVisibility(View.VISIBLE);
                }
            }
        });

        cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(ProfileEditPageActivity.this, view);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuitem) {
                        switch (menuitem.getItemId()) {
                            case R.id.popup_camera:
                                startCamera();
                                break;
                            case R.id.popup_gallery:
                                selectProfilePic();
                                break;
                        }
                        return false;
                    }
                });
                menu.inflate(R.menu.menu_profile_pic_option);
                menu.show();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserProfile();
            }
        });

        iv_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ProfileEditPageActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Select DOB");
            }
        });


        tv_work_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CALENDAR_TYPE = WORK_FROM;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ProfileEditPageActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Select DOB");
            }
        });

        tv_work_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CALENDAR_TYPE = WORK_TO;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ProfileEditPageActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Select DOB");
            }
        });

        btn_update_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilityFunction.checkEdits(et_work, et_work_location)) {
                    callWorkUpdate();
                } else {
                    ToastClass.showShortToast(getApplicationContext(), "Please Enter Work Fields Properly");
                }
            }
        });

        et_work_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LOCATION_TYPE=WORK_LOCATION;
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(ProfileEditPageActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });
        et_college_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LOCATION_TYPE=EDUCATION_LOCATION;
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(ProfileEditPageActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });
        til_work_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LOCATION_TYPE = WORK_LOCATION;
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(ProfileEditPageActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });

        check_currently_work.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkPresentWorking(b);
            }
        });

        check_college_currently.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkCollegePursuing(b);
            }
        });

        btn_save_college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCollege();
            }
        });


        tv_college_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CALENDAR_TYPE = EDUCATION_FROM;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ProfileEditPageActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Select DOB");
            }
        });

        tv_college_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CALENDAR_TYPE = EDUCATION_TO;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ProfileEditPageActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Select DOB");
            }
        });

        getAllProfileDetails();
    }

    public void checkCollegePursuing(boolean b) {
        if (b) {
            ll_college_to.setVisibility(View.GONE);
            view_college_to.setVisibility(View.GONE);
        } else {
            ll_college_to.setVisibility(View.VISIBLE);
            view_college_to.setVisibility(View.VISIBLE);
        }
    }

    public void checkPresentWorking(boolean is_currently) {
        if (is_currently) {
            ll_work_to.setVisibility(View.GONE);
            view_work_to.setVisibility(View.GONE);
        } else {
            ll_work_to.setVisibility(View.VISIBLE);
            view_work_to.setVisibility(View.VISIBLE);
        }
    }

    public void saveCollege() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userInfoPOJO.getUserProfileCitizen().getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("education_id", ""));
        nameValuePairs.add(new BasicNameValuePair("qualification", et_qualification.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("location", et_college_place.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("university", et_college_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("from", tv_college_from.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("to", tv_college_to.getText().toString()));
        if(check_college_currently.isChecked()) {
            nameValuePairs.add(new BasicNameValuePair("persuing", "1"));
        }else{
            nameValuePairs.add(new BasicNameValuePair("persuing", "0"));
        }
        nameValuePairs.add(new BasicNameValuePair("private_public", ""));

        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {

            }
        }, "CALL_COLLEGE_SAVE", true).execute(WebServicesUrls.UPDATE_PROFILE_EDUCATION);
    }

    public void callWorkUpdate() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userInfoPOJO.getUserProfileCitizen().getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("work_id", "0"));
        nameValuePairs.add(new BasicNameValuePair("work", et_work.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("place", et_work_location.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("location", et_work_location.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("from", tv_work_from.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("to", tv_work_to.getText().toString()));
        if (check_currently_work.isChecked()) {
            nameValuePairs.add(new BasicNameValuePair("currently", "1"));
        } else {
            nameValuePairs.add(new BasicNameValuePair("currently", "0"));
        }
        nameValuePairs.add(new BasicNameValuePair("private_public", "1"));

        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {

            }
        }, "CALL_WORK_API", true).execute(WebServicesUrls.UPDATE_PROFILE_WORK);
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
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".fileProvider", file);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

        } else {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        }
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }
    ProfileResultPOJO profileResultPOJO;
    public void getAllProfileDetails() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userInfoPOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));
        new WebServiceBaseResponse<ProfileResultPOJO>(nameValuePairs, this, new ResponseCallBack<ProfileResultPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<ProfileResultPOJO> responsePOJO) {
                try {
                    if (responsePOJO.isSuccess()) {
                        profileResultPOJO=responsePOJO.getResult();
                        setValues();
                    }else{
                        ToastClass.showShortToast(getApplicationContext(),responsePOJO.getMessage());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, ProfileResultPOJO.class,"CALL_PROFILE_API", true).execute(WebServicesUrls.GET_MORE_PROFILE_DATA);
    }

    public void setValues(){
        if(profileResultPOJO.getProfilePOJO()!=null) {
            et_first_name.setText(profileResultPOJO.getProfilePOJO().getFirstName());
            et_last_name.setText(profileResultPOJO.getProfilePOJO().getLastName());
            et_email.setText(profileResultPOJO.getProfilePOJO().getEmail());
            et_phone.setText(profileResultPOJO.getProfilePOJO().getMobile());
            et_alt_phone.setText(profileResultPOJO.getProfilePOJO().getAltMobile());
            et_city.setText(profileResultPOJO.getProfilePOJO().getCity());
            et_state.setText(profileResultPOJO.getProfilePOJO().getState());
            et_country.setText(profileResultPOJO.getProfilePOJO().getCountry());
            et_pincode.setText(profileResultPOJO.getProfilePOJO().getZipCode());
        }

        if(profileResultPOJO.getWorkPOJOList()!=null&&profileResultPOJO.getWorkPOJOList().size()>0){
            for(WorkPOJO workPOJO:profileResultPOJO.getWorkPOJOList()){
                inflateWorkView(workPOJO);
            }
        }
        if(profileResultPOJO.getEducationPOJOS()!=null&&profileResultPOJO.getEducationPOJOS().size()>0){
            for(EducationPOJO educationPOJO:profileResultPOJO.getEducationPOJOS()){
                inflateEducation(educationPOJO);
            }
        }


    }

    public void inflateEducation(EducationPOJO educationPOJO){
        if(educationPOJO!=null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.inflate_education_preview, null);

            EditText et_college_name=view.findViewById(R.id.et_college_name);
            EditText et_qualification=view.findViewById(R.id.et_qualification);
            EditText et_college_place=view.findViewById(R.id.et_college_place);
            TextView tv_college_from=view.findViewById(R.id.tv_college_from);
            final LinearLayout ll_college_to=view.findViewById(R.id.ll_college_to);
            final View view_college_to=view.findViewById(R.id.view_college_to);
            CheckBox check_college_currently=view.findViewById(R.id.check_college_currently);
            Button btn_save_college=view.findViewById(R.id.btn_save_college);
            TextView tv_name= view.findViewById(R.id.tv_name);
            ImageView iv_delete = view.findViewById(R.id.iv_delete);
            ImageView iv_plus = view.findViewById(R.id.iv_plus);
            final LinearLayout ll_inflate_education = view.findViewById(R.id.ll_inflate_education);


            tv_name.setText(educationPOJO.getQualification());
            check_college_currently.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        ll_college_to.setVisibility(View.GONE);
                        view_college_to.setVisibility(View.GONE);
                    }else{
                        ll_college_to.setVisibility(View.VISIBLE);
                        view_college_to.setVisibility(View.VISIBLE);
                    }
                }
            });

            et_college_name.setText(educationPOJO.getQualificationUniversity());
            et_college_place.setText(educationPOJO.getQualificationLocation());
            et_qualification.setText(educationPOJO.getQualification());
            tv_college_from.setText(educationPOJO.getQualificationFrom());
            tv_college_to.setText(educationPOJO.getQualificationTo());

            if(educationPOJO.getPersuing().equals("1")){
                check_college_currently.setChecked(true);
            }else{
                check_college_currently.setChecked(false);
            }

            iv_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ll_inflate_education.getVisibility()==View.VISIBLE){
                        ll_inflate_education.setVisibility(View.GONE);
                    }else{
                        ll_inflate_education.setVisibility(View.VISIBLE);
                    }
                }
            });


            ll_education.addView(view);
        }
    }

    public void inflateWorkView(WorkPOJO workPOJO){
        if(workPOJO!=null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.inflate_work_preview, null);
            EditText et_work = view.findViewById(R.id.et_work);
            EditText et_work_position = view.findViewById(R.id.et_work_position);
            EditText et_work_location = view.findViewById(R.id.et_work_location);
            TextView tv_work_from = view.findViewById(R.id.tv_work_from);
            TextView tv_work_to = view.findViewById(R.id.tv_work_to);
            CheckBox check_currently_work = view.findViewById(R.id.check_currently_work);
            Button btn_update_work = view.findViewById(R.id.btn_update_work);
            TextView tv_name= view.findViewById(R.id.tv_name);
            ImageView iv_delete = view.findViewById(R.id.iv_delete);
            ImageView iv_plus = view.findViewById(R.id.iv_plus);
            final LinearLayout ll_work_layout = view.findViewById(R.id.ll_work_layout);
            final LinearLayout ll_work_to = view.findViewById(R.id.ll_work_to);
            final View view_work_to = view.findViewById(R.id.view_work_to);

            et_work.setText(workPOJO.getWorkPosition());
            tv_name.setText(workPOJO.getWorkPosition());
            et_work_position.setText(workPOJO.getWorkPlace());
            et_work_location.setText(workPOJO.getWorkLocation());

            tv_work_from.setText(workPOJO.getWorkFrom());
            tv_work_to.setText(workPOJO.getWorkTo());

            check_currently_work.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        ll_work_to.setVisibility(View.GONE);
                        view_work_to.setVisibility(View.GONE);
                    }else{
                        ll_work_to.setVisibility(View.VISIBLE);
                        view_work_to.setVisibility(View.VISIBLE);
                    }
                }
            });

            tv_work_to.setText(workPOJO.getWorkTo());
            if(workPOJO.getCurrentlyWorking().equals("1")){
                check_currently_work.setChecked(true);
            }else{
                check_currently_work.setChecked(false);
            }
            iv_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ll_work_layout.getVisibility()==View.VISIBLE){
                        ll_work_layout.setVisibility(View.GONE);
                    }else{
                        ll_work_layout.setVisibility(View.VISIBLE);
                    }
                }
            });

            ll_work.addView(view);
        }
    }

    public void selectProfilePic() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    String image_path_string = "";

    public void saveUserProfile() {
        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            if (image_path_string.length() > 0 && new File(image_path_string).exists()) {
                FileBody bin1 = new FileBody(new File(image_path_string));
                reqEntity.addPart("photo", bin1);
            } else {
                reqEntity.addPart("photo", new StringBody(""));
            }

            String gender = "";
            switch (rg_gender.getCheckedRadioButtonId()) {
                case R.id.rb_male:
                    gender = "1";
                    break;
                case R.id.rb_female:
                    gender = "2";
                    break;
                case R.id.rb_other:
                    gender = "3";
                    break;
            }

            reqEntity.addPart("user_id", new StringBody(Constants.userInfoPOJO.getUserProfileCitizen().getUserId()));
            reqEntity.addPart("user_profile_id", new StringBody(Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));
            reqEntity.addPart("fullname", new StringBody(et_first_name.getText().toString() + " " + et_last_name.getText().toString()));
            reqEntity.addPart("gender", new StringBody(gender));
            reqEntity.addPart("date_of_birth", new StringBody(et_dob.getText().toString()));
            reqEntity.addPart("email", new StringBody(et_email.getText().toString()));
            reqEntity.addPart("mobile", new StringBody(et_phone.getText().toString()));
            reqEntity.addPart("alt_mobile", new StringBody(et_alt_phone.getText().toString()));
            reqEntity.addPart("city", new StringBody(et_city.getText().toString()));
            reqEntity.addPart("state", new StringBody(et_state.getText().toString()));
            reqEntity.addPart("pincode", new StringBody(et_pincode.getText().toString()));
            reqEntity.addPart("country", new StringBody(et_country.getText().toString()));

            new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    Log.d(TagUtils.getTag(), apicall + ":-" + response);
                }
            }, "CALL_SAVE_PROFILE_API", true).execute(WebServicesUrls.UPDATE_PROFILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (null == data)
                    return;
                Uri selectedImageUri = data.getData();
                System.out.println(selectedImageUri.toString());
                // MEDIA GALLERY
                String selectedImagePath = getPath(
                        this, selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if (selectedImagePath != null && selectedImagePath != "") {
                    image_path_string = selectedImagePath;
                    Log.d(TagUtils.getTag(), "selected path:-" + selectedImagePath);
                    setProfilePic();
                } else {
                    Toast.makeText(this, "Selected File is Corrupted", Toast.LENGTH_LONG).show();
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
                    setProfilePic();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TagUtils.getTag(), "Place: " + place.getName());
                check_in_place = (String) place.getName();
                if (LOCATION_TYPE.equals(WORK_LOCATION)) {
                    et_work_location.setText(check_in_place);
                } else if (LOCATION_TYPE.equals(EDUCATION_LOCATION)) {
                    et_college_place.setText(check_in_place);
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TagUtils.getTag(), status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    String check_in_place = "";

    public void setProfilePic() {
        Glide.with(getApplicationContext())
                .load(image_path_string)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .dontAnimate()
                .into(cv_profile_pic);
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        if (CALENDAR_TYPE.equals(WORK_FROM)) {
            tv_work_from.setText(date);
        } else if (CALENDAR_TYPE.equals(WORK_TO)) {
            tv_work_to.setText(date);
        } else if (CALENDAR_TYPE.equals(EDUCATION_FROM)) {
            tv_college_from.setText(date);
        } else if (CALENDAR_TYPE.equals(EDUCATION_TO)) {
            tv_college_to.setText(date);
        } else {
            et_dob.setText(date);
        }
    }
}
