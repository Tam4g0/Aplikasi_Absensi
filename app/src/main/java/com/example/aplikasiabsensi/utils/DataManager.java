package com.example.aplikasiabsensi.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.aplikasiabsensi.model.Absensi;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataManager {
    private static final String PREF_NAME = "AbsensiData";
    private static final String KEY_USERS = "users";
    private static final String KEY_ABSENSI = "absensi";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private Gson gson;

    public DataManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    // Untuk menyimpan data pengguna (username, password)
    public boolean saveUser(String username, String email, String password) {
        List<User> users = getUsers();

        // Cek apakah username sudah digunakan
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; // Username sudah digunakan
            }
        }

        // Tambahkan pengguna baru
        users.add(new User(username, email, password));

        // Simpan ke SharedPreferences
        String usersJson = gson.toJson(users);
        editor.putString(KEY_USERS, usersJson);
        editor.apply();

        return true;
    }

    // Untuk mengambil daftar pengguna
    public List<User> getUsers() {
        String usersJson = sharedPreferences.getString(KEY_USERS, null);
        if (usersJson == null) {
            return new ArrayList<>();
        }

        Type type = new TypeToken<List<User>>() {}.getType();
        return gson.fromJson(usersJson, type);
    }

    // Untuk verifikasi login
    public boolean verifyUser(String username, String password) {
        List<User> users = getUsers();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    // Untuk menyimpan data absensi
    public void saveAbsensi(String userId) {
        List<Absensi> absensiList = getAbsensiByUser(userId);

        // Buat objek absensi baru
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        Date now = new Date();
        String tanggal = dateFormat.format(now);
        String waktu = timeFormat.format(now);

        Absensi absensi = new Absensi(userId, now.getTime(), tanggal, waktu);

        // Tambahkan ke daftar
        absensiList.add(absensi);

        // Simpan ke SharedPreferences
        String allAbsensi = sharedPreferences.getString(KEY_ABSENSI, null);
        List<Absensi> allAbsensiList;

        if (allAbsensi == null) {
            allAbsensiList = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<Absensi>>() {}.getType();
            allAbsensiList = gson.fromJson(allAbsensi, type);
        }

        // Hapus absensi pengguna yang lama (opsional)
        List<Absensi> newAllAbsensiList = new ArrayList<>();
        for (Absensi a : allAbsensiList) {
            if (!a.getUserId().equals(userId)) {
                newAllAbsensiList.add(a);
            }
        }

        // Tambahkan absensi pengguna yang baru
        newAllAbsensiList.addAll(absensiList);

        // Simpan
        String absensiJson = gson.toJson(newAllAbsensiList);
        editor.putString(KEY_ABSENSI, absensiJson);
        editor.apply();
    }

    // Untuk mengambil daftar absensi berdasarkan userId
    public List<Absensi> getAbsensiByUser(String userId) {
        String absensiJson = sharedPreferences.getString(KEY_ABSENSI, null);

        if (absensiJson == null) {
            return new ArrayList<>();
        }

        Type type = new TypeToken<List<Absensi>>() {}.getType();
        List<Absensi> allAbsensi = gson.fromJson(absensiJson, type);

        List<Absensi> userAbsensi = new ArrayList<>();
        for (Absensi absensi : allAbsensi) {
            if (absensi.getUserId().equals(userId)) {
                userAbsensi.add(absensi);
            }
        }

        return userAbsensi;
    }

    // Kelas User untuk penyimpanan data pengguna
    public static class User {
        private String username;
        private String email;
        private String password;

        public User(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }
}