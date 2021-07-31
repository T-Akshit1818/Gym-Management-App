package com.fitnessclub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitnessclub.Model.MessagePojo;
import com.fitnessclub.R;

import java.util.ArrayList;
import java.util.List;

public class TrainerMessageAdapter extends RecyclerView.Adapter<TrainerMessageAdapter.MessagesViewHolder> {

    private final Context context;

    public TrainerMessageAdapter(List<MessagePojo> msg, String from, Context context){
        this.from=from;
        this.context=context;
        this.msg=msg;
    }
    List<MessagePojo> msg=new ArrayList<>();
    String from;
    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =null;
        if(viewType==1){
            itemView= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.child_frommessage, parent, false);
            return new MessagesViewHolder(itemView);
        }else{
            itemView=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.child_tomessage, parent, false);
            return new MessagesViewHolder(itemView);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(msg.get(position).getFromemail().equals(from)){
            return 1;
        }
        else{
            return 2;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        MessagePojo m=msg.get(position);

        holder.msgfield.setText(m.getMsg());
    }

    public void data(List<MessagePojo> msg){
        this.msg=msg;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return msg.size();
    }

    class MessagesViewHolder extends  RecyclerView.ViewHolder{
        TextView msgfield;
        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            msgfield=itemView.findViewById(R.id.msgs);
        }

    }


}
