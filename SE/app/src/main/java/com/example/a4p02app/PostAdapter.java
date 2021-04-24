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
    //List<String> nameListFilt;
    //List<String> nameListAll;
    //List<String> contentListFilt;
    //List<String> contentListAll;
    //List<String> dateListFilt;
    //List<String> dateListAll;
    List<Integer> profPic;
    List<Post> postListFilt;
    List<Post> postListAll;

    public PostAdapter(List<Post> postListFilt, List<Integer> pics) {
        this.postListFilt = postListFilt;
        postListAll = postListFilt;
        //this.contentListFilt = content;
        //contentListAll = contentListFilt;
        //this.dateListFilt = dates;
        //dateListAll = dateListFilt;
        this.profPic = pics;
        System.out.println(postListAll.get(0).getName());
        /*System.out.println(postListFilt.get(1));
        System.out.println(postListFilt.get(2));
        System.out.println(postListFilt.get(3));
        System.out.println(postListFilt.get(4));
    */
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
        holder.npoName.setText(postListFilt.get(position).getName());
        holder.postContent.setText(postListFilt.get(position).getContent());
        holder.postDate.setText(postListFilt.get(position).getDate());
        holder.npoPic.setImageResource(profPic.get(position));
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

            //int i=0;
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
            //Toast.makeText(view.getContext(), postListFilt.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }
    }
}
