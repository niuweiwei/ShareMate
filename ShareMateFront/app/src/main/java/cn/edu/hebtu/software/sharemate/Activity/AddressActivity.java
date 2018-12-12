package cn.edu.hebtu.software.sharemate.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.hebtu.software.sharemate.R;

public class AddressActivity extends AppCompatActivity {
    private EditText editText;
    private TextView finishText;
    private TextView titleText;
    private TextView alterText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        editText = findViewById(R.id.content);
        finishText = findViewById(R.id.finish);
        titleText = findViewById(R.id.title);
        alterText = findViewById(R.id.alter);
        alterText.setText("请输入2-40个字符");
        titleText.setText(getIntent().getStringExtra("msg"));
        editText.setText(getIntent().getStringExtra("content"));
        ImageView imageView = findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressActivity.this.finish();
            }
        });
        finishText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.length()<2){
                    alterText.setText("您输入的小于2个字符");
                    alterText.setTextColor(getResources().getColor(R.color.warmRed));
                }else{
                    String content = editText.getText().toString();
                    //把内容存到数据库中
                    AddressActivity.this.finish();
                }
            }
        });
    }
}
