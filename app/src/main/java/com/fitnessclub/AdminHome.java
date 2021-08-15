package com.fitnessclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.fitnessclub.Adapters.AdapterAdminHome;
import com.fitnessclub.Adapters.Adaptertrainerworkoutlist;
import com.fitnessclub.Admin.TrainersList;
import com.fitnessclub.Admin.TrainingListActivity;
import com.fitnessclub.Admin.UserComplaintsActivity;
import com.fitnessclub.Admin.UserList;
import com.fitnessclub.Model.WotoutPojo;
import com.fitnessclub.Networking.ServiceGenerator;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHome extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ListView worklist;

    RecyclerView recyclerView;
    Adaptertrainerworkoutlist AdapterUser;
    ArrayList<WotoutPojo> list;
    List<WotoutPojo> wotoutPojo;
    ProgressDialog loading;
    GridView gridview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        gridview = (GridView) findViewById(R.id.gridview);
        wotoutPojo = new ArrayList<>();
        getWorkoutContent();

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout=findViewById(R.id.drawe_layout);
        navigationView=findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
      //  navigationView.setCheckedItem(R.id.nav_users);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_users:
                        Intent intent1 =new Intent(AdminHome.this, UserList.class);
                       // intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent1);
                        break;
                    case R.id.nav_trainers:
                        Intent i =new Intent(AdminHome.this, TrainersList.class);
                      //  i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                        break;
                    case R.id.nav_training:
                        Intent t =new Intent(AdminHome.this, TrainingListActivity.class);
                     //   t.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(t);
                        break;
                    case R.id.nav_complaints:
                        Intent c =new Intent(AdminHome.this, UserComplaintsActivity.class);
                        //   t.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(c);
                        break;
                    case R.id.log_out:
                        navigationView.setCheckedItem(R.id.log_out);
                        Intent intent2 =new Intent(AdminHome.this,LoginActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
                        SharedPreferences.Editor et=sharedPreferences.edit();
                        et.clear();
                        et.apply();
                        startActivity(intent2);
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public void getWorkoutContent() {
        loading = new ProgressDialog(AdminHome.this);
        loading.setMessage("Loading");
        loading.show();

        Call<List<WotoutPojo>> call= ServiceGenerator.getGymApi().gettraining();
        call.enqueue(new Callback<List<WotoutPojo>>() {
            @Override
            public void onResponse(Call<List<WotoutPojo>> call, Response<List<WotoutPojo>> response) {
                loading.dismiss();
                 // Toast.makeText(AdminHome.this, response.toString(), Toast.LENGTH_SHORT).show();
                if (response.body() == null) {
                    Toast.makeText(AdminHome.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    wotoutPojo = response.body();
                    gridview.setAdapter(new AdapterAdminHome(wotoutPojo, AdminHome.this));
                }
            }
            @Override
            public void onFailure(Call<List<WotoutPojo>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AdminHome.this, "Something went wrong...Please contact admin !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}