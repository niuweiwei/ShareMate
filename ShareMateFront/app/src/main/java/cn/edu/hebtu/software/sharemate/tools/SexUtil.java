package cn.edu.hebtu.software.sharemate.tools;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

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

import cn.edu.hebtu.software.sharemate.Activity.SelectInterestActivity;
import cn.edu.hebtu.software.sharemate.Bean.UserBean;

public class SexUtil extends AsyncTask {

    private Context context;
    private String path;
    public SexUtil(Context context,String path){
        this.context = context;
        this.path=path;
    }
    @Override
    protected Object doInBackground(Object[] objects) {
        Log.e("SexUtil","异步任务");
        UserBean user = (UserBean) objects[0];
        Log.e("SexUtil",user.getUserSex());
        Log.e("SexUtil",user.getUserBirth());
        try {
            URL url = new URL(path+"StartServlet");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter writer = new BufferedWriter(osw);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userSex",user.getUserSex())
                        .put("userBirth",user.getUserBirth());
            String str = jsonObject.toString();
            writer.write(str);
            writer.flush();
            writer.close();
            connection.connect();

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String str2 = reader.readLine();
            JSONObject result = new JSONObject(str2);
            String message = result.getString("msg");
            int userId = result.getInt("userId");
            reader.close();
            Log.e("result",message+" "+userId);
            Log.e("SexUtil","性别和生日上传成功");
            Intent intent = new Intent(context,SelectInterestActivity.class);
            intent.putExtra("userId",userId);
            context.startActivity(intent);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
