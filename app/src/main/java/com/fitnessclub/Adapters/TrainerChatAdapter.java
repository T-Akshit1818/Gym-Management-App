package com.fitnessclub.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.fitnessclub.Model.MessagePojo;
import com.fitnessclub.R;
import com.fitnessclub.TrainerMessageActivity;
import com.fitnessclub.Utils;

import java.util.List;

public class TrainerChatAdapter extends BaseAdapter {
    List<MessagePojo> ar;
    Context cnt;
    SharedPreferences sharedPreferences;
    String session;

    public TrainerChatAdapter(List<MessagePojo> ar, Context cnt) {
        this.ar = ar;
        this.cnt = cnt;
    }
    @Override
    public int getCount() {
        return ar.size();
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
        LayoutInflater obj1 = (LayoutInflater) cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2 = obj1.inflate(R.layout.child_trainerchat, null);

        sharedPreferences = cnt.getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");

        TextView tv_from = (TextView) obj2.findViewById(R.id.tv_from);
        tv_from.setText(ar.get(pos).getFromemail());

        CardView card_chat=(CardView)obj2.findViewById(R.id.card_chat);

        card_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(cnt, TrainerMessageActivity.class);
                intent.putExtra("from",ar.get(pos).getFromemail());
                intent.putExtra("to",session);
                cnt.startActivity(intent);

            }
        });

        return obj2;
    }
}