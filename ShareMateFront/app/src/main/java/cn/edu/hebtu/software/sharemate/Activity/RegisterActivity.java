package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.edu.hebtu.software.sharemate.Bean.UserBean;
import cn.edu.hebtu.software.sharemate.R;
import cn.edu.hebtu.software.sharemate.tools.IdentifyingCode;
import cn.edu.hebtu.software.sharemate.tools.PasswordUtils;
import cn.edu.hebtu.software.sharemate.tools.TelephoneUtils;
import cn.edu.hebtu.software.sharemate.tools.UpLoadUtil;
import cn.edu.hebtu.software.sharemate.tools.UsernameUtils;

public class RegisterActivity extends AppCompatActivity {

    private static final int OPEN_ALBUM = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    private ImageView back;
    private Button btnTrue;
    private ImageView ivCode;
    private EditText etCode;
    private String realCode;
    private ImageView head;
    private PopupWindow popupWindow;
    private LinearLayout rootLinear;
    private Button btnOpen;
    private Button btnCancle;
    private File file;
    private Uri cropUri;
    private File cropFile;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etPhone;
    private EditText etConfirmPawd;
    private String userName;
    private String userPassword;
    private String userPhone;
    private String confirmPawd;
    private UserBean user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //创建保存图片的路径
        file = new File(Environment.getExternalStorageDirectory() + "/CoolImage/");
        findViews();
        back.setOnClickListener(new backClickListener());
        ivCode.setOnClickListener(new idtfCodeClickListener());
//        head.setOnClickListener(new photoClickListener());
        judgeForm();

    }

    private void findViews() {
        back = findViewById(R.id.iv_back);
        btnTrue = findViewById(R.id.btn_true);
        ivCode = findViewById(R.id.iv_showCode);
        etCode = findViewById(R.id.et_phoneCodes);
        head = findViewById(R.id.head);
        rootLinear = findViewById(R.id.root);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etPhone = findViewById(R.id.et_phone);
        etConfirmPawd = findViewById(R.id.et_confirm_password);
    }
    /**
     * 判断输入的数据格式是否正确
     */
    private void judgeForm() {
        user = new UserBean();
        etUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    userName = etUsername.getText().toString();
                    //判断用户名格式,限16个字符，支持中英文、数字、减号或下划线
                    boolean resultName = UsernameUtils.isName(userName);
                    Log.e("userName",userName);
                    Log.e("resultName",resultName+"");
                    if (resultName == false) {
                        Toast.makeText(RegisterActivity.this,"请输入16个字符以内的用户名，支持中英文、数字、减号或下划线",
                                                        Toast.LENGTH_SHORT).show();
//                        etUsername.setError("有错误信息");
                    } else {
                        user.setUserName(userName);
                    }
                }
            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    userPassword = etPassword.getText().toString();
                    //判断密码格式,8-16位数字和字母
                    boolean resultPassword = PasswordUtils.isPassword(userPassword);
                    Log.e("userPassword",userPassword);
                    Log.e("resultPassword",resultPassword+"");
                    if (resultPassword == false) {
                        Toast.makeText(RegisterActivity.this, "请输入8-16位由数字和字母组成的密码",
                                                Toast.LENGTH_SHORT).show();
//                        etPassword.setError("密码错误");
                    } else {
                        user.setUserPassword(userPassword);
                    }
                }
            }
        });
        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    userPhone = etPhone.getText().toString();
                    //判断手机号码格式,11位数字
                    boolean resultPhone = TelephoneUtils.isPhone(userPhone);
                    Log.e("userPhone",userPhone);
                    Log.e("resultPhone",resultPhone+"");
                    if (resultPhone == false) {
                        Toast.makeText(RegisterActivity.this, "请输入正确格式的手机号",
                                                Toast.LENGTH_SHORT).show();
//                        etPhone.setError("手机号码错误");
                    } else {
                        user.setUserPhone(userPhone);
                    }
                }
            }
        });
        etConfirmPawd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    confirmPawd = etConfirmPawd.getText().toString();
                    Log.e("confirmPawd",confirmPawd);
                    if (userPassword.equals(confirmPawd)) {
                        btnTrue.setOnClickListener(new ButtonClickListener());
                    } else {
                        Toast.makeText(RegisterActivity.this, "两次密码输入不一样", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    /**
     * 异步任务
     */
    public class RegisterUtil extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            Log.e("RegisterUtil", "异步任务");
            UserBean user = (UserBean) objects[0];
            String result = null;
            try {
                URL url = new URL(getResources().getString(R.string.server_path)+"RegisterFontServlet");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true); // 允许输入流
                connection.setDoOutput(true); // 允许输出流
                connection.setRequestMethod("POST");//请求方式
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");//设定传送的内容类型是可序列化的java对象
                connection.setRequestProperty("Charset", "UTF-8");
                OutputStream outputStream = connection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                BufferedWriter writer = new BufferedWriter(outputStreamWriter);
                JSONObject userJSON = new JSONObject();
                userJSON.put("userName", user.getUserName())
                        .put("userPassword", user.getUserPassword())
                        .put("userPhone", user.getUserPhone());
                String str = userJSON.toString();
                writer.write(str);
                writer.flush();
                writer.close();
                //向服务器端发送数据，请求服务端
                connection.connect();
                //通过输入流来获取响应
                InputStream inputStream = connection.getInputStream();
                //字节流到字符流的转换流
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                //字符缓冲输入流
                BufferedReader reader = new BufferedReader(inputStreamReader);
                //服务器返回结果
                result = reader.readLine();
                reader.close();
                inputStream.close();
                inputStream.close();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Object o) {
            String result = (String)o;
            Log.e("RegisterUtil",result);
            if(result.equals("插入成功")){
                Log.e("RegisterUtil", "文字上传成功");
                Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
                startActivity(intent);
            }else if (result.equals("该用户已注册")){
                Toast.makeText(RegisterActivity.this,"该用户已注册",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 点击返回
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
            String ImageCode = etCode.getText().toString().toLowerCase();
            if (ImageCode.equals(realCode)) {
//                Toast.makeText(RegisterActivity.this, ImageCode + "验证码正确", Toast.LENGTH_SHORT).show();

                //上传数据
                btnTrue.setFocusable(true);//设置可以获取焦点，但不一定获得
                btnTrue.setFocusableInTouchMode(true);
                btnTrue.requestFocus();//要获取焦点
                if (!userName.equals("") && !userPassword.equals("") && !userPhone.equals("")) {
                    RegisterUtil registerUtil = new RegisterUtil();
                    registerUtil.execute(user);
                    Log.e("RegisterActivity", "上传数据");
                } else {
                    Toast.makeText(RegisterActivity.this, "请输入信息", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, ImageCode + "验证码错误", Toast.LENGTH_SHORT).show();
            }
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
     * 以下方法在实现头像文字一起上传前先不实现
     */

    /**
     * 上传头像
     */
    private class photoClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            popupWindow = new PopupWindow(RegisterActivity.this);
            popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
            View view = getLayoutInflater().inflate(R.layout.upload_head, null);
            btnOpen = view.findViewById(R.id.btn_open);
            btnCancle = view.findViewById(R.id.btn_cancle);
            //打开手机相册
            btnOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, OPEN_ALBUM);
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
            popupWindow.showAtLocation(rootLinear, Gravity.CENTER, 0, 0);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //用户没有进行有效地设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode) {
            //从相册中获取到图片了，才执行裁剪动作
            case OPEN_ALBUM:
                if (data != null) {
                    startPhotoZoom(data.getData());
                }
                Log.e("data", data.getData().getPath() + "");
                Log.e("DATA", data + "");
                break;
            //裁剪后的返回值
            case RESULT_REQUEST_CODE:
                if (data != null) {
                    setImageToHeadView(data);
                }
                String path = cropUri.getPath();
                getUpLoadUtil(path);
                Log.e("RegisterActivity", "上传头像");
                break;
        }
    }

    /**
     * 对图片进行裁剪
     *
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        Log.e("uri", uri.toString());
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
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300); // 输出图片大小
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        Log.e("cropUri = ", cropUri.toString());
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, RESULT_REQUEST_CODE);//这里的RESULT_REQUEST_CODE是在startActivityForResult里使用的返回值。
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的view
     */
    private void setImageToHeadView(Intent intent) {
        if (!cropUri.equals("")) {
            RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true);
            Glide.with(this).load(cropUri).apply(mRequestOptions).into(head);
        }
    }

    private void getUpLoadUtil(String path) {
        UpLoadUtil uploadUtil = new UpLoadUtil();
        uploadUtil.execute(path);
    }
}
