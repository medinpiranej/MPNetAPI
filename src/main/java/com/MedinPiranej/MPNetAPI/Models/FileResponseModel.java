package com.MedinPiranej.MPNetAPI.Models;

/**
 * Created by MP
 */
public class FileResponseModel {

    @Override
    public String toString(){
        return "FileResponseModel -> " +
                "httpStatusCode = " + httpStatusCode +
                ", errorMessage = \"" + errorMessage +
                "\", timeTakenForOperation = " + timeTakenForOperation +
                ", responseContent.length : \"" + responseContent.length + "\"";
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

    public byte [] getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(byte [] responseContent) {
        this.responseContent = responseContent;
    }

    public long getTimeTakenForOperation() {
        return timeTakenForOperation;
    }

    public void setTimeTakenForOperation(long timeTakenForOperation) {
        this.timeTakenForOperation = timeTakenForOperation;
    }

    private int httpStatusCode;

    private String errorMessage;

    private byte [] responseContent;

    private long timeTakenForOperation;

}
