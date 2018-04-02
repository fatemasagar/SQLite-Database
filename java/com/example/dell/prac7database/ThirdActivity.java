package com.example.dell.prac7database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.*;

public class ThirdActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    SmsManager sms;
    Button btsave;
    TextView troll,tse,tsub,tts1,tts2,ti;
    Spinner sub,sem,tst1,tst2;
    Spinner e1;
    int index_e1;
    String rollnos[];
    SQLiteDatabase db;
    String data ="";
    String i;
    String phone ;
    String[] subject,sem_ar,test1,test2;
    int index_sub,index_sem,index_tst1,index_tst2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        db=openOrCreateDatabase("STUDENT_ACADEMIC", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Academic(rollno TEXT,sem TEXT,subject TEXT,test1 TEXT,test2 TEXT);");
        init_Compo();
    }
    private void init_Compo()
    {
        ti = (TextView)findViewById(R.id.det);
        ti.setPaintFlags(ti.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        troll = (TextView) findViewById(R.id.txtenroll2);
        tse = (TextView) findViewById(R.id.txtsem);
        tsub = (TextView) findViewById(R.id.txtsubjct);
        tts1 = (TextView) findViewById(R.id.txttest1);
        tts2 = (TextView) findViewById(R.id.txttest2);

        e1 = (Spinner) findViewById(R.id.edroll);
        e1.setOnItemSelectedListener(this);
        sub = (Spinner) findViewById(R.id.ssub);
        sem = (Spinner) findViewById(R.id.ssem);
        tst1 = (Spinner) findViewById(R.id.stest1);
        tst2 = (Spinner) findViewById(R.id.stest2);

        btsave = (Button)findViewById(R.id.btnsave2);
        btsave.setOnClickListener(this);

        subject = getResources().getStringArray(R.array.subject);
        sem_ar  =  getResources().getStringArray(R.array.sem);
        test1 =  getResources().getStringArray(R.array.test1);
        test2 =  getResources().getStringArray(R.array.test2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,subject);
        sub.setAdapter(adapter);
        sub.setOnItemSelectedListener(this);
        sem.setAdapter( new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,sem_ar));
        sem.setOnItemSelectedListener(this);
        tst1.setAdapter( new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,test1));
        tst1.setOnItemSelectedListener(this);
        tst2.setAdapter( new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,test2));
        tst2.setOnItemSelectedListener(this);
        init_roll();
    }
    private void init_roll()
    {

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
        e1.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        /*switch(v.getId())
    	{
    		case R.id.btnsave2 :
    			dataDisplay();
    			data = "Enrollnment No: "+e1.getText()+"\nSem : "+sem_ar[index_sem]+"\nsubject : "+subject[index_sub]+"\nTest 1 : "+test1[index_tst1]+"\nTest 2 : "+test2[index_tst2];
    			showMessage("Data", data);
    			break;

    		case R.id.btnexit :
    			sendSMS(phone,data);
    			 clear();
    			//showMessage("Alert","SMS Sent");
    			break;
    	}*/
        if(view==btsave)
        {
            dataDisplay();
            data = "Enrollnment No: "+rollnos[index_e1]+"\nSem : "+sem_ar[index_sem]+"\nsubject : "+subject[index_sub]+"\nTest 1 : "+test1[index_tst1]+"\nTest 2 : "+test2[index_tst2];
            showMessage("Data", data);
        }
    }
    public void clear()
    {
        e1.setSelection(0);
        sub.setSelection(0);
        sem.setSelection(0);
        tst1.setSelection(0);
        tst2.setSelection(0);
    }


    private void sendSMS(String phone2, String data2) {
        sms = SmsManager.getDefault();
        sms.sendTextMessage(phone2,null,data2, null, null);

    }
    private void dataDisplay() {
        db.execSQL("INSERT INTO Academic VALUES('"+rollnos[index_e1]+"','"+sem_ar[index_sem]+"','"+subject[index_sub]+"','"+test1[index_tst1]+"','"+test2[index_tst2]+"');");
        ToastDis("Data added Successfully");
        clear();
    }
    public void showMessage(String title,String message) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void ToastDis(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getId())
        {
            case R.id.edroll :
                index_e1 = adapterView.getSelectedItemPosition();
                break;
            case R.id.ssub :
                index_sub =  adapterView.getSelectedItemPosition();
                //ToastDis(subject[index_sub]);
                break;

            case R.id.ssem :
                index_sem = adapterView.getSelectedItemPosition();
                //ToastDis(sem_ar[index_sem]);
                break;

            case R.id.stest1:
                index_tst1 = adapterView.getSelectedItemPosition();
                //ToastDis(test1[index_tst1]);
                break;

            case R.id.stest2:
                index_tst2 = adapterView.getSelectedItemPosition();
                //ToastDis(test2[index_tst2]);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
