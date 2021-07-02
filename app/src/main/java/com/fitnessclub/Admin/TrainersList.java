package com.fitnessclub.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Call<List<UsersPojo>> reg= ServiceGenerator.getGymApi().gettrainerslist();
        reg.enqueue(new Callback<List<UsersPojo>>() {
            @Override
            public void onResponse(Call<List<UsersPojo>> call, Response<List<UsersPojo>> response) {
                List<UsersPojo> usersPojos=response.body();
                Log.d("test123",usersPojos.get(0).getEmail());
                AdminUsersAdapter adminUsersAdapter=new AdminUsersAdapter(usersPojos);
                list.setAdapter(adminUsersAdapter);
            }

            @Override
            public void onFailure(Call<List<UsersPojo>> call, Throwable t) {
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