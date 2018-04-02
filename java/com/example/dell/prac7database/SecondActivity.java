package com.example.dell.prac7database;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{
    int a=0;
    int flag=0;
    Button btsave;
    TextView troll,tn,tmob,title;
    EditText eroll,en,emob;
    Intent i;
    String rollno,name,mob;
    private String tempr;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        db=openOrCreateDatabase("STUDENT_ACADEMIC", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABL9++++++E IF NOT EXISTS Student(name TEXT, rollno TEXT, phone TEXT);");
        init_Compo();
    }
    private void init_Compo() {
        troll = (TextView) findViewById(R.id.txtenroll);
        tn = (TextView) findViewById(R.id.txtname);
        tmob = (TextView) findViewById(R.id.txtphone);
        title = (TextView) findViewById(R.id.det);
        title.setPaintFlags(title.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        eroll = (EditText) findViewById(R.id.edenroll);
        en = (EditText) findViewById(R.id.edname);
        emob = (EditText) findViewById(R.id.edphone);
        btsave = (Button)findViewById(R.id.btnsave);
        btsave.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view==btsave) {
            if(eroll.getText().toString().trim().length()==0||en.getText().toString().trim().length()==0||emob.getText().toString().trim().length()==0) {
                showMessage("Error", "Please enter all values");
                return;
            }
            else {
                flag=1;
                getdata();
                a = Validate_rollno();
                if(a==0){
                    db.execSQL("INSERT INTO Student VALUES('"+en.getText()+"','"+eroll.getText()+"','"+emob.getText()+"');");
                    ToastDis("Data added Successfully");
                    clear();
                }
            }
        }
    }
    /*public void getd()
    {
        String buff="";
        Cursor c=db.rawQuery("SELECT rollno FROM Details", null);
        while(c.moveToNext())
        {
            buff = "Data :"+"\n"+c.getString(0)+"\n"+c.getString(1)+"\n"+c.getString(2);
        }
        ToastDis(buff);
    }*/
    public void ToastDis(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
    public void clear() {
        eroll.setText("");
        en.setText("");
        emob.setText("");
    }

    public void getdata() {
        rollno = eroll.getText().toString();
        name = en.getText().toString();
        mob = emob.getText().toString();
    }
    private int Validate_rollno() {

        tempr = rollno;
        int lenght = tempr.length();
        String s1 = tempr.substring(0,7);
        //ToastDis(s1);
        String no = tempr.substring(7,11);
        //ToastDis(no);
        if(lenght!=11) {
            showMessage("Error", "Length Should be proper");
            return 1;
        }
        if(!s1.equals("CSU17S-")) {
            showMessage("Error", "Format Incorrect");
            return 1;
        }
        if(no.length()!=4) {
            showMessage("Error", "Format Incorrect. Plaese Check Numerics");
            return 1;
        }
        return 0;
    }

    public void showMessage(String title,String message) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
