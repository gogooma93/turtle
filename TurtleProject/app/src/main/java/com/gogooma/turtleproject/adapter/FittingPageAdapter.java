package com.gogooma.turtleproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gogooma.turtleproject.R;
import com.gogooma.turtleproject.model.Clothe;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FittingPageAdapter extends RecyclerView.Adapter<FittingPageAdapter.FittingPageViewHolder> {

    private Context context;
    private List<Clothe> clotheList;

    public FittingPageAdapter(Context context, List<Clothe> clotheList) {
        this.context = context;
        this.clotheList = clotheList;

    }

    @NonNull
    @Override
    public FittingPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grid_row_fittingpage, parent, false);
        return new FittingPageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FittingPageViewHolder fittingPageViewHolder, int position) {
        Clothe clothe = clotheList.get(position);
        Picasso.get().load(clothe.getFittingPhoto()).into(fittingPageViewHolder.imgvComposition);
    }

    @Override
    public int getItemCount() {
        return clotheList.size();
    }

    public class FittingPageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgvComposition;

        public FittingPageViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgvComposition = itemView.findViewById(R.id.imgv_composition);
        }
    }
}
