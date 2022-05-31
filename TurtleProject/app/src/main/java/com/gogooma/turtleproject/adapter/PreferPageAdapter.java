package com.gogooma.turtleproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gogooma.turtleproject.R;
import com.gogooma.turtleproject.model.PreferPicture;
import com.gogooma.turtleproject.view.ClosetActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PreferPageAdapter extends RecyclerView.Adapter<PreferPageAdapter.PreferPageViewHolder> {

    private Context context;
    private List<PreferPicture> preferPictureList;
//    private OnItemClickListener onItemClickListener;

//    public interface OnItemClickListener {
//        void onItemClick(int position);
//        void onDeleteClick(int position);
//    }
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }

    public PreferPageAdapter(Context context, List<PreferPicture> preferPictureList) {
        this.context = context;
        this.preferPictureList = preferPictureList;

    }


    @NonNull
    @Override
    public PreferPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grid_row_preferpage, parent, false);
        return new PreferPageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreferPageViewHolder preferPageViewHolder, int position) {
        PreferPicture preferPicture = preferPictureList.get(position);
        Picasso.get().load(preferPicture.getClothImage()).into(preferPageViewHolder.imgvPreferpage);

        preferPageViewHolder.tvDeletePref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int deleteId = preferPicture.getPreferId();
                int pos = preferPageViewHolder.getAdapterPosition();
                ((ClosetActivity) ClosetActivity.pcontext).deletePrefShowDialog(deleteId, pos);
                //((ClosetActivity) ClosetActivity.pcontext).clickDeleteClothButton(deleteId);
                //Log.e("찜한 옷", "ID: " + deleteId);
            }
        });

        preferPageViewHolder.btnFittingClothe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ClosetActivity) ClosetActivity.pcontext).clickBtnFittingClothe();
            }
        });

        preferPageViewHolder.btnFittingColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ClosetActivity) ClosetActivity.pcontext).clickBtnFittingColor();
            }
        });
    }

    @Override
    public int getItemCount() {
        return preferPictureList.size();
    }

    public class PreferPageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgvPreferpage;
        private TextView tvDeletePref;
        private Context context;
        private Button btnFittingClothe, btnFittingColor;

        public PreferPageViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgvPreferpage = itemView.findViewById(R.id.imgv_prefer_page);
            this.tvDeletePref = itemView.findViewById(R.id.tv_delete_pref);
            this.btnFittingClothe = itemView.findViewById(R.id.btn_fitting_clothe);
            this.btnFittingColor = itemView.findViewById(R.id.btn_fitting_color);

        }
    }
}
