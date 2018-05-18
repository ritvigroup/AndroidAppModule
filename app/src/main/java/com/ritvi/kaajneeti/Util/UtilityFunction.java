package com.ritvi.kaajneeti.Util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.EditText;

import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sunil on 01-03-2018.
 */

public class UtilityFunction {
    public static ArrayList<NameValuePair> getNameValuePairs(Context context){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("device_token",Pref.GetDeviceToken(context,"simulator_id")));
        if(android.os.Build.MODEL.equals("")) {
            nameValuePairs.add(new BasicNameValuePair("device_name", android.os.Build.MODEL));
        }else{
            nameValuePairs.add(new BasicNameValuePair("device_name", "simulator"));
        }
        nameValuePairs.add(new BasicNameValuePair("device_os","android"));
        nameValuePairs.add(new BasicNameValuePair("language",Pref.GetStringPref(context,StringUtils.SELECTED_LANGUAGE,"en")));
        nameValuePairs.add(new BasicNameValuePair("location_lang",Pref.GetStringPref(context,StringUtils.CURRENT_LATITUDE,"28.6274271")));
        nameValuePairs.add(new BasicNameValuePair("location_long",Pref.GetStringPref(context,StringUtils.CURRENT_LONGITUDE,"77.3723356")));

        return nameValuePairs;
    }


    public static String getCurrentDate(){
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        String formatted_date=sdf.format(d);
        return formatted_date;
    }

    public static String getConvertedDate(String date){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date d = sdf.parse(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formatted_date = simpleDateFormat.format(d);
            return formatted_date;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String getServerConvertedDate(String date){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String formatted_date = simpleDateFormat.format(d);
            return formatted_date;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    public static String getServerConvertedFullDate(String date){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            String formatted_date = simpleDateFormat.format(d);
            return formatted_date;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String[] getDateValues(String date){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            String formatted_date=simpleDateFormat.format(d);
            String[] dateValues={formatted_date.split("-")[0],formatted_date.split("-")[1],formatted_date.split("-")[2]};
            return dateValues;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getProfileID(UserInfoPOJO userInfoPOJO){
        if(userInfoPOJO.getUserProfileCitizen()!=null){
            return userInfoPOJO.getUserProfileCitizen().getUserProfileId();
        }else{
            return userInfoPOJO.getUserProfileLeader().getUserProfileId();
        }
    }

    public static String getProfileName(UserInfoPOJO userInfoPOJO){
        UserProfilePOJO userProfilePOJO=getUserProfilePOJO(userInfoPOJO);
        if(userProfilePOJO.getFirstName().equals("")||userProfilePOJO.getLastName().equals("")){
            return userInfoPOJO.getUserName();
        }else{
            return userProfilePOJO.getFirstName()+" "+userProfilePOJO.getLastName();
        }
    }

    public static UserProfilePOJO getUserProfilePOJO(UserInfoPOJO userInfoPOJO) {
        if(userInfoPOJO.getUserProfileCitizen()!=null){
            return userInfoPOJO.getUserProfileCitizen();
        }else{
            return userInfoPOJO.getUserProfileLeader();
        }
    }

    public static String hashCal(String type, String str) {
        byte[] hashSequence = str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashSequence);
            byte messageDigest[] = algorithm.digest();

            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1)
                    hexString.append("0");
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException NSAE) {
        }
        return hexString.toString();
    }

    public static boolean checkEdits(EditText... editTexts){
        for(EditText editText:editTexts){
            if(editText.getText().toString().length()==0){
                return false;
            }
        }
        return true;
    }


    public static String saveThumbFile(File f){
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());

            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int dp(float value) {
        return (int) Math.ceil(1 * value);
    }

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int convertedDP(Context context, int size){
        final float scale = context.getResources().getDisplayMetrics().density;
        Log.d(TagUtils.getTag(),"scale:-"+scale);
        return (int) (size/scale);
    }

    public static int convertDpToPx(Context context,int dp){
        return Math.round(dp*(context.getResources().getDisplayMetrics().xdpi/ DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int[] screenDimensions(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        int[] sizes=new int[]{width,height};
        return sizes;
    }

//    public void setProfilPicWithName(CircleImageView circleImageView, TextView textView,){
//
//    }

}
