package com.fitnessclub;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fitnessclub.Model.ProfilePojo;
import com.fitnessclub.Networking.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    EditText email, password,phonenumber,fullname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fullname=findViewById(R.id.fullname);
        email=findViewById(R.id.email);
        phonenumber=findViewById(R.id.phonenumber);
        password=findViewById(R.id.password);
       SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String emailId = sharedPreferences.getString("user_name", "def-val");



    }
}