package com.project.civillian.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.civillian.R;
import com.project.civillian.model.Civil;
import com.project.civillian.service.CivilService;
import com.project.civillian.util.ApiUtil;

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
        getExtras();
        civilService = new CivilService(this);
        civil = civilService.getCivilLogin();
        initComponent();
        ApiUtil.subscribeToAllTopics();
    }

    private void initComponent(){
        btLogin = findViewById(R.id.bt_login);
        String userStr = civil.getNama()==null||"".equals(civil.getNama())?civil.getUsername():civil.getNama();
        if(civil != null && civil.getUsername() != null) btLogin.setText("Login As "+userStr);

        btRegister = findViewById(R.id.bt_register);
//        icInstagram = findViewById(R.id.ic_instagram);
        icFacebook = findViewById(R.id.ic_facebook);
        icTwitter = findViewById(R.id.ic_twitter);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                MainActivity.this.finish();
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

    private void goToMapNotification(String latitude, String longitude){
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);
        MainActivity.this.finish();
    }

    private void getExtras(){
        System.out.println("getExtras");
        if(getIntent() != null){
            System.out.println("getExtras getIntent");
            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                System.out.println("getExtras bundle");
                String latitude = bundle.getString("latitude");
                String longitude = bundle.getString("longitude");
                System.out.println("latitude="+latitude+", longitude="+longitude);
                if(latitude!=null && longitude!=null && !"".equals(latitude) && !"".equals(longitude)
                        && !"0".equals(latitude) && !"0".equals(longitude)){
                    System.out.println(" Go to Map Activity - latitude="+latitude+", longitude="+longitude);
                    goToMapNotification(latitude, longitude);
                }
            }
        }
    }

}
