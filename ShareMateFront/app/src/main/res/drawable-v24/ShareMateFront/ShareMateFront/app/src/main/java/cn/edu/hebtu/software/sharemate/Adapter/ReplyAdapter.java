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

public class ReplyAdapter extends BaseAdapter {
    private List<Reply> replies;
    private int itemlayout;
    private Context context;
    private Boolean isZan=false;

    public ReplyAdapter(List<Reply> replies, int itemlayout, Context context) {
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
            final TextView count = convertView.findViewById(R.id.tv_zan);
            count.setText(replies.get(position).getCountZan() + "");
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
