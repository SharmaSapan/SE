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

    private static final String TAG = "PostAdapter";

    List<Integer> profPic;
    List<Post> postListFilt;
    List<Post> postListAll;
    PostAdapter.RowClickListener clickListener;

    public PostAdapter(List<Post> postListFilt, List<Integer> pics, PostAdapter.RowClickListener clickListener) {
        this.postListFilt = postListFilt;
        postListAll = postListFilt;
        this.profPic = pics;
        //System.out.println(postListAll.get(0).getName());
        this.clickListener = clickListener;
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
        holder.npoName.setText(postListFilt.get(position).getName());
        holder.postContent.setText(postListFilt.get(position).getContent());
        holder.postDate.setText(postListFilt.get(position).getDate());
        holder.npoPic.setImageResource(profPic.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickListener.onRowClick(postListFilt.get(position).getUID(),postListFilt.get(position).getAuthID());
                //clickListener.onRowClick(postListFilt.get(position).getAuthID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return postListFilt.size();
    }

    @Override
    public Filter getFilter() {

        return myFilter;
    }

    Filter myFilter = new Filter() {

        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            String search = charSequence.toString();

            if (search.isEmpty()) {

                postListFilt = postListAll;
            } else {
                List<Post> filteredNameList = new ArrayList<>();
                for (Post postsearch: postListAll) {
                    if (postsearch.getName().toLowerCase().contains(search.toLowerCase())||
                            postsearch.getContent().toLowerCase().contains(search.toLowerCase())||
                            postsearch.getDate().toLowerCase().contains(search.toLowerCase()))
                    {
                        filteredNameList.add(postsearch);
                    }
            }
                postListFilt = filteredNameList;
            }

            FilterResults filterResults = new FilterResults();

            filterResults.values = postListFilt;
            return filterResults;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            postListFilt = (ArrayList<Post>) filterResults.values;
            notifyDataSetChanged();
        }
    };



    class ViewHolder extends RecyclerView.ViewHolder{

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
        }
    }
    public interface RowClickListener{
        void onRowClick(String uid, String authid);
    }
}
