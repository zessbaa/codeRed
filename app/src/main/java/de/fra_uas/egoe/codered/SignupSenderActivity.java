package de.fra_uas.egoe.codered;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignupSenderActivity extends AppCompatActivity {

    private Button backButton, signupSenderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_sender);


        backButton = findViewById(R.id.sender_back_btn);
        signupSenderButton = findViewById(R.id.sender_signup_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        signupSenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAccountSetup();
            }
        });
    }


    void goToAccountSetup(){
        Intent goToAccount = new Intent(SignupSenderActivity.this, AccountSetupSenderActivity.class);
        startActivity(goToAccount);
        finish();
    }

}
