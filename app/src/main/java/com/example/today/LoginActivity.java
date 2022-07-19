package com.example.today;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.today.models.User;
import com.example.today.validation.LoginValidator;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public void clearFields() {
        EditText username = findViewById(R.id.login_username_et_id);
        EditText password = findViewById(R.id.login_password_et_id);

        username.setText("");
        password.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences("com.example.today", Context.MODE_PRIVATE);

        // Create demo account
        if (User.getByUsername(this,"demo") == null) {
            User user = new User();
            user.setUsername("demo");
            user.setPassword("test");
            user.insert(this);
        } else {
            // Set password to test when changed.
            User user = User.getByUsername(this, "demo");
            user.setPassword("test");
            user.update(this);
        }
    }

    public void gotoCreateAccount(View view){
        clearFields();
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        EditText username = findViewById(R.id.login_username_et_id);
        EditText password = findViewById(R.id.login_password_et_id);

        LoginValidator loginValidator = new LoginValidator();

        if (loginValidator.loginValidation(this, username.getText().toString(), password.getText().toString())) {
            User user = User.getByUsername(this, username.getText().toString());

            editor = preferences.edit();
            editor.putInt("userId", user.getID());
            editor.apply();

            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);

            clearFields();
            Toast.makeText(this, getString(R.string.login_welcomeBack_info_toast), Toast.LENGTH_SHORT).show();
        } else {
            clearFields();
            Toast.makeText(this, getString(R.string.login_incorrect_error_toast), Toast.LENGTH_LONG).show();
        }
    }
}
