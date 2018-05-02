package de.fra_uas.egoe.codered;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmailText, loginPwdText;
    private Button loginBtn, registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmailText = findViewById(R.id.login_email_label);
        loginPwdText = findViewById(R.id.login_pwd_label);
        loginBtn = findViewById(R.id.login_btn);
        registerBtn = findViewById(R.id.login_signup_btn);


    }
}
