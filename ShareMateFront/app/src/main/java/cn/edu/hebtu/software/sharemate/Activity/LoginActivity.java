package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.edu.hebtu.software.sharemate.R;


public class LoginActivity extends AppCompatActivity {

    private TextView tvSpinner;
    private Button btnSpinner;
    private TextView login;
    private Button btnTrue;
    private TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        login.setOnClickListener(new pswdClickListener());
        btnTrue.setOnClickListener(new trueClickListener());
        register.setOnClickListener(new registerClickListener());
        tvSpinner.setOnClickListener(new SpinnerClickListener());
        btnSpinner.setOnClickListener(new SpinnerClickListener());
    }
    private void findViews(){
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        btnTrue = findViewById(R.id.btn_true);
        tvSpinner = findViewById(R.id.tv_spinner);
        btnSpinner = findViewById(R.id.btn_spinner);
    }
    //选择地区和地区代码
    private class SpinnerClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this,CountryActivity.class);
            startActivityForResult(intent, 12);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 12:
                if (resultCode == RESULT_OK) {
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

    //点击密码登录
    private class pswdClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this,PhonePswdLoginActivity.class);
            startActivity(intent);
        }
    }
    //点击确定按钮
    private class trueClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this,InputCodeActivity.class);
            startActivity(intent);
        }
    }
    //注册
    private class registerClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
    }
}
