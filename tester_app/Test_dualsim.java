package com.example.rudresh.tester;

import android.app.Activity;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Rudresh on 02-08-2018.
 */

public class Test_dualsim {

    static void test_dualsim(Activity MainActivity, TelephonyInfo telephonyInfo, Button indicator_dual_sim){
        boolean sim1_ready=false;
        boolean sim2_ready=false;

        if(!telephonyInfo.isDualSIM()){
            Toast.makeText(MainActivity,"Not a Dual sim phone",Toast.LENGTH_SHORT).show();
            indicator_dual_sim.setBackgroundResource(R.drawable.test_fail);
        }
        else {

            if (telephonyInfo.isSIM1Ready()) {
                sim1_ready = true;
            }
            else {
                Toast.makeText(MainActivity,"Sim1 not ready",Toast.LENGTH_SHORT).show();
            }

            if (telephonyInfo.isSIM2Ready()) {
                sim2_ready = true;
            }
            else {
                Toast.makeText(MainActivity,"sim2 not ready",Toast.LENGTH_SHORT).show();
            }

            if(sim1_ready && sim2_ready){
                indicator_dual_sim.setBackgroundResource(R.drawable.test_ok);
            }
            else{
                indicator_dual_sim.setBackgroundResource(R.drawable.test_fail);
            }
        }
    }
}

