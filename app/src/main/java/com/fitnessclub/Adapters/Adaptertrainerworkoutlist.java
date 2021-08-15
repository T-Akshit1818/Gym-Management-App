package com.fitnessclub.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fitnessclub.AdminHome;
import com.fitnessclub.EditWorkoutActivity;
import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Model.UsersPojo;
import com.fitnessclub.Model.WotoutPojo;
import com.fitnessclub.Networking.ServiceGenerator;
import com.fitnessclub.R;
import com.fitnessclub.TrainerHomeActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adaptertrainerworkoutlist extends BaseAdapter {
    List<WotoutPojo> wotoutPojo=new ArrayList<>();
    Context con;
    ProgressDialog progressDialog;
    String url= "http://getfitt.club/fitnessclub/";
    public Adaptertrainerworkoutlist(List<WotoutPojo> wotoutPojo,Context con){
        this.wotoutPojo=wotoutPojo;
        this.con = con;
    }
    @Override
    public int getCount() {
        return wotoutPojo.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup) {
        LayoutInflater l = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View workout = l.inflate(R.layout.child_trainerworkoutlist, null);

        ImageView image=(ImageView)workout.findViewById(R.id.image);
        Glide.with(con).load(url+wotoutPojo.get(pos).getImage()).into(image);


        TextView tvworkname = (TextView) workout.findViewById(R.id.tvworkname);
        tvworkname.setText(wotoutPojo.get(pos).getType());

        ImageView imgedit=(ImageView)workout.findViewById(R.id.imgedit);
        imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(con, EditWorkoutActivity.class);
                intent.putExtra("tid",wotoutPojo.get(pos).getTid());
                intent.putExtra("type",wotoutPojo.get(pos).getType());
                intent.putExtra("level",wotoutPojo.get(pos).getLevel());
                intent.putExtra("about",wotoutPojo.get(pos).getAbout());
                intent.putExtra("tim",wotoutPojo.get(pos).getTim());
                intent.putExtra("gender",wotoutPojo.get(pos).getGender());
                con.startActivity(intent);
            }
        });

        ImageView imgdelete=(ImageView)workout.findViewById(R.id.imgdelete);
        imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteContent(wotoutPojo.get(pos).getTid());
            }
        });

        return workout;
    }

    public void deleteContent(final String cid)
    {
        progressDialog = new ProgressDialog(con);
        progressDialog.setMessage("Deleting Workout");
        progressDialog.show();
        Call<SuccessOrFailureResponse> call = ServiceGenerator.getGymApi().deletetrainingcontent(cid);
        call.enqueue(new Callback<SuccessOrFailureResponse>() {
            @Override
            public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(con,"Server issue",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent(con, TrainerHomeActivity.class);
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
