package com.example.rudresh.tester;

import android.app.Activity;

import java.io.File;

/**
 * Created by Rudresh on 17-08-2018.
 */

public class Create_logCat {
    static File logDirectory, logFile;
    static Process process;
    public static void create_logCat(String tc_name, String logDir_path){
        logDirectory = new File(logDir_path);
        logFile = new File(logDirectory, "logcat" +tc_name+ ".txt");

        // create log folder
        if (!logDirectory.exists()) {
            logDirectory.mkdir();
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
    }
}
