package com.project.civillian.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.project.civillian.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class LoginTokenActivity extends AppCompatActivity {

    TextView tvRegister;
    Button btSubmit;
    EditText token1, token2, token3, token4, token5, token6, token7, token8, token9, token10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_token);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initComponent();
        initAction();
    }

    public void initComponent(){
        tvRegister = findViewById(R.id.tv_register);
        tvRegister.setText(Html.fromHtml("Tidak punya akun? <b>Buat Akun.</b>"));
        btSubmit = findViewById(R.id.bt_submit);
        token1 = findViewById(R.id.token1);
        token2 = findViewById(R.id.token2);
        token3 = findViewById(R.id.token3);
        token4 = findViewById(R.id.token4);
        token5 = findViewById(R.id.token5);
        token6 = findViewById(R.id.token6);
        token7 = findViewById(R.id.token7);
        token8 = findViewById(R.id.token8);
        token9 = findViewById(R.id.token9);
        token10 = findViewById(R.id.token10);
    }

    public void initAction(){
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
                Intent intent = new Intent(LoginTokenActivity.this, ResetPasswordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                LoginTokenActivity.this.finish();
            }
        });
        token1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start,int before, int count){
                token2.requestFocus();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

        });
        token2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start,int before, int count){
                token3.requestFocus();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

        });
        token3.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start,int before, int count){
                token4.requestFocus();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

        });
        token4.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start,int before, int count){
                token5.requestFocus();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

        });
        token5.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start,int before, int count){
                token6.requestFocus();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

        });
        token6.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start,int before, int count){
                token7.requestFocus();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

        });
        token7.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start,int before, int count){
                token8.requestFocus();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

        });
        token8.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start,int before, int count){
                token9.requestFocus();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

        });
        token9.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start,int before, int count){
                token10.requestFocus();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

        });
    }

}
