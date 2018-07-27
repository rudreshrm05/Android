package com.example.rudresh.tester;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Rudresh on 25-07-2018.
 */

public class Main_activity extends Activity {

    Button indicator_bluetooth, indicator_wifi, indicator_dual_sim;
    LinearLayout bluetooth_Option, wifi_option, dual_sim_option;
    BluetoothAdapter BA;
    WifiManager wifi;
    TelephonyInfo telephonyInfo;

    final BroadcastReceiver broad_cast_receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                if(state==BluetoothAdapter.STATE_ON){
                    indicator_bluetooth.setBackgroundResource(R.drawable.test_ok);
                }
            }
            if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
                if (state == WifiManager.WIFI_STATE_ENABLED||state == WifiManager.WIFI_STATE_ENABLING) {
                    indicator_wifi.setBackgroundResource(R.drawable.test_ok);
                }
            }
        }
    };


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        telephonyInfo = TelephonyInfo.getInstance(this);
        bluetooth_Option=(LinearLayout)findViewById(R.id.bluetooth_option);
        indicator_bluetooth=(Button)findViewById(R.id.indicator_bluetooth);
        wifi_option=(LinearLayout)findViewById(R.id.wifi_option);
        indicator_wifi=(Button)findViewById(R.id.indicator_wifi);
        dual_sim_option=(LinearLayout)findViewById(R.id.dual_sim_option);
        indicator_dual_sim=(Button)findViewById(R.id.indicator_dual_sim);

        IntentFilter filter_bluetooth=new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(broad_cast_receiver, filter_bluetooth);
        IntentFilter filter_wifi = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(broad_cast_receiver, filter_wifi);

        bluetooth_Option.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                BA = BluetoothAdapter.getDefaultAdapter();
                if(BA==null){
                    indicator_bluetooth.setBackgroundResource(R.drawable.test_fail);
                }
                else {
                    BA.enable();
                    if (BA.isEnabled()) {
                        indicator_bluetooth.setBackgroundResource(R.drawable.test_ok);
                    } else {
                        indicator_bluetooth.setBackgroundResource(R.drawable.test_fail);
                    }
                }

            }
        });

        wifi_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

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
        });

        dual_sim_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean sim1_ready=false;
                boolean sim2_ready=false;

                if(!telephonyInfo.isDualSIM()){
                    Toast.makeText(Main_activity.this,"Not a dual sim phone",Toast.LENGTH_LONG).show();
                }
                else{
                    if(telephonyInfo.isSIM1Ready()){
                        sim1_ready=true;
                    }
                    else{
                        Toast.makeText(Main_activity.this,"sim 1 not ready",Toast.LENGTH_LONG).show();
                    }
                    if(telephonyInfo.isSIM2Ready()){
                        sim2_ready=true;
                    }
                    else{
                        Toast.makeText(Main_activity.this,"sim 2 not ready",Toast.LENGTH_LONG).show();
                    }

                    if(sim1_ready&&sim2_ready){
                        indicator_dual_sim.setBackgroundResource(R.drawable.test_ok);
                    }
                    else{
                        indicator_dual_sim.setBackgroundResource(R.drawable.test_fail);
                    }

                }
            }
        });
    }
}



