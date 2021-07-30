package com.fitnessclub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fitnessclub.Model.WotoutPojo;
import com.fitnessclub.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterAdminviewTrainingList extends BaseAdapter {
    List<WotoutPojo> wotoutPojo=new ArrayList<>();
    Context con;
    String url= "http://getfitt.club/fitnessclub/";
    public AdapterAdminviewTrainingList(List<WotoutPojo> wotoutPojo,Context con){
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
        View workout = l.inflate(R.layout.child_admintrainiglist, null);

        ImageView image=(ImageView)workout.findViewById(R.id.image);
        Glide.with(con).load(url+wotoutPojo.get(pos).getImage()).into(image);


        TextView tvworkname = (TextView) workout.findViewById(R.id.tvworkname);
        tvworkname.setText(wotoutPojo.get(pos).getType());


        return workout;
    }

}
