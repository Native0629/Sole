package com.nav.tagger;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Navit on 7/9/2018.
 */

public class AllAlbumImageAdapter  extends RecyclerView.Adapter<AllAlbumImageAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<Model_images> items;
    int int_position;

    public AllAlbumImageAdapter(Context context, ArrayList<Model_images> items,int int_position) {
        this.context = context;
        this.items = items;
        this.int_position = int_position;
    }


    @Override
    public AllAlbumImageAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_photosfolder, parent, false);
        return new AllAlbumImageAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final AllAlbumImageAdapter.ItemViewHolder holder, final int position) {
        holder.tv_foldern.setText(items.get(position).getStr_folder());
        holder.tv_foldersize.setText(items.get(position).getAl_imagepath().size()+"");
        holder.tv_foldern.setVisibility(View.GONE);
        holder.tv_foldersize.setVisibility(View.GONE);


        Glide.with(context).load(items.get(int_position).getAl_imagepath().get(position))


                .into(holder.galleryImage);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView galleryImage;
        private TextView tv_foldersize;
        private TextView tv_foldern;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_foldern = (TextView) itemView.findViewById(R.id.tv_folder);
            tv_foldersize = (TextView) itemView.findViewById(R.id.tv_folder2);
            galleryImage = (ImageView) itemView.findViewById(R.id.iv_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


        }
    }


}


