package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.edu.hebtu.software.sharemate.R;

public class SelectTopicActivity extends AppCompatActivity implements View.OnClickListener{

    private int userId;
    //定义一个标记
    private Boolean b_sub_square = false;
    private Button button;
    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,
            btn15,btn16,btn17,btn18,btn19,btn20,btn21,btn22,btn23;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_topic);
        findViews();
        userId = getIntent().getIntExtra("userId",0);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
        btn15.setOnClickListener(this);
        btn16.setOnClickListener(this);
        btn17.setOnClickListener(this);
        btn18.setOnClickListener(this);
        btn19.setOnClickListener(this);
        btn20.setOnClickListener(this);
        btn21.setOnClickListener(this);
        btn22.setOnClickListener(this);
        btn23.setOnClickListener(this);
        button.setOnClickListener(this);
    }
    private void findViews(){
        button = findViewById(R.id.button);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn10 = findViewById(R.id.btn10);
        btn11 = findViewById(R.id.btn11);
        btn12 = findViewById(R.id.btn12);
        btn13 = findViewById(R.id.btn13);
        btn14 = findViewById(R.id.btn14);
        btn15 = findViewById(R.id.btn15);
        btn16 = findViewById(R.id.btn16);
        btn17 = findViewById(R.id.btn17);
        btn18 = findViewById(R.id.btn18);
        btn19 = findViewById(R.id.btn19);
        btn20 = findViewById(R.id.btn20);
        btn21 = findViewById(R.id.btn21);
        btn22 = findViewById(R.id.btn22);
        btn23 = findViewById(R.id.btn23);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn1.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn1.setActivated(b_sub_square);
                }
                break;
            case R.id.btn2:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn2.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn2.setActivated(b_sub_square);
                }
                break;
            case R.id.btn3:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn3.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn3.setActivated(b_sub_square);
                }
                break;
            case R.id.btn4:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn4.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn4.setActivated(b_sub_square);
                }
                break;
            case R.id.btn5:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn5.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn5.setActivated(b_sub_square);
                }
                break;
            case R.id.btn6:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn6.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn6.setActivated(b_sub_square);
                }
                break;
            case R.id.btn7:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn7.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn7.setActivated(b_sub_square);
                }
                break;
            case R.id.btn8:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn8.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn8.setActivated(b_sub_square);
                }
                break;
            case R.id.btn9:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn9.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn9.setActivated(b_sub_square);
                }
                break;
            case R.id.btn10:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn10.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn10.setActivated(b_sub_square);
                }
                break;
            case R.id.btn11:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn11.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn11.setActivated(b_sub_square);
                }
                break;
            case R.id.btn12:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn12.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn12.setActivated(b_sub_square);
                }
                break;
            case R.id.btn13:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn13.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn13.setActivated(b_sub_square);
                }
                break;
            case R.id.btn14:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn14.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn14.setActivated(b_sub_square);
                }
                break;
            case R.id.btn15:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn15.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn15.setActivated(b_sub_square);
                }
                break;
            case R.id.btn16:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn16.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn16.setActivated(b_sub_square);
                }
                break;
            case R.id.btn17:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn17.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn17.setActivated(b_sub_square);
                }
                break;
            case R.id.btn18:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn18.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn18.setActivated(b_sub_square);
                }
                break;
            case R.id.btn19:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn19.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn19.setActivated(b_sub_square);
                }
                break;
            case R.id.btn20:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn20.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn20.setActivated(b_sub_square);
                }
                break;
            case R.id.btn21:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn21.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn21.setActivated(b_sub_square);
                }
                break;
            case R.id.btn22:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn22.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn22.setActivated(b_sub_square);
                }
                break;
            case R.id.btn23:
                if(! b_sub_square){
                    b_sub_square = true;
                    //设置是否被激活状态，true表示被激活
                    btn23.setActivated(b_sub_square);
                }else {
                    b_sub_square = false;
                    //设置是否被激活状态，false表示未被激活
                    btn23.setActivated(b_sub_square);
                }
                break;
            case R.id.button:
                Intent intent = new Intent(SelectTopicActivity.this,MainActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
                break;
        }
    }
}
