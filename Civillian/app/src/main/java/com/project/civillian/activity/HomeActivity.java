package com.project.civillian.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.project.civillian.R;
import com.project.civillian.model.Civil;
import com.project.civillian.model.Incident;
import com.project.civillian.service.CivilService;
import com.project.civillian.service.PanicService;
import com.project.civillian.util.ApiUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class HomeActivity extends AppCompatActivity implements Runnable {
    private ImageView icProfile, /*icInstagram,*/ icFacebook, icTwitter;
    private TextView tvNameDixplay, tvSuccessImage, tvSuccessVidio, tvPolisi;
    private Button btEmergency, btPlay, btUploadFoto, btUploadVidio;
    private MediaRecorder myAudioRecorder;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private String fileName = "";
    private boolean isRecording = false;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    private SeekBar soundSeekBar;
    private Thread soundThread;
    private LinearLayout layoutSound;
    private Double longitude, latitude;
    private GifImageView icPolisi;
    CivilService civilService;
    PanicService panicService;
    Civil civil;
    private Boolean isImageUploaded, isVidioUploaded, isSoundUploaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getExtras();
        civilService = new CivilService(this);
        civil = civilService.getCivilLogin();
        panicService = new PanicService(this);

        if(checkPermission()){
            getLatLong();
        }

        if(!checkPermissionsRecorder()){
            isRecording = false;
            requestPermissionsRecorder();
        }

        initComponent();
        initAction();
        initRecorder();
        initThreadSeekbar();
    }

    private void initThreadSeekbar(){
        soundThread = new Thread(this);
    }

    private void initComponent(){
        icProfile = findViewById(R.id.ic_profile);
//        icInstagram = findViewById(R.id.ic_instagram);
        icFacebook = findViewById(R.id.ic_facebook);
        icTwitter = findViewById(R.id.ic_twitter);
        tvNameDixplay = findViewById(R.id.tv_nameDisplay);
        if(civil != null){
            String nama = civil.getNama() != null && !"".equals(civil.getNama()) ? civil.getNama() : civil.getUsername();
            String nik = civil.getNik() != null && !"".equals(civil.getNik()) ? civil.getNik() : "Mohon lengkapi NIK";
            tvNameDixplay.setText(nama+"\n"+nik);
        }
        btEmergency = findViewById(R.id.bt_emergency);
        btPlay = findViewById(R.id.bt_play);
        soundSeekBar = (SeekBar) findViewById(R.id.sound_bar);
        layoutSound = findViewById(R.id.layout_play_sound);
        btUploadFoto = findViewById(R.id.bt_uploadFoto);
        btUploadVidio = findViewById(R.id.bt_uploadVidio);
        icPolisi = findViewById(R.id.ic_polisi);
        tvPolisi = findViewById(R.id.tv_polisi);
        tvSuccessImage = findViewById(R.id.tv_success_image);
        tvSuccessVidio = findViewById(R.id.tv_success_vidio);
        if(isSoundUploaded!=null && isSoundUploaded){
            btEmergency.setVisibility(Button.GONE);
            icPolisi.setVisibility(GifImageView.VISIBLE);
            tvPolisi.setVisibility(TextView.VISIBLE);
        }
        if(isImageUploaded!=null && isImageUploaded){
            btUploadFoto.setVisibility(Button.GONE);
            tvSuccessImage.setVisibility(TextView.VISIBLE);
        }
        if(isVidioUploaded!=null && isVidioUploaded){
            btUploadVidio.setVisibility(Button.GONE);
            tvSuccessVidio.setVisibility(TextView.VISIBLE);
        }
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
        btUploadFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CameraActivity.class);
                intent.putExtra("isImageUploaded", isImageUploaded);
                intent.putExtra("isVidioUploaded", isVidioUploaded);
                intent.putExtra("isSoundUploaded", isSoundUploaded);
                startActivity(intent);
            }
        });
        btUploadVidio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, VidioActivity.class);
                intent.putExtra("isImageUploaded", isImageUploaded);
                intent.putExtra("isVidioUploaded", isVidioUploaded);
                intent.putExtra("isSoundUploaded", isSoundUploaded);
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
                Toast.makeText(getApplicationContext(), "Long press, untuk melaporkan.", Toast.LENGTH_SHORT).show();
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
                        isRecording = false;

                        Incident incident = new Incident(fileName, latitude, longitude, getApplicationContext());
                        System.out.println(incident.toString());
                        if(panicService.doPanic(incident, getExternalCacheDir().getAbsolutePath()+fileName)){
                            System.out.println("SUCCESS CALL DO PANIC - SOUND RECORDER");
                            System.out.println("Menghubungi kantor polisi terdekat.\nMengirim lokasi Anda..\n"+incident.getLatestFormattedAddress());
                            Toast.makeText(getApplicationContext(), "Menghubungi kantor polisi terdekat.\nMengirim lokasi Anda..\n"+incident.getLatestFormattedAddress(), Toast.LENGTH_LONG).show();
                            isSoundUploaded = true;
                            btEmergency.setVisibility(Button.GONE);
                            icPolisi.setVisibility(GifImageView.VISIBLE);
                            tvPolisi.setVisibility(TextView.VISIBLE);
                            HashMap<String, String> mapStr = new HashMap<>();
                            mapStr.put("latitude",latitude+"");
                            mapStr.put("longitude",longitude+"");
//                            Send_Notification();
                            ApiUtil.sendPushToSingleInstance(getApplicationContext(), mapStr);
                        } else {
                            System.out.println("FAILED CALL DO PANIC - SOUND RECORDER");
                            Toast.makeText(getApplicationContext(), "CONNECTION TIMEOUT - Gagal Mengirim Lokasi Anda", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return false;
            }
        });
    }

    private boolean checkPermissionsRecorder() {
        int resultAudio = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        int resultWriteStorage = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        return (resultAudio == PackageManager.PERMISSION_GRANTED && resultWriteStorage == PackageManager.PERMISSION_GRANTED);
    }
    private void requestPermissionsRecorder() {
        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{RECORD_AUDIO}, REQUEST_AUDIO_PERMISSION_CODE);
        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }

    @Override
    public void run() {
        int currentPosition = 0;
        int soundTotal = mediaPlayer.getDuration();
        soundSeekBar.setMax(soundTotal);
        while (mediaPlayer != null && currentPosition < soundTotal)
        {
            try {
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

    private void getExtras(){
        if(getIntent() != null){
            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                isImageUploaded = bundle.getBoolean("isImageUploaded");
                isVidioUploaded = bundle.getBoolean("isVidioUploaded");
                isSoundUploaded = bundle.getBoolean("isSoundUploaded");
            }
        }
        System.out.println("CameraActivity isImageUploaded="+isImageUploaded+", isVidioUploaded="+isVidioUploaded+", isSoundUploaded="+isSoundUploaded);
    }

    private void getLatLong(){
        final Map<String, Double> result = new HashMap<>();
        System.out.println("getLatLong");
        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    System.out.println("latitude="+location.getLatitude()+", longitude="+location.getLongitude());
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }

        });
    }

    private boolean checkPermission() {
        boolean result = false;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    result = true;
                }else{
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    result = true;
                }
            } else {
                result = true;
            }
        }
        return result;
    }





    private void Send_Notification()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://fcm.googleapis.com/fcm/send",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("farmerorderdataresponse - "+response);
                        try{
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Login Error !1"+e,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Oops error");
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("title","Order Received");
                params.put("body","This is a notification");
                params.put("topic","allTopics");
                return params;
            }

            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String serverKey="AAAAk4TEFVM:APA91bErfrTcoSiKt-oc7rS8dCqoN4Kl953dG7TTUJ3IEgJvcLdM1YMjB1n22cRg5XusbvbXTCVgvAxntljZbRhyugs8TkkO4Qcz6QgON_3TS6lJD32DYHaK8P_kL0iFWHvgerXWPmKf";
                headers.put("Authorization", "key="+serverKey);
                return headers;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


}
