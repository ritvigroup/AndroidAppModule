package com.ritvi.kaajneeti.webservice;


import com.ritvi.kaajneeti.pojo.ResponsePOJO;

/**
 * Created by sunil on 29-12-2016.
 */

public interface ResponseCallBack<T> {
    public void onGetMsg(ResponsePOJO<T> responsePOJO);
}
