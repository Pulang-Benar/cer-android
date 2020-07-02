package com.project.civillian.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.civillian.model.Civil;

/**
 * Created by Shoozay on 12/23/2019.
 */

public class SqlLiteUtil extends SQLiteOpenHelper {
    //Dekalarasi variabel
    public static final String DATABASE_NAME = "cer";
    public static final String TABLE_NAME = "civil";
    public static final String field_nik = "nik";
    public static final String field_email = "email";
    public static final String field_nama = "nama";
    public static final String field_gender = "gender";
    public static final String field_tanggalLahir = "tanggalLahir";
    public static final String field_tempatLahir = "tempatLahir";
    public static final String field_alamat = "alamat";
    public static final String field_telp = "telp";
    public static final String field_telpRef = "telpRef";
    public static final String field_username = "username";
    public static final String field_password = "password";
    public static final String field_token = "token";

    Context context = null;

    public SqlLiteUtil(Context context) {
        super(context, DATABASE_NAME.concat(".db"), null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Execute Query untuk membuat tabel
        sqLiteDatabase.execSQL("CREATE table IF NOT EXISTS " + TABLE_NAME +
                " ("+field_nik+" TEXT, " +
                " "+field_email+" TEXT, " +
                " "+field_nama+" TEXT, " +
                " "+field_gender+" TEXT, " +
                " "+field_tanggalLahir+" TEXT, " +
                " "+field_tempatLahir+" TEXT, " +
                " "+field_alamat+" TEXT, " +
                " "+field_telp+" TEXT, " +
                " "+field_telpRef+" TEXT, " +
                " "+field_username+" TEXT, " +
                " "+field_password+" TEXT, " +
                " "+field_token+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Execute query untuk menghapus tabel
        db.execSQL("Drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    //START USING LOCAL SQL LITE
    public Long insertSqlLite(Civil c){
        truncateSqlLite();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(field_nik, c.getNik());
        contentValues.put(field_email, c.getEmail());
        contentValues.put(field_nama, c.getNama());
        contentValues.put(field_gender, c.getGender());
        contentValues.put(field_tanggalLahir, DateUtil.dateToString(c.getTanggalLahir(), DateUtil.DATE_PATTERN_DB));
        contentValues.put(field_tempatLahir, c.getTempatLahir());
        contentValues.put(field_alamat, c.getAlamat());
        contentValues.put(field_telp, c.getTelp());
        contentValues.put(field_telpRef, c.getTelpRef());
        contentValues.put(field_username, c.getUsername());
        contentValues.put(field_password, c.getPassword());
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return result;
    }

    public Integer updateSqlLite(Civil c){
        System.out.println("updateSqlLite");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(field_nik, c.getNik());
        contentValues.put(field_email, c.getEmail());
        contentValues.put(field_nama, c.getNama());
        contentValues.put(field_gender, c.getGender());
        contentValues.put(field_tanggalLahir, DateUtil.dateToString(c.getTanggalLahir(), DateUtil.DATE_PATTERN_DB));
        contentValues.put(field_tempatLahir, c.getTempatLahir());
        contentValues.put(field_alamat, c.getAlamat());
        contentValues.put(field_telp, c.getTelp());
        contentValues.put(field_telpRef, c.getTelpRef());
        contentValues.put(field_username, c.getUsername());
        contentValues.put(field_password, c.getPassword());
        Integer rowsUpdated = db.update(TABLE_NAME, contentValues, field_username.concat(" = ?"), new String[] { c.getUsername() });
        db.close();
        return rowsUpdated;
    }

    public Integer updateToken(String username, String password, String token){
        System.out.println("UPDATE TOKEN -> "+token);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(field_username, username);
        contentValues.put(field_password, password);
        contentValues.put(field_token, token);
        Integer rowsUpdated = db.update(TABLE_NAME, contentValues, field_username.concat(" = ?"), new String[] { username });
        db.close();
        return rowsUpdated;
    }

    public Long insertToken(String username, String password, String token){
        System.out.println("INSERT TOKEN");
        truncateSqlLite();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(field_username, username);
        contentValues.put(field_password, password);
        contentValues.put(field_token, token);
        Long rowsUpdated = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return rowsUpdated;
    }

    public boolean deleteSqlLite(String nik){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, field_nik.concat(" = ?"), new String[] { nik });
        db.close();
        return true;
    }

    public void truncateSqlLite(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }

    public Civil getCivilSqlLite(){
        Civil c = new Civil();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME, null);
        while (res.moveToNext()) {
            c.setNik(res.getString(0));
            c.setEmail(res.getString(1));
            c.setNama(res.getString(2));
            c.setGender(res.getString(3));
            c.setTanggalLahir(DateUtil.StringToDate(res.getString(4), DateUtil.DATE_PATTERN_DB));
            c.setTempatLahir(res.getString(5));
            c.setAlamat(res.getString(6));
            c.setTelp(res.getString(7));
            c.setTelpRef(res.getString(8));
            c.setUsername(res.getString(9));
            c.setPassword(res.getString(10));
            c.setToken(res.getString(11));
        }
        res.close();
        db.close();
        return c;
    }
    //END USING LOCAL SQL LITE



}