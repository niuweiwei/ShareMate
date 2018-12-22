package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.LoginUtil;
import cn.edu.hebtu.software.sharemate.tools.TelephoneUtils;


public class LoginActivity extends AppCompatActivity {

    private TextView tvSpinner;
    private Button btnSpinner;
    private TextView pswdLogin;
    private Button btnTrue;
    private TextView register;
    private EditText etPhone;
    private String phone;
    private UserBean user = new UserBean();
    private boolean resultPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        pswdLogin.setOnClickListener(new pswdClickListener());
        btnTrue.setOnClickListener(new trueClickListener());
        register.setOnClickListener(new registerClickListener());
        tvSpinner.setOnClickListener(new SpinnerClickListener());
        btnSpinner.setOnClickListener(new SpinnerClickListener());

        etPhone.setOnFocusChangeListener(new FocusChangeListener());
    }
    private void findViews(){
        pswdLogin = findViewById(R.id.pswd_login);
        register = findViewById(R.id.register);
        btnTrue = findViewById(R.id.btn_true);
        tvSpinner = findViewById(R.id.tv_spinner);
        btnSpinner = findViewById(R.id.btn_spinner);
        etPhone = findViewById(R.id.et_phone);
    }

    /**
     * 验证输入的手机号是否存在
     */
    private class FocusChangeListener implements View.OnFocusChangeListener{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus){

            }else {

            }
        }
    }

    /**
     * 选择地区和地区代码
     */
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

    /**
     * 点击密码登录
     */
    private class pswdClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this,PhonePswdLoginActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 点击确定按钮
     */
    private class trueClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
//            btnTrue.setFocusable(true);
//            btnTrue.setFocusableInTouchMode(true);
//            btnTrue.requestFocus();
            phone = etPhone.getText().toString();
            Log.e("phone",phone);
            if (!"".equals(phone)){
                //判断手机号码格式,11位数字
                resultPhone = TelephoneUtils.isPhone(phone);
                if (resultPhone == true){
                    user.setUserPhone(phone);
                    LoginUtil loginUtil = new LoginUtil(LoginActivity.this);
                    loginUtil.execute(user);
                }else {
                    Toast.makeText(LoginActivity.this, "请输入正确格式的手机号",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(LoginActivity.this,"请输入手机号码",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 注册
     */
    private class registerClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
    }
}
