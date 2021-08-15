package com.fitnessclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.fitnessclub.Adapters.TrainerMyBookingsAdapter;
import com.fitnessclub.Networking.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainerMyBookingsActivity extends AppCompatActivity {
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_my_bookings);
        list=(ListView)findViewById(R.id.list);
        getSupportActionBar().setTitle("Bookings Requests");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String emailId = sharedPreferences.getString("user_name", "def-val");
        Call<List<BookingsPojo>> reg= ServiceGenerator.getGymApi().trainermybookings(emailId);
        reg.enqueue(new Callback<List<BookingsPojo>>() {
            @Override
            public void onResponse(Call<List<BookingsPojo>> call, Response<List<BookingsPojo>> response) {
                List<BookingsPojo> bookingsPojo=response.body();
                TrainerMyBookingsAdapter userMybookingsAdapter=new TrainerMyBookingsAdapter(bookingsPojo, TrainerMyBookingsActivity.this);
                list.setAdapter(userMybookingsAdapter);
            }

            @Override
            public void onFailure(Call<List<BookingsPojo>> call, Throwable t) {
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