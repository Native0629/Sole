package com.nav.tagger.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.nav.tagger.VideoActivity;
import com.nav.tagger.view.RegularButton;
import com.nav.tagger.view.RegularTextView;

import java.util.ArrayList;

/**
 * Created by navitgupta on 12/06/18.
 */

public class AllTagImageAdapter extends RecyclerView.Adapter<AllTagImageAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<TagImageList> items;

    public AllTagImageAdapter(Context context, ArrayList<TagImageList> items) {
        this.context = context;
        this.items = items;
    }


    @Override
    public AllTagImageAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_images_row_item, parent, false);
        return new AllTagImageAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final AllTagImageAdapter.ItemViewHolder holder, final int position) {

        Glide.with(context).load(items.get(position).getTag_image())
                .into(holder.galleryImage);

        if (items.get(position).getTag_image().contains(".mp4")) {
            holder.videoIcon.setVisibility(View.VISIBLE);
        } else if (items.get(position).getTag_image().contains(".MP4")) {
            holder.videoIcon.setVisibility(View.VISIBLE);
        } else if (items.get(position).getTag_image().contains(".3gp")) {
            holder.videoIcon.setVisibility(View.VISIBLE);
        } else if (items.get(position).getTag_image().contains(".3GP")) {
            holder.videoIcon.setVisibility(View.VISIBLE);
        } else if (items.get(position).getTag_image().contains(".mkv")) {
            holder.videoIcon.setVisibility(View.VISIBLE);
        } else if (items.get(position).getTag_image().contains(".MKV")) {
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
        private ImageView videoIcon;


        public ItemViewHolder(View itemView) {
            super(itemView);
            galleryImage = (ImageView) itemView.findViewById(R.id.galleryImage);
            videoIcon = (ImageView) itemView.findViewById(R.id.videoIcon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (items.get(getPosition()).getTag_image().contains(".mp4")) {
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("video", items.get(getPosition()).getTag_image());
                context.startActivity(intent);
            } else if (items.get(getPosition()).getTag_image().contains(".MP4")) {
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("video", items.get(getPosition()).getTag_image());
                context.startActivity(intent);
            }else if (items.get(getPosition()).getTag_image().contains(".3gp")) {
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("video", items.get(getPosition()).getTag_image());
                context.startActivity(intent);
            }else if (items.get(getPosition()).getTag_image().contains(".3GP")) {
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("video", items.get(getPosition()).getTag_image());
                context.startActivity(intent);
            }else if (items.get(getPosition()).getTag_image().contains(".mkv")) {
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("video", items.get(getPosition()).getTag_image());
                context.startActivity(intent);
            }else if (items.get(getPosition()).getTag_image().contains(".MKV")) {
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("video", items.get(getPosition()).getTag_image());
                context.startActivity(intent);
            }
            else {
                Intent intent = new Intent(context, ImageViewSliderActivity.class);
                intent.putExtra("tag","0");
                intent.putExtra("pos",getPosition());
                context.startActivity(intent);
            }
        }
    }


}


