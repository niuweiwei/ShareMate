package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.hebtu.software.sharemate.Bean.Reply;
import cn.edu.hebtu.software.sharemate.R;

public class ReplyCommentAdapter extends BaseAdapter {
    private List<Reply> replies;
    private int itemlayout;
    private Context context;

    public ReplyCommentAdapter(List<Reply> replies, int itemlayout, Context context) {
        this.replies = replies;
        this.itemlayout = itemlayout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return replies.size();
    }

    @Override
    public Object getItem(int position) {
        return replies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null==convertView){
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView=layoutInflater.inflate(itemlayout,null);
        }
            ImageView image = convertView.findViewById(R.id.iv_image);
            image.setImageResource(replies.get(position).getImage());
            TextView name = convertView.findViewById(R.id.tv_name);
            name.setText(replies.get(position).getName());
            TextView content = convertView.findViewById(R.id.tv_CommentContent);
            content.setText(replies.get(position).getContent());
            TextView time = convertView.findViewById(R.id.tv_time);
            time.setText(replies.get(position).getTime());
            TextView countZan = convertView.findViewById(R.id.tv_zan);
            countZan.setText(replies.get(position).getCountZan() + "");
        return convertView;
    }
}
