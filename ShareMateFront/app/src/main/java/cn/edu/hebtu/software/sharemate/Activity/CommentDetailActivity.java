package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.hebtu.software.sharemate.Adapter.CommentAdapter;
import cn.edu.hebtu.software.sharemate.Adapter.ReplyCommentAdapter;
import cn.edu.hebtu.software.sharemate.Bean.Comment;
import cn.edu.hebtu.software.sharemate.Bean.Reply;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.ImageTask;

public class CommentDetailActivity extends AppCompatActivity {
    private ListView listView;
    private LinearLayout commentLayout;
    private EditText etComment;
    private TextView send;
    private TextView sumComment;
    private ImageView userPhoto;
    private UserBean user;//当前登录人
    private List<Comment> comments=new ArrayList<>();
    private String path;
    private Map<Integer,ArrayList<Reply>> replyMap=new HashMap<>();
    private int noteId=1;
    private List<Integer> commentlist=new ArrayList<>();//当前用户点赞过的所有评论id
    private List<Integer> replylist=new ArrayList<>();//当前用户赞过的所有回复id
    private int currentCommentId=0;//当前要回复的评论的Id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decomment);
        path="http://"+getResources().getString(R.string.server_path)+":8080/sharemate/";
        listView=findViewById(R.id.lv_comment);
        etComment=findViewById(R.id.et_comment);
        send=findViewById(R.id.tv_send);
        sumComment=findViewById(R.id.tv_sumComment);

        Intent intent=getIntent();
        noteId=intent.getIntExtra("noteId",0);
        //用户头像
        userPhoto=findViewById(R.id.iv_headimage);
        user=(UserBean)intent.getSerializableExtra("user");
        ImageTask imageTask1=new ImageTask(path+user.getUserPhotoPath());
        Object[] objects1=new Object[]{userPhoto};
        imageTask1.execute(objects1);

