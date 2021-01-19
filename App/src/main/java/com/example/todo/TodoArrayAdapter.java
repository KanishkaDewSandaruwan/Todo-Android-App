package com.example.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TodoArrayAdapter extends ArrayAdapter<ToDo> {

    private Context context;
    private int resource;
    List<ToDo> toDoList;

    public TodoArrayAdapter(Context context, int resource, List<ToDo> toDoList) {
        super(context, resource, toDoList);

        this.context = context;
        this.resource = resource;
        this.toDoList = toDoList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(resource,parent,false);

        TextView showTitle = row.findViewById(R.id.txtTitle);
        TextView showDesc = row.findViewById(R.id.txtDescription);
        TextView showDate = row.findViewById(R.id.txtDate);
        ImageView showImage = row.findViewById(R.id.imgView);

        ToDo toDo = toDoList.get(position);

        String date = toDo.getStarted();
        String dates = String.valueOf(date);

        showTitle.setText(toDo.getTitle());
        showDesc.setText(toDo.getDesc());
        showDate.setText(dates);
        showImage.setVisibility(row.INVISIBLE);

        if (toDo.getFinished() > 0){
            showImage.setVisibility(row.VISIBLE);
        }


        return row;
    }

}
