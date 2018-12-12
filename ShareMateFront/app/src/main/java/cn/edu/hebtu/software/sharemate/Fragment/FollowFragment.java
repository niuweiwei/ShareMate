package cn.edu.hebtu.software.sharemate.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.CustomAdapter;
import cn.edu.hebtu.software.sharemate.Bean.NoteBean;
import cn.edu.hebtu.software.sharemate.R;

public class FollowFragment extends Fragment {
    private CustomAdapter customAdapter=null;
    private List<NoteBean> notes;
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.note_fragment,container,false);
        ImageView image = view.findViewById(R.id.img);
        image.setImageResource(R.drawable.meng);
        image.setMaxWidth(100);
        image.setMaxHeight(100);
        TextView text = view.findViewById(R.id.text);
        text.setText("啊哦~ \n" +
                "你还没有关注的人的笔记哦");
        text.setTextSize(25);
        return view;
    }
}