        //整个页面
        final View contentView=findViewById(R.id.rl_root);
        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /**
                 * 点击空白位置 隐藏软键盘
                 */
                contentView.setFocusable(true);
                contentView.setFocusableInTouchMode(true);
                contentView.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etComment.getWindowToken(), 0);
                return false;
            }
        });
        UserLikeComment userLikeComment=new UserLikeComment(path);
        userLikeComment.execute();

        //跳转到上一页
        skipPreviousPage();
        //发送评论或对评论进行回复
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hint=etComment.getHint().toString();
                String content=etComment.getText().toString();
                if(hint.equals("矜持点赞也可以，知音难觅聊一句")){
                    //发表对笔记的评论,向数据库中添加该评论
                    if (!content.trim().isEmpty()){
                        AddCommentTask addCommentTask=new AddCommentTask(path+"CommentServlet",content);
                        addCommentTask.execute();
                        Toast toast=Toast.makeText(CommentDetailActivity.this,"评论成功",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }else{
                        Toast toast=Toast.makeText(CommentDetailActivity.this,"评论内容不能为空",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }

                }else {
                    //对该评论进行回复
                    Log.e("添加评论的回复",content);
                    if(!content.trim().isEmpty()){
                        AddReplyTask addReplyTask=new AddReplyTask(path+"ReplyServlet",content);
                        addReplyTask.execute();
                        Toast toast=Toast.makeText(CommentDetailActivity.this,"回复成功",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }else{
                        Toast toast=Toast.makeText(CommentDetailActivity.this,"回复内容不能为空",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                }
                etComment.setText("");
                etComment.setHint("矜持点赞也可以，知音难觅聊一句");
                //隐藏软键盘
                InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });
    }
    //查询当前用户点赞过哪些评论
    class UserLikeComment extends AsyncTask{
        private String path;
        public UserLikeComment(String path) {
            this.path = path;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            URL url= null;
            try {
                url = new URL(path+"UserServlet?userId="+user.getUserId()+"&remark=userlikeComment");
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType","utf-8");
                InputStream is=connection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(is);
                BufferedReader reader=new BufferedReader(inputStreamReader);
                String res=reader.readLine();
                JSONArray jsonArray=new JSONArray(res);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject object=jsonArray.getJSONObject(i);
                    commentlist.add(object.getInt("commentId"));
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
            UserLikeReply userLikeReply=new UserLikeReply(path);
            userLikeReply.execute();
        }
    }
    //查询当前用户点赞过哪些回复
    class UserLikeReply extends AsyncTask{
        private String path;
        public UserLikeReply(String path) {
            this.path = path;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            URL url= null;
            try {
                url = new URL(path+"UserServlet?userId="+user.getUserId()+"&remark=userlikeReply");
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType","utf-8");
                InputStream is=connection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(is);
                BufferedReader reader=new BufferedReader(inputStreamReader);
                String res=reader.readLine();
                JSONArray jsonArray=new JSONArray(res);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject object=jsonArray.getJSONObject(i);
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
    }
    //添加评论的异步类
    class AddCommentTask extends AsyncTask{
        private String path;
        private String content;
        public AddCommentTask(String path,String content) {
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
                writer.write("remark=addComment&commentDetail="+content+"&userId="+user.getUserId()+"&noteId="+noteId);
                writer.flush();
                writer.close();
                connection.connect();
                InputStream is=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(is));
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
            //添加完评论后重新加载评论
            comments=new ArrayList<>();
            CommentTask commentTask=new CommentTask();
            commentTask.execute();
        }
    }
    //查看评论的异步类
    public class CommentTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            //准备数据
            SimpleDateFormat sdf=new SimpleDateFormat("MM-dd hh:mm");
            Date date=new Date();
            try {
                URL url=new URL(path+"CommentServlet?noteId="+noteId+"&remark=selectComment");
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType","utf-8");
                InputStream is=connection.getInputStream();
                InputStreamReader reader=new InputStreamReader(is);
                BufferedReader reader1=new BufferedReader(reader);
                String res=reader1.readLine();
                //解析JSON格式
                JSONArray array=new JSONArray(res);
                for(int i=0;i<array.length();i++){
                    JSONObject obj=array.getJSONObject(i);
                    Comment comment=new Comment();
                    comment.setCommentId(obj.getInt("commentId"));
                    comment.setContent(obj.getString("commentDetail"));
                    comment.setCommentTime(obj.getString("commentDate"));
                    comment.setCountZan(obj.getInt("commentLikeCount"));
                    UserBean user=new UserBean(obj.getString("userName"),obj.getString("userPhoto"));
                    comment.setUser(user);
                    comments.add(comment);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return comments;
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            final List<Comment> comments=(List<Comment>)o;
            sumComment.setText(comments.size()+"");
            Intent intent=getIntent();
            String userPhotoPath=intent.getStringExtra("userPhoto");
            ImageTask imageTask=new ImageTask(path+userPhotoPath);
            Object[] objects=new Object[]{userPhoto};
            imageTask.execute(objects);
            ReplyTask replyTask=new ReplyTask();
            replyTask.execute();
        }
    }
    //查看评论页回复的异步类
    class ReplyTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {

            for (Comment comment : comments) {
                ArrayList<Reply> replyList = new ArrayList<>();
                URL url = null;
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
                        reply.setContent(obj.getString("replyDetail"));
                        reply.setTime(obj.getString("replyDate"));
                        reply.setCountZan(obj.getInt("replyLikeCount"));
                        reply.setUserName(obj.getString("userName"));
                        reply.setUserPhoto(obj.getString("userPhoto"));
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
                replyMap.put(comment.getCommentId(),replyList);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            //创建Adapter对象
            final CommentAdapter commentAdapter=new CommentAdapter(comments,R.layout.item_comment,CommentDetailActivity.this,replyMap,commentlist,replylist,user,path);
            //设置Adapter
            listView.setAdapter(commentAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String content=comments.get(position).getUser().getUserName()+":"+comments.get(position).getContent();
                    showPopupWindow(content,position);
                }
            });
        }
    }
    //添加评论回复的异步类
    class AddReplyTask extends AsyncTask{
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
                writer.write("remark=addReply&replyDetail="+content+"&userId="+user.getUserId()+"&commentId="+currentCommentId);
                writer.flush();
                writer.close();
                connection.connect();
                InputStream is=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                Log.e("result",reader.readLine());
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
            ReplyTask2 replyTask=new ReplyTask2();
            replyTask.execute();
        }
    }
    //重新加载回复了指定的评论
    class ReplyTask2 extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] objects) {
                ArrayList<Reply> replyList = new ArrayList<>();
                URL url = null;
                try {
                    url = new URL(path + "ReplyServlet?commentId=" + currentCommentId+"&remark=selectReply");
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
                        reply.setContent(obj.getString("replyDetail"));
                        reply.setTime(obj.getString("replyDate"));
                        reply.setCountZan(obj.getInt("replyLikeCount"));
                        reply.setUserName(obj.getString("userName"));
                        reply.setUserPhoto(obj.getString("userPhoto"));
                        if (obj.getString("reReplyName").equals("0")) {
                            //是针对评论的回复
                            reply.setCommentId(currentCommentId);
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
                replyMap.put(currentCommentId,replyList);
                return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            //创建Adapter对象
            final CommentAdapter commentAdapter=new CommentAdapter(comments,R.layout.item_comment,CommentDetailActivity.this,replyMap,commentlist,replylist,user,path);
            //设置Adapter
            listView.setAdapter(commentAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String content=comments.get(position).getUser().getUserName()+":"+comments.get(position).getContent();
                    showPopupWindow(content,position);
                }
            });
        }
    }

    private void showPopupWindow(String content,final int position){
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etComment.getWindowToken(), 0);

        LinearLayout linearLayout=findViewById(R.id.rl_root);
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
                //根据position得到点击的那个评论·，回复指定评论
                //自动弹出软键盘
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                etComment.setHint("回复"+comments.get(position).getUser().getUserName()+"…");
                currentCommentId=comments.get(position).getCommentId();
                popupWindow.dismiss();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏popupWindow
                etComment.setHint("矜持点赞也可以，知音难觅聊一句");
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(view);
        //显示PopupWindow
        popupWindow.showAtLocation(linearLayout, Gravity.NO_GRAVITY,0,0);
    }
    //返回上一页
    private void skipPreviousPage(){
        ImageView imageView=findViewById(R.id.iv_rightarrow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentDetailActivity.this.finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        comments=new ArrayList<>();
        CommentTask commentTask=new CommentTask();
        commentTask.execute();
    }
}
