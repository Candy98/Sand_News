package com.example.sovjanta.customUi;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.sovjanta.R;
import com.google.android.material.snackbar.Snackbar;

public class CustomSnackbar {
   public void infoSnack(View v, Context c,String message){
        Snackbar sb = Snackbar.make(v, message, Snackbar.LENGTH_SHORT);
        sb.getView().setBackgroundColor(ContextCompat.getColor(c, R.color.colorPrimaryDark));
        sb.show();
    }
  public   void errorSnack(View v, Context c,String message){
        Snackbar sb = Snackbar.make(v, message, Snackbar.LENGTH_SHORT);
        sb.getView().setBackgroundColor(ContextCompat.getColor(c, R.color.RedError));
        sb.show();
    }
}
