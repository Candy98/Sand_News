package com.example.sovjanta.customUi;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.sovjanta.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;

public class CustomUISnackError {
    public CustomUISnackError(Context context, View view, String text){
        Snackbar sb = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        sb.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.RedError));
        sb.show();
    }
}
