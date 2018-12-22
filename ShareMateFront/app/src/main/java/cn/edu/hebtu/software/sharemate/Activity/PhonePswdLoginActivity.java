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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.PasswordUtils;
import cn.edu.hebtu.software.sharemate.tools.PhonePswdLoginUtil;
import cn.edu.hebtu.software.sharemate.tools.TelephoneUtils;

public class PhonePswdLoginActivity extends AppCompatActivity {

    private TextView tvSpinner;
    private Button btnSpinner;
    private ImageView back;
    private Button btnLogin;
    private TextView forgetPswd;
    private TextView codeLogin;
    private EditText etPhone;
    private String phone;
    private EditText etPassword;
    private String password;
    private UserBean user = new UserBean();
    private boolean resultPhone;
    private boolean resultPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_pswd_login);
        findViews();
        back.setOnClickListener(new backClickListener());
        btnLogin.setOnClickListener(new ButtonClickListener());
        forgetPswd.setOnClickListener(new forgetPswdClickListener());
//        codeLogin.setOnClickListener(new codeLoginClickListener());
        btnSpinner.setOnClickListener(new SpinnerClickListener());
        tvSpinner.setOnClickListener(new SpinnerClickListener());
        etPhone.setOnFocusChangeListener(new FocusChangeListener());
        etPassword.setOnFocusChangeListener(new FocusChangeListener());
    }
    private void findViews(){
        back = findViewById(R.id.iv_back);
        btnLogin = findViewById(R.id.btn_login);
        forgetPswd = findViewById(R.id.tv_forget_password);
//        codeLogin = findViewById(R.id.tv_code_login);
        btnSpinner = findViewById(R.id.btn_spinner);
        tvSpinner = findViewById(R.id.tv_spinner);
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
    }

    /**
     * 根据手机号和密码判断该用户是否存在
     */
    private class FocusChangeListener implements View.OnFocusChangeListener{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()){
                case R.id.et_phone:
                    if (hasFocus){

                    }else {
                        phone = etPhone.getText().toString();
                        Log.e("phone",phone);
                        //判断手机号码格式,11位数字
                        resultPhone = TelephoneUtils.isPhone(phone);
                        if (resultPhone == true){
                            user.setUserPhone(phone);
                        }else {
                            Toast.makeText(PhonePswdLoginActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case R.id.et_password:
                    if (hasFocus){

                    }else {
                        password = etPassword.getText().toString();
                        Log.e("password",password);
                        //判断密码格式,8-16位数字和字母
                        resultPassword = PasswordUtils.isPassword(password);
                        if (resultPassword == true){
                            user.setUserPassword(password);
                        }else {
                            Toast.makeText(PhonePswdLoginActivity.this,"请输入正确的密码",Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }
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
            btnLogin.setFocusable(true);//设置可以获取焦点，但不一定获得
            btnLogin.setFocusableInTouchMode(true);
            btnLogin.requestFocus();//要获取焦点
            if (!phone.equals("") && !password.equals("")){
                PhonePswdLoginUtil phonePswdLoginUtil = new PhonePswdLoginUtil(PhonePswdLoginActivity.this);
                phonePswdLoginUtil.execute(user);
            }else {
                Toast.makeText(PhonePswdLoginActivity.this,"请输入手机号或密码",Toast.LENGTH_SHORT).show();
            }
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
}
