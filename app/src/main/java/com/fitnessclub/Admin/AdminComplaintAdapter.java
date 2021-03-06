package com.fitnessclub.Admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnessclub.AdminHome;
import com.fitnessclub.Model.ComplaintsPojo;
import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Model.TrainersPojo;
import com.fitnessclub.Networking.ServiceGenerator;
import com.fitnessclub.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminComplaintAdapter extends BaseAdapter {
    List<ComplaintsPojo> usersPojoList=new ArrayList<>();
    Context con;
    String id;
    ProgressDialog progressDialog;
    AdminComplaintAdapter(List<ComplaintsPojo> usersPojoList, Context con){
        this.usersPojoList=usersPojoList;
        this.con = con;
    }
    @Override
    public int getCount() {
        return usersPojoList.size();
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.admincomplaintlist,null);
        TextView name=v.findViewById(R.id.name);
        TextView email=v.findViewById(R.id.email);
        TextView type=v.findViewById(R.id.type);
        TextView msg=v.findViewById(R.id.msg);


        name.setText(usersPojoList.get(position).getFullname());
        email.setText(usersPojoList.get(position).getEmail());
        type.setText(usersPojoList.get(position).getType());
        msg.setText(usersPojoList.get(position).getMsg());
        return v;
    }

}