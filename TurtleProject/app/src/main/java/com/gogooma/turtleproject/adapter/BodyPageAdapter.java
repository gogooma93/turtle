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
import com.gogooma.turtleproject.model.BodyPicture;
import com.gogooma.turtleproject.view.ClosetActivity;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BodyPageAdapter extends RecyclerView.Adapter<BodyPageAdapter.BodyPageViewHolder> {

    private Context context;
    private List<BodyPicture> bodyPictureList;

    public BodyPageAdapter(Context context, List<BodyPicture> bodyPictureList) {
        this.context = context;
        this.bodyPictureList = bodyPictureList;
    }


    @NonNull
    @Override
    public BodyPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grid_row_bodypage, parent, false);
        return new BodyPageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BodyPageViewHolder bodyPageViewHolder, int position) {
        BodyPicture bodyPicture = bodyPictureList.get(position);
        Picasso.get().load(bodyPicture.getMyPhotoUrl()).into(bodyPageViewHolder.imgvProfile);

        bodyPageViewHolder.tvDelProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int delProfileId = bodyPicture.getMyPhotoId();
                int pos = bodyPageViewHolder.getAdapterPosition();
                ((ClosetActivity) ClosetActivity.pcontext).deleteProfileShowDialog(delProfileId, pos);
                //((ClosetActivity) ClosetActivity.pcontext).clickDeleteProfileButton(delProfileId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bodyPictureList.size();
    }

    public class BodyPageViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgvProfile;
        private Context context;
        private TextView tvDelProfile;
        private BodyPageAdapter bodyPageAdapter;


        public BodyPageViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgvProfile = itemView.findViewById(R.id.imgv_photo);
            this.tvDelProfile = itemView.findViewById(R.id.tv_delete_profile);

        }


    }
}
