package com.ritvi.kaajneeti.Util;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;

import java.util.HashSet;
import java.util.Set;

public class SetViews {

    static Set<ImageView> imageViews = new HashSet<>();
    static Set<TextView> textViews = new HashSet<>();

    public static void setProfilePhoto(Context context, String profilePhoto, ImageView profileImageView) {
        if (profilePhoto.equals(Constants.userProfilePOJO.getProfilePhotoPath())) {
            imageViews.add(profileImageView);
            Log.d(TagUtils.getTag(), "added");
        }
        Glide.with(context)
                .load(profilePhoto)
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(profileImageView);
    }

    public static void setProfileName(String profilename, TextView textView) {
        if (profilename.equals(Constants.userProfilePOJO.getFirstName()+" "+Constants.userProfilePOJO.getLastName())) {
            textViews.add(textView);
            Log.d(TagUtils.getTag(), "added");
        }
        textView.setText(profilename);
    }


    public static void changeProfilePics(Context context, String path) {
        if (imageViews != null && imageViews.size() > 0) {
            for (ImageView imageView : imageViews) {
                if (imageView != null) {
                    Glide.with(context)
                            .load(path)
                            .placeholder(R.drawable.ic_default_profile_pic)
                            .error(R.drawable.ic_default_profile_pic)
                            .dontAnimate()
                            .into(imageView);
                }
            }
        }
        changeProfileNames();
    }

    public static void changeProfileNames(){
        try {
            for (TextView textView : textViews) {
                textView.setText(Constants.userProfilePOJO.getFirstName() + " " + Constants.userProfilePOJO.getLastName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
