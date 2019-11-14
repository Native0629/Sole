package com.nav.tagger.Activity;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nav.tagger.Model.GalleryList;
import com.nav.tagger.Model.TagImageList;
import com.nav.tagger.R;
import com.nav.tagger.Utils.DatabaseHelper;
import com.nav.tagger.adapter.GalleryAdapter;
import com.nav.tagger.adapter.NonTagAdapter;
import com.nav.tagger.view.RegularTextView;

import java.util.ArrayList;

/**
 * Created by navitgupta on 12/06/18.
 */

public class NonTagActivity extends AppCompatActivity {
    private RecyclerView tagListImage;
    private RegularTextView back;
    private ArrayList <GalleryList> galleryLists;
    private ArrayList <GalleryList> tempgalleryLists;
    private ArrayList<String> stringArrayList;
    private NonTagAdapter galleryAdapter;
    private DatabaseHelper db;
    private ArrayList <TagImageList>tagImageLists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_tag_image);
        init();
    }

    private void init() {
        db = new DatabaseHelper(this);
        galleryLists= new ArrayList<>();
        tagImageLists= new ArrayList<>();
        tempgalleryLists= new ArrayList<>();
        stringArrayList= new ArrayList<>();
        stringArrayList.clear();
        tempgalleryLists.clear();
        tagImageLists.clear();
        tagListImage = findViewById(R.id.tagListImage);
        back = findViewById(R.id.back);
        tagImageLists = db.getAllTagsImages();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(NonTagActivity.this, 5);

        tagListImage.setLayoutManager(mLayoutManager);
        int spanCount = 2;
        int spacing = 8;
        boolean includeEdge = true;
        tagListImage.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        stringArrayList = getAllShownImagesPath(NonTagActivity.this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        try{
            for (int i = 0; i < stringArrayList.size(); i++) {
                galleryLists.add(new GalleryList(stringArrayList.get(i),"0","0"));
            }

        }catch (Exception e){}

        try{
            if (tagImageLists.size()==0){
                galleryAdapter = new NonTagAdapter(NonTagActivity.this, galleryLists);
                tagListImage.setAdapter(galleryAdapter);
            }else {
                boolean add = false;
                for (int i = 0; i < galleryLists.size(); i++) {
                    for (int j = 0; j < tagImageLists.size(); j++) {
                        if (galleryLists.get(i).getImage().equals(tagImageLists.get(j).getTag_image())){
                            add= true;
                            break;
                        }else {
                           add = false;

                        }
                    }
                    if (add==false){
                        tempgalleryLists.add(new GalleryList(galleryLists.get(i).getImage(),"0","0"));
                    }
                }
                galleryAdapter = new NonTagAdapter(NonTagActivity.this, tempgalleryLists);
                tagListImage.setAdapter(galleryAdapter);
            }

        }catch (Exception e){}


    }

    private ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
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
