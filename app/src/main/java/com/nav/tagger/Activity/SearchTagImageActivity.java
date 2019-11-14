package com.nav.tagger.Activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.nav.tagger.Model.AddTagList;
import com.nav.tagger.Model.TagImageList;
import com.nav.tagger.R;
import com.nav.tagger.Utils.DatabaseHelper;
import com.nav.tagger.adapter.AllTagImageAdapter;
import com.nav.tagger.adapter.SearchImageAdapter;
import com.nav.tagger.view.RegularEditText;
import com.nav.tagger.view.RegularTextView;

import java.util.ArrayList;

/**
 * Created by navitgupta on 22/06/18.
 */

public class SearchTagImageActivity extends AppCompatActivity {

    private RecyclerView tagListImage;
    private ArrayList<TagImageList> tagImageLists;
    public static ArrayList<TagImageList> tagImageListstemp;
    private DatabaseHelper db;
    private SearchImageAdapter adapter;
    private RegularTextView back;
    private RegularEditText serachTag;
    public  ArrayList<AddTagList> addTagLists;
    boolean hide = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_tag_activity);
        init();
    }

    private void init() {
        back = findViewById(R.id.back);
        db = new DatabaseHelper(this);
        tagListImage = findViewById(R.id.tagListImage);
        serachTag = findViewById(R.id.serachTag);
        tagImageLists = new ArrayList<>();
        tagImageListstemp = new ArrayList<>();
        addTagLists = db.getAllTags();
        tagImageLists.clear();
        tagImageListstemp.clear();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SearchTagImageActivity.this, 5);

        tagListImage.setLayoutManager(mLayoutManager);
        int spanCount = 2;
        int spacing = 20;
        boolean includeEdge = true;
        tagListImage.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        tagImageLists = db.getAllTagsImages();
        try{
            serachTag.setText(getIntent().getStringExtra("tag_name"));
            for (int i = 0; i < addTagLists.size(); i++) {
                if (serachTag.getText().toString().trim().equalsIgnoreCase(addTagLists.get(i).getTag())){
                    if (addTagLists.get(i).getHide().equals("hide")){
                        hide = true;
                    }
                }
            }
            if (hide == true){

            }else
            for (int i = 0; i < tagImageLists.size(); i++) {
                if (serachTag.getText().toString().trim().equalsIgnoreCase(tagImageLists.get(i).getTag())){
                    tagImageListstemp.add(tagImageLists.get(i));
                }
            }
            initializeRecycler();
        }catch (Exception e){}
        serachTag.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                tagImageListstemp.clear();
                try{

                    for (int i = 0; i < addTagLists.size(); i++) {
                        if (serachTag.getText().toString().trim().equalsIgnoreCase(addTagLists.get(i).getTag())){
                            if (addTagLists.get(i).getHide().equals("hide")){
                                hide = true;
                            }
                        }
                    }
                    if (hide == true){

                    }else
                        for (int i = 0; i < tagImageLists.size(); i++) {
                            if (serachTag.getText().toString().trim().equalsIgnoreCase(tagImageLists.get(i).getTag())){
                                tagImageListstemp.add(tagImageLists.get(i));
                            }
                        }
                    initializeRecycler();
                }catch (Exception e){}

/*
                if(s.length() != 0){
                    for (int i = 0; i < tagImageLists.size(); i++) {
                        if (s.toString().trim().equalsIgnoreCase(tagImageLists.get(i).getTag())){
                            tagImageListstemp.add(tagImageLists.get(i));
                        }
                    }
                    initializeRecycler();
                }
*/

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    public  void initializeRecycler() {
        adapter = new SearchImageAdapter(SearchTagImageActivity.this, tagImageListstemp);
        tagListImage.setAdapter(adapter);

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
