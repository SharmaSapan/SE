package com.example.a4p02app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.VHolder> {

    List<String> name;
    List<String> content;
    List<String> date;
    List<Integer> profilePic;
    Context context;

    public PostAdapter(Context context, List<String> name, List<String> content, List<String> date, List<Integer> profilepic){
        this.context = context;
        this.name = name;
        this.content = content;
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
        holder.postContent.setText(content.get(position));
        holder.postDate.setText(date.get(position));
        holder.npoPic.setImageResource(profilePic.get(position));
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {

        TextView npoName, postContent, postDate;
        ImageView npoPic;
        public VHolder(@NonNull View itemView) {
            super(itemView);

            npoName = itemView.findViewById(R.id.NPO_name);
            postContent = itemView.findViewById(R.id.postContent);
            npoPic = itemView.findViewById(R.id.NPO_pic);
            postDate = itemView.findViewById(R.id.postDate);
        }
    }
}
