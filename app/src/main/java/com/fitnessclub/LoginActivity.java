package com.fitnessclub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Networking.ServiceGenerator;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Spinner spinner;

    Button login;
    TextView forgotpassword, new_user,new_trainer;
    EditText username, password;
    String type[] = {"Select Login Type", "admin", "user", "Trainer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spinner = findViewById(R.id.logintype);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username);
        new_user = findViewById(R.id.new_user);

        new_trainer=findViewById(R.id.new_trainer);
        new_trainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, TrainerRegistrationActivity.class);
                startActivity(i);
            }
        });
        forgotpassword = findViewById(R.id.forgotpassword);
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_list, type);
        aa.setDropDownViewResource(R.layout.spinner_list);
        spinner.setAdapter(aa);
        spinner.setSelection(0);
        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(username) || isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Fields cannot be left empty", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (spinner.getSelectedItem().toString().equals("admin")) {
                        if (getText(username).equals("admin") && getText(password).equals("admin")) {
                            Intent i = new Intent(LoginActivity.this, AdminHome.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid Details for Admin", Toast.LENGTH_SHORT).show();


                        }
                    }

                    else if(spinner.getSelectedItem().toString().equals("user")){
                        Call<SuccessOrFailureResponse> reg = ServiceGenerator.getGymApi().login(getText(username), getText(password));
                        reg.enqueue(new Callback<SuccessOrFailureResponse>() {
                            @Override
                            public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                                Log.d("test123", response.toString());
                                SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
                                SharedPreferences.Editor et = sharedPreferences.edit();
                                et.putString("user_name", getText(username));
                                et.commit();
                                Intent i = new Intent(LoginActivity.this, User_HomeActivity.class);

                                startActivity(i);
                                finish();
                            }

                            @Override
                            public void onFailure(Call<SuccessOrFailureResponse> call, Throwable t) {
                                Log.d("test1234", t.getMessage());
                            }
                        });
                    }
                    else{
                        Call<SuccessOrFailureResponse> reg = ServiceGenerator.getGymApi().trainerlogin(getText(username), getText(password));
                        reg.enqueue(new Callback<SuccessOrFailureResponse>() {
                            @Override
                            public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                                Log.d("test123", response.toString());
                                SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
                                SharedPreferences.Editor et = sharedPreferences.edit();
                                et.putString("user_name", getText(username));
                                et.commit();
//                                Intent i = new Intent(LoginActivity.this, TrainerHomeActivity.class);
//
//                                startActivity(i);
//                                finish();
                            }

                            @Override
                            public void onFailure(Call<SuccessOrFailureResponse> call, Throwable t) {
                                Log.d("test1234", t.getMessage());
                            }
                        });
                    }
                }
            }
        });

    }

    private String getText(EditText editText) {
        return editText.getText().toString();
    }


    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().isEmpty();
    }
}