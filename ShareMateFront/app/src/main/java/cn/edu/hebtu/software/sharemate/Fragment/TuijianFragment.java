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
        List<NoteBean> notes = new ArrayList<>();
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
