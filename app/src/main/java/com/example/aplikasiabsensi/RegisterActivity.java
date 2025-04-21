package com.example.aplikasiabsensi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiabsensi.utils.DataManager;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;
    private TextView tvLogin;

    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi view
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        // Inisialisasi data manager
        dataManager = new DataManager(this);

        // Register button click listener
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                // Validasi input
                if (TextUtils.isEmpty(username)) {
                    etUsername.setError("Username tidak boleh kosong");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Email tidak boleh kosong");
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setError("Format email tidak valid");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Password tidak boleh kosong");
                    return;
                }

                if (password.length() < 6) {
                    etPassword.setError("Password minimal 6 karakter");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    etConfirmPassword.setError("Password tidak cocok");
                    return;
                }

                // Simpan user
                boolean success = dataManager.saveUser(username, email, password);

                if (success) {
                    // Berhasil register
                    Toast.makeText(RegisterActivity.this, "Registrasi berhasil! Silakan login", Toast.LENGTH_SHORT).show();

                    // Kembali ke LoginActivity
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Gagal register
                    Toast.makeText(RegisterActivity.this, "Username sudah digunakan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Login text click listener
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kembali ke LoginActivity
                finish();
            }
        });
    }
}