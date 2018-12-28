package cn.edu.hebtu.software.sharemate.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class SettingActivity extends AppCompatActivity {
    private ImageView imageView;
    private UserBean user;
    private ArrayList<Integer> type = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        type = getIntent().getIntegerArrayListExtra("type");
        imageView = findViewById(R.id.back);
        LinearLayout setPer = findViewById(R.id.setPer);
        LinearLayout zanText = findViewById(R.id.zan);
        user = (UserBean) getIntent().getSerializableExtra("user");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,MainActivity.class);
                intent.putExtra("flag","my");
                intent.putExtra("userId",user.getUserId());
                intent.putIntegerArrayListExtra("type",type);
                Log.e("set",user.getUserId()+"");
                startActivity(intent);
            }
        });
        setPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,PersonalActivity.class);
                intent.putExtra("user",user);
                intent.putIntegerArrayListExtra("type",type);
                intent.putExtra("sign","set");
                startActivity(intent);
            }
        });
        zanText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SettingActivity.this,ZanActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }
}
