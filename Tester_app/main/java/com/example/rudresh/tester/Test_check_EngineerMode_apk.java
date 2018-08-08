package com.example.rudresh.tester;

import android.app.Activity;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Rudresh on 08-08-2018.
 */

public class Test_check_EngineerMode_apk {
    static void check_engineermode_apk(Activity activity, Button indicator_check_EngineerMode){
        File engineerModeApk_path=new File("/system/priv-app/EngineerMode.apk");

        if(engineerModeApk_path.exists()){
            Toast.makeText(activity,"EngineerMode.apk exists",Toast.LENGTH_SHORT).show();
            indicator_check_EngineerMode.setBackgroundResource(R.drawable.test_fail);
        }
        else{
            Toast.makeText(activity,"EngineerMode.apk does not exists",Toast.LENGTH_SHORT).show();
            indicator_check_EngineerMode.setBackgroundResource(R.drawable.test_ok);
        }
    }
}
