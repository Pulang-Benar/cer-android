package com.project.civillian.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.project.civillian.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

public class LoginTokenActivity extends AppCompatActivity {

    TextView tvRegister;
    Button btSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_token);
        initComponent();
    }

    public void initComponent(){
        tvRegister = findViewById(R.id.tv_register);
        tvRegister.setText(Html.fromHtml("Tidak punya akun? <b>Buat Akun.</b>"));
        btSubmit = findViewById(R.id.bt_submit);
        tvRegister.setOnTouchListener(new CompoundButton.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(LoginTokenActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                LoginTokenActivity.this.finish();
                return true;
            }
        });
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginTokenActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                LoginTokenActivity.this.finish();
            }
        });
    }

}
