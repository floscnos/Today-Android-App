package com.example.today;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.today.models.User;

import com.example.today.validation.LoginValidator;

public class CreateAccountActivity extends AppCompatActivity {

    public void clearFields() {
        EditText usernameField = findViewById(R.id.createAccount_username_et_id);
        EditText password1Field = findViewById(R.id.createAccount_password1_et_id);
        EditText password2Field = findViewById(R.id.createAccount_password2_et_id);

        usernameField.setText("");
        password1Field.setText("");
        password2Field.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void createAccount(View view) {
        EditText usernameField = findViewById(R.id.createAccount_username_et_id);
        EditText password1Field = findViewById(R.id.createAccount_password1_et_id);
        EditText password2Field = findViewById(R.id.createAccount_password2_et_id);

        String username = usernameField.getText().toString();
        String password1 = password1Field.getText().toString();
        String password2 = password2Field.getText().toString();

        LoginValidator loginValidator = new LoginValidator();

        Boolean valid = true;
        String error = "";

        if (!loginValidator.userRegistrationValidation(username)) {
            valid = false;
            error = getString(R.string.createAccount_usernameInvalid_error_toast);
        }
        if (valid) {
            if (User.getByUsername(this, username) != null) {
                valid = false;
                error = getString(R.string.createAccount_usernameInvalid_error_toast);
            }
        }

        System.out.println(loginValidator.passwordRegistrationValidation(password1, password2));

        if (valid) {
            if (!loginValidator.passwordRegistrationValidation(password1, password2)) {
                valid = false;
                error = getString(R.string.createAccount_passwordInvalid_error_toast);
            }
        }
        if (valid) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password1);

            user.insert(this);

            finish();
            clearFields();
        } else {
            clearFields();
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
    }
}