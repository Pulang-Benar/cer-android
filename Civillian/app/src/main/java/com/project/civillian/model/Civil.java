package com.project.civillian.model;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import cz.msebera.android.httpclient.entity.StringEntity;

public class Civil {
    private Integer nik;
    private String email;
    private String nama;
    private String gender;
    private Date tanggalLahir;
    private String tempatLahir;
    private String alamat;
    private String telp;
    private String telpRef;
    private String password;

    public Civil(){

    }

    public Civil(Integer nik, String email, String nama, String gender, Date tanggalLahir, String tempatLahir, String alamat, String telp, String telpRef, String password) {
        this.nik=nik;
        this.email=email;
        this.nama=nama;
        this.gender=gender;
        this.tanggalLahir=tanggalLahir;
        this.tempatLahir=tempatLahir;
        this.alamat=alamat;
        this.telp=telp;
        this.telpRef=telpRef;
        this.password=password;
    }

    public Integer getNik() {
        return nik;
    }

    public void setNik(Integer nik) {
        this.nik = nik;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getTelpRef() {
        return telpRef;
    }

    public void setTelpRef(String telpRef) {
        this.telpRef = telpRef;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Civil{" +
                "nik=" + nik +
                ", email='" + email + '\'' +
                ", nama='" + nama + '\'' +
                ", gender='" + gender + '\'' +
                ", tanggalLahir=" + tanggalLahir +
                ", tempatLahir='" + tempatLahir + '\'' +
                ", alamat='" + alamat + '\'' +
                ", telp='" + telp + '\'' +
                ", telpRef='" + telpRef + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public StringEntity getStringEntity() throws JSONException, UnsupportedEncodingException {
        JSONObject jsonParams = new JSONObject();

//        private Integer nik;
//        private String email;
//        private String nama;
//        private String gender;
//        private Date tanggalLahir;
//        private String tempatLahir;
//        private String alamat;
//        private String telp;
//        private String telpRef;
//        private String password;
//        jsonParams.put("nik", getNik());
//        jsonParams.put("nik", siswa.getNik());
//        jsonParams.put("nik", siswa.getNik());
//        jsonParams.put("nik", siswa.getNik());
//        jsonParams.put("nik", siswa.getNik());
//        jsonParams.put("nik", siswa.getNik());
//        jsonParams.put("nik", siswa.getNik());
//        jsonParams.put("nik", siswa.getNik());
//        jsonParams.put("nik", siswa.getNik());
//        jsonParams.put("nik", siswa.getNik());
        return new StringEntity(jsonParams.toString());
    }

}
