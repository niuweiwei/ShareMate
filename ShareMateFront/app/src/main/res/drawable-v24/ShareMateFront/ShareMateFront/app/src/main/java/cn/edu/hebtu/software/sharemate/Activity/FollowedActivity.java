package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.FollowedListAdapter;
import cn.edu.hebtu.software.sharemate.Bean.FollowBean;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class FollowedActivity extends AppCompatActivity {

    private String path = null;
    List<FollowBean> follows = new ArrayList<>();
    ListView listView = null;
    FollowedListAdapter adapter = null;
    private int currentUserId ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followed);
        listView = findViewById(R.id.lv_followed);
        path = getResources().getString(R.string.server_path);
        currentUserId = 3;

        FollowTask followTask = new FollowTask();
        followTask.execute();

        //为返回按钮绑定监听器
        Button back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private class FollowTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                //获取连接
                URL url = new URL(path+"FollowServlet?userId="+currentUserId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("contentType","utf-8");

                //得到服务器端数据输入流
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String res = reader.readLine();

                JSONArray array = new JSONArray(res);
                Log.e("数据源数组的长度",array.length()+"");
                for(int i=0;i<array.length();i++){
                    JSONObject object = array.getJSONObject(i);
                    FollowBean follow = new FollowBean();
                    UserBean fan = new UserBean();
                    fan.setUserId(object.getInt("fansId"));
                    fan.setUserName(object.getString("fansName"));
                    fan.setUserPhotoPath(path+object.getString("fansPhotoPath"));
                    fan.setUserIntroduce(object.getString("fansIntroduce"));
                    fan.setFollowCount(object.getInt("followCount"));
                    fan.setFanCount(object.getInt("fanCount"));
                    fan.setLikeCount(object.getInt("likeCount"));

                    follow.setCurrentUser(currentUserId);
                    follow.setUser(fan);
                    follow.setDate(object.getString("followDate"));
                    follow.setFollow(object.getBoolean("isFollow"));

                    follows.add(follow);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return follows;
        }

        @Override
        protected void onPostExecute(Object o) {

            Collections.sort(follows,new DateCompare());
            adapter = new FollowedListAdapter(FollowedActivity.this,R.layout.followed_list_item_layout,follows);
            //点击listView的各个子项  跳转到指定用户的个人首页
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(FollowedActivity.this,FriendActivity.class);
                    intent.putExtra("friend",follows.get(position).getUser());
                    startActivity(intent);
                }
            });
        }
    }

    private class DateCompare implements Comparator<FollowBean> {

        @Override
        public int compare(FollowBean o1, FollowBean o2) {
            String date1 = o1.getDate();
            String date2 = o2.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            Date time1 = null;
            Date time2 = null;
            try {
                time1 = sdf.parse(date1);
                time2 = sdf.parse(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(time1.after(time2))
                return -1;
            else if(time1.before(time2))
                return 1;
            else
                return 0;
        }
    }
}
