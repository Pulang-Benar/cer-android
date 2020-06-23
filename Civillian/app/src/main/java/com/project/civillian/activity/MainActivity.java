package com.project.civillian.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.project.civillian.R;
import com.project.civillian.model.Civil;
import com.project.civillian.service.CivilService;

public class MainActivity extends AppCompatActivity {
    Button btLogin;
    Button btRegister;
//    ImageView icInstagram;
    ImageView icFacebook, icTwitter;
    CivilService civilService;
    Civil civil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        civilService = new CivilService(this);
        civil = civilService.getCivilLogin();
        initComponent();
    }

    private void initComponent(){
        btLogin = findViewById(R.id.bt_login);
        if(civil != null && civil.getUsername() != null) btLogin.setText("Login As "+civil.getUsername());

        btRegister = findViewById(R.id.bt_register);
//        icInstagram = findViewById(R.id.ic_instagram);
        icFacebook = findViewById(R.id.ic_facebook);
        icTwitter = findViewById(R.id.ic_twitter);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
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

    }



}
