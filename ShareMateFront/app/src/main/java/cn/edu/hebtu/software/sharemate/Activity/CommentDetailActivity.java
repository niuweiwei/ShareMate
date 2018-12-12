package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.CommentAdapter;
import cn.edu.hebtu.software.sharemate.Bean.Comment;
import cn.edu.hebtu.software.sharemate.R;

public class CommentDetailActivity extends AppCompatActivity {
    private ListView listView;
    private LinearLayout commentLayout;
    private EditText etComment;
    private TextView send;
    private List<Comment> comments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decomment);
        listView=findViewById(R.id.lv_comment);
        etComment=findViewById(R.id.et_comment);
        send=findViewById(R.id.tv_send);
        getComment();
        skipPreviousPage();

    }

    private void getComment(){
        //准备数据
        SimpleDateFormat sdf=new SimpleDateFormat("MM-dd hh:mm");
        Date date=new Date();
        comments=new ArrayList<>();
        Comment comment1=new Comment(1,1,R.drawable.b13, "新地图开发","水煮要吃谭记水煮，在渊明北路上（靠近中山路）推荐鸡脚腰子等荤菜，一定要老板多刷特制辣酱~然后拌粉推荐高师傅，和谭记在一个区域~然后苍蝇馆推荐两家：一家叫老三样，一家叫佳佳麻辣烧菜馆",sdf.format(date),3);
        Comment comment2=new Comment(2,1,R.drawable.a7, "不存在的","好感动终于有人推荐我们南昌的小吃了",sdf.format(date),3);
        Comment comment3=new Comment(3,1,R.drawable.b16, "nebule","南昌的凉粉也挺好吃的",sdf.format(date),2);
        comments.add(comment1);
        comments.add(comment2);
        comments.add(comment3);
        //创建Adapter对象
        final CommentAdapter commentAdapter=new CommentAdapter(comments,R.layout.item_comment,CommentDetailActivity.this);
        //设置Adapter
        listView.setAdapter(commentAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String content=comments.get(position).getName()+":"+comments.get(position).getContent();
                showPopupWindow(content,position);
            }
        });
        //发送评论或对评论进行回复
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hint=etComment.getHint().toString();
                String content=etComment.getText().toString();
                if(hint.equals("矜持点赞也可以，知音难觅聊一句")){
                    //发表对笔记的评论,向数据库中添加该评论
                    Log.e("添加评论",content);
                }else {
                    //对该评论进行回复
                    Log.e("添加评论的回复",content);
                }
                etComment.setText("");
                etComment.setHint("矜持点赞也可以，知音难觅聊一句");
                //隐藏软键盘
                InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                //Toast
                Toast toast=Toast.makeText(CommentDetailActivity.this,"评论成功",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        });
    }
    private void showPopupWindow(String content,final int position){
        LinearLayout linearLayout=findViewById(R.id.rl_root);
        //创建PopupWindow对象
        final PopupWindow popupWindow = new PopupWindow(linearLayout,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        View view = getLayoutInflater().inflate(R.layout.popupwindow_layout,null);
        TextView textView=view.findViewById(R.id.tv_content);
        textView.setText(content);
        Button button2=view.findViewById(R.id.btn2);
        Button button3=view.findViewById(R.id.btn3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //根据position得到点击的那个评论·，回复指定评论
                //自动弹出软键盘
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                etComment.setHint("回复"+comments.get(position).getName()+"…");
                popupWindow.dismiss();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏popupWindow
                etComment.setHint("矜持点赞也可以，知音难觅聊一句");
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(view);
        //显示PopupWindow
        popupWindow.showAtLocation(linearLayout, Gravity.NO_GRAVITY,0,0);
    }
    //返回上一页
    private void skipPreviousPage(){
        ImageView imageView=findViewById(R.id.iv_rightarrow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentDetailActivity.this.finish();
            }
        });
    }

}
