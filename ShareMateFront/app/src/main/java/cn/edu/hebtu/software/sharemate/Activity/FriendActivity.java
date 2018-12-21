package cn.edu.hebtu.software.sharemate.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.NoteAdapter;
import cn.edu.hebtu.software.sharemate.Bean.NoteBean;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class FriendActivity extends AppCompatActivity {

    private UserBean user;
    private TextView nameView;
    private TextView introView;
    private TextView idView;
    private ImageView photoView;
    private ImageView backView;
    private TextView collection;
    private TextView note;
    private GridView gridView;
    private NoteAdapter noteAdapter;
    private List<cn.edu.hebtu.software.sharemate.Bean.NoteBean> collectionList = new ArrayList<>();
    private List<cn.edu.hebtu.software.sharemate.Bean.NoteBean> noteList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        user = (UserBean) getIntent().getSerializableExtra("friend");
        findView();
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendActivity.this.finish();
            }
        });
        note.setTextColor(getResources().getColor(R.color.warmRed));
        collection.setTextColor(getResources().getColor(R.color.darkGray));
        noteAdapter = new NoteAdapter(FriendActivity.this, R.layout.note_item,noteList);
        gridView.setAdapter(noteAdapter);
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collection.setTextColor(getResources().getColor(R.color.warmRed));
                note.setTextColor(getResources().getColor(R.color.darkGray));
                cn.edu.hebtu.software.sharemate.Bean.NoteBean collection1 = new NoteBean(R.drawable.e,"明星资讯随时看");
                cn.edu.hebtu.software.sharemate.Bean.NoteBean collection2 = new NoteBean(R.drawable.f,"旅游时笔芯狂魔");
                collectionList.add(collection1);
                collectionList.add(collection2);
                noteAdapter = new NoteAdapter(FriendActivity.this, R.layout.note_item,collectionList);
                gridView.setAdapter(noteAdapter);
            }
        });
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.setTextColor(getResources().getColor(R.color.warmRed));
                collection.setTextColor(getResources().getColor(R.color.darkGray));
                noteAdapter = new NoteAdapter(FriendActivity.this, R.layout.note_item,noteList);
                gridView.setAdapter(noteAdapter);
            }
        });
    }
    public void findView(){
        nameView = findViewById(R.id.userName);
        idView = findViewById(R.id.userId);
        photoView = findViewById(R.id.userPhoto);
        introView = findViewById(R.id.userIntro);
        backView = findViewById(R.id.back);
        collection = findViewById(R.id.collection);
        note = findViewById(R.id.note);
        gridView = findViewById(R.id.root);
        gridView.setEmptyView((findViewById(R.id.empty_view)));
        idView.setText("Share Mate号："+user.getUserId());
        nameView.setText(user.getUserName());
        photoView.setImageResource(user.getUserPhoto());
        introView.setText(user.getUserIntroduce());
    }
}
