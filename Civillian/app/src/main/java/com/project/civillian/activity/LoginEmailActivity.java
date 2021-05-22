package com.project.civillian.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.project.civillian.R;
import com.project.civillian.service.CivilService;
import com.project.civillian.service.LoginService;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

public class LoginEmailActivity extends AppCompatActivity {

    TextView tvRegister;
    Button btEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initComponent();
    }

    public void initComponent(){
        tvRegister = findViewById(R.id.tv_register);
        tvRegister.setText(Html.fromHtml("Tidak punya akun? <b>Buat Akun.</b>"));
        btEmail = findViewById(R.id.bt_email);
        tvRegister.setOnTouchListener(new CompoundButton.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(LoginEmailActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                LoginEmailActivity.this.finish();
                return true;
            }
        });
        btEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginEmailActivity.this, LoginTokenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }
}
