package com.MedinPiranej.MPNetAPI;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.MedinPiranej.MPNetAPI.Interfaces.FileUploadDelegate;
import com.MedinPiranej.MPNetAPI.Interfaces.MPNetAPIDelegate;
import com.MedinPiranej.MPNetAPI.Models.RestResponseModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *  This utility class provides an abstraction layer for sending multipart HTTP
 */
public class MultipartUtility extends MPNetAPI{

    private final String TAG = "MultipartUtility";

    private static final String LINE_FEED = "\r\n";
    private final String boundary;
    private HttpURLConnection httpConn;
    private String charset;
    private OutputStream outputStream;
    private PrintWriter writer;
    private String requestUrl;


    public MultipartUtility(String requestURL) throws IOException {
        this(requestURL, null,"POST");
    }

    /**
     *      * This constructor initializes a new HTTP POST request with content type
     *      * is set to multipart/form-data
     *      * @param requestURL
     *      * @param headersDelegate
     *      * @param restRequestMethod te send request, example POST,PUT
     *      * @throws IOException
     */
    public MultipartUtility(String requestURL, MPNetAPIDelegate headersDelegate, @NonNull String restRequestMethod) throws IOException {
        this.charset = "utf-8";
        requestUrl = requestURL;
        // creates a unique boundary based on time stamp
        boundary = "---------fejwgdfg8dfg8dfg4383v3443" + System.currentTimeMillis();

        URL url = new URL(requestURL);

        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true); // indicates POST method
        httpConn.setDoInput(true);
        httpConn.setRequestMethod(restRequestMethod);
        httpConn.setChunkedStreamingMode(4096); // chunk size set to 4096 bytes(4kb)

        if(headersDelegate != null){
            headersDelegate.addHeadersToRequest(httpConn);
        }

        httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        outputStream = httpConn.getOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);
    }

    /**
     *  Adds a form field to the request
     *  @param name field name
     *  @param value field value
     */
    public void addFormField(String name, String value) {
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"" + name + "\"").append(LINE_FEED);
        writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.append(value).append(LINE_FEED);
        writer.flush();
    }

    /**
     *      * Adds a upload file section to the request
     *      * @param fieldName name attribute in <input type="file" name="..." />
     *      * @param uploadFile a File to be uploaded
     *      * @throws IOException
     */
    public void addFilePart(Context context, String fieldName, Uri filePath, FileUploadDelegate fileUploadDelegate) throws IOException{
        List <Uri> files = new ArrayList<>();
        files.add(filePath);
        addFilePart(context,fieldName,files, null,fileUploadDelegate);
    }

    public void addFilePart(Context context, String fieldName, List<Uri> filesToAdd,List<FileUploadDelegate> delegates,FileUploadDelegate allFilesUploadDelegate) throws IOException {
        Uri filePath = null;
        if(filesToAdd != null && filesToAdd.size() > 0) {
            for (int i = 0; i < filesToAdd.size(); i++) {
                filePath = filesToAdd.get(i);
                File uploadFile = FileUtils.getFile(context, filePath);
                InputStream inputStream = context.getContentResolver().openInputStream(filePath);
                String fileName = uploadFile.getName();
                String fileMime = FileUtils.getMimeType(context, filePath);
                writer.append("--" + boundary).append(LINE_FEED);
                writer.append("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
                writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
                writer.append("Content-Type: " + fileMime).append(LINE_FEED);
                writer.append(LINE_FEED);
                writer.flush();

                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                long bytesWritten = 0;
                long fileLength = uploadFile.length();
                double progressPercent = 0;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    bytesWritten += bytesRead;
                    outputStream.flush();
                    if (bytesRead > 0) {
                        double double_bytesWritten = bytesWritten;
                        double double_fileLength = fileLength;
                        progressPercent = double_bytesWritten / double_fileLength;

                        // TO run delegate methods in main thread these file values are copied inside final variables to make them accessible for Anonymous runnable class
                        final double _progressPercent = progressPercent;
                        final int index = i;
                        final FileUploadDelegate _allFilesUploadDelegate = allFilesUploadDelegate;
                        final List<FileUploadDelegate> _delegates = delegates;
                        final List<Uri> _filesToAdd = filesToAdd;

                        Handler handler = new Handler(context.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                int progresss;
                                int progressss;
                                if (_delegates != null && _delegates.size() > index && _delegates.get(index) != null) {
                                    progresss = (int) (_progressPercent * 100);
                                    _delegates.get(index).uploadProgressUpdated(progresss);
                                }
                                if(_allFilesUploadDelegate != null){
                                    double file_progress = ((100/_filesToAdd.size())*(index));
                                    progressss = (int)  (((_progressPercent * 100)/_filesToAdd.size()) + file_progress);
                                    _allFilesUploadDelegate.uploadProgressUpdated(progressss);
                                }
                            }
                        });
                    }
                }
                inputStream.close();

                writer.append(LINE_FEED);
                writer.flush();
            }
        }
        else {
            Log.e(TAG,"Warning tried to attach to Multipart request a empty file URI ! current URL = " + httpConn != null? httpConn.toString() : "(httpConn = null)");
        }
    }

    /**
     *      * Adds a header field to the request.
     *      * @param name - name of the header field
     *      * @param value - value of the header field
     *
     */
    public void addHeaderField(String name, String value) {
        writer.append(name + ": " + value).append(LINE_FEED);
        writer.flush();
    }

    /**
     *      * Completes the request and receives response from the server.
     */
    public RestResponseModel sendRequest() {
        RestResponseModel response = new RestResponseModel();
        String httpStringResponse = "";

        writer.append(LINE_FEED).flush();
        writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();
        try {
            // checks server's status code first
            int status = httpConn.getResponseCode();
            response.setHttpStatusCode(status);
            if (status == HttpURLConnection.HTTP_OK) { //200
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        httpConn.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    httpStringResponse += line;
                }
                reader.close();
                httpConn.disconnect();
                response.setResponseContent(httpStringResponse);
            }
            else {
                response.setErrorMessage("Server Returned status : " + status);
                try{// trying to read response body if found
                    InputStream in = httpConn.getErrorStream();
                    response.setResponseContent(isToString(in, "UTF-8"));
                }
                catch (Exception e){
                    e.printStackTrace();
                    // looks like doesn't have a response body from server
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            response.setHttpStatusCode(-1);
            response.setErrorMessage(e.getLocalizedMessage());
        }

        response.setConnection(httpConn);
        response.setUrl(requestUrl);
        return response;
    }
}