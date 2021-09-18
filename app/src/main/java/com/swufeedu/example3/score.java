package com.swufeedu.example3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class score extends AppCompatActivity {
    private static final String TAG="BallActivity";
    int score=0;
    int scorea=0;
    int scoreb=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vsscore);
    }
    public void clicka(View btn){
        Log.i(TAG,"clicka");
        if(btn.getId()==R.id.btna3){
            scorea+=3;
        }
        else if(btn.getId()==R.id.btna2){
            scorea+=2;
        }
        else if(btn.getId()==R.id.btna1){
            scorea+=1;
        }
        TextView out1 = findViewById(R.id.scorea);
        out1.setText(String.valueOf(scorea));

    }
    public void clickb(View btn){
        Log.i(TAG,"clickb");
        if(btn.getId()==R.id.btnb3){
            scoreb+=3;
        }
        else if(btn.getId()==R.id.btnb2){
            scoreb+=2;
        }
        else if(btn.getId()==R.id.btnb1){
            scoreb+=1;
        }
        TextView out2 = findViewById(R.id.scoreb);
        out2.setText(String.valueOf(scoreb));


    }
    public void clickr(View btn){
        Log.i(TAG,"clickr");
        scorea=0;
        scoreb=0;
        TextView out1 = findViewById(R.id.scorea);
        out1.setText(String.valueOf(scorea));
        TextView out2 = findViewById(R.id.scoreb);
        out2.setText(String.valueOf(scoreb));

    }




}