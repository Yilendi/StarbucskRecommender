/**
 * recyclerview adapter that facilitates view page
 */
package com.yilendad.starbucksrecommender;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<DrinkModel> drinkModelList;
    private Context context;

    //constructor
    public RecyclerAdapter(ArrayList<DrinkModel> drinkModelList, Context context) {
        this.drinkModelList = drinkModelList ;
        this.context = context;
    }
    //my view holder class refers to the small layout, one-element layout
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView content;
        TextView desc;
        TextView content_d ;
        public MyViewHolder(final View view) {
            super(view);
            name = view.findViewById(R.id.tv_name) ;
            content = view.findViewById(R.id.tv_content) ;
            desc = view.findViewById(R.id.tv_des) ;
            content_d = view.findViewById(R.id.tv_content_d) ;
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_drink, parent, false);
       MyViewHolder holder = new MyViewHolder(itemView);
       return holder ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        String name = drinkModelList.get(position).getName();
        holder.content.setText(name);
        String description = drinkModelList.get(position).description();;
        holder.content_d.setText(description);
    }

    @Override
    public int getItemCount() {
        return drinkModelList.size() ;
    }
}
