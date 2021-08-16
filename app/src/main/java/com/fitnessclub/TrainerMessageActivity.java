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

import com.fitnessclub.Adapters.TrainerMessageAdapter;
import com.fitnessclub.Model.MessagePojo;
import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Networking.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainerMessageActivity extends AppCompatActivity {

    String frm;
    String eto;
    String pid="3";
    List<MessagePojo> msg=new ArrayList<MessagePojo>();

    EditText et_message;
    ProgressDialog pd;
    Button btnsend;
    TrainerMessageAdapter trainerMessageAdapter;
    Runnable r;
    RecyclerView recyclerView;
    Handler h=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_message);
        et_message=(EditText)findViewById(R.id.msgtext);
        btnsend=(Button)findViewById(R.id.btnsend);
        frm=getIntent().getStringExtra("from");
        eto=getIntent().getStringExtra("to");

        String email = frm;
        String[] parts = email.split("@");

        // now parts[0] contains "example"
        // and parts[1] contains "domain.com"

        setTitle(parts[0]);
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_message.getText().toString().isEmpty())
                {
                    Toast.makeText(TrainerMessageActivity.this,"Enter message", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(TrainerMessageActivity.this, frm+eto, Toast.LENGTH_SHORT).show();
                sendMessage(getIntent().getStringExtra("to"),getIntent().getStringExtra("from"));
            }
        });

        final RecyclerView recyclerView=findViewById(R.id.messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        trainerMessageAdapter=new TrainerMessageAdapter(msg,frm,TrainerMessageActivity.this);
        recyclerView.setAdapter(trainerMessageAdapter);

        r =new Runnable() {
            @Override
            public void run() {
                h.postDelayed(r,9000);
                getmessages();
            }
        };

        h.post(r);


    }

    public void getmessages(){
        Call<List<MessagePojo>> call= ServiceGenerator.getGymApi().getUserChat(frm, eto);
        call.enqueue(new Callback<List<MessagePojo>>() {
            @Override
            public void onResponse(Call<List<MessagePojo>> call, Response<List<MessagePojo>> response) {
                if(response.body()==null){
                    Toast.makeText(TrainerMessageActivity.this,"No data found", Toast.LENGTH_SHORT).show();
                }else {
                    msg = response.body();
                    trainerMessageAdapter.data(msg);
                }
            }

            @Override
            public void onFailure(Call<List<MessagePojo>> call, Throwable t) {

            }
        });
    }
    public void sendMessage(final String frm, final String eto) {
        Call<SuccessOrFailureResponse> call= ServiceGenerator.getGymApi().sendMessage(frm,eto,et_message.getText().toString());
        call.enqueue(new Callback<SuccessOrFailureResponse>() {
            @Override
            public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                getmessages();
                et_message.setText("");
                if (response.body().getMessage().equals("true")) {
                    Toast.makeText(TrainerMessageActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    et_message.setText("");
                } else {
                    Toast.makeText(TrainerMessageActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SuccessOrFailureResponse> call, Throwable t) {
            }
        });
    }
}