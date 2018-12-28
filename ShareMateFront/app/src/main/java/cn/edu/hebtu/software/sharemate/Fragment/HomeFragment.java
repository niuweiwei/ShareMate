package cn.edu.hebtu.software.sharemate.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.edu.hebtu.software.sharemate.Activity.LoginActivity;
import cn.edu.hebtu.software.sharemate.Activity.MainActivity;
import cn.edu.hebtu.software.sharemate.R;

public class HomeFragment extends Fragment {
    private Map<String,View> TabspecViews = new HashMap<>();
    private Map<String,ImageView> imageViews = new HashMap<>();
    private Map<String,TextView> textViews = new HashMap<>();
    private PopupWindow popupWindow=null;
    private LinearLayout root;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home,container,false);
        root = view.findViewById(R.id.root);
        Button more = view.findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
                addBackgroundAlpha(0.7f);
            }
        });

        //获取tabhost组件
        FragmentTabHost tabHost = view.findViewById(android.R.id.tabhost);
        //初始化Tabhost容器
        tabHost.setup(getContext(),getChildFragmentManager(),android.R.id.tabcontent);
        tabHost.getTabWidget().setDividerDrawable(null);
        //创建选项卡对象
        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("tab6")
                .setIndicator(getTabSpecView("关注","tab6"));
        //添加选项卡
        tabHost.addTab(tabSpec1,FollowFragment.class,null);
        //创建选项卡对象
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("tab7")
                .setIndicator(getTabSpecView("发现","tab7"));
        //添加选项卡
        tabHost.addTab(tabSpec2,FaxianFragment.class,null);
        //创建选项卡对象
        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("tab8")
                .setIndicator(getTabSpecView("附近","tab8"));
        //添加选项卡
        tabHost.addTab(tabSpec3,FujinFragment.class,null);
        //设置默认选中某个选项卡
        tabHost.setCurrentTab(1);
        TextView textView = textViews.get("tab7");
        textView.setTextColor(getResources().
                getColor(R.color.white));
        //切换
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Set<String> keys=TabspecViews.keySet();//返回所有key
                for(String str:keys){
                    if(str.equals(tabId)){
                        if(str.equals("tab6")){
                            TextView textView1 = textViews.get("tab6");
                            textView1.setTextColor(getResources().
                                    getColor(R.color.white));
                            TextView textView2 = textViews.get("tab7");
                            textView2.setTextColor(getResources().
                                    getColor(R.color.top1));
                            TextView textView3 = textViews.get("tab8");
                            textView3.setTextColor(getResources().
                                    getColor(R.color.top1));
                        }else if(str.equals("tab7")){
                            TextView textView1 = textViews.get("tab6");
                            textView1.setTextColor(getResources().
                                    getColor(R.color.top1));
                            TextView textView2 = textViews.get("tab7");
                            textView2.setTextColor(getResources().
                                    getColor(R.color.white));
                            TextView textView3 = textViews.get("tab8");
                            textView3.setTextColor(getResources().
                                    getColor(R.color.top1));
                        }else if(str.equals("tab8")){
                            TextView textView1 = textViews.get("tab6");
                            textView1.setTextColor(getResources().
                                    getColor(R.color.top1));
                            TextView textView2 = textViews.get("tab7");
                            textView2.setTextColor(getResources().
                                    getColor(R.color.top1));
                            TextView textView3 = textViews.get("tab8");
                            textView3.setTextColor(getResources().
                                    getColor(R.color.white));
                        }
                    }
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
        //退出登录
        final TextView btnback=v.findViewById(R.id.tuichu);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        //将自定义的视图添加到 popupWindow 中
        popupWindow.setContentView(v);
        //控制 popupwindow 再点击屏幕其他地方时自动消失
        popupWindow .setFocusable(true);
        popupWindow .setOutsideTouchable(true);
        popupWindow.showAtLocation(root, Gravity.NO_GRAVITY,0,0);

    }
    private View getTabSpecView(String name, String tag){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.tabspesc_layout2,null);

        TextView textView = view.findViewById(R.id.text);
        textView.setText(name);
        textView.setTextSize(20);
        textView.setTextColor(getResources().
                getColor(R.color.top1));
        textViews.put(tag,textView);
        TabspecViews.put(tag,view);
        return view;
    }

    // 弹出选项框时为背景加上透明度
        private void addBackgroundAlpha(float alpha){
            WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
            params.alpha = alpha;
            getActivity().getWindow().setAttributes(params);
        }
}
