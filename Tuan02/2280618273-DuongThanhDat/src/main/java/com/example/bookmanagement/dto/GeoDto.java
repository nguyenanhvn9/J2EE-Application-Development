package com.example.bookmanagement.dto;

public class GeoDto {
    private String lat;
    private String lng;

    // Constructors
    public GeoDto() {}

    public GeoDto(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    // Getters and Setters
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "GeoDto{" +
                "lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}
