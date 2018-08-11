package com.example.rudresh.tester;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.widget.Button;

import static java.lang.System.exit;

/**
 * Created by Rudresh on 02-08-2018.
 */

public class Test_wifi extends Activity {

   static void test_wifi(Activity activity, WifiManager wifi, Button indicator_wifi){

       if(wifi==null){
           indicator_wifi.setBackgroundResource(R.drawable.test_fail);
           try{
               Create_result_xml.create_result_xml(activity, "TEST_WIFI", "FAIL", "Device does not support WI-FI");
           }catch(Exception e){exit(1);}
       }
       else {
           wifi.setWifiEnabled(true);

           if (wifi.isWifiEnabled()) {
               indicator_wifi.setBackgroundResource(R.drawable.test_ok);
               try{
                   Create_result_xml.create_result_xml(activity, "TEST_WIFI", "PASS", "");
               }catch(Exception e){exit(1);}
           } else {
               indicator_wifi.setBackgroundResource(R.drawable.test_fail);
               try{
                   Create_result_xml.create_result_xml(activity, "TEST_WIFI", "FAIL", "Wifi not enabled");
               }catch(Exception e){exit(1);}
           }
       }
    }
}
