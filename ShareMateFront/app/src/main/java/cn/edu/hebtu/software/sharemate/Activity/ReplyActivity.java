package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.CommentAdapter;
import cn.edu.hebtu.software.sharemate.Adapter.ReplyAdapter;
import cn.edu.hebtu.software.sharemate.Adapter.ReplyCommentAdapter;
import cn.edu.hebtu.software.sharemate.Bean.Comment;
import cn.edu.hebtu.software.sharemate.Bean.Reply;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.ImageTask;

public class ReplyActivity extends AppCompatActivity {
    private Boolean isZan=false;
    private TextView  send;
    private EditText etReply;
    private ArrayList<Reply> replyList;
    private String path;

    private UserBean user;//当前登录用户
    private ImageView userPhoto;
    private Comment comment;
    private int currentReplyId=0;
    private List<Integer> replylist=new ArrayList<>();//当前用户赞过的所有回复id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        path=getResources().getString(R.string.server_path);
        send=findViewById(R.id.tv_send);
        etReply=findViewById(R.id.et_reply);
        userPhoto=findViewById(R.id.iv_headimage);
        Bundle b=getIntent().getExtras();
        comment=(Comment)b.getSerializable("comment");
        user=(UserBean)b.getSerializable("user");
        ImageTask imageTask1=new ImageTask(user.getUserPhotoPath());
        Object[] objects1=new Object[]{userPhoto};
        imageTask1.execute(objects1);

