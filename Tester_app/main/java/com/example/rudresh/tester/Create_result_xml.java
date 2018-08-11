package com.example.rudresh.tester;

import android.app.Activity;
import android.content.Context;
import android.util.Xml;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static java.lang.System.exit;

/**
 * Created by Rudresh on 05-08-2018.
 */

public class Create_result_xml {
   static String RESULT_TEST_BLUETOOTH, RESULT_TEST_WIFI, RESULT_TEST_SIM_FUNCTIONALITY, RESULT_TEST_SIGNAL_STRENGTH, RESULT_TEST_NFC, RESULT_TEST_HEADPHONE_JACK, RESULT_TEST_BASIC_APPS, RESULT_TEST_SPEAKER_MICROPHONE, RESULT_TEST_BASIC_APPS_UNINSTALLABLE, RESULT_TEST_CHECK_ENGINEERINGMOD_APK,
                 LOG_TEST_BLUETOOTH, LOG_TEST_WIFI, LOG_TEST_SIM_FUNCTIONALITY, LOG_TEST_SIGNAL_STRENGTH, LOG_TEST_NFC, LOG_TEST_HEADPHONE_JACK, LOG_TEST_BASIC_APPS, LOG_TEST_SPEAKER_MICROPHONE, LOG_TEST_BASIC_APPS_UNINSTALLABLE, LOG_TEST_CHECK_ENGINEERINGMOD_APK;
   static File file;

    public static void create_result_xml(Activity activity, String TC_NAME, String TEST_RESULT, String TEST_LOG) throws IllegalArgumentException, IllegalStateException, IOException
    {

        switch(TC_NAME){

            case "TEST_BLUETOOTH" :

                RESULT_TEST_BLUETOOTH=TEST_RESULT;
                LOG_TEST_BLUETOOTH=TEST_LOG;
                break;

            case "TEST_WIFI" :

                RESULT_TEST_WIFI=TEST_RESULT;
                LOG_TEST_WIFI=TEST_LOG;
                break;

            case "TEST_SIM_FUNCTIONALITY" :

                RESULT_TEST_SIM_FUNCTIONALITY=TEST_RESULT;
                LOG_TEST_SIM_FUNCTIONALITY=TEST_LOG;
                break;

            case "TEST_SINGNAL_STRENGTH" :

                RESULT_TEST_SIGNAL_STRENGTH=TEST_RESULT;
                LOG_TEST_SIGNAL_STRENGTH=TEST_LOG;
                break;

            case "TEST_NFC" :

                RESULT_TEST_NFC=TEST_RESULT;
                LOG_TEST_NFC=TEST_LOG;
                break;

            case "TEST_HEADPHONE_JACK" :

                RESULT_TEST_HEADPHONE_JACK=TEST_RESULT;
                LOG_TEST_HEADPHONE_JACK=TEST_LOG;
                break;

            case "TEST_BASIC_APPS" :

                RESULT_TEST_BASIC_APPS=TEST_RESULT;
                LOG_TEST_BASIC_APPS=TEST_LOG;
                break;

            case "TEST_SPEAKER_MICROPHONE" :

                RESULT_TEST_SPEAKER_MICROPHONE=TEST_RESULT;
                LOG_TEST_SPEAKER_MICROPHONE=TEST_LOG;
                break;

            case "TEST_BASIC_APPS_UNINSTALLABLE" :

                RESULT_TEST_BASIC_APPS_UNINSTALLABLE=TEST_RESULT;
                LOG_TEST_BASIC_APPS_UNINSTALLABLE=TEST_LOG;
                break;

            case "TEST_CHECK_ENGINEERMOD_APK" :

                RESULT_TEST_CHECK_ENGINEERINGMOD_APK=TEST_RESULT;
                LOG_TEST_CHECK_ENGINEERINGMOD_APK=TEST_LOG;
                break;
        }

        String path=activity.getFilesDir().toString();
        file=new File(path, "Test_result.xml");

        XmlSerializer xmlSerializer = Xml.newSerializer();
        FileOutputStream fos;

       fos = new FileOutputStream (new File(file.getAbsolutePath().toString()), true);

       xmlSerializer.setOutput(fos,"UTF-8");

        xmlSerializer.startDocument(null, true);
        xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        xmlSerializer.startTag("","TestCase");
        xmlSerializer.attribute("", "name",TC_NAME);

        xmlSerializer.startTag("","result");
        xmlSerializer.text(TEST_RESULT);
        xmlSerializer.endTag("","result");

        xmlSerializer.startTag("","log");
        xmlSerializer.text(TEST_LOG);
        xmlSerializer.endTag("","log");

        xmlSerializer.endTag("","TestCase");

        xmlSerializer.endDocument();

        fos.close();

    }

    static String getResultXML(){
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(new File(file.getAbsolutePath().toString()));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line+"\n");
            }
        }catch(Exception e){exit(1);}
        return ""+sb;
    }

}
