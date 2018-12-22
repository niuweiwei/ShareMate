package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.ForgetPasswordUtil;
import cn.edu.hebtu.software.sharemate.tools.TelephoneUtils;

public class ForgetPasswordActivity extends AppCompatActivity {

    private TextView tvSpinner;
    private Button btnSpinner;
    private ImageView back;
    private Button btnTrue;//发送验证码
    private EditText etPhoneNum;//电话号码
    private String phoneNum;
    private UserBean user = new UserBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        findViews();

        back.setOnClickListener(new backClickListener());
        btnTrue.setOnClickListener(new ButtonClickListener());
        btnSpinner.setOnClickListener(new SpinnerClickListener());
        tvSpinner.setOnClickListener(new SpinnerClickListener());
    }
    private void findViews(){
        back = findViewById(R.id.iv_back);
        btnTrue = findViewById(R.id.btn_true);
        btnSpinner = findViewById(R.id.btn_spinner);
        tvSpinner = findViewById(R.id.tv_spinner);
        etPhoneNum = findViewById(R.id.et_phone);
    }
    //选择地区和地区代码
    private class SpinnerClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ForgetPasswordActivity.this,CountryActivity.class);
            startActivityForResult(intent, 12);
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
//                    String countryName = bundle.getString("countryName");
                    String countryNumber = bundle.getString("countryNumber");

                    tvSpinner.setText(countryNumber);
//                    tv1.setText(countryName);

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
            Intent intent = new Intent(ForgetPasswordActivity.this,PhonePswdLoginActivity.class);
            startActivity(intent);
        }
    }
    //点击确定
    private class ButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            phoneNum = etPhoneNum.getText().toString();
            if (!phoneNum.equals("")) {
                //判断手机号码格式,11位数字
                boolean resultPhone = TelephoneUtils.isPhone(phoneNum);
                if (resultPhone == true) {
                    user.setUserPhone(phoneNum);
//                    Intent intent = new Intent(ForgetPasswordActivity.this,VerifyCodeActivity.class);
//                    intent.putExtra("phoneNum",phoneNum);
//                    startActivity(intent);
                    ForgetPasswordUtil forgetPasswordUtil = new ForgetPasswordUtil(ForgetPasswordActivity.this);
                    forgetPasswordUtil.execute(user);
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
                    etPhoneNum.requestFocus();
                }
            }else {
                Toast.makeText(ForgetPasswordActivity.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                etPhoneNum.requestFocus();
            }

        }
    }
}
