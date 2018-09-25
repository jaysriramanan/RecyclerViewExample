package com.example.test.recyclerviewexample.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.recyclerviewexample.DisplayView;
import com.example.test.recyclerviewexample.R;

public class RvAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private String[] states;

    public RvAdapter(Context context,String[] states) {
        this.context = context;
        this.states=states;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_item,viewGroup,false);
        return new RvHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            final RvHolder holder=(RvHolder)viewHolder;
            holder.recyclerItemText.setText(states[i]);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(),DisplayView.class);
                    intent.putExtra("position",holder.getAdapterPosition());
                    view.getContext().startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return states.length;
    }

    public class RvHolder extends RecyclerView.ViewHolder{
        TextView recyclerItemText;

        public RvHolder(@NonNull View itemView) {
            super(itemView);
            recyclerItemText=itemView.findViewById(R.id.recyclerItemText);
        }
    }
}
