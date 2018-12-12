package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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

import cn.edu.hebtu.software.sharemate.Adapter.ReplyAdapter;
import cn.edu.hebtu.software.sharemate.Bean.Reply;
import cn.edu.hebtu.software.sharemate.R;

public class ReplyActivity extends AppCompatActivity {
    private Boolean isZan=false;
    private TextView  send;
    private EditText etReply;
    private List<Reply> replies1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        send=findViewById(R.id.tv_send);
        etReply=findViewById(R.id.et_reply);
        clickZan();
        getReply();
        skipPreviousPage();

    }
    private void getReply(){
        //根据Id从数据库中找到评论，设置评论
        Intent request=getIntent();
        int commentId=request.getIntExtra("commentId",0);
        ImageView imageView=findViewById(R.id.iv_image);
        imageView.setImageResource(R.drawable.b13);
        TextView name=findViewById(R.id.tv_name);
        name.setText("新地图开发");
        final TextView content=findViewById(R.id.tv_CommentContent);
        content.setText("水煮要吃谭记水煮，在渊明北路上（靠近中山路）推荐鸡脚腰子等荤菜，一定要老板多刷特制辣酱~然后拌粉推荐高师傅，和谭记在一个区域~然后苍蝇馆推荐两家：一家叫老三样，一家叫佳佳麻辣烧菜馆");
        TextView time=findViewById(R.id.tv_time);
        time.setText("07-09 17:33");
        TextView count=findViewById(R.id.tv_zanCount);
        count.setText(3+"");
        //准备回复数据
        SimpleDateFormat sdf=new SimpleDateFormat("MM-dd hh:mm");
        Date date=new Date();
        replies1=new ArrayList<>();
        Reply reply1=new Reply(1,1,R.drawable.b13,"一个人去游泳像投水(作者)","好嘞！got!!!",sdf.format(date),2);
        Reply reply2=new Reply(2,1,R.drawable.b13,"TT","哈哈哈哈哈我也想要",sdf.format(date),1);
        Reply reply3=new Reply(3,1,R.drawable.b13,"啊","水煮最有名的不是洪都的丽丽水煮吗",sdf.format(date),1);
        Reply reply4=new Reply(4,1,R.drawable.b13,"J.C24 > 啊","那也是招牌水煮之一，南昌好几家都很有名的",sdf.format(date),1);
        Reply reply5=new Reply(5,1,R.drawable.b13,"Juno","行家啊",sdf.format(date),1);
        replies1.add(reply1);
        replies1.add(reply2);
        replies1.add(reply3);
        replies1.add(reply4);
        replies1.add(reply5);
        //设置Adapter
        ReplyAdapter replyAdapter=new ReplyAdapter(replies1,R.layout.item_reply,ReplyActivity.this);
        ListView listView=findViewById(R.id.lv_reply);
        showAllListView(replyAdapter,listView);
        listView.setAdapter(replyAdapter);
        //回复指定的回复
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String content=replies1.get(position).getName()+":"+replies1.get(position).getContent();
                showPopupWindow(content,position);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hint=etReply.getHint().toString();
                String content=etReply.getText().toString();
                if(hint.equals("矜持点赞也可以，知音难觅聊一句")){
                    //回复该评论,向数据库中添加该评论的回复
                    Log.e("添加评论回复",hint);
                    Log.e("添加评论回复",content);
                }else {
                    //向数据库中添加回复
                    Log.e("添加回复",hint);
                    Log.e("添加回复",content);
                }
                etReply.setText("");
                etReply.setHint("矜持点赞也可以，知音难觅聊一句");
                //隐藏软键盘
                InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                //Toast
                Toast toast=Toast.makeText(ReplyActivity.this,"评论成功",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        });

    }
    private void showPopupWindow(String content,final int position){
        LinearLayout linearLayout=findViewById(R.id.root);
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
                //根据position得到点击的那个回复，回复指定的回复
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                etReply.setHint("回复"+replies1.get(position).getName()+"…");
                popupWindow.dismiss();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏popupWindow
                etReply.setHint("矜持点赞也可以，知音难觅聊一句");
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(view);
        //显示PopupWindow
        popupWindow.showAtLocation(linearLayout, Gravity.NO_GRAVITY,0,0);
    }
    //解决ListView嵌套ListView,item显示不全问题
    private void showAllListView(ReplyAdapter replyAdapter,ListView listView) {
        if (replyAdapter != null) {
            int totalHeight = 0;
            for (int i = 0; i <replyAdapter.getCount(); i++) {
                View listItem =replyAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight+ (listView.getDividerHeight() * (replyAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }
    //点赞，赞数加1
    private void clickZan(){
        final ImageView zan=findViewById(R.id.iv_zan);
        final TextView zanCount=findViewById(R.id.tv_zanCount);
        zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isZan){
                    zan.setImageResource(R.drawable.a11);
                    int count=Integer.parseInt(zanCount.getText().toString());
                    count++;
                    zanCount.setText(count+"");
                    isZan=true;
                }else{
                    zan.setImageResource(R.drawable.a10);
                    int count=Integer.parseInt(zanCount.getText().toString());
                    count--;
                    zanCount.setText(count+"");
                    isZan=false;
                }
            }
        });
    }
    //返回上一页
    private void skipPreviousPage(){
        ImageView imageView1=findViewById(R.id.iv_rightarrow);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReplyActivity.this.finish();
            }
        });
    }
}
