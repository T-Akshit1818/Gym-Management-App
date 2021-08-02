package com.fitnessclub.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnessclub.Model.BookingsPojo;
import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Networking.ServiceGenerator;
import com.fitnessclub.R;
import com.fitnessclub.TrainerHomeActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainerMyBookingsAdapter extends BaseAdapter {
    List<BookingsPojo> bookPojo=new ArrayList<>();
    Context con;
    String id;
    ProgressDialog progressDialog;
    public TrainerMyBookingsAdapter(  List<BookingsPojo> bookPojo, Context con){
        this.bookPojo=bookPojo;
        this.con = con;
    }
    @Override
    public int getCount() {
        return bookPojo.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_trainermybookings,null);
        TextView tvname=v.findViewById(R.id.tvname);
        TextView tvmessage=v.findViewById(R.id.tvmessage);
        TextView tvstatus=v.findViewById(R.id.tvstatus);
        TextView tvdate=v.findViewById(R.id.tvdate);
        Button btnconfirm=v.findViewById(R.id.btnconfirm);
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Confirm(bookPojo.get(position).getBid());
            }
        });

        tvname.setText("User Name : "+bookPojo.get(position).getName());
        tvmessage.setText("Message : "+bookPojo.get(position).getMessage());
        tvstatus.setText("Status : "+bookPojo.get(position).getStatus());
        tvdate.setText(bookPojo.get(position).getDat());
        return v;
    }

    public void Confirm(final String bid)
    {
        progressDialog = new ProgressDialog(con);
        progressDialog.setMessage("Confirming Booking");
        progressDialog.show();
        Call<SuccessOrFailureResponse> call = ServiceGenerator.getGymApi().confirmbooking(bid);
        call.enqueue(new Callback<SuccessOrFailureResponse>() {
            @Override
            public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(con,"Something wrong",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent(con, TrainerHomeActivity.class);
                    con.startActivity(intent);

                    Toast.makeText(con,"Booking Confirmed successfully",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SuccessOrFailureResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(con, "Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}