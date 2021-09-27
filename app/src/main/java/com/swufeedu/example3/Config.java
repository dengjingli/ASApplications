package com.swufeedu.example3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Config extends AppCompatActivity {
    TextView dollarText,eurText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);

        Intent intent =getIntent();
        float dollar2 =intent.getFloatExtra("dollar_rate_key",0.0f);
        float eur2 =intent.getFloatExtra("eur_rate_key",0.0f);
        dollarText=findViewById(R.id.dollor);
        eurText=findViewById(R.id.eur);
        dollarText.setText(String.valueOf(dollar2));
        eurText.setText(String.valueOf(eur2));
    }
    public void save(View btn) {
        float dollar =Float.parseFloat(dollarText.getText().toString());
        float eur =Float.parseFloat(eurText.getText().toString());
        Intent first=getIntent();
        first.putExtra("dollar_key",dollar);
        first.putExtra("eur_key",eur);

        setResult(2,first);
        finish();
    }
}