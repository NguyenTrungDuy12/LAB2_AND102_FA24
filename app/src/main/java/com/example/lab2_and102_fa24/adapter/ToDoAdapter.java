package com.example.lab2_and102_fa24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lab2_and102_fa24.R;
import com.example.lab2_and102_fa24.model.ToDo;

import java.util.ArrayList;

public class ToDoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ToDo> list;


    public ToDoAdapter(Context context, ArrayList<ToDo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.item_layout,viewGroup, false);
        ToDo toDo = list.get(i);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtContent = view.findViewById(R.id.txtContent);
        TextView textDate = view.findViewById(R.id.txtDate);
        TextView txtType = view.findViewById(R.id.txtType);

        txtTitle.setText(toDo.getTitle());
        txtContent.setText(toDo.getContent());
        textDate.setText(toDo.getDate());
        txtType.setText(toDo.getType());

        return view;
    }
}
