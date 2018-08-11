package com.example.rudresh.tester;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Rudresh on 11-08-2018.
 */

public class Display_log extends Activity {
    TextView log;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_log);

        log=(TextView)findViewById(R.id.textview_log);

        try {
            log.setText(Create_result_xml.getResultXML());
        }catch(Exception e){Toast.makeText(Display_log.this,""+e,Toast.LENGTH_SHORT).show();
        }
    }
}
