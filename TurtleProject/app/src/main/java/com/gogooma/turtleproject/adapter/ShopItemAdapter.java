package com.gogooma.turtleproject.adapter;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gogooma.turtleproject.R;
import com.gogooma.turtleproject.model.ShopItem;


import com.gogooma.turtleproject.view.ItemDetailActivity;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.ShopItemViewHolder> {

    private Context context;
    private ArrayList<ShopItem> shopItemList;

    public ShopItemAdapter(Context context, ArrayList<ShopItem> shopItemList) {
        this.context = context;
        this.shopItemList = shopItemList;
    }

    @NonNull
    @Override
    public ShopItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grid_row_items, parent, false);
        return new ShopItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemViewHolder shopItemViewHolder, int position) {
        ShopItem shopItem = shopItemList.get(position);
        shopItemViewHolder.tvClothName.setText(shopItem.getClothName());
        shopItemViewHolder.tvClothPrice.setText(String.valueOf(shopItem.getClothPrice()));
        Picasso.get().load(shopItem.getModelImgUrl()).into(shopItemViewHolder.imgvModel);

        shopItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailintent = new Intent(view.getContext(), ItemDetailActivity.class);
                int position = shopItemViewHolder.getAdapterPosition();
                detailintent.putExtra("clothImgUrl", shopItem.getClothImgUrl());
                detailintent.putExtra("clothName", shopItem.getClothName());
                detailintent.putExtra("clothPrice",shopItem.getClothPrice());
                detailintent.putExtra("clothDesc", shopItem.getDescription());
                detailintent.putExtra("clothColor", shopItem.getColor());
                detailintent.putExtra("clothColorNum", shopItem.getColorNum());
                detailintent.putExtra("clothSerialNum", shopItem.getSerialNum());
                detailintent.putExtra("shopItemList", shopItemList);
                context.startActivity(detailintent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return shopItemList.size();
    }

    public class ShopItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgvModel;
        private TextView tvClothName, tvClothPrice;
        private Context context;


        public ShopItemViewHolder(View itemView) {
            super(itemView);

            imgvModel =itemView.findViewById(R.id.imgv_model);
            tvClothName = itemView.findViewById(R.id.tv_clothname);
            tvClothPrice = itemView.findViewById(R.id.tv_clothprice);

        }
    }
}

