package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.ZanAdapter;
import cn.edu.hebtu.software.sharemate.Bean.NoteBean;
import cn.edu.hebtu.software.sharemate.R;

public class ZanActivity extends AppCompatActivity {

    private ZanAdapter zanAdapter;
    private GridView gridView;
    private List<NoteBean> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zan);
        gridView =findViewById(R.id.root);
        gridView.setEmptyView((findViewById(R.id.empty_view)));
        zanAdapter= new ZanAdapter(this, R.layout.zan_item,list);
        gridView.setAdapter(zanAdapter);
        ImageView imageView = findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ZanActivity.this, SettingActivity.class);
                intent.putExtra("user",getIntent().getSerializableExtra("user"));
                startActivity(intent);
            }
        });
    }
}
