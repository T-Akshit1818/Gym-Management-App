package com.fitnessclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fitnessclub.Model.ProfilePojo;
import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Networking.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainerProfileActivity extends AppCompatActivity {
    EditText email, password,phonenumber,fullname;
    Button btntupdate;
    Spinner ex;
    String exp[] = {"Select Exp ","1 year", "2 years", "3 Years", "5+ years"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_profile);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fullname=findViewById(R.id.fullname);
        email=findViewById(R.id.email);
        phonenumber=findViewById(R.id.phonenumber);
        password=findViewById(R.id.password);

        ex=findViewById(R.id.exp);

        ArrayAdapter aa1 = new ArrayAdapter(this,R.layout.spinner_list,exp);
        aa1.setDropDownViewResource(R.layout.spinner_list);
        ex.setAdapter(aa1);
        ex.setSelection(0);


        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String emailId = sharedPreferences.getString("user_name", "def-val");


        Call<List<ProfilePojo>> reg= ServiceGenerator.getGymApi().trainerprofile(emailId);
        reg.enqueue(new Callback<List<ProfilePojo>>() {
            @Override
            public void onResponse(Call<List<ProfilePojo>> call, Response<List<ProfilePojo>> response) {
                Toast.makeText(TrainerProfileActivity.this,response.body().get(0).getEmail(),Toast.LENGTH_LONG).show();
                fullname.setText(response.body().get(0).getFullname());
                email.setText(response.body().get(0).getEmail());
                password.setText(response.body().get(0).getPass());
                phonenumber.setText(response.body().get(0).getPhone());
            }
            @Override
            public void onFailure(Call<List<ProfilePojo>> call, Throwable t) {
                Toast.makeText(TrainerProfileActivity.this,"fail",Toast.LENGTH_LONG).show();
            }
        });

        btntupdate=(Button)findViewById(R.id.btntupdate);
        btntupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(fullname) || isEmpty(password) || isEmpty(email) || isEmpty(phonenumber) ){
                    Toast.makeText(TrainerProfileActivity.this, "Fields cannot be left empty", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(TrainerProfileActivity.this,"Updated Successfully",Toast.LENGTH_LONG).show();

                    //Toast.makeText(RegistrationActivity.this,getText(fullname)+getText(email)+getText(password)+getText(phonenumber)+gender.getSelectedItem().toString()+type.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
                    Call<SuccessOrFailureResponse> reg= ServiceGenerator.getGymApi().trainer_update(getText(fullname),
                            getText(email),getText(password),getText(phonenumber),ex.getSelectedItem().toString());
                    reg.enqueue(new Callback<SuccessOrFailureResponse>() {
                        @Override
                        public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                            Intent i = new Intent(TrainerProfileActivity.this, TrainerHomeActivity.class);
                            startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<SuccessOrFailureResponse> call, Throwable t) {
                            Toast.makeText(TrainerProfileActivity.this,"fail",Toast.LENGTH_LONG).show();
                            Log.d("test",t.getMessage());
                        }
                    });

                }
            }
        });


    }
    private String getText(EditText editText){
        return editText.getText().toString();
    }


    private boolean isEmpty(EditText editText){
        return editText.getText().toString().isEmpty();
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