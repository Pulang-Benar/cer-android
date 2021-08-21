package com.project.civillian.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.project.civillian.R;
import com.project.civillian.model.Civil;
import com.project.civillian.service.CivilService;
import com.project.civillian.service.LoginService;
import com.project.civillian.util.ApiUtil;

public class MainActivity extends AppCompatActivity {
    Button btLogin;
    Button btRegister;
//    ImageView icInstagram;
    ImageView icFacebook, icTwitter;
    ProgressBar progressBar;
    CivilService civilService;
    Civil civil;
    LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getExtras();
        civilService = new CivilService(this);
        civil = civilService.getCivilLogin();
        loginService = new LoginService(this);
        initComponent();
        initAction();
        ApiUtil.subscribeToAllTopics();
    }

    private void initComponent() {
        btLogin = findViewById(R.id.bt_login);
        String userStr = civil.getNama() == null || "".equals(civil.getNama()) ? civil.getUsername() : civil.getNama();
        if (civil != null && civil.getUsername() != null) btLogin.setText("Login As " + userStr);

        btRegister = findViewById(R.id.bt_register);
//        icInstagram = findViewById(R.id.ic_instagram);
        icFacebook = findViewById(R.id.ic_facebook);
        icTwitter = findViewById(R.id.ic_twitter);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

    }

    private void initAction(){
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                btLogin.setEnabled(false);
                new AsyncTask<Void,Void,Void>(){
                    //The variables you need to set go here
                    @Override
                    protected Void doInBackground(final Void... params){
                        System.out.println("civil = "+civil.getUsername());
                        if(civil.getUsername() != null) {
                            System.out.println("civil not null "+civil.getUsername());
//                            if(loginService.doLogin(civil.getUsername(), civil.getPassword())){
                            if(true){
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                MainActivity.this.finish();
                            }
                        } else {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            MainActivity.this.finish();
                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(final Void result){
                        progressBar.setVisibility(View.INVISIBLE);
                        btLogin.setEnabled(true);
                    }
                }.execute();
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
