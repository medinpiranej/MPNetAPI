package com.MedinPiranej.MPNetAPI;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.MedinPiranej.MPNetAPI.Interfaces.MPNetAPIDelegate;
import com.MedinPiranej.MPNetAPI.Interfaces.RestRequestCompletedDelegate;
import com.MedinPiranej.MPNetAPI.Models.RestResponseModel;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by MP
 */
public class RestApi extends MPNetAPI {

    private final static String TAG = "RestApi.class";

    /**
     * Send a Asynchronous POST(application/json) HTTP request and send result on given RestRequestCompletedDelegate *** WITHOUT AUTHORIZATION ***
     * @param servicePath to send request
     * @param requestJSONObject , Json string object to restConnect
     * @param delegate to send result when finished
     */
    public static void postAsynchronous(@NonNull final String servicePath, final String requestJSONObject, @NonNull final RestRequestCompletedDelegate delegate){
        postAsynchronous(servicePath,null,requestJSONObject,delegate);
    }

    /**
     * Send a Asynchronous POST(application/json) HTTP request and send result on given RestRequestCompletedDelegate
     * @param servicePath to send request
     * @param headersDelegate add custom headers to request , authorization can be added here
     * @param requestJSONObject , Json string object to restConnect
     * @param delegate to send result when finished
     */
    public static void postAsynchronous(@NonNull final String servicePath, @Nullable final MPNetAPIDelegate headersDelegate, final String requestJSONObject, @NonNull final RestRequestCompletedDelegate delegate){
        new AsyncTask<Void,Void,Void>(){
            RestResponseModel response;
            @Override
            protected Void doInBackground(Void... params) {
                response = post(servicePath, headersDelegate, requestJSONObject);
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                delegate.requestFinishedWithResponse(response);
            }
        }.execute();
    }

    /**
     * Send a Asynchronous POST(application/x-www-form-urlencoded) HTTP request and send result on given RestRequestCompletedDelegate  *** WITHOUT AUTHORIZATION ***
     * @param servicePath to send request
     * @param postDataParams to add to POST request
     * @param delegate to send result when finished
     */
    public static void postAsynchronous(@NonNull final String servicePath, final HashMap<String,String> postDataParams,@NonNull final RestRequestCompletedDelegate delegate){
        postAsynchronous(servicePath,null,postDataParams,delegate);
    }

    /**
     * Send a Asynchronous POST(application/x-www-form-urlencoded) HTTP request and send result on given RestRequestCompletedDelegate
     * @param servicePath to send request
     * @param headersDelegate add custom headers to request , authorization can be added here
     * @param postDataParams to add to POST request
     * @param delegate to send result when finished
     */
    public static void postAsynchronous(@NonNull final String servicePath, @Nullable final MPNetAPIDelegate headersDelegate, final HashMap<String,String> postDataParams, @NonNull final RestRequestCompletedDelegate delegate){
        new AsyncTask<Void,Void,Void>(){
            RestResponseModel response;
            @Override
            protected Void doInBackground(Void... params) {
                response = post(servicePath,headersDelegate,postDataParams);
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                delegate.requestFinishedWithResponse(response);
            }
        }.execute();
    }

    /**
     * Send a Asynchronous PUT(application/json) HTTP request and send result on given RestRequestCompletedDelegate *** WITHOUT AUTHORIZATION ***
     * @param servicePath to send request
     * @param requestJSONObject , Json string object to post
     * @param delegate to send result when finished
     */
    public static void putAsynchronous(@NonNull final String servicePath, final String requestJSONObject, @NonNull final RestRequestCompletedDelegate delegate){
        putAsynchronous(servicePath,null,requestJSONObject,delegate);
    }

    /**
     * Send a Asynchronous PUT(application/json) HTTP request and send result on given RestRequestCompletedDelegate
     * @param servicePath to send request
     * @param headersDelegate add custom headers to request , authorization can be added here
     * @param requestJSONObject , Json string object to post
     * @param delegate to send result when finished
     */
    public static void putAsynchronous(@NonNull final String servicePath, @Nullable final MPNetAPIDelegate headersDelegate, final String requestJSONObject, @NonNull final RestRequestCompletedDelegate delegate){
        new AsyncTask<Void,Void,Void>(){
            RestResponseModel response;
            @Override
            protected Void doInBackground(Void... params) {
                response = put(servicePath, headersDelegate, requestJSONObject);
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                delegate.requestFinishedWithResponse(response);
            }
        }.execute();
    }

