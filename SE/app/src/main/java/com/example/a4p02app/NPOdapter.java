package com.example.a4p02app;

import android.app.FragmentTransaction;
import android.content.Intent;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4p02app.fragments.nonprofitFragment;

import java.util.ArrayList;
import java.util.List;

public class NPOdapter extends RecyclerView.Adapter<NPOdapter.ViewHolder> implements Filterable {

    private static final String TAG = "NPOdapter";

    List<String> nameListFilt;
    List<String> nameListAll;
    RowClickListener clickListener;
    List<Integer> profPic;

    public NPOdapter(List<String> nameListFilt, List<Integer> pics, RowClickListener clickListener) {
        this.nameListFilt = nameListFilt;
        nameListAll = nameListFilt;
        this.profPic = pics;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.npo_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.npoName.setText(nameListFilt.get(position));
        holder.npoPic.setImageResource(profPic.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickListener.onRowClick(nameListFilt.get(position));
            }
        });
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

            String search = charSequence.toString();

            if (search.isEmpty()) {

                nameListFilt = nameListAll;
            } else {
                List<String> filteredNameList = new ArrayList<>();
                for (String nposearch: nameListAll) {
                    if (nposearch.toLowerCase().contains(search.toLowerCase())) {
                        filteredNameList.add(nposearch);
                    }
                }
                nameListFilt = filteredNameList;

            }
            FilterResults filterResults = new FilterResults();

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



    class ViewHolder extends RecyclerView.ViewHolder  {
//implements View.OnClickListener
        TextView npoName;
        ImageView npoPic;
        ConstraintLayout cLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            npoName = itemView.findViewById(R.id.NPO_name);
            npoPic = itemView.findViewById(R.id.NPO_pic);
            cLayout = itemView.findViewById(R.id.row);

            //itemView.setOnClickListener(this);

        }

       // @Override
       // public void onClick(View view) {



          //  Toast.makeText(view.getContext(), nameListFilt.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        //}
    }
    public interface RowClickListener{
        void onRowClick(String npo);
    }
}
