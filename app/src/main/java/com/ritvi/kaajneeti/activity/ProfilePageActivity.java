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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.FileUtils;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.activity.Profile.ProfileEditPageActivity;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.pojo.payment.WalletDetailPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.ResponseCallBack;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponse;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePageActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 101;
    private final int STATE_SELECT_INTENT = 102;
    private static final int CAMERA_REQUEST = 103;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_user_type)
    TextView tv_user_type;
    @BindView(R.id.tv_mobile_number)
    TextView tv_mobile_number;
    @BindView(R.id.tv_email)
    TextView tv_email;

    @BindView(R.id.cv_profile)
    CircleImageView cv_profile;
    @BindView(R.id.iv_edit)
    ImageView iv_edit;
    @BindView(R.id.iv_unfavorite)
    ImageView iv_unfavorite;
    @BindView(R.id.tv_contribute)
    TextView tv_contribute;

    UserProfilePOJO userProfilePOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userProfilePOJO = (UserProfilePOJO) getIntent().getSerializableExtra("userProfilePOJO");

        tv_user_name.setText(userProfilePOJO.getFirstName()+" "+userProfilePOJO.getLastName());
        tv_mobile_number.setText(userProfilePOJO.getMobile());
        tv_email.setText(userProfilePOJO.getEmail());


        Glide.with(getApplicationContext())
                .load(userProfilePOJO.getProfilePhotoPath())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(cv_profile);


        if (userProfilePOJO.getUserId().equals(Constants.userProfilePOJO.getUserId())) {
            iv_edit.setVisibility(View.VISIBLE);
            cv_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final PopupMenu menu = new PopupMenu(ProfilePageActivity.this, view);

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


        } else {
            iv_edit.setVisibility(View.GONE);
        }

//        if (userProfilePOJO.getUserProfileCitizen() != null) {
//            tv_user_type.setText("Citizen");
//            iv_unfavorite.setVisibility(View.GONE);
//            tv_location.setText(userInfoPOJO.getUserProfileCitizen().getCity());
//            tv_contribute.setVisibility(View.GONE);
//        } else {
//            tv_user_type.setText("Leader");
//            iv_unfavorite.setVisibility(View.VISIBLE);
//            tv_location.setText(userInfoPOJO.getUserProfileLeader().getCity());
//        }

        tv_contribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWalletDetailAPI();
            }
        });

        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(ProfilePageActivity.this,ProfileInfoActivity.class));
                startActivity(new Intent(ProfilePageActivity.this, ProfileEditPageActivity.class));
            }
        });

    }

    public void showAmountDialog(final String amount) {
        final Dialog dialog1 = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_enter_amount);
        dialog1.setTitle("Enter Amount");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final EditText et_amount = dialog1.findViewById(R.id.et_amount);
        final EditText et_remark = dialog1.findViewById(R.id.et_remark);
        TextView tv_amount = dialog1.findViewById(R.id.tv_amount);
        final Button btn_cancel = dialog1.findViewById(R.id.btn_cancel);
        Button btn_send = dialog1.findViewById(R.id.btn_send);

        tv_amount.setText("Rs " + amount);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double balance_amount = Double.parseDouble(amount);
                    double transfer_amount = Double.parseDouble(et_amount.getText().toString());
                    if (transfer_amount <= balance_amount) {
                        callTransferAPI(et_remark.getText().toString(), transfer_amount);
                        dialog1.dismiss();
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), "You don't have sufficient balance");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastClass.showShortToast(getApplicationContext(), "Enter valid amount");
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

    public void getWalletDetailAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        new WebServiceBaseResponse<WalletDetailPOJO>(nameValuePairs, this, new ResponseCallBack<WalletDetailPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<WalletDetailPOJO> responsePOJO) {
                try {
                    if (responsePOJO.isSuccess()) {
                        WalletDetailPOJO walletDetailPOJO = responsePOJO.getResult();
                        showAmountDialog(walletDetailPOJO.getMyWalletBalance());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, WalletDetailPOJO.class, "GET_WALLET_DETAIL", true).execute(WebServicesUrls.WALLET_DETAIL_API);

    }

    public void callTransferAPI(String comment, double transfer_amount) {
        Random rand = new Random();
        String randomString = Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("payment_gateway_id", "4"));
        nameValuePairs.add(new BasicNameValuePair("transaction_id", UtilityFunction.hashCal("SHA-256", randomString).substring(0, 20)));
        nameValuePairs.add(new BasicNameValuePair("payment_to_user_profile_id", userProfilePOJO.getProfilePhotoId()));
        nameValuePairs.add(new BasicNameValuePair("transaction_date", UtilityFunction.getCurrentDate()));
        nameValuePairs.add(new BasicNameValuePair("transaction_amount", String.valueOf(transfer_amount)));
        nameValuePairs.add(new BasicNameValuePair("transaction_shipping_amount", String.valueOf(transfer_amount)));
        nameValuePairs.add(new BasicNameValuePair("transaction_status", "1"));
        nameValuePairs.add(new BasicNameValuePair("debit_or_credit", "0"));
        nameValuePairs.add(new BasicNameValuePair("comments", comment));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                Log.d(TagUtils.getTag(), "trans response:-" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("success")) {
                        ToastClass.showShortToast(getApplicationContext(), "Thanks for your contribution.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CALL_TRANS_API", true).execute(WebServicesUrls.SAVE_PAYMENT_TRANSACTIONS);
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
                        ProfilePageActivity.this, selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if (selectedImagePath != null && selectedImagePath != "") {
                    image_path_string = selectedImagePath;
                    Log.d(TagUtils.getTag(), "selected path:-" + selectedImagePath);
                    setProfilePic();
                } else {
                    Toast.makeText(ProfilePageActivity.this, "Selected File is Corrupted", Toast.LENGTH_LONG).show();
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
        Glide.with(getApplicationContext())
                .load(image_path_string)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .dontAnimate()
                .into(cv_profile);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
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

}
