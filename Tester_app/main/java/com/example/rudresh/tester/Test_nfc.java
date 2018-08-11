package com.example.rudresh.tester;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.widget.Button;
import android.widget.Toast;

import static java.lang.System.exit;

/**
 * Created by Rudresh on 02-08-2018.
 */

public class Test_nfc {
   static void test_nfc(Activity activity, NfcAdapter adapter, Button indicator_nfc){

        if(adapter==null){
            indicator_nfc.setBackgroundResource(R.drawable.test_fail);
            Toast.makeText(activity, "Device does not support NFC",Toast.LENGTH_SHORT).show();
            try{
                Create_result_xml.create_result_xml(activity, "TEST_NFC", "FAIL", "Device does not support NFC");
            }catch(Exception e){exit(1);}
        }
        else {
            indicator_nfc.setBackgroundResource(R.drawable.test_ok);
            try{
                Create_result_xml.create_result_xml(activity, "TEST_NFC", "PASS", "");
            }catch(Exception e){exit(1);}
        }
    }
}
