package com.project.civillian.service;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.project.civillian.model.Civil;
import com.project.civillian.model.Incident;
import com.project.civillian.util.ApiUtil;
import com.project.civillian.util.JsonUtil;
import com.project.civillian.util.SqlLiteUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class PanicService  {
    Context context = null;
    SqlLiteUtil sqlLiteUtil;

    public PanicService(Context context) {
        this.context = context;
        sqlLiteUtil = new SqlLiteUtil(context);
    }

    public Boolean doPanic(Incident i, String filePath){
        final Map<String, String> mapResult = new HashMap<>();
        try {
            ApiUtil.postPanicJson(context, getToken(), i, filePath, new JsonHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    System.out.println("FAILED Panic Action ("+statusCode+") = "+getStr(errorResponse));
                    mapResult.put("FAIL", "FAILED Panic Action ("+statusCode+") = "+getStr(errorResponse));
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                    System.out.println("SUCCESS Panic Action ("+statusCode+")");
                    try {
                        Map<String, Object> resMap = new HashMap<String, Object>();
                        resMap = JsonUtil.jsonToMap(jsonObject);
                        for (Map.Entry<String,Object> entry : resMap.entrySet()){
                            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                        }
                        if("OK_DEFAULT".equals(resMap.get("respStatusCode"))){
                            mapResult.put("SUCCESS", "Data added successfully");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
            mapResult.put("FAIL", e.getMessage());
        }
        return mapResult.containsKey("SUCCESS");
    }

    private String getToken(){
        Civil c = sqlLiteUtil.getCivilSqlLite();
        if(c != null) return c.getToken();
        else return null;
    }

    private String getStr(JSONObject jsonObject){
        if(jsonObject != null){
            return jsonObject.toString();
        } else {
            return "";
        }
    }


    public Boolean sendNotif(String latitude, String longitude, String alamat){
        System.out.println("call service send notif "+latitude+", "+longitude);
        final Map<String, String> mapResult = new HashMap<>();
        try {
            ApiUtil.sendNotif(context, latitude, longitude, alamat, new JsonHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    System.out.println("FAILED send notif ("+statusCode+") = "+getStr(errorResponse));
                    mapResult.put("FAIL", "FAILED send notif ("+statusCode+") = "+getStr(errorResponse));
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                    System.out.println("SUCCESS send notif ("+statusCode+")");
                    try {
                        Map<String, Object> resMap = new HashMap<String, Object>();
                        resMap = JsonUtil.jsonToMap(jsonObject);
                        for (Map.Entry<String,Object> entry : resMap.entrySet()){
                            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                        }
                        if("OK_DEFAULT".equals(resMap.get("respStatusCode"))){
                            mapResult.put("SUCCESS", "Data added successfully");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
            mapResult.put("FAIL", e.getMessage());
        }
        return mapResult.containsKey("SUCCESS");
    }

}
