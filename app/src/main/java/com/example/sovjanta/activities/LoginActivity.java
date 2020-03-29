package com.example.sovjanta.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sovjanta.MainActivity;
import com.example.sovjanta.R;
import com.example.sovjanta.customUi.CustomUISnackError;
import com.example.sovjanta.customUi.ProgressDialogCustom;
import com.example.sovjanta.prefUtils.PrefLogin;
import com.example.sovjanta.utilities.NetworkUtility;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.Serializable;

import studios.codelight.smartloginlibrary.users.SmartUser;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Serializable, GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 1;
    SmartUser currentUser;
    SignInButton google_login_button;
    //GoogleApiClient mGoogleApiClient;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initGoogleAuth();

        initUI();

    }

    private void initGoogleAuth() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void initUI() {
        google_login_button = findViewById(R.id.google_login_button);

        google_login_button.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void refreshLayout() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            Toast.makeText(this, result.getSignInAccount().toString(), Toast.LENGTH_SHORT).show();
            PrefLogin login = new PrefLogin(this);
            login.setBool("IsLogin", true);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Sign in cancel", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.google_login_button: {
                if (NetworkUtility.getInstance(this).isOnline()) {

                    doSignUpWithGoogle();

                } else {
                    CustomUISnackError customUISnackError = new CustomUISnackError(this, v, "Please connect with network");
                }
                break;
            }
        }
    }

    private void doSignUpWithGoogle() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, RC_SIGN_IN);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