        boolean isLike=b.getBoolean("isLike");
        UserLikeReply userLikeReply=new UserLikeReply(path);
        userLikeReply.execute();
        //判断评论有无被点击过
        ImageView zan=findViewById(R.id.iv_zan);
        if (isLike){
            zan.setImageResource(R.drawable.a11);
        }else{
            zan.setImageResource(R.drawable.a10);
        }
        clickZan();
        skipPreviousPage();

    }
    //查询当前用户点赞过哪些回复
    class UserLikeReply extends AsyncTask {
        private String path;

        public UserLikeReply(String path) {
            this.path = path;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            URL url = null;
            try {
                url = new URL(path + "UserServlet?userId=" + user.getUserId() + "&remark=userlikeReply");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType", "utf-8");
                InputStream is = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String res = reader.readLine();
                JSONArray jsonArray = new JSONArray(res);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    replylist.add(object.getInt("replyId"));
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            getReply();
        }
    }

        private void getReply(){

        ReplyTask replyTask=new ReplyTask();
        replyTask.execute();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hint=etReply.getHint().toString();
                String content=etReply.getText().toString();
                if(hint.equals("矜持点赞也可以，知音难觅聊一句")){
                    //回复该评论,向数据库中添加该评论的回复
                    if (!content.trim().isEmpty()){
                        AddReplyTask addReplyTask=new AddReplyTask(path+"ReplyServlet",content);
                        addReplyTask.execute();
                        Toast toast=Toast.makeText(ReplyActivity.this,"回复成功",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }else{
                        //Toast
                        Toast toast=Toast.makeText(ReplyActivity.this,"回复内容不能为空",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }

                }else {
                    if(!content.trim().isEmpty()){
                        //向数据库中添加回复的回复
                        AddReReplyTask addReReplyTask=new AddReReplyTask(path+"ReplyServlet",content);
                        addReReplyTask.execute();
                        Toast toast=Toast.makeText(ReplyActivity.this,"回复成功",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }else{
                        Toast toast=Toast.makeText(ReplyActivity.this,"回复内容不能为空",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }

                }
                etReply.setText("");
                etReply.setHint("矜持点赞也可以，知音难觅聊一句");
                //隐藏软键盘
                InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);

            }
        });

    }
    //添加回复的异步
    class AddReplyTask extends AsyncTask {
        private String path;
        private String content;
        public AddReplyTask(String path,String content) {
            this.path = path;
            this.content=content;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            URL url = null;
            try {
                url = new URL(path);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("contentType","utf-8");
                OutputStream os=connection.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(os));
                writer.write("remark=addReply&replyDetail="+content+"&userId="+user.getUserId()+"&commentId="+comment.getCommentId());
                writer.flush();
                writer.close();
                connection.connect();
                InputStream is=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                Log.e("replyDetail",reader.readLine());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            ReplyTask replyTask=new ReplyTask();
            replyTask.execute();
        }
    }
    //加载回复的异步
    class ReplyTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            replyList=new ArrayList<>();
            URL url=null;
                try {
                    url = new URL(path + "ReplyServlet?commentId=" + comment.getCommentId()+"&remark=selectReply");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("contentType", "utf-8");
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);
                    String res1 = reader.readLine();
                    JSONArray array = new JSONArray(res1);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Reply reply = new Reply();
                        reply.setReplyId(obj.getInt("replyId"));
                        Log.e("replyId",obj.getInt("replyId")+"");
                        reply.setContent(obj.getString("replyDetail"));
                        reply.setTime(obj.getString("replyDate"));
                        reply.setCountZan(obj.getInt("replyLikeCount"));
                        UserBean user=new UserBean();
                        user.setUserId(obj.getInt("userId"));
                        user.setFanCount(obj.getInt("fanCount"));
                        user.setFollowCount(obj.getInt("followCount"));
                        user.setLikeCount(obj.getInt("likeCount"));
                        user.setUserIntroduce(obj.getString("introduce"));
                        user.setUserName(obj.getString("userName"));
                        user.setUserPhotoPath(path+obj.getString("userPhoto"));
                        reply.setUser(user);
                        if (obj.getString("reReplyName").equals("0")) {
                            //是针对评论的回复
                            reply.setCommentId(comment.getCommentId());
                        } else {
                            //针对回复的回复
                            reply.setReReplyName(obj.getString("reReplyName"));
                        }
                        replyList.add(reply);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            //设置评论
            ImageView imageView=findViewById(R.id.iv_image);
            ImageTask imageTask=new ImageTask(comment.getUser().getUserPhotoPath());
            imageTask.execute(imageView);
            TextView name=findViewById(R.id.tv_name);
            name.setText(comment.getUser().getUserName());
            final TextView content=findViewById(R.id.tv_CommentContent);
            content.setText(comment.getContent());
            TextView time=findViewById(R.id.tv_time);
            time.setText(comment.getCommentTime());
            TextView count=findViewById(R.id.tv_zanCount);
            count.setText(comment.getCountZan()+"");
            TextView sumReply=findViewById(R.id.tv_sumReply);
            sumReply.setText(replyList.size()+"");
            //设置Adapter
            ReplyAdapter replyAdapter=new ReplyAdapter(replyList,R.layout.item_reply,ReplyActivity.this,replylist,user.getUserId(),path);
            ListView listView=findViewById(R.id.lv_reply);
            showAllListView(replyAdapter,listView);
            listView.setAdapter(replyAdapter);
            //回复指定的回复
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //隐藏软键盘
                    InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(),0);
                    String content=replyList.get(position).getUserName()+":"+replyList.get(position).getContent();
                    showPopupWindow(content,position);
                }
            });
        }
    }
    //添加回复的回复的异步
    class AddReReplyTask extends AsyncTask{
        private String path;
        private String content;
        public AddReReplyTask(String path,String content) {
            this.path = path;
            this.content=content;
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            URL url = null;
            try {
                url = new URL(path);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("contentType","utf-8");
                OutputStream os=connection.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(os));
                writer.write("remark=addReReply&replyDetail="+content+"&userId="+user.getUserId()+"&reReplyId="+currentReplyId);
                Log.e("content",content);
                writer.flush();
                writer.close();
                connection.connect();
                InputStream is=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                Log.e("reReplyDetail",reader.readLine());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            ReplyTask replyTask=new ReplyTask();
            replyTask.execute();
        }
    }
    private void showPopupWindow(String content,final int position){
        LinearLayout linearLayout=findViewById(R.id.root);
        //创建PopupWindow对象
        final PopupWindow popupWindow = new PopupWindow(linearLayout,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        View view = getLayoutInflater().inflate(R.layout.popupwindow_layout,null);
        TextView textView=view.findViewById(R.id.tv_content);
        textView.setText(content);
        Button button2=view.findViewById(R.id.btn2);
        Button button3=view.findViewById(R.id.btn3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //根据position得到点击的那个回复，回复指定的回复
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                etReply.setHint("回复"+replyList.get(position).getUserName()+"…");
                currentReplyId=replyList.get(position).getReplyId();
                popupWindow.dismiss();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏popupWindow
                etReply.setHint("矜持点赞也可以，知音难觅聊一句");
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(view);
        //显示PopupWindow
        popupWindow.showAtLocation(linearLayout, Gravity.NO_GRAVITY,0,0);
    }
    //解决ListView嵌套ListView,item显示不全问题
    private void showAllListView(ReplyAdapter replyAdapter,ListView listView) {
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
    //点赞评论，赞数加1
    private void clickZan(){
        final ImageView zan=findViewById(R.id.iv_zan);
        final TextView zanCount=findViewById(R.id.tv_zanCount);
        zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isZan){
                    zan.setImageResource(R.drawable.a11);
                    int count=Integer.parseInt(zanCount.getText().toString());
                    count++;
                    zanCount.setText(count+"");
                    isZan=true;
                }else{
                    zan.setImageResource(R.drawable.a10);
                    int count=Integer.parseInt(zanCount.getText().toString());
                    count--;
                    zanCount.setText(count+"");
                    isZan=false;
                }
            }
        });
    }
    //返回上一页
    private void skipPreviousPage(){
        ImageView imageView1=findViewById(R.id.iv_rightarrow);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReplyActivity.this.finish();
            }
        });

    }

}
