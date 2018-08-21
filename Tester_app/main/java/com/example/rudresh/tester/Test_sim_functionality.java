package com.example.rudresh.tester;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;

import static java.lang.System.exit;

/**
 * Created by Rudresh on 02-08-2018.
 */

public class Test_sim_functionality extends Activity {
    TelephonyInfo telephonyInfo;
    LinearLayout sim1_option, sim2_option, check_imei_option;
    Button indicator_sim1, indicator_sim2, indicator_imei;
    String sim1_IMEI, sim2_IMEI;
    boolean test_sim1=false, test_sim2=false, test_imei=false;
    String log="";
    File logDirectory, logFile;
    Process process;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sim_functionality);

        Create_logCat.create_logCat("SIMFunctionality", Main_activity.app_logs_dir_path);

        telephonyInfo = TelephonyInfo.getInstance(Test_sim_functionality.this);

        sim1_option=(LinearLayout)findViewById(R.id.sim1_option);
        indicator_sim1=(Button)findViewById(R.id.indicator_sim1);
        sim2_option=(LinearLayout)findViewById(R.id.sim2_option);
        indicator_sim2=(Button)findViewById(R.id.indicator_sim2);
        check_imei_option=(LinearLayout)findViewById(R.id.check_imei_option);
        indicator_imei=(Button)findViewById(R.id.indicator_check_imei);

        final boolean isDual=telephonyInfo.isDualSIM();

        sim1_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(telephonyInfo.isSIM1Ready()){
                    indicator_sim1.setBackgroundResource(R.drawable.test_ok);
                    test_sim1=true;
                }
                else{
                    indicator_sim1.setBackgroundResource(R.drawable.test_fail);
                    log+="SIM1 not ready";

                    AlertDialog.Builder builder=new AlertDialog.Builder(Test_sim_functionality.this);

                    builder.setCancelable(true);
                    builder.setTitle("Test SIM functionality");

                    builder.setMessage("SIM1 not ready");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.show();
                }
            }
        });

        sim2_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDual){
                    if(telephonyInfo.isSIM2Ready()){
                        indicator_sim2.setBackgroundResource(R.drawable.test_ok);
                        test_sim2=true;
                    }
                    else {
                        indicator_sim2.setBackgroundResource(R.drawable.test_fail);
                        log += "SIM2 not ready";
                        AlertDialog.Builder builder = new AlertDialog.Builder(Test_sim_functionality.this);

                        builder.setCancelable(true);
                        builder.setTitle("Test SIM functionality");

                        builder.setMessage("SIM2 not ready");

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        builder.show();
                    }
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Test_sim_functionality.this);

                    builder.setCancelable(true);
                    builder.setTitle("Test SIM functionality");

                    builder.setMessage("The Phone supports only one sim");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.show();
                    test_sim2=true;
                }
            }
        });

        check_imei_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sim1_IMEI=telephonyInfo.getImsiSIM1();
                sim2_IMEI=telephonyInfo.getImsiSIM2();

                if(sim1_IMEI==null || sim2_IMEI==null){
                    if(isDual) {
                        indicator_imei.setBackgroundResource(R.drawable.test_fail);
                        log += "IMEI missing";
                        AlertDialog.Builder builder = new AlertDialog.Builder(Test_sim_functionality.this);

                        builder.setCancelable(true);
                        builder.setTitle("Test SIM functionality");

                        builder.setMessage("IMEI missing");

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        builder.show();
                    }
                    else{
                        if(sim1_IMEI==null){
                            indicator_imei.setBackgroundResource(R.drawable.test_fail);
                            log += "IMEI missing";
                            AlertDialog.Builder builder = new AlertDialog.Builder(Test_sim_functionality.this);

                            builder.setCancelable(true);
                            builder.setTitle("Test SIM functionality");

                            builder.setMessage("IMEI missing");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            builder.show();
                        }
                        else{
                            test_imei=true;
                            indicator_imei.setBackgroundResource(R.drawable.test_ok);
                        }
                    }
                }
                else{
                    test_imei=true;
                    indicator_imei.setBackgroundResource(R.drawable.test_ok);
                }

                if(test_sim1 & test_sim2 & test_imei){
                    try{
                        Create_result_xml.create_result_xml(Test_sim_functionality.this, "TEST_SIM_FUNCTIONALITY", "PASS", "");
                    }catch(Exception e){exit(1);}
                }
                else{
                    try{
                        Create_result_xml.create_result_xml(Test_sim_functionality.this, "TEST_SIM_FUNCTIONALITY", "FAIL", log);
                    }catch(Exception e){exit(1);}
                }

                AlertDialog.Builder builder=new AlertDialog.Builder(Test_sim_functionality.this);

                builder.setCancelable(true);
                builder.setTitle("IMEI number");
                builder.setMessage("SIM1 : "+sim1_IMEI+"\n"+"SIM2 : "+sim2_IMEI);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });
    }
}

