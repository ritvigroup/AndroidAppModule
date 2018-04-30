package com.ritvi.kaajneeti.activity;

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
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.FileUtils;
import com.ritvi.kaajneeti.Util.Pref;
import com.ritvi.kaajneeti.Util.StringUtils;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;
import com.ritvi.kaajneeti.webservice.WebUploadService;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileInfoActivity extends LocalizationActivity implements DatePickerDialog.OnDateSetListener, WebServicesCallBack {

    private final int STATE_SELECT_INTENT = 102;
    private int PICK_IMAGE_REQUEST = 101;
    private static final int CAMERA_REQUEST = 103;
    private static final String CALL_PROFILE_SAVE_API = "call_profile_save_api";
    private static final String CALL_PROFILE_GET_API = "call_profile_get_api";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_calendar)
    ImageView iv_calendar;
    @BindView(R.id.et_birth_date)
    EditText et_birth_date;
    @BindView(R.id.rg_gender)
    RadioGroup rg_gender;
    @BindView(R.id.rb_male)
    RadioButton rb_male;
    @BindView(R.id.rb_female)
    RadioButton rb_female;
    @BindView(R.id.rb_other)
    RadioButton rb_other;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_alternate_mobile)
    EditText et_alternate_mobile;
    @BindView(R.id.et_mobile_number)
    EditText et_mobile_number;

    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;

    @BindView(R.id.tv_state_select)
    TextView tv_state_select;
    @BindView(R.id.tv_skip)
    TextView tv_skip;
    @BindView(R.id.btn_accept)
    Button btn_accept;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d(TagUtils.getTag(),"user profile:-"+Constants.userProfilePOJO.toString());
        autoFillForm();

        iv_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ProfileInfoActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Select DOB");
            }
        });

        tv_state_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileInfoActivity.this, StateSelectActivity.class);
                startActivityForResult(i, STATE_SELECT_INTENT);
            }
        });

        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_PROFILE_SKIPPED, true);
                startActivity(new Intent(ProfileInfoActivity.this, HomeActivity.class));
                finish();
            }
        });


        cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(ProfileInfoActivity.this, view);

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

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callProfileSaveAPI();
            }
        });

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


    public void selectProfilePic() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void autoFillForm() {
        try {
            et_name.setText(Constants.userProfilePOJO.getFirstName());
            switch (Constants.userProfilePOJO.getGender()) {
                case "1":
                    rb_male.setChecked(true);
                    break;
                case "2":
                    rb_female.setChecked(true);
                    break;
                case "3":
                    rb_other.setChecked(true);
                    break;
                default:
                    break;
            }

            Glide.with(getApplicationContext())
                    .load(Constants.userProfilePOJO.getProfilePhotoPath())
                    .placeholder(R.drawable.ic_default_profile_pic)
                    .error(R.drawable.ic_default_profile_pic)
                    .dontAnimate()
                    .into(cv_profile_pic);

//            if(userProfilePOJO.getState()!=null||userProfilePOJO.getState().length()>0) {
//                tv_state_select.setText(userProfilePOJO.getState());
//            }else{
//                tv_state_select.setText("select");
//            }

            et_birth_date.setText(Constants.userProfilePOJO.getDateOfBirth());
            et_email.setText(Constants.userProfilePOJO.getEmail());
            et_alternate_mobile.setText(Constants.userProfilePOJO.getAltMobile());
            et_mobile_number.setText(Constants.userProfilePOJO.getMobile());
            if(Constants.userProfilePOJO.getState().equals("")){
                tv_state_select.setText("select");
            }else{
                tv_state_select.setText(Constants.userProfilePOJO.getState());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callProfileSaveAPI() {

        String gender = "";
        if (rg_gender.getCheckedRadioButtonId() != -1) {
            switch (((RadioButton) findViewById(rg_gender.getCheckedRadioButtonId())).getText().toString().toLowerCase()) {
                case "male":
                    gender = String.valueOf(Constants.GENDER_MALE);
                    break;
                case "female":
                    gender = String.valueOf(Constants.GENDER_FEMALE);
                    break;
                case "other":
                    gender = String.valueOf(Constants.GENDER_OTHER);
                    break;
                default:
                    gender = String.valueOf(Constants.GENDER_DEFAULT);
            }
        }

        String date = UtilityFunction.getConvertedDate(et_birth_date.getText().toString());


//        if (et_birth_date.getText().toString().length() > 0) {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            try {
//                Date d1 = simpleDateFormat.parse(et_birth_date.getText().toString());
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                date = sdf.format(d1);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }

        Log.d(TagUtils.getTag(),"converted b'day:-"+date);

        Log.d(TagUtils.getTag(), "profile image:-" + image_path_string);

        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            if (image_path_string.length() > 0 && new File(image_path_string).exists()) {
                FileBody bin1 = new FileBody(new File(image_path_string));
                reqEntity.addPart("photo", bin1);
            } else {
                reqEntity.addPart("photo", new StringBody(""));
            }
            reqEntity.addPart("user_id", new StringBody(Constants.userProfilePOJO.getUserId()));
            reqEntity.addPart("user_profile_id", new StringBody(Constants.userProfilePOJO.getUserProfileId()));
            reqEntity.addPart("fullname", new StringBody(et_name.getText().toString(), Charset.forName(HTTP.UTF_8)));
            reqEntity.addPart("gender", new StringBody(gender, Charset.forName(HTTP.UTF_8)));
            reqEntity.addPart("date_of_birth", new StringBody(date, Charset.forName(HTTP.UTF_8)));
            reqEntity.addPart("state", new StringBody(tv_state_select.getText().toString(), Charset.forName(HTTP.UTF_8)));
            reqEntity.addPart("email", new StringBody(et_email.getText().toString(), Charset.forName(HTTP.UTF_8)));
            reqEntity.addPart("mobile", new StringBody(et_mobile_number.getText().toString(), Charset.forName(HTTP.UTF_8)));
            reqEntity.addPart("alt_mobile", new StringBody(et_alternate_mobile.getText().toString(), Charset.forName(HTTP.UTF_8)));
            new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    Log.d(TagUtils.getTag(),apicall+":-"+response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optString(Constants.API_STATUS).equals(Constants.API_SUCCESS)) {
                            String user_profile = jsonObject.optJSONObject("result").toString();
                            Gson gson = new Gson();
                            UserProfilePOJO userProfilePOJO = gson.fromJson(user_profile, UserProfilePOJO.class);
                            Pref.SetStringPref(getApplicationContext(),StringUtils.USER_PROFILE,user_profile);
                            Constants.userProfilePOJO =userProfilePOJO;
                            Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, true);
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finishAffinity();
                        }
                        ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TagUtils.getTag(), "error:-" + e.toString());
                    }


                }
            }, CALL_PROFILE_SAVE_API, true).execute(WebServicesUrls.UPDATE_PROFILE_AFTER_LOGIN);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        et_birth_date.setText(date);
    }

    String image_path_string = "";

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
                        ProfileInfoActivity.this, selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if (selectedImagePath != null && selectedImagePath != "") {
                    image_path_string = selectedImagePath;
                    Log.d(TagUtils.getTag(), "selected path:-" + selectedImagePath);
                    setProfilePic();
                } else {
                    Toast.makeText(ProfileInfoActivity.this, "Selected File is Corrupted", Toast.LENGTH_LONG).show();
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
        if (requestCode == STATE_SELECT_INTENT) {
            if (resultCode == Activity.RESULT_OK) {
                String state = data.getStringExtra("state");
                tv_state_select.setText(state);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
            return;
        }
    }

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
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onGetMsg(String apicall, String response) {
        Log.d(TagUtils.getTag(), apicall + ":-" + response);
        switch (apicall) {
            case CALL_PROFILE_SAVE_API:
                parseProfileResponse(response);
                break;
            case CALL_PROFILE_GET_API:
                parseProfileGetResponse(response);
                break;
        }
    }

    public void parseProfileGetResponse(String response) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseProfileResponse(String response) {
        Log.d(TagUtils.getTag(),"profile update response:-"+response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("status").equals("success")) {
                String user_profile = jsonObject.optJSONObject("result").toString();
                Gson gson = new Gson();
                UserProfilePOJO userProfilePOJO = gson.fromJson(user_profile, UserProfilePOJO.class);
//                Pref.SaveUserProfile(LoginActivity.this, user_profile);
                Pref.SetStringPref(getApplicationContext(),StringUtils.USER_PROFILE,user_profile);
                Constants.userProfilePOJO=userProfilePOJO;
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, true);
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finishAffinity();
            }
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
