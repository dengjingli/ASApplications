package com.swufeedu.example3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;  //导包
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DecimalFormat;

public class bmi extends AppCompatActivity implements View.OnClickListener {
    Button btn;
    EditText heightText, weightText;
    TextView result, suggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi);

        btn = findViewById(R.id.button);
        heightText = findViewById(R.id.height);
        weightText = findViewById(R.id.weight);
        result = findViewById(R.id.TextResult);
        suggestion = findViewById(R.id.TextSug);
        btn.setOnClickListener(this);
    }

    public void onClick(View v) {
        DecimalFormat df = new DecimalFormat("0.00");
        double weight, height, BMI;
        if (weightText.getText().length() != 0&&heightText.getText().length() != 0) {
            weight = Double.parseDouble(weightText.getText().toString());
            height = Double.parseDouble(heightText.getText().toString());
            BMI = weight / (height * height);

            result.setText(df.format(BMI));
            if (BMI < 18.4) {
                suggestion.setText("偏瘦，多吃点补补");
            } else if (BMI < 23.9) {
                suggestion.setText("正常，请保持");
            } else if (BMI < 27.9) {
                suggestion.setText("过重，注意锻炼身体");
            } else {
                suggestion.setText("肥胖，少吃多运动");
            }
        }
    }
}