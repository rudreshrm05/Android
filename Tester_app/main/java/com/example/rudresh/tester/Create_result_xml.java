package com.example.rudresh.tester;

import android.app.Activity;
import android.content.Context;
import android.util.Xml;
import org.xmlpull.v1.XmlSerializer;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Rudresh on 05-08-2018.
 */

public class Create_result_xml {
   static String RESULT_TEST_BLUETOOTH;
    public static void setResult(String test, String RESULT){
        switch (test){
            case "BLUETOOTH_TEST" : RESULT_TEST_BLUETOOTH=RESULT;
                 break;
        }
    }
    public static void CreateXMLString(Activity activity, String TC_NAME, String RESULT, String LOG) throws IllegalArgumentException, IllegalStateException, IOException
    {
        XmlSerializer xmlSerializer = Xml.newSerializer();
        FileOutputStream fos;

        fos =activity.openFileOutput("file1.xml", Context.MODE_APPEND);

        xmlSerializer.setOutput(fos,"UTF-8");

        xmlSerializer.startDocument(null, true);
        xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        xmlSerializer.startTag("","TestCase");
        xmlSerializer.attribute("", "name",TC_NAME);

        xmlSerializer.startTag("","result");
        xmlSerializer.text(RESULT);
        xmlSerializer.endTag("","result");

        xmlSerializer.startTag("","log");
        xmlSerializer.text(LOG);
        xmlSerializer.endTag("","log");

        xmlSerializer.endTag("","TestCase");

        fos.close();
    }

}
