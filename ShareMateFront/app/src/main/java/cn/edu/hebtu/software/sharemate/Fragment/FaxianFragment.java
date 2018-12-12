package cn.edu.hebtu.software.sharemate.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.edu.hebtu.software.sharemate.R;

public class FaxianFragment extends Fragment {
    private Map<String,View> TabspecViews = new HashMap<>();
    private Map<String,ImageView> imageViews = new HashMap<>();
    private Map<String,TextView> textViews = new HashMap<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.faxian_fragment,container,false);
        //获取tabhost组件
        FragmentTabHost tabHost = view.findViewById(android.R.id.tabhost);
        //初始化Tabhost容器
        tabHost.setup(getContext(),getChildFragmentManager(),android.R.id.tabcontent);
        tabHost.getTabWidget().setDividerDrawable(null);
        //创建选项卡对象
        TabHost.TabSpec tabSpec0 = tabHost.newTabSpec("t0")
                .setIndicator(getTabSpecView("推荐","t0"));
        //添加选项卡
        tabHost.addTab(tabSpec0,TuijianFragment.class,null);
        //创建选项卡对象
        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("t1")
                .setIndicator(getTabSpecView("推荐","t1"));
        //添加选项卡
        tabHost.addTab(tabSpec1,TuijianFragment.class,null);
        //创建选项卡对象
        TabHost.TabSpec tabSpec7 = tabHost.newTabSpec("t2")
                .setIndicator(getTabSpecView("美食","t2"));
        //添加选项卡
        tabHost.addTab(tabSpec7,FoodFragment.class,null);
        //创建选项卡对象
        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("t3")
                .setIndicator(getTabSpecView("旅行","t3"));
        //添加选项卡
        tabHost.addTab(tabSpec3, TravelFragment.class,null);
        //创建选项卡对象
        TabHost.TabSpec tabSpec4 = tabHost.newTabSpec("t4")
                .setIndicator(getTabSpecView("美妆","t4"));
        //添加选项卡
        tabHost.addTab(tabSpec4,MeizhuangFragment.class,null);
        //创建选项卡对象
        TabHost.TabSpec tabSpec5 = tabHost.newTabSpec("t5")
                .setIndicator(getTabSpecView("动漫","t5"));
        //添加选项卡
        tabHost.addTab(tabSpec5, DongmanFragment.class,null);
        //创建选项卡对象
        TabHost.TabSpec tabSpec6 = tabHost.newTabSpec("t6")
                .setIndicator(getTabSpecView("运动","t6"));
        //添加选项卡
        tabHost.addTab(tabSpec6,SportFragment.class,null);
        //设置默认选中某个选项卡
        tabHost.setCurrentTab(0);
        TextView textView = textViews.get("t1");
        textView.setTextColor(getResources().
                getColor(R.color.textcolor2));
        //切换
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Set<String> keys=TabspecViews.keySet();//返回所有key
                for(String str:keys){
                    if(str.equals(tabId)){
                        if(str.equals("t1")){
                            TextView textView1 = textViews.get("t1");
                            textView1.setTextColor(getResources().
                                    getColor(R.color.textcolor2));
                            TextView textView2 = textViews.get("t2");
                            textView2.setTextColor(getResources().
                                  getColor(R.color.textcolor));
                            TextView textView3 = textViews.get("t3");
                            textView3.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView4 = textViews.get("t4");
                            textView4.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView5 = textViews.get("t5");
                            textView5.setTextColor(getResources().
                                  getColor(R.color.textcolor));
                            TextView textView6 = textViews.get("t6");
                            textView6.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                        }else if(str.equals("t2")){
                            TextView textView1 = textViews.get("t1");
                            textView1.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView2 = textViews.get("t2");
                            textView2.setTextColor(getResources().
                                    getColor(R.color.textcolor2));
                            TextView textView3 = textViews.get("t3");
                            textView3.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView4 = textViews.get("t4");
                            textView4.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView5 = textViews.get("t5");
                            textView5.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView6 = textViews.get("t6");
                            textView6.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                        }else if(str.equals("t3")){
                            TextView textView1 = textViews.get("t1");
                            textView1.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView2 = textViews.get("t2");
                            textView2.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView3 = textViews.get("t3");
                            textView3.setTextColor(getResources().
                                    getColor(R.color.textcolor2));
                            TextView textView4 = textViews.get("t4");
                            textView4.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView5 = textViews.get("t5");
                            textView5.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView6 = textViews.get("t6");
                            textView6.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                        }else if(str.equals("t4")){
                            TextView textView1 = textViews.get("t1");
                            textView1.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView2 = textViews.get("t2");
                            textView2.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView3 = textViews.get("t3");
                            textView3.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView4 = textViews.get("t4");
                            textView4.setTextColor(getResources().
                                    getColor(R.color.textcolor2));
                            TextView textView5 = textViews.get("t5");
                            textView5.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView6 = textViews.get("t6");
                            textView6.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                        }else if(str.equals("t5")){
                            TextView textView1 = textViews.get("t1");
                            textView1.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView2 = textViews.get("t2");
                            textView2.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView3 = textViews.get("t3");
                            textView3.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView4 = textViews.get("t4");
                            textView4.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView5 = textViews.get("t5");
                            textView5.setTextColor(getResources().
                                    getColor(R.color.textcolor2));
                            TextView textView6 = textViews.get("t6");
                            textView6.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                        }else if(str.equals("t6")){
                            TextView textView1 = textViews.get("t1");
                            textView1.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView2 = textViews.get("t2");
                            textView2.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView3 = textViews.get("t3");
                            textView3.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView4 = textViews.get("t4");
                            textView4.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView5 = textViews.get("t5");
                            textView5.setTextColor(getResources().
                                    getColor(R.color.textcolor));
                            TextView textView6 = textViews.get("t6");
                            textView6.setTextColor(getResources().
                                    getColor(R.color.textcolor2));
                        }
                    }
                }
            }
        });
        TabWidget tabWidget = tabHost.getTabWidget();
        // 标签的个数
        int count = tabWidget.getChildCount();
        // 获取手机屏幕的宽高
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenheight = displayMetrics.heightPixels;
        if (count >= 4) {
            for (int i = 0; i < count; i++) {
                // 设置每个标签的宽度，为屏幕的1/4
                tabWidget.getChildTabViewAt(i).setMinimumWidth(
                        (screenWidth) / 5);
            }
        }
        return view;
    }
    private View getTabSpecView(String name, String tag){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.tabspesc_layout2,null);

        TextView textView = view.findViewById(R.id.text);
        textView.setText(name);
        textView.setTextSize(15);
        textView.setTextColor(getResources().
                getColor(R.color.textcolor));
        textViews.put(tag,textView);
        TabspecViews.put(tag,view);
        return view;
    }
}
