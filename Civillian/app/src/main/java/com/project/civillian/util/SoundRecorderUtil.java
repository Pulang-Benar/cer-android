package com.project.civillian.util;

import android.media.MediaRecorder;

import java.io.IOException;

public class SoundRecorderUtil {

    private MediaRecorder myAudioRecorder;

    public Boolean doRecord(String fileName){
        try {
            myAudioRecorder = new MediaRecorder();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

            System.out.println("START RECORDING... "+fileName);
            myAudioRecorder.setOutputFile(fileName);
            myAudioRecorder.prepare();
            myAudioRecorder.start();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean doStop(){
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder = null;
        return false;
    }
}
