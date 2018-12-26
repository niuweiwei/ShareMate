package cn.edu.hebtu.software.sharemate.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
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

import cn.edu.hebtu.software.sharemate.Activity.CommentDetailActivity;
import cn.edu.hebtu.software.sharemate.Activity.FriendActivity;
import cn.edu.hebtu.software.sharemate.Activity.NoteDetailActivity;
import cn.edu.hebtu.software.sharemate.Activity.PersonalActivity;
import cn.edu.hebtu.software.sharemate.Adapter.CustomAdapter;
import cn.edu.hebtu.software.sharemate.Bean.CommentBean;
import cn.edu.hebtu.software.sharemate.Bean.NoteBean;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class FollowFragment extends Fragment implements CustomAdapter.Callback,AdapterView.OnClickListener {
    private List<NoteBean> notes=new ArrayList<NoteBean>();
    private ListTask listTask;
    private ZanTask zanTask;
    private FollowTask followTask;
    private CollectTask collectTask;
    private CommentTask commentTask;
    private ListView listView;
    private CustomAdapter customAdapter = null;
    private int userId;
    private TextView textView;
    private ImageView imageView;
    private Handler handler=null;
    private String U;
    private Map<Integer,Boolean> isLike=new HashMap<>();
    private Map<Integer,Boolean> isFollow=new HashMap<>();
    private Map<Integer,Boolean> isCollect=new HashMap<>();
    private Map<Integer,Boolean> isComment=new HashMap<>();
    //@SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.note_fragment, container, false);
        U=getResources().getString(R.string.server_path);
        textView = view.findViewById(R.id.text);
        imageView = view.findViewById(R.id.img);
        handler = new Handler();
        userId = getActivity().getIntent().getIntExtra("userId",0);
        Log.e("关注 userId",userId+"");

        listView = view.findViewById(R.id.list);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //getActivity().dispatchTouchEvent(event);
                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(getActivity().getCurrentFocus()!=null && getActivity().getCurrentFocus().getWindowToken()!=null){
                        manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
    }
    @Override  //item内部点击事件，点赞收藏和评论
    public void click(View v) {
        switch (v.getId()) {
            //点击关注的用户头像跳转至该用户资料详情页
            case R.id.user_icon:
                int u = (int) v.getTag();
                UserBean userBean =new UserBean();
                userBean.setUserId(notes.get(u).getUser().getUserId());
                userBean.setUserPhotoPath(notes.get(u).getUser().getUserPhotoPath());
                userBean.setUserName(notes.get(u).getUser().getUserName());
                userBean.setUserSex(notes.get(u).getUser().getUserSex());
                userBean.setUserAddress(notes.get(u).getUser().getUserAddress());
                userBean.setUserBirth(notes.get(u).getUser().getUserBirth());
                userBean.setUserIntroduce(notes.get(u).getUser().getUserIntroduce());
                Intent perIntent = new Intent();
                perIntent.setClass(getActivity(), FriendActivity.class);
                perIntent.putExtra("friend",userBean);
                startActivity(perIntent);
                break;
            //关注和取消关注
            case R.id.guannzhu:
                int p = (int) v.getTag();
                if(isFollow.get(p)==null){
                    isFollow.put(p,true);
                }
                int noteid1 = notes.get(p).getNoId();
                followTask = new FollowTask();
                followTask.execute(noteid1,isFollow.get(p));
                if(!isFollow.get(p)){
                    notes.get(p).setFol(1);
                    customAdapter.notifyDataSetChanged();
                    isFollow.put(p,true);
                } else {
                    notes.get(p).setFol(-1);
                    customAdapter.notifyDataSetChanged();
                    isFollow.put(p,false);
                    //Toast
                    Toast toast=Toast.makeText(getActivity(),"取消关注啦",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                break;
            //点赞获取消赞
            case R.id.dianzan:
                int position = (int) v.getTag();
                if(isLike.get(position)==null){
                    if(notes.get(position).isIslike()==1)
                    {isLike.put(position,true);}else{
                        isLike.put(position,false);
                    }
                }
                int noteid = notes.get(position).getNoId();
                zanTask = new ZanTask();
                zanTask.execute(noteid,isLike.get(position));
                if(!isLike.get(position)){
                    notes.get(position).setIslike(1);
                    notes.get(position).setZan(R.drawable.xihuan2);
                    int c = Integer.parseInt(notes.get(position).getZancount1());c++;
                    notes.get(position).setZancount1(c+"");
                    isLike.put(position,true);
                    //Toast
                    Toast toast=Toast.makeText(getActivity(),"点赞的你颜值超高",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                    customAdapter.notifyDataSetChanged();
                }
                else{
                    notes.get(position).setIslike(0);
                    notes.get(position).setZan(R.drawable.xin);
                    int c = Integer.parseInt(notes.get(position).getZancount1());c--;
                    notes.get(position).setZancount1(c+"");
                    isLike.put(position,false);
                    //Toast
                    Toast toast=Toast.makeText(getActivity(),"赞取消了哦",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                    customAdapter.notifyDataSetChanged();
                }
                break;
            //收藏和取消收藏
            case R.id.shoucang:
                int position2 = (int) v.getTag();
                if(isCollect.get(position2)==null){
                    if(notes.get(position2).getIscollect()==1)
                    {isCollect.put(position2,true);}else{
                        isCollect.put(position2,false);
                    }
                }
                int noteid3 = notes.get(position2).getNoId();
                Log.e("noteid",""+noteid3);
                collectTask = new CollectTask();
                collectTask.execute(noteid3,isCollect.get(position2));
                if(!isCollect.get(position2)){
                    notes.get(position2).setIscollect(1);
                    notes.get(position2).setCol(R.drawable.xingxing2);
                    int c = notes.get(position2).getCollectcount();c++;
                    notes.get(position2).setCollectcount(c);
                    isCollect.put(position2,true);
                    customAdapter.notifyDataSetChanged();
                }else {
                    notes.get(position2).setIscollect(0);
                    notes.get(position2).setCol(R.drawable.xingxing);
                    int c = notes.get(position2).getCollectcount();c--;
                    notes.get(position2).setCollectcount(c);
                    isCollect.put(position2,false);
                    customAdapter.notifyDataSetChanged();
                }
                break;
            //点击发送添加评论
            case R.id.fabu:
                int fp = (int) v.getTag();
                if(isComment.get(fp)==null){
                    isComment.put(fp,false);
                }
                int noteid4 = notes.get(fp).getNoId();
                String comment = notes.get(fp).getCommentDetail();
                Log.e("点击发送",comment);
                if(!comment.equals("")){
                    commentTask = new CommentTask();
                    commentTask.execute(noteid4,isComment.get(fp),comment);
                    int c = notes.get(fp).getPingluncount();c++;
                    notes.get(fp).setPingluncount(c);
                    notes.get(fp).setCommentDetail(notes.get(fp).getUserContent().getUserName()+":"+comment);
                }
                //TextView t = v.getRootView().findViewById(R.id.pinglun2);
                //t.setText("小明:"+notes.get(fp).getCommentDetail());
                EditText e = v.getRootView().findViewById(R.id.fb);
                e.setText("");
                //Toast
                Toast toast=Toast.makeText(getActivity(),"评论成功",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                customAdapter.notifyDataSetChanged();
                break;
            //点击评论图标跳转至评论页面
            case R.id.pinglun:
                int c=(int)v.getTag();
                UserBean user =new UserBean();
                user.setUserId(notes.get(c).getUserContent().getUserId());
                user.setUserPhotoPath(notes.get(c).getUserContent().getUserPhotoPath());
                Log.e("userPath",notes.get(c).getUserContent().getUserPhotoPath());
                user.setUserName(notes.get(c).getUserContent().getUserName());
                user.setUserSex(notes.get(c).getUserContent().getUserSex());
                user.setUserAddress(notes.get(c).getUserContent().getUserAddress());
                user.setUserBirth(notes.get(c).getUserContent().getUserBirth());
                user.setUserIntroduce(notes.get(c).getUserContent().getUserIntroduce());
                int noteId = notes.get(c).getNoId();
                Intent intent=new Intent(getActivity(),CommentDetailActivity.class);
                intent.putExtra("noteId",noteId);
                intent.putExtra("user",user);
                startActivity(intent);
                break;
        }
    }

    //点赞触发进程，向服务器端传入noteID和用户id
    public class ZanTask extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                int noteid = (int) objects[0];
                boolean islike = (boolean) objects[1];
                Log.e("noteid", noteid+"");Log.e("like", islike+"");
                URL url = new URL(U+"FollowNoteServlet?userId="+userId+"&noteId="+noteid+"&islike="+islike);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType", "UTF-8");
                InputStream is = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String res = reader.readLine();
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
            customAdapter.notifyDataSetChanged();
        }
    }
    //添加关注和取消关注的进程
    public class FollowTask extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                int noteid = (int) objects[0];
                boolean isfollow = (boolean) objects[1];
                Log.e("noteid", noteid+"");Log.e("follow", isfollow+"");
                URL url = new URL(U+"FollowNoteServlet?userId="+userId+"&noteId="+noteid+"&isfollow="+isfollow);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType", "UTF-8");
                InputStream is = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String res = reader.readLine();
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
            listTask = new ListTask();
            listTask.execute();
            customAdapter.notifyDataSetChanged();
        }
    }
    // 添加收藏和取消收藏
    public class CollectTask extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                int noteid = (int) objects[0];
                boolean iscollect = (boolean) objects[1];
                Log.e("noteid", noteid+"");Log.e("collect", iscollect+"");
                URL url = new URL(U+"FollowNoteServlet?userId="+userId+"&noteId="+noteid+"&iscollect="+iscollect);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType", "UTF-8");
                InputStream is = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String res = reader.readLine();
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
            customAdapter.notifyDataSetChanged();
        }
    }
    //添加评论，需要传入用户id和评论内容和noteid
    public class CommentTask extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                int noteid = (int) objects[0];
                boolean iscommet = (boolean) objects[1];
                String commentdetail = (String)objects[2];
                Log.e("noteid", noteid+"");Log.e("like", iscommet+"");
                URL url = new URL(U+"FollowNoteServlet?userId="+userId+"&noteId="+noteid+"&iscomment="+iscommet+"&commentdetail="+commentdetail);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType", "UTF-8");
                InputStream is = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String res = reader.readLine();
                //解析Json字符串
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray array = jsonObject.getJSONArray("note");
                    for (int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        NoteBean note1 = new NoteBean();
                        String img = object.getString("noteImage");
                        String url1 = U+img;
                        URL url2= new URL(url1);
                        HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
                        BufferedInputStream ins = new BufferedInputStream(conn.getInputStream());
                        Bitmap a= BitmapFactory.decodeStream(ins);
                        note1.setNoteImage1(a);
                        note1.setNoteTitle(object.getString("noteTitle"));
                        note1.setNoteDetail(object.getString("noteDetail"));
                        note1.setNoteTime(object.getString("noteDate"));
                        JSONObject object2 = object.getJSONObject("user");
                        UserBean user = new UserBean();
                        user.setUserName(object2.getString("userName"));
                        user.setUserId(object2.getInt("userId"));
                        //user.setUserPhoto(object2.getString("usePhoto"));
                        String userImg = object2.getString("userPhoto");
                        String url3 = U+userImg;
                        URL urluser= new URL(url3);
                        HttpURLConnection conn2 = (HttpURLConnection) urluser.openConnection();
                        BufferedInputStream ins2 = new BufferedInputStream(conn2.getInputStream());
                        Bitmap b= BitmapFactory.decodeStream(ins2);
                        //user.setUserPhoto(R.drawable.a1);
                        user.setUserImage(b);
                        note1.setUser(user);
                        note1.setNoId(object.getInt("noteId"));
                        note1.setZancount1(String.valueOf(object.getInt("noteLikeCount")));
                        note1.setCollectcount(object.getInt("noteCollectionCount"));
                        note1.setPingluncount(object.getInt("noteCommentCount"));
                        int j;boolean c=true;
                        for(j=0;j<notes.size();j++){
                            if(note1.getNoId()==notes.get(j).getNoId()){
                                c=false;
                                break;
                            }
                        }
                        if(c) {notes.add(note1);}
                    }

                    Log.e("LoginTask", notes.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
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
            super.onPostExecute(o);
            customAdapter.notifyDataSetChanged();
        }
    }
    //从服务器获取登录用户所关注的所有笔记
    public class ListTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            notes=new ArrayList<>();
            try {
                URL url = new URL(U+"FollowNoteServlet?userId="+userId);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType", "UTF-8");
                InputStream is = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String res = reader.readLine();
                //解析Json字符串
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray array = jsonObject.getJSONArray("note");
                    for (int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        NoteBean note1 = new NoteBean();
                        String img = object.getString("noteImage");
                        String url1 = U+img;
                        URL url2= new URL(url1);
                        HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
                        BufferedInputStream ins = new BufferedInputStream(conn.getInputStream());
                        Bitmap a= BitmapFactory.decodeStream(ins);
                        note1.setNoteImage1(a);
                        note1.setNoteTitle(object.getString("noteTitle"));
                        note1.setNoteDetail(object.getString("noteDetail"));
                        note1.setNoteTime(object.getString("noteDate"));
                        JSONObject object2 = object.getJSONObject("user");
                        JSONObject object3 = object.getJSONObject("userContent");
                        JSONArray commentarray= object.getJSONArray("comment");
                        int count = object.getInt("noteCommentCount")-1;
                        JSONObject commentobject = commentarray.getJSONObject(count);
                        JSONObject comuser = commentobject.getJSONObject("user");
                        CommentBean notecomment=new CommentBean();
                        UserBean usercomment = new UserBean();
                        usercomment.setUserName(comuser.getString("userName"));
                        notecomment.setUser(usercomment);
                        notecomment.setCommentDetail(commentobject.getString("commentDetail"));
                        UserBean user = new UserBean();
                        user.setUserName(object2.getString("userName"));
                        user.setUserId(object2.getInt("userId"));
                        user.setUserSex(object2.getString("userSex"));
                        user.setUserBirth(object2.getString("userBirth"));
                        user.setUserIntroduce(object2.getString("userIntro"));
                        user.setUserAddress(object2.getString("userAddress"));
                        String userImg = object2.getString("userPhoto");
                        user.setUserPhotoPath(userImg);
                        String url3 = U+userImg;
                        URL urluser= new URL(url3);
                        HttpURLConnection conn2 = (HttpURLConnection) urluser.openConnection();
                        BufferedInputStream ins2 = new BufferedInputStream(conn2.getInputStream());
                        Bitmap b= BitmapFactory.decodeStream(ins2);
                        user.setUserImage(b);
                        note1.setUser(user);
                        //获取当前用户信息
                        UserBean user2 = new UserBean();
                        user2.setUserName(object3.getString("userName"));
                        user2.setUserId(object3.getInt("userId"));
                        user2.setUserSex(object3.getString("userSex"));
                        user2.setUserBirth(object3.getString("userBirth"));
                        user2.setUserIntroduce(object3.getString("userIntro"));
                        user2.setUserAddress(object3.getString("userAddress"));
                        String userImg2 = object3.getString("userPhoto");
                        user2.setUserPhotoPath(userImg2);
                        String url4 = U+userImg2;
                        URL urluser2= new URL(url4);
                        HttpURLConnection conn3 = (HttpURLConnection) urluser2.openConnection();
                        BufferedInputStream ins3 = new BufferedInputStream(conn3.getInputStream());
                        Bitmap d= BitmapFactory.decodeStream(ins3);
                        user2.setUserImage(d);
                        note1.setUserContent(user2);
                        note1.setCommentBean(notecomment);
                        note1.setNoId(object.getInt("noteId"));
                        note1.setZancount1(String.valueOf(object.getInt("noteLikeCount")));
                        note1.setCollectcount(object.getInt("noteCollectionCount"));
                        note1.setPingluncount(object.getInt("noteCommentCount"));
                        note1.setIslike(object.getInt("like"));
                        note1.setIscollect(object.getInt("isCollect"));
                        note1.setIsfollow(1);
                        note1.setFol(R.drawable.cancelfollowedbutton_style);
                       notes.add(note1);
                    }
                    if(notes.size()==0){
                        handler.post(runnableUi);
                    }
                    Log.e("LoginTask", notes.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
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
            super.onPostExecute(o);
            customAdapter = new CustomAdapter(getActivity(), R.layout.list_item, FollowFragment.this, notes);
            listView.setAdapter(customAdapter);
            customAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if ( listTask != null && listTask.getStatus() == ListTask.Status.RUNNING ){
            listTask.cancel(true);
            listTask = null;
        }
        if ( zanTask != null && zanTask.getStatus() == ZanTask.Status.RUNNING ){
            zanTask.cancel(true);
            zanTask = null;
        }
        if ( followTask != null && followTask.getStatus() == FollowTask.Status.RUNNING ){
            followTask.cancel(true);
            followTask = null;
        }
        if ( collectTask != null && collectTask.getStatus() == CollectTask.Status.RUNNING ){
            collectTask.cancel(true);
            collectTask = null;
        }
        if ( commentTask != null && commentTask.getStatus() == CommentTask.Status.RUNNING ){
            commentTask.cancel(true);
            commentTask = null;
        }
    }
    Runnable   runnableUi=new  Runnable(){
        @Override
        public void run() {
            //更新界面
            textView.setText("啊哦~ \n还没有关注的人笔记哦");
            textView.setTextSize(25);
            imageView.setImageResource(R.drawable.meng);
            imageView.setMaxHeight(100);imageView.setMaxWidth(100);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        listTask = new ListTask();
        listTask.execute();
    }
}
