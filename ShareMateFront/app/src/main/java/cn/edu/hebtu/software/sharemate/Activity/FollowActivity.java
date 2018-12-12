package cn.edu.hebtu.software.sharemate.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.FocusAdapter;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;


public class FollowActivity extends AppCompatActivity {
    private UserBean user;
    private ListView listView;
    private FocusAdapter focusAdapter;
    private List<UserBean> userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        listView = findViewById(R.id.root);
        listView.setEmptyView((findViewById(R.id.empty_view)));
        focusAdapter = new FocusAdapter(R.layout.focus_item, this, userList);
        listView.setAdapter(focusAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    LinearLayout focusLayout = view.findViewById(R.id.focus);
                    focusLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showFocusDialog();
                        }
                    });
                    Intent intent = new Intent();
                    intent.setClass(FollowActivity.this, FriendActivity.class);
                    intent.putExtra("user", userList.get(position));
                    user = userList.get(position);
                    startActivity(intent);
                }
            });
        ImageView imageView = findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FollowActivity.this.finish();
            }
        });
    }
    private void showFocusDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认不再关注？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userList.remove(user);
                //把当前关注的人删除存到数据库里
                dialog.dismiss();
                listView.setAdapter(focusAdapter);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
