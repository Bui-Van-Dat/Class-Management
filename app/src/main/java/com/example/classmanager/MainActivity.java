package com.example.classmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 600;
    DatabaseReference mData, mData1;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    Button btnClassList, btnAlarm;
    ImageView imgAlarm1, imgAlarm2, imgMic;
    TextView txtTv;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgAlarm1 = (ImageView) findViewById(R.id.imgAlarm1);
        imgAlarm2 = (ImageView) findViewById(R.id.imgAlarm2);
        imgMic = (ImageView) findViewById(R.id.imgMic);
        txtTv = (TextView) findViewById(R.id.txtTv);
        btnAlarm = (Button) findViewById(R.id.btnAlarm);
        btnClassList = (Button) findViewById(R.id.btnList);
        mData = FirebaseDatabase.getInstance().getReference("List_lights");
        mData.child("light1").setValue("0");
        mData.child("light2").setValue("0");
        mData.child("light3").setValue("0");
        mData.child("light4").setValue("0");

        mData1 = FirebaseDatabase.getInstance().getReference("list_strings");
        mData1.child("alarm").setValue(txtTv.getText());

        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                    {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        break;
                    }
                    case R.id.classDiagram:
                    {
                        startActivity(new Intent(getApplicationContext(), Diagram.class));
//                        finish();
                        break;
                    }
                    case R.id.classList:
                    {
                        startActivity(new Intent(getApplicationContext(), StudentList.class));
//                        finish();
                        break;
                    }
                    case R.id.createLeave:
                    {
                        startActivity(new Intent(getApplicationContext(), Leaves.class));
//                        finish();
                        break;
                    }
                    case R.id.logout:
                    {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                        break;
                    }
                }
                return false;
            }
        });

        imgMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();

            }
        });

        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData1.child("alarm").setValue("nhấp nháy");
            }
        });
        btnClassList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StudentList.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }
    private void speak() {
        //intent to show speed to text
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi speak something");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e){
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtTv.setText(result.get(0));
                    mData1.child("alarm").setValue(result.get(0));
                }
                break;
            }
        }
    }
}