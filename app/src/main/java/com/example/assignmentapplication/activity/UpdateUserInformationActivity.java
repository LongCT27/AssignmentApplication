package com.example.assignmentapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.User;
import com.example.assignmentapplication.room.ShopDao;
import com.example.assignmentapplication.room.ShopDatabaseInstance;
import com.example.assignmentapplication.utilities.UserHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdateUserInformationActivity extends AppCompatActivity {

    private User user = null;
    private EditText edtFullName, edtCurrentPassword, edtNewPassword, edtConfirmPassword;
    ShopDao shopDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_user_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        shopDao = ShopDatabaseInstance.getDatabase(getApplicationContext()).shopDao();
        getUserFromFile();
        edtFullName = findViewById(R.id.etusername);
        edtCurrentPassword = findViewById(R.id.et_current_password);
        edtNewPassword = findViewById(R.id.et_new_password);
        edtConfirmPassword = findViewById(R.id.et_confirm_password);
        Button saveButton = findViewById(R.id.btn_submit);
        saveButton.setOnClickListener(v -> {
            boolean canUpdate = ValidateUserInput(edtFullName.getText().toString(),
                    edtCurrentPassword.getText().toString(),
                    edtNewPassword.getText().toString(),
                    edtConfirmPassword.getText().toString(), user);
            if (canUpdate && user != null) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.getMainLooper());

                executor.execute(() -> {
                    // Background work
                    user.username = edtFullName.getText().toString();
                    user.password = edtNewPassword.getText().toString();
                    user.createdAt = System.currentTimeMillis();
                    shopDao.updateUser(user);
                    // UI Thread work
                    handler.post(() -> {
                        showToast("Update successfully");
                        Intent intent = new Intent(UpdateUserInformationActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    });
                });
            }
        });
    }

    private boolean ValidateUserInput(String string, String string1, String string2, String string3, User userInDb) {
        if (string.isEmpty() || string1.isEmpty() || string2.isEmpty() || string3.isEmpty()) {
            showToast("Please fill all fields");
            return false;
        }
        if (!string1.equals(userInDb.password)) {
            showToast("Current password is incorrect");
            return false;
        }
        if (!string2.equals(string3)) {
            showToast("New password and confirm password do not match");
            return false;
        }
        if (string.length() < 6){
            showToast("FullName must be at least 6 characters");
            return false;
        }
        if (string2.length() < 6){
            showToast("Password must be at least 6 characters");
            return false;
        }
        return true;
    }

    private void getUserFromFile(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            // Background work here
            String email = UserHelper.getUserEmailFromFile();
            if(shopDao == null) {
                shopDao = ShopDatabaseInstance.getDatabase(getApplicationContext()).shopDao();
            }
            if (email != null) {
                user = shopDao.getUserByEmail(email);
            }
            User finalUser = user;
            handler.post(() -> {
                // UI Thread work here
                if (finalUser == null) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Update UI with user information
                    edtFullName.setText(finalUser.username);
                }
            });
        });
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}