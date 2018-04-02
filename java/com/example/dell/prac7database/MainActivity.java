package com.example.dell.prac7database;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bts,bex,bta,btssnd;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComp();
    }
    private void initializeComp() {
        bts = (Button) findViewById(R.id.btnst);
        bts.setOnClickListener(this);
        bex = (Button) findViewById(R.id.exit);
        bex.setOnClickListener(this);
        bta = (Button) findViewById(R.id.acad);
        bta.setOnClickListener(this);
        btssnd = (Button) findViewById(R.id.send_sms);
        btssnd.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view==bts) {
            i = new Intent(MainActivity.this,SecondActivity.class);
            startActivity(i);
        }
        if(view==bta) {
            i = new Intent(MainActivity.this,ThirdActivity.class);
            startActivity(i);
        }
        if(view==btssnd) {
            i = new Intent(MainActivity.this,SendActivity.class);
            startActivity(i);
        }
        if(view==bex) {
            System.exit(0);
        }
    }
}
