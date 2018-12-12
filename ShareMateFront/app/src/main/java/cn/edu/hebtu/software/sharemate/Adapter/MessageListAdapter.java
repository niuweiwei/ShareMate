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

import cn.edu.hebtu.software.sharemate.Bean.TitleBean;
import cn.edu.hebtu.software.sharemate.R;

public class MessageListAdapter extends BaseAdapter {

    private Context context;
    private int item_layout;
    private List<TitleBean> titles = new ArrayList<>();

    public MessageListAdapter(Context context, int item_layout, List<TitleBean> titles) {
        this.context = context;
        this.item_layout = item_layout;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(item_layout,null);
        }
        ImageView icon = convertView.findViewById(R.id.iv_icon);
        icon.setImageResource(titles.get(position).getIcon());
        TextView title = convertView.findViewById(R.id.tv_enter);
        title.setText(titles.get(position).getTitle());

        return convertView;
    }
}
