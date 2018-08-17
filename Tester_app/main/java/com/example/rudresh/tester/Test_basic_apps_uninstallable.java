package com.example.rudresh.tester;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

/**
 * Created by Rudresh on 03-08-2018.
 */

public class Test_basic_apps_uninstallable {
    static String[] uninstallable_ba=new String[8];
    static int i=0;
    static List<ApplicationInfo> basic_packages=new ArrayList<>();
    static String uninstallable_basic_apps="";

    static void test_basic_apps_uninstallable(Activity activity,String package_names[], List<ApplicationInfo> all_packages, Button indicator_basic_apps_uninstallable){

        Create_logCat.create_logCat("basicAppsUninstallable", activity.getString(R.string.Tester_logdir_path));

        int count_non_uninstallable_ba=0;

        for(int i=0;i<8;i++) {
            for (ApplicationInfo packageInfo : all_packages) {

                if (package_names[i].equals(packageInfo.packageName)) {
                    basic_packages.add(packageInfo);
                }
            }
        }

        for(ApplicationInfo pinfo : basic_packages){

            if((ApplicationInfo.FLAG_SYSTEM & pinfo.flags)!=0 & (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP & pinfo.FLAG_UPDATED_SYSTEM_APP)!=0){
                count_non_uninstallable_ba++;
            }
            else{
                uninstallable_ba[i]=pinfo.packageName;
                i++;
            }
        }

        for(int i=0; uninstallable_ba[i]!=null; i++){
            uninstallable_basic_apps+=" "+uninstallable_ba[i];
        }

        if(count_non_uninstallable_ba==8){
            indicator_basic_apps_uninstallable.setBackgroundResource(R.drawable.test_ok);
            try{
                Create_result_xml.create_result_xml(activity, "TEST_BASIC_APPS_UNINSTALLABLE", "PASS", "");
            }catch(Exception e){exit(1);}
        }
        else{
            indicator_basic_apps_uninstallable.setBackgroundResource(R.drawable.test_fail);
            try{
                Create_result_xml.create_result_xml(activity, "TEST_BASIC_APPS_UNINSTALLABLE", "FAIL", "Some basic apps are uninstallable:"+uninstallable_basic_apps);
            }catch(Exception e){exit(1);}
        }
    }
}
