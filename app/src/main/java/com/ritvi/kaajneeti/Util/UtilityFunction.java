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
        nameValuePairs.add(new BasicNameValuePair("device_token",Pref.GetDeviceToken(context,"")));
        nameValuePairs.add(new BasicNameValuePair("device_name",android.os.Build.MODEL));
        nameValuePairs.add(new BasicNameValuePair("device_os","android"));
        nameValuePairs.add(new BasicNameValuePair("language",Pref.GetStringPref(context,StringUtils.SELECTED_LANGUAGE,"")));
        nameValuePairs.add(new BasicNameValuePair("latitude",Pref.GetStringPref(context,StringUtils.CURRENT_LATITUDE,"")));
        nameValuePairs.add(new BasicNameValuePair("longitude",Pref.GetStringPref(context,StringUtils.CURRENT_LONGITUDE,"")));

        return nameValuePairs;
    }
}
