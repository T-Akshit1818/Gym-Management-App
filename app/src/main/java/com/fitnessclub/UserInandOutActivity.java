package com.fitnessclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Networking.ServiceGenerator;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInandOutActivity extends AppCompatActivity {

    Button btnin;
    TextView tvdate,tvintime;
    String email,dat,in,out;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_inand_out);
        getSupportActionBar().setTitle("Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnin=(Button)findViewById(R.id.btnin);
        tvdate=(TextView)findViewById(R.id.tvdate);
        tvintime=(TextView)findViewById(R.id.tvintime);
        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        email = sharedPreferences.getString("user_name", "def-val");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        tvdate.setText("Today Date : " +formatter.format(date));
        dat=formatter.format(date);
        in=formatter1.format(date);
        out=formatter2.format(date);
        if(!sharedPreferences.getString("in_time", "def-val").equals("def-val"))
        {
            tvintime.setVisibility(View.VISIBLE);
            tvintime.setText("In Time : "+sharedPreferences.getString("in_time", "def-val"));
            btnin.setText("Out");
        }

        btnin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnin.getText().toString().equals("Out"))
                {
                    Call<SuccessOrFailureResponse> reg = ServiceGenerator.getGymApi().out(email,dat,sharedPreferences.getString("in_time", "def-val"),out);

                    reg.enqueue(new Callback<SuccessOrFailureResponse>() {
                        @Override
                        public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                            if (response.isSuccessful()) {
                                SharedPreferences sharedPreferences1 = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
                                SharedPreferences.Editor et = sharedPreferences1.edit();
                                et.remove("in_time");
                                et.commit();
                                Toast.makeText(UserInandOutActivity.this, "Out Successfully", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(UserInandOutActivity.this, User_HomeActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(UserInandOutActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SuccessOrFailureResponse> call, Throwable t) {
                            Log.d("test123", t.getMessage());
                        }
                    });
                }
                else {
                    Call<SuccessOrFailureResponse> reg = ServiceGenerator.getGymApi().in(email, dat, in);

                    reg.enqueue(new Callback<SuccessOrFailureResponse>() {
                        @Override
                        public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                            if (response.isSuccessful()) {
                                SharedPreferences sharedPreferences1 = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
                                SharedPreferences.Editor et = sharedPreferences1.edit();
                                et.putString("in_time", in);
                                et.commit();
                                Toast.makeText(UserInandOutActivity.this, "In Successfully", Toast.LENGTH_LONG).show();
                                // Log.d("test123",response.toString());
                                Intent i = new Intent(UserInandOutActivity.this, User_HomeActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(UserInandOutActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();

                            }


                        }

                        @Override
                        public void onFailure(Call<SuccessOrFailureResponse> call, Throwable t) {
                            //  Toast.makeText(RegistrationActivity.this,"fail",Toast.LENGTH_LONG).show();
                            Log.d("test123", t.getMessage());
                        }
                    });
                }

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