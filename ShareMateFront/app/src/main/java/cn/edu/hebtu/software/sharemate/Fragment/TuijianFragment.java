package cn.edu.hebtu.software.sharemate.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Activity.NoteDetailActivity;
import cn.edu.hebtu.software.sharemate.Adapter.GridViewAdapter;
import cn.edu.hebtu.software.sharemate.Bean.NoteBean;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class TuijianFragment extends Fragment {
    private GridViewAdapter gridViewAdapter=null;
    private List<NoteBean> notes;
    private ListView listView;
    private GridView gridView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.note_fragment,container,false);
        UserBean user=new UserBean("小明",R.drawable.a1);
        NoteBean st1= new NoteBean(R.drawable.dm10,"路飞手办，路飞公仔。。。。。。。。","笔记一",user,"昨天12:00",88,77,88,99);
        NoteBean st2= new NoteBean(R.drawable.dm6,"海贼王手办公仔模型Q版","笔记一",user,"昨天12:00",98,77,88,99);
        NoteBean st3= new NoteBean(R.drawable.dm2,"路飞手办，三个路飞Q版模型","笔记一",user,"昨天12:00",18,77,88,99);
        NoteBean st4= new NoteBean(R.drawable.dm8,"海贼王手办，路飞公仔全套","笔记一",user,"昨天12:00",28,77,88,99);
        NoteBean st5= new NoteBean(R.drawable.dm1,"路飞生日礼物全套，onepiece手办模型","笔记一",user,"昨天12:00",48,77,88,99);
        List<NoteBean> notes = new ArrayList<>();
        notes.add(st1);
        notes.add(st2);
        notes.add(st3);
        notes.add(st4);
        notes.add(st5);
        gridView = view.findViewById(R.id.root);
        //创建Adapter对象
        gridViewAdapter = new GridViewAdapter(getActivity(),R.layout.grid_item, notes);
        //设置Adapter
        gridView.setAdapter(gridViewAdapter);
        gridView.setHorizontalSpacing(5);
        gridView.setVerticalSpacing(5);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("点击了", ""+ position);
                Intent intent = new Intent();
                intent.setClass(getActivity(),NoteDetailActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
