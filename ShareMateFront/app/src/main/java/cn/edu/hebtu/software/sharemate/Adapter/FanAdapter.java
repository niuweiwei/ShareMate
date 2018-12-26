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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Activity.FriendActivity;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class FanAdapter extends BaseAdapter {
    private String path = null;
    private UserBean user;
    private Context context;
    private int itemLayout;
    private List<UserBean> fanList = new ArrayList<>();

    public FanAdapter(Context context, int itemLayout, List<UserBean> fanList,UserBean userBean,String path) {
        this.context = context;
        this.itemLayout = itemLayout;
        this.fanList = fanList;
        this.user = userBean;
        this.path = path;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(null == convertView) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(itemLayout, null);
        }
        final ImageView imageView = convertView.findViewById(R.id.img_content);
        String photoPath = fanList.get(position).getUserPhotoPath();
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(context).load(photoPath).apply(mRequestOptions).into(imageView);
        TextView textView = convertView.findViewById(R.id.tv_note);
        textView.setText(fanList.get(position).getUserName());
        final TextView followView = convertView.findViewById(R.id.follow);
        if(fanList.get(position).isStates()){
            followView.setText(" 互相关注 ");
            followView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("确认不再关注？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //把取消关注的用户从数据库中删除
                            DeleteFollow deleteFollow = new DeleteFollow();
                            deleteFollow.execute(user.getUserId(),fanList.get(position).getUserId(),followView);
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
        }else{
            followView.setText(" 回粉 ");
            followView.setTextColor(context.getResources().getColor(R.color.warmRed));
            followView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insertFollow follow = new insertFollow();
                    follow.execute(user.getUserId(),fanList.get(position).getUserId(),followView);
                }
            });
        }
        TextView noteText = convertView.findViewById(R.id.noteCount);
        noteText.setText("笔记."+fanList.get(position).getNoteCount());
        TextView fanText = convertView.findViewById(R.id.fanCount);
        fanText.setText("粉丝."+fanList.get(position).getFanCount());
        //点击头像跳转到当前用户的详情页面
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FriendActivity.class);
                intent.putExtra("friend",fanList.get(position));
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    //把粉丝加入我的关注表里
    public class insertFollow extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            int followId =(Integer) objects[0];
            int userId = (Integer)objects[1];
            TextView followView =(TextView) objects[2];
            try {
                URL url = new URL(path+"InsertFollowServlet?followId="+followId+"&userId="+userId);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("contentType","UTF-8");
                int res = urlConnection.getResponseCode();
                if(res == 200){
                    InputStream is = urlConnection.getInputStream();
                    Log.e("test","添加成功！");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return followView;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            TextView followView = (TextView) o;
            followView.setText("互相关注");
            followView.setTextColor(context.getResources().getColor(R.color.darkGray));
        }
    }
    //把取消关注的用户从数据库中删除
    public class DeleteFollow extends  AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            int followId =(Integer) objects[0];
            int userId = (Integer)objects[1];
            TextView followView =(TextView) objects[2];
            try {
                URL url = new URL(path+"DeleteFollowServlet?followId="+followId+"&userId="+userId);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("contentType","UTF-8");
                int res = urlConnection.getResponseCode();
                if(res == 200){
                    InputStream is = urlConnection.getInputStream();
                    Log.e("test","删除成功！");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return followView;
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            TextView followView = (TextView) o;
            followView.setText(" 回粉 ");
            followView.setTextColor(context.getResources().getColor(R.color.warmRed));
        }
    }
}
