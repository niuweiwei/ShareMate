package cn.edu.hebtu.software.sharemate.Activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.edu.hebtu.software.sharemate.tools.GradationScrollView;
import cn.edu.hebtu.software.sharemate.R;

public class ProductActivity extends AppCompatActivity implements GradationScrollView.ScrollViewListener{
    private ImageView wishImage;
    private TextView wishText;
    private int flag=0;
    private RelativeLayout title;
    private int height;
    private TextView titleName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deproduct);
        TextView textView=findViewById(R.id.tv_deleteprice);
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        wishImage=findViewById(R.id.iv_wish);
        wishText=findViewById(R.id.tv_wish);
        //心愿单点击
        wishImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0) {
                    wishImage.setImageResource(R.drawable.b12);
                    wishText.setTextColor(getResources().getColor(R.color.inkGray));
                    flag=1;
                }else if(flag==1){
                    wishImage.setImageResource(R.drawable.b5);
                    wishText.setTextColor(getResources().getColor(R.color.darkGray));
                    flag=0;
                }
            }
        });
        initListeners();
    }
    //获取顶部图片高度后，设置滚动监听
    private void initListeners(){
        final ImageView banner=findViewById(R.id.iv_productImage);
        title=findViewById(R.id.tv_title);
        final GradationScrollView scrollView=findViewById(R.id.scrollView);
        ViewTreeObserver vto=banner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                title.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                height=banner.getHeight();
                scrollView.setScrollViewListener(ProductActivity.this);
            }
        });
    }
    //滑动监听
    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
        titleName=findViewById(R.id.tv_titleName);
        if (y <= 0) {   //设置标题的背景颜色
            title.setBackgroundColor(Color.argb((int) 0, 0,0,0));
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            titleName.setTextColor(Color.argb((int) alpha, 255,255,255));
            titleName.setTextSize(18);
            title.setBackgroundColor(Color.argb((int) alpha, 222,65,56));
        } else {    //滑动到banner下面设置普通颜色
            title.setBackgroundColor(Color.argb((int) 255, 222,65,56));
        }
    }
}