    /**
     * Send a Asynchronous PUT(application/x-www-form-urlencoded) HTTP request and send result on given RestRequestCompletedDelegate  *** WITHOUT AUTHORIZATION ***
     * @param servicePath to send request
     * @param postDataParams to add to POST request
     * @param delegate to send result when finished
     */
    public static void putAsynchronous(@NonNull final String servicePath, final HashMap<String,String> postDataParams,@NonNull final RestRequestCompletedDelegate delegate){
        putAsynchronous(servicePath,null,postDataParams,delegate);
    }

    /**
     * Send a Asynchronous PUT(application/x-www-form-urlencoded) HTTP request and send result on given RestRequestCompletedDelegate
     * @param servicePath to send request
     * @param headersDelegate add custom headers to request , authorization can be added here
     * @param postDataParams to add to POST request
     * @param delegate to send result when finished
     */
    public static void putAsynchronous(@NonNull final String servicePath, @Nullable final MPNetAPIDelegate headersDelegate, final HashMap<String,String> postDataParams, @NonNull final RestRequestCompletedDelegate delegate){
        new AsyncTask<Void,Void,Void>(){
            RestResponseModel response;
            @Override
            protected Void doInBackground(Void... params) {
                response = put(servicePath,headersDelegate,postDataParams);
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                delegate.requestFinishedWithResponse(response);
            }
        }.execute();
    }

    /**
     * Send a Asynchronous GET HTTP request and send result on given RestRequestCompletedDelegate *** without authorization ***
     * @param servicePath to send request
     * @param delegate to send result when finished
     */
    public static void getAsynchronous(@NonNull final String servicePath, @NonNull final RestRequestCompletedDelegate delegate){
        getAsynchronous(servicePath,null,delegate);
    }

    /**
     * Send a Asynchronous GET HTTP request and send result on given RestRequestCompletedDelegate
     * @param servicePath to send request
     * @param headersDelegate add custom headers to request , authorization can be added here
     * @param delegate to send result when finished
     */
    public static void getAsynchronous(@NonNull final String servicePath, @Nullable final MPNetAPIDelegate headersDelegate, @NonNull final RestRequestCompletedDelegate delegate){
        new AsyncTask<Void,Void,Void>(){
            RestResponseModel response;
            @Override
            protected Void doInBackground(Void... params) {
                response = RestApi.get(servicePath,headersDelegate);
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                delegate.requestFinishedWithResponse(response);
            }
        }.execute();
    }

    /**
     * Send a Asynchronous DELETE HTTP request and send result on given RestRequestCompletedDelegate *** without authorization ***
     * @param servicePath to send request
     * @param delegate to send result when finished
     */
    public static void deleteAsynchronous(@NonNull final String servicePath, @NonNull final RestRequestCompletedDelegate delegate){
        deleteAsynchronous(servicePath,null,delegate);
    }

    /**
     * Send a Asynchronous DELETE HTTP request and send result on given RestRequestCompletedDelegate
     * @param servicePath to send request
     * @param headersDelegate add custom headers to request , authorization can be added here
     * @param delegate to send result when finished
     */
    public static void deleteAsynchronous(@NonNull final String servicePath, @Nullable final MPNetAPIDelegate headersDelegate, @NonNull final RestRequestCompletedDelegate delegate){
        new AsyncTask<Void,Void,Void>(){
            RestResponseModel response;
            @Override
            protected Void doInBackground(Void... params) {
                response = RestApi.delete(servicePath,headersDelegate);
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                delegate.requestFinishedWithResponse(response);
            }
        }.execute();
    }

    /**
     * Send a POST(application/json) HTTP request and return response *** WITHOUT AUTHORIZATION ***
     * *** This is a synchronous function ... Cannot be run on main thread ***
     * @param servicePath to send request
     * @param requestData , Json string object to restConnect
     * @return request response
     */
    @WorkerThread
    public static RestResponseModel post(@NonNull String servicePath, String requestData) {
        return post(servicePath,null,requestData);
    }

