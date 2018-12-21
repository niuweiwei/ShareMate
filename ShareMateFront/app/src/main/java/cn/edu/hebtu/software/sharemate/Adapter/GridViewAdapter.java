package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.hebtu.software.sharemate.Bean.NoteBean;
import cn.edu.hebtu.software.sharemate.R;

public class GridViewAdapter extends BaseAdapter implements View.OnClickListener{

    //ALt+enter出方法
    private Context context;
    private boolean c1=true;
    private int itemLayout;
    private Callback mCallback;
    private Map<Integer,Boolean> isLike=new HashMap<>();
    private List<NoteBean> notes =new ArrayList<>();
    public GridViewAdapter(Context context, int itemLayout, Callback mCallback, List<NoteBean> notes){
        this.context=context;
        this.mCallback=mCallback;
        this.itemLayout=itemLayout;
        this.notes = notes;
    }
    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 加载item布局
        if (convertView==null){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(itemLayout,null);//传空的意思是不用他去加载父视图
        }

        //获取每个item中视图空间对象，设置显示的图片和文字
        ImageView ivPhoto=convertView.findViewById(R.id.iv_photo);
        ivPhoto.setImageBitmap(notes.get(position).getNoteImage1());
        TextView text=convertView.findViewById(R.id.note_text);
        text.setText(notes.get(position).getNoteDetail());
        TextView username=convertView.findViewById(R.id.user_name);
        username.setText(notes.get(position).getUser().getUserName());
        ImageView icon=convertView.findViewById(R.id.user_icon);
        icon.setImageBitmap(notes.get(position).getUser().getUserImage());
        final TextView count=convertView.findViewById(R.id.zan_count);
        count.setText(notes.get(position).getZancount()+"");
        final Button button = convertView.findViewById(R.id.dianzan);
        if(notes.get(position).isIslike()==1){
            notes.get(position).setZan(R.drawable.xihuan2);
        }else if(notes.get(position).isIslike()==0) {
            notes.get(position).setZan(R.drawable.xin);
        }else {notes.get(position).setZan(R.drawable.weizhi);}
        button.setBackgroundResource(notes.get(position).getZan());
        button.setTag(position);
        icon.setTag(position);
        button.setOnClickListener(this);
        icon.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        mCallback.click(v);
    }
    /**
     * 回调接口.
     */
    public interface Callback {
        void click(View v);
    }
}
