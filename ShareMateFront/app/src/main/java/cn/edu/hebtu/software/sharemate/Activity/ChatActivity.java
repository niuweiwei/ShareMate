package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.ChatListAdapter;
import cn.edu.hebtu.software.sharemate.Bean.ChatBean;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class ChatActivity extends AppCompatActivity {

    private List<ChatBean> chats = new ArrayList<>();
    private UserBean me ;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        me = new UserBean("bjt的小公主",R.drawable.me);
        listView = findViewById(R.id.lv_content);

        Intent intent = getIntent();
        UserBean user = (UserBean) intent.getSerializableExtra("user");
        TextView nameView = findViewById(R.id.tv_name);
        nameView.setText(user.getUserName());

        switch (user.getUserName()){
            case "白敬亭":
                chats.add(new ChatBean(me,"亲爱的，在么？",0));
                chats.add(new ChatBean(user,"在呢，刚录完明星大侦探。怎么了",1));
                chats.add(new ChatBean(me,"明天我们出去吃饭吧，吃你喜欢吃的",0));
                chats.add(new ChatBean(user,"那明天我们一起出去吃火锅！",1));
                break;
            case "王鸥":
                chats.add(new ChatBean(user,"小公主，我收工了",1));
                chats.add(new ChatBean(me,"鸥姐辛苦了!",0));
                chats.add(new ChatBean(user,"刚刚录完明星大侦探",1));
                chats.add(new ChatBean(user,"我刚才还和小白说你呢",1));
                chats.add(new ChatBean(me,"明天你有空么?",0));
                chats.add(new ChatBean(me,"我和小白说明天去吃火锅!",0));
                chats.add(new ChatBean(user,"好呀",1));
                chats.add(new ChatBean(me,"说定了呀",0));
                break;
            case "你的小可爱":
                chats.add(new ChatBean(me,"您的小公主已上线!",0));
                chats.add(new ChatBean(user,"臭不要脸",1));
                chats.add(new ChatBean(me,"你上回分享的那个大理石眼影盘也太好看了吧!",0));
                chats.add(new ChatBean(user,"那是，也不看是谁的眼光",1));
                chats.add(new ChatBean(me,"你是魔鬼么做个人不好么？",0));
                break;
            case "plly":
                chats.add(new ChatBean(me,"暑假在必胜客赚了多少钱?",0));
                chats.add(new ChatBean(user,"没多少,每天又累又困，工资还很少.",1));
                chats.add(new ChatBean(me,"没关系马上就开学了。想我么?",0));
                chats.add(new ChatBean(user,"天哪！时间过的也太快了吧！已经开学了！",1));
                break;
        }
        ChatListAdapter adapter = new ChatListAdapter(this,chats,R.layout.chat_left_item_layout,R.layout.chat_right_item_layout);
        listView.setAdapter(adapter);

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
