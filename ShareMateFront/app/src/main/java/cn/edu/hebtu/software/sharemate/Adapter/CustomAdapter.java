package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.util.Log;
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

import cn.edu.hebtu.software.sharemate.Bean.Comment;
import cn.edu.hebtu.software.sharemate.Bean.CommentBean;
import cn.edu.hebtu.software.sharemate.Bean.NoteBean;
import cn.edu.hebtu.software.sharemate.R;

public class CustomAdapter extends BaseAdapter implements View.OnClickListener{
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
            viewHolder.shouCount=convertView.findViewById(R.id.c_count);
            viewHolder.noteComment= convertView.findViewById(R.id.pinglun);
            viewHolder.Comment2= convertView.findViewById(R.id.pinglun2);
            viewHolder.commentCount=convertView.findViewById(R.id.p_count);
            viewHolder.Comment=convertView.findViewById(R.id.pinglun1);
            viewHolder.Allcount=convertView.findViewById(R.id.all_count);
            viewHolder.contentUserIcon=convertView.findViewById(R.id.user_icon2);
            viewHolder.addComment=convertView.findViewById(R.id.fb);
            viewHolder.sendComment=convertView.findViewById(R.id.fabu);
            viewHolder.noteTime = convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        //设置显示的图片和文字
        if(notes.get(position).isIslike()==1){
            notes.get(position).setZan(R.drawable.xihuan2);
        }else {notes.get(position).setZan(R.drawable.xin);}
        if(notes.get(position).getIscollect()==1){
            notes.get(position).setCol(R.drawable.xingxing2);
        }else {notes.get(position).setCol(R.drawable.xingxing);}
        if(notes.get(position).getIsfollow()==1){
            viewHolder.guanZhu.setBackgroundResource(R.drawable.cancelfollowedbutton_style);
            viewHolder.guanZhu.setText("已关注");
            viewHolder.guanZhu.setTextColor(convertView.getResources().getColor(R.color.deepGray));
        }else{
            viewHolder.guanZhu.setBackgroundResource(R.drawable.followbutton_style);
            viewHolder.guanZhu.setText("关注");
            viewHolder.guanZhu.setTextColor(convertView.getResources().getColor(R.color.brightRed));
        }
        viewHolder.noteZan.setBackgroundResource(notes.get(position).getZan());
        viewHolder.ivPhoto.setImageBitmap(notes.get(position).getNoteImage1());
        viewHolder.noteDetail.setText(notes.get(position).getNoteDetail());
        viewHolder.noteTitle.setText(notes.get(position).getNoteTitle());
        viewHolder.noteTime.setText(notes.get(position).getNoteTime());
        viewHolder.userName.setText(notes.get(position).getUser().getUserName());
        viewHolder.Comment.setText(notes.get(position).getCommentBean().getUser().getUserName()+":"+
        notes.get(position).getCommentBean().getCommentDetail());
        viewHolder.Comment2.setText(notes.get(position).getCommentDetail());
        viewHolder.Allcount.setText("共"+notes.get(position).getPingluncount()+"条评论");
        viewHolder.userIcon.setImageBitmap(notes.get(position).getUser().getUserImage());
        viewHolder.contentUserIcon.setImageBitmap(notes.get(position).getUserContent().getUserImage());
        viewHolder.zanCount.setText(notes.get(position).getZancount1()+"");
        viewHolder.shouCount.setText(notes.get(position).getCollectcount()+"");
        viewHolder.commentCount.setText(notes.get(position).getPingluncount()+"");
        viewHolder.noteShoucang.setBackgroundResource(notes.get(position).getCol());
        viewHolder.userIcon.setTag(position);
        viewHolder.guanZhu.setTag(position);
        viewHolder.noteZan.setTag(position);
        viewHolder.sendComment.setTag(position);
        viewHolder.noteShoucang.setTag(position);
        viewHolder.noteComment.setTag(position);
        viewHolder.addComment.setTag(position);
        viewHolder.userIcon.setOnClickListener(this);
        viewHolder.guanZhu.setOnClickListener(this);
        viewHolder.noteZan.setOnClickListener(this);
        viewHolder.noteShoucang.setOnClickListener(this);
        viewHolder.noteComment.setOnClickListener(this);
        viewHolder.sendComment.setOnClickListener(this);
        viewHolder.addComment.setOnClickListener(this);
        final ViewHolder finalViewHolder = viewHolder;
        //final ViewHolder finalViewHolder2 = viewHolder;
        viewHolder.addComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                }else {
                    int p = (int) v.getTag();
                    String c = ""+finalViewHolder.addComment.getText();
                    if(!c.equals("")){
                    notes.get(p).setCommentDetail(c);
                    }
                    Log.e("comment",c);
                }
            }
        });
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
        TextView Comment;TextView Allcount;TextView Comment2;
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
