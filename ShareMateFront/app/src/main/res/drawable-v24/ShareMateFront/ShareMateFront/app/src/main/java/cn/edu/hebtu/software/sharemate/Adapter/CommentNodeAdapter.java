package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.hebtu.software.sharemate.Bean.Comment;
import cn.edu.hebtu.software.sharemate.R;

public class CommentNodeAdapter extends BaseAdapter {
    private List<Comment> comments;
    private int itemlayout;
    private Context context;
    private Boolean isZan=false;

    public CommentNodeAdapter(List<Comment> comments, int itemlayout, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null==convertView){
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView=layoutInflater.inflate(itemlayout,null);
        }
        ImageView imageView=convertView.findViewById(R.id.iv_image);
        imageView.setImageResource(comments.get(position).getHeadImage());
        TextView name=convertView.findViewById(R.id.tv_name);
        name.setText(comments.get(position).getName());
        TextView content=convertView.findViewById(R.id.tv_content);
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
        return convertView;
    }
}
