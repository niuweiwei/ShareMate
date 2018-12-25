package cn.edu.hebtu.software.sharemate.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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

import cn.edu.hebtu.software.sharemate.Activity.NoteDetailActivity;
import cn.edu.hebtu.software.sharemate.Activity.PersonalActivity;
import cn.edu.hebtu.software.sharemate.Adapter.GridViewAdapter;
import cn.edu.hebtu.software.sharemate.Bean.NoteBean;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class BeautFragment extends Fragment implements GridViewAdapter.Callback,AdapterView.OnClickListener{
    private GridViewAdapter gridViewAdapter=null;
    private List<NoteBean> notes=new ArrayList<NoteBean>();
    private GridView gridView;
    private ListTask listTask;
    private ZanTask zanTask;
    private UserBean user;
    private int userId;
    private String U;
    private Map<Integer,Boolean> isLike=new HashMap<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.note_fragment,container,false);
        U=getResources().getString(R.string.server_path);
        userId = getActivity().getIntent().getIntExtra("userId",0);
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

    @Override
    public void click(View v) {
        switch (v.getId()) {
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
                    gridViewAdapter.notifyDataSetChanged();
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
                    gridViewAdapter.notifyDataSetChanged();
                }
                break;

        }
    }

    //点赞触发的子进程，传userID和noteID给服务器
    public class ZanTask extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                int noteid = (int) objects[0];
                boolean islike = (boolean) objects[1];
                Log.e("noteid", noteid+"");Log.e("like", islike+"");
                URL url = new URL(U+"BeautNoteServlet?userId="+userId+"&noteId="+noteid+"&islike="+islike);
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
        if ( zanTask != null && zanTask.getStatus() == ZanTask.Status.RUNNING ){
            zanTask.cancel(true);
            zanTask = null;
        }
    }

    public class ListTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            notes=new ArrayList<>();
            try {
                URL url = new URL(U+"BeautNoteServlet?userId="+userId);
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
//                        user.setUserId(object2.getInt("userId"));
//                        user.setUserSex(object2.getString("userSex"));
//                        user.setUserBirth(object2.getString("userBirth"));
//                        user.setUserIntroduce(object2.getString("userIntro"));
//                        user.setUserAddress(object2.getString("userAddress"));
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
                        note1.setIslike(object.getInt("like"));
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
            //创建Adapter对象
            gridViewAdapter = new GridViewAdapter(getActivity(),R.layout.grid_item,BeautFragment.this, notes);
            //设置Adapter
            gridView.setAdapter(gridViewAdapter);
            gridView.setHorizontalSpacing(5);
            gridView.setVerticalSpacing(5);
            gridViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        listTask = new ListTask();
        listTask.execute();
    }
}
