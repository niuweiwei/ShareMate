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
    private String path = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan);
        findViews();
        path = getResources().getString(R.string.server_path);
        GetFan getFan = new GetFan();
        getFan.execute(user);
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
            int userId = userBean.getUserId();
            try {
                URL url = new URL(path+"FanServlet?userId="+userId);
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
                    fan.setUserId(userObject.getInt("userId"));
                    fan.setUserName(userObject.getString("userName"));
                    fan.setUserPhotoPath(path+userObject.getString("userPhoto"));
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
            fanAdapter = new FanAdapter(FanActivity.this,R.layout.fan_item,userList,user,path);
            listView.setAdapter(fanAdapter);
        }
    }
    public void findViews(){
        user = (UserBean) getIntent().getSerializableExtra("user");
        listView = findViewById(R.id.root);
        listView.setEmptyView((findViewById(R.id.empty_view)));
        imageView = findViewById(R.id.back);
    }
}
