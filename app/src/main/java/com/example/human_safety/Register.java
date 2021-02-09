package com.example.human_safety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

public class Register extends AppCompatActivity {
    Button btn1,btn2,btn3;
    EditText e1;
    SQLiteDatabase sqlitedb;
    SQLiteOpenHelper sl;
    DatabaseHandler myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        e1=findViewById(R.id.contact_no);
        btn1=findViewById(R.id.add);
        btn2=findViewById(R.id.delete);
        btn3=findViewById(R.id.view);

        myDB=new DatabaseHandler(this);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sr=e1.getText().toString();
                addData(sr);
                Toast.makeText(Register.this,"Save Data",Toast.LENGTH_LONG).show();
                e1.setText("");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sqlitedb=myDB.getWritableDatabase();
                String x=e1.getText().toString();
                DeleteData(x);
                Toast.makeText(Register.this,"Delete Data",Toast.LENGTH_LONG).show();

            }
        });
        
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void loadData() {
        ArrayList<String> theList = new ArrayList<>( );
        Cursor data = myDB.getListContents();
        if (data.getCount()==0)
        {
            Toast.makeText(Register.this,"There Is No Content",Toast.LENGTH_SHORT).show();
        }
        else {
            while (data.moveToNext()){
                theList.add(data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                ListView listView=findViewById(R.id.list);
                listView.setAdapter(listAdapter);
            }
        }
    }


    private boolean DeleteData(String x)
    {
        return sqlitedb.delete(DatabaseHandler.TABLE_NAME,DatabaseHandler.COL2+"=?",new String[]{x})>0;
    }

    private void addData(String newEntry)
    {
        boolean insertData = myDB.addData(newEntry);
        if (insertData==true)
        {
            Toast.makeText(Register.this,"Data Added",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(Register.this,"Unsuccessful",Toast.LENGTH_SHORT).show();
        }
    }
}
