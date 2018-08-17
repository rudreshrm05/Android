package com.example.rudresh.tester;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.System.exit;

/**
 * Created by Rudresh on 02-08-2018.
 */

public class Test_bluetooth extends Activity {

    static BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
    IntentFilter filter =new IntentFilter();
    static Button indicator_bluetooth_enable, indicator_bluetooth_pair, indicator_bluetooth_play, indicator_bluetooth_disable;
    LinearLayout bluetooth_enable_option, bluetooth_pair_option, bluetooth_play_option, bluetooth_disable_option;
    MediaPlayer music;
    static boolean bluetooth_support=false;
    static String device_name="TEST_SPEAKER";

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                if(state==BluetoothAdapter.STATE_ON || state==BluetoothAdapter.STATE_TURNING_ON){
                    indicator_bluetooth_enable.setBackgroundResource(R.drawable.test_ok);
                }
                if(state==BluetoothAdapter.STATE_OFF || state==BluetoothAdapter.STATE_TURNING_OFF){
                    indicator_bluetooth_disable.setBackgroundResource(R.drawable.test_ok);
                }
            }

            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device.getName().equalsIgnoreCase(device_name)){
                    device.createBond();
                }
            }

            if(action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)){
                if(isPaired()) {
                    indicator_bluetooth_pair.setBackgroundResource(R.drawable.test_ok);
                }
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_bluetooth);

        bluetooth_enable_option=(LinearLayout)findViewById(R.id.bluetooth_enable_option);
        indicator_bluetooth_enable=(Button)findViewById(R.id.indicator_bluetooth_enable);
        bluetooth_pair_option=(LinearLayout)findViewById(R.id.bluetooth_pair_option);
        indicator_bluetooth_pair=(Button)findViewById(R.id.indicator_bluetooth_pair);
        bluetooth_play_option=(LinearLayout)findViewById(R.id.bluetooth_play_option);
        indicator_bluetooth_play=(Button)findViewById(R.id.indicator_bluetooth_play);
        bluetooth_disable_option=(LinearLayout)findViewById(R.id.bluetooth_disable_option);
        indicator_bluetooth_disable=(Button)findViewById(R.id.indicator_bluetooth_disable);

        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);

        if(bluetoothAdapter==null){
            indicator_bluetooth_enable.setBackgroundResource(R.drawable.test_fail);
            bluetooth_support=false;
           try{
               Create_result_xml.create_result_xml(Test_bluetooth.this, "TEST_BLUETOOTH", "FAIL", "Device does not support bluetooth");
           }catch(Exception e){exit(1);}
        }
        else{
            bluetooth_support=true;
        }

        AlertDialog.Builder builder=new AlertDialog.Builder(Test_bluetooth.this);

        builder.setCancelable(true);
        builder.setTitle("Test Requirements");
        builder.setMessage("You should have a bluetooth speaker with name 'TEST_SPEAKER'");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();

        bluetooth_enable_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bluetoothAdapter.isEnabled()){
                    indicator_bluetooth_enable.setBackgroundResource(R.drawable.test_ok);
                }
                else{

                    if(bluetooth_support) {
                        bluetoothAdapter.enable();
                    }
                    else{
                        indicator_bluetooth_enable.setBackgroundResource(R.drawable.test_fail);
                        Toast.makeText(Test_bluetooth.this,"Device does not support bluetooth",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        bluetooth_disable_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bluetoothAdapter.isEnabled()){
                    indicator_bluetooth_disable.setBackgroundResource(R.drawable.test_ok);
                }
                else{
                    bluetoothAdapter.disable();
                }
            }
        });

        bluetooth_pair_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bluetooth_support) {
                    filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
                    filter.addAction(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(mReceiver, filter);

                    if(isPaired()){
                        indicator_bluetooth_pair.setBackgroundResource(R.drawable.test_ok);
                    }
                    else {
                        bluetoothAdapter.startDiscovery();
                        Toast.makeText(Test_bluetooth.this,"Searching device...",Toast.LENGTH_SHORT).show();
                        Thread thread=new Thread(){
                            public void run(){
                                try {
                                    sleep(10000);
                                }catch(Exception e){exit(1);}
                                finally{
                                        if(!isPaired()){
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    indicator_bluetooth_pair.setBackgroundResource(R.drawable.test_fail);
                                                }
                                            });
                                            unregisterReceiver(mReceiver);
                                            try{
                                                Create_result_xml.create_result_xml(Test_bluetooth.this, "TEST_BLUETOOTH", "FAIL", "Unable to pair with the device");
                                            }catch(Exception e){exit(1);}
                                        }
                                }
                            }
                        };thread.start();
                    }
                }
                else{
                    Toast.makeText(Test_bluetooth.this,"Device does not support bluetooth",Toast.LENGTH_SHORT).show();
                }
            }
        });

        bluetooth_play_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bluetooth_support) {
                    if(isPaired()) {
                        music = MediaPlayer.create(Test_bluetooth.this, R.raw.speaker_test);

                        music.start();

                        AlertDialog.Builder builder = new AlertDialog.Builder(Test_bluetooth.this);
                        builder.setCancelable(true);
                        builder.setTitle("Result");
                        builder.setMessage("Are you able to hear the music through the bluetooth speaker?");

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                indicator_bluetooth_play.setBackgroundResource(R.drawable.test_fail);
                                try{
                                    Create_result_xml.create_result_xml(Test_bluetooth.this, "TEST_BLUETOOTH", "FAIL", "User could not hear the music through the bluetooth test speaker, connection failed with the bluetooth speaker");
                                }catch(Exception e){exit(1);}
                            }
                        });

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                indicator_bluetooth_play.setBackgroundResource(R.drawable.test_ok);
                                try{
                                    Create_result_xml.create_result_xml(Test_bluetooth.this, "TEST_BLUETOOTH", "PASS", "");
                                }catch(Exception e){exit(1);}
                            }
                        });
                        builder.show();
                    }
                    else{
                        AlertDialog.Builder builder=new AlertDialog.Builder(Test_bluetooth.this);

                        builder.setCancelable(true);
                        builder.setTitle("Device not conected");
                        builder.setMessage("wait for few seconds or check whether the bluetooth device name is 'TEST_SPEAKER'");

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        builder.show();
                    }
                }
                else{
                    Toast.makeText(Test_bluetooth.this,"Device does not support bluetooth",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

   static boolean isPaired() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        List<String> pairedDevicesList = new ArrayList<String>();
        for (BluetoothDevice bt : pairedDevices)
            pairedDevicesList.add(bt.getName());

        if(pairedDevicesList.contains(device_name)){
            return true;
        }
        else{
            return  false;
        }
    }

    static void setPair_fail(){
        indicator_bluetooth_pair.setBackgroundResource(R.drawable.test_fail);
    }
}



