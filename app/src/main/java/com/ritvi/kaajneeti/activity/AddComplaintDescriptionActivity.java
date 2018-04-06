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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddComplaintDescriptionActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 101;
    private static int PICK_IMAGE_REQUEST = 102;
    private static final int VIDEO_CAPTURE = 103;
    private static int PICK_VIDEO_REQUEST = 104;
    private static final int TAG_PEOPLE = 105;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_subject)
    EditText et_subject;
    @BindView(R.id.et_description)
    EditText et_description;
    @BindView(R.id.rv_files)
    RecyclerView rv_files;
    @BindView(R.id.btn_submit)
    Button btn_submit;

    private final String COVER_IMAGE = "cover_image";
    private final String COVER_IMAGE_GALLERY = "cover_image_gallery";
    private final String ATTACH_CAMERA = "attach_image";
    private final String ATTACH_CAMERA_GALLERY = "attach_image_gallery";
    private final String ATTACH_VIDEO = "attach_video";
    private final String ATTACH_VIDEO_GALLERY = "attach_video_gallery";

    String attach_type = "";

    String applicant_name="";
    String applicant_father_name="";
    String applicant_mobile="";
    String complaint_type_id="";
    String leader_id="";
    List<UserInfoPOJO> taggedUserInfoPOJOS=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint_description);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle("Complaint Description");
        attachAdapter();

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            applicant_name=bundle.getString("applicant_name");
            applicant_father_name=bundle.getString("applicant_father_name");
            applicant_mobile=bundle.getString("applicant_mobile");
            complaint_type_id=bundle.getString("complaint_type_id");
            leader_id=bundle.getString("leader_id");
            taggedUserInfoPOJOS=(List<UserInfoPOJO>) bundle.getSerializable("taggedpeople");

            if(taggedUserInfoPOJOS!=null&&taggedUserInfoPOJOS.size()>0){
                Log.d(TagUtils.getTag(),"tagged people:-"+taggedUserInfoPOJOS.toString());
            }
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveComplaint();
            }
        });
    }

    public void saveComplaint(){
        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("", ""));
            reqEntity.addPart("user_profile_id", new StringBody(Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));
            reqEntity.addPart("complaint_subject", new StringBody(et_subject.getText().toString()));
            reqEntity.addPart("complaint_description", new StringBody(et_description.getText().toString()));
            reqEntity.addPart("applicant_name", new StringBody(applicant_name));
            reqEntity.addPart("applicant_father_name", new StringBody(applicant_father_name));
            reqEntity.addPart("applicant_mobile", new StringBody(applicant_mobile));
            reqEntity.addPart("assign_to_profile_id", new StringBody(leader_id));
            reqEntity.addPart("complaint_type_id", new StringBody(complaint_type_id));


            if(taggedUserInfoPOJOS!=null&&taggedUserInfoPOJOS.size()>0){
                for(int i=0;i<taggedUserInfoPOJOS.size();i++){
                    if(taggedUserInfoPOJOS.get(i).getUserProfileCitizen()!=null) {
                        reqEntity.addPart("complaint_member[" + i + "]", new StringBody(taggedUserInfoPOJOS.get(i).getUserProfileCitizen().getUserProfileId()));
                    }else{
                        reqEntity.addPart("complaint_member[" + i + "]", new StringBody(taggedUserInfoPOJOS.get(i).getUserProfileLeader().getUserProfileId()));
                    }
                }
            }else{
                reqEntity.addPart("complaint_member", new StringBody(complaint_type_id));
            }

            int count = 0;

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

            new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    Log.d(TagUtils.getTag(), apicall + " :- " + response);
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                        if(jsonObject.optString("status").equals("success")){
                            startActivity(new Intent(AddComplaintDescriptionActivity.this,ApplicationSubmittedActivity.class).putExtra("comp_type","complaint"));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, "CREATE_EVENT", true).execute(WebServicesUrls.POST_COMPLAINT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.capri4physio.fileProvider", mediaFile);
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
                        this, selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if (selectedImagePath != null && selectedImagePath != "") {
                    image_path_string = selectedImagePath;
                    Log.d(TagUtils.getTag(), "selected path:-" + selectedImagePath);
                    setAttach(image_path_string);
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
                    setAttach(image_path_string);

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
    }

    public void setAttach(String path) {

       if (attach_type.equals(ATTACH_CAMERA) || attach_type.equals(ATTACH_CAMERA_GALLERY)) {
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
