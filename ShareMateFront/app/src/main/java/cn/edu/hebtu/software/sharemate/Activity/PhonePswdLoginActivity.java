package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.hebtu.software.sharemate.R;

public class PhonePswdLoginActivity extends AppCompatActivity {

    private TextView tvSpinner;
    private Button btnSpinner;
    private ImageView back;
    private Button btnLogin;
    private TextView forgetPswd;
    private TextView codeLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_pswd_login);
        findViews();
        back.setOnClickListener(new backClickListener());
        btnLogin.setOnClickListener(new ButtonClickListener());
        forgetPswd.setOnClickListener(new forgetPswdClickListener());
        codeLogin.setOnClickListener(new codeLoginClickListener());
        btnSpinner.setOnClickListener(new SpinnerClickListener());
        tvSpinner.setOnClickListener(new SpinnerClickListener());
    }
    private void findViews(){
        back = findViewById(R.id.iv_back);
        btnLogin = findViewById(R.id.btn_login);
        forgetPswd = findViewById(R.id.tv_forget_password);
        codeLogin = findViewById(R.id.tv_code_login);
        btnSpinner = findViewById(R.id.btn_spinner);
        tvSpinner = findViewById(R.id.tv_spinner);
    }
    //选择地区和地区代码
    private class SpinnerClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PhonePswdLoginActivity.this,CountryActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode)
        {
            case 12:
                if (resultCode == RESULT_OK)
                {
                    Bundle bundle = data.getExtras();
                    String countryNumber = bundle.getString("countryNumber");

                    tvSpinner.setText(countryNumber);

                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //点击返回
    private class backClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PhonePswdLoginActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }
    //点击登录按钮
    private class ButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PhonePswdLoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }
    //忘记密码
    private class forgetPswdClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PhonePswdLoginActivity.this,ForgetPasswordActivity.class);
            startActivity(intent);
        }
    }
    //验证码登录
    private class codeLoginClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PhonePswdLoginActivity.this,VerifyCodeActivity.class);
            startActivity(intent);
        }
    }
}
