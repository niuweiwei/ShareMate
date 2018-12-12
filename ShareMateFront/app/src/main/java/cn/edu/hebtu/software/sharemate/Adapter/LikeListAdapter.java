package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Bean.LikeBean;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.RoundImgView;

public class LikeListAdapter extends BaseAdapter{

    private Context context;
    private List<LikeBean> likes = new ArrayList<>();
    private int item_layout;

    public LikeListAdapter(Context context, List<LikeBean> likes, int item_layout) {
        this.context = context;
        this.likes = likes;
        this.item_layout = item_layout;
    }

    @Override
    public int getCount() {
        return likes.size();
    }

    @Override
    public Object getItem(int position) {
        return likes.get(position);
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

        RoundImgView portraitView = convertView.findViewById(R.id.iv_portrait);
        portraitView.setImageResource(likes.get(position).getUser().getUserPhoto());
        TextView nameView = convertView.findViewById(R.id.tv_name);
        nameView.setText(likes.get(position).getUser().getUserName());
        ImageView noteView = convertView.findViewById(R.id.iv_note);
        noteView.setImageResource(likes.get(position).getNoteId());
        //将对象的 date 属性 转化成字符串
        Date date = likes.get(position).getDate();
        String time = new SimpleDateFormat("yyyy-MM-dd").format(date);
        TextView dateView = convertView.findViewById(R.id.tv_date);
        dateView.setText(time);
        return convertView;
    }
}
