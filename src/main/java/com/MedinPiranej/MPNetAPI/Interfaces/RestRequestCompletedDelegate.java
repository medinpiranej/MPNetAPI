package com.MedinPiranej.MPNetAPI.Interfaces;

import com.MedinPiranej.MPNetAPI.Models.RestResponseModel;

/**
 * Created by MP
 */
public interface RestRequestCompletedDelegate {

    void requestFinishedWithResponse(RestResponseModel response);

}
