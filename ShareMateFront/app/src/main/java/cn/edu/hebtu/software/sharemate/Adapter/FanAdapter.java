package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class FanAdapter extends BaseAdapter {
    private Context context;
    private int itemLayout;
    private List<UserBean> fanList = new ArrayList<>();

    public FanAdapter(Context context, int itemLayout, List<UserBean> fanList) {
        this.context = context;
        this.itemLayout = itemLayout;
        this.fanList = fanList;
    }

    @Override
    public int getCount() {
        return fanList.size();
    }

    @Override
    public Object getItem(int position) {
        return fanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null == convertView) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(itemLayout, null);
        }
        ImageView imageView = convertView.findViewById(R.id.img_content);
        String photoPath = "http://10.7.89.233:8080/sharemate/"+fanList.get(position).getUserPhotoPath();
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(context).load(photoPath).apply(mRequestOptions).into(imageView);
        TextView textView = convertView.findViewById(R.id.tv_note);
        textView.setText(fanList.get(position).getUserName());
        TextView followView = convertView.findViewById(R.id.follow);
        if(fanList.get(position).isStates()){
            followView.setText(" 互相关注 ");
        }else{
            followView.setText(" 回粉 ");
            followView.setTextColor(context.getResources().getColor(R.color.warmRed));
        }
        TextView noteText = convertView.findViewById(R.id.noteCount);
        noteText.setText("笔记."+fanList.get(position).getNoteCount());
        TextView fanText = convertView.findViewById(R.id.fanCount);
        fanText.setText("粉丝."+fanList.get(position).getFanCount());
        return convertView;
    }
}
