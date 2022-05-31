package com.gogooma.turtleproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gogooma.turtleproject.R;
import com.gogooma.turtleproject.model.ShopItem;
import com.gogooma.turtleproject.view.ItemDetailActivity;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OtherColorAdapter extends RecyclerView.Adapter<OtherColorAdapter.OtherColorViewHolder> {

    private Context context;
    private ArrayList<ShopItem> sameModelList;

    public OtherColorAdapter(Context context, ArrayList<ShopItem> sameModelList) {
        this.context = context;
        this.sameModelList = sameModelList;
    }

    @NonNull
    @Override
    public OtherColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grid_row_othercolor, parent, false);
        return new OtherColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherColorViewHolder otherColorViewHolder, int position) {
        ShopItem sameItem = sameModelList.get(position);
        otherColorViewHolder.tvOtherColorName.setText(sameItem.getColor());
        Picasso.get().load(sameItem.getClothImgUrl()).into(otherColorViewHolder.imgvOtherColor);


        otherColorViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailintent = new Intent(view.getContext(), ItemDetailActivity.class);
                detailintent.putExtra("clothImgUrl", sameItem.getClothImgUrl());
                detailintent.putExtra("clothName", sameItem.getClothName());
                detailintent.putExtra("clothPrice",sameItem.getClothPrice());
                detailintent.putExtra("clothDesc", sameItem.getDescription());
                detailintent.putExtra("clothColor", sameItem.getColor());
                detailintent.putExtra("clothColorNum", sameItem.getColorNum());
                detailintent.putExtra("clothSerialNum", sameItem.getSerialNum());
                detailintent.putExtra("shopItemList", sameModelList);

                context.startActivity(detailintent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sameModelList.size();
    }

    public class OtherColorViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgvOtherColor;
        private TextView tvOtherColorName;

        public OtherColorViewHolder(View itemView) {
            super(itemView);
            imgvOtherColor = itemView.findViewById(R.id.imgv_othercolor);
            tvOtherColorName = itemView.findViewById(R.id.tv_othercolorname);
        }
    }
}

