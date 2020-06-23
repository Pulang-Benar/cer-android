package com.project.civillian.model;

public class Incident {
    Integer id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
