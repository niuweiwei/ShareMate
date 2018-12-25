package cn.edu.hebtu.software.sharemate.tools;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.edu.hebtu.software.sharemate.R;

public class SelectInterestUtil extends AsyncTask {

    private Context context;
    private int userId;
    private int typeId;
    private String remark;
    private String path;
    public SelectInterestUtil(Context context,String path){
        this.path=path;
        this.context = context;
    }
    @Override
    protected Object doInBackground(Object[] objects) {
        Log.e("SelectInterestUtil","异步任务");
        userId = (int)objects[0];
        typeId = (int)objects[1];
        remark = (String)objects[2];
        Log.e("userId,typeId",userId + " "+ typeId + " " + remark);
        try {
            URL url = new URL(path+"SelectInterestServlet?" +
                    "userId="+userId+"&typeId="+typeId+"&remark="+remark);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Charset","UTF-8");

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String result = reader.readLine();
            Log.e("result",result);
//            Intent intent = new Intent(context,SelectTopicActivity.class);
//            intent.putExtra("userId",userId);
//            context.startActivity(intent);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
