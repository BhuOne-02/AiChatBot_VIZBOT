package com.example.vizbot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class form_family extends AppCompatActivity {
    EditText rl1,rl1name,rl1num,rl2,rl2name,rl2num,rl3,rl3name,rl3num,desp;
    Button btn_next1;
    String relation1, relation1_name,relation1_num,relation2,relation2_name,relation2_num,relation3,relation3_name,relation3_num,desc,add,phone,docphone,FullName,bloodtype,occup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_family);
        rl1=(EditText) findViewById(R.id.rl1);
        rl1name=(EditText) findViewById(R.id.rl1_name);
        rl1num=(EditText)findViewById(R.id.rl1_number);
        rl2=(EditText) findViewById(R.id.rl2);
        rl2name=(EditText) findViewById(R.id.rl2_name);
        rl2num=(EditText) findViewById(R.id.rl2_number);
        rl3=(EditText)findViewById(R.id.rl3);
        rl3name=(EditText)findViewById(R.id.rl3_name);
        rl3num=(EditText)findViewById(R.id.rl3_number);
        desp=(EditText)findViewById(R.id.desc);
        btn_next1=(Button)findViewById(R.id.next1);



        btn_next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 relation1=rl1.getText().toString();
                 relation1_name=rl1name.getText().toString();
                 relation1_num=rl1num.getText().toString();
                 relation2=rl2.getText().toString();
                 relation2_name=rl2name.getText().toString();
                 relation2_num=rl2num.getText().toString();
                 relation3=rl3.getText().toString();
                 relation3_name=rl3name.getText().toString();
                 relation3_num=rl3num.getText().toString();
                 desc=desp.getText().toString();
                Bundle bundle=getIntent().getExtras();
                 add=bundle.getString("add");
                 FullName=bundle.getString("FullName");
                 phone=bundle.getString("phone");
                 docphone=bundle.getString("docphone");
                 bloodtype=bundle.getString("bloodtype");
                occup=bundle.getString("occupation");
                Intent intent_family=new Intent(form_family.this,chat_bot.class);
                intent_family.putExtra("add",add);
                intent_family.putExtra("FullName",FullName);
                intent_family.putExtra("phone",phone);
                intent_family.putExtra("docphone",docphone);
                intent_family.putExtra("bloodtype",bloodtype);
                intent_family.putExtra("add",add);
                intent_family.putExtra("rl1",relation1);
                intent_family.putExtra("rl1_name",relation1_name);
                intent_family.putExtra("rl1_num",relation1_num);
                intent_family.putExtra("rl2",relation2);
                intent_family.putExtra("rl2_name",relation2_name);
                intent_family.putExtra("rl2_num",relation2_num);
                intent_family.putExtra("rl3",relation3);
                intent_family.putExtra("rl3_name",relation3_name);
                intent_family.putExtra("rl3_num",relation3_num);
                intent_family.putExtra("desc",desc);
                intent_family.putExtra("occup",occup);
                startActivity(intent_family);
            }
        });

    }
}