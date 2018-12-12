package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import cn.edu.hebtu.software.sharemate.R;

public class SelectInterestActivity extends AppCompatActivity {

    private Button button;
    private CheckBox cb1,cb2,cb3,cb4,cb5,cb6;
    private String str1,str2,str3,str4,str5,str6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_interest);
        findViews();
        button.setOnClickListener(new ButtonClickListener());
        cb1.setOnClickListener(new CheckBoxClickListener());
        cb2.setOnClickListener(new CheckBoxClickListener());
        cb3.setOnClickListener(new CheckBoxClickListener());
        cb4.setOnClickListener(new CheckBoxClickListener());
        cb5.setOnClickListener(new CheckBoxClickListener());
        cb6.setOnClickListener(new CheckBoxClickListener());
    }
    private void findViews(){
        button = findViewById(R.id.btn_next);
        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);
        cb4 = findViewById(R.id.cb4);
        cb5 = findViewById(R.id.cb5);
        cb6 = findViewById(R.id.cb6);
    }
    private class ButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SelectInterestActivity.this,SelectTopicActivity.class);
            startActivity(intent);
        }
    }
    private class CheckBoxClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cb1:
                    if (cb1.isChecked()){
                        str1 = "美妆";
                    }
                    break;
                case R.id.cb2:
                    if (cb2.isChecked()){
                        str2 = "旅行";
                    }
                    break;
                case R.id.cb3:
                    if (cb3.isChecked()){
                        str3 = "运动";
                    }
                    break;
                case R.id.cb4:
                    if (cb4.isChecked()){
                        str4 = "美食";
                    }
                    break;
                case R.id.cb5:
                    if (cb5.isChecked()){
                        str5 = "科技";
                    }
                    break;
                case R.id.cb6:
                    if (cb6.isChecked()){
                        str6 = "动漫";
                    }
                    break;
            }
        }
    }
}
