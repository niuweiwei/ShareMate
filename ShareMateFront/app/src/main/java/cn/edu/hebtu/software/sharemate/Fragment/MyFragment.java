package cn.edu.hebtu.software.sharemate.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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

import cn.edu.hebtu.software.sharemate.Activity.FanActivity;
import cn.edu.hebtu.software.sharemate.Activity.FollowActivity;
import cn.edu.hebtu.software.sharemate.Activity.FriendActivity;
import cn.edu.hebtu.software.sharemate.Activity.NoteDetailActivity;
import cn.edu.hebtu.software.sharemate.Activity.PersonalActivity;
import cn.edu.hebtu.software.sharemate.Activity.SettingActivity;
import cn.edu.hebtu.software.sharemate.Adapter.NoteAdapter;
import cn.edu.hebtu.software.sharemate.Bean.NoteBean;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class MyFragment extends Fragment {
    private GridView gridView;
    private NoteAdapter noteAdapter;
    private List<NoteBean> collectionList = new ArrayList<>();
    private List<NoteBean> noteList = new ArrayList<>();
    private UserBean user = new UserBean() ;
    private TextView nameText;
    private TextView idText;
    private TextView introText;
    private TextView collection;
    private TextView note;
    private TextView followCount;
    private TextView fanCount;
    private TextView likeCount;
    private ImageView headImg;
    private ImageView settingView;
    private Button button;
    private String path = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        path = getResources().getString(R.string.server_path);
        GetUserDetail getUser = new GetUserDetail();
        getUser.execute(this);
        GetNote getNote = new GetNote();
        getNote.execute(user);
        GetCollection getCollection = new GetCollection();
        getCollection.execute(user);
        View view = inflater.inflate(R.layout.fragment_my,container,false);
        findView(view);
        setListener();
        return view;
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
                    noteBean.setNoteImagePath(path+noteObject.getString("notePhoto"));
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
            noteAdapter = new NoteAdapter(getActivity(), R.layout.note_item,noteList,user);
            gridView.setAdapter(noteAdapter);
            setNoteGridView(gridView);
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
                    noteBean.setNoteImagePath(path+noteObject.getString("notePhoto"));
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
    //从数据库中获得UserBean对象
    public class GetUserDetail extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                URL url = new URL(path+"UserServlet?userId=2");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("contentType", "UTF-8");
                InputStream is = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String res = br.readLine();
                //解析JSON
                JSONObject jsonObject = new JSONObject(res);
                user.setUserId(jsonObject.getInt("userId"));
                user.setUserPhotoPath(path+jsonObject.getString("userPhoto"));
                user.setUserName(jsonObject.getString("userName"));
                user.setUserSex(jsonObject.getString("userSex"));
                user.setUserBirth(jsonObject.getString("userBirth"));
                user.setUserAddress(jsonObject.getString("userAddress"));
                user.setUserIntroduce(jsonObject.getString("userIntro"));
                user.setFollowCount(jsonObject.getInt("followCount"));
                user.setFanCount(jsonObject.getInt("fanCount"));
                user.setLikeCount(jsonObject.getInt("likeCount"));
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
            nameText.setText(user.getUserName());
            String userId = String.format("%06d",user.getUserId());
            idText.setText("ShareMate号:" + userId);
            if (user.getUserIntroduce() == null || user.getUserIntroduce().length() < 20) {
                introText.setText(user.getUserIntroduce());
            } else {
                introText.setText(user.getUserIntroduce().substring(0, 20) + ".....");
            }
            String photoPath = user.getUserPhotoPath();
            RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true);
            Glide.with(getActivity()).load(photoPath).apply(mRequestOptions).into(headImg);
            followCount.setText(""+user.getFollowCount());
            fanCount.setText(""+user.getFanCount());
            likeCount.setText(""+user.getLikeCount());
        }
    }
    public void findView(View view){
        nameText = view.findViewById(R.id.userName);
        idText = view.findViewById(R.id.userId);
        introText = view.findViewById(R.id.userIntro);
        headImg = view.findViewById(R.id.userPhoto);
        gridView = view.findViewById(R.id.root);
        gridView.setEmptyView((view.findViewById(R.id.empty_view)));
        collection = view.findViewById(R.id.collection);
        note = view.findViewById(R.id.note);
        settingView = view.findViewById(R.id.setting);
        button = view.findViewById(R.id.personal);
        followCount = view.findViewById(R.id.followCount);
        fanCount = view.findViewById(R.id.fanCount);
        likeCount = view.findViewById(R.id.likeCount);
    }

    public void setListener(){
        SetOnclickListener listener = new SetOnclickListener();
        collection.setOnClickListener(listener);
        note.setOnClickListener(listener);
        followCount.setOnClickListener(listener);
        fanCount.setOnClickListener(listener);
        settingView.setOnClickListener(listener);
        button.setOnClickListener(listener);
    }
    public void setNoteGridView(GridView gridView){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("111","111");
                Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
                intent.putExtra("noteId",noteList.get(position).getNoId());
                Log.e("noteId",noteList.get(position).getNoId()+"");
                intent.putExtra("userId",user.getUserId());
                startActivity(intent);
            }
        });
    }
    public void setCollectGridView(GridView gridView){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("111","111");
                Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
                intent.putExtra("noteId",collectionList.get(position).getNoId());
                intent.putExtra("userId",user.getUserId());
                startActivity(intent);
            }
        });
    }
    public class SetOnclickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.note:
                    note.setTextColor(getResources().getColor(R.color.warmRed));
                    collection.setTextColor(getResources().getColor(R.color.darkGray));
                    noteAdapter = new NoteAdapter(getActivity(), R.layout.note_item,noteList,user);
                    gridView.setAdapter(noteAdapter);
                    setNoteGridView(gridView);
                    break;
                case R.id.collection:
                    collection.setTextColor(getResources().getColor(R.color.warmRed));
                    note.setTextColor(getResources().getColor(R.color.darkGray));
                    noteAdapter = new NoteAdapter(getActivity(), R.layout.note_item,collectionList,user);
                    gridView.setAdapter(noteAdapter);
                    setCollectGridView(gridView);
                    break;
                case R.id.followCount:
                    Intent focusIntent = new Intent();
                    focusIntent.setClass(getActivity(), FollowActivity.class);
                    focusIntent.putExtra("user",user);
                    startActivity(focusIntent);
                    break;
                case R.id.fanCount:
                    Intent fanIntent = new Intent();
                    fanIntent.setClass(getActivity(), FanActivity.class);
                    fanIntent.putExtra("user",user);
                    startActivity(fanIntent);
                    break;
                case R.id.personal:
                    Intent perIntent = new Intent();
                    perIntent.setClass(getActivity(), PersonalActivity.class);
                    perIntent.putExtra("user",user);
                    perIntent.putExtra("sign","my");
                    startActivityForResult(perIntent,1);
                    break;
                case R.id.setting:
                    Intent setIntent = new Intent();
                    setIntent.setClass(getActivity(), SettingActivity.class);
                    setIntent.putExtra("user",user);
                    startActivityForResult(setIntent,2);
                    break;
            }
        }
    }
}
