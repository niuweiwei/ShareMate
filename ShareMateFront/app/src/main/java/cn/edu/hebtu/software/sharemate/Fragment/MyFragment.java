package cn.edu.hebtu.software.sharemate.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Activity.AddressActivity;
import cn.edu.hebtu.software.sharemate.Activity.FollowActivity;
import cn.edu.hebtu.software.sharemate.Activity.NoteDetailActivity;
import cn.edu.hebtu.software.sharemate.Activity.PersonalActivity;
import cn.edu.hebtu.software.sharemate.Activity.SettingActivity;
import cn.edu.hebtu.software.sharemate.Adapter.NoteAdapter;
import cn.edu.hebtu.software.sharemate.Bean.NoteBean;
import cn.edu.hebtu.software.sharemate.tools.UpLoadUtil;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;

import static android.app.Activity.RESULT_OK;

public class MyFragment extends Fragment {
    private PopupWindow popupWindow = null;
    private LinearLayout root;
    private TextView nameText;
    private TextView idText;
    private TextView introText;
    private ImageView headImg;
    private GridView gridView;
    private NoteAdapter noteAdapter;
    private List<NoteBean> collectionList = new ArrayList<>();
    private List<NoteBean> noteList = new ArrayList<>();
    private UserBean user ;
    private TextView collection;
    private TextView note;
    private TextView focusView;
    private TextView addView;
    private ImageView settingView;
    private Button more;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        user = new UserBean(951004, R.drawable.heart,"净汉的小迷妹","女",
               "黑龙江省双鸭山市","1995-10-04","今天是个好日子，心想的事儿都能成");
        View view = inflater.inflate(R.layout.fragment_my,container,false);
        root = view.findViewById(R.id.root1);
        findView(view);
        setListener();
        noteAdapter = new NoteAdapter(getActivity(), R.layout.note_item,noteList);
        gridView.setAdapter(noteAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void showPopupWindow(){
        popupWindow = new PopupWindow(getContext());
        popupWindow.setWidth(850);
        popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                addBackgroundAlpha(1f);
            }
        });
        View view =getLayoutInflater().inflate(R.layout.more_item,null);
        ImageView imageView=view.findViewById(R.id.delete);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //将自定义的视图添加到 popupWindow 中
        popupWindow.setContentView(view);
        //控制 popupwindow 再点击屏幕其他地方时自动消失
        popupWindow .setFocusable(true);
        popupWindow .setOutsideTouchable(true);
        popupWindow.showAtLocation(root, Gravity.LEFT,0,0);
    }
    public void findView(View view){
        nameText = view.findViewById(R.id.userName);
        nameText.setText(user.getUserName());
        idText = view.findViewById(R.id.userId);
        idText.setText("Share mate号:"+user.getUserId());
        introText = view.findViewById(R.id.userIntro);
        introText.setText(user.getUserIntroduce());
        headImg = view.findViewById(R.id.userPhoto);
        headImg.setImageResource(user.getUserPhoto());
        gridView = view.findViewById(R.id.root);
        gridView.setEmptyView((view.findViewById(R.id.empty_view)));
        collection = view.findViewById(R.id.collection);
        focusView = view.findViewById(R.id.focus);
        addView = view.findViewById(R.id.address);
        note = view.findViewById(R.id.note);
        settingView = view.findViewById(R.id.setting);
        button = view.findViewById(R.id.personal);
        more = view.findViewById(R.id.more);
        note.setTextColor(getResources().getColor(R.color.warmRed));
        collection.setTextColor(getResources().getColor(R.color.darkGray));
    }

    public void setListener(){
        SetOnclickListener listener = new SetOnclickListener();
        collection.setOnClickListener(listener);
        note.setOnClickListener(listener);
        focusView.setOnClickListener(listener);
        addView.setOnClickListener(listener);
        settingView.setOnClickListener(listener);
        button.setOnClickListener(listener);
        more.setOnClickListener(listener);
    }
    public class SetOnclickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.address:
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), AddressActivity.class);
                    intent.putExtra("msg","常住地");
                    intent.putExtra("content",user.getUserAddress());
                    //从数据库中取出当前用户
                    startActivity(intent);
                    break;
                case R.id.note:
                    note.setTextColor(getResources().getColor(R.color.warmRed));
                    collection.setTextColor(getResources().getColor(R.color.darkGray));
                    noteAdapter = new NoteAdapter(getActivity(), R.layout.note_item,noteList);
                    gridView.setAdapter(noteAdapter);
                    break;
                case R.id.collection:
                    collection.setTextColor(getResources().getColor(R.color.warmRed));
                    note.setTextColor(getResources().getColor(R.color.darkGray));
                    NoteBean collection1 = new NoteBean(R.drawable.e,"明星资讯随时看");
                    NoteBean collection2 = new NoteBean(R.drawable.f,"旅游时笔芯狂魔");
                    collectionList.add(collection1);
                    collectionList.add(collection2);
                    noteAdapter = new NoteAdapter(getActivity(), R.layout.note_item,collectionList);
                    gridView.setAdapter(noteAdapter);
                    break;
                case R.id.personal:
                    Intent perIntent = new Intent();
                    perIntent.setClass(getActivity(), PersonalActivity.class);
                    perIntent.putExtra("user",user);
                    startActivity(perIntent);
                    break;
                case R.id.focus:
                    Intent focusIntent = new Intent();
                    focusIntent.setClass(getActivity(), FollowActivity.class);
                    startActivity(focusIntent);
                    break;
                case R.id.setting:
                    Intent setIntent = new Intent();
                    setIntent.setClass(getActivity(), SettingActivity.class);
                    setIntent.putExtra("user",user);
                    startActivity(setIntent);
                    break;
                case R.id.more:
                    showPopupWindow();
                    addBackgroundAlpha(0.7f);
                    break;
            }
        }
    }
    // 弹出选项框时为背景加上透明度
    private void addBackgroundAlpha(float alpha){
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = alpha;
        getActivity().getWindow().setAttributes(params);
    }
}
