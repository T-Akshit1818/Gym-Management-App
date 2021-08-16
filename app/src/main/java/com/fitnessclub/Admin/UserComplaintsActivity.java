package com.fitnessclub.Admin;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fitnessclub.Model.ComplaintsPojo;
import com.fitnessclub.Networking.ServiceGenerator;
import com.fitnessclub.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserComplaintsActivity extends AppCompatActivity {
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_complaints);
        list=(ListView)findViewById(R.id.list);
        getSupportActionBar().setTitle("Complaints");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Call<List<ComplaintsPojo>> reg= ServiceGenerator.getGymApi().getcomplaints();
        reg.enqueue(new Callback<List<ComplaintsPojo>>() {
            @Override
            public void onResponse(Call<List<ComplaintsPojo>> call, Response<List<ComplaintsPojo>> response) {
                List<ComplaintsPojo> usersPojos=response.body();
                Log.d("test123",usersPojos.get(0).getEmail());
                AdminComplaintAdapter adapter=new AdminComplaintAdapter(usersPojos,UserComplaintsActivity.this);
                list.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<ComplaintsPojo>> call, Throwable t) {
                Log.d("test123",t.getMessage().toString());
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