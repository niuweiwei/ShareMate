package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.CommentListAdapter;
import cn.edu.hebtu.software.sharemate.Bean.CommentBean;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class CommentActivity extends AppCompatActivity {

    private PopupWindow window = null;
    private RelativeLayout root = null;
    private View view = null;
    private RelativeLayout replyLayout =null;
    private InputMethodManager manager = null;
    private Button send = null;
    private EditText text = null;
    private CommentListAdapter adapter = null;
    private final List<CommentBean> comments = new ArrayList<>();
    private ListView listView = null;
    private String path = null;
    private int currentUserId ;//表示当前用户的id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        currentUserId = getIntent().getIntExtra("userId",0);
        root = findViewById(R.id.root);
        replyLayout = findViewById(R.id.rl_reply);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        listView = findViewById(R.id.lv_comment);
        path = getResources().getString(R.string.server_path);
        //点击 listview 的每一个子项 弹出 popupwindow
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                manager.hideSoftInputFromWindow(CommentActivity.this.getCurrentFocus().getWindowToken(), 0);
                //将回复框隐藏 关闭软键盘
                if(replyLayout.getVisibility()==View.VISIBLE) {
                    replyLayout.setVisibility(View.GONE);
//                    manager.hideSoftInputFromWindow(CommentActivity.this.getCurrentFocus().getWindowToken(), 0);
                }
                window = new PopupWindow(root,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                window.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        addBackgroundAlpha(1f);
                        window.dismiss();
                    }
                });
                if(window.isShowing()){
                    window.dismiss();
                }else{
                        showPopupWindow(root,position);
                        addBackgroundAlpha(0.7f);
                }
            }
        });

        //为发送回复的按钮绑定事件
        send = findViewById(R.id.btn_send);
        text = findViewById(R.id.et_reply);

        CommentTask commentTask = new CommentTask();
        commentTask.execute();

        //为返回按钮绑定事件
        Button back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //显示 popupwindow
    private void showPopupWindow(RelativeLayout root, final int position){

        //获取到要显示的视图
        if(view == null){
            view = getLayoutInflater().inflate(R.layout.comments_popupwindow_layout,null);
        }
        //通过view获取到各个按钮 并绑定事件监听器
        final Button like = view.findViewById(R.id.btn_like);
        //根据该用户是否对该评论赞而现实button 中的数据
        if(comments.get(position).isLike()){
            //表示该用户对该评论点赞过
            like.setText("取消赞");
        }else
            like.setText("赞");

        final Button reply = view.findViewById(R.id.btn_reply);
        Button detail = view.findViewById(R.id.btn_detail);
        final Button commentList = view.findViewById(R.id.btn_commentlist);
        Button delete = view.findViewById(R.id.btn_delete);
        Button cancel = view.findViewById(R.id.btn_cancel);

        //为popupwindow中的按钮绑定监听器

        //为赞或取消赞绑定监听器
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(like.getText().equals("赞")){
                    int tag = comments.get(position).getTag();
                    int id = comments.get(position).getId();
                    LikeTask likeTask = new LikeTask();
                    likeTask.execute(currentUserId,tag,id,"like");
                    comments.get(position).setLike(true);
                    Toast likeToast = Toast.makeText(CommentActivity.this,"点赞成功",Toast.LENGTH_SHORT);
                    likeToast.setGravity(Gravity.TOP,0,300);
                    likeToast.show();
                }else{
                    int tag = comments.get(position).getTag();
                    int id = comments.get(position).getId();
                    LikeTask likeTask = new LikeTask();
                    likeTask.execute(currentUserId,tag,id,"cancel");
                    comments.get(position).setLike(false);
                    Toast cancelToast = Toast.makeText(CommentActivity.this,"取消赞成功",Toast.LENGTH_SHORT);
                    cancelToast.setGravity(Gravity.TOP,0,300);
                    cancelToast.show();
                }
                window.dismiss();
            }
        });

        //为回复选项绑定监听器
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                replyLayout.setVisibility(View.VISIBLE);
                EditText replyInput = findViewById(R.id.et_reply);
                replyInput.setHint("回复 "+comments.get(position).getUser().getUserName()+":");

                //使得回复输入框自动获取焦点
                replyInput.setFocusable(true);
                replyInput.setFocusableInTouchMode(true);
                replyInput.requestFocus();
                //自动弹出软键盘
                manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        //为发送按钮绑定监听器
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text.getText().toString();
                if(msg.trim().isEmpty()){
                    //nothing to do
                }else{
                    //开启异步任务 添加回复
                    SendTask sendTask = new SendTask();
                    sendTask.execute(currentUserId,comments.get(position).getTag(),msg,comments.get(position).getId());
                    //回复框隐藏 软键盘消失
                    if(replyLayout.getVisibility()==View.VISIBLE) {
                        replyLayout.setVisibility(View.GONE);
                        manager.hideSoftInputFromWindow(CommentActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }
                    //弹出toast提示用户回复成功或失败
                    Toast replySuccess = Toast.makeText(CommentActivity.this,"发表评论成功",Toast.LENGTH_SHORT);
                    replySuccess.setGravity(Gravity.TOP,0,300);
                    replySuccess.show();
                }
            }
        });

        //为 delete按钮绑定事件 弹出对话框 提示用户是否删除该条评论
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //popupwindow 消失
                window.dismiss();
                //弹出提示
                AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确认删除该评论");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //nothing to do
                    }
                });
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //开启异步任务 数据库删除该评论
                       DeleteTask deleteTask = new DeleteTask(position);
                        deleteTask.execute(comments.get(position).getTag(),comments.get(position).getId());
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //为其余三个按钮绑定监听器
        Listener listener = new Listener(position);
        detail.setOnClickListener(listener);
        commentList.setOnClickListener(listener);
        cancel.setOnClickListener(listener);

        //将视图设置到 window
        window.setContentView(view);
        //控制 popupwindow 再点击屏幕其他地方时自动消失
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.showAtLocation(root, Gravity.BOTTOM,0,0);
    }

    //调节背景的透明度
    private void addBackgroundAlpha(float alpha){
        //获取到当前屏幕的一系列参数
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = alpha;
        //设置参数
        getWindow().setAttributes(params);
    }

    //监听器类
    private class Listener implements View.OnClickListener{
        private int position;

        public Listener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_detail:
                    //跳转到笔记详情页面
                    Intent intent = new Intent(CommentActivity.this,NoteDetailActivity.class);
                    intent.putExtra("noteId",comments.get(position).getNoteId());
                    intent.putExtra("userId",currentUserId);
                    startActivity(intent);
                    break;
                case R.id.btn_commentlist:
                    Intent intent1 = new Intent(CommentActivity.this,CommentDetailActivity.class);
                    intent1.putExtra("noteId",comments.get(position).getNoteId());
                    intent1.putExtra("userId",currentUserId);
                    startActivity(intent1);
                    break;
                case R.id.btn_cancel:
                    window.dismiss();
                    break;
            }
        }
    }

    //异步任务：获取评论及所有回复
    private class CommentTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                URL url = new URL(path+"CAndRServlet?userId="+currentUserId);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType","utf-8");

                String result = "";
                InputStream is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = "";
                while((line=reader.readLine())!=null){
                    result+=line;
                }

                if(comments.size()!=0)
                    comments.clear();
                JSONArray array = new JSONArray(result);
                Log.e("array",array.length()+"");
                for(int i=0;i<array.length();i++) {

                    JSONObject object = array.getJSONObject(i);
                    CommentBean comment = new CommentBean();
                    comment.setTag(object.getInt("tag"));
                    UserBean publisher = new UserBean();
                    publisher.setUserId(object.getInt("publisherId"));
                    publisher.setUserName(object.getString("publisherName"));
                    publisher.setUserPhotoPath(path+object.getString("publisherPhotoPath"));
                    publisher.setUserIntroduce(object.getString("publisherIntro"));
                    publisher.setFanCount(object.getInt("fanCount"));
                    publisher.setFollowCount(object.getInt("followCount"));
                    publisher.setLikeCount(object.getInt("likeCount"));
                    comment.setUser(publisher);
                    comment.setUserId(object.getInt("userId"));
                    if(object.getInt("userId")==currentUserId){
                        comment.setName("我");
                    }else {
                        comment.setName(object.getString("userName"));
                    }
                    comment.setNoteId(object.getInt("noteId"));
                    comment.setNotePhotoPath(path+object.getString("notePhotoPath"));
                    comment.setComment(object.getString("content"));
                    comment.setArgued(object.getString("argued"));
                    comment.setDate(object.getString("date"));
                    comment.setId(object.getInt("id"));
                    comment.setLike(object.getBoolean("isLike"));
                    comment.setArguedId(object.getInt("arguedId"));
                    comments.add(comment);
                }
                Log.e("size",comments.size()+"");
            } catch (MalformedURLException e) {
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

            adapter = new CommentListAdapter(CommentActivity.this,comments,R.layout.comment_list_item_layout);
            listView.setAdapter(adapter);
        }
    }

    //异步：任务赞评论或者是回复
    private class LikeTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            int userId = (Integer)objects[0];
            int tag = (Integer)objects[1];
            int id = (Integer)objects[2];
            String act = (String) objects[3];

            try {
                String str = path+"LikeCAndRServlet?userId="+userId+"&tag="+tag+"&id="+id+"&act="+act;
                URL url = new URL(str);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType","utf-8");

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

    //异步任务：对评论或者回复进行回复
    private class SendTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            int userId = (Integer)objects[0];
            int tag = (Integer)objects[1];
            String replyContent = (String) objects[2];
            int id = (Integer)objects[3];

            try {
                URL url = new URL(path+"AddReplyServlet");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("contentType","utf-8");

                JSONObject object = new JSONObject();
                object.put("userId",userId);
                object.put("tag",tag);
                object.put("replyContent",replyContent);
                object.put("id",id);

                OutputStream os = connection.getOutputStream();
                BufferedWriter br = new BufferedWriter(new OutputStreamWriter(os));
                br.write(object.toString());
                br.flush();
                br.close();

                InputStream is = connection.getInputStream();

            } catch (MalformedURLException e) {
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
            super.onPostExecute(o);
            text.setText("");
        }
    }

    //异步任务：删除评论或回复
    private class DeleteTask extends AsyncTask{

        private int position;

        public DeleteTask(int position) {
            this.position = position;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            int tag = (Integer)objects[0];
            int id = (Integer)objects[1];
            try {
                URL url = new URL(path+"DeleteCAndRServlet?tag="+tag+"&id="+id);
                HttpURLConnection connection =(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType","utf-8");

                InputStream is = connection.getInputStream();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Toast deleteSuccess = Toast.makeText(CommentActivity.this,"评论删除成功",Toast.LENGTH_SHORT);
            deleteSuccess.setGravity(Gravity.TOP,0,300);
            deleteSuccess.show();
            new CommentTask().execute();
            adapter.notifyDataSetChanged();
        }
    }
}
