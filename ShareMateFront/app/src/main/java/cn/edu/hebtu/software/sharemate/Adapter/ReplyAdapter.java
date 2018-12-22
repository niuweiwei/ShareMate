package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.hebtu.software.sharemate.Bean.Reply;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.ImageTask;

public class ReplyAdapter extends BaseAdapter {
    private List<Reply> replies;
    private int itemlayout;
    private Context context;
    private String path;

    private List<Integer> replylist;
    private boolean isZan;
    private int userId;
    public ReplyAdapter(List<Reply> replies, int itemlayout, Context context,List<Integer> replylist,int userId,String path) {
        this.replies = replies;
        this.itemlayout = itemlayout;
        this.context = context;
        this.replylist=replylist;
        this.userId=userId;
        this.path=path;
    }

    @Override
    public int getCount() {
        return replies.size();
    }

    @Override
    public Object getItem(int position) {
        return replies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
            if(null==convertView){
                LayoutInflater layoutInflater=LayoutInflater.from(context);
                convertView=layoutInflater.inflate(itemlayout,null);
            }
            ImageView image = convertView.findViewById(R.id.iv_image);
            ImageTask imageTask=new ImageTask(path+replies.get(position).getUserPhoto());
            imageTask.execute(image);
            TextView name = convertView.findViewById(R.id.tv_name);
            name.setText(replies.get(position).getUserName());
            //判断是对评论的回复还是对回复的回复
            if (replies.get(position).getReReplyName() != null) {
                //是对回复的回复
                TextView textView = convertView.findViewById(R.id.tv_reReply);
                String reReplyName = replies.get(position).getReReplyName();
                textView.setText("@" + reReplyName);
            }
            TextView content = convertView.findViewById(R.id.tv_CommentContent);
            content.setText(replies.get(position).getContent());
            TextView time = convertView.findViewById(R.id.tv_time);
            time.setText(replies.get(position).getTime());
            final TextView count = convertView.findViewById(R.id.tv_zan);
            count.setText(replies.get(position).getCountZan() + "");
            View line=convertView.findViewById(R.id.view_line);
            if(position==replies.size()-1){
                line.setVisibility(View.INVISIBLE);
            }
            //判断用户是否点赞过该回复
            final ImageView zan=convertView.findViewById(R.id.iv_zan);
            boolean flag=false;
            for(Integer i:replylist){
                if (i.equals(replies.get(position).getReplyId())){
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
                    LikeTask likeTask=new LikeTask(path+"ReplyServlet?remark=deleteLike&userId="+userId+"&replyId="+replies.get(position).getReplyId(),position,zan,count);
                    likeTask.execute();
                }else {
                    LikeTask likeTask=new LikeTask(path+"ReplyServlet?remark=addLike&userId="+userId+"&replyId="+replies.get(position).getReplyId(),position,zan,count);
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
                Log.e("resultLike", res);
                if ("点赞成功".equals(res)) {
                    isZan = true;
                } else if ("取消赞".equals(res)) {
                    isZan = false;
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
