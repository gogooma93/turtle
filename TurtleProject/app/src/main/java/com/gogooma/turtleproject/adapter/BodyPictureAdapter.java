package com.gogooma.turtleproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gogooma.turtleproject.R;
import com.gogooma.turtleproject.model.BodyPicture;
import com.gogooma.turtleproject.view.ClotheActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BodyPictureAdapter extends RecyclerView.Adapter<BodyPictureAdapter.BodyPictureViewHolder> {

    private Context context;
    private ArrayList<BodyPicture> bodyPictureList;
    //private ImageView imgvProfile;

    public BodyPictureAdapter(Context context, ArrayList<BodyPicture> bodyPictureList) {
        this.context = context;
        this.bodyPictureList = bodyPictureList;
    }

    @NonNull
    @Override
    public BodyPictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grid_row_profile, parent,false);
        return new BodyPictureViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BodyPictureViewHolder bodyPictureViewHolder, int position) {
        BodyPicture bodyPicture = bodyPictureList.get(position);
        Picasso.get().load(bodyPicture.getMyPhotoUrl()).into(bodyPictureViewHolder.imgvProfile);

        //클릭 구현
        bodyPictureViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bUrl = bodyPicture.getMyPhotoUrl();
                int myPhotoId = bodyPicture.getMyPhotoId();
                ((ClotheActivity) ClotheActivity.context).clickProfilePicture(bUrl, myPhotoId);
            }
        });
    }

//    //추가?
//    public void addItem(BodyPicture bodyPicture) {
//        bodyPictureList.add(bodyPicture);
//    }


    @Override
    public int getItemCount() {
        return bodyPictureList.size();
    }



    public class BodyPictureViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgvProfile;
        private Context context;

        public  BodyPictureViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgvProfile = itemView.findViewById(R.id.imgv_profile);
        }
    }
}
