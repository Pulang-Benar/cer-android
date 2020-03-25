package com.project.civillian.util;

import android.content.Context;
import android.os.StrictMode;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.project.civillian.model.Civil;
import cz.msebera.android.httpclient.entity.StringEntity;

public class ApiUtil {
    private static final String BASE_URL = "http://10.10.128.224:8080/siswa/";
    private static final int CONNECTION_TIMEOUT = 2000;
    private static SyncHttpClient clientSync = new SyncHttpClient();
//    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getSync(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        clientSync.setTimeout(CONNECTION_TIMEOUT);
        clientSync.setConnectTimeout(CONNECTION_TIMEOUT);
        clientSync.setMaxRetriesAndTimeout(0, CONNECTION_TIMEOUT);
        clientSync.setResponseTimeout(CONNECTION_TIMEOUT);
        clientSync.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void postSyncJson(Context context, Civil civil, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) throws UnsupportedEncodingException, JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        clientSync.setTimeout(CONNECTION_TIMEOUT);
        clientSync.setConnectTimeout(CONNECTION_TIMEOUT);
        clientSync.setMaxRetriesAndTimeout(0, CONNECTION_TIMEOUT);
        clientSync.setResponseTimeout(CONNECTION_TIMEOUT);
        clientSync.post(context, getAbsoluteUrl(url), civil.getStringEntity(), "application/json", responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        System.out.println("Call URL = "+BASE_URL + relativeUrl);
        return BASE_URL + relativeUrl;
    }

}
