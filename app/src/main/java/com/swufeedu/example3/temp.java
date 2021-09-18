package com.swufeedu.example3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class temp extends AppCompatActivity implements View.OnClickListener {
    private TextView result;
    private EditText editInput;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp);

        result = (TextView)findViewById(R.id.tv_result);
        editInput = (EditText) findViewById(R.id.value_hint);
        btn = (Button)findViewById(R.id.btn_c2f);

        btn.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        outputValue();
    }
    private void outputValue(){
        float inputValue = Float.parseFloat(editInput.getText().toString());
        result.setText(String.valueOf(getF2C(inputValue)));
    }
    private float getF2C(float f){
        return ((f-32.0f)/1.8f);
    }


}