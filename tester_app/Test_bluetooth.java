package com.example.rudresh.tester;

import android.bluetooth.BluetoothAdapter;
import android.widget.Button;

/**
 * Created by Rudresh on 02-08-2018.
 */

public class Test_bluetooth {

    static void test_bluetooth(BluetoothAdapter BA, Button indicator_bluetooth){
        if(BA==null){
            indicator_bluetooth.setBackgroundResource(R.drawable.test_fail);
        }
        else {
            BA.enable();
            if (BA.isEnabled()) {
                indicator_bluetooth.setBackgroundResource(R.drawable.test_ok);
            }
            else {
                indicator_bluetooth.setBackgroundResource(R.drawable.test_fail);
            }
        }
    }
}
