package com.fegcocco.emovebackend.dto;

public class GeocodingDTO {
    private String address;
    private Double latitude;
    private Double longitude;
    private String displayName;

    public GeocodingDTO() {
    }

    public GeocodingDTO(String address, Double latitude, Double longitude, String displayName) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.displayName = displayName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}