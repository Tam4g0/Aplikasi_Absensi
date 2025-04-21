package com.example.aplikasiabsensi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aplikasiabsensi.adapter.AbsensiAdapter;
import com.example.aplikasiabsensi.model.Absensi;
import com.example.aplikasiabsensi.utils.DataManager;
import com.example.aplikasiabsensi.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AbsensiListActivity extends AppCompatActivity {
    private TextView tvWelcome;
    private RecyclerView rvAbsensi;
    private FloatingActionButton fabAddAbsensi;
    private FloatingActionButton fabLogout;

    private SessionManager sessionManager;
    private DataManager dataManager;
    private AbsensiAdapter absensiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi_list);

        // Inisialisasi view
        tvWelcome = findViewById(R.id.tvWelcome);
        rvAbsensi = findViewById(R.id.rvAbsensi);
        fabAddAbsensi = findViewById(R.id.fabAddAbsensi);
        fabLogout = findViewById(R.id.fabLogout);

        // Inisialisasi session manager dan data manager
        sessionManager = new SessionManager(this);
        dataManager = new DataManager(this);

        // Cek apakah sudah login
        if (!sessionManager.isLoggedIn()) {
            // Jika belum login, kembali ke LoginActivity
            Intent intent = new Intent(AbsensiListActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Set welcome message
        String username = sessionManager.getUsername();
        tvWelcome.setText("Selamat datang, " + username);

        // Setup RecyclerView
        setupRecyclerView();

        // Add absensi button click listener
        fabAddAbsensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buka AbsensiActivity
                Intent intent = new Intent(AbsensiListActivity.this, AbsensiActivity.class);
                startActivity(intent);
            }
        });

        // Logout button click listener
        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout user
                sessionManager.logoutUser();

                // Kembali ke LoginActivity
                Intent intent = new Intent(AbsensiListActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload data when resuming activity
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        // Get absensi data
        List<Absensi> absensiList = dataManager.getAbsensiByUser(sessionManager.getUsername());

        // Setup adapter
        absensiAdapter = new AbsensiAdapter(absensiList);

        // Setup RecyclerView
        rvAbsensi.setLayoutManager(new LinearLayoutManager(this));
        rvAbsensi.setAdapter(absensiAdapter);
    }
}