package com.fitnessclub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Networking.ServiceGenerator;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookTrainerActivity extends AppCompatActivity {
    int mYear, mMonth, mDay;
    String DAY, MONTH, YEAR;
    EditText etmessage,etname,et_date;
    Button btnbooktrainer;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_trainer);


        etmessage=(EditText)findViewById(R.id.etmessage);

        etname=(EditText)findViewById(R.id.etname);

        et_date=(EditText)findViewById(R.id.et_date);
        et_date.setFocusable(false);
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bdate();
            }
        });
        btnbooktrainer=(Button) findViewById(R.id.btnbooktrainer);
        btnbooktrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etname.getText().toString().isEmpty()){
                    Toast.makeText(BookTrainerActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(etmessage.getText().toString().isEmpty()){
                    Toast.makeText(BookTrainerActivity.this, "Enter message", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    BookTrainer();
                }

            }
        });

    }

    private void BookTrainer()
    {
        loading = new ProgressDialog(BookTrainerActivity.this);
        loading.setMessage("Loading....");
        loading.show();
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String emailId = sharedPreferences.getString("user_name", "def-val");
        String tid=getIntent().getStringExtra("tid");
        Toast.makeText(BookTrainerActivity.this,tid,Toast.LENGTH_LONG).show();

        Call<SuccessOrFailureResponse> reg= ServiceGenerator.getGymApi().booktrainer(tid,et_date.getText().toString(),etmessage.getText().toString(),etname.getText().toString(),emailId);
        reg.enqueue(new Callback<SuccessOrFailureResponse>() {
            @Override
            public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                if(response.isSuccessful())
                {
                    loading.dismiss();
                    Toast.makeText(BookTrainerActivity.this,response.body().toString(),Toast.LENGTH_LONG).show();
                    // Log.d("test123",response.toString());
//                    Intent i = new Intent(BookTrainerActivity.this, UsertrainersListActivity.class);
//                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(BookTrainerActivity.this,response.body().toString(),Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }


            }

            @Override
            public void onFailure(Call<SuccessOrFailureResponse> call, Throwable t) {
                //  Toast.makeText(RegistrationActivity.this,"fail",Toast.LENGTH_LONG).show();
                Log.d("test123",t.getMessage());
            }
        });
    }

    public void bdate() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(BookTrainerActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        DAY = dayOfMonth + "";
                        MONTH = monthOfYear + 1 + "";
                        YEAR = year + "";

                        et_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
    }
}