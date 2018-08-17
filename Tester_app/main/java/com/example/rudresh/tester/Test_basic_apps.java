package com.example.rudresh.tester;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.widget.Button;
import java.util.List;

import static java.lang.System.exit;

/**
 * Created by Rudresh on 02-08-2018.
 */

public class Test_basic_apps{
    static void test_basic_apps(Activity activity, String[] package_names, List<ApplicationInfo> packages, Button indicator_basic_apps){

        Create_logCat.create_logCat("basicApps", activity.getString(R.string.Tester_logdir_path));

        int app_count=0, mpi=0;
        boolean isPackageExist;
        String[] missing_packages=new String[8];
        String missing_pkgs="";

        for(int i=0;i<8;i++){
            isPackageExist=false;

                for (ApplicationInfo packageInfo : packages) {

                        if (package_names[i].equals(packageInfo.packageName)) {
                            isPackageExist=true;
                        }
                    }

                    if(isPackageExist){
                        app_count++;
                    }
                    else{
                        missing_packages[mpi]=package_names[i];
                        mpi++;
                    }
        }

        for(int i=0; missing_packages[i]!=null; i++){
            missing_pkgs+=" "+missing_packages[i];
        }

        if(app_count==8){
            indicator_basic_apps.setBackgroundResource(R.drawable.test_ok);
                try{
                     Create_result_xml.create_result_xml(activity, "TEST_BASIC_APPS", "PASS", "");
                    }catch(Exception e){exit(1);}
        }
        else {
            indicator_basic_apps.setBackgroundResource(R.drawable.test_fail);
            try{
                Create_result_xml.create_result_xml(activity, "TEST_BASIC_APPS", "FAIL", "Missing packages:"+missing_pkgs);
            }catch(Exception e){exit(1);}
            }
    }
}
