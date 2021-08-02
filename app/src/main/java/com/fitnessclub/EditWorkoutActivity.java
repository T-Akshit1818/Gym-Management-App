package com.fitnessclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Networking.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditWorkoutActivity extends AppCompatActivity {

    private static final String TAG = AddWorkoutActivity.class.getSimpleName();
    private static final String SERVER_PATH = "http://getfitt.club/";

    EditText about;
    Button btn_uploadpic,btnaddworkout,btnupdateworkout;
    ProgressDialog load;

    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;

    private Uri uri;
    RatingBar rv_rating;
    SharedPreferences sharedPreferences;
    String session;
    Spinner type,level,tim,gender;
    String g[] = {"male","female"};

    String ty[] = {"Walking", "Interval training", "Squats", "Push-ups", "Biceps", "Cardio"};

    String l[] = {"Endurance", "Strength", "Balance", "Flexibility"};

    String t[] = {"15 mins", "30 mins", "45 mins"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        getSupportActionBar().setTitle("Workout Information");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");

        type=(Spinner) findViewById(R.id.type);
        level=(Spinner)findViewById(R.id.level);
        tim=(Spinner)findViewById(R.id.time);
        about=(EditText)findViewById(R.id.about);
        gender=(Spinner)findViewById(R.id.gender);
        btnupdateworkout=(Button)findViewById(R.id.btnupdateworkout);
        btnupdateworkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateWorkout();
            }
        });

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_list, g);
        aa.setDropDownViewResource(R.layout.spinner_list);
        gender.setAdapter(aa);
        gender.setSelection(0);


        ArrayAdapter aa1 = new ArrayAdapter(this, R.layout.spinner_list, ty);
        aa1.setDropDownViewResource(R.layout.spinner_list);
        type.setAdapter(aa1);
        type.setSelection(0);

        ArrayAdapter aa2 = new ArrayAdapter(this, R.layout.spinner_list, l);
        aa2.setDropDownViewResource(R.layout.spinner_list);
        level.setAdapter(aa2);
        level.setSelection(0);

        ArrayAdapter aa3 = new ArrayAdapter(this, R.layout.spinner_list, t);
        aa3.setDropDownViewResource(R.layout.spinner_list);
        tim.setAdapter(aa3);
        tim.setSelection(0);

        about.setText(getIntent().getStringExtra("about"));

    }

    private void updateWorkout()
    {
        String ty = type.getSelectedItem().toString();
        String le = level.getSelectedItem().toString();
        String time = tim.getSelectedItem().toString();
        String gen = gender.getSelectedItem().toString();
        String abt= about.getText().toString();
        String tid =(getIntent().getStringExtra("tid"));

        Call<SuccessOrFailureResponse> reg= ServiceGenerator.getGymApi().edittrainingcontent(ty,le,time,abt,gen,tid);
        reg.enqueue(new Callback<SuccessOrFailureResponse>() {
            @Override
            public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(EditWorkoutActivity.this,"Updated Successfully",Toast.LENGTH_LONG).show();
//                    Intent i = new Intent(EditWorkoutActivity.this, TrainerHomeActivity.class);
//                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(EditWorkoutActivity.this,response.body().toString(),Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<SuccessOrFailureResponse> call, Throwable t) {
                  Toast.makeText(EditWorkoutActivity.this,"server issue",Toast.LENGTH_LONG).show();
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