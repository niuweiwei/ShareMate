package cn.edu.hebtu.software.sharemate.Fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


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

import cn.edu.hebtu.software.sharemate.Activity.NoteDetailActivity;
import cn.edu.hebtu.software.sharemate.Activity.PersonalActivity;
import cn.edu.hebtu.software.sharemate.Adapter.GridViewAdapter;
import cn.edu.hebtu.software.sharemate.Bean.NoteBean;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class FujinFragment extends Fragment implements GridViewAdapter.Callback,AdapterView.OnClickListener{
    private GridViewAdapter gridViewAdapter=null;
    private List<NoteBean> notes=new ArrayList<NoteBean>();
    private GridView gridView;
    private TextView textView;
    private ImageView imageView;
    private ListTask listTask;
    private int userId;
    private String content=null;
    private int image = R.drawable.meng;
    private Handler handler=null;

    private String U;
    private Map<Integer,Boolean> isLike=new HashMap<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.note_fragment,container,false);
        U=getResources().getString(R.string.server_path);
        userId = getActivity().getIntent().getIntExtra("userId",0);
        textView = view.findViewById(R.id.text);
        imageView = view.findViewById(R.id.img);
        handler = new Handler();
        gridView = view.findViewById(R.id.root);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("点击了", ""+ position);
                int noteid = notes.get(position).getNoId();
                Intent intent = new Intent();
                intent.setClass(getActivity(),NoteDetailActivity.class);
                intent.putExtra("noteId",noteid);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
    }


    @Override      //item内部组件的点击事件
    public void click(View v) {
        switch (v.getId()) {
        }
    }
    //从服务器端获取用户附近的人的笔记，需要传入用户的id到服务器端
    public class ListTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            notes=new ArrayList<>();
            try {
                URL url = new URL(U+"FujinNoteServlet?userId="+userId);
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
                    Log.e("array",array.length()+"");
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
                        note1.setZancount1(object2.getString("userAddress"));
                        note1.setZan(R.drawable.weizhi);
                        note1.setCollectcount(object.getInt("noteCollectionCount"));
                        note1.setPingluncount(object.getInt("noteCommentCount"));
                        notes.add(note1);
                    }
                    if(notes.size()==0){
                        handler.post(runnableUi);
                    }

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
            Log.e("111", notes.size()+"");
            //创建Adapter对象
            gridViewAdapter = new GridViewAdapter(getActivity(),R.layout.grid_item,FujinFragment.this, notes);
            //设置Adapter
            gridView.setAdapter(gridViewAdapter);
            gridView.setHorizontalSpacing(5);
            gridView.setVerticalSpacing(5);
            gridViewAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if ( listTask != null && listTask.getStatus() == ListTask.Status.RUNNING ){
            listTask.cancel(true);
            listTask = null;
        }
    }
    Runnable   runnableUi=new  Runnable(){
        @Override
        public void run() {
            //更新界面
            textView.setText("啊哦~ \n还没有附近的笔记哦");
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
