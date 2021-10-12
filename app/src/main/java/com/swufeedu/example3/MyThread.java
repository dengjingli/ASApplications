package com.swufeedu.example3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThread implements Runnable{
    private static final String TAG="MyThread";
    private Handler handler;
    public void setHandler(Handler handler){
        this.handler = handler;
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
            try {
                Document doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm ").get();
                Log.i(TAG, "run:title=" + doc.title());

                Elements h4s = doc.getElementsByTag("h4");
                for (Element h4 : h4s) {
                    Log.i(TAG, "run:h4=" + h4.text());
                }
                Elements tables = doc.getElementsByTag("table");
                Element table1 = tables.first();
                Log.i(TAG, "run:table=" + table1);

                Elements tds = table1.getElementsByTag("td");
                for (int i = 0; i < tds.size(); i += 6) {
                    Element td1 = tds.get(i);//货币名称i+=6
                    Element td2 = tds.get(i + 5);//折算价
                    String str1 = td1.text();
                    String val = td2.text();
                    Log.i(TAG, "run: " + str1 + "==> " + val);
                    float v = 100f / Float.parseFloat(val);
                    // 获取数据并返回 ……
                    bundle.putFloat(str1,v);

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
