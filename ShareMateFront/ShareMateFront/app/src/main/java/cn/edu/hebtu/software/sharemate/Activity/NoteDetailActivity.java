package cn.edu.hebtu.software.sharemate.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

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

import cn.edu.hebtu.software.sharemate.Adapter.CommentNodeAdapter;
import cn.edu.hebtu.software.sharemate.Bean.Comment;
import cn.edu.hebtu.software.sharemate.Bean.NoteBean;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.Fragment.TuijianFragment;
import cn.edu.hebtu.software.sharemate.tools.GradationScrollView;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.ImageTask;

public class NoteDetailActivity extends AppCompatActivity implements GradationScrollView.ScrollViewListener{
    private RelativeLayout RLtitle;
    private GradationScrollView scrollView;
    private TextView noteTitle;
    private int height;

    private ImageView attentionImage;
    private ImageView zanImage;
    private ImageView collectionImage;

    private ImageView commentImage;
    private boolean isAttention=false;
    private boolean isZan=false;
    private boolean isCollection=false;
    private TextView countZan;
    private TextView countComment;
    private TextView allComment;
    private LinearLayout llComment;
    private LinearLayout bottom;
    private TextView send;
    private EditText etComment;
    private LinearLayout lleditComment;

    private ImageView banner;
    private ImageView userPhoto;
    private TextView userName;
    private TextView noteDetail;
    private TextView noteTime;
    private TextView collectionCount;
    private TextView zanCount;
    private TextView countCollection;
    private ImageView nowUserPhoto;
    private Button back;

    private NoteBean note=new NoteBean();
    private String path;//修改------
    private int userId;//当前注册人的id
    private int noteId;//当前笔记Id
    private UserBean user=new UserBean();//当前注册人
    private UserBean noteUser=new UserBean();//发布笔记的user
    private List<Integer> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        getViewById();
        path=getResources().getString(R.string.server_path);
//        UserLikeComment userLikeComment=new UserLikeComment(path+"UserServlet?userId="+userId+"&remark=userlikeComment");
//        userLikeComment.execute();
//        setNote();

