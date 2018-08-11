package com.example.rudresh.tester;

import android.app.Activity;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import static java.lang.System.exit;

/**
 * Created by Rudresh on 08-08-2018.
 */

public class Test_check_EngineerMode_apk {
    static void check_engineermode_apk(Activity activity, Button indicator_check_EngineerMode){
        File engineerModeApk_path=new File("/system/priv-app/EngineerMode.apk");

        if(engineerModeApk_path.exists()){
            Toast.makeText(activity,"EngineerMode.apk exists",Toast.LENGTH_SHORT).show();
            indicator_check_EngineerMode.setBackgroundResource(R.drawable.test_fail);
            try{
                Create_result_xml.create_result_xml(activity, "TEST_CHECK_ENGINEER_MOD_APK", "FAIL", "The EngineerMode.apk is present");
            }catch(Exception e){exit(1);}
        }
        else{
            Toast.makeText(activity,"EngineerMode.apk does not exists",Toast.LENGTH_SHORT).show();
            indicator_check_EngineerMode.setBackgroundResource(R.drawable.test_ok);
            try{
                Create_result_xml.create_result_xml(activity, "TEST_CHECK_ENGINEER_MOD_APK", "PASS", "");
            }catch(Exception e){exit(1);}
        }
    }
}
