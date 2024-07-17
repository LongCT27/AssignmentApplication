package com.example.assignmentapplication.mangeUserActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.User;
import com.example.assignmentapplication.room.ShopDatabaseSingleton;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddUserActivity extends AppCompatActivity {

    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,14}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
    Spinner spinner;
    EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;

    TextInputLayout inputLayoutUsername, inputLayoutEmail, inputLayoutPassword, inputLayoutConfirmPassword;

    MaterialButton buttonBack;

    Button buttonCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initEditTexts();
        initSpinner();
        initButtons();

        buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initButtons() {
        buttonCreate = findViewById(R.id.button_create_user);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetErrors();
                if (isValidUser()){
                    User user = new User();
                    user.username = editTextUsername.getText().toString();
                    user.email = editTextEmail.getText().toString();
                    user.password = editTextPassword.getText().toString();
                    user.role = spinner.getSelectedItemPosition();
                    user.createdAt = System.currentTimeMillis();
                    ShopDatabaseSingleton.getInstance(getApplicationContext()).shopDao().insertUser(user);
                    setResult(RESULT_OK);
                    finish();
                }
            }

        });
    }

    private void resetErrors() {
        inputLayoutUsername.setError(null);
        inputLayoutEmail.setError(null);
        inputLayoutPassword.setError(null);
        inputLayoutConfirmPassword.setError(null);
    }

    private boolean isValidUser() {
        boolean isValid = true;
        if (editTextUsername.getText().toString().isEmpty()){
            inputLayoutUsername.setError("Username is required");
            isValid = false;
        }
        if (editTextEmail.getText().toString().isEmpty()) {
            inputLayoutEmail.setError("Email is required");
            isValid = false;
        }
        if (editTextPassword.getText().toString().isEmpty()) {
            inputLayoutPassword.setError("Password is required");
            isValid = false;
        }
        if (editTextConfirmPassword.getText().toString().isEmpty()) {
            inputLayoutConfirmPassword.setError("Confirm password is required");
            isValid = false;
        }

        if(ShopDatabaseSingleton.getInstance(this).shopDao().getUserByUsername(editTextUsername.getText().toString()) != null){
            inputLayoutUsername.setError("Username already exists");
            isValid = false;
            return isValid;
        }

        if(ShopDatabaseSingleton.getInstance(this).shopDao().getUserByEmail(editTextEmail.getText().toString()) != null){
            inputLayoutEmail.setError("Email already exists");
            isValid = false;
            return isValid;
        }

        Matcher matcher = pattern.matcher(editTextPassword.getText().toString());
        if (!matcher.matches()) {
            inputLayoutPassword.setError("Password must contain at least 6 characters, one uppercase letter, one lowercase letter, one number, and one special character");
            isValid = false;
            return isValid;
        }

        if (!editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString())) {
            inputLayoutConfirmPassword.setError("Passwords do not match");
            isValid = false;
            return isValid;
        }
        return isValid;
    }

    private void initSpinner() {
        spinner = findViewById(R.id.spinner_role);
        String[] titles = {
                "Manger", "Customer"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, titles);
        spinner.setAdapter(adapter);
    }

    private void initEditTexts() {
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextUsername = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);
        editTextConfirmPassword = findViewById(R.id.edit_text_confirm_password);

        inputLayoutUsername = findViewById(R.id.input_layout_username);
        inputLayoutEmail = findViewById(R.id.input_layout_email);
        inputLayoutPassword = findViewById(R.id.input_layout_password);
        inputLayoutConfirmPassword = findViewById(R.id.input_layout_confirm_password);

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                inputLayoutPassword.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                inputLayoutConfirmPassword.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}