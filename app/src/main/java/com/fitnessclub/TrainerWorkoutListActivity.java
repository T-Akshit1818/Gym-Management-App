package com.fitnessclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.fitnessclub.Adapters.Adaptertrainerworkoutlist;
import com.fitnessclub.Model.UsersPojo;
import com.fitnessclub.Model.WotoutPojo;
import com.fitnessclub.Networking.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainerWorkoutListActivity extends AppCompatActivity {

    ListView worklist;

    RecyclerView recyclerView;
    Adaptertrainerworkoutlist AdapterUser;
    ArrayList<WotoutPojo> list;
    List<WotoutPojo> wotoutPojo;
    ProgressDialog loading;
    GridView gridview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_workout_list);
        getSupportActionBar().setTitle("Workout Training Content");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gridview = (GridView) findViewById(R.id.gridview);
        wotoutPojo = new ArrayList<>();
        getWorkoutContent();

    }

    public void getWorkoutContent() {
        loading = new ProgressDialog(TrainerWorkoutListActivity.this);
        loading.setMessage("Loading....");
        loading.show();
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user_name", "def-val");
        Call<List<WotoutPojo>> call= ServiceGenerator.getGymApi().getmytraininglist(email);
        call.enqueue(new Callback<List<WotoutPojo>>() {
            @Override
            public void onResponse(Call<List<WotoutPojo>> call, Response<List<WotoutPojo>> response) {
                loading.dismiss();
                //   Toast.makeText(AdminHotelsActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                if (response.body() == null) {
                    Toast.makeText(TrainerWorkoutListActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    wotoutPojo = response.body();
                    gridview.setAdapter(new Adaptertrainerworkoutlist(wotoutPojo, TrainerWorkoutListActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<WotoutPojo>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(TrainerWorkoutListActivity.this, "Something went wrong...Please contact admin !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}