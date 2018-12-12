package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.VerifyCodeView;

public class VerifyCodeActivity extends AppCompatActivity {

    private VerifyCodeView verifyCodeView;
    private ImageView back;
    private Button btnTrue;
    private TextView tvResend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        findViews();
        back.setOnClickListener(new backClickListener());
        btnTrue.setOnClickListener(new ButtonClickListener());
        verifyCodeView.setInputCompleteListener(new inputCompleteListener());
    }
    private void findViews(){
        verifyCodeView = findViewById(R.id.verify_code_view);
        back = findViewById(R.id.iv_back);
        btnTrue = findViewById(R.id.btn_true);
        tvResend = findViewById(R.id.tv_resend);
    }
    //输入验证码
    private class inputCompleteListener implements VerifyCodeView.InputCompleteListener{

        @Override
        public void inputComplete() {
//            Toast.makeText(VerifyCodeActivity.this,"inputComplete:" +
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
            Intent intent = new Intent(VerifyCodeActivity.this,PhonePswdLoginActivity.class);
            startActivity(intent);
        }
    }
    //点击确定按钮
    private class ButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(VerifyCodeActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }
}
