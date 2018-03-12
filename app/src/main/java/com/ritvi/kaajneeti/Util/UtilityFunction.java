package com.ritvi.kaajneeti.Util;

import android.content.Context;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

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
}
