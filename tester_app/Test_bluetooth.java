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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.System.exit;

/**
 * Created by Rudresh on 02-08-2018.
 */

public class Test_bluetooth extends Activity {

    BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    Button button_bluetooth_test, indicator_bluetooth_test;
    MediaPlayer music;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
             if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //Toast.makeText(Test_bluetooth.this,""+device.getName(),Toast.LENGTH_SHORT).show();
                 if(device.getName().equalsIgnoreCase("TEST_SPEAKER")){
                     pair_play(device);
                 }
             }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_bluetooth);

        if(!bluetoothAdapter.isEnabled())bluetoothAdapter.enable();

        button_bluetooth_test=(Button)findViewById(R.id.button_bluetooth_test);

        indicator_bluetooth_test=(Button)findViewById(R.id.indicator_bluetooth);

        filter.addAction(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

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


        button_bluetooth_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothAdapter.startDiscovery();
            }
        });
    }

    void pair_play(BluetoothDevice device) {
        try{
        device.createBond();
        music=MediaPlayer.create(Test_bluetooth.this,R.raw.speaker_test);
        music.start();
                  AlertDialog.Builder builder = new AlertDialog.Builder(Test_bluetooth.this);

                  builder.setCancelable(true);
                  builder.setTitle("Result");
                  builder.setMessage("Are you able to hear the music through the bluetooth speaker?");

                  builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          dialogInterface.cancel();
                          Toast.makeText(Test_bluetooth.this,"test ok",Toast.LENGTH_SHORT).show();
                      }
                  });

                  builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          Toast.makeText(Test_bluetooth.this,"test failed",Toast.LENGTH_SHORT).show();
                      }
                  });builder.show();
            }catch (Exception e){Toast.makeText(Test_bluetooth.this,""+e,Toast.LENGTH_SHORT).show();
        }
    }
}
