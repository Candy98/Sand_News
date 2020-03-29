package com.example.sovjanta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sovjanta.activities.LoginActivity;
import com.example.sovjanta.prefUtils.PrefLogin;
import com.example.sovjanta.utilities.NetworkUtility;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

public class SplashActivity extends AppCompatActivity  {
    LottieAnimationView spLoading;
    PrefLogin prefLogin;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        spLoading=findViewById(R.id.spLoading);
        spLoading.playAnimation();

        timerSet();
    }

    private void timerSet() {
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over

                prefLogin=new PrefLogin(getApplicationContext());

                if (prefLogin.getBool("IsLogin")&&NetworkUtility.getInstance(getApplicationContext()).isOnline()){

                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                } if (NetworkUtility.getInstance(getApplicationContext()).isOnline()&&!prefLogin.getBool("IsLogin")){
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                }
                if (!NetworkUtility.getInstance(getApplicationContext()).isOnline()){
                    showBuilder();
                }

            }
        }, 5000);
    }

    private void showBuilder() {
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Internet connection problem").setTitle("Error!").setCancelable(false).setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timerSet();

            }
        }).setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).show();


    }
}





