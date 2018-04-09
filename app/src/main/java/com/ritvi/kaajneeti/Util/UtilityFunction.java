package com.ritvi.kaajneeti.Util;

import android.content.Context;

import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

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

    public static String getProfileID(UserInfoPOJO userInfoPOJO){
        if(userInfoPOJO.getUserProfileCitizen()!=null){
            return userInfoPOJO.getUserProfileCitizen().getUserProfileId();
        }else{
            return userInfoPOJO.getUserProfileLeader().getUserProfileId();
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


}
