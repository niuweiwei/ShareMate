package cn.edu.hebtu.software.tabhost;

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

public class CustomAdapter extends BaseAdapter {
    private boolean isIconChange= true;
    private boolean c1 = true;
    private boolean c2 = true;
    private Context context;
    private int itemLaout;
    private List<NoteBean> notes =new ArrayList<>();
    public CustomAdapter(Context context, int itemLayout, List<NoteBean> notes){
        this.context=context;
        this.itemLaout = itemLayout;
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
        if (convertView==null){
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView=layoutInflater.inflate(itemLaout,null);
        }
        //获取每个item中视图空间对象，设置显示的图片和文字
        ImageView ivPhoto=convertView.findViewById(R.id.iv_photo);
        ivPhoto.setImageResource(notes.get(position).getPhoto());
        TextView noteText=convertView.findViewById(R.id.note_alltext);
        noteText.setText(notes.get(position).getAlltext());
        TextView title=convertView.findViewById(R.id.title);
        title.setText(notes.get(position).getTitle());
        TextView time=convertView.findViewById(R.id.time);
        time.setText(notes.get(position).getTime());
        final TextView username=convertView.findViewById(R.id.user_name);
        username.setText(notes.get(position).getUsername());
        final TextView pinglun= convertView.findViewById(R.id.pinglun1);
        //pinglun.setText("小明：这么难吗？");
        ImageView userIcon=convertView.findViewById(R.id.user_icon);
        userIcon.setImageResource(notes.get(position).getUsericon());

//      fabu.setVisibility(View.INVISIBLE);
//            //点击评论发送按钮出现，默认隐藏
//            final EditText faping=convertView.findViewById(R.id.fb);
//        faping.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if(hasFocus){
//                        fabu.setVisibility(View.VISIBLE);
//                    }else{
//                        fabu.setVisibility(View.VISIBLE);
//                    }
//                }
//        });
        //点击发送获取输入框内容
//        fabu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fabu.setFocusableInTouchMode(true);
//                fabu.setFocusable(true);
//                fabu.requestFocus();
//                String p= (String) fabu.getText();
//                Log.e("fabu",p);
//                username.setText(p);
//            }
//        });

        final TextView count=convertView.findViewById(R.id.z_count);
        count.setText(notes.get(position).getZanCount()+"");
        final TextView count2=convertView.findViewById(R.id.c_count);
        count2.setText(notes.get(position).getCollectcount()+"");
        TextView count4=convertView.findViewById(R.id.p_count);
        count4.setText(notes.get(position).getPingluncount()+"");
        final Button guanzhu = convertView.findViewById(R.id.guannzhu);
        guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isIconChange) {
                    guanzhu.setBackgroundResource(R.drawable.yiguanzhu);

                    isIconChange = false;
                } else {
                    guanzhu.setBackgroundResource(R.drawable.guanzhu);
                    isIconChange = true;
                }
//                Intent intent = new Intent();
//                intent.setClass(context,FindActivity.class);
//                context.startActivity(intent);
            }
        });
        final Button dianzan = convertView.findViewById(R.id.dianzan);
        dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c1){
                    dianzan.setBackgroundResource(R.drawable.xihuan2);
//                    int c = notes.get(position).getZanCount();c++;
//                    count.setText(""+c);
                    c1=false;
                }
                else{
                    dianzan.setBackgroundResource(R.drawable.xinxin);
//                    int c = notes.get(position).getZanCount();c--;
//                    count.setText(""+c);
                    c1=true;
                }
            }
        });
        final Button shoucang = convertView.findViewById(R.id.shoucang);
        shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c2){
                shoucang.setBackgroundResource(R.drawable.xingxing2);
                    int c = notes.get(position).getCollectcount();c++;
                    count2.setText(""+c);
                c2=false;
                }
                else {
                    shoucang.setBackgroundResource(R.drawable.xingxing);
                    int c = notes.get(position).getCollectcount();c--;
                    count2.setText(""+c);
                    c2=true;
                }
            }
        });
        final EditText fb= convertView.findViewById(R.id.fb);
        final Button fabu = convertView.findViewById(R.id.fabu);
        fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabu.setFocusableInTouchMode(true);
                fabu.setFocusable(true);
                fabu.requestFocus();
                String t1=""+fb.getText();
                pinglun.setText(t1);
            Log.e("fabu","1"+"2"+pinglun.getText());
                fb.setText("");

            }
        });
        return convertView;
    }





}
