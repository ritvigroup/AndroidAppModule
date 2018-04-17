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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.erikagtierrez.multiple_media_picker.Gallery;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.activity.ApplicationSubmittedActivity;
import com.ritvi.kaajneeti.adapter.CustomAutoCompleteAdapter;
import com.ritvi.kaajneeti.adapter.MediaListAdapter;
import com.ritvi.kaajneeti.pojo.DepartmentPOJO;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.ResponseListCallback;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponseList;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 15-04-2018.
 */

@SuppressLint("ValidFragment")
public class CreateComplaintFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    static final int OPEN_MEDIA_PICKER = 1;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 101;

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
    @BindView(R.id.et_location)
    EditText et_location;
    @BindView(R.id.iv_location)
    ImageView iv_location;
    @BindView(R.id.tv_attach)
    TextView tv_attach;
    @BindView(R.id.spinner_department)
    Spinner spinner_department;
    @BindView(R.id.tv_post)
    TextView tv_post;
    @BindView(R.id.et_description)
    EditText et_description;
    @BindView(R.id.et_date)
    EditText et_date;
    @BindView(R.id.iv_calendar)
    ImageView iv_calendar;
    @BindView(R.id.auto_fav_list)
    AutoCompleteTextView auto_fav_list;
    @BindView(R.id.iv_favorite_leader_add)
    ImageView iv_favorite_leader_add;

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

        attachMediaAdapter();

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

        tv_profile_description.setText(Html.fromHtml(profile_description));

        et_subject.setText(complaint_name);

        iv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPlace();
            }
        });

        tv_attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectMedia();
            }
        });
        getAllDepartment();

        tv_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveComplaint();
            }
        });

        iv_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CreateComplaintFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });


        auto_fav_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (leaderPOJOS.size() > 0) {
                    leader_id = leaderPOJOS.get(i).getUserProfileLeader().getUserProfileId();
                }
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

    List<DepartmentPOJO> departmentPOJOS = new ArrayList<>();

    public void getAllDepartment() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", UtilityFunction.getProfileID(Constants.userInfoPOJO)));
        new WebServiceBaseResponseList<DepartmentPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<DepartmentPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<DepartmentPOJO> responseListPOJO) {
                try {
                    if (responseListPOJO.getResultList().size() > 0) {
                        departmentPOJOS.addAll(responseListPOJO.getResultList());

                        List<String> departmentStringList = new ArrayList<>();
                        for (DepartmentPOJO departmentPOJO : departmentPOJOS) {
                            departmentStringList.add(departmentPOJO.getDepartmentName());
                        }


                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                getActivity(), R.layout.dropsimpledown, departmentStringList);
                        spinner_department.setAdapter(spinnerArrayAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, DepartmentPOJO.class, "CALL_DEPARTMENT_GET_API", true).execute(WebServicesUrls.DEPARTMENT_URL);
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


    public void selectMedia() {
        Intent intent = new Intent(getActivity(), Gallery.class);
        intent.putExtra("title", "Select media");
        // Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
        intent.putExtra("mode", 1);
        intent.putExtra("maxSelection", 5 - mediaFiles.size()); // Optional
        startActivityForResult(intent, OPEN_MEDIA_PICKER);
    }

    LatLng latLong;
    String address = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                Log.i(TagUtils.getTag(), "Place: " + place.getName());
                place.getAddress();
                latLong = place.getLatLng();
                address = place.getAddress().toString();
                et_location.setText((String) place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i(TagUtils.getTag(), status.getStatusMessage());

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        } else if (requestCode == OPEN_MEDIA_PICKER) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK && data != null) {
                ArrayList<String> selectionResult = data.getStringArrayListExtra("result");
                mediaFiles.addAll(selectionResult);
                mediaListAdapter.notifyDataSetChanged();
            }
        }
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
            if (leader_id.length() > 0) {
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("", ""));
                reqEntity.addPart("user_profile_id", new StringBody(Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));
                reqEntity.addPart("complaint_subject", new StringBody(et_subject.getText().toString()));
                reqEntity.addPart("complaint_description", new StringBody(et_description.getText().toString()));
                UserProfilePOJO userProfilePOJO = UtilityFunction.getUserProfilePOJO(Constants.userInfoPOJO);
                reqEntity.addPart("applicant_name", new StringBody(userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName()));
                reqEntity.addPart("applicant_father_name", new StringBody(""));
                reqEntity.addPart("applicant_mobile", new StringBody(userProfilePOJO.getMobile()));
                reqEntity.addPart("assign_to_profile_id", new StringBody(leader_id));
                reqEntity.addPart("complaint_type_id", new StringBody("1"));
                reqEntity.addPart("applicant_email", new StringBody(userProfilePOJO.getEmail()));
                reqEntity.addPart("schedule_date", new StringBody(UtilityFunction.getConvertedDate(et_date.getText().toString())));
                if (spinner_privpub.getSelectedItemPosition() == 0) {
                    reqEntity.addPart("privacy", new StringBody("1"));
                } else {
                    reqEntity.addPart("privacy", new StringBody("0"));
                }
                reqEntity.addPart("address", new StringBody(address));
                reqEntity.addPart("place", new StringBody(et_location.getText().toString()));
                if (latLong != null) {
                    reqEntity.addPart("latitude", new StringBody(String.valueOf(latLong.latitude)));
                    reqEntity.addPart("longitude", new StringBody(String.valueOf(latLong.longitude)));
                } else {
                    reqEntity.addPart("latitude", new StringBody(""));
                    reqEntity.addPart("longitude", new StringBody(""));
                }

                if (departmentPOJOS.size() > 0) {
                    reqEntity.addPart("department", new StringBody(departmentPOJOS.get(spinner_department.getSelectedItemPosition()).getDepartmentId()));
                } else {
                    reqEntity.addPart("department", new StringBody(""));
                }
                reqEntity.addPart("complaint_member", new StringBody(""));

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
                                startActivity(new Intent(getActivity(), ApplicationSubmittedActivity.class).putExtra("comp_type", "complaint"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, "CREATE_EVENT", true).execute(WebServicesUrls.POST_COMPLAINT);
            } else {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Select Leader First");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month = "";
        String day = "";
        if ((monthOfYear + 1) < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf(monthOfYear + 1);
        }

        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = String.valueOf(dayOfMonth);
        }

        String date = day + "-" + month + "-" + year;
        et_date.setText(date);
    }
}
