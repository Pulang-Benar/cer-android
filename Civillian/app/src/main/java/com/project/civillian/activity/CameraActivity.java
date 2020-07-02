package com.project.civillian.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.project.civillian.R;
import com.project.civillian.model.Civil;
import com.project.civillian.model.Incident;
import com.project.civillian.service.CivilService;
import com.project.civillian.service.PanicService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CameraActivity extends AppCompatActivity {
    private Button btCamera, btSendCamera;
    private TextView tvNameDixplay;
    private ImageView ivCamera;
    private static final int CAMERA_REQUEST_CODE = 7777;
    CivilService civilService;
    Civil civil;
    PanicService panicService;
    String fileName, alamatLengkap="";
    private Double longitude, latitude;
    private Boolean isImageUploaded, isVidioUploaded, isSoundUploaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
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
        btCamera = (Button) findViewById(R.id.bt_camera);
        btSendCamera = findViewById(R.id.bt_send_camera);
        ivCamera = (ImageView) findViewById(R.id.iv_camera);
    }

    private void initAction(){
        btCamera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent khusus untuk menangkap foto lewat kamera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });
        btSendCamera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("filePath -> "+getExternalCacheDir().getAbsolutePath()+fileName);
                if(panicService.doPanic(new Incident(fileName, latitude, longitude, getApplicationContext()), getExternalCacheDir().getAbsolutePath()+fileName)){
                    Toast.makeText(getApplicationContext(), "Berhasil mengunggah foto", Toast.LENGTH_SHORT).show();
                    System.out.println("SUCCESS CALL DO PANIC - IMAGE UPLOAD");
                    isImageUploaded = true;
                } else {
                    Toast.makeText(getApplicationContext(), "CONNECTION TIMEOUT - Gagal mengunggah foto", Toast.LENGTH_SHORT).show();
                    System.out.println("FAILED CALL DO PANIC - IMAGE UPLOAD");
                    //GANTI CONNECTION TIMEOUT
                }

                Toast.makeText(getApplicationContext(), "Mengirim bukti rekaman..", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CameraActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("isImageUploaded", isImageUploaded);
                intent.putExtra("isVidioUploaded", isVidioUploaded);
                intent.putExtra("isSoundUploaded", isSoundUploaded);
                intent.putExtra("alamatLengkap", alamatLengkap);
                startActivity(intent);
                CameraActivity.this.finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case(CAMERA_REQUEST_CODE) :
                if(resultCode == Activity.RESULT_OK) {
                    Bitmap bitmap;
                    bitmap = (Bitmap) data.getExtras().get("data");
                    fileName = "/img-"+new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date())+".jpg";
                    System.out.println("SAVING IMAGE TO... "+getExternalCacheDir().getAbsolutePath()+fileName);
                    File pictureFile = new File(getExternalCacheDir().getAbsolutePath()+fileName);
                    try {
                        FileOutputStream fos = new FileOutputStream(pictureFile);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
                        fos.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found: " + e.getMessage());
                    } catch (IOException e) {
                        System.out.println("Error accessing file: " + e.getMessage());
                    }

                    ivCamera.setVisibility(ImageView.VISIBLE);
                    btSendCamera.setVisibility(Button.VISIBLE);
                    ivCamera.setImageBitmap(bitmap);
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
