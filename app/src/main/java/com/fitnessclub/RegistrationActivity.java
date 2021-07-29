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

import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Networking.ServiceGenerator;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    EditText email, password,phonenumber,fullname;
    Spinner gender,type;
    String logintype[] = {"Select Login Type", "user"};
    String gendertype[] = {"Select Gender ","male", "female"};
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        register=(Button) findViewById(R.id.Register);
        fullname=findViewById(R.id.fullname);
        email=findViewById(R.id.email);
        phonenumber=findViewById(R.id.phonenumber);
        password=findViewById(R.id.password);
        gender=findViewById(R.id.gender);
        type=findViewById(R.id.logintype);

        ArrayAdapter aa = new ArrayAdapter(this,R.layout.spinner_list,logintype);
        aa.setDropDownViewResource(R.layout.spinner_list);
        type.setAdapter(aa);
        type.setSelection(0);

        ArrayAdapter aa1 = new ArrayAdapter(this,R.layout.spinner_list,gendertype);
        aa1.setDropDownViewResource(R.layout.spinner_list);
        gender.setAdapter(aa1);
        gender.setSelection(0);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEmpty(fullname) || isEmpty(password) || isEmpty(email) || isEmpty(phonenumber) ){
                    Toast.makeText(RegistrationActivity.this, "Fields cannot be left empty", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(RegistrationActivity.this,"Registered Successfully",Toast.LENGTH_LONG).show();

                    userRegistration();

                    Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(i);
                }


            }
        });
    }


    private void userRegistration()
    {

        Call<SuccessOrFailureResponse> reg= ServiceGenerator.getGymApi().register(getText(fullname),
                getText(email),getText(password),getText(phonenumber),gender.getSelectedItem().toString());
        reg.enqueue(new Callback<SuccessOrFailureResponse>() {
            @Override
            public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(RegistrationActivity.this,response.body().toString(),Toast.LENGTH_LONG).show();
                    // Log.d("test123",response.toString());
                    Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(RegistrationActivity.this,response.body().toString(),Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<SuccessOrFailureResponse> call, Throwable t) {
                //  Toast.makeText(RegistrationActivity.this,"fail",Toast.LENGTH_LONG).show();
                Log.d("test123",t.getMessage());
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