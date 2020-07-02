package com.project.civillian.util;

import android.content.Context;
import android.os.StrictMode;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.project.civillian.model.Incident;

import org.json.JSONException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.message.BasicNameValuePair;


public class ApiUtil {
    private static final String BASE_URL = "https://cer-api.herokuapp.com";
//    private static final String BASE_URL = "http://192.168.43.160:8085";
    private static final String LOGIN_URL = "/xa/oauth/token";
    private static final String PANIC_URL = "/xa/api/panic/trx/auth/panic/v.1";
    private static final String FIREBASE_URL = "https://fcm.googleapis.com/fcm/send";
    private static final int CONNECTION_TIMEOUT = 60000;
    private static SyncHttpClient clientSync = new SyncHttpClient();

    public static void getSync(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        clientSync.setTimeout(CONNECTION_TIMEOUT);
        clientSync.setConnectTimeout(CONNECTION_TIMEOUT);
        clientSync.setMaxRetriesAndTimeout(0, CONNECTION_TIMEOUT);
        clientSync.setResponseTimeout(CONNECTION_TIMEOUT);
        clientSync.get(getAbsoluteUrl(url), params, responseHandler);
    }

    private static void setTimeout() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        clientSync.setTimeout(CONNECTION_TIMEOUT);
        clientSync.setConnectTimeout(CONNECTION_TIMEOUT);
        clientSync.setMaxRetriesAndTimeout(0, CONNECTION_TIMEOUT);
        clientSync.setResponseTimeout(CONNECTION_TIMEOUT);
    }

    public static void postPanicJson(Context context, String token, Incident i, String filePath, AsyncHttpResponseHandler responseHandler) throws JSONException, UnsupportedEncodingException {
        System.out.println("CALL postPanicJson");
        System.out.println("TOKEN = "+token);
        setTimeout();
        Header[] headers = {new BasicHeader("Authorization", "Bearer ".concat(token)),
                new BasicHeader("Accept", "application/json"),
                new BasicHeader("Accept-Language", "en-US")};
        MultipartEntityBuilder entity = MultipartEntityBuilder.create();
//        entity.addTextBody("data", "{\\\"latestLatitude\\\": \\\"-8.7113867\\\",\\\"latestLongitude\\\": \\\"115.1680439\\\",\\\"latestFormattedAddress\\\": \\\"Jl. Melasti No.86, Legian, Kuta, Kabupaten Badung, Bali 80361\\\",\\\"latestProvince\\\": \\\"Bali\\\",\\\"latestCity\\\": \\\"Kabupaten Badung\\\",\\\"latestDistrict\\\": \\\"Kuta\\\",\\\"latestFileChecksum\\\": null,\\\"latestDeviceID\\\": \\\"0123456781\\\",\\\"latestDeviceName\\\": \\\"Xiaomi\\\"}");
        entity.addTextBody("data", "{\\\"latestLatitude\\\": \\\""+i.getLatestLatitude()+"\\\",\\\"latestLongitude\\\": \\\""+i.getLatestLongitude()+"\\\",\\\"latestFormattedAddress\\\": \\\""+i.getLatestFormattedAddress()+"\\\",\\\"latestProvince\\\": \\\""+i.getLatestProvince()+"\\\",\\\"latestCity\\\": \\\""+i.getLatestCity()+"\\\",\\\"latestDistrict\\\": \\\""+i.getLatestDistrict()+"\\\",\\\"latestFileChecksum\\\": null,\\\"latestDeviceID\\\": \\\""+i.getLatestDeviceID()+"\\\",\\\"latestDeviceName\\\": \\\""+i.getLatestDeviceID()+"\\\"}");
//        entity.addBinaryBody("evidence", new File("/storage/777C-1CF6/DCIM/Camera/C360VID_20191104_215236.mp4"));
//        entity.addBinaryBody("evidence", new File("/storage/777C-1CF6/Download/image004.jpg"));
        entity.addBinaryBody("evidence", new File(filePath));
        clientSync.post(context, getAbsoluteUrl(PANIC_URL), headers, entity.build(), null, responseHandler);
    }

    public static void postLogin(Context context, String username, String password, AsyncHttpResponseHandler responseHandler) throws JSONException, UnsupportedEncodingException {
        setTimeout();
        List<NameValuePair> formData = new ArrayList<NameValuePair>();
        formData.add(new BasicNameValuePair("grant_type", "password"));
        formData.add(new BasicNameValuePair("username", username));
        formData.add(new BasicNameValuePair("client_id", "xa-core"));
        formData.add(new BasicNameValuePair("password", password));
        Header[] headers = {new BasicHeader("Authorization", "Basic eGEtY29yZTpzZWNyZXR4YTAx"), new BasicHeader("Accept", "application/json")};
        clientSync.post(context, getAbsoluteUrl(LOGIN_URL), headers, new UrlEncodedFormEntity(formData), "application/x-www-form-urlencoded", responseHandler);
    }

    public static void sendNotif(Context context, String latitude, String longitude, String alamat, AsyncHttpResponseHandler responseHandler) throws JSONException, UnsupportedEncodingException {
        System.out.println("CALL sendNotif");
        setTimeout();
        Header[] headers = {
                new BasicHeader("Authorization", "key=AAAAk4TEFVM:APA91bErfrTcoSiKt-oc7rS8dCqoN4Kl953dG7TTUJ3IEgJvcLdM1YMjB1n22cRg5XusbvbXTCVgvAxntljZbRhyugs8TkkO4Qcz6QgON_3TS6lJD32DYHaK8P_kL0iFWHvgerXWPmKf"),
                new BasicHeader("Accept", "application/json"),
                new BasicHeader("Accept-Language", "en-US")
        };

        String rawData = "{\n\t\"to\":\"/topics/allTopics\",\n\t\"content-available\":\"true\",\n\t\"notification\":{\n\t\t\"title\":\"Civillian Emergency - Help Me\",\n\t\t\"body\":\""+alamat+"\"\n\t},\n\t\"data\":{\n\t\t\"latitude\":\""+latitude+"\",\n\t\t\"longitude\":\""+longitude+"\"\n\t}\n}";
        StringEntity requestEntity = new StringEntity(
                rawData,
                ContentType.APPLICATION_JSON);

        clientSync.post(context, FIREBASE_URL, headers, requestEntity, "application/json", responseHandler);
    }


    public static void subscribeToAllTopics(){
        FirebaseMessaging.getInstance().subscribeToTopic("allTopics")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        System.out.println("SUCCESS SUBSCRIBED");
                        if (!task.isSuccessful()) {
                            System.out.println("FAILED SUBSCRIBED");
                        }
                    }
                });
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        System.out.println("Call URL = "+BASE_URL + relativeUrl);
        return BASE_URL + relativeUrl;
    }


}
