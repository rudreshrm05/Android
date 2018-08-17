package com.example.rudresh.tester;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;

import static java.lang.System.exit;

/**
 * Created by Rudresh on 17-08-2018.
 */

public class Test_signal_strength {
    public  static void test_signal_strength(Activity activity, Button indicator_signal_strength){

        Create_logCat.create_logCat("testSignalStrength", activity.getString(R.string.Tester_logdir_path));

        int MIN_ASU=6, MIN_DBM=-97;
        int signalStrengthAsuLevel=Main_activity.signalStrengthAsuLevel;
        int signalStrengthDbm=Main_activity.signalStrengthDbm;

        String dialogMessage="";
        if(signalStrengthAsuLevel>MIN_ASU&&signalStrengthDbm>MIN_DBM){
            indicator_signal_strength.setBackgroundResource(R.drawable.test_ok);
            dialogMessage="Dbm : "+signalStrengthDbm+"\n"+"Asu : "+signalStrengthAsuLevel;
            try{
                Create_result_xml.create_result_xml(activity, "TEST_SIGNAL_STRENGTH", "PASS","");
            }catch(Exception e){exit(1);}
        }
        else{
            indicator_signal_strength.setBackgroundResource(R.drawable.test_fail);
            dialogMessage="Dbm : "+signalStrengthDbm+"\n"+"Asu : "+signalStrengthAsuLevel+"\n"+"Low signal strength(<6 asu and <-97 dbm)";
            try{
                Create_result_xml.create_result_xml(activity, "TEST_SIGNAL_STRENGTH", "FAIL","Signal strength low");
            }catch(Exception e){exit(1);}
        }

        AlertDialog.Builder builder=new AlertDialog.Builder(activity);

        builder.setCancelable(true);
        builder.setTitle("Signal strength");

        builder.setMessage(dialogMessage);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
}
