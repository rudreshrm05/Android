package com.example.rudresh.tester;

import android.widget.Toast;

/**
 * Created by Rudresh on 02-08-2018.
 */

public class Test_dualsim {

    String test_dualsim(TelephonyInfo telephonyInfo){
        boolean sim1_ready=false;
        boolean sim2_ready=false;

        if(!telephonyInfo.isDualSIM()){
            return "NOT_DUAL";
        }
        else {
            if (telephonyInfo.isSIM1Ready()) {
                sim1_ready = true;
            } else {
                return "SIM1_NOT_READY";
            }
            if (telephonyInfo.isSIM2Ready()) {
                sim2_ready = true;
            } else {
                return "SIM2_NOT_READY";
            }
            if(sim1_ready&&sim2_ready){
                return "BOTH_READY";
            }
        }
        return "";
    }
}

