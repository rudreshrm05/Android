package com.example.rudresh.tester;

import android.nfc.NfcAdapter;

/**
 * Created by Rudresh on 02-08-2018.
 */

public class Test_nfc {
    boolean test_nfc(NfcAdapter adapter){
        if(adapter==null){
            return false;
        }
        else{
            return true;
        }
    }
}
