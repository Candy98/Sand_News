package com.example.sovjanta.customUi;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogCustom {
    ProgressDialog pd;
    public ProgressDialogCustom(Context c, String message){
        pd=new ProgressDialog(c);
        pd.setMessage(message);


    }
    public void show(){
        pd.show();
    }
   public void dismiss(){
        pd.dismiss();
    }
}
