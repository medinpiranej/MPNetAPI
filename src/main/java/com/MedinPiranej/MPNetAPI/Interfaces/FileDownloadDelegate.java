package com.MedinPiranej.MPNetAPI.Interfaces;

/**
 * Created by MP
 */
public abstract class FileDownloadDelegate {
    
    public void downloadStarted(){}

    public void downloadProgressUpdated(int progress){};

    public abstract void downloadFinished(int httpStatus,byte [] fileBytes);

    public void downloadFinishedWithError(Exception exception){
        exception.printStackTrace();
    }
}
