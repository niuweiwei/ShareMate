package cn.edu.hebtu.software.sharemate;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.edu.hebtu.software.sharemate.Bean.UserBean;

//将修改后的用户数据保存到数据库中
public class SaveUser extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] objects) {
        UserBean userBean = (UserBean) objects[0];
        try {
            JSONObject userObject = new JSONObject();
            userObject.put("userId",userBean.getUserId());
            userObject.put("userName",userBean.getUserName());
            userObject.put("userSex",userBean.getUserSex());
            userObject.put("userBirth",userBean.getUserBirth());
            userObject.put("userAddress",userBean.getUserAddress());
            userObject.put("userIntro",userBean.getUserIntroduce());
            String content = String.valueOf(userObject);
            URL url = new URL("http://10.7.89.233:8080/sharemate/SaveUserServlet");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("contentType", "UTF-8");
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(content);
            bw.flush();
            bw.close();
            if(urlConnection.getResponseCode()==200){
                Log.e("test","上传成功");
            }
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
