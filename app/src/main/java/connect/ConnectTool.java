package connect;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import module.PasswordChange;
import module.RouteColloctionItem;
import module.Suggestion;
import module.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConnectTool {


    private Gson g;
    private OkHttpClient client;
    private MediaType JSON;


     public ConnectTool()
    {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        g=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        JSON= MediaType.parse("application/json; charset=utf-8");
    }


    //登录使用的连接工具类
    public String login(User user)
    {

        String loginUrl ="http://47.102.156.224/api/login";
        Log.d("登录链接", loginUrl);
        try {
            String json=g.toJson(user);
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder().url(loginUrl).post(body).build();
           // Request request = new Request.Builder().url(loginUrl).build();
            Response response = client.newCall(request).execute();
            String temp=response.body().string();
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    //路径收藏按钮
    public String routeCollectRequest(RouteColloctionItem rtIcom)
    {

        String rtCollectUrl ="http://47.102.156.224/api/path";
        try {
            String json=g.toJson(rtIcom);
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder().url(rtCollectUrl).post(body).build();
            // Request request = new Request.Builder().url(loginUrl).build();
            Response response = client.newCall(request).execute();
            String temp=response.body().string();
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


//    //登录使用的连接工具类
//    public String getSpotImformation(User user)
//    {
//        try {
//            String json=g.toJson(user);
//            RequestBody body = RequestBody.create(JSON, json);
//            Request request = new Request.Builder().url(loginUrl).post(body).build();
//            Response response = client.newCall(request).execute();
//            String temp=response.body().string();
//            return temp;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
    //}

    //建议
    public String suggestion(Suggestion su)
    {

        String Url ="http://47.102.156.224/api/path";
        try {
            String json=g.toJson(su);
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder().url(Url).post(body).build();
            // Request request = new Request.Builder().url(loginUrl).build();
            Response response = client.newCall(request).execute();
            String temp=response.body().string();
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//改密码
    public String changePwd(PasswordChange pa)
    {

        String Url ="http://47.102.156.224/api/path";
        try {
            String json=g.toJson(pa);
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder().url(Url).post(body).build();
            // Request request = new Request.Builder().url(loginUrl).build();
            Response response = client.newCall(request).execute();
            String temp=response.body().string();
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
