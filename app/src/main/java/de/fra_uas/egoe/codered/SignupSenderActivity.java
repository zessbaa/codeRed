package de.fra_uas.egoe.codered;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class SignupSenderActivity extends AppCompatActivity {

    //UI Elements
    private Button backButton, signupButton;
    private EditText senderEmail, senderPassword, senderConfirmPassword;
    private ProgressBar signupProgress;

    //Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_sender);

        //UI Elemetns initialization
        backButton = findViewById(R.id.sender_back_btn);
        signupButton = findViewById(R.id.sender_signup_button);
        senderEmail = findViewById(R.id.sender_email_label);
        senderPassword = findViewById(R.id.sender_password_label);
        senderConfirmPassword = findViewById(R.id.sender_confirm_password_label);
        signupProgress = findViewById(R.id.sender_progress_bar);

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //user inputs
                String email = senderEmail.getText().toString();
                String password = senderPassword.getText().toString();
                String confirmPassword = senderConfirmPassword.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)){

                    if(password.equals(confirmPassword)){

                        signupProgress.setVisibility(View.VISIBLE);
                        //create a user with firebase service
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    sendToSetup();
                                }else{
                                    String error = task.getException().getMessage();
                                    toastMessage("Error: " + error);
                                }

                                signupProgress.setVisibility(View.INVISIBLE);
                            }
                        });

                    }else{
                        toastMessage("Error: Confirm password does not match your password!");
                    }

                }else {
                    toastMessage("Error: Invalid input!");
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            goToMain();
        }
    }

    private void goToMain() {
        Intent mainIntent = new Intent(SignupSenderActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }


    private void toastMessage(String message){
        Toast.makeText(SignupSenderActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void sendToSetup() {
        Intent setupIntent = new Intent(SignupSenderActivity.this, AccountSetupSenderActivity.class);
        startActivity(setupIntent);
        finish();
    }
}
