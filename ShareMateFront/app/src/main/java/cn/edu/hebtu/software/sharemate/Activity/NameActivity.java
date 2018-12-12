package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class NameActivity extends AppCompatActivity {

    private TextView msgText;
    private TextView finishText;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        msgText = findViewById(R.id.msg);
        msgText.setText("请输入2-8个字符");
        editText = findViewById(R.id.content);
        finishText = findViewById(R.id.finish);
        Intent intent = getIntent();
        UserBean user = (UserBean) intent.getSerializableExtra("user");
            editText.setText(user.getUserName());
        finishText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.length()<2){
                    msgText.setText("您输入的小于2个字符");
                }else{
                    String content = editText.getText().toString();
                    //把内容存到数据库中
                    NameActivity.this.finish();
                }
            }
        });
        ImageView imageView = findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NameActivity.this.finish();
            }
        });
    }
}
