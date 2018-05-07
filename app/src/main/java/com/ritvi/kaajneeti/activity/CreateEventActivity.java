package com.ritvi.kaajneeti.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.FileUtils;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.adapter.EventAttachAdapter;
import com.ritvi.kaajneeti.pojo.event.EventAttachment;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;
import com.ritvi.kaajneeti.webservice.WebUploadService;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

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
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final int CAMERA_REQUEST = 101;
    private static int PICK_IMAGE_REQUEST = 102;
    private static final int VIDEO_CAPTURE = 103;
    private static int PICK_VIDEO_REQUEST = 104;
    private static final int TAG_PEOPLE = 105;

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.iv_camera)
    ImageView iv_camera;
    @BindView(R.id.iv_event_image)
    ImageView iv_event_image;

    @BindView(R.id.et_start_date)
    EditText et_start_date;
    @BindView(R.id.iv_start_calendar)
    ImageView iv_start_calendar;
    @BindView(R.id.et_start_time)
    EditText et_start_time;
    @BindView(R.id.iv_start_clock)
    ImageView iv_start_clock;
    @BindView(R.id.et_end_date)
    EditText et_end_date;
    @BindView(R.id.iv_end_calendar)
    ImageView iv_end_calendar;
    @BindView(R.id.et_end_time)
    EditText et_end_time;
    @BindView(R.id.iv_end_clock)
    ImageView iv_end_clock;
    @BindView(R.id.btn_tag)
    Button btn_tag;
    @BindView(R.id.rv_files)
    RecyclerView rv_files;
    @BindView(R.id.btn_create)
    Button btn_create;
    @BindView(R.id.et_event_name)
    EditText et_event_name;
    @BindView(R.id.et_location)
    EditText et_location;
    @BindView(R.id.et_description)
    EditText et_description;


    boolean start_date = false;
    boolean start_time = false;

    private final String COVER_IMAGE = "cover_image";
    private final String COVER_IMAGE_GALLERY = "cover_image_gallery";
    private final String ATTACH_CAMERA = "attach_image";
    private final String ATTACH_CAMERA_GALLERY = "attach_image_gallery";
    private final String ATTACH_VIDEO = "attach_video";
    private final String ATTACH_VIDEO_GALLERY = "attach_video_gallery";

    String attach_type = "";

    String cover_image_path = "";
    List<UserInfoPOJO> taggedUserProfilePOJOS=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(CreateEventActivity.this, view);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuitem) {
                        switch (menuitem.getItemId()) {
                            case R.id.popup_camera:
                                attach_type = COVER_IMAGE;
                                startCamera();
                                break;
                            case R.id.popup_gallery:
                                attach_type = COVER_IMAGE_GALLERY;
                                selectImageFromGallery();
                                break;
                        }
                        return false;
                    }
                });
                menu.inflate(R.menu.menu_profile_pic_option);
                menu.show();
            }
        });

        iv_start_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_date = true;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CreateEventActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        iv_end_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_date = false;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CreateEventActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        iv_start_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_time = true;
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                        CreateEventActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show(getFragmentManager(), "TimePicker");
            }
        });
        iv_end_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_time = false;
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                        CreateEventActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show(getFragmentManager(), "TimePicker");
            }
        });
