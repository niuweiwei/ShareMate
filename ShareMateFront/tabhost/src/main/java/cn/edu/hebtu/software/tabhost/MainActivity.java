package cn.edu.hebtu.software.tabhost;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;
    private Handler handler;
    private String content = null;
    List<Person> persons = new ArrayList<Person>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        textView = findViewById(R.id.text);
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginTask loginTask = new loginTask();
                loginTask.execute();
            }
        });

    }

    public class loginTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                URL url = new URL("http://10.7.89.193:8080/bookshop/loginservlet?username=张三&password=123");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("contentType", "UTF-8");
                InputStream is = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String res = reader.readLine();
                //解析Json字符串
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray array = jsonObject.getJSONArray("persons");
                    for (int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        Person person = new Person();
                        person.setUserName(object.getString("userName"));
                        person.setUserPassword(object.getString("userPassword"));
                        person.setUserSex(object.getString("userSex"));
                        person.setUserPhoto(object.getString("userPhoto"));
                        persons.add(person);
                    }
                    content=persons.get(1).getUserPhoto();
                    handler.post(runnable);
                    Log.e("LoginTask", persons.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            textView.setText("the Content is:" + content);
        }
    };
}
