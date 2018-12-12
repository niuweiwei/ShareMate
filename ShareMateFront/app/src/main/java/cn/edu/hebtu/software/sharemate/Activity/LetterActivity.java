package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.LetterListAdapter;
import cn.edu.hebtu.software.sharemate.Bean.LetterBean;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class LetterActivity extends AppCompatActivity {

    private List<LetterBean> letters = new ArrayList<>();
    private ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter);

        //准备数据源
        UserBean user1 = new UserBean("白敬亭",R.drawable.baijingting);
        UserBean user2 = new UserBean("王鸥",R.drawable.wangou);
        UserBean user3 = new UserBean("你的小可爱",R.drawable.mengfeifei);
        UserBean user4 = new UserBean("plly",R.drawable.sunliying);
        //将字符串格式化为日期
        Date date1 = new Date();
        Date date2 = new Date();
        Date date3= new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            date1 = format.parse("2018-12-2");
            date2 = format.parse("2018-10-5");
            date3 = format.parse("2018-9-30");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        letters.add(new LetterBean(user1,"那明天我们一起去吃火锅!",date1));
        letters.add(new LetterBean(user2,"说定了呀",date1));
        letters.add(new LetterBean(user3,"你是魔鬼么做个人不好么？",date2));
        letters.add(new LetterBean(user4,"天哪！时间过的也太快了吧！已经开学了！",date3));

        //绑定适配器
        listView = findViewById(R.id.lv_contact);
        LetterListAdapter adapter = new LetterListAdapter(this,R.layout.letter_list_item_layout,letters);
        listView.setAdapter(adapter);

        //为每一个子项绑定上点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LetterActivity.this,ChatActivity.class);
                intent.putExtra("user",letters.get(position).getUser());
                startActivity(intent);
            }
        });

        //为返回按钮绑定事件
        Button back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
