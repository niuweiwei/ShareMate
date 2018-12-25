package cn.edu.hebtu.software.sharemate.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.CommentNodeAdapter;
import cn.edu.hebtu.software.sharemate.Bean.Comment;
import cn.edu.hebtu.software.sharemate.tools.GradationScrollView;
import cn.edu.hebtu.software.sharemate.R;

public class NoteDetailActivity extends AppCompatActivity implements GradationScrollView.ScrollViewListener{
    private RelativeLayout RLtitle;
    private GradationScrollView scrollView;
    private TextView noteTitle;
    private int height;

    private ImageView attentionImage;
    private ImageView zanImage;
    private ImageView collectionImage;

    private ImageView commentImage;
    private boolean isAttention=false;
    private boolean isZan=false;
    private boolean isCollection=false;
    private TextView countZan;
    private TextView countComment;
    private TextView allComment;
    private LinearLayout llComment;
    private LinearLayout bottom;
    private TextView send;
    private EditText etComment;
    private LinearLayout lleditComment;

    private ImageView banner;
    private ImageView userPhoto;
    private TextView userName;
    private TextView noteDetail;
    private TextView noteTime;
    private TextView collectionCount;
    private TextView zanCount;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        getViewById();
        //setNote();
        setComment();
        clickAttention();
        clickZanAndCollection();
        clickComment();
        toAllComment();
        initListeners();

        //点击返回按钮
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getViewById(){
        //标题
        RLtitle=findViewById(R.id.rl_title);
        noteTitle=findViewById(R.id.tv_noteTitle);
        scrollView=findViewById(R.id.scrollView);
        //底部
        attentionImage=findViewById(R.id.iv_attention);
        zanImage=findViewById(R.id.iv_zan);
        collectionImage=findViewById(R.id.iv_collection);

        etComment=findViewById(R.id.et_comment);
        send=findViewById(R.id.tv_send);
        countComment=findViewById(R.id.tv_countComment);
        llComment=findViewById(R.id.ll_comment);
        bottom=findViewById(R.id.ll_bottom);
        lleditComment=findViewById(R.id.ll_editcomment);
        allComment=findViewById(R.id.tv_allComment);
        countZan=findViewById(R.id.tv_countZan);

        //主页
        banner=findViewById(R.id.iv_banner);
        userPhoto=findViewById(R.id.iv_image);
        userName=findViewById(R.id.tv_name);
        noteDetail=findViewById(R.id.tv_detail);
        noteTitle=findViewById(R.id.tv_noteTitle);
        noteTime=findViewById(R.id.tv_time);
        collectionCount=findViewById(R.id.tv_allcountCollection);
        zanCount=findViewById(R.id.tv_allcountZan);

        //返回按钮
        back = findViewById(R.id.btn_back);
    }

    //设置笔记页面
    private void setNote(){
        NoteTask noteTask=new NoteTask();
        noteTask.execute();
    }
    public class NoteTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                URL url=new URL("http://10.7.89.231:8080/sharemate/NoteServlet?noteId=1");
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType","utf-8");
                InputStream is=connection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(is);
                BufferedReader reader=new BufferedReader(inputStreamReader);
                String res=reader.readLine();
                JSONObject object=new JSONObject(res);
                Log.e("res",res);
                noteTitle.setText(object.getString("noteTitle"));
                Log.e("noteTitle",object.getString("noteTitle"));
                //noteImage
                noteDetail.setText(object.getString("noteDetail"));
                noteTime.setText(object.getString("noteDate"));
                //userPhoto
                userName.setText(object.getString("userName"));
                zanCount.setText(object.getInt("likeCount")+"");
                collectionCount.setText(object.getInt("collectCount")+"");
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

