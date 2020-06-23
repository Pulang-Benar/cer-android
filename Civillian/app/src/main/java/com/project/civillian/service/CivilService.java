package com.project.civillian.service;

import android.content.Context;

import com.project.civillian.model.Civil;
import com.project.civillian.util.SqlLiteUtil;

public class CivilService {
    Context context = null;
    SqlLiteUtil sqlLiteUtil;

    public CivilService(Context context) {
        this.context = context;
        sqlLiteUtil = new SqlLiteUtil(context);
    }

    public Civil getCivilLogin(){
        //GET CIVIL FROM API FIRST
        //THEN
        return sqlLiteUtil.getCivilSqlLite();
    }

    public Integer updateCivil(Civil civil){
        return sqlLiteUtil.updateSqlLite(civil);
    }

}
