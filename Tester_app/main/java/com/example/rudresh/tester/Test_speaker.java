package com.example.rudresh.tester;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.widget.Button;

import static java.lang.System.exit;

/**
 * Created by Rudresh on 02-08-2018.
 */

public class Test_speaker {
    static MediaPlayer test_audio_file;
    static AudioManager audioManager;
    static int bufferSize,i,j;
    static double audio_samples[]=new double[6], lastLevel;
    static AudioRecord audio;
   // static Thread thread;
    static Activity act;
    static Button indicator_speaker;

    static void test_speaker(Activity activity, MediaPlayer audio_file, AudioManager audio_Manager, Button indicatorSpeaker) {

        Create_logCat.create_logCat("TestSpeaker", Main_activity.app_logs_dir_path);

        int sampleRate = 8000;
        test_audio_file = audio_file;
        audioManager = audio_Manager;
        act = activity;
        indicator_speaker = indicatorSpeaker;
        bufferSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        audio = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 2, 0);

        test_audio_file.start();
        audio.startRecording();
        j = 0;
       Thread thread = new Thread(new Runnable() {
            public void run() {
                for (i = 0; i < 6; i++) {
                    try {
                        Thread.sleep(2000);
                    }
                    catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                    readAudioBuffer();//After this call we can get the last value assigned to the lastLevel variable

                    act.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            audio_samples[j] = lastLevel;
                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 50, 0);
                            j++;

                            if (i == 5) {

                                if (audio_samples[0]<audio_samples[4]) {
                                    indicator_speaker.setBackgroundResource(R.drawable.test_ok);
                                    try{
                                        Create_result_xml.create_result_xml(act, "TEST_SPEAKER_MICROPHONE", "PASS", "");
                                    }catch(Exception e){exit(1);}
                                } else {
                                    indicator_speaker.setBackgroundResource(R.drawable.test_fail);
                                    try{
                                        Create_result_xml.create_result_xml(act, "TEST_SPEAKER_MICROPHONE", "FAIL", "Defect in device or noisy environment");
                                    }catch(Exception e){exit(1);}
                                }
                            }
                        }
                    });
                }
            }
        });

        thread.start();
    }

   static private void readAudioBuffer() {
        try {
            short[] buffer = new short[bufferSize];
            int bufferReadResult = 1;

            if (audio != null) {
                // Sense the voice…
                bufferReadResult = audio.read(buffer, 0, bufferSize);
                double sumLevel = 0;

                for (int i = 0; i < bufferReadResult; i++)
                { sumLevel += buffer[i]; }
                lastLevel = Math.abs((sumLevel / bufferReadResult)); }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}
