package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.edu.hebtu.software.sharemate.R;

public class FabuActivity extends AppCompatActivity {
    private PopupWindow window = null;
    private LinearLayout root = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabu);

        //显示图片
        final Intent intent = getIntent();
        final String code = intent.getStringExtra("code");
        ImageView imageView = findViewById(R.id.imageView);
        if (code != null) {
            if (code.equals("1")) {
                byte[] bis = intent.getByteArrayExtra("pic");
                Bitmap  bitmap1 = BitmapFactory.decodeByteArray(bis, 0, bis.length);
                imageView.setImageBitmap(bitmap1);
            } else if (code.equals("2")) {
                String picturePath = intent.getStringExtra("pic1");
                Log.e("code",code);
                Bitmap  bitmap2 = BitmapFactory.decodeFile(picturePath);
                imageView.setImageBitmap(bitmap2);

            } else {
                Log.e("picture", "图片未获取到");
            }
        }
        //退出发布页面
        root = findViewById(R.id.fabu_root);
        window = new PopupWindow(root, RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        Button btnShare = findViewById(R.id.guanbi);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(window.isShowing()){
                    window.dismiss();
                }else{
                    showPopupWindow(root);
                    addBackgroundAlpha(0.7f);
                }
            }
        });
        Button btnShare1 = findViewById(R.id.cuncaogao);
        btnShare1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(window.isShowing()){
                    window.dismiss();
                }else{
                    showPopupWindow(root);
                    addBackgroundAlpha(0.7f);
                }
            }
        });


        //获取话题
        LinearLayout linearLayout=findViewById(R.id.btn_topic);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTopicPopupWindow();
            }
        });



        //获取位置
        LinearLayout linearLayout1=findViewById(R.id.add_position);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPositionPopupWindow();
            }
        });
    }




    public void showPositionPopupWindow(){
        LayoutInflater inflater =getLayoutInflater();
        View view = inflater.inflate(R.layout.position_popupwindow,null);
        window.setContentView(view);
        final Button btn1=view.findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_postion);
                String position=btn1.getText().toString();
                textView.setText(position);
                window.dismiss();
            }
        });
        final Button btn2=view.findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_postion);
                String position=btn2.getText().toString();
                textView.setText(position);
                window.dismiss();
            }
        });
        final Button btn3=view.findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_postion);
                String position=btn3.getText().toString();
                textView.setText(position);
                window.dismiss();
            }
        });
        final Button btn4=view.findViewById(R.id.button4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_postion);
                String position=btn4.getText().toString();
                textView.setText(position);
                window.dismiss();
            }
        });
        final Button btn5=view.findViewById(R.id.button5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_postion);
                String position=btn5.getText().toString();
                textView.setText(position);
                window.dismiss();
            }
        });
        final Button btn6=view.findViewById(R.id.button6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_postion);
                String position=btn6.getText().toString();
                textView.setText(position);
                window.dismiss();
            }
        });
        final Button btn7=view.findViewById(R.id.button7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_postion);
                String position=btn7.getText().toString();
                textView.setText(position);
                window.dismiss();
            }
        });
        final Button btn8=view.findViewById(R.id.button8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_postion);
                String position=btn8.getText().toString();
                textView.setText(position);
                window.dismiss();
            }
        });
        final Button btn9=view.findViewById(R.id.button9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_postion);
                String position=btn9.getText().toString();
                textView.setText(position);
                window.dismiss();
            }
        });
        final Button btn10=view.findViewById(R.id.button10);
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_postion);
                String position=btn10.getText().toString();
                textView.setText(position);
                window.dismiss();
            }
        });
        window.showAtLocation(root, Gravity.BOTTOM,0,0);
    }




    //展示topicpopupwindow
    private void showTopicPopupWindow(){
        LayoutInflater inflater =getLayoutInflater();
        View view = inflater.inflate(R.layout.topic_popupwindow,null);
        window.setContentView(view);
        final Button button1=view.findViewById(R.id.meizhuang1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button1.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button2=view.findViewById(R.id.meizhuang2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button2.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button3=view.findViewById(R.id.meizhuang3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button3.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button4=view.findViewById(R.id.meizhuang4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button4.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button5=view.findViewById(R.id.lvxing1);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button5.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button6=view.findViewById(R.id.lvxing2);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button6.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button7=view.findViewById(R.id.lvxing3);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button7.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button8=view.findViewById(R.id.lvxing4);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button8.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button9=view.findViewById(R.id.yundong1);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button9.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button10=view.findViewById(R.id.yundong2);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button10.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button11=view.findViewById(R.id.yundong3);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button11.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });

        final Button button12=view.findViewById(R.id.meishi1);
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button12.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button13=view.findViewById(R.id.meishi2);
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button13.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button14=view.findViewById(R.id.meishi3);
        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button14.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button15=view.findViewById(R.id.meishi4);
        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button15.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button16=view.findViewById(R.id.keji1);
        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button16.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button17=view.findViewById(R.id.keji2);
        button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button17.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button18=view.findViewById(R.id.keji3);
        button18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button18.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button19=view.findViewById(R.id.keji4);
        button19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button19.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button20=view.findViewById(R.id.dongman1);
        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button20.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button21=view.findViewById(R.id.dongman2);
        button21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button21.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button22=view.findViewById(R.id.dongman3);
        button22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button22.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });
        final Button button23=view.findViewById(R.id.dongman4);
        button23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.wenzi_topic);
                String tips=button23.getText().toString();
                textView.setText(tips);
                window.dismiss();
            }
        });

        window.showAtLocation(root, Gravity.BOTTOM,0,0);

    }





    //保存退出popupwindow
    private void showPopupWindow(LinearLayout root){

        //设置显示的视图
        LayoutInflater inflater =getLayoutInflater();
        View view = inflater.inflate(R.layout.back_popupwindow_layout,null);
        Button btnCamera = view.findViewById(R.id.btn_save);
        Button btnPhoto = view.findViewById(R.id.btn_dissave);
        Button btnCancel = view.findViewById(R.id.btn_cancel1);
        //为弹出框中的每一个按钮
        ClickListener listener = new ClickListener();
        btnCamera.setOnClickListener(listener);
        btnPhoto.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);

        //将自定义的视图添加到 popupWindow 中
        window.setContentView(view);
        //控制 popupwindow 再点击屏幕其他地方时自动消失
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //在弹窗消失时调用
                addBackgroundAlpha(1f);
            }
        });
        //显示 popupWindow 设置 弹出框的位置
        window.showAtLocation(root, Gravity.BOTTOM,0,0);
    }
    private class ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_save:
                    finish();
                    break;
                case R.id.btn_dissave:
                    finish();
                    break;

                case R.id.btn_cancel1:
                    window.dismiss();
                    break;
            }
        }
    }
    // 弹出选项框时为背景加上透明度
    private void addBackgroundAlpha(float alpha){
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = alpha;
        getWindow().setAttributes(params);
    }




}









