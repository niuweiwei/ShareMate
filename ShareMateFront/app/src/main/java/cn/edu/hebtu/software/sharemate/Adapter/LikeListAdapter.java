package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
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
import cn.edu.hebtu.software.sharemate.tools.ImageTask;
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

        //获取用户的头像
        ImageView portraitView = convertView.findViewById(R.id.iv_portrait);
        String userPhotoPath = likes.get(position).getUser().getUserPhotoPath();
        ImageTask imageTask1 = new ImageTask(userPhotoPath);
        Object[] objects = new Object[]{portraitView};
        imageTask1.execute(objects);


        //获取笔记图片
        String notePhotoPath = likes.get(position).getNotePhotoPath();
        ImageView noteView = convertView.findViewById(R.id.iv_note);
        ImageTask imageTask2 = new ImageTask(notePhotoPath);
        imageTask2.execute(new Object[]{noteView});


        TextView nameView = convertView.findViewById(R.id.tv_name);
        nameView.setText(likes.get(position).getUser().getUserName());
        //将对象的 date 属性 转化成字符串
        TextView dateView = convertView.findViewById(R.id.tv_date);
        dateView.setText(likes.get(position).getDate());
        return convertView;
    }
}
