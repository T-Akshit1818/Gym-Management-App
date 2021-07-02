package com.fitnessclub.Admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fitnessclub.Model.UsersPojo;
import com.fitnessclub.R;

import java.util.ArrayList;
import java.util.List;

public class AdminUsersAdapter extends BaseAdapter {
    List<UsersPojo> usersPojoList=new ArrayList<>();
AdminUsersAdapter(  List<UsersPojo> usersPojoList){
    this.usersPojoList=usersPojoList;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adminuserlist,null);
        TextView name=v.findViewById(R.id.name);
        TextView email=v.findViewById(R.id.email);
        name.setText(usersPojoList.get(position).getFullname());
        email.setText(usersPojoList.get(position).getEmail());
        return v;
    }
}
