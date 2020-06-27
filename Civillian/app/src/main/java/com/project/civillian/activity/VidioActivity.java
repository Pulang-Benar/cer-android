package com.project.civillian.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.project.civillian.R;
import com.project.civillian.model.Civil;
import com.project.civillian.model.Incident;
import com.project.civillian.service.CivilService;
import com.project.civillian.service.PanicService;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VidioActivity extends AppCompatActivity {
    private Button btVidio, btSendCamera;
    private TextView tvNameDixplay;
    private VideoView ivVideo;
    private MediaController myVideoController;
    private static final int VIDEO_REQUEST_CODE = 1111;
    CivilService civilService;
    Civil civil;
    String filePath;
    PanicService panicService;
    private FusedLocationProviderClient mFusedLocation;
    private Double longitude, latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vidio);
        civilService = new CivilService(this);
        civil = civilService.getCivilLogin();
        panicService = new PanicService(this);
        initComponent();
        initLocation();
        initAction();
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

    private void initLocation(){
        // GET CURRENT LOCATION
        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }
        });
    }

    private void initAction(){
        btVidio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent khusus untuk menangkap vidio lewat kamera
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(intent, VIDEO_REQUEST_CODE);
            }
        });
        btSendCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("filePath doPanic -> "+filePath);
                if(panicService.doPanic(new Incident(filePath, latitude, longitude, getApplicationContext()), filePath)){
                    System.out.println("SUCCESS CALL DO PANIC - SOUND RECORDER");
                    //GANTI TOMBOL PANIC dengan POLISI MENUJU LOKASI ANDA
                } else {
                    System.out.println("FAILED CALL DO PANIC - SOUND RECORDER");
                    //GANTI CONNECTION TIMEOUT
                }
                Toast.makeText(getApplicationContext(), "Mengirim bukti rekaman..", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(VidioActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
//                    savefile(videoUri);
                    filePath = getRealPathFromURI(videoUri);
                    System.out.println("filePath -> "+filePath);
                    ivVideo.setVisibility(VideoView.VISIBLE);
                    ivVideo.setVideoURI(videoUri);
                }
                break;
        }
    }

//    private void savefile(Uri sourceuri) {
//        System.out.println("getRealPathFromURI -> "+;
//        fileName = "/vid-"+new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date())+".mp4";
//
//        BufferedInputStream bis = null;
//        BufferedOutputStream bos = null;
//
//        try {
//            bis = new BufferedInputStream(new FileInputStream(getRealPathFromURI(sourceuri)));
//            bos = new BufferedOutputStream(new FileOutputStream(getExternalCacheDir().getAbsolutePath()+fileName, false));
//            byte[] buf = new byte[1024];
//            bis.read(buf);
//            do {
//                bos.write(buf);
//            } while(bis.read(buf) != -1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (bis != null) bis.close();
//                if (bos != null) bos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

}
