package com.example.rudresh.tester;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import static java.lang.System.exit;

/**
 * Created by Rudresh on 11-08-2018.
 */

public class Display_log extends Activity {
    TextView log;
    Button button_export_log;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_log);

        log=(TextView)findViewById(R.id.textview_log);
        log.setText("");
        button_export_log=(Button)findViewById(R.id.button_export_log);

        try {
            log.setText(Create_result_xml.getResultXML());
        }catch(Exception e){Toast.makeText(Display_log.this,""+e,Toast.LENGTH_SHORT).show();
            exit(1);
        }

        button_export_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("xml/*");
                File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Tester/Tester_result.xml");
                if(file.exists()) {
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Test_result xml file");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_STREAM, Uri.parse("file://" + file.getAbsolutePath()));
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            }
        });
    }
}
