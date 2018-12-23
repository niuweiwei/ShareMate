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
import java.util.HashMap;
import java.util.Map;

import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.SaveUser;
import cn.edu.hebtu.software.sharemate.tools.UpLoadUtil;

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
    private String sex;
    private String birth;
    private UserBean user;
    private static final int CODE_PHOTO_REQUEST = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    private String sign;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        path = getResources().getString(R.string.server_path);
        file = new File(Environment.getExternalStorageDirectory() + "/CoolImage/");
        rootLayout = findViewById(R.id.root);
        user = (UserBean) getIntent().getSerializableExtra("user");
        sign = getIntent().getStringExtra("sign");
        findView();
        setContent();
        setListener();
    }

    private void findView() {
        iv_back = findViewById(R.id.back);
        iv_head = findViewById(R.id.head);
        tv_name = findViewById(R.id.user);
        tv_id = findViewById(R.id.num);
        tv_sex = findViewById(R.id.sex);
        tv_birth = findViewById(R.id.birth);
        tv_address = findViewById(R.id.address);
        tv_introduce = findViewById(R.id.introduction);
        layoutName = findViewById(R.id.ly_name);
        layoutAddress = findViewById(R.id.ly_address);
        layoutBirth = findViewById(R.id.ly_birth);
        layoutIntro = findViewById(R.id.ly_intro);
        layoutSex = findViewById(R.id.ly_sex);
    }
    public void setContent(){
        tv_address.setText(user.getUserAddress());
        tv_birth.setText(user.getUserBirth());
        tv_sex.setText(user.getUserSex());
        String userId = String.format("%06d",user.getUserId());
        tv_id.setText(userId);
        tv_name.setText(user.getUserName());
        String photoPath = user.getUserPhotoPath();
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(this).load(photoPath).apply(mRequestOptions).into(iv_head);
        if (user.getUserIntroduce() == null || user.getUserIntroduce().length() < 7) {
            tv_introduce.setText(user.getUserIntroduce());
        } else {
            tv_introduce.setText(user.getUserIntroduce().substring(0, 6) + "...");
        }
    }

    private void setListener() {
        perOnClickListener listener = new perOnClickListener();
        iv_head.setOnClickListener(listener);
        iv_back.setOnClickListener(listener);
        layoutName.setOnClickListener(listener);
        layoutSex.setOnClickListener(listener);
        layoutIntro.setOnClickListener(listener);
        layoutBirth.setOnClickListener(listener);
        layoutAddress.setOnClickListener(listener);
    }

    public class perOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.head:
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, CODE_PHOTO_REQUEST);
                    break;
                case R.id.ly_name:
                    Intent userIntent = new Intent();
                    userIntent.setClass(PersonalActivity.this, NameActivity.class);
                    userIntent.putExtra("user", user);
                    startActivityForResult(userIntent,3);
                    break;
                case R.id.ly_sex:
                    showSexDialog();
                    break;
                case R.id.ly_birth:
                    showBirthDialog();
                    break;
                case R.id.ly_address:
                    Intent addIntent = new Intent();
                    addIntent.setClass(PersonalActivity.this, AddressActivity.class);
                    addIntent.putExtra("user", user);
                    addIntent.putExtra("msg", "常住地");
                    startActivityForResult(addIntent,4);
                    break;
                case R.id.ly_intro:
                    Intent introIntent = new Intent();
                    introIntent.setClass(PersonalActivity.this, AddressActivity.class);
                    introIntent.putExtra("user", user);
                    introIntent.putExtra("msg", "个性签名");
                    startActivityForResult(introIntent,5);
                    break;
                case R.id.back:
                    SaveUser saveUser = new SaveUser();
                    saveUser.execute(user);
                    if("my".equals(sign)){
                        Intent myIntent = new Intent(PersonalActivity.this,MainActivity.class);
                        myIntent.putExtra("flag","my");
                        startActivity(myIntent);
                    }else if("set".equals(sign)){
                        Intent myIntent = new Intent(PersonalActivity.this,SettingActivity.class);
                        myIntent.putExtra("user",user);
                        startActivity(myIntent);
                    }
            }
        }
    }

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
                //文本数据全部添加到Map里
                final Map<String,Object> paramMap = new HashMap<>();
                paramMap.put("userId",user.getUserId());
                UpLoadUtil upLoadUtil = new UpLoadUtil();
                upLoadUtil.execute(cropUri.getPath(),paramMap);
                break;
        }
        if(requestCode == 3 && resultCode == 200){
            user = (UserBean)data.getSerializableExtra("responseUser");
            setContent();
        }
        if(requestCode == 4 && resultCode == 200){
            user = (UserBean)data.getSerializableExtra("responseUser");
            setContent();
        }
        if(requestCode == 5 && resultCode == 200){
            user = (UserBean)data.getSerializableExtra("responseUser");
            setContent();
        }
    }
    //性别选择器
    private void showSexDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择你的性别");
        View v = getLayoutInflater().inflate(R.layout.activity_sex, null);
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
                tv_sex.setText(sex);
                user.setUserSex(sex);
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
    private void showBirthDialog() {
        final PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ConstraintLayout.LayoutParams.MATCH_PARENT);
        View view = getLayoutInflater().inflate(R.layout.activity_birth, null);
        TextView okText = view.findViewById(R.id.tv_ok);
        TextView canaleText = view.findViewById(R.id.tv_cancle);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datepicker);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd");
                birth = format.format(calendar.getTime());
            }
        });
        popupWindow.setContentView(view);
        addBackgroundAlpha((float) 0.50);
        popupWindow.showAtLocation(rootLayout, Gravity.BOTTOM, 0, 0);
        okText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_birth.setText(birth);
                user.setUserBirth(birth);
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

    //修改activity的透明度
    private void addBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = alpha;
        getWindow().setAttributes(params);
    }


    //修改头像    对图片进行裁剪
    private void startPhotoZoom(Uri uri) {
        if (!file.exists()) {
            file.mkdirs();
        }
        cropFile = new File(Environment.getExternalStorageDirectory() + "/CoolImage", System.currentTimeMillis() + ".jpg");
        if (cropFile.exists()) {
            cropFile.delete();
        }else{
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
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }
}
