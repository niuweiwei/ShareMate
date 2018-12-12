package cn.edu.hebtu.software.sharemate.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Bean.FollowBean;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.RoundImgView;

public class FollowedListAdapter extends BaseAdapter {

    private Context context;
    private int item_layout;
    private List<FollowBean> follows = new ArrayList<>();

    public FollowedListAdapter(Context context, int item_layout, List<FollowBean> follows) {
        this.context = context;
        this.item_layout = item_layout;
        this.follows = follows;
    }

    @Override
    public int getCount() {
        return follows.size();
    }

    @Override
    public Object getItem(int position) {
        return follows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //为每一个子项填充布局
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(item_layout,null);
        }

        RoundImgView portraitView = convertView.findViewById(R.id.iv_portrait);
        portraitView.setImageResource(follows.get(position).getUser().getUserPhoto());
        TextView nameView = convertView.findViewById(R.id.tv_name);
        nameView.setText(follows.get(position).getUser().getUserName());

        //将对象中的 date 属性 转化成字符串
        final Date date = follows.get(position).getDate();
        String time = new SimpleDateFormat("yyyy-MM-dd").format(date);

        TextView timeView = convertView.findViewById(R.id.tv_date);
        timeView.setText(time);

        //为 "回粉" 按钮绑定点击事件
        final Button follow = convertView.findViewById(R.id.btn_follow);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(follow.getText().equals("回粉")) {
                    follow.setText("已关注");
                    follow.setTextColor(context.getResources().getColor(R.color.deepGray));
                    follow.setBackgroundResource(R.drawable.cancelfollowedbutton_style);
                }else{
                    // 点击 “已关注” 弹出"是否取消关注"的提示框
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("确认取消关注？");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //nothing to do
                        }
                    });
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            follow.setText("回粉");
                            follow.setTextColor(context.getResources().getColor(R.color.brightRed));
                            follow.setBackgroundResource(R.drawable.followbutton_style);
                        }
                    });
                    //获取提示框
                    final AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }
            }
        });

        return convertView;
    }
}
