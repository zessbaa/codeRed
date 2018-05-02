package de.fra_uas.egoe.codered;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseRoleActivity extends AppCompatActivity {

    private Button backButton, senderButton, receiverButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);

        backButton = findViewById(R.id.choose_role_back_btn);
        senderButton = findViewById(R.id.sender_btn);
        receiverButton = findViewById(R.id.receiver_btn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        senderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSingUpSender();
            }
        });

        receiverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSingUpReceiver();
            }
        });
    }

    void goToSingUpSender(){

        Intent goToSender = new Intent(ChooseRoleActivity.this, SignupSenderActivity.class);
        startActivity(goToSender);
    }

    void goToSingUpReceiver(){

        Intent goToreceiver = new Intent(ChooseRoleActivity.this, SignupReceiverActivity.class);
        startActivity(goToreceiver);
    }
}
