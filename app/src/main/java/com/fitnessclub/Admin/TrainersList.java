package com.fitnessclub.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.fitnessclub.Model.TrainersPojo;
import com.fitnessclub.Model.UsersPojo;
import com.fitnessclub.Networking.ServiceGenerator;
import com.fitnessclub.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainersList extends AppCompatActivity {
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        list=(ListView)findViewById(R.id.list);
        getSupportActionBar().setTitle("Trainers List");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Call<List<TrainersPojo>> reg= ServiceGenerator.getGymApi().gettrainerslist();
        reg.enqueue(new Callback<List<TrainersPojo>>() {
            @Override
            public void onResponse(Call<List<TrainersPojo>> call, Response<List<TrainersPojo>> response) {
                List<TrainersPojo> usersPojos=response.body();
                Log.d("test123",usersPojos.get(0).getEmail());
                AdminTrainerAdapter adminTrainerAdapter=new AdminTrainerAdapter(usersPojos,TrainersList.this);
                list.setAdapter(adminTrainerAdapter);
            }

            @Override
            public void onFailure(Call<List<TrainersPojo>> call, Throwable t) {
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