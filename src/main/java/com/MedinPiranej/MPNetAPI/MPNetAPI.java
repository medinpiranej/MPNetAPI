package com.MedinPiranej.MPNetAPI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MP
 */
public abstract class MPNetAPI {
    private final static String TAG = "MPNetAPI.class";

    // TODO set false if not debugging
    public final static boolean isDebugging = true;

    public static HashMap<String,String> customHeaders;

    public static String isToString(InputStream inputStream, String encoding) throws IOException {
        return new String(getBytes(inputStream), encoding);
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toByteArray();
    }

    public static void copyStream(InputStream input, OutputStream output) throws IOException {
        final int BUFFER_SIZE = 2 * 1024 * 1024;
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = input.read(buffer);
            while (bytesRead != -1) {
                output.write(buffer, 0, bytesRead);
                bytesRead = input.read(buffer);
            }
            //If needed, close streams.
        } finally {
            input.close();
            output.close();
        }
    }

    public static String encodeHashMapToString(HashMap<String, String> params) throws UnsupportedEncodingException {
        String result = "";
        boolean addAmp = false;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (!addAmp) {
                addAmp = true;
            }
            else {
                result += "&" ;
            }

            result += URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
        }

        return result;
    }

}
