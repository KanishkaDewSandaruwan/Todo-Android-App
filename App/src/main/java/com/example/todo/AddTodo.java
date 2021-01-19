package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTodo extends AppCompatActivity {

    Button btnBackNewTodo;
    private  EditText title;
    private EditText description;
    private DBHandler dbHandler;
    Context context ;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        context = this;
        dbHandler = new DBHandler(context);

        title = findViewById(R.id.editUpdateTitle);
        description = findViewById(R.id.editUpdateDescription);

        btnSave = findViewById(R.id.btnSaveTodo);
        btnBackNewTodo = findViewById(R.id.btnBackSaveTodo);

        btnBackNewTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(title.getText().toString().isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(),"Enter Title",Toast.LENGTH_SHORT);
                    toast.show();
                }else if(description.getText().toString().isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(),"Enter Description ",Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    String getTitle = title.getText().toString();
                    String getDesc = description.getText().toString();
                    long getStarted = System.currentTimeMillis();
//                    String day = new SimpleDateFormat("dd/mm/yyyy", Locale.getDefault()).format(new Date());


                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss ");
                    String currentDateandTime = sdf.format(new Date());

                    ToDo toDo = new ToDo(getTitle, getDesc, currentDateandTime, 0);
                    dbHandler.addTodos(toDo);
                    Toast toast = Toast.makeText(getApplicationContext(), "Todo Added Successfuly", Toast.LENGTH_LONG);
                    toast.show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });

    }
}
