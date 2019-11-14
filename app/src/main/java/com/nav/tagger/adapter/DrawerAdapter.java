package com.nav.tagger.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nav.tagger.Activity.AllTagImagesActivity;
import com.nav.tagger.Activity.AllTagsActivity;
import com.nav.tagger.Activity.DashBoardActivity;
import com.nav.tagger.Activity.NonTagActivity;
import com.nav.tagger.Activity.TagByImageActicity;
import com.nav.tagger.Model.DraweList;
import com.nav.tagger.R;
import com.nav.tagger.view.BoldTextView;

import java.util.ArrayList;

/**
 * Created by Fujitsu on 10-05-2018.
 */

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<DraweList> items;

    public DrawerAdapter(Context context, ArrayList<DraweList> items) {
        this.context = context;
        this.items = items;
    }


    @Override
    public DrawerAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawe_row_item, parent, false);
        return new DrawerAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final DrawerAdapter.ItemViewHolder holder, final int position) {

        holder.name.setText(items.get(position).getName());

        holder.draweicon.setImageDrawable(items.get(position).getImage());

        holder.draweicon.setVisibility(View.GONE);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private BoldTextView name;
        private ImageView draweicon;


        public ItemViewHolder(View itemView) {
            super(itemView);
            name = (BoldTextView) itemView.findViewById(R.id.name);
            draweicon = (ImageView) itemView.findViewById(R.id.draweicon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (getPosition() == 0) {
                Intent intent = new Intent(context, AllTagsActivity.class);
                context.startActivity(intent);
            } else if (getPosition() == 1) {
                Intent intent = new Intent(context, AllTagImagesActivity.class);
                context.startActivity(intent);
            }else if (getPosition() == 2) {
                Intent intent = new Intent(context, NonTagActivity.class);
                context.startActivity(intent);
            } else if (getPosition() == 3) {
                DashBoardActivity.alertDialogPassword();
            }
        }
    }


}
