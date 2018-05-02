package de.fra_uas.egoe.codered;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;


public class AccountSetupSenderActivity extends AppCompatActivity {

    private ProgressBar senderSetupProgress;
    private  Toolbar accountSetupToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup_sender);

        accountSetupToolbar = findViewById(R.id.sender_account_setup_toolbar);
        senderSetupProgress = findViewById(R.id.sender_setup_progress);

        setSupportActionBar(accountSetupToolbar);
        getSupportActionBar().setTitle("Account setup");

        //senderSetupProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
