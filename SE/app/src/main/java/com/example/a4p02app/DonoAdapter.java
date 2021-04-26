package com.example.a4p02app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
//this adapter works for donations list for the user
public class DonoAdapter extends RecyclerView.Adapter<DonoAdapter.VHolder> implements Filterable {
    //initialize all parts of the card to display, and context
    List<String> name;
    List<String> amount;
    List<String> date;
    List<Integer> profilePic;
    Context context;

    public DonoAdapter(Context context, List<String> name, List<String> amount, List<String> date, List<Integer> profilepic){
        //initialize all parts of the card to display, and context
        this.context = context;
        this.name = name;
        this.amount = amount;
        this.date=date;
        this.profilePic=profilepic;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_row, parent, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {

        holder.npoName.setText(name.get(position));
        holder.donoAmount.setText(amount.get(position));
        holder.donoDate.setText(date.get(position));
        holder.npoPic.setImageResource(profilePic.get(position));
/*
        holder.cLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, nonProfit.class);
                in.putExtra("npoName", name.get(position));
                context.startActivity(in);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return amount.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class VHolder extends RecyclerView.ViewHolder {

        TextView npoName, donoAmount, donoDate;
        ImageView npoPic;
        ConstraintLayout cLayout;

        public VHolder(@NonNull View itemView) {
            super(itemView);

            npoName = itemView.findViewById(R.id.NPO_name);
            donoAmount = itemView.findViewById(R.id.postContent);
            npoPic = itemView.findViewById(R.id.NPO_pic);
            donoDate = itemView.findViewById(R.id.postDate);
            cLayout = itemView.findViewById(R.id.row);
        }
    }
}
