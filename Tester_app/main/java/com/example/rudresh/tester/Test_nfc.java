package com.example.rudresh.tester;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Rudresh on 02-08-2018.
 */

public class Test_nfc {
   static void test_nfc(NfcAdapter adapter, Button indicator_nfc, Activity MainActivity){

        if(adapter==null){
            indicator_nfc.setBackgroundResource(R.drawable.test_fail);
            Toast.makeText(MainActivity, "Device does not support NFC",Toast.LENGTH_SHORT).show();
        }
        else {
            indicator_nfc.setBackgroundResource(R.drawable.test_ok);
        }
    }
}
