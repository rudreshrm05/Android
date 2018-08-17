package com.example.rudresh.tester;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;

import static java.lang.System.exit;

/**
 * Created by Rudresh on 02-08-2018.
 */

public class Test_wifi extends Activity{
    WifiManager wifi;
    LinearLayout wifi_enable_option, wifi_disable_option;
    Button indicator_wifi_enable, indicator_wifi_disable;
    static boolean TEST_WIFI_ON=false, TEST_WIFI_OFF=false;

    final BroadcastReceiver broad_cast_receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);

                if (state == WifiManager.WIFI_STATE_ENABLED||state == WifiManager.WIFI_STATE_ENABLING) {
                    indicator_wifi_enable.setBackgroundResource(R.drawable.test_ok);
                    TEST_WIFI_ON=true;
                }
                else if(state == WifiManager.WIFI_STATE_DISABLED||state == WifiManager.WIFI_STATE_DISABLING){
                    indicator_wifi_disable.setBackgroundResource(R.drawable.test_ok);
                    TEST_WIFI_OFF=true;
                }

            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_wifi);

        Create_logCat.create_logCat("TestWiFi", getString(R.string.Tester_logdir_path));

        wifi = (WifiManager) getSystemService(Main_activity.WIFI_SERVICE);
        IntentFilter filter_wifi = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(broad_cast_receiver, filter_wifi);

        wifi_enable_option=(LinearLayout)findViewById(R.id.wifi_enable_option);
        indicator_wifi_enable=(Button)findViewById(R.id.indicator_wifi_enable);
        wifi_disable_option=(LinearLayout)findViewById(R.id.wifi_disable_option);
        indicator_wifi_disable=(Button)findViewById(R.id.indicator_wifi_disable);

        if(wifi==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(Test_wifi.this);
            builder.setCancelable(true);
            builder.setTitle("Test WiFi");
            builder.setMessage("Device does not support bluetooth");

            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder.show();
            try{
                Create_result_xml.create_result_xml(Test_wifi.this, "TEST_WIFI", "FAIL", "Device does not support bluetooth");
            }catch(Exception e){exit(1);}
        }

        wifi_enable_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!wifi.isWifiEnabled()) {
                    wifi.setWifiEnabled(true);
                    Thread thread = new Thread() {
                        public void run() {
                            try {
                                sleep(3000);
                            } catch (Exception e) {
                                exit(1);
                            } finally {
                                if (!wifi.isWifiEnabled()) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            indicator_wifi_enable.setBackgroundResource(R.drawable.test_fail);
                                        }
                                    });
                                    try{
                                        Create_result_xml.create_result_xml(Test_wifi.this, "TEST_WIFI", "FAIL", "Failed to enable bluetooth");
                                    }catch(Exception e){exit(1);}
                                }
                            }
                        }
                    };thread.start();
                }
                else{
                    indicator_wifi_enable.setBackgroundResource(R.drawable.test_ok);
                    TEST_WIFI_ON=true;
                }
            }
        });

        wifi_disable_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wifi.isWifiEnabled()) {
                    wifi.setWifiEnabled(false);
                    Thread thread = new Thread() {
                        public void run() {
                            try {
                                sleep(3000);
                            } catch (Exception e) {
                                exit(1);
                            } finally {
                                if (wifi.isWifiEnabled()) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            indicator_wifi_disable.setBackgroundResource(R.drawable.test_fail);
                                        }
                                    });
                                    try{
                                        Create_result_xml.create_result_xml(Test_wifi.this, "TEST_WIFI", "FAIL", "Failed to disable WiFi");
                                    }catch(Exception e){exit(1);}
                                }
                            }
                        }
                    };thread.start();
                }
                else{
                    indicator_wifi_disable.setBackgroundResource(R.drawable.test_ok);
                    TEST_WIFI_OFF=true;
                }

                if(TEST_WIFI_ON && TEST_WIFI_OFF){
                    try{
                        Create_result_xml.create_result_xml(Test_wifi.this, "TEST_WIFI", "PASS", "");
                    }catch(Exception e){exit(1);}
                }
            }
        });
    }

}
