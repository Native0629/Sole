package com.nav.tagger.Activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nav.tagger.Model.GalleryList;
import com.nav.tagger.Model.TagImageList;
import com.nav.tagger.PhotosActivity;
import com.nav.tagger.R;
import com.nav.tagger.Utils.DatabaseHelper;
import com.nav.tagger.adapter.AllTagImageAdapter;
import com.nav.tagger.adapter.DrawerAdapter;
import com.nav.tagger.adapter.GalleryAdapter;
import com.nav.tagger.adapter.TagAdapter;
import com.nav.tagger.view.RegularTextView;

import java.util.ArrayList;

/**
 * Created by navitgupta on 12/06/18.
 */

public class AllTagImagesActivity extends AppCompatActivity {

    private RecyclerView tagListImage;
    private ArrayList <TagImageList>tagImageLists;
    private DatabaseHelper db;
    private AllTagImageAdapter adapter;
    public static RegularTextView noTag;
    private RegularTextView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tag);
        init();
    }

    private void init() {
        back = findViewById(R.id.back);
        db = new DatabaseHelper(this);
        tagListImage = findViewById(R.id.tagListImage);
        noTag =findViewById(R.id.noTag);
        tagImageLists = new ArrayList<>();

        tagImageLists.clear();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(AllTagImagesActivity.this, 5);

        tagListImage.setLayoutManager(mLayoutManager);
        int spanCount = 2;
        int spacing = 8;
        boolean includeEdge = true;
        tagListImage.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        tagImageLists = db.getAllTagsImages();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initializeRecycler();


    }

    public  void initializeRecycler() {
        if (tagImageLists.size()==0){
            noTag.setVisibility(View.VISIBLE);
            tagListImage.setVisibility(View.GONE);
        }else {
            noTag.setVisibility(View.GONE);
            tagListImage.setVisibility(View.VISIBLE);
            adapter = new AllTagImageAdapter(AllTagImagesActivity.this, tagImageLists);
            tagListImage.setAdapter(adapter);
        }


    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;
                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }




}
