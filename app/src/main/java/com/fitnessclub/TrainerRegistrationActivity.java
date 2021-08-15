package com.fitnessclub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Networking.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainerRegistrationActivity extends AppCompatActivity {

    EditText email, password,phone,fullname;
    Spinner gender,type;
    String logintype[] = {"Select Login Type", "user"};
    String exp[] = {"Select Exp ","1 year", "2 years", "3 Years", "5+ years"};
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_registration);
        register=findViewById(R.id.tRegister);
        fullname=findViewById(R.id.tfullname);
        email=findViewById(R.id.temail);
        phone=findViewById(R.id.tphonenumber);
        password=findViewById(R.id.tpassword);
        gender=findViewById(R.id.exp);
        type=findViewById(R.id.logintype);

        ArrayAdapter aa1 = new ArrayAdapter(this, R.layout.spinner_list,exp);
        aa1.setDropDownViewResource(R.layout.spinner_list);
        gender.setAdapter(aa1);
        gender.setSelection(0);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEmpty(fullname) || isEmpty(password) || isEmpty(email) || isEmpty(phone) ){
                    Toast.makeText(TrainerRegistrationActivity.this, "Fields cannot be left empty", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(TrainerRegistrationActivity.this,"Registered Successfully",Toast.LENGTH_LONG).show();

                    //Toast.makeText(RegistrationActivity.this,getText(fullname)+getText(email)+getText(password)+getText(phonenumber)+gender.getSelectedItem().toString()+type.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
                    Call<SuccessOrFailureResponse> reg= ServiceGenerator.getGymApi().trainerregister(getText(fullname),
                            getText(email),getText(password),getText(phone),gender.getSelectedItem().toString());
                    reg.enqueue(new Callback<SuccessOrFailureResponse>() {
                        @Override
                        public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                            Toast.makeText(TrainerRegistrationActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                            Log.d("test123",response.toString());

                            Intent i = new Intent(TrainerRegistrationActivity.this, LoginActivity.class);
                            startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<SuccessOrFailureResponse> call, Throwable t) {
                            //  Toast.makeText(TrainerRegistrationActivity.this,"fail",Toast.LENGTH_LONG).show();
                            Log.d("test123",t.getMessage());
                        }
                    });

                    Intent i = new Intent(TrainerRegistrationActivity.this, LoginActivity.class);
                    startActivity(i);
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
}