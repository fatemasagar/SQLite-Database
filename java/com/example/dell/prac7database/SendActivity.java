package com.example.dell.prac7database;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.widget.*;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

public class SendActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    String[] rollnos;
    SQLiteDatabase db;
    Button btsc;
    Spinner sp1;
    TextView tv,tv2,tvtx1,tvtx2,tv3;
    Intent i;
    String getRollNo;
    String phoneno;
    int index_roll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        db=openOrCreateDatabase("STUDENT_ACADEMIC", Context.MODE_PRIVATE, null);
        initialize_compo();
    }

    private void initialize_compo() {
        sp1 = (Spinner) findViewById(R.id.edrollsend);
        sp1.setOnItemSelectedListener(this);
        tv = (TextView)findViewById(R.id.txenroll);
        tv2 = (TextView)findViewById(R.id.edmsg);
        tv3 = (TextView)findViewById(R.id.edno);
        tvtx1 = (TextView) findViewById (R.id.tx1);
        tvtx2 = (TextView) findViewById (R.id.tx2);
        btsc = (Button)findViewById(R.id.btnsend);
        btsc.setOnClickListener(this);
        init_roll();
    }

    private void init_roll() {
        Cursor c=db.rawQuery("SELECT rollno FROM Student", null);
        if(c.getCount()==0)
        {
            showMessage("Error", "No records found");
            return;
        }
        StringBuffer buffer=new StringBuffer();
        while(c.moveToNext())
        {
            buffer.append(c.getString(0)+"\n");
        }
        String s = buffer.toString();
        rollnos = s.split("\n");
        StringBuffer buf = new StringBuffer();
        for(int i=0;i<rollnos.length;i++)
        {
            buf.append(rollnos[i]+"\n");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,rollnos);
        sp1.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if(view==btsc)
        {
            //showMessage("Title", "hjsdhgf");
            Cursor c=db.rawQuery("SELECT * FROM Academic WHERE rollno='"+rollnos[index_roll]+"'", null);
            if(c.getCount()==0) {
                showMessage("Error", "No records found");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while(c.moveToNext()) {
                buffer.append("Rollno: "+c.getString(0)+"\n");
                buffer.append("Sem: "+c.getString(1)+"\n");
                buffer.append("Subject: "+c.getString(2)+"\n");
                buffer.append("Test 1: "+c.getString(3)+"\n");
                buffer.append("Test 2: "+c.getString(4)+"\n");
            }
            Cursor c1=db.rawQuery("SELECT phone FROM Student WHERE rollno='"+rollnos[index_roll]+"'", null);
            if(c1.getCount()==0) {

              //  Toast.makeText(getApplicationContext(),phone_no,Toast.LENGTH_SHORT).show();
                showMessage("Error", "No records found");
                return;
            }
            StringBuffer buff=new StringBuffer();
            while(c1.moveToNext()) {
                buff.append(c1.getString(0));
            }
            phoneno=buff.toString();
            tv2.setText(buffer.toString());
            tv3.setText(phoneno);
            //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            sendSMS(phoneno,buffer.toString());
        }
    }
    private void sendSMS(String phone, String data) {
        // TODO Auto-generated method stub
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phone,null,data,null,null);
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        index_roll = adapterView.getSelectedItemPosition();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO Auto-generated method stub
    }
}
