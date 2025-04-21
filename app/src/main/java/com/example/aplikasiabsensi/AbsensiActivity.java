package com.example.aplikasiabsensi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiabsensi.utils.DataManager;
import com.example.aplikasiabsensi.utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AbsensiActivity extends AppCompatActivity {
    private TextView tvCurrentDate;
    private TextView tvCurrentTime;
    private Button btnCheckIn;

    private SessionManager sessionManager;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi);

        // Inisialisasi view
        tvCurrentDate = findViewById(R.id.tvCurrentDate);
        tvCurrentTime = findViewById(R.id.tvCurrentTime);
        btnCheckIn = findViewById(R.id.btnCheckIn);

        // Inisialisasi session manager dan data manager
        sessionManager = new SessionManager(this);
        dataManager = new DataManager(this);

        // Cek apakah sudah login
        if (!sessionManager.isLoggedIn()) {
            finish();
            return;
        }

        // Set current date and time
        updateDateTime();

        // Check-in button click listener
        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simpan absensi
                String username = sessionManager.getUsername();
                dataManager.saveAbsensi(username);

                // Tampilkan pesan sukses
                Toast.makeText(AbsensiActivity.this, "Check-in berhasil!", Toast.LENGTH_SHORT).show();

                // Kembali ke activity sebelumnya
                finish();
            }
        });
    }

    private void updateDateTime() {
        // Get current date and time
        Date now = new Date();

        // Format date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String tanggal = dateFormat.format(now);
        tvCurrentDate.setText("Tanggal: " + tanggal);

        // Format time
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String waktu = timeFormat.format(now);
        tvCurrentTime.setText("Waktu: " + waktu);
    }
}