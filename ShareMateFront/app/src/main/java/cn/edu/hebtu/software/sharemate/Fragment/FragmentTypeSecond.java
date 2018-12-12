package cn.edu.hebtu.software.sharemate.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Activity.ProductActivity;
import cn.edu.hebtu.software.sharemate.Adapter.ShopdetialAdapter;
import cn.edu.hebtu.software.sharemate.Bean.ShopBean;
import cn.edu.hebtu.software.sharemate.R;

public class FragmentTypeSecond extends Fragment {
    private List<ShopBean> shopBeans =new ArrayList<>();
    private GridView gridView;
    private ShopdetialAdapter shopdetialAdapter;
    private int mType = 0;
    private String mTitle;
    public void setType(int mType) {
        this.mType = mType;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_second,
                container,
                false);
        shopBeans =new ArrayList<>();
        ShopBean shopBean1 =new ShopBean(R.drawable.shop1,"上海 想你的铃儿响叮当","STYLEJING静风格 森系思念小铃铛S925银项链","￥179","十元劵");
        ShopBean shopBean2 =new ShopBean(R.drawable.shop2,"上海 趣味灵动星光","STYLEJING静风格 公主星S925银针不对称彩色","￥66","10元劵");
        ShopBean shopBean3 =new ShopBean(R.drawable.shop3,"英国 打造百变日抛眼妆","W7九色眼影盘","￥65","99选2");
        ShopBean shopBean4 =new ShopBean(R.drawable.shop4,"上海 对你一见钟情","STYLEJING静风格 一见钟情 S925银针不对称","￥86","10元劵");
        ShopBean shopBean5 =new ShopBean(R.drawable.shop5,"日本 超平价全身美白","DAISO大创 美白化妆水120ml","￥29","自营");
        ShopBean shopBean6 =new ShopBean(R.drawable.shop6,"上海 兜兜转转还是你","STYLEJING静风格 爱情螺旋定律S925银锁骨链","￥196","立减10元");
        shopBeans.add(shopBean1);
        shopBeans.add(shopBean2);
        shopBeans.add(shopBean3);
        shopBeans.add(shopBean4);
        shopBeans.add(shopBean5);
        shopBeans.add(shopBean6);
        gridView=view.findViewById(R.id.root2);

        shopdetialAdapter=new ShopdetialAdapter(getContext(),
                R.layout.list_item2,
                shopBeans);
        gridView.setAdapter(shopdetialAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
