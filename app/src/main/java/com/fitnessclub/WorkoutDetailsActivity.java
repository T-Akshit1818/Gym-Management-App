package com.fitnessclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class WorkoutDetailsActivity extends AppCompatActivity {

    TextView tv_type,tv_level,tv_time,tv_about;
    ImageView workimage;
    Button btncomplaint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);
        getSupportActionBar().setTitle("Workout Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_type=(TextView)findViewById(R.id.tv_type);
        tv_level=(TextView)findViewById(R.id.tv_level);
        tv_time=(TextView)findViewById(R.id.tv_time);
        tv_about=(TextView)findViewById(R.id.tv_about);
        btncomplaint=(Button)findViewById(R.id.btncomplaint);
        btncomplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WorkoutDetailsActivity.this, ComplaintActivity.class);
                intent.putExtra("id",getIntent().getStringExtra("tid"));
                startActivity(intent);
            }
        });

        workimage=(ImageView)findViewById(R.id.workimage);
        Glide.with(getApplicationContext()).load("http://getfitt.club/fitnessclub/"+getIntent().getStringExtra("image")).into(workimage);

        tv_type.setText(getIntent().getStringExtra("type"));
        tv_level.setText(getIntent().getStringExtra("level"));
        tv_time.setText(getIntent().getStringExtra("tim"));
        tv_about.setText(getIntent().getStringExtra("about"));
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