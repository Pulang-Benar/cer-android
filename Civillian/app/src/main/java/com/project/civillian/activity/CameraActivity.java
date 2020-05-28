package com.project.civillian.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.project.civillian.R;

public class CameraActivity extends AppCompatActivity {

    private Button btCamera, btVidio, btSendCamera;
    private ImageView ivCamera;
    private VideoView ivVideo;
    private MediaController myVideoController;
    private static final String TAG = CameraActivity.class.getSimpleName();
    private static final int CAMERA_REQUEST_CODE = 7777;
    private static final int VIDEO_REQUEST_CODE = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        btCamera = (Button) findViewById(R.id.bt_camera);
        btVidio = (Button) findViewById(R.id.bt_video);
        btSendCamera = findViewById(R.id.bt_send_camera);
        ivCamera = (ImageView) findViewById(R.id.iv_camera);
        ivVideo = (VideoView) findViewById(R.id.iv_video);
        myVideoController = new MediaController(this);
        ivVideo.setMediaController(myVideoController);
        btCamera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent khusus untuk menangkap foto lewat kamera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });
        btVidio.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent khusus untuk menangkap vidio lewat kamera
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(intent, VIDEO_REQUEST_CODE);
            }
        });
        btSendCamera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Mengirim bukti rekaman..", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CameraActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
//                    cara get image foto
                    Bitmap bitmap;
                    bitmap = (Bitmap) data.getExtras().get("data");
                    ivCamera.setVisibility(ImageView.VISIBLE);
                    ivCamera.setImageBitmap(bitmap);
                }
                break;

            case(VIDEO_REQUEST_CODE) :
                if(resultCode == Activity.RESULT_OK) {
//                    cara get vidio result
                    Uri videoUri = data.getData();
                    ivVideo.setVisibility(VideoView.VISIBLE);
                    ivVideo.setVideoURI(videoUri);
                }
                break;

        }
    }

}
