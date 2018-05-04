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
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class AccountSetupSenderActivity extends AppCompatActivity {

    // UI Elements
    private ProgressBar senderSetupProgress;
    private  Toolbar accountSetupToolbar;
    private Spinner genderSpinner;
    private CircleImageView senderImage;
    private EditText senderName, senderAddress, senderBirthDate, senderPhone;
    private Button saveButton;
    private TextView senderEmail;


    //Firebase service
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private String sender_email;
    private String user_id;
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
        saveButton = findViewById(R.id.sender_setup_complete_btn);
        senderImage = findViewById(R.id.sender_image);
        senderEmail = findViewById(R.id.sender_setup_email);


        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();


        user_id = firebaseAuth.getCurrentUser().getUid();
        sender_email = firebaseAuth.getCurrentUser().getEmail();

        //set the sender email in the textview
        senderEmail.setText(sender_email);

        accountSetupToolbar = findViewById(R.id.sender_account_setup_toolbar);
        setSupportActionBar(accountSetupToolbar);
        getSupportActionBar().setTitle("Account setup");

        genderSpinner =  findViewById(R.id.sender_gender_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
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
                                .setAspectRatio(1, 1)
                                .start(AccountSetupSenderActivity.this);

                    }
                }else {

                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1, 1)
                            .start(AccountSetupSenderActivity.this);
                }
            }
        });


        //Check if the user is already exits in the database and load the information to display it
        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    if(task.getResult().exists()){
                         String name = task.getResult().getString("name");
                         String birth = task.getResult().getString("birth");
                         String gender = task.getResult().getString("gender");
                         String address = task.getResult().getString("address");
                         String phone = task.getResult().getString("phone");
                         String image = task.getResult().getString("profile_picture");

                         senderName.setText(name);
                         senderBirthDate.setText(birth);
                         senderAddress.setText(address);
                         senderPhone.setText(phone);
                         if(gender != null) {
                            int spinnerPosition = adapter.getPosition(gender);
                            genderSpinner.setSelection(spinnerPosition);
                        }

                        //Placeholder
                        RequestOptions placeholderRequest = new RequestOptions();
                         placeholderRequest.placeholder(R.mipmap.avatar);
                        Glide.with(AccountSetupSenderActivity.this).setDefaultRequestOptions(placeholderRequest).load(image).into(senderImage);



                    }


                }else {
                    String error = task.getException().getMessage();
                    toastMessage("Error: " + error);
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = senderName.getText().toString();
                final String birth = senderBirthDate.getText().toString();
                final String gender = genderSpinner.getSelectedItem().toString();
                final String address = senderAddress.getText().toString();
                final String phone = senderPhone.getText().toString();

                //Check the user inputs
                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(birth) && !TextUtils.isEmpty(gender)
                        && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(phone) && senderImageUri != null){

                    senderSetupProgress.setVisibility(View.VISIBLE);

                    //Upload the image to firebase
                    StorageReference image_path = storageReference.child("profile_images").child(user_id + ".jpg");
                    image_path.putFile(senderImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if(task.isSuccessful()){
                                //uri of the uploaded image
                                Uri download_uri = task.getResult().getDownloadUrl();

                                Map<Object, Object> userMap = new HashMap<>();
                                userMap.put("name", name);
                                userMap.put("email", sender_email);
                                userMap.put("birth", birth);
                                userMap.put("gender", gender);
                                userMap.put("address", address);
                                userMap.put("phone", phone);
                                userMap.put("profile_picture",download_uri.toString() );
                                userMap.put("is_receiver", false);
                                userMap.put("is_active", false);

                                firebaseFirestore.collection("Users").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){
                                            toastMessage("Your profile has been updated.");
                                            goToDashboard();

                                        }else {
                                            String error = task.getException().getMessage();
                                            toastMessage("Error: " + error);
                                        }
                                        senderSetupProgress.setVisibility(View.INVISIBLE);

                                    }
                                });


                            }else{
                                String error = task.getException().getMessage();
                                toastMessage("Error: " + error);
                                senderSetupProgress.setVisibility(View.INVISIBLE);

                            }



                        }
                    });

                }else {
                    toastMessage("Please fill in all the information");
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

    private void toastMessage(String message) {
        Toast.makeText(AccountSetupSenderActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void goToDashboard(){
        Intent mainIntent = new Intent(AccountSetupSenderActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
