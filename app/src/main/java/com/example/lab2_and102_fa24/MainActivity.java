package com.example.lab2_and102_fa24;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2_and102_fa24.DAO.TaskDAO;
import com.example.lab2_and102_fa24.adapter.TaskAdapter;
import com.example.lab2_and102_fa24.model.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TaskDAO dao;
    ArrayList<Task> listTask;
    String TAG = "//=======";
    RecyclerView rcvTask;
    EditText edtId, edtTitle, edtContent, edtDate, edtType;
    Button btnAdd;
    TaskAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        
        dao = new TaskDAO(this);
//        listTask =new ArrayList<>();
        listTask= dao.getList();
        Log.d(TAG, "onCreate: " + listTask.toString());
        adapter = new TaskAdapter(MainActivity.this, listTask);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvTask.setLayoutManager(linearLayoutManager);
        rcvTask.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString().trim();
                String content = edtContent.getText().toString().trim();
                String date = edtDate.getText().toString().trim();
                String type = edtType.getText().toString().trim();
                if(title.isEmpty() || content.isEmpty() || date.isEmpty() || type.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please input data", Toast.LENGTH_SHORT).show();
                    if(title.isEmpty()){
                        edtTitle.setError("Please enter title");
                    }

                    if(content.isEmpty()){
                        edtContent.setError("Please enter content");
                    }

                    if(date.isEmpty()){
                        edtDate.setError("Please enter date");
                    }

                    if(type.isEmpty()){
                        edtType.setError("Please enter type");
                    }
                }
                else {
                    Task inFor = new Task(1,title, content, date, type,0);
                    long check = dao.addInfor(inFor);
                    if (check <0){
                        Toast.makeText(MainActivity.this, "ERROR: Not insert data", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Insert succesfull", Toast.LENGTH_SHORT).show();
                    }
                    listTask.add(new Task(Integer.parseInt(edtId.getText().toString()), edtTitle.getText().toString() ,edtContent.getText().toString(), edtDate.getText().toString(),edtType.getText().toString(),0));
//                    adapter = new TaskAdapter(MainActivity.this, listTask);
//                    rcvTask.setLayoutManager(linearLayoutManager);
//                    rcvTask.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    reset();
                }

            }
        });

    }

    public void init(){
        rcvTask = findViewById(R.id.rcvTask);
        edtId = findViewById(R.id.edtID);
        edtContent = findViewById(R.id.edtContent);
        edtDate = findViewById(R.id.edtDate);
        edtTitle = findViewById(R.id.edtTitle);
        edtType = findViewById(R.id.edtType);
        btnAdd = findViewById(R.id.btnAdd);
    }

    public void reset(){
        edtId.setText("");
        edtContent.setText("");
        edtDate.setText("");
        edtTitle.setText("");
        edtType.setText("");
    }
}