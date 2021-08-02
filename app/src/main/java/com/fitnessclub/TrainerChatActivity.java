package com.fitnessclub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fitnessclub.Adapters.TrainerChatAdapter;
import com.fitnessclub.Model.MessagePojo;
import com.fitnessclub.Networking.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainerChatActivity extends AppCompatActivity {

    ListView list_view;
    ProgressDialog progressDialog;
    List<MessagePojo> message;
    SharedPreferences sharedPreferences;
    TrainerChatAdapter trainerChatAdapter;
    String session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_chat);
        getSupportActionBar().setTitle("Chat");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");

        list_view=(ListView)findViewById(R.id.list_view);
        message= new ArrayList<>();
        getMessages();
    }
    public void getMessages(){
        progressDialog = new ProgressDialog(TrainerChatActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        Call<List<MessagePojo>> call= ServiceGenerator.getGymApi().trainerchat(session);
        call.enqueue(new Callback<List<MessagePojo>>() {
            @Override
            public void onResponse(Call<List<MessagePojo>> call, Response<List<MessagePojo>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(TrainerChatActivity.this,"No data found", Toast.LENGTH_SHORT).show();
                }else {
                    message = response.body();
                    trainerChatAdapter=new TrainerChatAdapter(message,TrainerChatActivity.this);  //attach adapter class with therecyclerview
                    list_view.setAdapter(trainerChatAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<MessagePojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TrainerChatActivity.this, "Please try later!", Toast.LENGTH_SHORT).show();
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