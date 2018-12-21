package cn.edu.hebtu.software.sharemate.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Bean.FollowBean;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.ImageTask;
import cn.edu.hebtu.software.sharemate.tools.RoundImgView;

public class FollowedListAdapter extends BaseAdapter {

    private String path = "http://10.7.89.232:8080/sharemate/";
    private Context context;
    private int item_layout;
    private List<FollowBean> follows = new ArrayList<>();

    public FollowedListAdapter(Context context, int item_layout, List<FollowBean> follows) {
        this.context = context;
        this.item_layout = item_layout;
        this.follows = follows;
    }

    @Override
    public int getCount() {
        return follows.size();
    }

    @Override
    public Object getItem(int position) {
        return follows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //为每一个子项填充布局
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(item_layout,null);
        }

        RoundImgView portraitView = convertView.findViewById(R.id.iv_portrait);
        ImageTask imageTask = new ImageTask(follows.get(position).getUser().getUserPhotoPath());
        imageTask.execute(portraitView);
        TextView nameView = convertView.findViewById(R.id.tv_name);
        nameView.setText(follows.get(position).getUser().getUserName());

        //将对象中的 date 属性 转化成字符串
       String date = follows.get(position).getDate();

        TextView timeView = convertView.findViewById(R.id.tv_date);
        timeView.setText(date);

        final Button follow = convertView.findViewById(R.id.btn_follow);
        boolean isFollow = follows.get(position).isFollow();
        //判断当前用户与该粉丝是否互相关注
        if(isFollow == true){
            follow.setText("已关注");
            follow.setTextColor(context.getResources().getColor(R.color.deepGray));
            follow.setBackgroundResource(R.drawable.cancelfollowedbutton_style);
        }else{
            follow.setText("回粉");
            follow.setTextColor(context.getResources().getColor(R.color.brightRed));
            follow.setBackgroundResource(R.drawable.followbutton_style);
        }
        //为 "回粉" 按钮绑定点击事件
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(follow.getText().equals("回粉")) {
                    FollowTask followTask = new FollowTask(position);
                    followTask.execute(follow);
                }else{
                    // 点击 “已关注” 弹出"是否取消关注"的提示框
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("确认取消关注？");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //nothing to do
                        }
                    });
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            follow.setText("回粉");
                            follow.setTextColor(context.getResources().getColor(R.color.brightRed));
                            follow.setBackgroundResource(R.drawable.followbutton_style);
                        }
                    });
                    //获取提示框
                    final AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    follows.get(position).setFollow(false);
                    CancelTask cancelTask = new CancelTask(position);
                    cancelTask.execute();
                }
            }
        });

        return convertView;
    }

    private class FollowTask extends AsyncTask{

        int position;

        public FollowTask(int position) {
            this.position = position;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            Button follow = (Button) objects[0];
            try {
                Log.e("FollowedListAdapter","进入异步任务");
                String param = "followId="+follows.get(position).getCurrentUser()+"&userId="+follows.get(position).getUser().getUserId()+"&act=follow";
                Log.e("URL",path+"DoFollowServlet?"+param);
                URL url = new URL(path+"DoFollowServlet?"+param);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType","UTF-8");

                InputStream is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return follow;
        }

        @Override
        protected void onPostExecute(Object o) {
            Button follow = (Button) o;
            follow.setText("已关注");
            follow.setTextColor(context.getResources().getColor(R.color.deepGray));
            follow.setBackgroundResource(R.drawable.cancelfollowedbutton_style);
            follows.get(position).setFollow(true);
        }
    }

    private class CancelTask extends AsyncTask{

        private int position;

        public CancelTask(int position) {
            this.position = position;
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                URL url = new URL(path+"DoFollowServlet");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("contentType","utf-8");

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
                String param = "followId="+follows.get(position).getCurrentUser()+"&userId="+follows.get(position).getUser().getUserId()+"&act=cancel";
                writer.write(param);
                writer.flush();
                writer.close();
                connection.connect();

                InputStream is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
