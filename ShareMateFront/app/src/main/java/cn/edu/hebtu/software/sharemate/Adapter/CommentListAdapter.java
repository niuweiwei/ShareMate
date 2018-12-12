package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Bean.CommentBean;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.RoundImgView;

public class CommentListAdapter extends BaseAdapter {

    private Context context;
    private List<CommentBean> comments = new ArrayList<>();
    private int item_layout;

    public CommentListAdapter(Context context, List<CommentBean> comments, int item_layout) {
        this.context = context;
        this.comments = comments;
        this.item_layout = item_layout;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(item_layout,null);
        }

        //获取评论人的头像
        RoundImgView portraitView = convertView.findViewById(R.id.iv_portrait);
        portraitView.setImageResource(comments.get(position).getUser().getUserPhoto());
        //获取评论人的昵称
        TextView nameView = convertView.findViewById(R.id.tv_name);
        nameView.setText(comments.get(position).getUser().getUserName());
        //获取评论的时间
        Date date = comments.get(position).getDate();
        String time = new SimpleDateFormat("MM-dd").format(date);
        TextView timeView = convertView.findViewById(R.id.tv_date);
        timeView.setText(time);
        //获取评论内容
        TextView commentView = convertView.findViewById(R.id.tv_comment);
        commentView.setText(comments.get(position).getComment());
        //获取评论或回复的内容
        ImageView noteView = convertView.findViewById(R.id.iv_note);
        noteView.setImageResource(comments.get(position).getNotePhoto());
        //判断该该子项是评论的内容还是回复的内容
        int tag = comments.get(position).getTag();
        TextView typeView = convertView.findViewById(R.id.tv_type);
        TextView arguedView = convertView.findViewById(R.id.tv_argued);
        View line = convertView.findViewById(R.id.line);

        if( tag == CommentBean.COMMENT){

            typeView.setText("评论了你的笔记");
            arguedView.setVisibility(View.GONE);
            line.setVisibility(View.GONE);

        }else if( tag == CommentBean.REPLY){

            String user = comments.get(position).getName();
            if(user.equals("我")){
                typeView.setText("回复了你的评论");
            }else{
                typeView.setText("回复了"+user+"的评论");
            }
            arguedView.setVisibility(View.VISIBLE);
            arguedView.setText(user+"的评论:"+comments.get(position).getArgued());
            line.setVisibility(View.VISIBLE);
        }


        return convertView;
    }
}
