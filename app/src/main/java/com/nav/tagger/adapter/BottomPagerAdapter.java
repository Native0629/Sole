package com.nav.tagger.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.nav.tagger.Activity.DashBoardActivity;
import com.nav.tagger.Activity.ImageViewSliderActivity;
import com.nav.tagger.Model.AddTagList;
import com.nav.tagger.Model.GalleryList;
import com.nav.tagger.Model.TagImageList;
import com.nav.tagger.R;
import com.nav.tagger.Utils.DatabaseHelper;
import com.nav.tagger.view.RegularButton;
import com.nav.tagger.view.RegularTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Fujitsu on 20-06-2018.
 */

public class BottomPagerAdapter extends RecyclerView.Adapter<BottomPagerAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<TagImageList> items;

    public BottomPagerAdapter(Context context, ArrayList<TagImageList> items) {
        this.context = context;
        this.items = items;

    }


    @Override
    public BottomPagerAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bottom_page_row_item, parent, false);
        return new BottomPagerAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final BottomPagerAdapter.ItemViewHolder holder, final int position) {

        Glide.with(context).load(items.get(position).getTag_image())
                .into(holder.galleryImage);

/*
        Picasso.with(context)
                .load(items.get(position).getTag_image())
                .into(holder.galleryImage);
*/
       /* File imgFile = new  File(items.get(position).getTag_image());

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());



            holder.galleryImage.setImageBitmap(myBitmap);

        }*/
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView galleryImage;


        public ItemViewHolder(View itemView) {
            super(itemView);
            galleryImage = (ImageView) itemView.findViewById(R.id.galleryImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try{
                ImageViewSliderActivity.mPager.setCurrentItem(getPosition());
            }catch (Exception e){}


        }
    }


}

