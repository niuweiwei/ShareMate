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

public class FocusAdapter extends BaseAdapter {

    private int itemLayout;
    private Context context;
    private List<UserBean> userList= new ArrayList<>();

    public FocusAdapter(int itemLayout, Context context, List<UserBean> userList) {
        this.itemLayout = itemLayout;
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
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
        String photoPath = "http://10.7.89.233:8080/sharemate/"+userList.get(position).getUserPhotoPath();
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(context).load(photoPath).apply(mRequestOptions).into(imageView);
        TextView textView = convertView.findViewById(R.id.tv_note);
        textView.setText(userList.get(position).getUserName());
        TextView followView = convertView.findViewById(R.id.follow);
        if(userList.get(position).isStates()){
            followView.setText(" 互相关注 ");
        }else{
            followView.setText(" 已关注 ");
        }
        return convertView;
    }
}
