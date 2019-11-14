package com.nav.tagger.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nav.tagger.Activity.ImageViewSliderActivity;
import com.nav.tagger.Model.TagImageList;
import com.nav.tagger.R;
import com.nav.tagger.VideoActivity;

import java.util.ArrayList;

/**
 * Created by Navit on 8/5/2018.
 */

public class SearchImageAdapter  extends RecyclerView.Adapter<SearchImageAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<TagImageList> items;

    public SearchImageAdapter(Context context, ArrayList<TagImageList> items) {
        this.context = context;
        this.items = items;
    }


    @Override
    public SearchImageAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_images_row_item, parent, false);
        return new SearchImageAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final SearchImageAdapter.ItemViewHolder holder, final int position) {

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
                intent.putExtra("tag","5");
                intent.putExtra("pos",getPosition());
                context.startActivity(intent);
            }
        }
    }


}



