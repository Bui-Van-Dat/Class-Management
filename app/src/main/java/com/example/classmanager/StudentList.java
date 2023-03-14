package com.example.classmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentList extends AppCompatActivity {

    RecyclerView rcvStudents;
    StudentAdapter mStudentAdapter;
    List<Student> mListStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        initUi();
        getListStudentsFromDatabase();
    }

    private void initUi() {
        rcvStudents = findViewById(R.id.rcv_students_two);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvStudents.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvStudents.addItemDecoration(dividerItemDecoration);

        mListStudents = new ArrayList<>();
        mStudentAdapter = new StudentAdapter(mListStudents);
        rcvStudents.setAdapter(mStudentAdapter);
    }


    private void getListStudentsFromDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_users");

        //Cach 1
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(mListStudents != null){
                    mListStudents.clear();
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Student student = dataSnapshot.getValue(Student.class);
                    mListStudents.add(student);
                }
                mStudentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}