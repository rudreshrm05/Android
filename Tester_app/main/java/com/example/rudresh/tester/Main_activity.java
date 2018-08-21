package com.example.rudresh.tester;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.util.List;

import static java.lang.System.exit;

/**
 * Created by Rudresh on 25-07-2018.
 */

public class Main_activity extends Activity{

    Button indicator_bluetooth,
           indicator_wifi,
           indicator_sim_functionality,
           indicator_signal_strength,
           indicator_nfc,
           indicator_headphone_jack,
           indicator_basic_apps,
           indicator_speaker,
           indicator_basic_apps_uninstallable,
           indicator_check_engineermode,
           button_log,
           help_bluetooth,
           help_wifi,
           help_sim_functionality,
           help_signal_strength,
           help_nfc,
           help_headphone_jack,
           help_basic_apps,
           help_speaker,
           help_basic_apps_uninstallable,
           help_check_engineermode;

    LinearLayout bluetooth_Option,
                 wifi_option,
                 sim_functionality_option,
                 signal_strength_option,
                 nfc_option,
                 headphone_jack_option,
                 basic_apps_option,
                 speaker_option,
                 basic_apps_uninstallable_option,
                 check_engineermode_option;
    WifiManager wifi;
    NfcManager manager;
    NfcAdapter adapter;
    AudioManager audioManager;
    MediaPlayer test_audio_file;
    static public int signalStrengthDbm = 0;
    static public int signalStrengthAsuLevel = 0;
    static boolean WIFI_ON=false, WIFI_OFF=false;
    File logDirectory ;
    File logFile;
    Process process;
    static String app_logs_dir_path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/Tester/Tester_logs";


