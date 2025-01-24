package com.example.booksapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginMain extends AppCompatActivity {

    EditText userEdit;
    EditText pwEdit;
    Button butLogin;
    Button butRegister;
    DataBaseHelper db;
    TextView txtlog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        userEdit=findViewById(R.id.userEditReg);
        pwEdit=findViewById(R.id.pwEditReg);
        butLogin=findViewById(R.id.btnReg);
        butRegister=findViewById(R.id.btnCancel);
        txtlog=findViewById(R.id.txtLog);
        db=new DataBaseHelper(this);
        butRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginMain.this,registerAct.class);
                startActivity(intent);
            }
        });
        butLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname=userEdit.getText().toString();
                String pw=pwEdit.getText().toString();
                if(uname.equals("")||pw.equals("")){
                    Toast.makeText(LoginMain.this,"Please enter all the fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkuserpass=db.checkusernamepassword(uname,pw);
                    if(checkuserpass==true)
                    {
                        Toast.makeText(LoginMain.this,"Signed in successfully",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(), MainActivity.class );
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(LoginMain.this,"Invalid User",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}