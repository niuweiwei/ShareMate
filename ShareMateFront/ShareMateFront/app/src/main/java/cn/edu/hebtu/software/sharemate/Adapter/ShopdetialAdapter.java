package cn.edu.hebtu.software.sharemate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.sharemate.Bean.ShopBean;
import cn.edu.hebtu.software.sharemate.R;

public class ShopdetialAdapter extends BaseAdapter{
    private Context content;
    private int itemLayout;
    private List<ShopBean> shopBeans = new ArrayList<>();


    public ShopdetialAdapter() {
    }

    public ShopdetialAdapter(Context content, int itemLayout, List<ShopBean> shopBeans) {
        this.content = content;
        this.itemLayout = itemLayout;
        this.shopBeans = shopBeans;
    }

    @Override
    public int getCount() {
        return shopBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return shopBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        if(null==convertView){
            LayoutInflater layoutInflater=LayoutInflater.from(content);
            convertView=layoutInflater.inflate(itemLayout,null);
        }
        ImageView ivphoto=convertView.findViewById(R.id.item_image);
        ivphoto.setImageResource(shopBeans.get(position).getPhoto());
        TextView ivname=convertView.findViewById(R.id.item_name);
        ivname.setText(shopBeans.get(position).getName());
        TextView ivissure=convertView.findViewById(R.id.item_issure);
        ivissure.setText(shopBeans.get(position).getIssure());
        TextView ivprice=convertView.findViewById(R.id.item_price);
        ivprice.setText(shopBeans.get(position).getPrice());
        TextView ivyouhui=convertView.findViewById(R.id.item_youhui);
        ivyouhui.setText(shopBeans.get(position).getYouhui());

        return convertView;
    }
}

