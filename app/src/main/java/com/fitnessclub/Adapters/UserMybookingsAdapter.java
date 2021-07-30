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

import com.fitnessclub.BookTrainerActivity;
import com.fitnessclub.Model.BookingsPojo;
import com.fitnessclub.Model.TrainersPojo;
import com.fitnessclub.R;

import java.util.ArrayList;
import java.util.List;

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

        tvname.setText("Trainer Name : "+bookPojo.get(position).getFullname());
        tvmessage.setText("Message : "+bookPojo.get(position).getMessage());
        tvstatus.setText("Status : "+bookPojo.get(position).getStatus());
        tvdate.setText(bookPojo.get(position).getDat());
        return v;
    }

}
