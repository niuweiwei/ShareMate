package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.hebtu.software.sharemate.Activity.FriendActivity;
import cn.edu.hebtu.software.sharemate.Activity.ReplyActivity;
import cn.edu.hebtu.software.sharemate.Bean.Comment;
import cn.edu.hebtu.software.sharemate.Bean.Reply;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.ImageTask;

public class CommentAdapter extends BaseAdapter {
    private List<Comment> comments;
    private int itemlayout;
    private Context context;
    private Map<Integer, Boolean> isLike = new HashMap<>();
    private String path;
    private Map<Integer,ArrayList<Reply>> replyMap;
    private boolean isZan;
    private List<Integer> commentlist;
    private List<Integer> replylist;
    private UserBean user;//当前注册用户
    public CommentAdapter(List<Comment> comments, int itemlayout, Context context,Map<Integer, ArrayList<Reply>> replyMap,List<Integer> commentlist,List<Integer> replylist,UserBean user,String path) {
        this.comments = comments;
        this.itemlayout = itemlayout;
        this.context = context;
        this.replyMap = replyMap;
        this.commentlist=commentlist;
        this.replylist=replylist;
        this.user=user;
        this.path=path;
        for (int i = 0; i < comments.size(); i++) {
            isLike.put(i, false);
        }
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
    public synchronized View getView(final int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(itemlayout, null);
        }
        final Resources res = convertView.getResources();
        ImageView imageView = convertView.findViewById(R.id.iv_image);
        ImageTask imageTask = new ImageTask(comments.get(position).getUser().getUserPhotoPath());
        Object[] objects = new Object[]{imageView};
        imageTask.execute(objects);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, FriendActivity.class);
                intent.putExtra("friend",comments.get(position).getUser());
                context.startActivity(intent);
            }
        });

        TextView name = convertView.findViewById(R.id.tv_name);
        name.setText(comments.get(position).getUser().getUserName());
        final TextView content = convertView.findViewById(R.id.tv_content);
        content.setText(comments.get(position).getContent());
        TextView time = convertView.findViewById(R.id.tv_time);
        time.setText(comments.get(position).getCommentTime().toString());
        final TextView count = convertView.findViewById(R.id.tv_zan);
        count.setText(comments.get(position).getCountZan() + "");
        final int commentId=comments.get(position).getCommentId();
        TextView sumReply=convertView.findViewById(R.id.tv_sumReply);
        //判断用户是否点赞过该评论
        final ImageView zan=convertView.findViewById(R.id.iv_zan);
        boolean flag=false;
        for(Integer i:commentlist){
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
                    LikeTask likeTask=new LikeTask(path+"CommentServlet?remark=deleteLike&userId="+user.getUserId()+"&commentId="+comments.get(position).getCommentId(),position,zan,count);
                    likeTask.execute();
                }else {
                    LikeTask likeTask=new LikeTask(path+"CommentServlet?remark=addLike&userId="+user.getUserId()+"&commentId="+comments.get(position).getCommentId(),position,zan,count);
                    likeTask.execute();
                }
            }
        });
        //设置评论页的回复（只显示前2条）
        ListView listView = convertView.findViewById(R.id.lv_reply);
        LinearLayout replyComment=convertView.findViewById(R.id.ll_commentReply);
        LinearLayout allReply=convertView.findViewById(R.id.ll_allReply);
        if (replyMap.get(comments.get(position).getCommentId()).size()!=0){
            sumReply.setText(replyMap.get(commentId).size()+"条回复");
            ReplyCommentAdapter replyAdapter=null;
            if(replyMap.get(commentId).size()>2){
                ArrayList<Reply> newReplies=new ArrayList<>();
                newReplies.add(replyMap.get(commentId).get(0));
                newReplies.add(replyMap.get(commentId).get(1));
                replyAdapter = new ReplyCommentAdapter(newReplies, R.layout.item_reply_comment, context,replylist,user.getUserId(),path);
            }else {
                replyAdapter = new ReplyCommentAdapter(replyMap.get(commentId), R.layout.item_reply_comment, context,replylist,user.getUserId(),path);
            }

            showAllListView(replyAdapter, listView);
            listView.setAdapter(replyAdapter);

            int totalHeight = 0;
            ListAdapter listAdapter = listView.getAdapter();
            for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0); // 计算子项View 的宽高
                totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
            }
            LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)replyComment.getLayoutParams();
            params.height=totalHeight+150;
            replyComment.setLayoutParams(params);
            //跳转到全部回复页面
            allReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ReplyActivity.class);
                    Bundle b=new Bundle();
                    b.putSerializable("comment",comments.get(position));
                    for (Integer a:commentlist){
                        if (a.equals(comments.get(position).getCommentId())){
                            b.putBoolean("isLike",true);
                            break;
                        }else{
                            b.putBoolean("isLike",false);
                            break;
                        }
                    }
                    b.putSerializable("user",user);
                    intent.putExtras(b);
                    context.startActivity(intent);
                }
            });
        }else {
            //该评论暂时没有回复
            LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)replyComment.getLayoutParams();
            params.height=0;
            replyComment.setLayoutParams(params);
        }


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
    //解决ListView嵌套ListView问题
    private void showAllListView(ReplyCommentAdapter replyAdapter,ListView listView) {
        if (replyAdapter != null) {
            int totalHeight = 0;
            for (int i = 0; i <replyAdapter.getCount(); i++) {
                View listItem =replyAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight+ (listView.getDividerHeight() * (replyAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }
}