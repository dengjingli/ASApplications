package com.swufeedu.example3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Huilv extends AppCompatActivity implements Runnable{
    private static final String TAG="HuiActivity";
    private float dollarRate = 0.15f;
    private float eurRate = 0.13f;
    TextView show;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huilv);

        //读取保存的数据
        SharedPreferences sp=getSharedPreferences("myrate",Activity.MODE_PRIVATE);
        dollarRate=sp.getFloat("dollar_rate",0.1f);
        eurRate=sp.getFloat("eur_rate",0.1f);
        Log.i(TAG,"onCreate:get from sp dollar="+dollarRate);
        Log.i(TAG,"onCreate:get from sp eur="+eurRate);
        //开启线程
        handler=new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                Log.i(TAG,"handleMessge:接收消息");
                if(msg.what==6){
                    String str=(String)msg.obj;
                    Log.i(TAG,"handleMessage:str="+str);
                }
                super.handleMessage(msg);
            }
        };
        Thread t = new Thread(this);
        t.start();

    }
    public void huiclick(View btn){
        Log.i(TAG,"click");
        EditText inputText = findViewById(R.id.input);
        String text =inputText.getText().toString();
        if(text!=null&&text.length()>0){
            float num =Float.parseFloat(text);
            float res = 0;
            if(btn.getId()==R.id.butdollar){
                res = num*dollarRate;
            }
            else if(btn.getId()==R.id.button3){
                res = num*eurRate;
            }
            show = findViewById(R.id.result);
            show.setText(String.valueOf(res));
        }
    }
    public void openConfig(View btn) {
        config();
    }

    private void config() {
        Intent config =new Intent(this,Config.class);
        config.putExtra("dollar_rate_key",dollarRate);
        config.putExtra("eur_rate_key",eurRate);
        //startActivity(config);
        startActivityForResult(config,1);
    }

    @Override
    protected void onActivityResult (int requestCode,int resultCode,@NonNull Intent data){
        if(requestCode==1&&resultCode==2){
            Bundle bundle = data.getExtras();
            dollarRate=bundle.getFloat("dollar_key",0.1f);
            eurRate=bundle.getFloat("eur_key",0.2f);

            //保存数据到sp
            SharedPreferences sp=getSharedPreferences("myrate",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor =sp.edit();
            editor.putFloat("dollar_rate",dollarRate);
            editor.putFloat("eur_rate",eurRate);
            editor.apply();
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId()==R.id.menu_setting){
            Log.i(TAG,"onOptionsItemSelected:setting");
            config();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG,"run:rum()......");

        //获取网络数据
        URL url =null;

        try {
            url = new URL("https://www.usd-cny.com/bankofchina.htm");
            HttpURLConnection http =(HttpURLConnection)url.openConnection();
            InputStream in = http.getInputStream();

            String html = inputStream2String(in);
            Log.i(TAG,"run:html="+html);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //发送消息给主线程
        Message msg=handler.obtainMessage();
        msg.what=6;
        msg.obj="Hello from run";
        handler.sendMessage(msg);
        Log.i(TAG,"run:消息已发送");
    }
    private String inputStream2String(InputStream inputStream)
        throws IOException{
        final int bufferSize =1024;
        final char[] buffer=new char[bufferSize];
        final  StringBuilder out =new StringBuilder();
        Reader in = new InputStreamReader(inputStream,"gb2312");
        while (true){
            int rsz=in.read(buffer,0,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }
}