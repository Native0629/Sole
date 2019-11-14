package com.nav.tagger.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nav.tagger.Activity.AllTagsActivity;
import com.nav.tagger.Activity.TagByImageActicity;
import com.nav.tagger.Model.AddTagList;
import com.nav.tagger.R;
import com.nav.tagger.Utils.DatabaseHelper;
import com.nav.tagger.view.BoldTextView;
import com.nav.tagger.view.RegularButton;
import com.nav.tagger.view.RegularEditText;
import com.nav.tagger.view.RegularTextView;

import java.util.ArrayList;

/**
 * Created by Fujitsu on 13-06-2018.
 */

public class TagWiseAdapter extends RecyclerView.Adapter<TagWiseAdapter.ItemViewHolder> {

    private Context context;
    public static ArrayList<AddTagList> items;
    public static DatabaseHelper db;

    public TagWiseAdapter(Context context, ArrayList<AddTagList> items) {
        this.context = context;
        this.items = items;
        db = new DatabaseHelper(context);
    }


    @Override
    public TagWiseAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_list_row_item, parent, false);
        return new TagWiseAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final TagWiseAdapter.ItemViewHolder holder, final int position) {
       // TagByImageActicity.tagImageLists.clear();
        TagByImageActicity.tagImageLists = db.getAllTagsImageByTag(items.get(position).getTag());
        holder.tagName.setText(items.get(position).getTag());
        try{

            if (TagByImageActicity.tagImageLists.get(0).getTag_image().equals("")){

            }else {
                Glide.with(context).load(TagByImageActicity.tagImageLists.get(0).getTag_image())
                        .into(holder.tagImage);
            }

            TagByImageActicity.tagImageLists = db.getAllTagsImageByTag(items.get(0).getTag());
            TagByImageActicity.initializeRecyclerTag();
        }catch (Exception e){}

        try{
            holder.tagName.setText(items.get(position).getTag()+ " (" + String.valueOf(TagByImageActicity.tagImageLists.size()) + ")");

        }catch (Exception e){}


    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RegularTextView tagName;
        private ImageView tagImage;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tagName = (RegularTextView) itemView.findViewById(R.id.tag_name);
            tagImage = (ImageView) itemView.findViewById(R.id.tagImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            TagByImageActicity.tagImageLists.clear();
            TagByImageActicity.tagImageLists = db.getAllTagsImageByTag(items.get(getPosition()).getTag());
            TagByImageActicity.initializeRecyclerTag();
        }
    }


}
