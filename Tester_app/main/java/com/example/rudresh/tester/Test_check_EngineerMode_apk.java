package com.example.rudresh.tester;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import static java.lang.System.exit;

/**
 * Created by Rudresh on 08-08-2018.
 */

public class Test_check_EngineerMode_apk {
    static void check_engineermode_apk(Activity activity, Button indicator_check_EngineerMode){

        Create_logCat.create_logCat("TestEngMode", activity.getString(R.string.Tester_logdir_path));

        File engineerModeApk_path=new File("/system/priv-app/EngineerMode.apk");

        if(engineerModeApk_path.exists()){

            AlertDialog.Builder builder=new AlertDialog.Builder(activity);

            builder.setCancelable(true);
            builder.setTitle("Check EngineerMod.apk");

            builder.setMessage("The apk exists");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder.show();
            indicator_check_EngineerMode.setBackgroundResource(R.drawable.test_fail);
            try{
                Create_result_xml.create_result_xml(activity, "TEST_CHECK_ENGINEER_MOD_APK", "FAIL", "The EngineerMode.apk is present");
            }catch(Exception e){exit(1);}
        }
        else{

            AlertDialog.Builder builder=new AlertDialog.Builder(activity);

            builder.setCancelable(true);
            builder.setTitle("Check EngineerMod.apk");

            builder.setMessage("The apk does not exists");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder.show();
            indicator_check_EngineerMode.setBackgroundResource(R.drawable.test_ok);
            try{
                Create_result_xml.create_result_xml(activity, "TEST_CHECK_ENGINEER_MOD_APK", "PASS", "");
            }catch(Exception e){exit(1);}
        }
    }
}
