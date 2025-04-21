package com.example.aplikasiabsensi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiabsensi.utils.DataManager;
import com.example.aplikasiabsensi.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    private DataManager dataManager;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inisialisasi view
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Inisialisasi data manager dan session manager
        dataManager = new DataManager(this);
        sessionManager = new SessionManager(this);

        // Cek apakah sudah login
        if (sessionManager.isLoggedIn()) {
            // Jika sudah login, langsung ke AbsensiListActivity
            Intent intent = new Intent(LoginActivity.this, AbsensiListActivity.class);
            startActivity(intent);
            finish();
        }

        // Login button click listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Validasi input
                if (TextUtils.isEmpty(username)) {
                    etUsername.setError("Username tidak boleh kosong");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Password tidak boleh kosong");
                    return;
                }

                // Verifikasi user
                if (dataManager.verifyUser(username, password)) {
                    // Berhasil login
                    // Simpan session
                    for (DataManager.User user : dataManager.getUsers()) {
                        if (user.getUsername().equals(username)) {
                            sessionManager.createLoginSession(username, user.getEmail());
                            break;
                        }
                    }

                    Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();

                    // Pindah ke AbsensiListActivity
                    Intent intent = new Intent(LoginActivity.this, AbsensiListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Gagal login
                    Toast.makeText(LoginActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Register text click listener
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke RegisterActivity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}