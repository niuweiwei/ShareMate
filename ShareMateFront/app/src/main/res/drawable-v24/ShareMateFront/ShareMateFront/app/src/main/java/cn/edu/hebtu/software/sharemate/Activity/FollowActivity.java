package cn.edu.hebtu.software.sharemate.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.FocusAdapter;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;


public class FollowActivity extends AppCompatActivity {
    //user表示当前用户
    private UserBean user;
    private ImageView imageView;
    private ListView listView;
    private FocusAdapter focusAdapter;
    //userList是关注的好友
    private List<UserBean> userList = new ArrayList<>();
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        findViews();
        path = getResources().getString(R.string.server_path);
        GetFriend getFriend = new GetFriend();
        getFriend.execute(user);
        setListView(listView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FollowActivity.this,MainActivity.class);
                intent.putExtra("flag","my");
                startActivity(intent);
            }
        });
    }

    //从数据库中取出当前用户关注的好友
    public class GetFriend extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] objects) {
            UserBean userBean = (UserBean) objects[0];
            int userId = userBean.getUserId();
            try {
                URL url = new URL(path+"FriendServlet?userId="+userId);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream is = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String res = br.readLine();
                //解析JSON
                JSONArray array = new JSONArray(res);
                for(int i=0 ; i<array.length() ; i++){
                    JSONObject userObject = array.getJSONObject(i);
                    UserBean friend = new UserBean();
                    friend.setUserId(userObject.getInt("userId"));
                    friend.setUserName(userObject.getString("userName"));
                    friend.setUserPhotoPath(userObject.getString("userPhoto"));
                    friend.setUserIntroduce(userObject.getString("userIntro"));
                    friend.setFanCount(userObject.getInt("fanCount"));
                    friend.setLikeCount(userObject.getInt("likeCount"));
                    friend.setFollowCount(userObject.getInt("followCount"));
                    friend.setStates(userObject.getBoolean("status"));
                    userList.add(friend);
                }
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
            focusAdapter = new FocusAdapter(R.layout.focus_item, FollowActivity.this, userList);
            listView.setAdapter(focusAdapter);
        }
    }

    //设置布局控件
    public void findViews(){
        user = (UserBean) getIntent().getSerializableExtra("user");
        listView = findViewById(R.id.root);
        listView.setEmptyView((findViewById(R.id.empty_view)));
        imageView = findViewById(R.id.back);
    }

    //listView的点击事件
    public void setListView(ListView listView){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                LinearLayout focusLayout = view.findViewById(R.id.focus);
                focusLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showFocusDialog(position);
                    }
                });
                Intent intent = new Intent();
                intent.setClass(FollowActivity.this, FriendActivity.class);
                intent.putExtra("friend", userList.get(position));
                startActivity(intent);
            }
        });
    }
    //是否关注选择器
    private void showFocusDialog(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认不再关注？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //把取消关注的用户从数据库中删除
                DeleteFollow deleteFollow = new DeleteFollow();
                deleteFollow.execute(user.getUserId(),userList.get(position).getUserId());
                userList.remove(position);
                dialog.dismiss();
                listView.setAdapter(focusAdapter);
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

    //把取消关注的用户从数据库中删除
    public class DeleteFollow extends  AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            String followId =(String) objects[0];
            String userId = (String)objects[1];
            try {
                URL url = new URL("http://10.7.89.233:8080/sharemate/DeleteFollowServlet?followId="+followId+"&userId="+userId);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                int res = urlConnection.getResponseCode();
                if(res == 200){
                    Log.e("test","删除成功！");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
