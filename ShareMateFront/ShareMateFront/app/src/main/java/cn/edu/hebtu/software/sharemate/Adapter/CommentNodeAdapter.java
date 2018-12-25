package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.acl.LastOwnerException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import cn.edu.hebtu.software.sharemate.Activity.FriendActivity;
import cn.edu.hebtu.software.sharemate.Bean.Comment;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.ImageTask;

public class CommentNodeAdapter extends BaseAdapter {
    private List<Comment> comments;
    private int itemlayout;
    private Context context;
    private boolean isZan;
    int userId;//当前登录的用户
    private List<Integer> list;
    private UserBean user=new UserBean();

    private String path;

    public CommentNodeAdapter(List<Comment> comments, int itemlayout, Context context,int userId,List<Integer> list,String path) {
        this.comments = comments;
        this.itemlayout = itemlayout;
        this.context = context;
        this.userId=userId;
        this.list=list;
        this.path=path;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (null==convertView){
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView=layoutInflater.inflate(itemlayout,null);
        }
        final Resources res=convertView.getResources();
        ImageView imageView=convertView.findViewById(R.id.iv_image);
        ImageTask imageTask=new ImageTask(comments.get(position).getUser().getUserPhotoPath());
        Object[] objects=new Object[]{imageView};
        imageTask.execute(objects);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, FriendActivity.class);
                intent.putExtra("friend",comments.get(position).getUser());
                context.startActivity(intent);
            }
        });
        TextView name=convertView.findViewById(R.id.tv_name);
        name.setText(comments.get(position).getUser().getUserName());
        TextView content=convertView.findViewById(R.id.tv_content);
        content.setText(comments.get(position).getContent());
        TextView time=convertView.findViewById(R.id.tv_time);
        time.setText(comments.get(position).getCommentTime().toString());
        final TextView count=convertView.findViewById(R.id.tv_zan);
        count.setText(comments.get(position).getCountZan()+"");
        final ImageView zan=convertView.findViewById(R.id.iv_zan);
        boolean flag=false;
        for(Integer i:list){
            if (i.equals(comments.get(position).getCommentId())){
                flag=true;
                break;
            }
        }
        if (flag){
            isZan=true;
            zan.setImageResource(R.drawable.a11);
        }else{
            isZan=false;
            zan.setImageResource(R.drawable.a10);
        }
        //点赞，赞数加1
        zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isZan){
                    LikeTask likeTask=new LikeTask(path+"CommentServlet?remark=deleteLike&userId="+userId+"&commentId="+comments.get(position).getCommentId(),position,zan,count);
                    likeTask.execute();
                }else {
                    LikeTask likeTask=new LikeTask(path+"CommentServlet?remark=addLike&userId="+userId+"&commentId="+comments.get(position).getCommentId(),position,zan,count);
                    likeTask.execute();
                }
            }
        });
        return convertView;
    }
    //点赞或取消赞
    class LikeTask extends AsyncTask {
        private String path;
        private int position;
        private ImageView zan;
        private TextView count;

        public LikeTask(String path, int position, ImageView zan, TextView count) {
            this.path = path;
            this.position = position;
            this.zan = zan;
            this.count = count;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String res = "";
            try {
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType", "utf-8");
                InputStream is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                res = reader.readLine();
                Log.e("resultLike",res);
                if ("点赞成功".equals(res)) {
                    isZan=true;
                } else if ("取消赞".equals(res)) {
                    isZan=false;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if (isZan) {
                zan.setImageResource(R.drawable.a11);
                int zanCount = Integer.parseInt(count.getText().toString());
                zanCount++;
                count.setText(zanCount + "");
            } else {
                zan.setImageResource(R.drawable.a10);
                int zanCount = Integer.parseInt(count.getText().toString());
                zanCount--;
                count.setText(zanCount + "");
            }
        }
    }
}
