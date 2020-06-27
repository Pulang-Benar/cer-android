package com.project.civillian.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.provider.Settings;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Incident {
    String id;
    String latestLatitude;
    String latestLongitude;
    String latestFormattedAddress;
    String latestProvince;
    String latestCity;
    String latestDistrict;
    String latestFileChecksum;
    String latestDeviceID;
    String latestDeviceName;

    public Incident() {
    }

    public Incident(String id, String latestLatitude, String latestLongitude, String latestFormattedAddress, String latestProvince, String latestCity, String latestDistrict, String latestFileChecksum, String latestDeviceID, String latestDeviceName) {
        this.id = id;
        this.latestLatitude = latestLatitude;
        this.latestLongitude = latestLongitude;
        this.latestFormattedAddress = latestFormattedAddress;
        this.latestProvince = latestProvince;
        this.latestCity = latestCity;
        this.latestDistrict = latestDistrict;
        this.latestFileChecksum = latestFileChecksum;
        this.latestDeviceID = latestDeviceID;
        this.latestDeviceName = latestDeviceName;
    }

    public Incident(String id, Double latestLatitude, Double latestLongitude, Context context){
        Incident i = new Incident();
        i.setId(id);
        i.setLatestLatitude(latestLatitude+"");
        i.setLatestLongitude(latestLongitude+"");
        i.setLatestFileChecksum("");
        i.setLatestDeviceID(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
        i.setLatestDeviceName(Build.MANUFACTURER+" "+Build.MODEL);
        if(latestLatitude!=null && latestLongitude!=null){
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(latestLatitude, latestLongitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!addresses.isEmpty()){
                String alamatLengkap = addresses.get(0).getAddressLine(0); //setLatestFormattedAddress
                String provinsi = addresses.get(0).getAdminArea(); //setLatestProvince
                String kota = addresses.get(0).getSubAdminArea(); //setLatestCity
                String kecamatan = addresses.get(0).getLocality(); //setLatestDistrict 1
//                String kelurahan = addresses.get(0).getSubLocality(); //setLatestDistrict 2
                System.out.println("alamatLengkap -> "+ alamatLengkap);
                System.out.println("provinsi -> "+ provinsi);
                System.out.println("kota -> "+ kota);
                System.out.println("kecamatan -> "+ kecamatan);
                i.setLatestFormattedAddress(alamatLengkap);
                i.setLatestProvince(provinsi);
                i.setLatestCity(kota);
                i.setLatestDistrict(kecamatan);
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatestLatitude() {
        return latestLatitude;
    }

    public void setLatestLatitude(String latestLatitude) {
        this.latestLatitude = latestLatitude;
    }

    public String getLatestLongitude() {
        return latestLongitude;
    }

    public void setLatestLongitude(String latestLongitude) {
        this.latestLongitude = latestLongitude;
    }

    public String getLatestFormattedAddress() {
        return latestFormattedAddress;
    }

    public void setLatestFormattedAddress(String latestFormattedAddress) {
        this.latestFormattedAddress = latestFormattedAddress;
    }

    public String getLatestProvince() {
        return latestProvince;
    }

    public void setLatestProvince(String latestProvince) {
        this.latestProvince = latestProvince;
    }

    public String getLatestCity() {
        return latestCity;
    }

    public void setLatestCity(String latestCity) {
        this.latestCity = latestCity;
    }

    public String getLatestDistrict() {
        return latestDistrict;
    }

    public void setLatestDistrict(String latestDistrict) {
        this.latestDistrict = latestDistrict;
    }

    public String getLatestFileChecksum() {
        return latestFileChecksum;
    }

    public void setLatestFileChecksum(String latestFileChecksum) {
        this.latestFileChecksum = latestFileChecksum;
    }

    public String getLatestDeviceID() {
        return latestDeviceID;
    }

    public void setLatestDeviceID(String latestDeviceID) {
        this.latestDeviceID = latestDeviceID;
    }

    public String getLatestDeviceName() {
        return latestDeviceName;
    }

    public void setLatestDeviceName(String latestDeviceName) {
        this.latestDeviceName = latestDeviceName;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "id=" + id +
                ", latestLatitude='" + latestLatitude + '\'' +
                ", latestLongitude='" + latestLongitude + '\'' +
                ", latestFormattedAddress='" + latestFormattedAddress + '\'' +
                ", latestProvince='" + latestProvince + '\'' +
                ", latestCity='" + latestCity + '\'' +
                ", latestDistrict='" + latestDistrict + '\'' +
                ", latestFileChecksum='" + latestFileChecksum + '\'' +
                ", latestDeviceID='" + latestDeviceID + '\'' +
                ", latestDeviceName='" + latestDeviceName + '\'' +
                '}';
    }

}
