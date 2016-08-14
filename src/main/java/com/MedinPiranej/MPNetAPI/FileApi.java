package com.MedinPiranej.MPNetAPI;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.MedinPiranej.MPNetAPI.Interfaces.FileDownloadDelegate;
import com.MedinPiranej.MPNetAPI.Interfaces.FileUploadDelegate;
import com.MedinPiranej.MPNetAPI.Interfaces.MPNetAPIDelegate;
import com.MedinPiranej.MPNetAPI.Models.RestResponseModel;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MP
 */
public class FileApi extends MPNetAPI{

    public static final String TAG = "FileApi.class";

    public static void getFile(final String servicePath,@NonNull final FileDownloadDelegate fileDownloadDelegate ){
        getFile(servicePath,null,fileDownloadDelegate);
    }

    public static void getFile(final String servicePath,final MPNetAPIDelegate mpNetAPIDelegate,@NonNull final FileDownloadDelegate fileDownloadDelegate ) {

        new AsyncTask<Void, Void, Void>() {

            double progressPercent = 0;
            byte[] receivedFileBytes = null;
            int status = -1;
            Exception exceptionOccurred;

            @Override
            protected void onPreExecute(){
                fileDownloadDelegate.downloadStarted();
            }

            @Override
            protected Void doInBackground(Void... params) {
                HttpURLConnection connection = null;
                try {

                    final String ROOT_URL;
                    if(mpNetAPIDelegate != null ){
                        if(mpNetAPIDelegate.getApiRootMediaDownloadUrl() != null){
                            ROOT_URL = mpNetAPIDelegate.getApiRootMediaDownloadUrl();
                        }
                        else {
                            ROOT_URL = mpNetAPIDelegate.getApiRootUrl();
                        }
                    }
                    else{
                        ROOT_URL = "";
                    }

                    if (isDebugging) {
                        Log.e(TAG, "Downloading file from URL : " + ROOT_URL + servicePath);
                    }

                    connection = (HttpURLConnection) new URL(ROOT_URL + servicePath).openConnection();

                    if(mpNetAPIDelegate != null){
                        mpNetAPIDelegate.addHeadersToRequest(connection);
                    }

                    status = connection.getResponseCode();
                    if (status == 200) {
                        int fileLength = connection.getContentLength();
                        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                        InputStream inputStream = connection.getInputStream();
                        int nRead;
                        byte[] data = new byte[fileLength];
                        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                            buffer.write(data, 0, nRead);
                            if (nRead != 0) {
                                double double_nRead = nRead;
                                double double_fileLength = fileLength;
                                progressPercent = double_nRead / double_fileLength;
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        fileDownloadDelegate.downloadProgressUpdated((int) (progressPercent * 100));
                                    }
                                });
                            }
                        }
                        buffer.flush();
                        receivedFileBytes = buffer.toByteArray();

                        if(isDebugging){
                            Log.e(TAG,"Downloaded file (" + (receivedFileBytes!=null?receivedFileBytes.length:0) + " bytes) from url " + ROOT_URL + servicePath);
                        }

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                fileDownloadDelegate.downloadFinished(status, receivedFileBytes);
                            }
                        });
                    }
                } catch (final Exception e) {
                    status = -1;
                    e.printStackTrace();
                    exceptionOccurred = e;
                }
                finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if(status != -1) {
                    fileDownloadDelegate.downloadFinished(status, receivedFileBytes);
                }
                else {
                    fileDownloadDelegate.downloadFinishedWithError(exceptionOccurred);
                }
            }
        }.execute();
    }

    /**
     * Uploads a specific file in uri and send requestData as form data and delivers result and progress on delegate
     * @param context is used for getting files from Uri-s
     * @param servicePath to upload file
     * @param restRequestMethod te send request, example POST,PUT
     * @param requestData to send as form data
     * @param file to upload
     * @param delegate to deliver progress and response when finished
     */
    public static void uploadFile(Context context, String servicePath, @NonNull String restRequestMethod, HashMap<String,String> requestData, MPNetAPIDelegate headersDelegate, Uri file, String filesFieldName, FileUploadDelegate delegate){
        List<Uri> files = new ArrayList<>();
        files.add(file);
        uploadFile(context,servicePath, restRequestMethod, requestData,headersDelegate ,files, filesFieldName,null,delegate);
    }

    /**
     * Uploads a given List<Uri> of files and send requestData as form data and delivers result and progress on delegate
     * @param context is used for getting files from Uri-s
     * @param servicePath to upload files
     * @param restRequestMethod te send request, example POST,PUT
     * @param requestData to send as form data
     * @param files to upload
     * @param fileUploadDelegateList to deliver progress of each file
     * @param allFilesUploadDelegate to deliver overall progress , response and result
     */
    public static void uploadFile(final Context context, final String servicePath, @NonNull final String restRequestMethod, final HashMap<String,String> requestData, final MPNetAPIDelegate mpNetAPIDelegate, final List<Uri> files, final String filesFieldName, final List<FileUploadDelegate> fileUploadDelegateList, final FileUploadDelegate allFilesUploadDelegate){
        new AsyncTask<Void,Void,Void>(){
            RestResponseModel response = null;
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final String ROOT_URL;
                    if(mpNetAPIDelegate != null ){
                        ROOT_URL = mpNetAPIDelegate.getApiRootUrl();
                    }
                    else{
                        ROOT_URL = "";
                    }

                    MultipartUtility multipartUtility = new MultipartUtility(ROOT_URL + servicePath,mpNetAPIDelegate,restRequestMethod);


                    if(requestData != null){
                        for(Map.Entry<String, String> entry : requestData.entrySet()){
                            multipartUtility.addFormField(entry.getKey(),requestData.get(entry.getValue()));
                        }
                    }

                    if(files != null && files.size() > 0){
                        multipartUtility.addFilePart(context,filesFieldName,files,fileUploadDelegateList,allFilesUploadDelegate);
                    }

                    response = multipartUtility.sendRequest();

                    if(isDebugging) {
                        Log.e(TAG, response.toString());
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if(allFilesUploadDelegate != null){
                    allFilesUploadDelegate.uploadFinished(response);
                }
            }

        }.execute();
    }

}
