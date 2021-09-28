package com.swufeedu.example3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Config extends AppCompatActivity {
    TextView dollarText,eurText;
    private static final String TAG="ConfigActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);

        Intent intent =getIntent();
        float dollar2 =intent.getFloatExtra("dollar_rate_key",0.0f);
        float eur2 =intent.getFloatExtra("eur_rate_key",0.0f);
        Log.i(TAG,"onCreate:dollar2="+dollar2);
        Log.i(TAG,"onCreate:dollar2="+eur2);
        dollarText=findViewById(R.id.dollor);
        eurText=findViewById(R.id.eur);
        //将汇率填入控件中
        dollarText.setText(String.valueOf(dollar2));
        eurText.setText(String.valueOf(eur2));
    }
    public void save(View btn) {
        //重新获取新的汇率数据
        float dollar =Float.parseFloat(dollarText.getText().toString());
        float eur =Float.parseFloat(eurText.getText().toString());

        Intent first=getIntent();
        first.putExtra("dollar_key",dollar);
        first.putExtra("eur_key",eur);

        setResult(2,first);
        finish();
    }
}