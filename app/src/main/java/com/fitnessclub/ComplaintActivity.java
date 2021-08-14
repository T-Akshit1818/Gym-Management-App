package com.fitnessclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Networking.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplaintActivity extends AppCompatActivity {

    EditText tfullname,temail,tvmsg;
    Button btnraise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        tfullname=(EditText)findViewById(R.id.tfullname);
        temail=(EditText)findViewById(R.id.temail);
        tvmsg=(EditText)findViewById(R.id.tvmsg);

        btnraise=(Button)findViewById(R.id.btnraise);
        btnraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tfullname.getText().toString().equals("")){
                    Toast.makeText(ComplaintActivity.this, "Fullname cannot be  empty", Toast.LENGTH_SHORT).show();
                }
                else if (temail.getText().toString().equals("")) {
                    Toast.makeText(ComplaintActivity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if (tvmsg.getText().toString().equals("")) {
                    Toast.makeText(ComplaintActivity.this, "Message cannot be left empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ComplaintActivity.this,"Complaint Raised Successfully",Toast.LENGTH_LONG).show();
                    Call<SuccessOrFailureResponse> reg= ServiceGenerator.getGymApi().complaint(tfullname.getText().toString(),temail.getText().toString(),tvmsg.getText().toString(),getIntent().getStringExtra("id"));
                    reg.enqueue(new Callback<SuccessOrFailureResponse>() {
                        @Override
                        public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                            Toast.makeText(ComplaintActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                            Intent i = new Intent(ComplaintActivity.this, User_HomeActivity.class);
                            startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<SuccessOrFailureResponse> call, Throwable t) {
                            Toast.makeText(ComplaintActivity.this,"Server Issue",Toast.LENGTH_LONG).show();

                        }
                    });

                    Intent i = new Intent(ComplaintActivity.this, User_HomeActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}