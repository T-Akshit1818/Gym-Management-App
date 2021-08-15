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
import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Model.TrainersPojo;
import com.fitnessclub.Model.UsersPojo;
import com.fitnessclub.Networking.ServiceGenerator;
import com.fitnessclub.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminUsersAdapter  extends BaseAdapter {
    List<UsersPojo> usersPojoList=new ArrayList<>();
    Context con;
    String id;
    ProgressDialog progressDialog;
    AdminUsersAdapter(  List<UsersPojo> usersPojoList, Context con){
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adminuserlist,null);
        TextView name=v.findViewById(R.id.name);
        TextView email=v.findViewById(R.id.email);

        ImageView delete=v.findViewById(R.id.delete);

        name.setText(usersPojoList.get(position).getFullname());
        email.setText(usersPojoList.get(position).getEmail());
        id=usersPojoList.get(position).getId();
        //  Toast.makeText(con, id, Toast.LENGTH_SHORT).show();


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(usersPojoList.get(position).getId());
                Toast.makeText(con, usersPojoList.get(position).getId().toString(), Toast.LENGTH_SHORT).show();
            }

        });

        return v;
    }

    public void deleteUser(final String uid)
    {
        progressDialog = new ProgressDialog(con);
        progressDialog.setMessage("Deleting Trainer....");
        progressDialog.show();
        Call<SuccessOrFailureResponse> call = ServiceGenerator.getGymApi().deleteuser(uid);
        call.enqueue(new Callback<SuccessOrFailureResponse>() {
            @Override
            public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(con,"Server issue",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent(con, AdminHome.class);
                    con.startActivity(intent);

                    Toast.makeText(con,"Deleted successfully",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SuccessOrFailureResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(con, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }





}
