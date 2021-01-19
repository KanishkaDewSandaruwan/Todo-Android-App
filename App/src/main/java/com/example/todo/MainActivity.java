package com.example.todo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnAddTodo;
    private ListView listTodo;
    private TextView txtView;
    private DBHandler dbHandler;
    private int count;
    Context context;
    List<ToDo> toDoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        dbHandler = new DBHandler(context);
        count = dbHandler.getCount();

        btnAddTodo = findViewById(R.id.btnAddTodo);
        listTodo = findViewById(R.id.TodoListView);
        txtView = findViewById(R.id.viewListOfTodo);

        toDoList = new ArrayList<>();
        toDoList =  dbHandler.getAllTodos();

        TodoArrayAdapter todoArrayAdapter = new TodoArrayAdapter(context,R.layout.single_todo,toDoList);
        listTodo.setAdapter(todoArrayAdapter);



        txtView.setText("You Have "+count+ " ToDos");

        btnAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddTodo.class);
                startActivity(intent);
            }
        });

        listTodo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                final ToDo toDo = toDoList.get(position);
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle(toDo.getTitle());
                builder.setMessage(toDo.getDesc());
                builder.setPositiveButton("Finished", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AlertDialog.Builder builderFinish = new AlertDialog.Builder(context);
                        builderFinish.setTitle("Finish ToDo");
//                        builderFinish.setMessage("Select Your Openion ");
                        builderFinish.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHandler.updateFinished(toDo.getId());
                                startActivity(new Intent(context,MainActivity.class));
                                Toast toast = Toast.makeText(getApplicationContext(),"Your Todo Finish Success",Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });

                        builderFinish.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHandler.cancelFinish(toDo.getId());
                                startActivity(new Intent(context,MainActivity.class));
                                Toast t = Toast.makeText(getApplicationContext(),"Your Todo Finish Cancel Success",Toast.LENGTH_SHORT);
                                t.show();
                            }
                        });

                        builderFinish.show();
                    }
                });

                builder.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(),EditTodo.class);
                        intent.putExtra("ID",toDo.getId());
                        startActivity(intent);
                    }
                });

                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.deleteTodo(toDo.getId());
                        Toast toast = Toast.makeText(context,"Todo Delete Success",Toast.LENGTH_SHORT);
                        startActivity(new Intent(context,MainActivity.class));
                        toast.show();
                    }
                });

                builder.show();

            }
        });
    }
}
