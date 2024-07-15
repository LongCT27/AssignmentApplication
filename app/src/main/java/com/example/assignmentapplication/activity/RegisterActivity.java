package com.example.assignmentapplication.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.User;
import com.example.assignmentapplication.room.ShopDatabase;
import com.example.assignmentapplication.room.ShopDatabaseInstance;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    EditText emailEditText;
    EditText repasswordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button registerButton = findViewById(R.id.registerButton);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        emailEditText = findViewById(R.id.email);
        repasswordEditText = findViewById(R.id.repassword);
        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String repassword = repasswordEditText.getText().toString();
            register(username, password, email, repassword);
        });
        TextView loginTextView = findViewById(R.id.login);
        loginTextView.setOnClickListener(v -> {
            // Chuyển sang LoginActivity khi nhấn vào "Already have an account? Login"
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void register(String username, String password, String email, String repassword) {
        if(checkInput(username, password, email, repassword)) {
            // check if username and email are not exist in Db
            if(password.equals(repassword) && validateData(username, password, email, repassword)) {
                RegisterAsyncTask registerAsyncTask = new RegisterAsyncTask();
                registerAsyncTask.execute(username, password, email);
            } else {
                showToast("Password and Re-enter password are not the same");
                passwordEditText.setText("");
                repasswordEditText.setText("");
            }
        }
    }
    // check if all input is not null
    private boolean checkInput(String username, String password, String email, String repassword) {
        if(username.isEmpty() || password.isEmpty() || email.isEmpty() || repassword.isEmpty()) {
            showToast("Please enter all fields");
            usernameEditText.setText("");
            passwordEditText.setText("");
            emailEditText.setText("");
            repasswordEditText.setText("");
            return false;
        }
        return true;
    }
    // validate data for email, username, password and repassword
    private boolean validateData(String username, String password, String email, String repassword) {
        if(username.length() < 6) {
            showToast("Username must be at least 6 characters");
            usernameEditText.setText("");
            passwordEditText.setText("");
            emailEditText.setText("");
            repasswordEditText.setText("");
            return false;
        }
        if(password.length() < 6) {
            showToast("Password must be at least 6 characters");
            usernameEditText.setText("");
            passwordEditText.setText("");
            emailEditText.setText("");
            repasswordEditText.setText("");
            return false;
        }
        if(repassword.length() < 6) {
            showToast("Re-enter password must be at least 6 characters");
            usernameEditText.setText("");
            passwordEditText.setText("");
            emailEditText.setText("");
            repasswordEditText.setText("");
            return false;
        }
        if(!email.contains("@")) {
            showToast("Email is invalid");
            usernameEditText.setText("");
            passwordEditText.setText("");
            emailEditText.setText("");
            repasswordEditText.setText("");
            return false;
        }
        return true;
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private class RegisterAsyncTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... params) {
            ShopDatabase db = ShopDatabaseInstance.getDatabase(RegisterActivity.this);
            // check if email is exist in db
            User userIndb = db.shopDao().getUserByEmail(params[2]);
            if(userIndb != null) {
                return null;
            } else {
                User user = new User(params[0], params[1], params[2],0, 7152024);
                db.shopDao().insertUser(user);
                return user;
            }
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            if (user == null) {
                showToast("Email is already exist");
            } else {
                showToast("Register successfully");
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }
}