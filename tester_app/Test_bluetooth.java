package com.example.rudresh.tester;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by Rudresh on 02-08-2018.
 */

public class Test_bluetooth {

    static boolean test_bluetooth(BluetoothAdapter BA){
        if(BA==null){
            return false;
        }
        else {
            BA.enable();
            if (BA.isEnabled()) {
                return true;
            }
            else {
                return false;
            }
        }
    }
}
