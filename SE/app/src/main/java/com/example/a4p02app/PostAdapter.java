package com.example.a4p02app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "RecyclerAdapter";
    List<String> nameListFilt;
    List<String> nameListAll;
    List<String> contentListFilt;
    List<String> contentListAll;
    List<String> dateListFilt;
    List<String> dateListAll;
    List<Integer> profPic;
    //List<Integer> counter = new ArrayList<>();

    public PostAdapter(List<String> nameListFilt, List<String> content,
                       List<String> dates, List<Integer> pics) {
        this.nameListFilt = nameListFilt;
        nameListAll = nameListFilt;
        this.contentListFilt = content;
        contentListAll = contentListFilt;
        this.dateListFilt = dates;
        dateListAll = dateListFilt;
        this.profPic = pics;
        //allInfo.addAll(nameListAll);
        //allInfo.addAll(contentListAll);
        //allInfo.addAll(dateListAll);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.post_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.rowCountTextView.setText(String.valueOf(position));
        holder.npoName.setText(nameListFilt.get(position));
        holder.postContent.setText(contentListFilt.get(position));
        holder.postDate.setText(dateListFilt.get(position));
        holder.npoPic.setImageResource(profPic.get(position));
    }

    @Override
    public int getItemCount() {
        return nameListFilt.size();
    }

    @Override
    public Filter getFilter() {

        return myFilter;
    }

    Filter myFilter = new Filter() {

        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            //int i=0;
            String search = charSequence.toString();

            if (search.isEmpty()) {

                nameListFilt = nameListAll;
            } else {
                List<String> filteredNameList = new ArrayList<>();
                for (String postsearch: nameListAll) {
                    if (postsearch.toLowerCase().contains(search.toLowerCase())) {
                        filteredNameList.add(postsearch);
                    }
                }
                nameListFilt = filteredNameList;
/*
                List<String> filteredContentList = new ArrayList<>();
                List<Integer> count = new ArrayList<>();
                for (String postsearch: contentListAll) {

                    if (postsearch.toLowerCase().contains(search.toLowerCase())) {
                        filteredContentList.add(postsearch);
                        count.add(i);
                    }
                    //i++;
                }*/
                //contentListFilt = filteredContentList;
                //counter = count;
            }
            // List<String> contentNames = new ArrayList<>();
            //(int j=0; j<counter.size();j++){
            //   contentNames.add(nameListAll.get(counter.get(j)));
            //}
            FilterResults filterResults = new FilterResults();
            //nameListFilt.addAll(contentNames);

            filterResults.values = nameListFilt;
            return filterResults;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            nameListFilt = (ArrayList<String>) filterResults.values;
            notifyDataSetChanged();
        }
    };



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView npoName, postContent, postDate;
        ImageView npoPic;
        ConstraintLayout cLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            npoName = itemView.findViewById(R.id.NPO_name);
            postContent = itemView.findViewById(R.id.postContent);
            npoPic = itemView.findViewById(R.id.NPO_pic);
            postDate = itemView.findViewById(R.id.postDate);
            cLayout = itemView.findViewById(R.id.row);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), nameListFilt.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }
    }
}
