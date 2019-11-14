package com.nav.tagger.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.mukesh.permissions.AppPermissions;
import com.nav.tagger.Adapter_PhotosFolder;
import com.nav.tagger.AlbumAdapter;
import com.nav.tagger.Model.DraweList;
import com.nav.tagger.Model.GalleryList;
import com.nav.tagger.Model.TagImageList;
import com.nav.tagger.Model_images;
import com.nav.tagger.R;
import com.nav.tagger.Utils.AppUtills;
import com.nav.tagger.Utils.DatabaseHelper;
import com.nav.tagger.Utils.SessionManager;
import com.nav.tagger.adapter.DrawerAdapter;
import com.nav.tagger.adapter.GalleryAdapter;
import com.nav.tagger.adapter.NonTagAdapter;
import com.nav.tagger.adapter.TagAdapter;
import com.nav.tagger.view.BoldButton;
import com.nav.tagger.view.BoldTextView;
import com.nav.tagger.view.RegularButton;
import com.nav.tagger.view.RegularEditText;
import com.nav.tagger.view.RegularTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fujitsu on 10-05-2018.
 */

public class DashBoardActivity extends AppCompatActivity {

    private static final int ALL_REQUEST_CODE = 0;
    public static RecyclerView navigationDrawerList;
    public static ArrayList<DraweList> strings;
    public static DrawerAdapter adapter;
    public static GalleryAdapter galleryAdapter;
    public static RecyclerView galleryRecyclerview;
    private ImageView draweicon;
    public static ArrayList<String> stringArrayList;
    private NavigationView nav_view;
    private DrawerLayout drawer_layout;
    public static ArrayList<GalleryList> galleryLists;
    public static ArrayList<GalleryList> galleryListstmp;
    public static RegularTextView setting;
    private AppPermissions mRuntimePermission;
    public static ArrayList<TagImageList> tagImageLists;
    public static ArrayList<TagImageList> tagImageListsSize;
    public static DatabaseHelper db;
    public static DashBoardActivity dashboard;

    // CardView layoutBottomSheet;

    private CardView btn_bottom_sheet;
    public static LinearLayout bottom_sheet;
    public static BoldButton addTag;
    public static BoldButton addAlbum;
    public static BoldButton removeAlbum;
    private RegularTextView serachActivity;

