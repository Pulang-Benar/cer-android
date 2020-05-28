package com.project.civillian.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.project.civillian.R;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class HomeActivity extends AppCompatActivity implements Runnable {

    private ImageView icProfile, /*icInstagram,*/ icFacebook, icTwitter;
    private Button btEmergency, btPlay, btUpload;
    private MediaRecorder myAudioRecorder;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private String fileName = "";
    private boolean isRecording = false;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    private SeekBar soundSeekBar;
    private Thread soundThread;
    private LinearLayout layoutSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initComponent();
        initAction();
        initRecorder();
        initThreadSeekbar();
    }

    public void initThreadSeekbar(){
        soundThread = new Thread(this);
    }

    private void initComponent(){
        icProfile = findViewById(R.id.ic_profile);
//        icInstagram = findViewById(R.id.ic_instagram);
        icFacebook = findViewById(R.id.ic_facebook);
        icTwitter = findViewById(R.id.ic_twitter);
        btEmergency = findViewById(R.id.bt_emergency);
        btPlay = findViewById(R.id.bt_play);
        soundSeekBar = (SeekBar) findViewById(R.id.sound_bar);
        layoutSound = findViewById(R.id.layout_play_sound);
        btUpload = findViewById(R.id.bt_upload);
    }

    public void initAction(){
        icProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                HomeActivity.this.finish();
            }
        });
        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });
//        icInstagram.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = "https://instagram.com/kelaskaryawan_mercubuana";
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
//            }
//        });
        icFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/univmercubuana/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
//                Intent intent = new Intent(HomeActivity.this, CameraActivity.class);
//                startActivity(intent);
            }
        });
        icTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://twitter.com/univmercubuana";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        btEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Menghubungi kantor polisi terdekat.\nMengirim lokasi Anda..", Toast.LENGTH_SHORT).show();
            }
        });
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(getExternalCacheDir().getAbsolutePath()+fileName);
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        public void onPrepared(MediaPlayer player) {
                            player.start();
                        }
                    });
                    mediaPlayer.prepare();
//                    mediaPlayer.start();
                    soundThread.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                            mediaPlayer.release();
                            initThreadSeekbar();
                            mediaPlayer = new MediaPlayer();
                            soundSeekBar.setProgress(0);
                        }
                    });
                } catch (Exception e) {
                    // make something
                }
            }
        });
        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void initRecorder(){
        btEmergency.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                layoutSound.setVisibility(LinearLayout.VISIBLE);
//                Toast.makeText(getApplicationContext(), "Started recording audio", Toast.LENGTH_SHORT).show();
                try {
                    if(checkPermissions()){
                        myAudioRecorder = new MediaRecorder();
                        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

                        fileName = "/rec-"+new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date())+".3gp";
                        System.out.println("START RECORDING... "+getExternalCacheDir().getAbsolutePath()+fileName);
                        myAudioRecorder.setOutputFile(getExternalCacheDir().getAbsolutePath()+fileName);
                        myAudioRecorder.prepare();
                        myAudioRecorder.start();
                        isRecording = true;
                    } else {
                        isRecording = false;
                        requestPermissions();
                    }
                } catch (IllegalStateException ise) {
                    isRecording = false;
                    ise.printStackTrace();
                } catch (IOException ioe) {
                    isRecording = false;
                    ioe.printStackTrace();
                }
                return true;
            }
        });
        btEmergency.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (isRecording) {
                        myAudioRecorder.stop();
                        myAudioRecorder.reset();
                        myAudioRecorder.release();
                        myAudioRecorder = null;
//                        Toast.makeText(getApplicationContext(), "Finish recording audio", Toast.LENGTH_SHORT).show();
                        isRecording = false;
                    }
                }
                return false;
            }
        });
    }

    public boolean checkPermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermissions() {
        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{RECORD_AUDIO}, REQUEST_AUDIO_PERMISSION_CODE);
    }

    @Override
    public void run() {
        int currentPosition = 0;
        int soundTotal = mediaPlayer.getDuration();
        soundSeekBar.setMax(soundTotal);
        while (mediaPlayer != null && currentPosition < soundTotal)
        {
            try
            {
                Thread.sleep(300);
                currentPosition = mediaPlayer.getCurrentPosition();
            } catch (InterruptedException soundException) {
                return;
            } catch (Exception otherException) {
                return;
            }
            soundSeekBar.setProgress(currentPosition);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }


}
