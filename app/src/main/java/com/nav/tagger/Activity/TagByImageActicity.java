package com.nav.tagger.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.nav.tagger.Model.AddTagList;
import com.nav.tagger.Model.TagImageList;
import com.nav.tagger.R;
import com.nav.tagger.Utils.DatabaseHelper;
import com.nav.tagger.adapter.AllTagImageAdapter;
import com.nav.tagger.adapter.TagAdapter;
import com.nav.tagger.adapter.TagWiseAdapter;
import com.nav.tagger.view.RegularButton;
import com.nav.tagger.view.RegularEditText;
import com.nav.tagger.view.RegularTextView;

import java.util.ArrayList;

/**
 * Created by Fujitsu on 13-06-2018.
 */

public class TagByImageActicity extends AppCompatActivity {

    public static DatabaseHelper db;
    public static RecyclerView tagList;
    public static ArrayList<AddTagList> addTagLists;
    public static ArrayList<AddTagList> addTagListstemp;
    public static TagWiseAdapter adapter;
    public static AllTagImageAdapter allTagImageAdapter;
    public static TagByImageActicity allTagsActivity;
    public static RecyclerView tagByImages;
    public static RegularTextView back;
    public static ArrayList <TagImageList>tagImageLists;
    public static RegularTextView noTag;
    private LinearLayout tagNO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_wise_image);
        allTagsActivity = this;
        init();
    }

    private void init() {
        addTagLists = new ArrayList<>();
        addTagListstemp = new ArrayList<>();
        tagList = (RecyclerView)findViewById(R.id.tagList);
        back = (RegularTextView) findViewById(R.id.back);
        noTag = (RegularTextView) findViewById(R.id.noTag);
        tagNO = (LinearLayout) findViewById(R.id.tagNO);
        db = new DatabaseHelper(this);


        initializeRecycler();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tagByImages = findViewById(R.id.tagByImages);
        tagImageLists = new ArrayList<>();

        tagImageLists.clear();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(TagByImageActicity.this, 3);

        tagByImages.setLayoutManager(mLayoutManager);
        int spanCount = 3;
        int spacing = 8;
        boolean includeEdge = true;
        tagByImages.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        tagImageLists = db.getAllTagsImageByTag("Navit");
        initializeRecyclerTag();

    }
    public static void initializeRecyclerTag() {

        allTagImageAdapter = new AllTagImageAdapter(allTagsActivity, tagImageLists);
        tagByImages.setAdapter(allTagImageAdapter);

    }


    public static void initializeRecycler() {
        addTagLists.clear();
        addTagListstemp.clear();
        addTagLists = db.getAllTags();
        tagList.setLayoutManager(new LinearLayoutManager(allTagsActivity));
        tagList.setItemAnimator(new DefaultItemAnimator());
        for (int i = 0; i < addTagLists.size(); i++) {
            if (addTagLists.get(i).getHide().equals("unhide")){
                addTagListstemp.add(addTagLists.get(i));
            }
        }

        if (addTagListstemp.size()==0){
            noTag.setVisibility(View.VISIBLE);
            tagList.setVisibility(View.GONE);
        }else {
            noTag.setVisibility(View.GONE);
            tagList.setVisibility(View.VISIBLE);
            adapter = new TagWiseAdapter(allTagsActivity, addTagListstemp);
            tagList.setAdapter(adapter);
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

