package com.fitnessclub.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnessclub.BookTrainerActivity;
import com.fitnessclub.Model.BookingsPojo;
import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Model.TrainersPojo;
import com.fitnessclub.Networking.ServiceGenerator;
import com.fitnessclub.R;
import com.fitnessclub.TrainerHomeActivity;
import com.fitnessclub.User_HomeActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserMybookingsAdapter extends BaseAdapter {
    List<BookingsPojo> bookPojo=new ArrayList<>();
    Context con;
    String id;
    ProgressDialog progressDialog;
    public UserMybookingsAdapter(  List<BookingsPojo> bookPojo, Context con){
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_usermybookings,null);
        TextView tvname=v.findViewById(R.id.tvname);
        TextView tvmessage=v.findViewById(R.id.tvmessage);
        TextView tvstatus=v.findViewById(R.id.tvstatus);
        TextView tvdate=v.findViewById(R.id.tvdate);
        TextView tvtime=v.findViewById(R.id.tvtime);

        Button btncancel=v.findViewById(R.id.btncancel);



        if(bookPojo.get(position).getStatus().equals("Booked"))
        {
            btncancel.setVisibility(View.VISIBLE);

            btncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancelBooking(bookPojo.get(position).getBid());
                }
            });
        }
        else
        {
            btncancel.setVisibility(View.GONE);
        }

        tvname.setText("Trainer Name : "+bookPojo.get(position).getFullname());
        tvmessage.setText("Message : "+bookPojo.get(position).getMessage());
        tvstatus.setText("Status : "+bookPojo.get(position).getStatus());
        tvtime.setText("Time : "+bookPojo.get(position).getTim());

        tvdate.setText(bookPojo.get(position).getDat());
        return v;
    }


    public void cancelBooking(final String bid)
    {
        progressDialog = new ProgressDialog(con);
        progressDialog.setMessage("Cancel Booking");
        progressDialog.show();
        Call<SuccessOrFailureResponse> call = ServiceGenerator.getGymApi().cancelbooking(bid);
        call.enqueue(new Callback<SuccessOrFailureResponse>() {
            @Override
            public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(con,"Something wrong",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent(con, User_HomeActivity.class);
                    con.startActivity(intent);

                    Toast.makeText(con,"Booking Cancelled successfully",Toast.LENGTH_SHORT).show();
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
