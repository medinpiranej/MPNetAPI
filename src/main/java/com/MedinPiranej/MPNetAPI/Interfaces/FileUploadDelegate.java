package com.MedinPiranej.MPNetAPI.Interfaces;

import android.support.annotation.UiThread;

import com.MedinPiranej.MPNetAPI.Models.RestResponseModel;

/**
 * Created by MP
 */
public interface FileUploadDelegate {

    @UiThread
    void uploadStarted();

    @UiThread
    void uploadProgressUpdated(int progress);

    @UiThread
    void uploadFinished(RestResponseModel response);

}