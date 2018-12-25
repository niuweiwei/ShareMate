package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.LikeListAdapter;
import cn.edu.hebtu.software.sharemate.Bean.LikeBean;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.ImageTask;

public class LikeActivity extends AppCompatActivity {

    private List<LikeBean> likes = new ArrayList<>();
    private String path = null;
    private ListView listView = null;
    private LikeListAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        listView = findViewById(R.id.lv_like);
        path = getResources().getString(R.string.server_path);

        LikeNoteTask task = new LikeNoteTask();
        task.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LikeActivity.this,NoteDetailActivity.class);
                intent.putExtra("noteId",likes.get(position).getNoteId());
                startActivity(intent);
            }
        });

        //获取返回按钮
        Button back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private class LikeNoteTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            //与服务器端建立连接
            try {

                //假设当前的用户id为3
                URL url = new URL(path+"LikeNoteServlet?userId=3");
                HttpURLConnection connection = (HttpURLConnection)  url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType","utf-8");

                //接收连接传输过来的流
                InputStream is = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);
                BufferedReader buffer = new BufferedReader(reader);
                String res = buffer.readLine();

                JSONArray array = new JSONArray(res);
               for(int i=0;i<array.length();i++){

                    LikeBean like = new LikeBean();

                    //解析JSON 数据
                    JSONObject object = array.getJSONObject(i);
                    String userPath = object.getString("userPhoto");
                    String userName = object.getString("userName");
                    int noteId = object.getInt("noteId");
                    String notePath = object.getString("notePhoto");
                    String date = object.getString("likeDate");

                    //将解析得到的数据放入 LikeBean 对象中
                    UserBean user = new UserBean();
                    user.setUserId(object.getInt("userId"));
                    user.setUserName(userName);
                    user.setUserPhotoPath(path+userPath);
                    user.setFanCount(object.getInt("fanCount"));
                    user.setFollowCount(object.getInt("followCount"));
                    user.setLikeCount(object.getInt("likeCount"));
                    user.setUserIntroduce(object.getString("userIntroduce"));

                    like.setUser(user);
                    like.setNoteId(noteId);
                    like.setDate(date);
                    like.setNotePhotoPath(path+notePath);

                    likes.add(like);
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return likes;
        }

        @Override
        protected void onPostExecute(Object o) {
            adapter = new LikeListAdapter(LikeActivity.this,likes,R.layout.like_list_item_layout);
            //绑定Adapter
            listView.setAdapter(adapter);

        }
    }

}
