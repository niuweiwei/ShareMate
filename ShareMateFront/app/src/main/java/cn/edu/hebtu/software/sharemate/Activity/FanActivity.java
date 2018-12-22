package cn.edu.hebtu.software.sharemate.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

import cn.edu.hebtu.software.sharemate.Adapter.FanAdapter;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class FanActivity extends AppCompatActivity {
    private UserBean user;
    private ImageView imageView;
    private ListView listView;
    private FanAdapter fanAdapter;
    private List<UserBean> userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan);
        findViews();
        GetFan getFan = new GetFan();
        getFan.execute(user);
        setListView(listView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FanActivity.this,MainActivity.class);
                intent.putExtra("flag","my");
                startActivity(intent);
            }
        });
    }

    //从数据库中取出当前用户的粉丝
    public class GetFan extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            UserBean userBean = (UserBean) objects[0];
            int userId = Integer.parseInt(userBean.getUserId());
            try {
                URL url = new URL("http://10.7.89.233:8080/sharemate/FanServlet?userId="+userId);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("contentType", "UTF-8");
                InputStream is = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String res = br.readLine();
                //解析JSON
                JSONArray array = new JSONArray(res);
                for(int i=0 ; i<array.length() ; i++){
                    JSONObject userObject = array.getJSONObject(i);
                    UserBean fan = new UserBean();
                    fan.setUserId(userObject.getString("userId"));
                    fan.setUserName(userObject.getString("userName"));
                    fan.setUserPhotoPath(userObject.getString("userPhoto"));
                    fan.setUserIntroduce(userObject.getString("userIntro"));
                    fan.setStates(userObject.getBoolean("status"));
                    fan.setNoteCount(userObject.getInt("noteCount"));
                    fan.setFanCount(userObject.getInt("fanCount"));
                    fan.setFollowCount(userObject.getInt("followCount"));
                    fan.setLikeCount(userObject.getInt("likeCount"));
                    userList.add(fan);
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
            fanAdapter = new FanAdapter(FanActivity.this,R.layout.fan_item,userList);
            listView.setAdapter(fanAdapter);
        }
    }

    //listView的点击事件
    public void setListView(ListView listView){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final TextView followView = view.findViewById(R.id.follow);
                followView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if("回粉".equals(followView.getText())){
                            //把用户加入到我的关注表里
                            insertFollow inserts = new insertFollow();
                            inserts.execute(user.getUserId(),userList.get(position).getUserId());
                            followView.setText("互相关注");
                        }else if("互相关注".equals(followView.getText())){
                            showFocusDialog(position);
                        }
                    }
                });
                Intent intent = new Intent();
                intent.setClass(FanActivity.this, FriendActivity.class);
                intent.putExtra("friend", userList.get(position));
                startActivity(intent);
            }
        });
    }
    public void findViews(){
        user = (UserBean) getIntent().getSerializableExtra("user");
        listView = findViewById(R.id.root);
        listView.setEmptyView((findViewById(R.id.empty_view)));
        imageView = findViewById(R.id.back);
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
                listView.setAdapter(fanAdapter);
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
    //把粉丝加入我的关注表里
    public class insertFollow extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            String followId =(String) objects[0];
            String userId = (String)objects[1];
            try {
                URL url = new URL("http://10.7.89.233:8080/sharemate/InsertFollowServlet?followId="+followId+"&userId="+userId);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                int res = urlConnection.getResponseCode();
                if(res == 200){
                    Log.e("test","添加成功！");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
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
