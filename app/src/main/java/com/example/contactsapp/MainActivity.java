package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    EditText nameEdit;
    Spinner spinner;
    Spinner spinner2;
    DatabaseControl control;
    Button addButton;
    Button getButton;
    Button getButton2;
    Button deleteButton;
    TextView resultView;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        resultView = findViewById(R.id.resultView);
        control = new DatabaseControl(this);
        nameEdit = findViewById(R.id.nameEdit);
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        addButton = findViewById(R.id.addButton);
        getButton = findViewById(R.id.getButton);
        getButton2 = findViewById(R.id.getButton2);
        deleteButton = findViewById(R.id.deleteButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                String state = ((TextView) spinner.getSelectedView()).getText().toString();
                String platform = ((TextView) spinner2.getSelectedView()).getText().toString();
                control.open();
                boolean itWorked = control.insert(name, state, platform);
                control.close();
                if (itWorked)
                    Toast.makeText(getApplicationContext(), "Added " + name + " " + state + " " + platform, Toast.LENGTH_SHORT).show(); // helpful for developing an app, not practice for deployment
                else
                    Toast.makeText(getApplicationContext(), "FAILED " + name + " " + state + " " + platform, Toast.LENGTH_SHORT).show(); // helpful for developing an app, not practice for deployment
                onResume();
            }
        });

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control.open();
                String state = control.getState(nameEdit.getText().toString());
                String platform = ((TextView) spinner2.getSelectedView()).getText().toString();
                control.close();
                resultView.setText(state+": "+platform);
            }
        });

        getButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control.open();
                String name = control.getState2(nameEdit.getText().toString());
                String platform = ((TextView) spinner2.getSelectedView()).getText().toString();
                control.close();
                resultView.setText(name+": "+platform);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = (nameEdit.getText().toString());
                        String state = ((TextView) spinner.getSelectedView()).getText().toString();
                        String platform = ((TextView) spinner2.getSelectedView()).getText().toString();
                        control.open();
                        control.delete(name);
                        control.delete(state);
                        control.delete(platform);
                        control.close();

                    }
                });




                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.platform, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);



    }
    @Override
    public void onResume() {
        super.onResume();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        control.open();

        String [] getAllNamesArray = control.getAllNamesArray();
        //String [] getAllArtistArray = control.getAllArtistArray();
        //String [] getAllPlatformArray = control.getAllPlatformArray();


        control.close();
        RecyclerAdapter adapter = new RecyclerAdapter(getAllNamesArray);
        //RecyclerAdapter adapter2 = new RecyclerAdapter(getAllArtistArray);
        //RecyclerAdapter adapter3 = new RecyclerAdapter(getAllPlatformArray);
        adapter.setOnClickListner(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RecyclerAdapter.ViewHolder viewHolder = (RecyclerAdapter.ViewHolder) view.getTag();
                RecyclerAdapter.ViewHolder viewHolder2 = (RecyclerAdapter.ViewHolder) view.getTag();
                TextView textView = viewHolder.getTextView();
                TextView textView2 = viewHolder2.getTextView();
                String name = textView.getText().toString();
                String platform = textView2.getText().toString();
                resultView.setText(name);
                control.open();
                String state = control.getState(name);
                control.close();
                resultView.setText(name+": "+state+": "+platform);

            }
        });
        recyclerView.setAdapter(adapter);
        //recyclerView.setAdapter(adapter2);
        //recyclerView.setAdapter(adapter3);





        }



}