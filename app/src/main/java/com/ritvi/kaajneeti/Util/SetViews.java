package com.ritvi.kaajneeti.Util;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;

import java.util.HashSet;
import java.util.Set;

public class SetViews {

    static Set<ImageView> imageViews = new HashSet<>();

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
    }
}
