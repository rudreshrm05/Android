package com.example.rudresh.tester;

import android.content.pm.ApplicationInfo;
import android.widget.Button;
import java.util.List;

/**
 * Created by Rudresh on 02-08-2018.
 */

public class Test_basic_apps{
    static String[] test_basic_apps(String[] package_names, List<ApplicationInfo> packages, Button indicator_basic_apps){
        int app_count=0, mpi=0;
        boolean isPackageExist;
        String[] missing_packages=new String[8];

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


                if(app_count==8)indicator_basic_apps.setBackgroundResource(R.drawable.test_ok);
                else indicator_basic_apps.setBackgroundResource(R.drawable.test_fail);

        return missing_packages;
    }
}