package cn.edu.hebtu.software.sharemate.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.CustomAdapter;
import cn.edu.hebtu.software.sharemate.Bean.NoteBean;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class FollowDetailActivity extends AppCompatActivity implements CustomAdapter.Callback,AdapterView.OnClickListener{
    private boolean isIconChange = true;
    private boolean c1 = true;
    private CustomAdapter customAdapter=null;
    private List<NoteBean> notes;
    private ListView listView;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        Button button = findViewById(R.id.fanhui);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FollowDetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
//        UserBean user=new UserBean("小明",R.drawable.a1);
//        NoteBean st1= new NoteBean(R.drawable.dm10,"路飞手办，路飞公仔。。。。。。。。","笔记一",user,"昨天12:00",88,77,88,99);
//        NoteBean st2= new NoteBean(R.drawable.dm6,"海贼王手办公仔模型Q版","笔记一",user,"昨天12:00",98,77,88,99);
//        NoteBean st3= new NoteBean(R.drawable.dm2,"路飞手办，三个路飞Q版模型","笔记一",user,"昨天12:00",18,77,88,99);
//        NoteBean st4= new NoteBean(R.drawable.dm8,"海贼王手办，路飞公仔全套","笔记一",user,"昨天12:00",28,77,88,99);
//        NoteBean st5= new NoteBean(R.drawable.dm1,"路飞生日礼物全套，onepiece手办模型","笔记一",user,"昨天12:00",48,77,88,99);
        notes = new ArrayList<>();
//        notes.add(st1);
//        notes.add(st2);
//        notes.add(st3);
//        notes.add(st4);
//        notes.add(st5);
        listView = findViewById(R.id.list);
        customAdapter = new CustomAdapter(this,R.layout.list_item,this, notes);
        listView.setAdapter(customAdapter);

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                //使EditText触发一次失去焦点事件
                v.setFocusable(false);
//                v.setFocusable(true); //这里不需要是因为下面一句代码会同时实现这个功能
                v.setFocusableInTouchMode(true);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void click(View v) {
        switch (v.getId()) {
            case R.id.guannzhu:
                Log.e("tag1",v.getTag()+"");
                if (isIconChange) {
                    v.setBackgroundResource(R.drawable.yiguanzhu);
                    Log.e("tag", v.getTag() + "");
                    isIconChange = false;
                } else {
                    v.setBackgroundResource(R.drawable.guanzhu);
                    isIconChange = true;
                }
            break;
            case R.id.dianzan:
                int position = (int) v.getTag();
                Log.e("tag2",v.getTag()+"");
                if(c1){
                    v.setBackgroundResource(R.drawable.xihuan2);
                    int c = notes.get(position).getZancount();c++;
                    notes.get(position).setZancount(c);
                    c1=false;
                    customAdapter.notifyDataSetChanged();
                }
                else{
                    v.setBackgroundResource(R.drawable.xinxin);
                    int c = notes.get(position).getZancount();c--;
                    notes.get(position).setZancount(c);
                    c1=true;
                    customAdapter.notifyDataSetChanged();
                }
            break;
        }
    }
}

