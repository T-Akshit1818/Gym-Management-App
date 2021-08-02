package com.fitnessclub.Networking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fitnessclub.Model.ProfilePojo;
import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.R;
import com.fitnessclub.User_HomeActivity;
import com.fitnessclub.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    EditText email, password,phonenumber,fullname;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fullname=findViewById(R.id.fullname);
        email=findViewById(R.id.email);
        phonenumber=findViewById(R.id.phonenumber);
        password=findViewById(R.id.password);
       SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String emailId = sharedPreferences.getString("user_name", "def-val");


        Call<List<ProfilePojo>> reg= ServiceGenerator.getGymApi().profile(emailId);
        reg.enqueue(new Callback<List<ProfilePojo>>() {
            @Override
            public void onResponse(Call<List<ProfilePojo>> call, Response<List<ProfilePojo>> response) {
                Toast.makeText(ProfileActivity.this,response.body().get(0).getEmail(),Toast.LENGTH_LONG).show();
                fullname.setText(response.body().get(0).getFullname());
                email.setText(response.body().get(0).getEmail());
                password.setText(response.body().get(0).getPass());
                phonenumber.setText(response.body().get(0).getPhonenumber());

            }

            @Override
            public void onFailure(Call<List<ProfilePojo>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this,"fail",Toast.LENGTH_LONG).show();
            }
        });

        update=(Button)findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<SuccessOrFailureResponse> reg= ServiceGenerator.getGymApi().updateprofile(fullname.getText().toString(),email.getText().toString(),password.getText().toString(),phonenumber.getText().toString());
                reg.enqueue(new Callback<SuccessOrFailureResponse>() {
                    @Override
                    public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                        Intent i = new Intent(ProfileActivity.this, User_HomeActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Call<SuccessOrFailureResponse> call, Throwable t) {
                        Toast.makeText(ProfileActivity.this,"fail",Toast.LENGTH_LONG).show();
                        Log.d("test",t.getMessage());
                    }
                });
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}