    //--------------------------------------------------node页底部评论----------------------------------------------
    private void setComment(){
        //准备数据
        SimpleDateFormat sdf=new SimpleDateFormat("MM-dd hh:mm");
        Date date=new Date();
        List<Comment> comments=new ArrayList<>();
        Comment comment1=new Comment(1,1,R.drawable.b13,
                "新地图开发","水煮要吃谭记水煮，在渊明北路上（靠近中山路）推荐鸡脚腰子等荤菜，一定要老板多刷特制辣酱~然后拌粉推荐高师傅，和谭记在一个区域~然后苍蝇馆推荐两家：一家叫老三样，一家叫佳佳麻辣烧菜馆"
        ,sdf.format(date),3);
        Comment comment2=new Comment(2,1,R.drawable.a7, "不存在的","好感动终于有人推荐我们南昌的小吃了",sdf.format(date),3);
        Comment comment3=new Comment(3,1,R.drawable.b16, "nebule","南昌的凉粉也挺好吃的",sdf.format(date),2);
        comments.add(comment1);
        comments.add(comment2);
        comments.add(comment3);
        //创建Adapter对象
        CommentNodeAdapter commentNodeAdapter=new CommentNodeAdapter(comments,R.layout.item_comment_node,NoteDetailActivity.this);
        //设置Adapter
        ListView listView=findViewById(R.id.lv_comment);
        showAllListView(commentNodeAdapter,listView);
        listView.setAdapter(commentNodeAdapter);
    }
    //----------------------------------解决ScrollView嵌套ListView，item显示不全问题---------------------------------------
    private void showAllListView(CommentNodeAdapter commentAdapter,ListView listView) {
        if (commentAdapter != null) {
            int totalHeight = 0;
            for (int i = 0; i <commentAdapter.getCount(); i++) {
                View listItem =commentAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight+ (listView.getDividerHeight() * (commentAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }

    //--------------------------------------------------------点击关注--------------------------------------------------------------------
    private void clickAttention(){
        attentionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAttention){
                    //点击取消关注
                    showDialog(0);
                    isAttention=false;
                }else{
                    //点击关注
                    attentionImage.setImageResource(R.drawable.a9);
                    isAttention=true;
                }

            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        new AlertDialog.Builder(this)
                .setMessage("确定不再关注这位用户了吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //取消关注该用户
                        attentionImage.setImageResource(R.drawable.a8);
                    }
                })
                .setNegativeButton("取消",null)
                .create()
                .show();
        return super.onCreateDialog(id);
    }

    //--------------------------------------------------------------点击赞、收藏-----------------------------------------------------
    private void clickZanAndCollection(){
        zanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isZan){
                    zanImage.setImageResource(R.drawable.a3);
                    int count=Integer.parseInt(countZan.getText().toString());
                    count++;
                    countZan.setText(count+"");
                    zanCount.setText(count+"");
                    isZan=true;
                }else {
                    zanImage.setImageResource(R.drawable.a2);
                    TextView countZan=findViewById(R.id.tv_countZan);
                    int count=Integer.parseInt(countZan.getText().toString());
                    count--;
                    countZan.setText(count+"");
                    zanCount.setText(count+"");
                    isZan=false;
                }
            }
        });

        collectionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCollection){
                    collectionImage.setImageResource(R.drawable.a6);
                    TextView countCollection=findViewById(R.id.tv_countCollection);
                    int count=Integer.parseInt(countCollection.getText().toString());
                    count++;
                    countCollection.setText(count+"");
                    collectionCount.setText(count+"");
                    isCollection=true;
                }else {
                    collectionImage.setImageResource(R.drawable.a5);
                    TextView countCollection=findViewById(R.id.tv_countCollection);
                    int count=Integer.parseInt(countCollection.getText().toString());
                    count--;
                    countCollection.setText(count+"");
                    collectionCount.setText(count+"");
                    isCollection=false;
                }
            }
        });
    }

    //---------------------------------------------------------------点击评论----------------------------------------------------------
    private void clickComment(){
        llComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //自动出现软键盘
                InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                etComment.requestFocus();
                //隐藏底部,显示EditView
                bottom.setVisibility(View.INVISIBLE);
                lleditComment.setVisibility(View.VISIBLE);
            }
        });
        //点击发送
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发布评论,添加到数据库
                String commentContent=etComment.getText().toString();
                //隐藏软键盘
                InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                //显示底部,隐藏EditView
                bottom.setVisibility(View.VISIBLE);
                lleditComment.setVisibility(View.INVISIBLE);
                //Toast
                Toast toast=Toast.makeText(NoteDetailActivity.this,"评论成功",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                //评论数加1
                int count=Integer.parseInt(countComment.getText().toString());
                count++;
                countComment.setText(count+"");
            }
        });
    }

    //标题特效
    //----------------------------------获取顶部图片高度后，设置滚动监听--------------------------------------------------------
    private void initListeners(){
        ViewTreeObserver vto=banner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                RLtitle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                height=banner.getHeight();
                scrollView.setScrollViewListener(NoteDetailActivity.this);
            }
        });
    }
    //------------------------------------------------------滑动监听--------------------------------------------------------------------
    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {   //设置标题的背景颜色
            RLtitle.setBackgroundColor(Color.argb((int) 0, 0,0,0));
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            noteTitle.setTextColor(Color.argb((int) alpha, 255,255,255));
            noteTitle.setTextSize(18);
            RLtitle.setBackgroundColor(Color.argb((int) alpha, 222,65,56));
        } else {    //滑动到banner下面设置普通颜色
            RLtitle.setBackgroundColor(Color.argb((int) 255, 222,65,56));
        }
    }

    //-----------------------------------------跳转到全部评论页面-------------------------------------------------------------------
    private void toAllComment(){
        allComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NoteDetailActivity.this,CommentDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
