package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Bean.NoteBean;
import cn.edu.hebtu.software.sharemate.R;

public class CustomAdapter extends BaseAdapter implements View.OnClickListener{
    private boolean isIconChange= true;
    private boolean c1 = true;
    private boolean c2 = true;
    private Context context;
    private int itemLaout;
    private Callback mCallback;
    private List<NoteBean> notes =new ArrayList<>();

    public CustomAdapter(Context context, int itemLaout, Callback mCallback, List<NoteBean> notes) {
        this.context = context;
        this.itemLaout = itemLaout;
        this.mCallback = mCallback;
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
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView=layoutInflater.inflate(itemLaout,null);
            viewHolder.userIcon=convertView.findViewById(R.id.user_icon);
            viewHolder.userName=convertView.findViewById(R.id.user_name);
            viewHolder.guanZhu = convertView.findViewById(R.id.guannzhu);
            viewHolder.ivPhoto=convertView.findViewById(R.id.iv_photo);
            viewHolder.noteDetail=convertView.findViewById(R.id.note_alltext);
            viewHolder.noteTitle=convertView.findViewById(R.id.title);
            viewHolder.noteZan = convertView.findViewById(R.id.dianzan);
            viewHolder.zanCount=convertView.findViewById(R.id.z_count);
            viewHolder.noteShoucang = convertView.findViewById(R.id.shoucang);
            viewHolder.shouCount=convertView.findViewById(R.id.z_count);
            viewHolder.noteComment= convertView.findViewById(R.id.pinglun);
            viewHolder.commentCount=convertView.findViewById(R.id.z_count);
            viewHolder.Comment=convertView.findViewById(R.id.pinglun1);
            viewHolder.contentUserIcon=convertView.findViewById(R.id.user_icon2);
            viewHolder.addComment=convertView.findViewById(R.id.fb);
            viewHolder.sendComment=convertView.findViewById(R.id.fabu);
            viewHolder.noteTime = convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        //设置显示的图片和文字
        viewHolder.ivPhoto.setImageResource(notes.get(position).getNoteImage());
        viewHolder.noteDetail.setText(notes.get(position).getNoteDetail());
        viewHolder.noteTitle.setText(notes.get(position).getNoteTitle());
        viewHolder.noteTime.setText(notes.get(position).getNoteTime());
        viewHolder.userName.setText(notes.get(position).getUser().getUserName());
        viewHolder.Comment.setText("小明：这么难吗？");
        viewHolder.userIcon.setImageResource(notes.get(position).getUser().getUserPhoto());
        viewHolder.zanCount.setText(notes.get(position).getZancount()+"");
        viewHolder.guanZhu.setTag(position);
        viewHolder.noteZan.setTag(position);
        viewHolder.guanZhu.setOnClickListener(this);
        viewHolder.noteZan.setOnClickListener(this);
        viewHolder.noteShoucang.setOnClickListener(this);
        viewHolder.noteComment.setOnClickListener(this);
        return convertView;
    }

    class ViewHolder {
        ImageView userIcon;
        TextView userName;
        Button guanZhu;
        ImageView ivPhoto;
        TextView noteDetail;
        TextView noteTitle;
        Button noteZan;TextView zanCount;
        Button noteShoucang;TextView shouCount;
        Button noteComment;TextView commentCount;
        TextView Comment;
        ImageView contentUserIcon;
        EditText addComment;
        Button sendComment;
        TextView noteTime;
    }

    @Override
    public void onClick(View view) {
        mCallback.click(view);
    }

    /**
     * 回调接口.
     */
    public interface Callback {
        void click(View v);
    }

}
