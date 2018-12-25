package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Activity.FollowActivity;
import cn.edu.hebtu.software.sharemate.Activity.FriendActivity;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class FocusAdapter extends BaseAdapter {

    private UserBean user;
    private String path;
    private int itemLayout;
    private Context context;
    private List<UserBean> userList= new ArrayList<>();

    public FocusAdapter(int itemLayout, Context context, List<UserBean> userList,String path,UserBean userBean) {
        this.itemLayout = itemLayout;
        this.context = context;
        this.userList = userList;
        this.path = path;
        this.user = userBean;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(null == convertView) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(itemLayout, null);
        }
        final ImageView imageView = convertView.findViewById(R.id.img_content);
        String photoPath = userList.get(position).getUserPhotoPath();
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(context).load(photoPath).apply(mRequestOptions).into(imageView);
        TextView textView = convertView.findViewById(R.id.tv_note);
        textView.setText(userList.get(position).getUserName());
        final TextView focusText = convertView.findViewById(R.id.focus);
        focusText.setText("已关注");
        //点击头像跳转到当前用户的详情页面
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, FriendActivity.class);
                intent.putExtra("friend", userList.get(position));
                context.startActivity(intent);
            }
        });
        //点击已关注选择是否取消关注
        focusText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("确认不再关注？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        focusText.setText("关注");
                        focusText.setTextColor(context.getResources().getColor(R.color.warmRed));
                        //把取消关注的用户从数据库中删除
                        DeleteFollow deleteFollow = new DeleteFollow();
                        deleteFollow.execute(user.getUserId(),userList.get(position).getUserId());
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
            return convertView;
    }
    //把取消关注的用户从数据库中删除
    public class DeleteFollow extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            int followId =(Integer)objects[0];
            int userId = (Integer)objects[1];
            try {
                URL url = new URL(path+"DeleteFollowServlet?followId="+followId+"&userId="+userId);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("contentType","UTF-8");
                int res = urlConnection.getResponseCode();
                if(res == 200){
                    Log.e("test","删除成功！");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
