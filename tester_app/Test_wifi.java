package com.example.rudresh.tester;

import android.app.Activity;
import android.net.wifi.WifiManager;

/**
 * Created by Rudresh on 02-08-2018.
 */

public class Test_wifi extends Activity {

   boolean test_wifi(WifiManager wifi){
       if(wifi==null){
           return false;
       }
       else {
           wifi.setWifiEnabled(true);
           if (wifi.isWifiEnabled()) {
               return true;
           } else {
               return false;
           }
       }
    }

}
