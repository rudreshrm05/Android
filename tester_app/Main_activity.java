package com.example.rudresh.tester;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import static java.lang.System.exit;

/**
 * Created by Rudresh on 25-07-2018.
 */

public class Main_activity extends Activity{

    Button indicator_bluetooth, indicator_wifi, indicator_dual_sim, indicator_signal_strength, indicator_nfc, indicator_headphone_jack, indicator_basic_apps, indicator_speaker;
    LinearLayout bluetooth_Option, wifi_option, dual_sim_option, signal_strength_option, nfc_option, headphone_jack_option, basic_apps_option, speaker_option;
    BluetoothAdapter BA;
    WifiManager wifi;
    AudioManager audioManager;
    TelephonyInfo telephonyInfo;
    MediaPlayer test_audio_file;
    MediaRecorder test_sound;
    public int signalStrengthDbm = 0;
    public int signalStrengthAsuLevel = 0;
    int bufferSize,j,i;
    double lastLevel, audio_samples[]=new double[6];
    AudioRecord audio;
    Thread thread;

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
            if(action.equals(AudioManager.ACTION_HEADSET_PLUG)){
                if(intent.getIntExtra("state", 0) == 1){
                    indicator_headphone_jack.setBackgroundResource(R.drawable.test_ok);
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
        signal_strength_option=(LinearLayout)findViewById(R.id.signal_strength_option);
        indicator_signal_strength=(Button)findViewById(R.id.indicator_signal_strength);
        nfc_option=(LinearLayout)findViewById(R.id.nfc_option);
        indicator_nfc=(Button)findViewById(R.id.indicator_nfc);
        headphone_jack_option=(LinearLayout)findViewById(R.id.headphone_jack_option);
        indicator_headphone_jack=(Button)findViewById(R.id.indicator_headphone_jack);
        basic_apps_option=(LinearLayout)findViewById(R.id.basic_apps_option);
        indicator_basic_apps=(Button)findViewById(R.id.indicator_basic_apps);
        speaker_option=(LinearLayout)findViewById(R.id.speaker_option);
        indicator_speaker=(Button)findViewById(R.id.indicator_speaker);


        final PhoneStateListener phone_state_listener=new PhoneStateListener(){
            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength)
            {
                signalStrengthDbm = getSignalStrengthByName(signalStrength, "getDbm");
                signalStrengthAsuLevel = getSignalStrengthByName(signalStrength, "getAsuLevel");
            }

            private int getSignalStrengthByName(SignalStrength signalStrength, String methodName)
            {
                try
                {
                    Class classFromName = Class.forName(SignalStrength.class.getName());
                    java.lang.reflect.Method method = classFromName.getDeclaredMethod(methodName);
                    Object object = method.invoke(signalStrength);
                    return (int)object;
                }
                catch (Exception ex)
                {
                    return 0;
                }
            }

        };TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phone_state_listener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

        bluetooth_Option.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                BA = BluetoothAdapter.getDefaultAdapter();
                IntentFilter filter_bluetooth=new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
                registerReceiver(broad_cast_receiver, filter_bluetooth);
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
                IntentFilter filter_wifi = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
                registerReceiver(broad_cast_receiver, filter_wifi);

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

        signal_strength_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signalStrengthDbm > -97 && signalStrengthAsuLevel > 6){
                    indicator_signal_strength.setBackgroundResource(R.drawable.test_ok);
                }
                else{
                    indicator_signal_strength.setBackgroundResource(R.drawable.test_fail);
                }
                Toast.makeText(Main_activity.this,"dBm = "+signalStrengthDbm+"\n"+"asu = "+signalStrengthAsuLevel,Toast.LENGTH_SHORT).show();
            }
        });

        nfc_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NfcManager manager = (NfcManager)Main_activity.this.getSystemService(Context.NFC_SERVICE);
                NfcAdapter adapter = manager.getDefaultAdapter();
                if(adapter==null){
                    indicator_nfc.setBackgroundResource(R.drawable.test_fail);
                    Toast.makeText(Main_activity.this,"Device does not support NFC",Toast.LENGTH_SHORT).show();
                }
                else{
                    indicator_nfc.setBackgroundResource(R.drawable.test_ok);
                }
            }
        });

        headphone_jack_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentFilter filter_headset = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
                registerReceiver(broad_cast_receiver, filter_headset);
                indicator_headphone_jack.setBackgroundResource(R.drawable.test_fail);
            }
        });

        basic_apps_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int app_count=0, mpi=0;
                boolean isPackageExist;
                String[] missing_packages=new String[8];
                String[] package_names={getString(R.string.settings), getString(R.string.playstore), getString(R.string.google),getString(R.string.gmail), getString(R.string.google_maps), getString(R.string.calculator), getString(R.string.calendar), getString(R.string.messaging)};
                List<ApplicationInfo> packages = Main_activity.this.getPackageManager().getInstalledApplications(0);
                for(int i=0;i<8;i++){
                    isPackageExist=false;
                    for (ApplicationInfo packageInfo : packages) {
                        if (package_names[i].equals(packageInfo.packageName)) {
                            isPackageExist=true;
                        }
                    }
                    if(isPackageExist){
                        app_count++;
                    }
                    else{
                        missing_packages[mpi]=package_names[i];
                        mpi++;
                    }
                }
                if(app_count==8)indicator_basic_apps.setBackgroundResource(R.drawable.test_ok);
                else indicator_basic_apps.setBackgroundResource(R.drawable.test_fail);
            }
        });

        speaker_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                test_audio_file = MediaPlayer.create(Main_activity.this, R.raw.speaker_test);
                audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

                int sampleRate = 8000;
                bufferSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
                audio = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);

                test_audio_file.start();
                audio.startRecording();

                j=0;
                thread = new Thread(new Runnable() {
                    public void run() {
                        for(i=0;i<6;i++){
                            try{Thread.sleep(2000);}catch(InterruptedException ie){ie.printStackTrace();}
                            readAudioBuffer();//After this call we can get the last value assigned to the lastLevel variable

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    audio_samples[j]=lastLevel;
                                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,50,0);
                                    j++;
                                    if(i==5){
										if(audio_samples[1]>=audio_samples[4]){
											indicator_speaker.setBackgroundResource(R.drawable.test_ok);
										}
										else{
											indicator_speaker.setBackgroundResource(R.drawable.test_ok);
										}
									}
                                }
                            });
                        }
                    }
                });
                thread.start();
            }
        });
    }

   private void readAudioBuffer() {
        try {
            short[] buffer = new short[bufferSize];
            int bufferReadResult = 1;

            if (audio != null) {
                // Sense the voice…
                bufferReadResult = audio.read(buffer, 0, bufferSize);
                double sumLevel = 0;
                for (int i = 0; i < bufferReadResult; i++)
                { sumLevel += buffer[i]; }
                lastLevel = Math.abs((sumLevel / bufferReadResult)); }
        } catch (Exception e) { e.printStackTrace(); }
   }
}



