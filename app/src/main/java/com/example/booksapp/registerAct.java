package com.example.booksapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
public class registerAct extends AppCompatActivity {
    TextView reg;
    EditText uname;
    EditText pw;
    EditText pwC;
    Button btnReg;
    Button canc;
    DataBaseHelper db;
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        reg=findViewById(R.id.regText);
        uname=findViewById(R.id.userEditReg);
        pw=findViewById(R.id.pwEditReg);
        pwC=findViewById(R.id.pwCheck);
        btnReg=findViewById(R.id.btnReg);
        canc=findViewById(R.id.btnCancel);
        db=new DataBaseHelper(this);
        canc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(registerAct.this,LoginMain.class);
                startActivity(intent);

            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usern=uname.getText().toString();
                String passw=pw.getText().toString();
                String pwch=pwC.getText().toString();

                if(usern.equals("")||passw.equals("")||pwch.equals(""))
                    Toast.makeText(registerAct.this,"Please enter all the fields",Toast.LENGTH_SHORT).show();
                else
                if(passw.equals(pwch)){
                    Boolean checkuser =db.checkusername(usern);
                    if(checkuser==false){
                        Boolean insert = db.insertData(usern,passw);
                        if(insert==true){
                            Toast.makeText(registerAct.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(), LoginMain.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(registerAct.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(registerAct.this,"User Already Exist",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(registerAct.this,"Passwords Not Matching",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
