package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditTodo extends AppCompatActivity {

    Button btnBackUpdateTodo,btnUpdateTodo;
    EditText editTitle,editDescription;
    DBHandler dbHandler;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        context = this;
        dbHandler = new DBHandler(context);

        btnBackUpdateTodo = findViewById(R.id.btnBackSaveTodo);
        btnUpdateTodo = findViewById(R.id.btnSaveTodo);
        editTitle = findViewById(R.id.editUpdateTitle);
        editDescription = findViewById(R.id.editUpdateDescription);

        Intent intent = getIntent();
        final int id = intent.getIntExtra("ID",0);

        ToDo toDo  = dbHandler.getSingleTodo(id);


        editTitle.setText(toDo.getTitle());
        editDescription.setText(toDo.getDesc());



        btnBackUpdateTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        btnUpdateTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedTitle = editTitle.getText().toString();
                String updatedDesc = editDescription.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
                String newTime = sdf.format(new Date());

                ToDo updateTodo = new ToDo(id,updatedTitle,updatedDesc,newTime,0);
                dbHandler.updateTodo(updateTodo);
                startActivity(new Intent(context,MainActivity.class));
                Toast t = Toast.makeText(getApplicationContext(),"Todo Updated Success",Toast.LENGTH_SHORT);
                t.show();
            }
        });
    }
}
