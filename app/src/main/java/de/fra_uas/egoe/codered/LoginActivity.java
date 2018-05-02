package de.fra_uas.egoe.codered;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class LoginActivity extends AppCompatActivity {


    //LOG TAG
    private static final String TAG = MainActivity.class.getName();

    //UI Elements
    private EditText loginEmailText, loginPwdText;
    private Button loginBtn, registerBtn;
    private ProgressBar loginProgress;

    //Firebase Authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //get the instance from firebase authentication
        mAuth = FirebaseAuth.getInstance();

        // initialization of the UI Elements
        loginEmailText = findViewById(R.id.login_email_label);
        loginPwdText = findViewById(R.id.login_pwd_label);
        loginBtn = findViewById(R.id.login_btn);
        registerBtn = findViewById(R.id.login_signup_btn);
        loginProgress = findViewById(R.id.login_progressbar);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login: ");

                //Reading the user inputs
                String email = loginEmailText.getText().toString();
                String password = loginPwdText.getText().toString();

                //Check if the user enters values
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    loginProgress.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.i(TAG, "signin onComplete method");
                            if(task.isSuccessful()) {
                                goToMain();
                            }else{
                                String error = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error: " + error, Toast.LENGTH_LONG);
                            }
                            loginProgress.setVisibility(View.INVISIBLE);
                        }
                    });

                }
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        //Check if the user is  logged in
        if(currentUser != null){
            goToMain();
        }
    }

    public void goToMain() {
        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
