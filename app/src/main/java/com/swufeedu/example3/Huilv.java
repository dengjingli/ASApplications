package com.swufeedu.example3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Huilv extends AppCompatActivity {
    private static final String TAG="HuiActivity";
    private float dollarRate = 0.15f;
    private float eurRate = 0.13f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huilv);
    }
    public void click(View btn){
        Log.i(TAG,"click");
        EditText inputText = findViewById(R.id.input);
        String text =inputText.getText().toString();
        if(text!=null&&text.length()>0){
            float num =Float.parseFloat(text);
            float res = 0;
            if(btn.getId()==R.id.button2){
                res = num*dollarRate;
            }
            else if(btn.getId()==R.id.button3){
                res = num*eurRate;
            }

            TextView show = findViewById(R.id.result);
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
    protected void onActivityResult (int requestCode,int resultCode,Intent data){
        if(requestCode==1&&resultCode==2){
            Bundle bundle = data.getExtras();
            dollarRate=bundle.getFloat("key_dollar",0.1f);
            eurRate=bundle.getFloat("key_eur",0.1f);
        }
        super.onActivityResult(requestCode,resultCode,data);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.menu_setting){
            Log.i(TAG,"onOptionsItemSelected:setting");
            config();
        }
        return super.onOptionsItemSelected(item);
    }
}