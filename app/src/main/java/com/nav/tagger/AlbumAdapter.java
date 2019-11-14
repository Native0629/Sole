package com.nav.tagger;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.nav.tagger.Activity.DashBoardActivity;
import com.nav.tagger.Model.AddTagList;
import com.nav.tagger.Model.GalleryList;
import com.nav.tagger.Model.TagImageList;
import com.nav.tagger.Utils.DatabaseHelper;
import com.nav.tagger.adapter.DrawerAdapter;
import com.nav.tagger.adapter.GalleryAdapter;
import com.nav.tagger.view.RegularButton;
import com.nav.tagger.view.RegularTextView;

import java.util.ArrayList;

/**
 * Created by Navit on 7/9/2018.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<Model_images> items;
    DatabaseHelper db;
    public static ArrayList<TagImageList> tagImageLists;
    public static ArrayList<TagImageList> tagImageListsSize;
    public static ArrayList<String> stringArrayList;
    public static ArrayList<GalleryList> galleryLists;
    public static ArrayList<GalleryList> galleryListstmp;

    public AlbumAdapter(Context context, ArrayList<Model_images> items) {
        this.context = context;
        this.items = items;
        tagImageLists = new ArrayList<>();
        stringArrayList = new ArrayList<>();
        galleryListstmp = new ArrayList<>();
        tagImageListsSize = new ArrayList<>();
        galleryLists = new ArrayList<>();
        db = new DatabaseHelper(context);
    }


    @Override
    public AlbumAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_photosfolder, parent, false);
        return new AlbumAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final AlbumAdapter.ItemViewHolder holder, final int position) {
        holder.tv_foldern.setText(items.get(position).getStr_folder());
        holder.tv_foldersize.setText(galleryImages(position) + "");


        Glide.with(context).load(items.get(position).getAl_imagepath().get(0))


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
            if (galleryImages(getPosition())==0){
                Toast.makeText(context,"Tnere is no Non Tag image in " + items.get(getPosition()).getStr_folder(),Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent(context, PhotosActivity.class);
                intent.putExtra("value", getPosition());
                context.startActivity(intent);
            }


        }
    }

    public int galleryImages(int int_position) {
        tagImageLists.clear();
        stringArrayList.clear();
        galleryListstmp.clear();
        galleryLists.clear();
        tagImageLists = db.getAllTagsImages();
        for (int i = 0; i < DashBoardActivity.al_images.get(int_position).getAl_imagepath().size(); i++) {
            stringArrayList.add(DashBoardActivity.al_images.get(int_position).getAl_imagepath().get(i));
        }

        try {
            for (int i = 0; i < stringArrayList.size(); i++) {
                galleryLists.add(new GalleryList(stringArrayList.get(i), "0", "0"));
            }

        } catch (Exception e) {
        }

        try {
            if (tagImageLists.size() == 0) {
                return galleryLists.size();
                // galleryRecyclerview.setAdapter(galleryAdapter);
            } else {
                boolean add = false;
                for (int i = 0; i < galleryLists.size(); i++) {
                    for (int j = 0; j < tagImageLists.size(); j++) {
                        if (galleryLists.get(i).getImage().equals(tagImageLists.get(j).getTag_image())) {
                            add = true;
                            break;
                        } else {
                            add = false;

                        }
                    }
                    if (add == false) {
                        galleryListstmp.add(new GalleryList(galleryLists.get(i).getImage(), "0", "0"));
                    }
                }
                //  galleryAdapter = new GalleryAdapter(dashboard, galleryListstmp);
                //     galleryRecyclerview.setAdapter(galleryAdapter);

            }

        } catch (Exception e) {
        }

        return galleryListstmp.size();

    }


}

