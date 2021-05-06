package com.example.annuaire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactsAdapter.OnContactListener {

    RecyclerView recyclerView;
    AppDatabase db;
    LinearLayoutManager linearLayoutManager;
    List<Contact> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);
        contacts= (ArrayList<Contact>) db.contact().getAll();
        ContactsAdapter adapter = new ContactsAdapter(this, contacts,this, MainActivity.this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);

        contacts.clear();
        contacts.addAll(db.contact().getAll());
        adapter.notifyDataSetChanged();

        RefreshListView((ArrayList<Contact>) db.contact().getAll());

        FloatingActionButton addFAB = (FloatingActionButton) findViewById(R.id.add_contact_button);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RefreshListView((ArrayList<Contact>) db.contact().getAll());
        Toast.makeText(MainActivity.this,"resumed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String textQuery) {
                RefreshListView((ArrayList<Contact>) db.contact().findByName("%"+textQuery+"%"));
                return false;
            }
        });
        return true;
    }
    void RefreshListView(List<Contact> contacts){
        ContactsAdapter adapter = new ContactsAdapter(this,contacts,this,MainActivity.this);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onContactClick(int position) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("ID",String.valueOf(contacts.get(position).getID()));
        startActivity(intent);
    }
}
