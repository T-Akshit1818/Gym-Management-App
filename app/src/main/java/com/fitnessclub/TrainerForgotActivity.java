package com.fitnessclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Networking.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainerForgotActivity extends AppCompatActivity {

    EditText textEmailAddress;
    Button btngetpassword;
    SharedPreferences sharedPreferences;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_forgot);
        getSupportActionBar().setTitle("Retrive Password");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        email=sharedPreferences.getString("user_name","");

        btngetpassword=(Button)findViewById(R.id.btngetpassword);
        textEmailAddress=(EditText)findViewById(R.id.textEmailAddress);
        btngetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textEmailAddress.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Enter your Email",Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    Call<SuccessOrFailureResponse> reg= ServiceGenerator.getGymApi().forgottrainer(textEmailAddress.getText().toString());
                    reg.enqueue(new Callback<SuccessOrFailureResponse>() {
                        @Override
                        public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                            if (response.body().getStatus().equals("true")) {
                                Intent s=new Intent(TrainerForgotActivity.this,LoginActivity.class);
                                startActivity(s);
                                Toast.makeText(TrainerForgotActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(TrainerForgotActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SuccessOrFailureResponse> call, Throwable t) {
                            Toast.makeText(TrainerForgotActivity.this, t.toString(), Toast.LENGTH_LONG).show();

                        }
                    });
                }

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
