package com.MedinPiranej.MPNetAPI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.support.annotation.UiThread;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MP
 */
public abstract class Utils {

    public static String TAG = "Utils.class";

    private static LayoutInflater layoutInflater;

    /**
     * function to parse XML layouts and returns View Object
     * @param resourceId : id of xml layout , it is automatically generated and saved to R.layout.*
     * @return View Object of given XML , subviews can be accessed by calling
     * view.findViewById(subViewResourceID)
     */
    public static View getViewByXmlLayout(Context context,int resourceId){
        if(context == null){
            Log.e(TAG,"Error : attempted to getViewByXmlLayout with context = null");
            return null;
        }

        if ( layoutInflater == null ) // initialise layoutInflater
        {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return layoutInflater.inflate(resourceId, null);
    }

    public static boolean isValidDate(String date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            Date d = dateFormat.parse(date);
            // string contains valid date
            return true;
        }
        catch (ParseException e){
            // string contains invalid date
            return false;
        }
    }

    public static Date getDateFromString(String format,String date){
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date d;
        try {
            d = dateFormat.parse(date);
        }
        catch (Exception e){
            e.printStackTrace();
            d = null;
        }
        return d;
    }

    public static String dateToString(String format,Date date){
        DateFormat dateFormat = new SimpleDateFormat(format);
        String res = null;
        try {
            res = dateFormat.format(date);
            return res;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public static Bitmap resizeBitmap(Bitmap bm, int newWidth, boolean recycleOriginal) {
        if(bm == null){
            return null;
        } else if (bm.getWidth() <= newWidth) {
            return bm; // if image is smaller than requested size return given bitmap
        }
        float width = bm.getWidth();
        float height = bm.getHeight();
        int newHeight;
        float aspectRatio = width / height;
        newHeight = (int) (newWidth / aspectRatio);
        Bitmap resizedBitmap = ThumbnailUtils.extractThumbnail(bm, newWidth, newHeight);
        if (recycleOriginal) {
            bm.recycle();
        }
        return resizedBitmap;
    }

    public static void showInfoPopup(final Activity activity,final String title, final String message){
        // running on ui thread
        Handler mainHandler = new Handler(activity.getApplicationContext().getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setTitle(title);
                builder.setMessage(message);

                builder.setNegativeButton("ok!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        };
        mainHandler.post(myRunnable);
    }

    @UiThread
    public static AlertDialog.Builder showInfoPopup(final Context context, final String title, final String message) {
        // running on ui thread

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);

        builder.setNegativeButton("ok!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
        return builder;
    }

    public static void showInfoPopup(final Activity activity,final String title, final Spanned message){
        // running on ui thread
        Handler mainHandler = new Handler(activity.getApplicationContext().getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setTitle(title);
                builder.setMessage(message);

                builder.setNegativeButton("ok!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        };
        mainHandler.post(myRunnable);
    }

    public static ProgressDialog showProgressDialog(final Activity activity,final String title, final String message){

        ProgressDialog progressDialog = new ProgressDialog(activity);

        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(true);
        progressDialog.show();

        return progressDialog;
    }

    public static float dpToPixels(float dpSize,Context context){
        float coefficient = context.getResources().getDimension(R.dimen.dpCoefficient);
        return coefficient * (dpSize/100);
    }
}
