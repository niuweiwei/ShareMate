package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.edu.hebtu.software.sharemate.R;

public class PositionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        final Button button1=findViewById(R.id.btn1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent();
                String position=button1.getText().toString();
                intent1.putExtra("position",position);
                intent1.setClass(PositionActivity.this,FabuActivity.class);
                startActivity(intent1);
            }
        });
        final Button button2=findViewById(R.id.btn2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent();
                String position=button2.getText().toString();
                intent1.putExtra("position",position);
                intent1.setClass(PositionActivity.this,FabuActivity.class);
                startActivity(intent1);
            }
        });
        final Button button3=findViewById(R.id.btn3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent();
                String position=button3.getText().toString();
                intent1.putExtra("position",position);
                intent1.setClass(PositionActivity.this,FabuActivity.class);
                startActivity(intent1);
            }
        });
        final Button button4=findViewById(R.id.btn4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent();
                String position=button4.getText().toString();
                intent1.putExtra("position",position);
                intent1.setClass(PositionActivity.this,FabuActivity.class);
                startActivity(intent1);
            }
        });
        final Button button5=findViewById(R.id.btn5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent();
                String position=button5.getText().toString();
                intent1.putExtra("position",position);
                intent1.setClass(PositionActivity.this,FabuActivity.class);
                startActivity(intent1);
            }
        });
        final Button button6=findViewById(R.id.btn6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent();
                String position=button6.getText().toString();
                intent1.putExtra("position",position);
                intent1.setClass(PositionActivity.this,FabuActivity.class);
                startActivity(intent1);
            }
        });
        final Button button7=findViewById(R.id.btn7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent();
                String position=button7.getText().toString();
                intent1.putExtra("position",position);
                intent1.setClass(PositionActivity.this,FabuActivity.class);
                startActivity(intent1);
            }
        });
        final Button button8=findViewById(R.id.btn8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent();
                String position=button8.getText().toString();
                intent1.putExtra("position",position);
                intent1.setClass(PositionActivity.this,FabuActivity.class);
                startActivity(intent1);
            }
        });
        final Button button9=findViewById(R.id.btn9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent();
                String position=button9.getText().toString();
                intent1.putExtra("position",position);
                intent1.setClass(PositionActivity.this,FabuActivity.class);
                startActivity(intent1);
            }
        });
        final Button button10=findViewById(R.id.btn10);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent();
                String position=button10.getText().toString();
                intent1.putExtra("position",position);
                intent1.setClass(PositionActivity.this,FabuActivity.class);
                startActivity(intent1);
            }
        });
    }
}
