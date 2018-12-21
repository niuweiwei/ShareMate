package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Bean.CommentBean;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.ImageTask;
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

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(item_layout, null);
        }

        //获取评论人的头像的布局
        ImageView userPhoto = convertView.findViewById(R.id.iv_portrait);
        ImageTask userTaske = new ImageTask(comments.get(position).getUser().getUserPhotoPath());
        userTaske.execute(userPhoto);
        //获取相关笔记的图片的布局
        ImageView notePhoto = convertView.findViewById(R.id.iv_note);
        ImageTask noteTask = new ImageTask(comments.get(position).getNotePhotoPath());
        noteTask.execute(notePhoto);
        //获取评论人的昵称的布局
        TextView publisherName = convertView.findViewById(R.id.tv_name);
        publisherName.setText(comments.get(position).getUser().getUserName());
        //获取评论的时间的布局
        TextView  timeView = convertView.findViewById(R.id.tv_date);
        timeView.setText(comments.get(position).getDate());
        //获取评论内容的布局
        TextView conmmentView = convertView.findViewById(R.id.tv_comment);
        conmmentView.setText(comments.get(position).getComment());
        //获取类型 是评论还是回复的布局
        TextView typeView = convertView.findViewById(R.id.tv_type);
        //获取被回复的内容的布局
        TextView arguedView = convertView.findViewById(R.id.tv_argued);

        //判断该该子项是评论的内容还是回复的内容
        int tag = comments.get(position).getTag();
        View line = convertView.findViewById(R.id.line);
        if (tag == CommentBean.COMMENT) {

            typeView.setText("评论了你的笔记");
           arguedView.setVisibility(View.GONE);
            line.setVisibility(View.GONE);

        } else if (tag==CommentBean.REPLYCOMMENT||tag==CommentBean.REPLYREPLY) {

            String user = comments.get(position).getName();
            if (user.equals("我")) {
              typeView.setText("回复了你的评论");
            } else {
                typeView.setText("回复了" + user + "的评论");
            }
           arguedView.setVisibility(View.VISIBLE);
            arguedView.setText(user + "的评论:" + comments.get(position).getArgued());
            line.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

}
