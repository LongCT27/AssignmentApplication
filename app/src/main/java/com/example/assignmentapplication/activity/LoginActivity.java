package com.example.assignmentapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.User;
import com.example.assignmentapplication.room.ShopDatabase;
import com.example.assignmentapplication.utilities.UserHelper;

import com.example.assignmentapplication.room.ShopDatabaseInstance;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button loginButton = findViewById(R.id.loginButton);
        usernameEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            login(username, password);
        });
        TextView registerTextView = findViewById(R.id.register);
        registerTextView.setOnClickListener(v -> {
            // Chuyển sang RegisterActivity khi nhấn vào "Don't have an account? Register"
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        //If the user is already logged in, redirect to the Product List page immediately
        if (UserHelper.getUserIDFromFile() != -1){
            Intent intent = new Intent(LoginActivity.this, ProductListActivity.class);
            startActivity(intent);
            finish();
        }

    }

    // create login function using AsyncTask for database operations
    public void login(String username, String password) {
        if(username.isEmpty() || password.isEmpty()) {
            usernameEditText.setText("");
            passwordEditText.setText("");
            showToast("Please enter both username/email and password");
            return;
        } else {
            new LoginAsyncTask().execute(username, password);
        }
    }

    private class LoginAsyncTask extends AsyncTask<String, Void, User> {

        @Override
        protected User doInBackground(String... params) {
            String username = params[0];
            String password = params[1];

            ShopDatabase db = ShopDatabaseInstance.getDatabase(getApplicationContext());
            User user;
                user = db.shopDao().getUserByEmailAndPassword(username, password);
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);

            if (user != null) {
                // Handle successful login, for example:
                UserHelper.saveUserInfoExternal(user);
                //Redirect to ProductList page
                Intent intent = new Intent(LoginActivity.this, ProductListActivity.class);
                startActivity(intent);
                showToast("Login successful");
                finish();
            } else {
                showToast("Invalid username/email or password");
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
