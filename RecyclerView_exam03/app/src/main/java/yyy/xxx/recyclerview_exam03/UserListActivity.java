package yyy.xxx.recyclerview_exam03;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {

    ArrayList<Contact> contacts;
    RecyclerView rvContacts;
    ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);

        // Initialize contacts
        Contact.createContactsList(30);
        // Create adapter passing in the sample user data
        adapter = new ContactsAdapter(this, contacts);


        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!


    }

}
