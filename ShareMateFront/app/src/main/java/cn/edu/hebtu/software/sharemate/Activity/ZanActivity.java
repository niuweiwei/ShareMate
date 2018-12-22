package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

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

import cn.edu.hebtu.software.sharemate.Adapter.ZanAdapter;
import cn.edu.hebtu.software.sharemate.Bean.NoteBean;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class ZanActivity extends AppCompatActivity {

    private ZanAdapter zanAdapter;
    private GridView gridView;
    private List<NoteBean> noteList = new ArrayList<>();
    private UserBean userBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zan);
        userBean =(UserBean) getIntent().getSerializableExtra("user");
        ZanNote zanNote = new ZanNote();
        zanNote.execute(userBean);
        gridView =findViewById(R.id.root);
        ImageView imageView = findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ZanActivity.this, SettingActivity.class);
                intent.putExtra("user",getIntent().getSerializableExtra("user"));
                startActivity(intent);
            }
        });
    }

    public class ZanNote extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            UserBean user = (UserBean)objects[0];
            int uId =Integer.parseInt(user.getUserId()) ;
            try {
                URL url = new URL("http://10.7.89.233:8080/sharemate/ZanServlet?userId="+uId);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream is = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
               String res = br.readLine();
               //解析JSON
                JSONArray array = new JSONArray(res);
                for(int i =0; i<array.length();i++){
                    JSONObject noteObject = array.getJSONObject(i);
                    NoteBean noteBean = new NoteBean();
                    noteBean.setNoteImage(noteObject.getString("notePhoto"));
                    noteBean.setNoteTitle(noteObject.getString("noteTitle"));
                    noteList.add(noteBean);
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
            if(noteList.size()==0){
                gridView.setEmptyView((findViewById(R.id.empty_view)));
            }else{
                zanAdapter= new ZanAdapter(ZanActivity.this, R.layout.zan_item,noteList);
                gridView.setAdapter(zanAdapter);
            }
        }
    }
}
