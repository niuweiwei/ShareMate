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

public class TuijianFragment extends Fragment implements GridViewAdapter.Callback,AdapterView.OnClickListener{
    private GridViewAdapter gridViewAdapter=null;
    private List<NoteBean> notes=new ArrayList<NoteBean>();
    private GridView gridView;
    private ListTask listTask;
    private ListTask1 listTask1;
    private ListTask2 listTask2;
    private ListTask3 listTask3;
    private ZanTask zanTask;
    private int userId=2;
    private String U;
    private Map<Integer,Boolean> isLike=new HashMap<>();
    private ArrayList<Integer> type = new ArrayList<>();
    int typeid1,typeid2,typeid3,typeid4,typeid5,typeid6;
//    private int position;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.note_fragment,container,false);
        U=getResources().getString(R.string.server_path);
        //typeid需要从activity获取intent的内容，待修改
        type=getActivity().getIntent().getIntegerArrayListExtra("type");
        userId = getActivity().getIntent().getIntExtra("userId",0);
        Log.e("type  userId",type.toString()+" " + userId);
        gridView = view.findViewById(R.id.root);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                int noteid = notes.get(position).getNoId();
                intent.setClass(getActivity(),NoteDetailActivity.class);
                intent.putExtra("noteId",noteid);
                intent.putExtra("userId",userId);
                intent.putExtra("position",position);
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
                URL url = new URL(U+"TuijianNoteServlet?userId="+userId+"&noteId="+noteid+"&islike="+islike);
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

    //从服务器端获取用户选中的type的笔记，需要传入typeid
    public class ListTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            Log.e("tuijian task","成功打印");
            try {
                URL url = new URL(U+"TuijianNoteServlet?userId="+userId);
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
                    for (int i =0; i<array.length(); i++){
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
                        note1.setZancount1(String.valueOf(object.getInt("noteLikeCount")));
                        note1.setCollectcount(object.getInt("noteCollectionCount"));
                        note1.setPingluncount(object.getInt("noteCommentCount"));
                        //Log.e("islike",""+object.getInt("like"));
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
                    //content=persons.get(1).getUserPhoto();
                    //handler.post(runnable);
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
            gridViewAdapter = new GridViewAdapter(getActivity(),R.layout.grid_item,TuijianFragment.this, notes);
            //设置Adapter
            gridView.setAdapter(gridViewAdapter);
            gridViewAdapter.notifyDataSetChanged();
            gridView.setHorizontalSpacing(5);
            gridView.setVerticalSpacing(5);
            gridViewAdapter.notifyDataSetChanged();
        }
    }

    public class ListTask1 extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                URL url = new URL(U+"TuijianNoteServlet?userId="+userId+"&typeid1="+typeid1+"&typeid2="+typeid2+"&typeid3="+typeid3);
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
                    for (int i =0; i<array.length(); i++){
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
                        note1.setZancount1(String.valueOf(object.getInt("noteLikeCount")));
                        note1.setCollectcount(object.getInt("noteCollectionCount"));
                        note1.setPingluncount(object.getInt("noteCommentCount"));
                        //Log.e("islike",""+object.getInt("like"));
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
                    //content=persons.get(1).getUserPhoto();
                    //handler.post(runnable);
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
            gridViewAdapter = new GridViewAdapter(getActivity(),R.layout.grid_item,TuijianFragment.this, notes);
            //设置Adapter
            gridView.setAdapter(gridViewAdapter);
            gridViewAdapter.notifyDataSetChanged();
            gridView.setHorizontalSpacing(5);
            gridView.setVerticalSpacing(5);
            gridViewAdapter.notifyDataSetChanged();
        }
    }
    public class ListTask2 extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            notes=new ArrayList<>();
            try {
                URL url = new URL(U+"TuijianNoteServlet?userId="+userId+"&typeid1="+typeid1+"&typeid2="+typeid2+"&typeid3="+typeid3+"&typeid4="+typeid4);
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
                    for (int i =0; i<array.length(); i++){
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
                        note1.setZancount1(String.valueOf(object.getInt("noteLikeCount")));
                        note1.setCollectcount(object.getInt("noteCollectionCount"));
                        note1.setPingluncount(object.getInt("noteCommentCount"));
                        note1.setIslike(object.getInt("like"));
                        notes.add(note1);
                    }
                    //content=persons.get(1).getUserPhoto();
                    //handler.post(runnable);
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
            gridViewAdapter = new GridViewAdapter(getActivity(),R.layout.grid_item,TuijianFragment.this, notes);
            //设置Adapter
            gridView.setAdapter(gridViewAdapter);
            gridViewAdapter.notifyDataSetChanged();
            gridView.setHorizontalSpacing(5);
            gridView.setVerticalSpacing(5);
            gridViewAdapter.notifyDataSetChanged();
        }
    }
    public class ListTask3 extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                URL url = new URL(U+"TuijianNoteServlet?userId="+userId+"&typeid1="+typeid1+"&typeid2="+typeid2+"&typeid3="+typeid3+"&typeid4="+typeid4+"&typeid5="+typeid5);
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
                    for (int i =0; i<array.length(); i++){
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
                        note1.setZancount1(String.valueOf(object.getInt("noteLikeCount")));
                        note1.setCollectcount(object.getInt("noteCollectionCount"));
                        note1.setPingluncount(object.getInt("noteCommentCount"));
                        //Log.e("islike",""+object.getInt("like"));
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
                    //content=persons.get(1).getUserPhoto();
                    //handler.post(runnable);
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
            gridViewAdapter = new GridViewAdapter(getActivity(),R.layout.grid_item,TuijianFragment.this, notes);
            //设置Adapter
            gridView.setAdapter(gridViewAdapter);
            gridViewAdapter.notifyDataSetChanged();
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
        if ( zanTask != null && zanTask.getStatus() == ZanTask.Status.RUNNING ){
            zanTask.cancel(true);
            zanTask = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("tuijian userId",userId+"");
        Log.e("typesize",type.size()+"");
        if(type.size()==3) {
            typeid1 =type.get(0);
            typeid2 =type.get(1);
            typeid3 =type.get(2);
            listTask1 = new ListTask1();
            listTask1.execute();}
        if(type.size()==4){
            typeid1 =type.get(0);typeid2 =type.get(1);
            typeid3 =type.get(2);typeid4=type.get(3);
            listTask2 = new ListTask2();
            listTask2.execute();}
        if(type.size()==5){
            typeid1 =type.get(0);typeid2 =type.get(1);
            typeid3 =type.get(2);typeid4=type.get(3);typeid5=type.get(4);
            listTask3 = new ListTask3();
            listTask3.execute();}
        if(type.size()==0||type.size()==6){
            listTask = new ListTask();
            listTask.execute();
        }

    }
}


