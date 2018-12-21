package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.VerifyCodeView;

public class InputCodeActivity extends AppCompatActivity {

    private VerifyCodeView verifyCodeView;
    private ImageView back;
    private Button btnTrue;
    private TextView tvResend;
    private TextView tvPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_code);
        findViews();
        back.setOnClickListener(new backClickListener());
        btnTrue.setOnClickListener(new ButtonClickListener());
        tvPassword.setOnClickListener(new passwordClickListener());
        verifyCodeView.setInputCompleteListener(new inputCompleteListener());
    }
    private void findViews(){
        back = findViewById(R.id.iv_back);
        btnTrue = findViewById(R.id.btn_true);
        tvResend = findViewById(R.id.tv_resend);
        tvPassword = findViewById(R.id.tv_password);
        verifyCodeView = findViewById(R.id.verify_code_view);

    }
    //输入验证码
    private class inputCompleteListener implements VerifyCodeView.InputCompleteListener{

        @Override
        public void inputComplete() {
//            Toast.makeText(InputCodeActivity.this,"inputComplete:" +
//                    verifyCodeView.getEditContent(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void invalidContent() {

        }
    }
    //点击返回
    private class backClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(InputCodeActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }
    //点击确定按钮
    private class ButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //跳到首页
            ArrayList<Integer> list = new ArrayList<>();
            list.add(2);list.add(4);list.add(5);list.add(6);
            Intent intent = new Intent(InputCodeActivity.this,MainActivity.class);
            intent.putExtra("userId",2);
            //传递int型数组
            intent.putIntegerArrayListExtra("type", list);
            startActivity(intent);
        }
    }
    //密码登录
    private class passwordClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(InputCodeActivity.this,PhonePswdLoginActivity.class);
            startActivity(intent);
        }
    }
}
