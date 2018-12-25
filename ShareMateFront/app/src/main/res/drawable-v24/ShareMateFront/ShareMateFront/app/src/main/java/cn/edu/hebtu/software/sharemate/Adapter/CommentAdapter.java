package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Activity.ReplyActivity;
import cn.edu.hebtu.software.sharemate.Bean.Comment;
import cn.edu.hebtu.software.sharemate.Bean.Reply;
import cn.edu.hebtu.software.sharemate.R;

public class CommentAdapter extends BaseAdapter{
    private List<Comment> comments;
    private int itemlayout;
    private Context context;
    private Boolean isZan=false;

    public CommentAdapter(List<Comment> comments, int itemlayout, Context context) {
        this.comments = comments;
        this.itemlayout = itemlayout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (null==convertView){
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView=layoutInflater.inflate(itemlayout,null);
        }
        ImageView imageView=convertView.findViewById(R.id.iv_image);
        imageView.setImageResource(comments.get(position).getHeadImage());
        TextView name=convertView.findViewById(R.id.tv_name);
        name.setText(comments.get(position).getName());
        final TextView content=convertView.findViewById(R.id.tv_content);
        content.setText(comments.get(position).getContent());
        TextView time=convertView.findViewById(R.id.tv_time);
        time.setText(comments.get(position).getCommentTime().toString());
        final TextView count=convertView.findViewById(R.id.tv_zan);
        count.setText(comments.get(position).getCountZan()+"");
        //点赞，赞数加1
        final ImageView zan=convertView.findViewById(R.id.iv_zan);
        zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isZan){
                    zan.setImageResource(R.drawable.a11);
                    int zanCount=Integer.parseInt(count.getText().toString());
                    zanCount++;
                    count.setText(zanCount+"");
                    isZan=true;
                }else{
                    zan.setImageResource(R.drawable.a10);
                    int zanCount=Integer.parseInt(count.getText().toString());
                    zanCount--;
                    count.setText(zanCount+"");
                    isZan=false;
                }
            }
        });
        //------------------回复------------------
        //更具position区分每一个评论，分别找到评论对应的回复
        SimpleDateFormat sdf=new SimpleDateFormat("MM-dd hh:mm");
        Date date=new Date();
        List<Reply> replies1=new ArrayList<>();
        Reply reply1=new Reply(1,1,R.drawable.b13,"一个人去游泳像投水(作者)","好嘞！got!!!",sdf.format(date),2);
        Reply reply2=new Reply(2,1,R.drawable.b13,"TT","哈哈哈哈哈我也想要",sdf.format(date),1);
        replies1.add(reply1);
        replies1.add(reply2);
        ReplyCommentAdapter replyAdapter=new ReplyCommentAdapter(replies1,R.layout.item_reply_comment,context);
        ListView listView=convertView.findViewById(R.id.lv_reply);
        showAllListView(replyAdapter,listView);
        listView.setAdapter(replyAdapter);
        //跳转到回复页面,携带数据commentId
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent intent=new Intent(context,ReplyActivity.class);
                intent.putExtra("commentId",comments.get(position).getCommentId());
                context.startActivity(intent);
            }
        });
        final LinearLayout allReply=convertView.findViewById(R.id.ll_allReply);
        allReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ReplyActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    //解决ListView嵌套ListView问题
    private void showAllListView(ReplyCommentAdapter replyAdapter,ListView listView) {
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
}
