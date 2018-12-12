package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.hebtu.software.sharemate.Bean.ChatBean;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.RoundImgView;

public class ChatListAdapter extends BaseAdapter {

    private Context context;
    private List<ChatBean> chats;
    private int left_item_layout;
    private int right_item_layout;

    public ChatListAdapter(Context context, List<ChatBean> chats, int left_item_layout, int right_item_layout) {
        this.context = context;
        this.chats = chats;
        this.left_item_layout = left_item_layout;
        this.right_item_layout = right_item_layout;
    }

    @Override
    public int getCount() {
        return chats.size();
    }

    @Override
    public Object getItem(int position) {
        return chats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null ){
            if(chats.get(position).getType()==0){
                //表示当前消息是自己发送的
                convertView = LayoutInflater.from(context).inflate(right_item_layout,null);
            }else{
                //表示当前消息是对方发送的
                convertView = LayoutInflater.from(context).inflate(left_item_layout,null);
            }
        }
        RoundImgView portraitView = convertView.findViewById(R.id.iv_portrait);
        portraitView.setImageResource(chats.get(position).getUser().getUserPhoto());
        TextView contentView = convertView.findViewById(R.id.tv_content);
        contentView.setText(chats.get(position).getMessage());
        return convertView;
    }
}
