package com.example.rudresh.tester;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.widget.Button;

/**
 * Created by Rudresh on 02-08-2018.
 */

public class Test_wifi extends Activity {

   static void test_wifi(WifiManager wifi, Button indicator_wifi){

       if(wifi==null){
           indicator_wifi.setBackgroundResource(R.drawable.test_fail);
       }
       else {
           wifi.setWifiEnabled(true);

           if (wifi.isWifiEnabled()) {
               indicator_wifi.setBackgroundResource(R.drawable.test_ok);
           } else {
               indicator_wifi.setBackgroundResource(R.drawable.test_fail);
           }
       }
    }
}
