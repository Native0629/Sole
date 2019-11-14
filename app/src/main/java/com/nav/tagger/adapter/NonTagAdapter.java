package com.nav.tagger.adapter;

import android.content.Context;
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
import com.nav.tagger.Model.AddTagList;
import com.nav.tagger.Model.GalleryList;
import com.nav.tagger.R;
import com.nav.tagger.Utils.DatabaseHelper;
import com.nav.tagger.view.RegularButton;
import com.nav.tagger.view.RegularTextView;

import java.util.ArrayList;

/**
 * Created by navitgupta on 12/06/18.
 */

public class NonTagAdapter extends RecyclerView.Adapter<NonTagAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<GalleryList> items;
    private DatabaseHelper db;
    private String tag_Name = "";

    public NonTagAdapter(Context context, ArrayList<GalleryList> items) {
        this.context = context;
        this.items = items;
        db= new DatabaseHelper(context);
    }


    @Override
    public NonTagAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_image_row_item, parent, false);
        return new NonTagAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final NonTagAdapter.ItemViewHolder holder, final int position) {

        Glide.with(context).load(items.get(position).getImage())
                .into(holder.galleryImage);


            holder.checkbox.setVisibility(View.GONE);

        if (items.get(position).getImage().contains(".mp4")) {
            holder.videoIcon.setVisibility(View.VISIBLE);
        } else if (items.get(position).getImage().contains(".MP4")) {
            holder.videoIcon.setVisibility(View.VISIBLE);
        } else if (items.get(position).getImage().contains(".3gp")) {
            holder.videoIcon.setVisibility(View.VISIBLE);
        } else if (items.get(position).getImage().contains(".3GP")) {
            holder.videoIcon.setVisibility(View.VISIBLE);
        } else if (items.get(position).getImage().contains(".mkv")) {
            holder.videoIcon.setVisibility(View.VISIBLE);
        } else if (items.get(position).getImage().contains(".MKV")) {
            holder.videoIcon.setVisibility(View.VISIBLE);
        } else {
            holder.videoIcon.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView galleryImage;
        private ImageView checkbox;
        private ImageView videoIcon;


        public ItemViewHolder(View itemView) {
            super(itemView);
            galleryImage = (ImageView) itemView.findViewById(R.id.galleryImage);
            checkbox = (ImageView) itemView.findViewById(R.id.checkbox);
            videoIcon = (ImageView) itemView.findViewById(R.id.videoIcon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }


}


