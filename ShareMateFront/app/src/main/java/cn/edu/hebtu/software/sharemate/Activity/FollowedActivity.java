package cn.edu.hebtu.software.sharemate.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.FollowedListAdapter;
import cn.edu.hebtu.software.sharemate.Bean.FollowBean;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class FollowedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followed);

        //为 ListView 中的每一个子项准备数据
        List<FollowBean> follows = new ArrayList<>();

        UserBean user1 = new UserBean("小仙女",R.drawable.niuweiwei);
        UserBean user2 = new UserBean("小菲菲",R.drawable.mengfeifei);
        UserBean user3 = new UserBean("狗莹",R.drawable.sunliying);
        UserBean user4 = new UserBean("我昭",R.drawable.wangzhao);
        UserBean user5 = new UserBean("低调的仙女姐姐",R.drawable.wangou);
        UserBean user6 = new UserBean("白头鞋老",R.drawable.baijingting);

        //将字符串转换成 Date 存入对象
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date();
        Date date2 = new Date();
        Date date3 = new Date();
        Date date4 = new Date();
        Date date5 = new Date();
        Date date6 = new Date();
        try {
            date1 = formatter.parse("2018-1-30");
            date2 = formatter.parse("2018-4-5");
            date3 = formatter.parse("2018-7-8");
            date4 = formatter.parse("2018-9-27");
            date5 = formatter.parse("2018-10-15");
            date6 = formatter.parse("2018-11-28");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        follows.add(new FollowBean(user1,date1));
        follows.add(new FollowBean(user2,date2));
        follows.add(new FollowBean(user3,date3));
        follows.add(new FollowBean(user4,date4));
        follows.add(new FollowBean(user5,date5));
        follows.add(new FollowBean(user6,date6));

        //绑定 Adapter 适配器
        ListView listView = findViewById(R.id.lv_followed);
        FollowedListAdapter adapter = new FollowedListAdapter(this,R.layout.followed_list_item_layout,follows);
        listView.setAdapter(adapter);

        //为返回按钮绑定监听器
        Button back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
