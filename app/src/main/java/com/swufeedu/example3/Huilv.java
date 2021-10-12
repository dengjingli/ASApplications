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
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Huilv extends AppCompatActivity implements Runnable{
    private static final String TAG="HuiActivity";
    private float dollarRate = 0.15f;
    private float eurRate = 0.13f;
    TextView show;
    Handler handler;
    String todayTime;

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
//                    String str=(String)msg.obj;
//                    Log.i(TAG,"handleMessage:str="+str);
                    Bundle bdl=(Bundle)msg.obj;
                    dollarRate=bdl.getFloat("r1");
                    eurRate=bdl.getFloat("r2");

                    Log.i(TAG,"handleMessage:dollarRate="+dollarRate);
                    Log.i(TAG,"handleMessage:eurRate="+eurRate);

                    //保存在sp……
                    SharedPreferences sp=getSharedPreferences("myrate",Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sp.edit();
                    editor.putFloat("dollar_rate",dollarRate);
                    editor.putFloat("eur_rate",eurRate);
                    editor.apply();


                    //提示
                    Toast.makeText(Huilv.this,"数据已更新",Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };
        //开启线程
        MyThread td=new MyThread();
        td.setHandler(handler);

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
        Log.i(TAG,"run:run()......");

        //获取网络数据
        URL url =null;

        Bundle bundle=new Bundle();
        SharedPreferences preferences = getSharedPreferences("LastLoginTime", MODE_PRIVATE);
        String lastTime = preferences.getString("LoginTime", "2021-10-10");
        // Toast.makeText(MainActivity.this, "value="+date, Toast.LENGTH_SHORT).show();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
        todayTime = df.format(new Date());// 获取当前的日期

        if (lastTime.equals(todayTime)) { //如果两个时间段相等
            Log.i(TAG,"不是当日首次登陆Time："+lastTime);
        } else {
            Log.i(TAG,"上次登陆时间date："+lastTime);
            Log.i(TAG,"这是当日首次登陆todayDate："+todayTime);
            try {
                //url = new URL("https://www.usd-cny.com/bankofchina.htm");
                //HttpURLConnection http =(HttpURLConnection)url.openConnection();
                //InputStream in = http.getInputStream();

                //String html = inputStream2String(in);
                //Log.i(TAG,"run:html="+html);

                Document doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm ").get();
                Log.i(TAG, "run:title=" + doc.title());

                Elements h4s = doc.getElementsByTag("h4");
                for (Element h4 : h4s) {
                    Log.i(TAG, "run:h4=" + h4.text());
                }
                Elements tables = doc.getElementsByTag("table");
                Element table1 = tables.first();
                Log.i(TAG, "run:table=" + table1);

//            Elements hrefs = table1.getElementsByTag("a");
//            for(Element a:hrefs){
//                Log.i(TAG,"run:a="+a.text());
//            }

//            Elements trs = table1.getElementsByTag("tr");
//            for(Element tr:trs){
//                Log.i(TAG,"run:tr="+tr);
//            }

                Elements tds = table1.getElementsByTag("td");
                for (int i = 0; i < tds.size(); i += 6) {
                    Element td1 = tds.get(i);//货币名称i+=6
                    Element td2 = tds.get(i + 5);//折算价
                    String str1 = td1.text();
                    String val = td2.text();
                    //Log.i(TAG, "run: " + str1 + "==> " + val);
                    //float v = 100f / Float.parseFloat(val);
                    // 获取数据并返回 ……
                    if (str1.equals("美元")) {
                        bundle.putFloat("r1", 100f / Float.parseFloat(val));
                    } else if (str1.equals("欧元")) {
                        //float v=100f/Float.parseFloat(val);
                        bundle.putFloat("r2", 100f / Float.parseFloat(val));
                        //Log.i(TAG,str1+"=>> "+v+"\n");
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //发送消息给主线程
//        Message msg=handler.obtainMessage();
//        msg.what=6;
//        msg.obj="Hello from run";
            Message msg = handler.obtainMessage(6, bundle);
            handler.sendMessage(msg);
            Log.i(TAG, "run:消息已发送");
        }
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

    //模板
    //判断是否是当日第一次打开APP
    private void isTodayFirstLogin() {
        SharedPreferences preferences = getSharedPreferences("LastLoginTime", MODE_PRIVATE);
        String lastTime = preferences.getString("LoginTime", "2021-10-10");
        // Toast.makeText(MainActivity.this, "value="+date, Toast.LENGTH_SHORT).show();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
        todayTime = df.format(new Date());// 获取当前的日期

        if (lastTime.equals(todayTime)) { //如果两个时间段相等
            Toast.makeText(this, "不是当日首次登陆", Toast.LENGTH_SHORT).show();
            Log.e("Time", lastTime);
        } else {
            Toast.makeText(this, "当日首次登陆", Toast.LENGTH_SHORT).show();
            run();
            Log.e("date", lastTime);
            Log.e("todayDate", todayTime);
        }
    }

    //然后退出时间保存 这里是在onDestroy()的时候进行保存
    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveExitTime(todayTime);
    }

    /**
     * 保存每次退出的时间
     * @param extiLoginTime
     */
    private void saveExitTime(String extiLoginTime) {
        SharedPreferences.Editor editor = getSharedPreferences("LastLoginTime", MODE_PRIVATE).edit();
        editor.putString("LoginTime", extiLoginTime);
        //这里用apply()而没有用commit()是因为apply()是异步处理提交，不需要返回结果，而我也没有后续操作
        //而commit()是同步的，效率相对较低
        //apply()提交的数据会覆盖之前的,这个需求正是我们需要的结果
        editor.apply();
    }
}