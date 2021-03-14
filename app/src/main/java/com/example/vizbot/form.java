package com.example.vizbot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class form extends AppCompatActivity {
    EditText fname,phnum,docnum,blood,address,occupation;
    Button btn_next;
    String FullName,phone,docphone,bloodtye,add,occup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        fname=(EditText)findViewById(R.id.FullName);
        phnum=(EditText)findViewById(R.id.phone);
        docnum=(EditText)findViewById(R.id.docphone);
        blood=(EditText)findViewById(R.id.bloodtype);
        address=(EditText)findViewById(R.id.address);
        occupation=(EditText)findViewById(R.id.occ);
        btn_next=(Button)findViewById(R.id.btn_next);


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullName=fname.getText().toString();
                phone=phnum.getText().toString();
                docphone=docnum.getText().toString();
                bloodtye=blood.getText().toString();
                add=address.getText().toString();
                occup=occupation.getText().toString();
                System.out.println("values1"+FullName+" "+phone+" "+docphone+" "+bloodtye+" "+add);
                Intent form_intent=new Intent(form.this,form_family.class);
                form_intent.putExtra("FullName",FullName);
                form_intent.putExtra("phone",phone);
                form_intent.putExtra("docphone",docphone);
                form_intent.putExtra("bloodtype",bloodtye);
                form_intent.putExtra("add",add);
                form_intent.putExtra("occupation",occup);
                startActivity(form_intent);
            }
        });
    }
}