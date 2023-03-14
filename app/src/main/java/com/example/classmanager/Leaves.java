package com.example.classmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Leaves extends AppCompatActivity {

    EditText edtDateLeave, nameUser, reasonLeave;
    Button btnCreateLeave;
    DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaves);

        edtDateLeave = (EditText) findViewById(R.id.dateLeave);
        nameUser = (EditText) findViewById(R.id.nameUser);
        reasonLeave = (EditText) findViewById(R.id.reasonLeave);
        btnCreateLeave = (Button) findViewById(R.id.btnCreateLeave);
        mData = FirebaseDatabase.getInstance().getReference("List_leaves");

        edtDateLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Select();
            }
        });

        btnCreateLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = edtDateLeave.getText().toString().trim();
                String name = nameUser.getText().toString().trim();
                String reason = reasonLeave.getText().toString().trim();
                Leave leave = new Leave(name, date, reason);
                mData.child(date).setValue(leave);

            }
        });
    }
    private void Select() {
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                edtDateLeave.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },year,month,date);
        datePickerDialog.show();
    }

}

