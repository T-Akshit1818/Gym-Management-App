package com.fitnessclub.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.fitnessclub.Model.TrainersPojo;
import com.fitnessclub.R;
import com.fitnessclub.UserMessageActivity;
import com.fitnessclub.Utils;

import java.util.ArrayList;
import java.util.List;

public class UserTrainersListAdapter extends BaseAdapter {
    List<TrainersPojo> usersPojoList=new ArrayList<>();
    Context con;
    String id;
    SharedPreferences sharedPreferences;
    String session;
    ProgressDialog progressDialog;
    public UserTrainersListAdapter(List<TrainersPojo> usersPojoList, Context con){
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_usertrainerlist,null);
        TextView name=v.findViewById(R.id.name);
        TextView email=v.findViewById(R.id.email);
        TextView exp=v.findViewById(R.id.exp);

        ImageView imgcall=v.findViewById(R.id.imgcall);
        imgcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone =usersPojoList.get(position).getPhone() ;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                con.startActivity(intent);
            }
        });


        ImageView imgmsg=v.findViewById(R.id.imgmsg);
        imgmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = con.getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
                session = sharedPreferences.getString("user_name", "def-val");
                Intent intent=new Intent(con, UserMessageActivity.class);
                intent.putExtra("from",session);
                intent.putExtra("to",usersPojoList.get(position).getEmail());
                con.startActivity(intent);
            }
        });

        Button btnbook=(Button)v.findViewById(R.id.btnbook);
        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(con, BookTrainerActivity.class);
                intent.putExtra("tid",usersPojoList.get(position).getTid());
                con.startActivity(intent);
            }
        });


        name.setText(usersPojoList.get(position).getFullname());
        email.setText(usersPojoList.get(position).getEmail());
        exp.setText(usersPojoList.get(position).getExp());
        RatingBar rv_rating= (RatingBar)v.findViewById(R.id.rv_rating);
        rv_rating.setRating(Float.parseFloat(usersPojoList.get(position).getRating()));

        return v;
    }

}
