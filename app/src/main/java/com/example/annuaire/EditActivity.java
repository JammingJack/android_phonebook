package com.example.annuaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    AppDatabase database;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText phone;
    EditText job;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("name", "EditActivity onCreate: ");
        database=AppDatabase.getInstance(this);

        Log.d("name", "connected to db ");
        setContentView(R.layout.activity_edit);

        firstName = (EditText) findViewById(R.id.fn_filed);
        lastName = (EditText) findViewById(R.id.ln_filed);
        email = (EditText) findViewById(R.id.email_field);
        phone = (EditText) findViewById(R.id.num_filed);
        job = (EditText) findViewById(R.id.job_filed);
        Log.d("name", "attach graphic views to java objects ");
        Intent i = getIntent();
        Log.d("name", "retrieve intent ");
        if(i.hasExtra("ID")) {
            s = i.getStringExtra("ID");
        }else{
            Log.d("name", "no extras ID: ");
        }
        Contact contact = database.contact().findByID(Integer.parseInt(s));

        firstName.setText(contact.getName().split(" ",0)[0]);
        if(contact.getName().split(" ",0).length==2)
        lastName.setText(contact.getName().split(" ",0)[1]);
        email.setText(contact.getEmail());
        phone.setText(contact.getPhone());
        job.setText(contact.getJob());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_update,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==R.id.update){
            database.contact().update(new Contact(Integer.parseInt(s),firstName.getText().toString() + " " + lastName.getText().toString(),email.getText().toString(),job.getText().toString(),phone.getText().toString()));
            Toast.makeText(EditActivity.this,"Updated",Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(EditActivity.this, MainActivity.class);
            startActivity(myIntent);
        }
        return true;
    }
}