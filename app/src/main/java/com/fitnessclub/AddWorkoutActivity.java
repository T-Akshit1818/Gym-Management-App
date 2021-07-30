package com.fitnessclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Networking.GymApi;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddWorkoutActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    private static final String TAG = AddWorkoutActivity.class.getSimpleName();
    private static final String SERVER_PATH = "http://getfitt.club/";

    EditText about;
    Button btn_uploadpic,btnaddworkout;
    ProgressDialog load;

    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;

    private Uri uri;
    RatingBar rv_rating;
    SharedPreferences sharedPreferences;
    String session;
    Spinner type,level,tim,gender;
    String g[] = {"male","female"};

    String ty[] = {"Walking", "Interval training", "Squats", "Push-ups", "Biceps", "Cardio"};

    String l[] = {"Endurance", "Strength", "Balance", "Flexibility"};

    String t[] = {"15 mins", "30 mins", "45 mins"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);
        getSupportActionBar().setTitle("Add Workout Information");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");

        type=(Spinner) findViewById(R.id.type);
        level=(Spinner)findViewById(R.id.level);
        tim=(Spinner)findViewById(R.id.time);
        about=(EditText)findViewById(R.id.about);
        gender=(Spinner)findViewById(R.id.gender);


        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_list, g);
        aa.setDropDownViewResource(R.layout.spinner_list);
        gender.setAdapter(aa);
        gender.setSelection(0);


        ArrayAdapter aa1 = new ArrayAdapter(this, R.layout.spinner_list, ty);
        aa1.setDropDownViewResource(R.layout.spinner_list);
        type.setAdapter(aa1);
        type.setSelection(0);

        ArrayAdapter aa2 = new ArrayAdapter(this, R.layout.spinner_list, l);
        aa2.setDropDownViewResource(R.layout.spinner_list);
        level.setAdapter(aa2);
        level.setSelection(0);

        ArrayAdapter aa3 = new ArrayAdapter(this, R.layout.spinner_list, t);
        aa3.setDropDownViewResource(R.layout.spinner_list);
        tim.setAdapter(aa3);
        tim.setSelection(0);

        btn_uploadpic=(Button) findViewById(R.id.picupload);
        btn_uploadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
            }
        });
        btnaddworkout=(Button) findViewById(R.id.btnaddworkout);
        btnaddworkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutAdding();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, AddWorkoutActivity.this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK){
            uri = data.getData();
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, AddWorkoutActivity.this);
                file = new File(filePath);

            }else{
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    File file;
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(uri != null){
            String filePath = getRealPathFromURIPath(uri, AddWorkoutActivity.this);
            file = new File(filePath);
        }
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");

    }

    private void workoutAdding() {
        load = new ProgressDialog(AddWorkoutActivity.this);
        load.setTitle("Loading");
        load.show();
        Map<String, String> map = new HashMap<>();
        map.put("type", type.getSelectedItem().toString());
        map.put("level", level.getSelectedItem().toString());
        map.put("tim", tim.getSelectedItem().toString());
        map.put("about", about.getText().toString());
        map.put("gender", gender.getSelectedItem().toString());
        map.put("email", session);

        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GymApi addworkout = retrofit.create(GymApi.class);
        Call<SuccessOrFailureResponse> fileUpload = addworkout.addtraining(fileToUpload, map);
        fileUpload.enqueue(new Callback<SuccessOrFailureResponse>() {
            @Override
            public void onResponse(Call<SuccessOrFailureResponse> call, Response<SuccessOrFailureResponse> response) {
                load.dismiss();
                Toast.makeText(AddWorkoutActivity.this, "Added successfully. ", Toast.LENGTH_LONG).show();
                startActivity(new Intent(AddWorkoutActivity.this,TrainerHomeActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<SuccessOrFailureResponse> call, Throwable t) {
                load.dismiss();
                Toast.makeText(AddWorkoutActivity.this, "Error" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}