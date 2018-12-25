package cn.edu.hebtu.software.sharemate.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Adapter.TestFragmentAdapter;
import cn.edu.hebtu.software.sharemate.R;

public class ShoppingFragment extends Fragment {
    private View viewContent;
    private TabLayout tab_essence;
    private ViewPager vp_essence;
    private LinearLayout root;
    private List<Fragment> list;
    private PopupWindow popupWindow=null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewContent = inflater.inflate(R.layout.fragment_shopping,container,false);
        initConentView(viewContent);
        initData();
        root = viewContent.findViewById(R.id.root1);
        Button more =viewContent.findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
                addBackgroundAlpha(0.7f);
            }
        });

        list=new ArrayList<>();
        list.add(new FragmentTypeFirst());
        list.add(new FragmentTypeSecond());
        return viewContent;
    }

    private void showPopupWindow(){
        popupWindow = new PopupWindow(getContext());
        popupWindow.setWidth(850);
        popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //在弹窗消失时调用
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

    public void initConentView(View viewContent) {
        this.tab_essence = (TabLayout) viewContent.findViewById(R.id.tab_essence);
        this.vp_essence = (ViewPager) viewContent.findViewById(R.id.vp_essence);
    }

    public void initData() {
        //获取标签数据
        String[] titles = getResources().getStringArray(R.array.home_video_tab);

        //创建一个viewpager的adapter
        TestFragmentAdapter adapter = new TestFragmentAdapter(getFragmentManager(),list, Arrays.asList(titles));
        this.vp_essence.setAdapter(adapter);

        //将TabLayout和ViewPager关联起来

        this.tab_essence.setupWithViewPager(this.vp_essence);
    }

    // 弹出选项框时为背景加上透明度
    private void addBackgroundAlpha(float alpha){
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = alpha;
        getActivity().getWindow().setAttributes(params);
    }
}
