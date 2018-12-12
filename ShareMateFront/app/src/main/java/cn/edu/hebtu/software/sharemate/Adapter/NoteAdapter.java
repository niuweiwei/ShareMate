package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Bean.NoteBean;
import cn.edu.hebtu.software.sharemate.R;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private int itemLayout;
    private List<NoteBean> noteList = new ArrayList<>();

    public NoteAdapter(Context context, int itemLayout, List<NoteBean> noteList) {
        this.context = context;
        this.itemLayout = itemLayout;
        this.noteList = noteList;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null == convertView){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(itemLayout,null);
        }
        ImageView imageView = convertView.findViewById(R.id.img_content);
        imageView.setImageResource(noteList.get(position).getNoPhoto());
        TextView textView = convertView.findViewById(R.id.tv_note);
        textView.setText(noteList.get(position).getTitle());
        return convertView;
    }
}
