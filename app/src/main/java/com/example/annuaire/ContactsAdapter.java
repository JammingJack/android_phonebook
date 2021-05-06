package com.example.annuaire;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>{
    private List<Contact> dataList;
    private Context context;
    private AppDatabase database;
    private OnContactListener onContactListener;
    private Context ctx;
    private MainActivity mainActivity;
    public ContactsAdapter(Context context, List<Contact> dataList, OnContactListener onContactListener, MainActivity mainActivity)
    {
        this.mainActivity=mainActivity;
        this.context=context;
        this.dataList=dataList;
        this.onContactListener=onContactListener;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list,parent,false);
        this.ctx=parent.getContext();
        return new ViewHolder(ctx, view, onContactListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Initialize main data
        Contact data=dataList.get(position);

        // Initialize database
        database=AppDatabase.getInstance(context);

        // Set text on text view
        holder.id.setText(String.valueOf(data.getID()));
        holder.name.setText(data.getName());
        holder.email.setText(data.getEmail());
        holder.job.setText(data.getJob());
        holder.phone.setText(data.getPhone());
        holder.btCall.setOnClickListener(v->{
            Uri telephone = Uri.parse("tel:"+data.getPhone());
            Intent callActivity = new Intent(Intent.ACTION_DIAL,telephone);
            context.startActivity(callActivity);
            Log.d("clicked ", data.getPhone());
        });
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        //Initialize variable
        TextView id, name, email,job,phone;
        ImageView btCall;
        OnContactListener onContactListener;
        Context context;
        public ViewHolder(Context context, @NonNull View itemView, OnContactListener onContactListener) {
            super(itemView);
            // Assign variable and Listener
            this.context=context;
            id=itemView.findViewById(R.id.ID);
            name=itemView.findViewById(R.id.Name);
            email=itemView.findViewById(R.id.Email);
            job=itemView.findViewById(R.id.Job);
            phone=itemView.findViewById(R.id.Tel);
            this.onContactListener=onContactListener;

            //Attach OnClickListener to the viewHolder (this refers to the interface implemented
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this::onLongClick);
            btCall=itemView.findViewById(R.id.call_btn);
        }

        @Override
        public void onClick(View v) {
            onContactListener.onContactClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {

            Log.d("name", "onLongClick: clicked");
            Log.d("name", "onLongClick context: "+context.toString());
            Log.d("name", "onLongClick: ID = "+ id);
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Select your answer")
                        .setMessage("Do you want to delete this contact?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("id", id.getText().toString());
                                int idContact = Integer.parseInt(id.getText().toString());
                                Contact contactToDelete = new Contact();
                                int contactPos = getAdapterPosition();
                                contactToDelete.setID(idContact);
                                if(contactToDelete==null){
                                    Log.d("dd", "onClick: contact is null");
                                }else {
                                    dataList.remove(contactToDelete);
                                    database.contact().deleteContact(idContact);
                                    for (Contact c:database.contact().getAll()) {
                                            Log.d("contact", c.toString());
                                    }
                                    Log.d("name", "onClick: deleted!!");

                                    mainActivity.RefreshListView(database.contact().getAll());
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog dialog  = builder.create();
                dialog.show();
                return true;


        }
    }
    public interface OnContactListener{
        public void onContactClick(int position);
    }

}