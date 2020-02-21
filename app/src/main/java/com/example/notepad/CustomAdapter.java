package com.example.notepad;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<User> noteList;
    private Context context;

    public CustomAdapter(List<User> noteList, Context context) {
        this.noteList = noteList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final User currentUser= noteList.get(position);
        holder.titleTv.setText(currentUser.getTitle());
        holder.detailsTv.setText(currentUser.getDetails());
        holder.itemCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailsActivity.class);
                intent.putExtra("title",currentUser.getTitle());
                intent.putExtra("details",currentUser.getDetails());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTv,detailsTv;
        private CardView itemCv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv= itemView.findViewById(R.id.titleTv);
            detailsTv= itemView.findViewById(R.id.detailsTv);
            itemCv = itemView.findViewById(R.id.itemCv);
        }
    }
}
