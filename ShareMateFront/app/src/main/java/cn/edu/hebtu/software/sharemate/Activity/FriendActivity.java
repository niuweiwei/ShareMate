package cn.edu.hebtu.software.sharemate.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

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

import cn.edu.hebtu.software.sharemate.Adapter.NoteAdapter;
import cn.edu.hebtu.software.sharemate.Bean.NoteBean;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class FriendActivity extends AppCompatActivity {

    private UserBean user = new UserBean() ;
    private TextView nameView;
    private TextView introView;
    private TextView idView;
    private ImageView photoView;
    private ImageView backView;
    private TextView collection;
    private TextView note;
    private TextView followCount;
    private TextView fanCount;
    private TextView likeCount;
    private GridView gridView;
    private NoteAdapter noteAdapter;
    private List<NoteBean> collectionList = new ArrayList<>();
    private List<NoteBean> noteList = new ArrayList<>();
    private String path = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        path = getResources().getString(R.string.server_path);
        user = (UserBean) getIntent().getSerializableExtra("friend");
        findView();
        setListener();
        setContent();
        GetNote getNote = new GetNote();
        getNote.execute(user);
        GetCollection getCollection = new GetCollection();
        getCollection.execute(user);
    }
    public void findView(){
        nameView = findViewById(R.id.userName);
        idView = findViewById(R.id.userId);
        photoView = findViewById(R.id.userPhoto);
        introView = findViewById(R.id.userIntro);
        backView = findViewById(R.id.back);
        collection = findViewById(R.id.collection);
        note = findViewById(R.id.note);
        gridView = findViewById(R.id.root);
        followCount = findViewById(R.id.followCount);
        fanCount = findViewById(R.id.fanCount);
        likeCount = findViewById(R.id.likeCount);
    }
    public void setContent(){
        gridView.setEmptyView((findViewById(R.id.empty_view)));
        String userId = String.format("%06d",user.getUserId());
        idView.setText("Share Mate号："+userId);
        nameView.setText(user.getUserName());
        followCount.setText(""+user.getFollowCount());
        fanCount.setText(""+user.getFanCount());
        likeCount.setText(""+user.getLikeCount());
        if (user.getUserIntroduce() == null || user.getUserIntroduce().length() < 20) {
            introView.setText(user.getUserIntroduce());
        } else {
            introView.setText(user.getUserIntroduce().substring(0, 20) + ".....");
        }
        String photoPath = user.getUserPhotoPath();
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(this).load(photoPath).apply(mRequestOptions).into(photoView);

    }
    public void setListener(){
        SetOnClickListener listener = new SetOnClickListener();
        backView.setOnClickListener(listener);
        note.setOnClickListener(listener);
        collection.setOnClickListener(listener);
    }

    public class SetOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back:
                    FriendActivity.this.finish();
                    break;
                case R.id.note:
                    note.setTextColor(getResources().getColor(R.color.warmRed));
                    collection.setTextColor(getResources().getColor(R.color.darkGray));
                    noteAdapter = new NoteAdapter(FriendActivity.this, R.layout.note_item,noteList,user);
                    gridView.setAdapter(noteAdapter);
                    break;
                case R.id.collection:
                    collection.setTextColor(getResources().getColor(R.color.warmRed));
                    note.setTextColor(getResources().getColor(R.color.darkGray));
                    noteAdapter = new NoteAdapter(FriendActivity.this, R.layout.note_item,collectionList,user);
                    gridView.setAdapter(noteAdapter);
                    break;
            }
        }
    }
    //从数据库中取出来笔记
    public class GetNote extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            UserBean userBean = (UserBean) objects[0];
            int uId = userBean.getUserId();
            try {
                URL url = new URL(path+"NoteServlet?userId="+uId);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                InputStream is = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String res = br.readLine();
                //解析JSON
                JSONArray array = new JSONArray(res);
                for(int i=0; i<array.length(); i++){
                    JSONObject noteObject = array.getJSONObject(i);
                    NoteBean noteBean = new NoteBean();
                    noteBean.setNoId(noteObject.getInt("noteId"));
                    noteBean.setNoteTitle(noteObject.getString("noteTitle"));
                    noteBean.setNoteImagePath(noteObject.getString("notePhoto"));
                    noteBean.setUser(user);
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
            note.setTextColor(getResources().getColor(R.color.warmRed));
            collection.setTextColor(getResources().getColor(R.color.darkGray));
            noteAdapter = new NoteAdapter(FriendActivity.this, R.layout.note_item,noteList,user);
            gridView.setAdapter(noteAdapter);
        }
    }

    //从数据库中取出收藏
    public class GetCollection extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            UserBean userBean = (UserBean) objects[0];
            int uId = userBean.getUserId();
            try {
                URL url = new URL(path+"CollectionServlet?userId="+uId);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                InputStream is = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String res = br.readLine();
                //解析JSON
                JSONArray array = new JSONArray(res);
                for(int i=0; i<array.length(); i++){
                    JSONObject noteObject = array.getJSONObject(i);
                    NoteBean noteBean = new NoteBean();
                    UserBean user = new UserBean();
                    noteBean.setNoId(noteObject.getInt("noteId"));
                    noteBean.setNoteTitle(noteObject.getString("noteTitle"));
                    noteBean.setNoteImagePath(noteObject.getString("notePhoto"));
                    user.setUserName(noteObject.getString("userName"));
                    user.setUserPhotoPath(path+noteObject.getString("userPhoto"));
                    noteBean.setUser(user);
                    collectionList.add(noteBean);
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
    }
}
