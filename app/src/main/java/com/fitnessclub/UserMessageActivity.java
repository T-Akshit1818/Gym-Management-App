package com.fitnessclub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fitnessclub.Adapters.UserMessageAdapter;
import com.fitnessclub.Model.MessagePojo;
import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Model.TrainersPojo;
import com.fitnessclub.Networking.GymApi;
import com.fitnessclub.Networking.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserMessageActivity extends AppCompatActivity {

    GymApi apiService;
    String frm;
    String eto;
    String pid = "3";
    List<MessagePojo> msg = new ArrayList<MessagePojo>();

    EditText et_message;
    ProgressDialog pd;
    UserMessageAdapter userMessageAdapter;
    Button btn_send;
    Runnable r;
    RecyclerView recyclerView;
    Handler h = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_message);

        et_message = (EditText) findViewById(R.id.et_message);
        btn_send = (Button) findViewById(R.id.btn_send);
        frm = getIntent().getStringExtra("from");
        eto = getIntent().getStringExtra("to");
        setTitle(getIntent().getStringExtra("name"));

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(UserMessageActivity.this, frm+eto, Toast.LENGTH_SHORT).show();
                if (et_message.getText().toString().isEmpty()) {
                    Toast.makeText(UserMessageActivity.this,  "Enter chat", Toast.LENGTH_SHORT).show();
                }
                sendMessage(getIntent().getStringExtra("from"), getIntent().getStringExtra("to"));
            }
        });


        Log.d("gym", frm + "" + eto);
        recyclerView = findViewById(R.id.messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        userMessageAdapter = new UserMessageAdapter(msg, frm, UserMessageActivity.this);
        recyclerView.setAdapter(userMessageAdapter);

        r = new Runnable() {
            @Override
            public void run() {
                h.postDelayed(r, 9000);
                getmessages();


            }
        };
        h.post(r);
    }

    public void getmessages() {
       Call<List<MessagePojo>> call= ServiceGenerator.getGymApi().getUserChat(frm, eto);
        call.enqueue(new Callback<List<MessagePojo>>() {
            @Override
            public void onResponse(Call<List<MessagePojo>> call, Response<List<MessagePojo>> response) {
                if (response.body() == null) {
                    Toast.makeText(UserMessageActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    if (response.body().size() > 0) {
                        msg.clear();
                        msg.addAll(response.body());
                        userMessageAdapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(msg.size() - 1);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<MessagePojo>> call, Throwable t) {

            }
        });
    }

    public void sendMessage(final String frm, final String eto) {
        Call<SuccessOrFailureResponse> call= ServiceGenerator.getGymApi().sendMessage(frm, eto,et_message.getText().toString());
        call.enqueue(new Callback<SuccessOrFailureResponse>() {
            @Override
            public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                getmessages();
                et_message.setText("");
                if (response.body().getMessage().equals("true")) {
                    Toast.makeText(UserMessageActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    et_message.setText("");
                } else {
                    Toast.makeText(UserMessageActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SuccessOrFailureResponse> call, Throwable t) {
            }
        });
    }
}