    //Broadcast receiver to receive intents

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
                    WIFI_ON=true;
                }
                else if(state == WifiManager.WIFI_STATE_DISABLED||state == WifiManager.WIFI_STATE_DISABLING){
                    WIFI_OFF=true;
                }

            }

            if(action.equals(AudioManager.ACTION_HEADSET_PLUG)){

                if(intent.getIntExtra("state", 0) == 1){
                    indicator_headphone_jack.setBackgroundResource(R.drawable.test_ok);
                    try{
                        Create_result_xml.create_result_xml(Main_activity.this , "TEST_HEADPHONE_JACK", "PASS", "");
                    }catch(Exception e){exit(1);}
                }
            }
        }
    };


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        logDirectory = new File(app_logs_dir_path);
        logFile = new File(logDirectory, "logcat" +"Tester"+ ".txt");

        // create log folder
        if (!logDirectory.exists()) {
            logDirectory.mkdirs();
        }
        if(logFile.exists()){
            logFile.delete();
        }

        // clear the previous logcat and then write the new one to the file
        try {
           process = Runtime.getRuntime().exec("logcat -c");
            process = Runtime.getRuntime().exec("logcat -f " + logFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bluetooth_Option=(LinearLayout)findViewById(R.id.bluetooth_option);
        indicator_bluetooth=(Button)findViewById(R.id.indicator_bluetooth);
        wifi_option=(LinearLayout)findViewById(R.id.wifi_option);
        indicator_wifi=(Button)findViewById(R.id.indicator_wifi);
        sim_functionality_option=(LinearLayout)findViewById(R.id.sim_functionality_option);
        indicator_sim_functionality=(Button)findViewById(R.id.indicator_sim_functionality);
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
        basic_apps_uninstallable_option=(LinearLayout)findViewById(R.id.basic_apps_unistallable_option);
        indicator_basic_apps_uninstallable=(Button)findViewById(R.id.indicator_basic_apps_uninstallable);
        check_engineermode_option=(LinearLayout)findViewById(R.id.check_engineermode_option);
        indicator_check_engineermode=(Button)findViewById(R.id.indicator_check_engineermode);
        button_log=(Button)findViewById(R.id.log_button);
        help_bluetooth=(Button)findViewById(R.id.help_bluetooth);
        help_basic_apps=(Button)findViewById(R.id.help_basic_apps);
        help_basic_apps_uninstallable=(Button)findViewById(R.id.help_basic_apps_uninstallable);
        help_signal_strength=(Button)findViewById(R.id.help_signal_strength);
        help_sim_functionality=(Button)findViewById(R.id.help_sim_functionality);
        help_wifi=(Button)findViewById(R.id.help_wifi);
        help_nfc=(Button)findViewById(R.id.help_nfc);
        help_check_engineermode=(Button)findViewById(R.id.help_check_engineermode);
        help_speaker=(Button)findViewById(R.id.help_speaker);
        help_headphone_jack=(Button)findViewById(R.id.help_headphone_jack);


        //A phoneStateListener which listens signal changes and populate 'signalStrengthDbm' and 'signalStrengthAsuLevel'

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
        };

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phone_state_listener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);


        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Tester/Tester_result.xml");

       if(file.exists()){
           file.delete();
       }
           try {
               file.createNewFile();
            }
            catch(Exception e){
                Toast.makeText(Main_activity.this,""+e,Toast.LENGTH_SHORT).show();
                exit(1);
            }


        //Testing Bluetooth

        bluetooth_Option.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main_activity.this, Test_bluetooth.class);
                startActivity(intent);
            }
        });

        //Testing Wi-fi

        wifi_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main_activity.this, Test_wifi.class);
                startActivity(intent);
            }
        });

        //Testing Dual sim functionality

        sim_functionality_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main_activity.this, Test_sim_functionality.class);
                startActivity(intent);
            }
        });

        //Testing signal strength

        signal_strength_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Test_signal_strength.test_signal_strength(Main_activity.this, indicator_signal_strength);
            }
        });

        //Testing NFC

        nfc_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager = (NfcManager)Main_activity.this.getSystemService(Main_activity.NFC_SERVICE);
                adapter = manager.getDefaultAdapter();
                Test_nfc.test_nfc(Main_activity.this, adapter, indicator_nfc);
            }
        });

        //Testing headphone jack

        headphone_jack_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Create_logCat.create_logCat("HeadPhoneJack", app_logs_dir_path);

                IntentFilter filter_headset = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
                registerReceiver(broad_cast_receiver, filter_headset);
                indicator_headphone_jack.setBackgroundResource(R.drawable.test_fail);
                AlertDialog.Builder builder=new AlertDialog.Builder(Main_activity.this);

                builder.setCancelable(true);
                builder.setTitle("Test Headphone jack");
                builder.setMessage("Please plugin the headset to test");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });

        //Checking basic apps

        basic_apps_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ApplicationInfo> packages = Main_activity.this.getPackageManager().getInstalledApplications(0);
                String[] package_names={getString(R.string.settings), getString(R.string.playstore), getString(R.string.google),getString(R.string.gmail), getString(R.string.google_maps), getString(R.string.calculator), getString(R.string.calendar), getString(R.string.messaging)};
                Test_basic_apps.test_basic_apps(Main_activity.this, package_names, packages, indicator_basic_apps);
            }
        });

        //Testing speaker and microphone

        speaker_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test_audio_file = MediaPlayer.create(Main_activity.this, R.raw.speaker_test);
                audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                Test_speaker.test_speaker(Main_activity.this, test_audio_file, audioManager, indicator_speaker);
            }
        });

        //Check whether user can uninstall basic apps

        basic_apps_uninstallable_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ApplicationInfo> packages = Main_activity.this.getPackageManager().getInstalledApplications(0);
                String[] package_names={getString(R.string.settings), getString(R.string.playstore), getString(R.string.google),getString(R.string.gmail), getString(R.string.google_maps), getString(R.string.calculator), getString(R.string.calendar), getString(R.string.messaging)};
                Test_basic_apps_uninstallable.test_basic_apps_uninstallable(Main_activity.this, package_names, packages, indicator_basic_apps_uninstallable);
            }
        });

        //Check for EngineerMode.apk

        check_engineermode_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Test_check_EngineerMode_apk.check_engineermode_apk(Main_activity.this, indicator_check_engineermode);
            }
        });

        button_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main_activity.this, Display_log.class);
                startActivity(intent);
            }
        });

        help_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_help(Main_activity.this, "Test Bluetooth", "Tests the bluetooth functionality of the device by enabling, disabling, paring and playing music through a bluetooth speaker.\nA bluetooth speaker with the name 'TEST_SPEAKER' is required");
            }
        });

        help_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_help(Main_activity.this, "Test WiFi", "Test the WiFi functionality of the device by enabling and disabling the WiFi");
            }
        });

        help_sim_functionality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_help(Main_activity.this, "Test SIM functionality ", "Tests whether the SIM card(s) catches signal and also checks the IMEI");
            }
        });

        help_signal_strength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_help(Main_activity.this, "Test signal strength ", "Checks the signal strength");
            }
        });

        help_nfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_help(Main_activity.this, "Test NFC", "Tests whether the device supports NFC or not");
            }
        });

        help_headphone_jack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_help(Main_activity.this, "Test Headphone Jack ", "Test the Hedphone jack of the device for its functionality");
            }
        });

        help_basic_apps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_help(Main_activity.this, "Test Basic apps ", "1. Playstore\n2. Settings\n3. Google\n4. Google maps\n5. Gmail\n6. Messaging\n7. Calendar\n8. Calculator");
            }
        });

        help_basic_apps_uninstallable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_help(Main_activity.this, "Test Basic apps uninstallable", "Test whether the basic apps can be uninstalled or not");
            }
        });

        help_speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_help(Main_activity.this, "Test speaker/Microphone ", "Test the speaker and the Microphone of the device by playing a music with low and high volume.\nSilence is required while testing");
            }
        });

        help_check_engineermode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_help(Main_activity.this, "Check EngineerMod.apk ", "Checks whether the EngineerMod.apk is present on the device or not");
            }
        });
    }

    static void display_help(Activity activity, String title, String content){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(content);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if("PASS".equals(Create_result_xml.RESULT_TEST_BLUETOOTH))
            indicator_bluetooth.setBackgroundResource(R.drawable.test_ok);

        else if("FAIL".equals(Create_result_xml.RESULT_TEST_BLUETOOTH))
            indicator_bluetooth.setBackgroundResource(R.drawable.test_fail);

        if("PASS".equals(Create_result_xml.RESULT_TEST_SIM_FUNCTIONALITY))
            indicator_sim_functionality.setBackgroundResource(R.drawable.test_ok);

        else if("FAIL".equals(Create_result_xml.RESULT_TEST_SIM_FUNCTIONALITY))
            indicator_sim_functionality.setBackgroundResource(R.drawable.test_fail);

        if("PASS".equals(Create_result_xml.RESULT_TEST_WIFI))
            indicator_wifi.setBackgroundResource(R.drawable.test_ok);

        else if("FAIL".equals(Create_result_xml.RESULT_TEST_WIFI))
            indicator_wifi.setBackgroundResource(R.drawable.test_fail);
        try {
            process = Runtime.getRuntime().exec("logcat -f " + logFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Main_activity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}




