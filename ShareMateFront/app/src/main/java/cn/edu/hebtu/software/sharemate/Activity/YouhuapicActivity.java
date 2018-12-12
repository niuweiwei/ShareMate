package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import cn.edu.hebtu.software.sharemate.R;

public class YouhuapicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhuapic);
        Intent intent=getIntent();
        String code=intent.getStringExtra("code");
        Log.e("code",code);
        //添加第一个图片
        ImageView imageView=findViewById(R.id.imageView);
        if(code.equals("1"))
        {
            byte[] bis=intent.getByteArrayExtra("pic");
            Bitmap bitmap= BitmapFactory.decodeByteArray(bis,0,bis.length);
            imageView.setImageBitmap(bitmap);
        }
        else if(code.equals("2")){
            String picturePath=intent.getStringExtra("pic");
            Bitmap bitmap=BitmapFactory.decodeFile(picturePath);
            imageView.setImageBitmap(bitmap);
        }
        else{
            Log.e("picture","图片未获取到");
        }
    }

}
