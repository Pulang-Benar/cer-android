package com.project.civillian.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.project.civillian.R;
import com.project.civillian.model.Civil;
import com.project.civillian.model.Incident;
import com.project.civillian.service.CivilService;
import com.project.civillian.service.PanicService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VidioActivity extends AppCompatActivity {
    private Button btVidio, btSendCamera;
    private TextView tvNameDixplay;
    private VideoView ivVideo;
    private MediaController myVideoController;
    private static final int VIDEO_REQUEST_CODE = 1111;
    CivilService civilService;
    Civil civil;
    PanicService panicService;
    private Double longitude, latitude;
    private Boolean isImageUploaded, isVidioUploaded, isSoundUploaded;
    String fileName, alamatLengkap="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vidio);
        getExtras();
        civilService = new CivilService(this);
        civil = civilService.getCivilLogin();
        panicService = new PanicService(this);
        initComponent();
        initAction();

        if(checkPermission()){
            getLatLong();
        }
    }

    private void initComponent(){
        tvNameDixplay = findViewById(R.id.tv_nameDisplay);
        if(civil != null){
            String nama = civil.getNama() != null && !"".equals(civil.getNama()) ? civil.getNama() : civil.getUsername();
            String nik = civil.getNik() != null && !"".equals(civil.getNik()) ? civil.getNik() : "Mohon lengkapi NIK";
            tvNameDixplay.setText(nama+"\n"+nik);
        }
        btVidio = (Button) findViewById(R.id.bt_video);
        btSendCamera = findViewById(R.id.bt_send_camera);
        ivVideo = (VideoView) findViewById(R.id.iv_video);
        myVideoController = new MediaController(this);
        ivVideo.setMediaController(myVideoController);
    }

    private void initAction(){
        btVidio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent khusus untuk menangkap vidio lewat kamera
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                fileName = "/vid-"+new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date())+".mp4";
                System.out.println("1. fileName = "+fileName);
                Uri vidioUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", new File(getExternalCacheDir().getAbsolutePath()+fileName));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, vidioUri);
                startActivityForResult(intent, VIDEO_REQUEST_CODE);


            }
        });
        btSendCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("fileName doPanic -> "+fileName);
                if(panicService.doPanic(new Incident(fileName, latitude, longitude, getApplicationContext()), getExternalCacheDir().getAbsolutePath()+fileName)){
                    Toast.makeText(getApplicationContext(), "Berhasil mengunggah vidio rekaman", Toast.LENGTH_SHORT).show();
                    System.out.println("SUCCESS CALL DO PANIC - SOUND RECORDER");
                    isVidioUploaded = true;
                } else {
                    Toast.makeText(getApplicationContext(), "CONNECTION TIMEOUT - Gagal mengunggah vidio", Toast.LENGTH_SHORT).show();
                    System.out.println("FAILED CALL DO PANIC - SOUND RECORDER");
                }
                Intent intent = new Intent(VidioActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("isImageUploaded", isImageUploaded);
                intent.putExtra("isVidioUploaded", isVidioUploaded);
                intent.putExtra("isSoundUploaded", isSoundUploaded);
                intent.putExtra("alamatLengkap", alamatLengkap);
                startActivity(intent);
                VidioActivity.this.finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case(VIDEO_REQUEST_CODE) :
                if(resultCode == Activity.RESULT_OK) {
                    Uri videoUri = data.getData();
                    System.out.println("fileName -> "+fileName);
                    ivVideo.setVisibility(VideoView.VISIBLE);
                    ivVideo.setVideoURI(videoUri);
                    btSendCamera.setVisibility(Button.VISIBLE);
                }
                break;
        }
    }

    private void getExtras(){
        if(getIntent() != null){
            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                isImageUploaded = bundle.getBoolean("isImageUploaded");
                isVidioUploaded = bundle.getBoolean("isVidioUploaded");
                isSoundUploaded = bundle.getBoolean("isSoundUploaded");
                alamatLengkap = bundle.getString("alamatLengkap");
            }
        }
        System.out.println("CameraActivity isImageUploaded="+isImageUploaded+", isVidioUploaded="+isVidioUploaded+", isSoundUploaded="+isSoundUploaded);
    }

    private void getLatLong(){
        final Map<String, Double> result = new HashMap<>();
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

}
