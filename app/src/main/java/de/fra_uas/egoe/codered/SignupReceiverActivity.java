package de.fra_uas.egoe.codered;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class SignupReceiverActivity extends AppCompatActivity {

    private Button backButton;
    private EditText senderEmail, senderPassword, senderConfirmPassword;
    private ProgressBar signupProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_receiver);


        backButton = findViewById(R.id.receiver_back_btn);
        senderEmail = findViewById(R.id.sender_email_label);
        senderPassword = findViewById(R.id.sender_password_label);
        senderConfirmPassword = findViewById(R.id.sender_confirm_password_label);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
