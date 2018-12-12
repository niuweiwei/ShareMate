package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.CommentListAdapter;
import cn.edu.hebtu.software.sharemate.Bean.CommentBean;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

public class CommentActivity extends AppCompatActivity {

    private PopupWindow window = null;
    private RelativeLayout root = null;
    private View view = null;
    private RelativeLayout replyLayout =null;
    private InputMethodManager manager = null;
    private Button send = null;
    private EditText text = null;
    private CommentListAdapter adapter = null;
    private final List<CommentBean> comments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        root = findViewById(R.id.root);
        replyLayout = findViewById(R.id.rl_reply);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        UserBean user1 = new UserBean("小仙女",R.drawable.niuweiwei);
        UserBean user2 = new UserBean("小菲菲",R.drawable.mengfeifei);
        UserBean user3 = new UserBean("狗莹",R.drawable.sunliying);
        UserBean user4 = new UserBean("我昭",R.drawable.wangzhao);
        UserBean user5 = new UserBean("低调的仙女姐姐",R.drawable.wangou);
        UserBean user6 = new UserBean("白头鞋老",R.drawable.baijingting);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date();
        Date date2 = new Date();
        Date date3 = new Date();
        Date date4 = new Date();
        Date date5 = new Date();

        try {
            date1 = format.parse("2018-4-30");
            date2 = format.parse("2018-7-9");
            date3 = format.parse("2018-7-25");
            date4 = format.parse("2018-9-28");
            date5 = format.parse("2018-11-29");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        comments.add(new CommentBean(CommentBean.REPLY,user4,date5,"这个奶茶真的建议尝试一下，非常棒!",R.drawable.note3,user6.getUserName(),"我都想喝奶茶了!"));
        comments.add(new CommentBean(CommentBean.COMMENT,user6,date5,"我都想喝奶茶了!",R.drawable.note3,null,null));
        comments.add(new CommentBean(CommentBean.REPLY,user2,date4,"谢谢博主!收到了原图",R.drawable.note1,"我","私信聊"));
        comments.add(new CommentBean(CommentBean.COMMENT,user2,date3,"博主，想要这个壁纸呀!",R.drawable.note1,null,null));
        comments.add(new CommentBean(CommentBean.REPLY,user1,date2,"对对对，最近塔卡沙联名超级可爱",R.drawable.note4,"我","最近超级喜欢塔卡沙的包包"));
        comments.add(new CommentBean(CommentBean.REPLY,user3,date2,"这个包尤其喜欢呀",R.drawable.note4,"我","最近超级喜欢塔卡沙的包包"));
        comments.add(new CommentBean(CommentBean.COMMENT,user5,date1,"这个壁纸也太少女心了吧！超级喜欢",R.drawable.note2,null,null));

        ListView listView = findViewById(R.id.lv_comment);
        adapter = new CommentListAdapter(this,comments,R.layout.comment_list_item_layout);
        listView.setAdapter(adapter);

        //点击 listview 的每一个子项 弹出 popupwindow
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //将回复框隐藏 关闭软键盘
               if(replyLayout.getVisibility()==View.VISIBLE) {
                   replyLayout.setVisibility(View.GONE);
                   manager.hideSoftInputFromWindow(CommentActivity.this.getCurrentFocus().getWindowToken(), 0);
               }

                window = new PopupWindow(root,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                window.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        addBackgroundAlpha(1f);
                        window.dismiss();
                    }
                });
                if(window.isShowing()){
                    window.dismiss();
                }else{
                    showPopupWindow(root,position);
                    addBackgroundAlpha(0.7f);
                }
            }
        });

        //为发送按钮绑定事件
        send = findViewById(R.id.btn_send);
        text = findViewById(R.id.et_reply);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text.getText().toString();
                if("".equals(msg)){
                    //nothing to do
                }else{
                    //向数据库中添加该条评论

                    //回复框隐藏 软键盘消失
                    if(replyLayout.getVisibility()==View.VISIBLE) {
                        replyLayout.setVisibility(View.GONE);
                        manager.hideSoftInputFromWindow(CommentActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }
                    //弹出toast提示用户回复成功或失败
                    Toast replySuccess = Toast.makeText(CommentActivity.this,"发表评论成功",Toast.LENGTH_SHORT);
                    replySuccess.setGravity(Gravity.TOP,0,300);
                    replySuccess.show();
                }
            }
        });

        //为返回按钮绑定事件
        Button back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //显示 popupwindow
    private void showPopupWindow(RelativeLayout root, final int position){

        //获取到要显示的视图
        if(view == null){
            view = getLayoutInflater().inflate(R.layout.comments_popupwindow_layout,null);
        }
        //通过view获取到各个按钮 并绑定事件监听器
        final Button like = view.findViewById(R.id.btn_like);
        final Button reply = view.findViewById(R.id.btn_reply);
        Button detail = view.findViewById(R.id.btn_detail);
        final Button commentList = view.findViewById(R.id.btn_commentlist);
        Button delete = view.findViewById(R.id.btn_delete);
        Button cancel = view.findViewById(R.id.btn_cancel);

        //绑定监听器

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(like.getText().equals("赞")){
                    Toast likeToast = Toast.makeText(CommentActivity.this,"点赞成功",Toast.LENGTH_SHORT);
                    likeToast.setGravity(Gravity.TOP,0,300);
                    like.setText("取消赞");
                    likeToast.show();
                }else{
                    like.setText("赞");
                    Toast cancelToast = Toast.makeText(CommentActivity.this,"取消赞成功",Toast.LENGTH_SHORT);
                    cancelToast.setGravity(Gravity.TOP,0,300);
                    cancelToast.show();
                }
                window.dismiss();
            }
        });
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                replyLayout.setVisibility(View.VISIBLE);
                EditText replyInput = findViewById(R.id.et_reply);
                replyInput.setHint("回复 "+comments.get(position).getUser().getUserName()+":");

                //使得回复输入框自动获取焦点
                replyInput.setFocusable(true);
                replyInput.setFocusableInTouchMode(true);
                replyInput.requestFocus();
                //自动弹出软键盘
                manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        //为 delete按钮绑定事件 弹出对话框 提示用户是否删除该条评论
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //popupwindow 消失
                window.dismiss();
                //弹出提示
                AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确认删除该评论");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //nothing to do
                    }
                });
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        comments.remove(position);
                        //刷新适配器
                        adapter.notifyDataSetChanged();
                        //弹出 toast 提示删除成功 并将评论的内容改为"该评论已删除"
                        Toast deleteSuccess = Toast.makeText(CommentActivity.this,"评论删除成功",Toast.LENGTH_SHORT);
                        deleteSuccess.setGravity(Gravity.TOP,0,300);
                        deleteSuccess.show();
                        //到数据库删除该评论
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //为其余三个按钮绑定监听器
        Listener listener = new Listener();
        detail.setOnClickListener(listener);
        commentList.setOnClickListener(listener);
        cancel.setOnClickListener(listener);

        //将视图设置到 window
        window.setContentView(view);
        //控制 popupwindow 再点击屏幕其他地方时自动消失
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.showAtLocation(root, Gravity.BOTTOM,0,0);
    }

    //调节背景的透明度
    private void addBackgroundAlpha(float alpha){
        //获取到当前屏幕的一系列参数
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = alpha;
        //设置参数
        getWindow().setAttributes(params);
    }

    //监听器类
    private class Listener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_detail:
                    //跳转到笔记详情页面
                    Intent intent = new Intent(CommentActivity.this,NoteDetailActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_commentlist:
                    Intent intent1 = new Intent(CommentActivity.this,CommentDetailActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.btn_cancel:
                    window.dismiss();
                    break;
            }
        }
    }
}
