package com.ritvi.kaajneeti.pojo.facebook;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 28-02-2018.
 */

public class FacebookPictureDataPOJO {
    @SerializedName("height")
    String height;
    @SerializedName("is_silhouette")
    String is_silhouette;
    @SerializedName("url")
    String url;
    @SerializedName("width")
    String width;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getIs_silhouette() {
        return is_silhouette;
    }

    public void setIs_silhouette(String is_silhouette) {
        this.is_silhouette = is_silhouette;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
