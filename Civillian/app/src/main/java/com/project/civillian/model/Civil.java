package com.project.civillian.model;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import cz.msebera.android.httpclient.entity.StringEntity;

public class Civil {
    private String nik;
    private String email;
    private String nama;
    private String gender;
    private Date tanggalLahir;
    private String tempatLahir;
    private String alamat;
    private String telp;
    private String telpRef;
    private String username;
    private String password;
    private String token;

    public Civil(){

    }

    public Civil(String nik, String email, String nama, String gender, Date tanggalLahir, String tempatLahir, String alamat, String telp, String telpRef, String username, String password, String token) {
        this.nik=nik;
        this.email=email;
        this.nama=nama;
        this.gender=gender;
        this.tanggalLahir=tanggalLahir;
        this.tempatLahir=tempatLahir;
        this.alamat=alamat;
        this.telp=telp;
        this.telpRef=telpRef;
        this.username = username;
        this.password=password;
        this.token = token;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Civil{" +
                "nik='" + nik + '\'' +
                ", email='" + email + '\'' +
                ", nama='" + nama + '\'' +
                ", gender='" + gender + '\'' +
                ", tanggalLahir=" + tanggalLahir +
                ", tempatLahir='" + tempatLahir + '\'' +
                ", alamat='" + alamat + '\'' +
                ", telp='" + telp + '\'' +
                ", telpRef='" + telpRef + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
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
