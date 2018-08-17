package com.example.rudresh.tester;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.nfc.NfcAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import static java.lang.System.exit;

/**
 * Created by Rudresh on 02-08-2018.
 */

public class Test_nfc {
   static void test_nfc(Activity activity, NfcAdapter adapter, Button indicator_nfc){

       Create_logCat.create_logCat("TestNfc", activity.getString(R.string.Tester_logdir_path));

        if(adapter==null){
            indicator_nfc.setBackgroundResource(R.drawable.test_fail);
            AlertDialog.Builder builder=new AlertDialog.Builder(activity);

            builder.setCancelable(true);
            builder.setTitle("NFC");
            builder.setMessage("Device does not support NFC");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder.show();
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