        clickZanAndCollection();
        clickComment();
        toAllComment();
        initListeners();
        //点击返回按钮
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });
    }
    private void getViewById(){
        //标题
        RLtitle=findViewById(R.id.rl_title);
        noteTitle=findViewById(R.id.tv_noteTitle);
        scrollView=findViewById(R.id.scrollView);
        //底部
        attentionImage=findViewById(R.id.iv_attention);
        zanImage=findViewById(R.id.iv_zan);
        collectionImage=findViewById(R.id.iv_collection);
        countCollection=findViewById(R.id.tv_countCollection);

        etComment=findViewById(R.id.et_comment);
        send=findViewById(R.id.tv_send);
        countComment=findViewById(R.id.tv_countComment);
        llComment=findViewById(R.id.ll_comment);
        bottom=findViewById(R.id.ll_bottom);
        lleditComment=findViewById(R.id.ll_editcomment);
        allComment=findViewById(R.id.tv_allComment);
        countZan=findViewById(R.id.tv_countZan);

        //主页
        banner=findViewById(R.id.iv_banner);
        userPhoto=findViewById(R.id.iv_image);
        userName=findViewById(R.id.tv_name);
        noteDetail=findViewById(R.id.tv_detail);
        noteTitle=findViewById(R.id.tv_noteTitle);
        noteTime=findViewById(R.id.tv_time);
        collectionCount=findViewById(R.id.tv_allcountCollection);
        zanCount=findViewById(R.id.tv_allcountZan);

        nowUserPhoto=findViewById(R.id.iv_headimage);
        //返回按钮
        back = findViewById(R.id.btn_back);
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
                url = new URL(path);
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
                    list.add(object.getInt("commentId"));
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
            CommentTask commentTask=new CommentTask();
            commentTask.execute();
        }
    }
    //设置笔记页面
    private void setNote(){
        NoteTask noteTask=new NoteTask();
        noteTask.execute();
    }
    public class NoteTask extends AsyncTask{

        @Override
        protected NoteBean doInBackground(Object[] objects) {
            Intent intent=getIntent();
            noteId=intent.getIntExtra("noteId",0);
            userId=intent.getIntExtra("userId",0);
            try {
                URL url=new URL(path+"NoteServlet?noteId="+noteId);
                Log.e("notePath",path+"NoteServlet?noteId="+noteId);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType","utf-8");
                InputStream is=connection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(is);
                BufferedReader reader=new BufferedReader(inputStreamReader);
                String res=reader.readLine();
                JSONObject object=new JSONObject(res);
                note.setNoId(noteId);
                note.setNoteTitle(object.getString("noteTitle"));
                note.setNoteDetail(object.getString("noteDetail"));
                note.setNoteTime(object.getString("noteDate"));
                UserBean user=new UserBean(object.getString("userName"),object.getString("userPhoto"));
                user.setUserId(object.getInt("userId"));
                note.setUser(user);
                note.setZancount(object.getInt("likeCount"));
                note.setCollectcount(object.getInt("collectCount"));
                note.setPingluncount(object.getInt("commentCount"));
                note.setNoteImagePath(object.getString("noteImage"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return note;
        }
        @Override
        protected void onPostExecute(Object o) {
            //页面修改
            super.onPostExecute(o);
            NoteBean note=(NoteBean) o;
            Log.e("111",note.getUser().getUserId()+"");
            GetUserDetail getUserDetail=new GetUserDetail();
            getUserDetail.execute(note.getUser().getUserId());
            noteTitle.setText(note.getNoteTitle());
            noteDetail.setText(note.getNoteDetail());
            noteTime.setText(note.getNoteTime());
            userName.setText(note.getUser().getUserName());
            zanCount.setText(note.getZancount()+"");
            collectionCount.setText(note.getCollectcount()+"");
            countCollection.setText(note.getCollectcount()+"");
            countComment.setText(note.getPingluncount()+"");
            countZan.setText(note.getZancount()+"");
            allComment.setText("全部评论");
            ImageTask imageTask=new ImageTask(path+note.getNoteImagePath());
            Object[] objects=new Object[]{banner};
            imageTask.execute(objects);

            ImageTask imageTask1=new ImageTask(path+note.getUser().getUserPhotoPath());
            Object[] objects1=new Object[]{userPhoto};
            imageTask1.execute(objects1);
            userPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击用户头像跳转到用户主页修改----）
                    Intent intent=new Intent(NoteDetailActivity.this,FriendActivity.class);
                    intent.putExtra("friend",noteUser);
                    startActivity(intent);
                }
            });
            //当前注册的用户头像
            GetUserTask getUserTask=new GetUserTask();
            getUserTask.execute();
            if (note.getUser().getUserId()==userId){
                attentionImage.setVisibility(View.INVISIBLE);
            }else {
                //判断是否关注
                JudgeFollowTask judgeFollowTask=new JudgeFollowTask(path+"FollowServlet?remark=judgeFollow&userId="+note.getUser().getUserId()+"&followId="+userId);
                Log.e("follow",path+"FollowServlet?remark=judgeFollow&userId="+note.getUser().getUserId()+"&followId="+userId);
                judgeFollowTask.execute();
            }
            //判断是否点赞
            JudgeLikeTask judgeLikeTask=new JudgeLikeTask(path+"LikesServlet?remark=judgeLike&noteId="+note.getNoId()+"&userId="+userId);
            judgeLikeTask.execute();
            //判断是否收藏
            JudgeCollectTask judgeCollectTask=new JudgeCollectTask(path+"CollectServlet?remark=judgeCollect&noteId="+note.getNoId()+"&userId="+userId);
            judgeCollectTask.execute();
        }
    }

    //从数据库中获得UserBean对象
    public class GetUserDetail extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                URL url = new URL(path+"UserServlet?userId="+objects[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("contentType", "UTF-8");
                InputStream is = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String res = br.readLine();
                //解析JSON
                JSONObject jsonObject = new JSONObject(res);
                noteUser.setUserId(jsonObject.getInt("userId"));
                noteUser.setUserPhotoPath(path+jsonObject.getString("userPhoto"));
                noteUser.setUserName(jsonObject.getString("userName"));
                noteUser.setUserSex(jsonObject.getString("userSex"));
                noteUser.setUserBirth(jsonObject.getString("userBirth"));
                noteUser.setUserAddress(jsonObject.getString("userAddress"));
                noteUser.setUserIntroduce(jsonObject.getString("userIntro"));
                noteUser.setFollowCount(jsonObject.getInt("followCount"));
                noteUser.setFanCount(jsonObject.getInt("fanCount"));
                noteUser.setLikeCount(jsonObject.getInt("likeCount"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
    //得到当前登录的用户的头像和用户名
    class GetUserTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            URL url= null;
            try {
                url = new URL(path+"UserServlet?userId="+userId+"&remark=selectUser");
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType","utf-8");
                InputStream is=connection.getInputStream();
                InputStreamReader reader=new InputStreamReader(is);
                BufferedReader reader1=new BufferedReader(reader);
                String res=reader1.readLine();
                JSONObject obj=new JSONObject(res);
                user.setUserId(userId);
                user.setUserPhotoPath(obj.getString("userPhoto"));
                user.setUserName(obj.getString("userName"));
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
            ImageTask imageTask=new ImageTask(path+user.getUserPhotoPath());
            Log.e("userPhoto",path+user.getUserPhotoPath());
            Object[] objects=new Object[]{nowUserPhoto};
            imageTask.execute(objects);
        }
    }

    //--------------------------------------------------node页底部评论----------------------------------------------
    public class CommentTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            //准备数据
            SimpleDateFormat sdf=new SimpleDateFormat("MM-dd hh:mm");
            Date date=new Date();
            List<Comment> comments=new ArrayList<>();
            try {
                URL url=new URL(path+"CommentServlet?noteId="+note.getNoId()+"&remark=selectComment");
                Log.e("noteId",note.getNoId()+"");
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
                    UserBean user=new UserBean(obj.getString("userName"),path+obj.getString("userPhoto"));
                    user.setFanCount(obj.getInt("fanCount"));
                    user.setFollowCount(obj.getInt("followCount"));
                    user.setLikeCount(obj.getInt("likeCount"));
                    user.setUserIntroduce(obj.getString("introduce"));
                    user.setUserId(obj.getInt("userId"));
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
            List<Comment> comments=(List<Comment>)o;
            CommentNodeAdapter commentNodeAdapter=null;
            //note页只显示前3条评论
            if(comments.size()>3){
                List<Comment> newComments=new ArrayList<>();
                newComments.add(comments.get(0));
                newComments.add(comments.get(1));
                newComments.add(comments.get(3));
                //创建Adapter对象
                commentNodeAdapter=new CommentNodeAdapter(newComments,R.layout.item_comment_node,NoteDetailActivity.this,userId,list,path);
            }else{
                //创建Adapter对象
                commentNodeAdapter=new CommentNodeAdapter(comments,R.layout.item_comment_node,NoteDetailActivity.this,userId,list,path);
            }

            //设置Adapter
            ListView listView=findViewById(R.id.lv_comment);
            showAllListView(commentNodeAdapter,listView);
            listView.setAdapter(commentNodeAdapter);
        }
    }
    //----------------------------------解决ScrollView嵌套ListView，item显示不全问题---------------------------------------
    private void showAllListView(CommentNodeAdapter commentAdapter,ListView listView) {
        if (commentAdapter != null) {
            int totalHeight = 0;
            for (int i = 0; i <commentAdapter.getCount(); i++) {
                View listItem =commentAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight+ (listView.getDividerHeight() * (commentAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }

    //--------------------------------------------------------点击关注--------------------------------------------------------------------
    //判断是否关注
    class JudgeFollowTask extends AsyncTask{
        private String urlStr;

        public JudgeFollowTask(String url) {
            this.urlStr = url;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            URL url= null;
            try {
                url = new URL(urlStr);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType","utf-8");
                InputStream is=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                String res=reader.readLine();
                if ("已关注".equals(res)){
                    isAttention=true;
                }else if("未关注".equals(res)){
                    isAttention=false;
                }
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
            super.onPostExecute(o);
            Log.e("isAttention",isAttention+"");
            if (isAttention){
                attentionImage.setImageResource(R.drawable.a9);
            }else{
                attentionImage.setImageResource(R.drawable.a8);
            }
            attentionImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isAttention){
                        //点击取消关注
                        showDialog(0);
                    }else{
                        //点击关注
                        FollowTask followTask=new FollowTask(path+"FollowServlet?remark=addFollow&userId="+note.getUser().getUserId()+"&followId="+userId);
                        followTask.execute();
                    }
                }
            });
        }
    }
    class FollowTask extends AsyncTask{
        private String urlStr;

        public FollowTask(String url) {
            this.urlStr = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            URL url= null;
            try {
                url = new URL(urlStr);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType","utf-8");
                InputStream is=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                String res=reader.readLine();
                if ("关注成功".equals(res)){
                    isAttention=true;
                }else if("取消关注".equals(res)){
                    isAttention=false;
                }
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
            super.onPostExecute(o);
            if (isAttention){
                attentionImage.setImageResource(R.drawable.a9);
            }else{
                attentionImage.setImageResource(R.drawable.a8);
            }
        }
    }
    protected Dialog onCreateDialog(int id) {
        new AlertDialog.Builder(this)
                .setMessage("确定不再关注这位用户了吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //取消关注该用户
                        FollowTask followTask=new FollowTask(path+"FollowServlet?remark=deleteFollow&userId="+note.getUser().getUserId()+"&followId="+userId);
                        followTask.execute();
                    }
                })
                .setNegativeButton("取消",null)
                .create()
                .show();
        return super.onCreateDialog(id);
    }
    //--------------------------------------------------------------点击赞、收藏-----------------------------------------------------
    private void clickZanAndCollection(){
        zanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isZan){
                    LikeTask likeTask=new LikeTask(path+"LikesServlet?remark=deleteLike&noteId="+note.getNoId()+"&userId="+userId);
                    likeTask.execute();
                }else {
                    LikeTask likeTask=new LikeTask(path+"LikesServlet?remark=addLike&noteId="+note.getNoId()+"&userId="+userId);
                    likeTask.execute();
                }

            }
        });

        collectionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCollection){
                    CollectTask collectTask=new CollectTask(path+"CollectServlet?remark=deleteCollect&noteId="+note.getNoId()+"&userId="+userId);
                    collectTask.execute();
                }else{
                    CollectTask collectTask=new CollectTask(path+"CollectServlet?remark=addCollect&noteId="+note.getNoId()+"&userId="+userId);
                    collectTask.execute();
                }
            }
        });
    }
    class JudgeLikeTask extends AsyncTask{
        private String path;

        public JudgeLikeTask(String path) {
            this.path = path;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String res="";
            try {
                URL url=new URL(path);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType","utf-8");
                InputStream is=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                res=reader.readLine();
                if("已点赞".equals(res)){
                    isZan=true;
                }else if ("未点赞".equals(res)){
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
            if (isZan){
                zanImage.setImageResource(R.drawable.a3);
            }else {
                zanImage.setImageResource(R.drawable.a2);
            }
        }
    }

    class LikeTask extends AsyncTask{
        private String path;

        public LikeTask(String path) {
            this.path = path;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String res="";
            try {
                URL url=new URL(path);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType","utf-8");
                InputStream is=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                res=reader.readLine();
                if("点赞成功".equals(res)){
                    isZan=true;
                }else if ("取消赞".equals(res)){
                    isZan=false;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(Object o) {
            if (isZan){
                zanImage.setImageResource(R.drawable.a3);
                int count=Integer.parseInt(countZan.getText().toString());
                count++;
                countZan.setText(count+"");
                zanCount.setText(count+"");
            }else {
                zanImage.setImageResource(R.drawable.a2);
                TextView countZan=findViewById(R.id.tv_countZan);
                int count=Integer.parseInt(countZan.getText().toString());
                count--;
                countZan.setText(count+"");
                zanCount.setText(count+"");
            }
        }
    }
    class JudgeCollectTask extends AsyncTask{
        private String path;

        public JudgeCollectTask(String path) {
            this.path = path;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            URL url= null;
            try {
                url = new URL(path);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType","utf-8");
                InputStream is=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                String res=reader.readLine();
                if ("已收藏".equals(res)){
                    isCollection=true;
                }else if("未收藏".equals(res)){
                    isCollection=false;
                }
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
            if (isCollection){
                collectionImage.setImageResource(R.drawable.a6);
            }else{
                collectionImage.setImageResource(R.drawable.a5);
            }
        }
    }
    class CollectTask extends AsyncTask{
        private String path;

        public CollectTask(String path) {
            this.path = path;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            URL url = null;
            try {
                url = new URL(path);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType","utf-8");
                InputStream is=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                String res=reader.readLine();
                if ("增加收藏".equals(res)){
                    isCollection=true;
                }else if("取消收藏".equals(res)){
                    isCollection=false;
                }
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
            if (isCollection) {
                collectionImage.setImageResource(R.drawable.a6);
                TextView countCollection = findViewById(R.id.tv_countCollection);
                int count = Integer.parseInt(countCollection.getText().toString());
                count++;
                countCollection.setText(count + "");
                collectionCount.setText(count + "");
            }else{
                collectionImage.setImageResource(R.drawable.a5);
                TextView countCollection=findViewById(R.id.tv_countCollection);
                int count=Integer.parseInt(countCollection.getText().toString());
                count--;
                countCollection.setText(count+"");
                collectionCount.setText(count+"");
            }
        }
    }

    //---------------------------------------------------------------点击评论----------------------------------------------------------
    private void clickComment(){
        llComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //自动出现软键盘
                InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                etComment.requestFocus();
                //隐藏底部,显示EditView
                bottom.setVisibility(View.INVISIBLE);
                lleditComment.setVisibility(View.VISIBLE);
            }
        });
        //点击发送
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发布评论,添加到数据库
                String commentContent=etComment.getText().toString();
                Log.e("commentContent",commentContent);
                if (!commentContent.trim().isEmpty()){
                    AddCommentTask addCommentTask=new AddCommentTask(path+"CommentServlet",commentContent);
                    addCommentTask.execute();
                    //Toast
                    Toast toast=Toast.makeText(NoteDetailActivity.this,"评论成功",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }else {
                    //Toast
                    Toast toast=Toast.makeText(NoteDetailActivity.this,"评论不能为空",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                //隐藏软键盘
                InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                //显示底部,隐藏EditView
                bottom.setVisibility(View.VISIBLE);
                lleditComment.setVisibility(View.INVISIBLE);

                //评论数加1
                int count=Integer.parseInt(countComment.getText().toString());
                count++;
                countComment.setText(count+"");
            }
        });
    }
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
                writer.write("remark=addComment&commentDetail="+content+"&userId="+user.getUserId()+"&noteId="+note.getNoId());
                Log.e("addComment",user.getUserId()+"");
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
            CommentTask commentTask=new CommentTask();
            commentTask.execute();
        }
    }
    //标题特效
    //----------------------------------获取顶部图片高度后，设置滚动监听--------------------------------------------------------
    private void initListeners(){
        ViewTreeObserver vto=banner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                RLtitle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                height=banner.getHeight();
                scrollView.setScrollViewListener(NoteDetailActivity.this);
            }
        });
    }
    //------------------------------------------------------滑动监听--------------------------------------------------------------------
    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {   //设置标题的背景颜色
            RLtitle.setBackgroundColor(Color.argb((int) 0, 0,0,0));
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            noteTitle.setTextColor(Color.argb((int) alpha, 255,255,255));
            noteTitle.setTextSize(18);
            RLtitle.setBackgroundColor(Color.argb((int) alpha, 222,65,56));
        } else {    //滑动到banner下面设置普通颜色
            RLtitle.setBackgroundColor(Color.argb((int) 255, 222,65,56));
        }
    }

    //-----------------------------------------跳转到全部评论页面-------------------------------------------------------------------
    private void toAllComment(){
        allComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NoteDetailActivity.this,CommentDetailActivity.class);
                intent.putExtra("noteId",noteId);
                intent.putExtra("userId",user.getUserId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserLikeComment userLikeComment=new UserLikeComment(path+"UserServlet?userId="+userId+"&remark=userlikeComment");
        userLikeComment.execute();
        setNote();
    }
}
