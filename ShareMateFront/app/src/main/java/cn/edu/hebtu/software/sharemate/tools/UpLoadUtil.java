package cn.edu.hebtu.software.sharemate.tools;



import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

public class UpLoadUtil  extends AsyncTask{
    @Override
    protected Object doInBackground(Object[] objects) {
        String BOUNDARY = UUID.randomUUID().toString();
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";
        File file = new File((String) objects[0]);
        Map<String,Object> paramMap= (Map<String, Object>) objects[1];
        try {
            URL url = new URL("http://10.7.89.233:8080/sharemate/HeadServlet");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true); // 允许输入流
            con.setDoOutput(true); // 允许输出流
            con.setUseCaches(false); // 不允许使用缓存
            con.setRequestMethod("POST"); // 请求方式
            con.setRequestProperty("connection", "keep-alive");//设置Android端到服务器端连接持续有效
            con.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            con.setRequestProperty("Charset", "UTF-8");//编码方式
            DataOutputStream dos = new DataOutputStream(con.getOutputStream());

            //上传文本 ，在for循环中拼接报文，上传文本数据
            StringBuilder text = new StringBuilder();
            for(Map.Entry<String,Object> entry : paramMap.entrySet()) {
                text.append("--");
                text.append(BOUNDARY);
                text.append("\r\n");
                text.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"\r\n\r\n");
                text.append(entry.getValue());
                text.append("\r\n");
            }
            dos.write(text.toString().getBytes("utf-8")); //写入文本数据

            //上传图片
            StringBuffer sb = new StringBuffer();
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINE_END);
            sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""
                    + file.getName() + "\"" + LINE_END);
            sb.append("Content-Type: application/octet-stream; charset=utf-8" + LINE_END);
            sb.append(LINE_END);
            dos.write(sb.toString().getBytes());
            InputStream is = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                dos.write(bytes, 0, len);
            }
            is.close();
            dos.write(LINE_END.getBytes());
            // 请求结束标志
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
            dos.write(end_data);
            dos.flush();
            dos.close();

            //返回响应码
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
