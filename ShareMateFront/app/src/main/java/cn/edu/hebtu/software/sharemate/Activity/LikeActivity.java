package cn.edu.hebtu.software.sharemate.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.LikeListAdapter;
import cn.edu.hebtu.software.sharemate.Bean.LikeBean;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class LikeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        //将设置的日期进行格式化 将String类型转化成日期类型
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date();
        Date date2 = new Date();
        Date date3 = new Date();
        Date date4 = new Date();
        Date date5 = new Date();
        Date date6 = new Date();
        try {
             date1 = format.parse("2018-4-30");
             date2 = format.parse("2018-5-1");
             date3 = format.parse("2018-7-25");
             date4 = format.parse("2018-9-6");
             date5 = format.parse("2018-10-30");
             date6 = format.parse("2018-11-28");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //准备Adapter的数据源
        List<LikeBean> likes = new ArrayList<>();
        UserBean user1 = new UserBean("小仙女",R.drawable.niuweiwei);
        UserBean user2 = new UserBean("小菲菲",R.drawable.mengfeifei);
        UserBean user3 = new UserBean("狗莹",R.drawable.sunliying);
        UserBean user4 = new UserBean("我昭",R.drawable.wangzhao);
        UserBean user5 = new UserBean("低调的仙女姐姐",R.drawable.wangou);
        UserBean user6 = new UserBean("白头鞋老",R.drawable.baijingting);
        likes.add(new LikeBean(user1,date1,R.drawable.note1));
        likes.add(new LikeBean(user2,date2,R.drawable.note1));
        likes.add(new LikeBean(user3,date3,R.drawable.note2));
        likes.add(new LikeBean(user4,date4,R.drawable.note3));
        likes.add(new LikeBean(user5,date5,R.drawable.note4));
        likes.add(new LikeBean(user6,date6,R.drawable.note1));

        //绑定Adapter
        LikeListAdapter adapter = new LikeListAdapter(this,likes,R.layout.like_list_item_layout);
        ListView listView = findViewById(R.id.lv_like);
        listView.setAdapter(adapter);

        //获取返回按钮
        Button back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
