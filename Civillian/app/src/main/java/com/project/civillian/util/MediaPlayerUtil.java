package com.project.civillian.util;

import android.media.MediaPlayer;

public class MediaPlayerUtil {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Boolean isFinished;

    public void doPlay(String fileName){
        try {
            isFinished=false;
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
            mediaPlayer.start();
//                    soundThread.start();
//                    Toast.makeText(getApplicationContext(), "Playing record audio", Toast.LENGTH_LONG).show();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
//                            System.out.println("FINISH PLAY RECORD... "+getExternalCacheDir().getAbsolutePath()+fileName);
//                            Toast.makeText(getApplicationContext(), "Selesai mainkan rekaman suara", Toast.LENGTH_LONG).show();
                    isFinished=true;
                    mediaPlayer.stop();
                    mediaPlayer.release();
//                            initThreadSeekbar();
                    mediaPlayer = new MediaPlayer();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

}
