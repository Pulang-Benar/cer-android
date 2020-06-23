package com.project.civillian.service;

import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.project.civillian.util.ApiUtil;
import com.project.civillian.util.JsonUtil;
import com.project.civillian.util.SqlLiteUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class LoginService {
    Context context = null;
    SqlLiteUtil sqlLiteUtil;

    public LoginService(Context context) {
        this.context = context;
        sqlLiteUtil = new SqlLiteUtil(context);
    }

    public Boolean doLogin(final String username, final String password){
        final Map<String, String> mapResult = new HashMap<>();
        try {
            ApiUtil.postLogin(context, username, password, new JsonHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    System.out.println("FAILED Login ("+statusCode+") = "+errorResponse.toString()+"");
                    mapResult.put("FAIL", "FAILED Login ("+statusCode+") = "+errorResponse.toString()+"");
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                    System.out.println("SUCCESS Login ("+statusCode+")");
                    try {
                        Map<String, Object> resMap = new HashMap<String, Object>();
                        resMap = JsonUtil.jsonToMap(jsonObject);
                        for (Map.Entry<String,Object> entry : resMap.entrySet()){
                            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                        }
                        Integer updateRow = sqlLiteUtil.updateToken(username, password, (String) resMap.get("access_token"));
                        System.out.println("updateRow -> "+updateRow);
                        if(updateRow == 0){
                            System.out.println("insert row -> "+sqlLiteUtil.insertToken(username, password, (String) resMap.get("access_token")));

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
        return !mapResult.containsKey("FAIL");
    }

}
