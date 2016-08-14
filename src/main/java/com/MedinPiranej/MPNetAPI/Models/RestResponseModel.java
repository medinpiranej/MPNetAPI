package com.MedinPiranej.MPNetAPI.Models;

import java.net.HttpURLConnection;

/**
 * Created by MP
 */
public class RestResponseModel {

    @Override
    public String toString(){
        return "RestResponseModel -> " +
                "httpStatusCode = " + httpStatusCode +
                ", errorMessage = \"" + errorMessage +
                "\", timeTakenForOperation = " + timeTakenForOperation +
                ", responseContent : \"" + responseContent + "\"";
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    public long getTimeTakenForOperation() {
        return timeTakenForOperation;
    }

    public void setTimeTakenForOperation(long timeTakenForOperation) {
        this.timeTakenForOperation = timeTakenForOperation;
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public void setConnection(HttpURLConnection connection) {
        this.connection = connection;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private HttpURLConnection connection;

    private String url;

    private int httpStatusCode;

    private String errorMessage;

    private String responseContent;

    private long timeTakenForOperation;

}