    public static ArrayList<Model_images> al_images = new ArrayList<>();
    boolean boolean_folder;
    public static AlbumAdapter obj_adapter;
    public static BoldTextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        dashboard = this;
        init();
    }

    private void init() {
        db = new DatabaseHelper(this);
        mRuntimePermission = new AppPermissions();
        tagImageLists = new ArrayList<>();
        galleryLists = new ArrayList<>();
        tagImageListsSize = new ArrayList<>();
        galleryListstmp = new ArrayList<>();
        stringArrayList = new ArrayList<>();
        tagImageLists.clear();
        galleryListstmp.clear();
        textView = (BoldTextView) findViewById(R.id.textView);
        setting = (RegularTextView) findViewById(R.id.setting);
        serachActivity = (RegularTextView) findViewById(R.id.serachActivity);
        draweicon = (ImageView) findViewById(R.id.draweicon);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        galleryRecyclerview = (RecyclerView) findViewById(R.id.galleryRecyclerview);
        navigationDrawerList = (RecyclerView) findViewById(R.id.navigationDrawerList);
        strings = new ArrayList<>();
        // bottomSheetDialog();
        strings.add(new DraweList("All Tags", getResources().getDrawable(R.drawable.home)));
        //strings.add(new DraweList("All Tagged Wise Pics", getResources().getDrawable(R.drawable.home)));
        strings.add(new DraweList("All Tagged Pics", getResources().getDrawable(R.drawable.home)));
        strings.add(new DraweList("All Non Tagged Pics", getResources().getDrawable(R.drawable.home)));
        strings.add(new DraweList("Change Password", getResources().getDrawable(R.drawable.hide)));
        strings.add(new DraweList("Help", getResources().getDrawable(R.drawable.home)));
        initializeRecycler();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(DashBoardActivity.this, 2);

        galleryRecyclerview.setLayoutManager(mLayoutManager);
        int spanCount = 2;
        int spacing = 18;
        boolean includeEdge = true;
        galleryRecyclerview.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        if (!mRuntimePermission.hasPermission(this, AppUtills.ALL_PERMISSIONS)) {
            try{
                galleryImages();
            }catch (Exception e){}
            fn_imagespath();
        } else {
            mRuntimePermission.requestPermission(this, AppUtills.ALL_PERMISSIONS, 0);
        }


        draweicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.openDrawer(Gravity.LEFT);
            }
        });
        serachActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, SearchTagImageActivity.class);
                startActivity(intent);
            }
        });
        btn_bottom_sheet = (CardView) findViewById(R.id.btn_bottom_sheet);
        bottom_sheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        removeAlbum = (BoldButton) findViewById(R.id.removeAlbum);
        addAlbum = (BoldButton) findViewById(R.id.addAlbum);
        addTag = (BoldButton) findViewById(R.id.addTag);
        btn_bottom_sheet.setVisibility(View.GONE);
        btn_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottom_sheet.getVisibility() == View.VISIBLE) {
                    bottom_sheet.setVisibility(View.GONE);
                } else bottom_sheet.setVisibility(View.VISIBLE);
            }
        });
        addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogTag();
                bottom_sheet.setVisibility(View.GONE);
            }
        });
        removeAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottom_sheet.setVisibility(View.GONE);
                Intent intent = new Intent(DashBoardActivity.this, AllTagsActivity.class);
                startActivity(intent);
            }
        });

    }


    @SuppressLint("ResourceAsColor")
    public void alertDialogTag() {
        final Dialog progress_dialog = new android.app.Dialog(DashBoardActivity.this);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setContentView(R.layout.add_tag_dialog);
        final Window window = progress_dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(R.color.black_transparent));
        RegularButton save = (RegularButton) progress_dialog.findViewById(R.id.save);
        RegularButton cancel_action = (RegularButton) progress_dialog.findViewById(R.id.cancel_action);
        final RegularEditText tag_name = (RegularEditText) progress_dialog.findViewById(R.id.tag_name);


        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!tag_name.getText().toString().equals("")) {
                    progress_dialog.dismiss();
                    db.insertADDTAG(tag_name.getText().toString());
                    Toast.makeText(DashBoardActivity.this, "ADD Tag Successfully", Toast.LENGTH_LONG).show();

                }


            }
        });
        progress_dialog.show();
    }

    public ArrayList<Model_images> fn_imagespath() {
        al_images.clear();

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Log.e("Column", absolutePathOfImage);
            Log.e("Folder", cursor.getString(column_index_folder_name));

            for (int i = 0; i < al_images.size(); i++) {
                if (al_images.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) {
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    boolean_folder = false;
                }
            }


            if (boolean_folder) {

                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(al_images.get(int_position).getAl_imagepath());
                al_path.add(absolutePathOfImage);
                al_images.get(int_position).setAl_imagepath(al_path);

            } else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                Model_images obj_model = new Model_images();
                obj_model.setStr_folder(cursor.getString(column_index_folder_name));
                obj_model.setAl_imagepath(al_path);

                al_images.add(obj_model);


            }


        }


        for (int i = 0; i < al_images.size(); i++) {
            Log.e("FOLDER", al_images.get(i).getStr_folder());
            for (int j = 0; j < al_images.get(i).getAl_imagepath().size(); j++) {
                Log.e("FILE", al_images.get(i).getAl_imagepath().get(j));
            }
        }
        video();
        obj_adapter = new AlbumAdapter(getApplicationContext(), al_images);
        galleryRecyclerview.setAdapter(obj_adapter);
        return al_images;
    }

    public void video() {


        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null;
        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Video.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Log.e("Column", absolutePathOfImage);
            Log.e("Folder", cursor.getString(column_index_folder_name));

            for (int i = 0; i < al_images.size(); i++) {
                if (al_images.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) {
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    boolean_folder = false;
                }
            }


            if (boolean_folder) {

                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(al_images.get(int_position).getAl_imagepath());
                al_path.add(absolutePathOfImage);
                al_images.get(int_position).setAl_imagepath(al_path);

            } else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                Model_images obj_model = new Model_images();
                obj_model.setStr_folder(cursor.getString(column_index_folder_name));
                obj_model.setAl_imagepath(al_path);

                al_images.add(obj_model);


            }


        }


        for (int i = 0; i < al_images.size(); i++) {
            Log.e("FOLDER", al_images.get(i).getStr_folder());
            for (int j = 0; j < al_images.get(i).getAl_imagepath().size(); j++) {
                Log.e("FILE", al_images.get(i).getAl_imagepath().get(j));
            }
        }


    }


    public static  void galleryImages() {

        stringArrayList = getAllShownImagesPath(dashboard);
        try{
            int size = stringArrayList.size();
            textView.setText("Total Photos in Phone : " +String.valueOf(size));
        }catch (Exception e){}



    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            tagImageListsSize.clear();
            tagImageListsSize = db.getAllTagsImages();
            String size = String.valueOf(tagImageListsSize.size());
            strings.clear();
            String nonsize = String.valueOf(galleryListstmp.size());
            if (galleryListstmp.size() == 0) {
                if (tagImageLists.size() == 0) {
                    nonsize = String.valueOf(galleryLists.size());
                }
            }
            strings.add(new DraweList("All Tags", getResources().getDrawable(R.drawable.home)));
            //strings.add(new DraweList("All Tagged Wise Pics", getResources().getDrawable(R.drawable.home)));
            strings.add(new DraweList("All Tagged Pics" /*+ " (" + size + ")"*/, getResources().getDrawable(R.drawable.home)));
            strings.add(new DraweList("All Non Tagged Pics"/* + " (" + nonsize + ")"*/, getResources().getDrawable(R.drawable.home)));
            strings.add(new DraweList("Change Password", getResources().getDrawable(R.drawable.hide)));
            strings.add(new DraweList("Help", getResources().getDrawable(R.drawable.home)));
            adapter = new DrawerAdapter(DashBoardActivity.this, strings);
            navigationDrawerList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ALL_REQUEST_CODE:
                List<Integer> permissionResults = new ArrayList<>();
                for (int grantResult : grantResults) {
                    permissionResults.add(grantResult);
                }
                fn_imagespath();
                /*stringArrayList = getAllShownImagesPath(DashBoardActivity.this);
                tagImageLists = db.getAllTagsImages();
                try {
                    for (int i = 0; i < stringArrayList.size(); i++) {
                        galleryLists.add(new GalleryList(stringArrayList.get(i), "0", "0"));
                    }

                } catch (Exception e) {
                }

                try {
                    if (tagImageLists.size() == 0) {
                        galleryAdapter = new GalleryAdapter(DashBoardActivity.this, galleryLists);
                        galleryRecyclerview.setAdapter(galleryAdapter);
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
                        galleryAdapter = new GalleryAdapter(DashBoardActivity.this, galleryListstmp);
                        galleryRecyclerview.setAdapter(galleryAdapter);
                    }

                } catch (Exception e) {
                }*/


                break;
        }
    }


    private void initializeRecycler() {
        navigationDrawerList.setLayoutManager(new LinearLayoutManager(DashBoardActivity.this));
        navigationDrawerList.setItemAnimator(new DefaultItemAnimator());
        adapter = new DrawerAdapter(DashBoardActivity.this, strings);
        navigationDrawerList.setAdapter(adapter);

    }

    public static ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

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

    @SuppressLint("ResourceAsColor")
    public static  void alertDialogPassword() {
        final Dialog progress_dialog = new android.app.Dialog(dashboard);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setContentView(R.layout.chang_password_dialog);
        final Window window = progress_dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(R.color.black_transparent));
        RegularButton save = (RegularButton) progress_dialog.findViewById(R.id.save);
        RegularButton cancel_action = (RegularButton) progress_dialog.findViewById(R.id.cancel_action);
        final RegularEditText tag_name = (RegularEditText) progress_dialog.findViewById(R.id.tag_name);
        final RegularEditText tag_namenew = (RegularEditText) progress_dialog.findViewById(R.id.tag_namenew);


        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tag_name.getText().toString().equals(SessionManager.getPassword(dashboard))) {

                    if (!tag_namenew.getText().toString().trim().equals("")) {
                        progress_dialog.dismiss();
                        SessionManager.setPassword(dashboard,tag_namenew.getText().toString().trim());
                    }else  Toast.makeText(dashboard,"Enter new password",Toast.LENGTH_LONG).show();
                }else      Toast.makeText(dashboard,"Old Password is wrong",Toast.LENGTH_LONG).show();



            }
        });
        progress_dialog.show();
    }

}
