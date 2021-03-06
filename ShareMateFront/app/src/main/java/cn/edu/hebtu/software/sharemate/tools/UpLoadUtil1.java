package cn.edu.hebtu.software.sharemate.tools;



import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class UpLoadUtil1  extends AsyncTask{
    private String path;

    public UpLoadUtil1(String path) {
        this.path = path;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String BOUNDARY = UUID.randomUUID().toString();
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";
        File file = new File((String) objects[0]);
        try {
            URL url = new URL(path+"TestServlet");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true); // 允许输入流
            con.setDoOutput(true); // 允许输出流
            con.setUseCaches(false); // 不允许使用缓存
            con.setRequestMethod("POST"); // 请求方式
            con.setRequestProperty("connection", "keep-alive");
            con.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            con.setRequestProperty("Charset", "UTF-8");
            Log.e("con","con");
            DataOutputStream dos = new DataOutputStream(con.getOutputStream());
            StringBuffer sb = new StringBuffer();
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINE_END);
            sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""
                    + file.getName() + "\"" + LINE_END);
            sb.append("Content-Type: application/octet-stream; charset=utf-8" + LINE_END);
            sb.append(LINE_END);
            Log.e("sb","sb");
            dos.write(sb.toString().getBytes());
            Log.e("is","is");
            InputStream is = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                dos.write(bytes, 0, len);
            }
            is.close();
            dos.write(LINE_END.getBytes());
            Log.e("dos","dos");
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                    .getBytes();
            dos.write(end_data);
            dos.flush();
            int res = con.getResponseCode();
            if (res == 200) {
                Log.e("test","上传成功");

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
