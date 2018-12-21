package cn.edu.hebtu.software.sharemate.Activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.tools.UpLoadUtil;
import cn.edu.hebtu.software.sharemate.R;

public class PersonalActivity extends AppCompatActivity {

    private Uri cropUri;
    private File file;
    private File cropFile;
    private TextView tv_name;
    private TextView tv_sex;
    private TextView tv_id;
    private TextView tv_address;
    private TextView tv_birth;
    private TextView tv_introduce;
    private ImageView iv_back;
    private ImageView iv_head;
    private LinearLayout layoutName;
    private LinearLayout layoutSex;
    private LinearLayout layoutBirth;
    private LinearLayout layoutAddress;
    private LinearLayout layoutIntro;
    private LinearLayout rootLayout;
    private String name;
    private String sex;
    private String birth;
    //user应该从数据库中获得
    private UserBean user;
    private static final int CODE_PHOTO_REQUEST = 1;
    private static final int CROP_SMALL_PICTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        rootLayout = findViewById(R.id.root);
        user = (UserBean) getIntent().getSerializableExtra("user");
        findView();
        setListener();
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
    }

    private void findView() {
        iv_back = findViewById(R.id.back);
        iv_head = findViewById(R.id.head);
        iv_head.setImageResource(user.getUserPhoto());
        tv_name = findViewById(R.id.user);
        tv_name.setText(user.getUserName());
        tv_id = findViewById(R.id.num);
        tv_id.setText("" + user.getUserId());
        tv_sex = findViewById(R.id.sex);
        tv_sex.setText(user.getUserSex());
        tv_birth = findViewById(R.id.birth);
        tv_birth.setText(user.getUserBirth());
        tv_address = findViewById(R.id.address);
        tv_address.setText(user.getUserAddress());
        tv_introduce = findViewById(R.id.introduction);
        if (user.getUserIntroduce() == null || user.getUserIntroduce().length() < 7) {
            tv_introduce.setText(user.getUserIntroduce());
        } else {
            tv_introduce.setText(user.getUserIntroduce().substring(0, 6) + "...");
        }
        layoutName = findViewById(R.id.ly_name);
        layoutAddress = findViewById(R.id.ly_address);
        layoutBirth = findViewById(R.id.ly_birth);
        layoutIntro = findViewById(R.id.ly_intro);
        layoutSex = findViewById(R.id.ly_sex);
    }

    private void setListener() {
        perOnClickListener listener = new perOnClickListener();
        layoutName.setOnClickListener(listener);
        iv_back.setOnClickListener(listener);
        iv_head.setOnClickListener(listener);
        layoutSex.setOnClickListener(listener);
        layoutIntro.setOnClickListener(listener);
        layoutBirth.setOnClickListener(listener);
        layoutAddress.setOnClickListener(listener);
    }
    public class perOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back:
                    PersonalActivity.this.finish();
                    break;
                case R.id.head:
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, CODE_PHOTO_REQUEST);
                    break;
                case R.id.ly_name:
                    Intent userIntent = new Intent();
                    userIntent.setClass(PersonalActivity.this,NameActivity.class);
                    userIntent.putExtra("user",user);
                    startActivity(userIntent);
                    break;
                case R.id.ly_sex:
                    showSexDialog();
                    break;
                case R.id.ly_birth:
                    showBirthDialog();
                    break;
                case R.id.ly_address:
                    Intent addIntent = new Intent();
                    addIntent.setClass(PersonalActivity.this,AddressActivity.class);
                    addIntent.putExtra("content",user.getUserAddress());
                    addIntent.putExtra("msg","常住地");
                    startActivity(addIntent);
                    break;
                case R.id.ly_intro:
                    Intent introIntent = new Intent();
                    introIntent.setClass(PersonalActivity.this,AddressActivity.class);
                    introIntent.putExtra("content",user.getUserIntroduce());
                    introIntent.putExtra("msg","个性签名");
                    startActivity(introIntent);
                    break;
            }
        }
    }

    //性别选择器
    private void showSexDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择你的性别");
        View v = getLayoutInflater().inflate(R.layout.activity_sex,null);
        final ImageView manView = v.findViewById(R.id.iv_man);
        final ImageView womanView = v.findViewById(R.id.iv_woman);
        final TextView manText = v.findViewById(R.id.man);
        final TextView womanText = v.findViewById(R.id.woman);
        manView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manView.setImageResource(R.drawable.mans);
                womanView.setImageResource(R.drawable.woman);
                sex = manText.getText().toString();
            }
        });
        womanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manView.setImageResource(R.drawable.man);
                womanView.setImageResource(R.drawable.womans);
                sex = womanText.getText().toString();
            }
        });
        builder.setView(v);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    //把性别存到数据库里
                tv_sex.setText(sex);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //生日日期选择器
    private void showBirthDialog(){
        final PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ConstraintLayout.LayoutParams.MATCH_PARENT);
        View view = getLayoutInflater().inflate(R.layout.activity_birth,null);
        TextView okText = view.findViewById(R.id.tv_ok);
        TextView canaleText = view.findViewById(R.id.tv_cancle);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datepicker);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd");
                birth = format.format(calendar.getTime());
            }
        });
        popupWindow.setContentView(view);
        addBackgroundAlpha((float) 0.50);
        popupWindow.showAtLocation(rootLayout, Gravity.BOTTOM,0,0);
        okText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_birth.setText(birth);
                popupWindow.dismiss();
                addBackgroundAlpha((float) 1);
            }
        });
        canaleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                addBackgroundAlpha((float) 1);
            }
        });
    }
    private void addBackgroundAlpha(float alpha){
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = alpha;
        //设置参数
        getWindow().setAttributes(params);
    }

    //修改头像
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case CODE_PHOTO_REQUEST:
                if (data != null) {
                    startPhotoZoom(data.getData());
                }
                break;
            case CROP_SMALL_PICTURE:
                if (data != null) {
                    if (!cropUri.equals("")) {
                        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true);
                        Glide.with(this).load(cropUri).apply(mRequestOptions).into(iv_head);
                    }
                }
                UpLoadUtil upLoadUtil = new UpLoadUtil();
                upLoadUtil.execute(cropUri.getPath());
                break;
        }
    }

    //对图片进行裁剪
    private void startPhotoZoom(Uri uri) {
        if (!file.exists()) {
            file.mkdirs();
        }
        //保存裁剪后的图片
        cropFile = new File(Environment.getExternalStorageDirectory() + "/CoolImage", System.currentTimeMillis() + ".jpg");
        if (cropFile.exists()) {
            cropFile.delete();
            Log.e("delete", "delete");
        } else {
            try {
                cropFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cropUri = Uri.fromFile(cropFile);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        Log.e("cropUri = ", cropUri.toString());
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }
}
