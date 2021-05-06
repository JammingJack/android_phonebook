package com.example.annuaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {
    AppDatabase database;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText phone;
    EditText job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database=AppDatabase.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        firstName = (EditText) findViewById(R.id.fn_filed);
        lastName = (EditText) findViewById(R.id.ln_filed);
        email = (EditText) findViewById(R.id.email_field);
        phone = (EditText) findViewById(R.id.num_filed);
        job = (EditText) findViewById(R.id.job_filed);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_save,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch(id){
            case R.id.save :
            {
                database.contact().insert(new Contact(firstName.getText().toString() + " " + lastName.getText().toString(),email.getText().toString(),job.getText().toString(),phone.getText().toString()));
                Toast.makeText(AddActivity.this,"Added",Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(myIntent);
                break;
            }
            case R.id.Reset : {
                firstName.setText("");
                lastName.setText("");
                email.setText("");
                job.setText("");
                phone.setText("");
            }
            default:{}
        }
        return true;
    }
}