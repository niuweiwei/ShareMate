package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Bean.LetterBean;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.RoundImgView;

public class LetterListAdapter extends BaseAdapter {

    private Context context ;
    private int item_layout;
    private List<LetterBean> letters ;

    public LetterListAdapter(Context context, int item_layout, List<LetterBean> letters) {
        this.context = context;
        this.item_layout = item_layout;
        this.letters = letters;
    }

    @Override
    public int getCount() {
        return letters.size();
    }

    @Override
    public Object getItem(int position) {
        return letters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if( null == convertView ){
            convertView = LayoutInflater.from(context).inflate(item_layout,null);
        }

        //为每一个子项 填充布局
        RoundImgView portraitView = convertView.findViewById(R.id.iv_portrait);
        portraitView.setImageResource(letters.get(position).getUser().getUserPhoto());
        TextView nameView = convertView.findViewById(R.id.tv_name);
        nameView.setText(letters.get(position).getUser().getUserName());
        TextView lastView = convertView.findViewById(R.id.tv_chat);
        lastView.setText(letters.get(position).getContent());
        //将对象中的日期属性转化成字符串类型
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        String date = format.format(letters.get(position).getDate());
        TextView dateView = convertView.findViewById(R.id.tv_date);
        dateView.setText(date);

        return convertView;
    }
}
