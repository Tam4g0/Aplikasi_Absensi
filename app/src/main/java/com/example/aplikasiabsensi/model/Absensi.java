package com.example.aplikasiabsensi.model;

public class Absensi {
    private String userId;
    private long timestamp;
    private String tanggal;
    private String waktu;

    public Absensi() {
    }

    public Absensi(String userId, long timestamp, String tanggal, String waktu) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.tanggal = tanggal;
        this.waktu = waktu;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}