//
//        btn_tag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivityForResult(new Intent(CreateEventActivity.this,TagPeopleActivity.class).putExtra("taggedpeople", (Serializable) taggedUserProfilePOJOS),TAG_PEOPLE);
//            }
//        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEvent();
            }
        });

        attachAdapter();
    }

    public void saveEvent() {
        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("", ""));
            reqEntity.addPart("user_profile_id", new StringBody(Constants.userProfilePOJO.getUserProfileId()));
            reqEntity.addPart("event_name", new StringBody(et_event_name.getText().toString()));
            reqEntity.addPart("event_description", new StringBody(et_description.getText().toString()));
            reqEntity.addPart("event_location", new StringBody(et_location.getText().toString()));

            String start_date = getFormattedDate(et_start_date.getText().toString() + " " + et_start_time.getText().toString());
            String end_date = getFormattedDate(et_end_date.getText().toString() + " " + et_end_time.getText().toString());

            Log.d(TagUtils.getTag(), "start_date:-" + start_date);
            Log.d(TagUtils.getTag(), "end_date:-" + end_date);

            reqEntity.addPart("start_date", new StringBody(start_date));
            reqEntity.addPart("end_date", new StringBody(end_date));
            reqEntity.addPart("EveryYear", new StringBody(""));
            reqEntity.addPart("EveryMonth", new StringBody(""));
            reqEntity.addPart("event_attendee", new StringBody(""));

            boolean is_cover_set = false;
            int count = 0;
            if (new File(cover_image_path).exists()) {
                is_cover_set = true;
                FileBody coverFileBody = new FileBody(new File(cover_image_path));
                reqEntity.addPart("file[0]", coverFileBody);
                reqEntity.addPart("thumb[0]", new StringBody(""));
                count++;
            }

            for (EventAttachment eventAttachment : eventAttachments) {
                Log.d(TagUtils.getTag(), "event attachment:-" + eventAttachment.toString());

                if (eventAttachment.getType().equals(Constants.EVENT_IMAGE_ATTACH)) {
                    reqEntity.addPart("file[" + (count) + "]", new FileBody(new File(eventAttachment.getFile_path())));
                    reqEntity.addPart("thumb[" + (count) + "]", new StringBody(""));
                } else if (eventAttachment.getType().equals(Constants.EVENT_VIDEO_ATTACH)) {
                    reqEntity.addPart("file[" + (count) + "]", new FileBody(new File(eventAttachment.getFile_path())));
                    reqEntity.addPart("thumb[" + (count) + "]", new FileBody(new File(eventAttachment.getThumb_path())));
                }
                count++;
            }


            for(int i=0;i<taggedUserProfilePOJOS.size();i++){
                String profile_id="";
                if(taggedUserProfilePOJOS.get(i).getUserProfileLeader()!=null){
                    profile_id=taggedUserProfilePOJOS.get(i).getUserProfileLeader().getUserProfileId();
                }else{
                    profile_id=taggedUserProfilePOJOS.get(i).getUserProfileCitizen().getUserProfileId();
                }
                reqEntity.addPart("event_attendee["+i+"]",new StringBody(profile_id));
            }

            new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    Log.d(TagUtils.getTag(), apicall + " :- " + response);
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                        if(jsonObject.optString("status").equals("success")){
                            finish();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, "CREATE_EVENT", true).execute(WebServicesUrls.SAVE_EVENT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getFormattedDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
            Date d = sdf.parse(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            String formated_date = simpleDateFormat.format(d);
            return formated_date + ":00";
        } catch (Exception e) {
            return "";
        }
    }


    List<EventAttachment> eventAttachments = new ArrayList<>();
    EventAttachAdapter eventAttachAdapter;

    public void attachAdapter() {

        EventAttachment eventAttachment = new EventAttachment("", "", "", "", true);
        eventAttachments.add(eventAttachment);

        eventAttachAdapter = new EventAttachAdapter(this, null, eventAttachments);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_files.setHasFixedSize(true);
        rv_files.setAdapter(eventAttachAdapter);
        rv_files.setLayoutManager(linearLayoutManager);
        rv_files.setItemAnimator(new DefaultItemAnimator());
    }

    public void showAttachDialog() {
        final Dialog dialog1 = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_show_attachments);
        dialog1.setTitle("Select");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout ll_camera = (LinearLayout) dialog1.findViewById(R.id.ll_camera);
        LinearLayout ll_gallery = (LinearLayout) dialog1.findViewById(R.id.ll_gallery);
        LinearLayout ll_video_camera = (LinearLayout) dialog1.findViewById(R.id.ll_video_camera);
        LinearLayout ll_video_gallery = (LinearLayout) dialog1.findViewById(R.id.ll_video_gallery);
        Button btn_cancel = (Button) dialog1.findViewById(R.id.btn_cancel);

        ll_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attach_type = ATTACH_CAMERA;
                startCamera();
                dialog1.dismiss();
            }
        });

        ll_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attach_type = ATTACH_CAMERA_GALLERY;
                selectImageFromGallery();
                dialog1.dismiss();
            }
        });

        ll_video_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attach_type = ATTACH_VIDEO;
                openvideoCamera();
                dialog1.dismiss();
            }
        });

        ll_video_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attach_type = ATTACH_CAMERA_GALLERY;
                openVideoGallery();
                dialog1.dismiss();
            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
    }

    String video_file = "";

    public void openvideoCamera() {
        File mediaFile = new File(FileUtils.getVideoFolder() + File.separator + System.currentTimeMillis() + ".mp4");
        video_file = mediaFile.toString();
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        Uri videoUri = Uri.fromFile(mediaFile);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName()+".fileProvider", mediaFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        }
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        startActivityForResult(intent, VIDEO_CAPTURE);
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


    public void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void openVideoGallery() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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
                        CreateEventActivity.this, selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if (selectedImagePath != null && selectedImagePath != "") {
                    image_path_string = selectedImagePath;
                    Log.d(TagUtils.getTag(), "selected path:-" + selectedImagePath);
                    setAttach(image_path_string);
                    if (attach_type.equals(COVER_IMAGE) || attach_type.equals(COVER_IMAGE_GALLERY)) {
                        cover_image_path = image_path_string;
                    }
                } else {
                    Toast.makeText(CreateEventActivity.this, "Selected File is Corrupted", Toast.LENGTH_LONG).show();
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
                    setAttach(image_path_string);
                    if (attach_type.equals(COVER_IMAGE) || attach_type.equals(COVER_IMAGE_GALLERY)) {
                        cover_image_path = image_path_string;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        if (requestCode == VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
//                Toast.makeText(this, "Video saved to:\n" +
//                        data.getData(), Toast.LENGTH_LONG).show();
                Log.d(TagUtils.getTag(), "video file path:-" + video_file);

                setAttach(video_file);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to record video",
                        Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == PICK_VIDEO_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (null == data)
                    return;
                Uri selectedImageUri = data.getData();
                System.out.println(selectedImageUri.toString());
                // MEDIA GALLERY
                String selectedImagePath = getPath(this, selectedImageUri);
                Log.d(TagUtils.getTag(), "" + selectedImagePath);
                if (selectedImagePath != null && selectedImagePath != "") {
                    image_path_string = selectedImagePath;
                    Log.d(TagUtils.getTag(), "selected path:-" + selectedImagePath);
                    setAttach(selectedImagePath);
                } else {
                    Toast.makeText(this, "File Selected is corrupted", Toast.LENGTH_LONG).show();
                }
                System.out.println("Image Path =" + selectedImagePath);
            }
        }
        if (requestCode == TAG_PEOPLE) {
            if(resultCode == Activity.RESULT_OK){
                taggedUserProfilePOJOS = (List<UserInfoPOJO>) data.getSerializableExtra("taggedpeople");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public void setAttach(String path) {

        if (attach_type.equals(COVER_IMAGE) || attach_type.equals(COVER_IMAGE_GALLERY)) {
            Glide.with(getApplicationContext())
                    .load(path)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .dontAnimate()
                    .into(iv_event_image);
        } else if (attach_type.equals(ATTACH_CAMERA) || attach_type.equals(ATTACH_CAMERA_GALLERY)) {
            EventAttachment eventAttachment = new EventAttachment("", Constants.EVENT_IMAGE_ATTACH, path, "", false);
            eventAttachments.add(0, eventAttachment);
            eventAttachAdapter.notifyDataSetChanged();
        } else if (attach_type.equals(ATTACH_VIDEO) || attach_type.equals(ATTACH_VIDEO_GALLERY)) {
            EventAttachment eventAttachment = new EventAttachment("", Constants.EVENT_VIDEO_ATTACH, path, getBitmap(path), false);
            eventAttachments.add(0, eventAttachment);
            eventAttachAdapter.notifyDataSetChanged();
        }
    }

    public String getBitmap(String video_path) {
        File f = new File(video_path);
        if (f.exists()) {
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(f.toString(), MediaStore.Video.Thumbnails.MINI_KIND);
//            iv_image.setImageBitmap(thumb);

            String storage_file = FileUtils.getVideoFolder() + File.separator + System.currentTimeMillis() + ".png";
            FileOutputStream fos = null;

            try {
                fos = new FileOutputStream(new File(storage_file));
                Log.d(TagUtils.getTag(), "taking photos");
                thumb.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
                return storage_file;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
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
        if (start_date) {
            et_start_date.setText(date);
        } else {
            et_end_date.setText(date);
        }
    }


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hour = "";
        String minutes = "";
        if (hourOfDay < 10) {
            hour = "0" + hourOfDay;
        } else {
            hour = String.valueOf(hourOfDay);
        }

        if (minute < 10) {
            minutes = "0" + minute;
        } else {
            minutes = String.valueOf(minute);
        }

        String time = hour + ":" + minutes;
        if (start_time) {
            et_start_time.setText(time);
        } else {
            et_end_time.setText(time);
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
