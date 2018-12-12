package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.FileUtilcll;
import cn.edu.hebtu.software.sharemate.tools.IdentifyingCode;
import cn.edu.hebtu.software.sharemate.tools.UpLoadUtil;

public class RegisterActivity extends AppCompatActivity {

    private static final int OPEN_ALBUM = 2;
    private static final int RESULT_REQUEST_CODE = 3;
    private ImageView back;
    private Button btnTrue;
    private Button getCode;
    private TimeCount time;
    private ImageView ivCode;
    private EditText etCode;
    private String realCode;
    private ImageView head;
    private PopupWindow popupWindow;
    private LinearLayout rootLinear;
    private Button btnOpen;
    private Button btnCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        back.setOnClickListener(new backClickListener());
        btnTrue.setOnClickListener(new ButtonClickListener());

        time = new TimeCount(30000, 1000);
        getCode.setOnClickListener(new getCodeClickListener());

        ivCode.setOnClickListener(new idtfCodeClickListener());
        head.setOnClickListener(new photoClickListener());
    }

    private void findViews() {
        back = findViewById(R.id.iv_back);
        btnTrue = findViewById(R.id.btn_true);
        getCode = findViewById(R.id.get_verify_code);
        ivCode = findViewById(R.id.iv_showCode);
        etCode = findViewById(R.id.et_phoneCodes);
        head = findViewById(R.id.head);
        rootLinear = findViewById(R.id.root);
    }

    /**
     点击返回
     */
    private class backClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 点击确定按钮
     */
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String code = etCode.getText().toString().toLowerCase();
//            String msg = "生成的验证码："+realCode+"输入的验证码："+code;
//            Toast.makeText(RegisterActivity.this,msg,Toast.LENGTH_LONG).show();

            if(code.equals(realCode)){
                Toast.makeText(RegisterActivity.this,code + "验证码正确",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(RegisterActivity.this,code + "验证码错误",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 获取验证码
     */
    private class getCodeClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            time.start();
        }
    }

    /**
     * 实现验证码倒计时
     */
    public class TimeCount extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getCode.setBackgroundResource(R.drawable.get_verify_code2);
            getCode.setClickable(false);
            getCode.setText("(" + millisUntilFinished / 1000 + ")秒后可重发");
        }

        @Override
        public void onFinish() {
            getCode.setText("重新获取");
            getCode.setClickable(true);
            getCode.setBackgroundResource(R.drawable.get_verify_code);
        }
    }

    /**
     * 生成随机验证码图片
     */
    private class idtfCodeClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            ivCode.setImageBitmap(IdentifyingCode.getInstance().createBitmap());
            realCode = IdentifyingCode.getInstance().getCode().toLowerCase();
        }
    }

    /**
     * 上传头像
     */
    private class photoClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            popupWindow = new PopupWindow(RegisterActivity.this);
            popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
            View view = getLayoutInflater().inflate(R.layout.upload_head,null);
            btnOpen = view.findViewById(R.id.btn_open);
            btnCancle = view.findViewById(R.id.btn_cancle);
            //打开手机相册
            btnOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                    startActivityForResult(intent,OPEN_ALBUM);
                    popupWindow.dismiss();
                }
            });
            //取消
            btnCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
            popupWindow.setContentView(view);
            popupWindow.showAtLocation(rootLinear, Gravity.CENTER,0,0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                switch (requestCode){
                    case OPEN_ALBUM:
                        startPhotoZoom(data.getData());
                        break;
                    case RESULT_REQUEST_CODE:
                                RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true);
                                Bundle bundle = data.getExtras();
                                Log.e("aaa","aaa");
                                Bitmap bitmap = bundle.getParcelable("data");
                                Glide.with(this).load(FileUtilcll.saveBitmap(bitmap)).apply(mRequestOptions).into(head);
                                UpLoadUtil uploadUtil = new UpLoadUtil();
                                uploadUtil.execute(FileUtilcll.saveBitmap(bitmap));
                        break;
                }
        }

    //对图片进行裁剪
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);//这里的RESULT_REQUEST_CODE是在startActivityForResult里使用的返回值。
    }
}
