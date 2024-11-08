package com.example.lab2_and102_fa24.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Paint;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2_and102_fa24.DAO.TaskDAO;
import com.example.lab2_and102_fa24.R;
import com.example.lab2_and102_fa24.model.Task;

import java.util.ArrayList;
import java.util.Calendar;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{
    Context context;
    ArrayList<Task> list;
    TaskDAO taskDAO;

    public TaskAdapter(Context context, ArrayList<Task> list) {
        this.context = context;
        this.list = list;
        taskDAO = new TaskDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = list.get(holder.getAdapterPosition());
        holder.tvContent.setText(list.get(position).getContent());
        holder.tvDay.setText(list.get(position).getDate());

        if(list.get(position).getStatus() == 1){
            holder.ckTask.setChecked(true);
            holder.tvContent.setPaintFlags(holder.tvContent.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//            notifyDataSetChanged();
        }
        else {
            holder.ckTask.setChecked(false);
            holder.tvContent.setPaintFlags(holder.tvContent.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//            notifyDataSetChanged();
        }
        holder.ckTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int id = list.get(holder.getAdapterPosition()).getId();
                boolean checkRs = taskDAO.updatecheck(id, holder.ckTask.isChecked());

                if(checkRs){
                    Toast.makeText(context, "Update succesfull", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Update fail", Toast.LENGTH_SHORT).show();
                }

                list.clear();
                list = taskDAO.getList();
                notifyDataSetChanged();
            }
        });

        holder.imgDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("confirm Delete")
                    .setMessage("Are you sure delete this student?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        int id = list.get(holder.getAdapterPosition()).getId();
                        boolean check = TaskDAO.deleteInfor(id);
                        if (!check) {
                            Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();
                        } else {
                            list.clear();
                            list = taskDAO.getList();
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Delete success", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .create().show();
        });

        holder.imgUpdate.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.item_update, null);
            builder.setView(view);

            builder.setTitle("Update Student");
            builder.setIcon(android.R.drawable.checkbox_on_background);

            EditText edtTitle, edtContent, edtDate, edtType;
            edtTitle = view.findViewById(R.id.edtTitleUpdate);
            edtContent = view.findViewById(R.id.edtContentUpdate);
            edtDate = view.findViewById(R.id.edtDateUpdate);
            edtType = view.findViewById(R.id.edtTypeUpdate);

            edtTitle.setText(task.getTitle());
            edtContent.setText(task.getContent());
            edtDate.setText(task.getDate());
            edtType.setText(task.getType());

            edtDate.setOnClickListener(v1 -> {
                Calendar calendar = Calendar.getInstance();
                int date = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog dialog = new DatePickerDialog(context, (view1, year1, month1, dayOfMonth) -> {
                    edtDate.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
                }, year, month, date);
                dialog.show();
            });

            edtType.setOnClickListener(v2 -> {
                String[] list = {"Easy", "Medium", "Hard"};
                new android.app.AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.checkbox_on_background)
                        .setTitle("Chọn độ khó")
                        .setItems(list, (dialog, which) -> {
                            edtType.setText(list[which]);
                        })
                        .create().show();
            });

            builder.setPositiveButton("Update",(dialog, which) -> {
                int id = task.getId();
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();
                String date = edtDate.getText().toString();
                String type = edtType.getText().toString();

                Task task2 = new Task(id, title, content, date, type,1);
                task2.setTitle(title);
                task2.setContent(content);
                task2.setDate(date);
                task2.setType(type);

                boolean check = taskDAO.updateinfor(task2);
                if (!check) {
                    Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
                } else {
                    list.set(position, task2);
                    notifyItemChanged(holder.getAdapterPosition());
                    // cách 2
//                    Intent intent = new Intent(context, MainActivity.class);
//                    context.startActivity(intent);
                    Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Cancel",(dialog, which) -> dialog.dismiss());
            builder.create().show();

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent, tvDay;
        CheckBox ckTask;
        ImageView imgUpdate, imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvDay = itemView.findViewById(R.id.tvDay);
            ckTask = itemView.findViewById(R.id.ckTask);
            imgUpdate = itemView.findViewById(R.id.imgUpdate);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }

    }
