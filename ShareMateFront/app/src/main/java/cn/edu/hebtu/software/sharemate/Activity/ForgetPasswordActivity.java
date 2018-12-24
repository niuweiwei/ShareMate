package cn.edu.hebtu.software.sharemate.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import cn.edu.hebtu.software.sharemate.tools.ForgetPasswordUtil;
import cn.edu.hebtu.software.sharemate.tools.GetIpConfig;
import cn.edu.hebtu.software.sharemate.tools.TelephoneUtils;

public class ForgetPasswordActivity extends AppCompatActivity {

    private TextView tvSpinner;
    private Button btnSpinner;
    private ImageView back;
    private Button btnTrue;//发送验证码
    private EditText etPhoneNum;//电话号码
    private String phoneNum;
    private UserBean user = new UserBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        findViews();

        back.setOnClickListener(new backClickListener());
        btnTrue.setOnClickListener(new ButtonClickListener());
        btnSpinner.setOnClickListener(new SpinnerClickListener());
        tvSpinner.setOnClickListener(new SpinnerClickListener());
    }
    private void findViews(){
        back = findViewById(R.id.iv_back);
        btnTrue = findViewById(R.id.btn_true);
        btnSpinner = findViewById(R.id.btn_spinner);
        tvSpinner = findViewById(R.id.tv_spinner);
        etPhoneNum = findViewById(R.id.et_phone);
    }
    //选择地区和地区代码
    private class SpinnerClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ForgetPasswordActivity.this,CountryActivity.class);
            startActivityForResult(intent, 12);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode)
        {
            case 12:
                if (resultCode == RESULT_OK)
                {
                    Bundle bundle = data.getExtras();
//                    String countryName = bundle.getString("countryName");
                    String countryNumber = bundle.getString("countryNumber");

                    tvSpinner.setText(countryNumber);
//                    tv1.setText(countryName);

                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //点击返回
    private class backClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ForgetPasswordActivity.this,PhonePswdLoginActivity.class);
            startActivity(intent);
        }
    }
    //点击确定
    private class ButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            phoneNum = etPhoneNum.getText().toString();
            if (!phoneNum.equals("")) {
                //判断手机号码格式,11位数字
                boolean resultPhone = TelephoneUtils.isPhone(phoneNum);
                if (resultPhone == true) {
                    user.setUserPhone(phoneNum);
                    ForgetPasswordUtil forgetPasswordUtil = new ForgetPasswordUtil();
                    forgetPasswordUtil.execute(user);
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
                    etPhoneNum.requestFocus();
                }
            }else {
                Toast.makeText(ForgetPasswordActivity.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                etPhoneNum.requestFocus();
            }

        }
    }
    /**
     * 异步任务
     */
    public class ForgetPasswordUtil extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            Log.e("ForgetPasswordUtil","异步任务");
            UserBean user = (UserBean) objects[0];
            String userPhone = user.getUserPhone();
            String ip = GetIpConfig.getIp();
            JSONObject back = null;
            try {
                URL url = new URL("http://"+ ip +":8080/sharemate/ForgetPasswordServlet");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Charset","UTF-8");
                OutputStream os = connection.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter writer = new BufferedWriter(osw);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userPhone",userPhone);
                String str = jsonObject.toString();
                writer.write(str);
                writer.flush();
                writer.close();
                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isr);
                String str2 = reader.readLine();
                back = new JSONObject(str2);
                reader.close();
                isr.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return back;
        }

        @Override
        protected void onPostExecute(Object o) {
            JSONObject back = (JSONObject) o;
            String result = null;
            try {
                result = back.getString("msg");
                Log.e("result",result);
                if (result.equals("用户存在")){
                    int userId = back.getInt("userId");
                    String userPhone = back.getString("userPhone");
                    Intent intent = new Intent(ForgetPasswordActivity.this,VerifyCodeActivity.class);
                    intent.putExtra("userId",userId);
                    intent.putExtra("userPhone",userPhone);
                    startActivity(intent);
                }else if(result.equals("用户不存在")){
                    Toast.makeText(ForgetPasswordActivity.this,"该用户不存在",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
