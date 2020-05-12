package com.project.civillian.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.project.civillian.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class ResetPasswordActivity extends AppCompatActivity {

    CheckBox showPassword;
    EditText tfPassword, tfPasswordRetype;
    TextView tvRegister;
    Button btSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initComponent();
    }
    public void initComponent(){
        tfPassword = findViewById(R.id.tf_password);
        tfPasswordRetype = findViewById(R.id.tf_password_retype);
        showPassword = findViewById(R.id.show_password);
        tvRegister = findViewById(R.id.tv_register);
        btSubmit = findViewById(R.id.bt_submit);

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tfPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    tfPasswordRetype.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    tfPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    tfPasswordRetype.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        tvRegister.setOnTouchListener(new CompoundButton.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(ResetPasswordActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                ResetPasswordActivity.this.finish();
                return true;
            }
        });
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                ResetPasswordActivity.this.finish();
            }
        });
    }

}
