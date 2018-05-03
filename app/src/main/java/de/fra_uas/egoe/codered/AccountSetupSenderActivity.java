package de.fra_uas.egoe.codered;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;


public class AccountSetupSenderActivity extends AppCompatActivity {

    private ProgressBar senderSetupProgress;
    private  Toolbar accountSetupToolbar;
    private Spinner genderSpinner;
    private CircleImageView senderImage;
    private EditText senderName, senderAddress, senderBirthDate, senderPhone;
    private Button completeButton;
    private Uri senderImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup_sender);

        //UI Elements
        senderName = findViewById(R.id.sender_setup_name);
        senderAddress = findViewById(R.id.sender_setup_address);
        senderSetupProgress = findViewById(R.id.sender_setup_progress);
        senderPhone = findViewById(R.id.sender_setup_phone);
        senderBirthDate = findViewById(R.id.sender_setup_birth);
        completeButton = findViewById(R.id.sender_setup_complete_btn);
        senderImage = findViewById(R.id.sender_image);


        accountSetupToolbar = findViewById(R.id.sender_account_setup_toolbar);
        setSupportActionBar(accountSetupToolbar);
        getSupportActionBar().setTitle("Account setup");

        genderSpinner =  findViewById(R.id.sender_gender_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, R.layout.custom_spinner);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        genderSpinner.setAdapter(adapter);


        //get the user image
        senderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if the user has permission to access storage
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(AccountSetupSenderActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(AccountSetupSenderActivity.this, "Permission Denied.", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(AccountSetupSenderActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }else {

                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(AccountSetupSenderActivity.this);

                    }
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                senderImageUri = result.getUri();

                senderImage.setImageURI(senderImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
            }
        }
    }

}