    /**
     * Send a POST(application/json) HTTP request and return response
     * *** This is a synchronous function ... Cannot be run on main thread ***
     * @param servicePath to send request
     * @param headersDelegate add custom headers to request , authorization can be added here
     * @param requestJSONObject , Json string object to restConnect
     * @return request response
     */
    @WorkerThread
    public static RestResponseModel post(@NonNull String servicePath, @Nullable final MPNetAPIDelegate headersDelegate, String requestJSONObject) {
        return restRequest("POST",servicePath,headersDelegate,requestJSONObject,"application/json");
    }

    /**
     * Send a POST(application/x-www-form-urlencoded) HTTP request and return response *** WITHOUT AUTHORIZATION ***
     * *** This is a synchronous function ... Cannot be run on main thread ***
     * @param servicePath to send request
     * @param postDataParams to add to POST request
     * @return request response
     */
    @WorkerThread
    public static RestResponseModel post(@NonNull String servicePath, HashMap<String, String> postDataParams){
        return post(servicePath,null,postDataParams);
    }

    /**
     * Send a POST(application/x-www-form-urlencoded) HTTP request and return response *** WITHOUT AUTHORIZATION ***
     * *** This is a synchronous function ... Cannot be run on main thread ***
     * @param servicePath to send request
     * @param headersDelegate add custom headers to request , authorization can be added here
     * @param postDataParams to add to POST request
     * @return request response
     */
    @WorkerThread
    public static RestResponseModel post(@NonNull String servicePath, @Nullable final MPNetAPIDelegate headersDelegate, HashMap<String, String> postDataParams){
        String requestBody = null;
        try{
            requestBody = encodeHashMapToString(postDataParams);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return restRequest("POST",servicePath,headersDelegate,requestBody,"application/x-www-form-urlencoded");
    }


    /**
     * Send a PUT(application/json) HTTP request and return response *** WITHOUT AUTHORIZATION ***
     * *** This is a synchronous function ... Cannot be run on main thread ***
     * @param servicePath to send request
     * @param requestData , Json string object to put
     * @return request response
     */
    @WorkerThread
    public static RestResponseModel put(@NonNull String servicePath, String requestData) {
        return put(servicePath,null,requestData);
    }

    /**
     * Send a PUT(application/json) HTTP request and return response
     * *** This is a synchronous function ... Cannot be run on main thread ***
     * @param servicePath to send request
     * @param headersDelegate add custom headers to request , authorization can be added here
     * @param requestJSONObject , Json string object to put
     * @return request response
     */
    @WorkerThread
    public static RestResponseModel put(@NonNull String servicePath, @Nullable final MPNetAPIDelegate headersDelegate, String requestJSONObject) {
        return restRequest("PUT",servicePath,headersDelegate,requestJSONObject,"application/json");
    }

    /**
     * Send a PUT(application/x-www-form-urlencoded) HTTP request and return response *** WITHOUT AUTHORIZATION ***
     * *** This is a synchronous function ... Cannot be run on main thread ***
     * @param servicePath to send request
     * @param postDataParams to add to POST request
     * @return request response
     */
    @WorkerThread
    public static RestResponseModel put(@NonNull String servicePath, HashMap<String, String> postDataParams){
        return post(servicePath,null,postDataParams);
    }

    /**
     * Send a PUT(application/x-www-form-urlencoded) HTTP request and return response *** WITHOUT AUTHORIZATION ***
     * *** This is a synchronous function ... Cannot be run on main thread ***
     * @param servicePath to send request
     * @param headersDelegate add custom headers to request , authorization can be added here
     * @param postDataParams to add to POST request
     * @return request response
     */
    @WorkerThread
    public static RestResponseModel put(@NonNull String servicePath, @Nullable final MPNetAPIDelegate headersDelegate, HashMap<String, String> postDataParams){
        String requestBody = null;
        try{
            requestBody = encodeHashMapToString(postDataParams);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return restRequest("PUT",servicePath,headersDelegate,requestBody,"application/x-www-form-urlencoded");
    }


    /**
     * Send a GET HTTP request and return response *** WITHOUT AUTHORIZATION ***
     * *** This is a synchronous function ... Cannot be run on main thread ***
     * @param servicePath to send request
     * @return request response
     */
    @WorkerThread
    public static RestResponseModel get(@NonNull String servicePath){
        return get(servicePath,null);
    }

    /**
     * Send a GET HTTP request and return response
     * *** This is a synchronous function ... Cannot be run on main thread ***
     * @param servicePath to send request
     * @param headersDelegate add custom headers to request , authorization can be added here
     * @return request response
     */
    @WorkerThread
    public static RestResponseModel get(@NonNull String servicePath, @Nullable final MPNetAPIDelegate headersDelegate){
        return restRequest("GET",servicePath,headersDelegate,null,null);
    }

    /**
     * Send a DELETE HTTP request and return response *** without authorization ***
     * *** This is a synchronous function ... Cannot be run on main thread ***
     * @param servicePath to send request
     * @return request response
     */
    @WorkerThread
    public static RestResponseModel delete(@NonNull String servicePath){
        return delete(servicePath,null);
    }

    /**
     * Send a DELETE HTTP request and return response
     * *** This is a synchronous function ... Cannot be run on main thread ***
     * @param servicePath to send request
     * @param headersDelegate add custom headers to request , authorization can be added here
     * @return request response
     */
    @WorkerThread
    public static RestResponseModel delete(@NonNull String servicePath, @Nullable final MPNetAPIDelegate headersDelegate) {
        return restRequest("DELETE",servicePath,headersDelegate,null,null);
    }

    /**
     * Send a REST HTTP request and return response
     * *** This is a synchronous function ... Cannot be run on main thread ***
     * @param restRequestMethod , rest methods , example : GET, POST, PUT, DELETE
     * @param servicePath to send request
     * @param delegate add custom headers to request , authorization can be added here
     * @param requestBody to add to request ()
     * @param contentType of content
     * @return request response
     */
    @WorkerThread
    public static RestResponseModel restRequest(String restRequestMethod, @NonNull String servicePath, @Nullable final MPNetAPIDelegate delegate, String requestBody, String contentType){
        RestResponseModel response = new RestResponseModel();
        response.setTimeTakenForOperation(System.currentTimeMillis());
        int status;
        final String ROOT_URL;

        if(delegate != null){
            ROOT_URL = delegate.getApiRootUrl();
        }
        else{
            ROOT_URL = "";
        }

        if(isDebugging) {
            Log.e(TAG, "sending " + restRequestMethod + " request to "+ ROOT_URL + servicePath + (requestBody != null? " with requestBody : " + requestBody : " with no requestBody"));
        }

        HttpURLConnection connection = null;

        try{
            connection = (HttpURLConnection) new URL(ROOT_URL + servicePath).openConnection();

            if(delegate != null){
                delegate.addHeadersToRequest(connection);
            }
            else if(isDebugging){
                Log.e(TAG,"Request is being sent with no custom headers");
            }

            connection.setRequestMethod(restRequestMethod);

            if(requestBody != null) {

                connection.setDoOutput(true);

                if(contentType != null) {
                    connection.setRequestProperty("Content-Type", contentType);
                }

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(requestBody);

                writer.flush();
                writer.close();
                os.close();
            }

            status = connection.getResponseCode();
            response.setHttpStatusCode(status);

            if(status == 200) {
                InputStream in = connection.getInputStream();
                response.setResponseContent(isToString(in, "UTF-8"));
            }
            else {
                response.setErrorMessage("Server Returned status : " + status);
                try{// trying to read response body if found
                    InputStream in = connection.getErrorStream();
                    response.setResponseContent(isToString(in, "UTF-8"));
                }
                catch (Exception e){
                    e.printStackTrace();
                    // looks like doesn't have a response body from server
                }
            }
        }
        catch(Exception e){
            response.setErrorMessage(e.getLocalizedMessage());
            e.printStackTrace();
        }
        finally {
            response.setTimeTakenForOperation(System.currentTimeMillis() - response.getTimeTakenForOperation());
            if (connection != null) {
                connection.disconnect();
            }
        }

        if(isDebugging) {
            Log.e(TAG, "Response from " + restRequestMethod + " request from url : " + ROOT_URL + servicePath + " : " + response.toString());
        }

        response.setConnection(connection);
        response.setUrl(ROOT_URL + servicePath);
        return response;
    }

}
