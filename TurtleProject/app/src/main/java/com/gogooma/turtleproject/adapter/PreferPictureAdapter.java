package com.gogooma.turtleproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gogooma.turtleproject.R;
import com.gogooma.turtleproject.model.PreferPicture;
import com.gogooma.turtleproject.view.ClotheActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PreferPictureAdapter extends RecyclerView.Adapter<PreferPictureAdapter.PreferPictureViewHolder> {

    private Context context;
    private List<PreferPicture> preferPictureList;
    //private ImageView imgvPrefer;

    public PreferPictureAdapter(Context context, List<PreferPicture> preferPictureList) {
        this.context = context;
        this.preferPictureList = preferPictureList;
    }


    @NonNull
    @Override
    public PreferPictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grid_row_prefer, parent, false);
        return new PreferPictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreferPictureViewHolder preferPictureViewHolder, int position) {
        PreferPicture preferPicture = preferPictureList.get(position);
        Picasso.get().load(preferPicture.getClothImage()).into(preferPictureViewHolder.imgvPrefer);

        preferPictureViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pUrl = preferPicture.getClothImage();
                int preferId = preferPicture.getPreferId();
                ((ClotheActivity) ClotheActivity.context).clickPreferPicture(pUrl, preferId);
                //((GoChangeActivity)GoChangeActivity.mcontext).clickPreferPicture(pUrl, preferId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return preferPictureList.size();
    }

    public class PreferPictureViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgvPrefer;
        private Context context;

        public PreferPictureViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgvPrefer = itemView.findViewById(R.id.imgv_prefer);
        }
    }
}
