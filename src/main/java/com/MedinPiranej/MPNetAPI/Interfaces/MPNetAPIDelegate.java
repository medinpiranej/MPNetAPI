package com.MedinPiranej.MPNetAPI.Interfaces;

import android.support.annotation.NonNull;

import java.net.HttpURLConnection;

/**
 * Created by MP
 */
public interface MPNetAPIDelegate {

    /**
     * will be used by api as ROOT_URL and value returned will be added to every request
     * example if you return www.domain.com/api/ and you call RestApi.get("/page.php") it will make a get request to "www.domain.com/api/page.php"
     * @return
     */
    @NonNull
    String getApiRootUrl();

    /**
     * use this as root url if your api has another route for files to download
     * @return
     */
    String getApiRootMediaDownloadUrl();

    void addHeadersToRequest(HttpURLConnection connection);

}
