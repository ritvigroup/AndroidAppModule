package com.ritvi.kaajneeti.fragment.profile;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.FileUtils;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.pojo.profile.FullProfilePOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.pojo.userdetail.AddressPOJO;
import com.ritvi.kaajneeti.pojo.userdetail.EducationPOJO;
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
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 22-04-2018.
 */

@SuppressLint("ValidFragment")
public class UpdateUserProfileFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static final int PICK_IMAGE_REQUEST = 101;
    private static final int CAMERA_REQUEST = 102;

    @BindView(R.id.ll_personal_information)
    LinearLayout ll_personal_information;
    @BindView(R.id.ll_address)
    LinearLayout ll_address;
    @BindView(R.id.ll_work)
    LinearLayout ll_work;
    @BindView(R.id.ll_education)
    LinearLayout ll_education;
    @BindView(R.id.ll_add_address)
    LinearLayout ll_add_address;
    @BindView(R.id.ll_add_work)
    LinearLayout ll_add_work;
    @BindView(R.id.ll_add_education)
    LinearLayout ll_add_education;


    @BindView(R.id.iv_personal_view)
    ImageView iv_personal_view;
    @BindView(R.id.iv_address_view)
    ImageView iv_address_view;
    @BindView(R.id.iv_work_view)
    ImageView iv_work_view;
    @BindView(R.id.iv_education_view)
    ImageView iv_education_view;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.et_first_name)
    EditText et_first_name;
    @BindView(R.id.et_last_name)
    EditText et_last_name;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_dob)
    EditText et_dob;
    @BindView(R.id.iv_calendar)
    ImageView iv_calendar;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_alt_phone)
    EditText et_alt_phone;
    @BindView(R.id.et_bio)
    EditText et_bio;

    @BindView(R.id.rg_gender)
    RadioGroup rg_gender;
    @BindView(R.id.rg_martial_status)
    RadioGroup rg_martial_status;
    @BindView(R.id.rb_male)
    RadioButton rb_male;
    @BindView(R.id.rb_female)
    RadioButton rb_female;
    @BindView(R.id.rb_other)
    RadioButton rb_other;
    @BindView(R.id.rb_single)
    RadioButton rb_single;
    @BindView(R.id.rb_married)
    RadioButton rb_married;

    @BindView(R.id.btn_save_personal_info)
    Button btn_save_personal_info;

    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.iv_profile_image_edit)
    ImageView iv_profile_image_edit;


    String user_id;
    String profile_id;

    public UpdateUserProfileFragment(String user_id, String profile_id) {
        this.user_id = user_id;
        this.profile_id = profile_id;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_update_user_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        iv_personal_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll_personal_information.getVisibility() == View.VISIBLE) {
                    ll_personal_information.setVisibility(View.GONE);
                } else {
                    ll_personal_information.setVisibility(View.VISIBLE);
                }
            }
        });

        iv_address_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll_address.getVisibility() == View.VISIBLE) {
                    ll_address.setVisibility(View.GONE);
                } else {
                    ll_address.setVisibility(View.VISIBLE);
                }
            }
        });
        iv_work_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll_work.getVisibility() == View.VISIBLE) {
                    ll_work.setVisibility(View.GONE);
                } else {
                    ll_work.setVisibility(View.VISIBLE);
                }
            }
        });
        iv_education_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll_education.getVisibility() == View.VISIBLE) {
                    ll_education.setVisibility(View.GONE);
                } else {
                    ll_education.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_save_personal_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePersonalInformation();
            }
        });
        iv_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        UpdateUserProfileFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Select DOB");
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
                                startCamera();
                                break;
                            case R.id.popup_gallery:
                                selectProfilePic();
                                break;
                            case R.id.popup_remove:
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

        ll_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    UpdateAddressFragment updateAddressFragment = new UpdateAddressFragment(user_id, profile_id, null);
                    homeActivity.addFragmentinFrameHome(updateAddressFragment,"updateAddressFragment");
                }
            }
        });

        ll_add_education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();

                    UpdateEducationFragment updateEducation = new UpdateEducationFragment(user_id, profile_id, null);
                    homeActivity.addFragmentinFrameHome(updateEducation,"updateEducation");
                }
            }
        });

        ll_add_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    UpdateWorkFragment updateWorkFragment = new UpdateWorkFragment(user_id, profile_id, null);
                    homeActivity.addFragmentinFrameHome(updateWorkFragment,"updateWorkFragment");
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshUpdateProfile();
                getActivity().onBackPressed();
            }
        });


        getAllProfileData();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    refreshUpdateProfile();
                    getActivity().onBackPressed();
                    return true;
                }
                return false;
            }
        });
    }

    public void refreshUpdateProfile(){
        if(getActivity() instanceof HomeActivity){
            HomeActivity homeActivity= (HomeActivity) getActivity();
            homeActivity.refreshUserProfileFragment();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TagUtils.getTag(), "update profile");
    }

    private void savePersonalInformation() {
        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            if (image_path_string.length() > 0 && new File(image_path_string).exists()) {
                FileBody bin1 = new FileBody(new File(image_path_string));
                reqEntity.addPart("photo", bin1);
            } else {
                reqEntity.addPart("photo", new StringBody(""));
            }

            String gender = "0";
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

            String martial_status = "0";
            switch (rg_martial_status.getCheckedRadioButtonId()) {
                case R.id.rb_single:
                    martial_status = "1";
                    break;
                case R.id.rb_married:
                    martial_status = "2";
                    break;
            }

            reqEntity.addPart("user_id", new StringBody(user_id));
            reqEntity.addPart("user_profile_id", new StringBody(profile_id));
            reqEntity.addPart("fullname", new StringBody(et_first_name.getText().toString() + " " + et_last_name.getText().toString()));
            reqEntity.addPart("gender", new StringBody(gender));
            reqEntity.addPart("date_of_birth", new StringBody(et_dob.getText().toString()));
            reqEntity.addPart("email", new StringBody(et_email.getText().toString()));
            reqEntity.addPart("mobile", new StringBody(et_phone.getText().toString()));
            reqEntity.addPart("alt_mobile", new StringBody(et_alt_phone.getText().toString()));
            reqEntity.addPart("martial_status", new StringBody(martial_status));
            reqEntity.addPart("bio", new StringBody(et_bio.getText().toString()));
//            reqEntity.addPart("city", new StringBody(et_city.getText().toString()));
//            reqEntity.addPart("state", new StringBody(et_state.getText().toString()));
//            reqEntity.addPart("pincode", new StringBody(et_pincode.getText().toString()));
//            reqEntity.addPart("country", new StringBody(et_country.getText().toString()));

            new WebUploadService(reqEntity, getActivity(), new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    Log.d(TagUtils.getTag(), apicall + ":-" + response);
                }
            }, "CALL_SAVE_PROFILE_API", true).execute(WebServicesUrls.UPDATE_PROFILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeProfilePic() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("success")) {
                        image_path_string = "";
                        cv_profile_pic.setBackgroundResource(R.drawable.ic_default_profile_pic);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "REMOVE_PROFILE_API", true).execute(WebServicesUrls.REMOVE_PROFILE_PIC);
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


    public void getAllProfileData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id", profile_id));
        new WebServiceBaseResponse<FullProfilePOJO>(nameValuePairs, getActivity(), new ResponseCallBack<FullProfilePOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<FullProfilePOJO> responsePOJO) {
                if (responsePOJO.isSuccess()) {
                    setUpPersonalData(responsePOJO.getResult().getProfilePOJO());
                    ll_address.removeAllViews();
                    if (responsePOJO.getResult().getAddressPOJOS().size() > 0) {
                        for (AddressPOJO addressPOJO : responsePOJO.getResult().getAddressPOJOS()) {
                            inflateAddress(addressPOJO);
                        }
                    }
                    ll_work.removeAllViews();
                    if (responsePOJO.getResult().getWorkPOJOList().size() > 0) {
                        for (WorkPOJO workPOJO : responsePOJO.getResult().getWorkPOJOList()) {
                            inflateWork(workPOJO);
                        }
                    }
                    ll_education.removeAllViews();
                    if (responsePOJO.getResult().getEducationPOJOS().size() > 0) {
                        for (EducationPOJO educationPOJO : responsePOJO.getResult().getEducationPOJOS()) {
                            inflateEducation(educationPOJO);
                        }
                    }

                    showFirstView(FIRST_ADDRESS_VIEW);
                    showFirstView(FIRST_EDUCATION_VIEW);
                    showFirstView(FIRST_WORK_VIEW);

                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), responsePOJO.getMessage());
                }
            }
        }, FullProfilePOJO.class, "CALL_PROFILE_API", true).execute(WebServicesUrls.FULL_PROFILE_URL);
    }

    private final String FIRST_WORK_VIEW = "first_work_view";
    private final String FIRST_EDUCATION_VIEW = "first_education_view";
    private final String FIRST_ADDRESS_VIEW = "first_address_view";

    public void showFirstView(String view_type) {
        if (view_type.equals(FIRST_ADDRESS_VIEW)) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.inflate_add_first_view, null);

            LinearLayout ll_add = view.findViewById(R.id.ll_add);
            TextView tv_name= view.findViewById(R.id.tv_name);

            tv_name.setText("Add Address");

            ll_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getActivity() instanceof HomeActivity) {
                        HomeActivity homeActivity = (HomeActivity) getActivity();
                        UpdateAddressFragment updateAddressFragment = new UpdateAddressFragment(user_id, profile_id, null);
                        homeActivity.addFragmentinFrameHome(updateAddressFragment,"updateAddressFragment");
                    }
                }
            });

            ll_address.addView(view);
        } else if (view_type.equals(FIRST_EDUCATION_VIEW)) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.inflate_add_first_view, null);

            LinearLayout ll_add = view.findViewById(R.id.ll_add);
            TextView tv_name= view.findViewById(R.id.tv_name);

            tv_name.setText("Add Education");

            ll_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getActivity() instanceof HomeActivity) {
                        HomeActivity homeActivity = (HomeActivity) getActivity();
                        UpdateEducationFragment updateEducation = new UpdateEducationFragment(user_id, profile_id, null);
                        homeActivity.addFragmentinFrameHome(updateEducation,"updateEducation");
                    }
                }
            });

            ll_education.addView(view);
        } else if (view_type.equals(FIRST_WORK_VIEW)) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.inflate_add_first_view, null);

            LinearLayout ll_add = view.findViewById(R.id.ll_add);
            TextView tv_name= view.findViewById(R.id.tv_name);

            tv_name.setText("Add Work");

            ll_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getActivity() instanceof HomeActivity) {
                        HomeActivity homeActivity = (HomeActivity) getActivity();

                        UpdateWorkFragment updateWorkFragment = new UpdateWorkFragment(user_id, profile_id, null);
                        homeActivity.addFragmentinFrameHome(updateWorkFragment,"updateWorkFragment");
                    }
                }
            });

            ll_work.addView(view);
        }
    }

    public void inflateAddress(final AddressPOJO addressPOJO) {
        try {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.inflate_profile_item, null);
            LinearLayout ll_item = view.findViewById(R.id.ll_item);
            TextView tv_name = view.findViewById(R.id.tv_name);
            ImageView iv_delete = view.findViewById(R.id.iv_delete);
            ImageView iv_edit = view.findViewById(R.id.iv_edit);

            tv_name.setText(addressPOJO.getAddress());

            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setCancelable(true);
                    builder.setTitle("Delete")
                            .setMessage("Are you sure you want to delete this entry?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete

                                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                                    nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
                                    nameValuePairs.add(new BasicNameValuePair("user_profile_id", profile_id));
                                    nameValuePairs.add(new BasicNameValuePair("address_id", addressPOJO.getUserProfileAddressId()));
                                    new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
                                        @Override
                                        public void onGetMsg(String apicall, String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                if (jsonObject.optString("status").equals("success")) {
//                                                    ll_address.removeView(view);
                                                    ll_address.post(new Runnable() {
                                                        public void run() {
                                                            ll_address.removeView(view);
                                                        }
                                                    });
                                                } else {
                                                    ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, "CALL_DELETE", true).execute(WebServicesUrls.DELETE_PROFILE_ADDRESS);

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });

            iv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getActivity() instanceof HomeActivity) {
                        HomeActivity homeActivity = (HomeActivity) getActivity();
                        UpdateAddressFragment updateAddressFragment = new UpdateAddressFragment(user_id, profile_id, addressPOJO);
                        homeActivity.addFragmentinFrameHome(updateAddressFragment,"updateAddressFragment");
                    }
                }
            });

            ll_address.addView(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inflateWork(final WorkPOJO workPOJO) {
        try {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.inflate_profile_item, null);
            LinearLayout ll_item = view.findViewById(R.id.ll_item);
            TextView tv_name = view.findViewById(R.id.tv_name);
            ImageView iv_delete = view.findViewById(R.id.iv_delete);
            ImageView iv_edit = view.findViewById(R.id.iv_edit);

            tv_name.setText(workPOJO.getWorkPosition());

            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setCancelable(true);
                    builder.setTitle("Delete")
                            .setMessage("Are you sure you want to delete this entry?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete

                                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                                    nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
                                    nameValuePairs.add(new BasicNameValuePair("user_profile_id", profile_id));
                                    nameValuePairs.add(new BasicNameValuePair("work_id", workPOJO.getUserProfileWorkId()));
                                    new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
                                        @Override
                                        public void onGetMsg(String apicall, String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                if (jsonObject.optString("status").equals("success")) {
//                                                    ll_work.removeView(view);
                                                    ll_work.post(new Runnable() {
                                                        public void run() {
                                                            ll_work.removeView(view);
                                                        }
                                                    });
                                                } else {
                                                    ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, "CALL_DELETE", true).execute(WebServicesUrls.DELETE_PROFILE_WORK);

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });

            iv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getActivity() instanceof HomeActivity) {
                        HomeActivity homeActivity = (HomeActivity) getActivity();
                        UpdateWorkFragment updateWorkFragment = new UpdateWorkFragment(user_id, profile_id, workPOJO);
                        homeActivity.addFragmentinFrameHome(updateWorkFragment,"updateWorkFragment");
                    }
                }
            });

            ll_work.addView(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inflateEducation(final EducationPOJO educationPOJO) {
        try {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.inflate_profile_item, null);
            LinearLayout ll_item = view.findViewById(R.id.ll_item);
            TextView tv_name = view.findViewById(R.id.tv_name);
            ImageView iv_delete = view.findViewById(R.id.iv_delete);
            ImageView iv_edit = view.findViewById(R.id.iv_edit);

            tv_name.setText(educationPOJO.getQualification());

            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setCancelable(true);
                    builder.setTitle("Delete")
                            .setMessage("Are you sure you want to delete this entry?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete

                                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                                    nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
                                    nameValuePairs.add(new BasicNameValuePair("user_profile_id", profile_id));
                                    nameValuePairs.add(new BasicNameValuePair("qualification_id", educationPOJO.getUserProfileEducationId()));
                                    new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
                                        @Override
                                        public void onGetMsg(String apicall, String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                if (jsonObject.optString("status").equals("success")) {
                                                    ll_education.removeView(view);
                                                    ll_education.post(new Runnable() {
                                                        public void run() {
                                                            ll_education.removeView(view);
                                                        }
                                                    });
                                                } else {
                                                    ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, "CALL_DELETE", true).execute(WebServicesUrls.DELETE_PROFILE_EDUCATION);

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });

            iv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getActivity() instanceof HomeActivity) {
                        HomeActivity homeActivity = (HomeActivity) getActivity();
                        UpdateEducationFragment updateEducation = new UpdateEducationFragment(user_id, profile_id, educationPOJO);
                        homeActivity.addFragmentinFrameHome(updateEducation,"updateEducation");
                    }
                }
            });

            ll_education.addView(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUpPersonalData(UserProfilePOJO profilePOJO) {
        et_first_name.setText(profilePOJO.getFirstName());
        et_last_name.setText(profilePOJO.getLastName());
        et_email.setText(profilePOJO.getEmail());
        et_bio.setText(profilePOJO.getUserBio());
        et_dob.setText(profilePOJO.getDateOfBirth());
        if (profilePOJO.getMobile().length() > 0) {
            String phone = profilePOJO.getMobile().replace("+91", "");
            et_phone.setText(phone);
        }
        et_alt_phone.setText(profilePOJO.getAltMobile());


        switch (profilePOJO.getGender()) {
            case "1":
                rb_male.setChecked(true);
                break;
            case "2":
                rb_female.setChecked(true);
                break;
            case "3":
                rb_other.setChecked(true);
                break;
        }

        switch (profilePOJO.getMaritalStatus()) {
            case "1":
                rb_single.setChecked(true);
                break;
            case "2":
                rb_married.setChecked(true);
                break;
        }

        Glide.with(getActivity().getApplicationContext())
                .load(profilePOJO.getProfilePhotoPath())
                .error(R.drawable.ic_default_profile_pic)
                .placeholder(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(cv_profile_pic);

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        et_dob.setText(date);
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
                    setProfilePic();
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
                    setProfilePic();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return;
        }
    }

    public void setProfilePic() {
        if (image_path_string.length() > 0 && new File(image_path_string).exists()) {
            Glide.with(getActivity().getApplicationContext())
                    .load(image_path_string)
                    .error(R.drawable.ic_default_profile_pic)
                    .placeholder(R.drawable.ic_default_profile_pic)
                    .dontAnimate()
                    .into(cv_profile_pic);
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
