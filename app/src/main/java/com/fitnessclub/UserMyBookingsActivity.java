package com.fitnessclub;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fitnessclub.Adapters.UserMybookingsAdapter;
import com.fitnessclub.Networking.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserMyBookingsActivity extends AppCompatActivity {

    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_bookings);
        list=(ListView)findViewById(R.id.list);
        getSupportActionBar().setTitle("My Bookings");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String emailId = sharedPreferences.getString("user_name", "def-val");
        Call<List<BookingsPojo>> reg= ServiceGenerator.getGymApi().usermybookings(emailId);
        reg.enqueue(new Callback<List<BookingsPojo>>() {
            @Override
            public void onResponse(Call<List<BookingsPojo>> call, Response<List<BookingsPojo>> response) {
                List<BookingsPojo> bookingsPojo=response.body();
                UserMybookingsAdapter userMybookingsAdapter=new UserMybookingsAdapter(bookingsPojo, UserMyBookingsActivity.this);
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