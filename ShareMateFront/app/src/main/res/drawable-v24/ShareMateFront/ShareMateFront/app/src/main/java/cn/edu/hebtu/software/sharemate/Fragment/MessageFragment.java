package cn.edu.hebtu.software.sharemate.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.MessageListAdapter;
import cn.edu.hebtu.software.sharemate.Bean.TitleBean;
import cn.edu.hebtu.software.sharemate.Activity.CommentActivity;
import cn.edu.hebtu.software.sharemate.Activity.FollowedActivity;
import cn.edu.hebtu.software.sharemate.Activity.LetterActivity;
import cn.edu.hebtu.software.sharemate.Activity.LikeActivity;
import cn.edu.hebtu.software.sharemate.R;

public class MessageFragment extends Fragment {

    private List<TitleBean> titles = new ArrayList<>();
    private PopupWindow popupWindow = null;
    private LinearLayout root;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message,container,false);
        root = view.findViewById(R.id.root);
        Button button = view.findViewById(R.id.btn_more);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
                addBackgroundAlpha(0.7f);
            }
        });
        titles.add(new TitleBean(R.drawable.like,"收到的赞"));
        titles.add(new TitleBean(R.drawable.comment,"收到的评论"));
        titles.add(new TitleBean(R.drawable.followed,"新增关注"));
        titles.add(new TitleBean(R.drawable.message,"收到的私信"));

        MessageListAdapter listAdapter = new MessageListAdapter(getActivity(),R.layout.message_list_item_layout,titles);
        ListView listView = view.findViewById(R.id.lv_message);
        listView.setAdapter(listAdapter);

        //为列表的每一项绑定点击监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent likeIntent = new Intent(getActivity(), LikeActivity.class);
                        startActivity(likeIntent);
                        break;
                    case 1:
                        Intent commentIntent = new Intent(getActivity(), CommentActivity.class);
                        startActivity(commentIntent);
                        break;
                    case 2:
                        Intent followedIntent = new Intent(getActivity(), FollowedActivity.class);
                        startActivity(followedIntent);
                        break;
                    case 3:
                        Intent letterIntent = new Intent(getActivity(), LetterActivity.class);
                        startActivity(letterIntent);
                        break;
                }
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
        View v =getLayoutInflater().inflate(R.layout.more_item,null);
        ImageView imageView=v.findViewById(R.id.delete);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //将自定义的视图添加到 popupWindow 中
        popupWindow.setContentView(v);
        //控制 popupwindow 再点击屏幕其他地方时自动消失
        popupWindow .setFocusable(true);
        popupWindow .setOutsideTouchable(true);
        popupWindow.showAtLocation(root, Gravity.NO_GRAVITY,0,0);
    }

    // 弹出选项框时为背景加上透明度
    private void addBackgroundAlpha(float alpha){
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = alpha;
        getActivity().getWindow().setAttributes(params);
    }
}
