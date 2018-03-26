package com.ritvi.kaajneeti.webservice;


import com.ritvi.kaajneeti.pojo.ResponseListPOJO;

/**
 * Created by sunil on 29-12-2016.
 */

public interface ResponseListCallback<T> {
    public void onGetMsg(ResponseListPOJO<T> responseListPOJO);
}
