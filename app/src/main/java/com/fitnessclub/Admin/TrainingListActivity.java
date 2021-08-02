package com.fitnessclub.Admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fitnessclub.Adapters.AdapterAdminviewTrainingList;
import com.fitnessclub.Model.WotoutPojo;
import com.fitnessclub.Networking.ServiceGenerator;
import com.fitnessclub.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainingListActivity extends AppCompatActivity {

    List<WotoutPojo> wotoutPojo;
    ListView list_view;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);
        getSupportActionBar().setTitle("Workout List");

        list_view = (ListView) findViewById(R.id.training_list);
        wotoutPojo = new ArrayList<>();
        loading = new ProgressDialog(TrainingListActivity.this);
        loading.setMessage("List loading");
        loading.show();
        Call<List<WotoutPojo>> call= ServiceGenerator.getGymApi().gettraining();
        call.enqueue(new Callback<List<WotoutPojo>>() {
            @Override
            public void onResponse(Call<List<WotoutPojo>> call, Response<List<WotoutPojo>> response) {
                loading.dismiss();
                //   Toast.makeText(AdminHotelsActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                if (response.body() == null) {
                    Toast.makeText(TrainingListActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    wotoutPojo = response.body();
                    list_view.setAdapter(new AdapterAdminviewTrainingList(wotoutPojo, TrainingListActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<WotoutPojo>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(TrainingListActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }


}