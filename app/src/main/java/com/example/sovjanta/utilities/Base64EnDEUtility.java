package com.example.sovjanta.utilities;

import android.util.Base64;

public class Base64EnDEUtility {

    public String decode(String enString) {
        String orig = enString;
        byte[] decoded = Base64.decode(orig,0);


        return new String(decoded);
    }

}
