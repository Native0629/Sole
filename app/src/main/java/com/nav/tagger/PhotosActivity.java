package com.nav.tagger;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.nav.tagger.Activity.AllTagImagesActivity;
import com.nav.tagger.Activity.AllTagsActivity;
import com.nav.tagger.Activity.DashBoardActivity;
import com.nav.tagger.Model.GalleryList;
import com.nav.tagger.Model.TagImageList;
import com.nav.tagger.Utils.DatabaseHelper;
import com.nav.tagger.adapter.DrawerAdapter;
import com.nav.tagger.adapter.GalleryAdapter;
import com.nav.tagger.adapter.TagAdapter;
import com.nav.tagger.view.BoldButton;
import com.nav.tagger.view.BoldTextView;
import com.nav.tagger.view.RegularButton;
import com.nav.tagger.view.RegularEditText;
import com.nav.tagger.view.RegularTextView;

import java.util.ArrayList;

/**
 * Created by navitgupta on 01/07/18.
 */

public class PhotosActivity extends AppCompatActivity {
    private static GalleryAdapter galleryAdapter;
    public static int int_position;
    public static RecyclerView galleryRecyclerview;
    AllAlbumImageAdapter adapter;
    public static DatabaseHelper db;
    public static ArrayList<TagImageList> tagImageLists;
    public static ArrayList<TagImageList> tagImageListsSize;
    public static ArrayList<String> stringArrayList;
    public static ArrayList<GalleryList> galleryLists;
    public static ArrayList<GalleryList> galleryListstmp;
    public static PhotosActivity dashboard;
    private CardView btn_bottom_sheet;
    public static LinearLayout bottom_sheet;
    public static ImageView btn_bottom;
    public static BoldButton addTag;
    public static BoldButton addAlbum;
    public static BoldButton removeAlbum;
    public static BoldTextView albumname;
    public static RegularTextView selectedSize;
    private RegularTextView back;
    public static ArrayList<String> strings;
    public static boolean refresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        strings = new ArrayList<>();
        //strings.add("ADD ALBUM");
        strings.add("ADD TAG");
       // strings.add("REMOVE ALBUM");
        dashboard = this;
        tagImageLists = new ArrayList<>();
        stringArrayList = new ArrayList<>();
        galleryListstmp = new ArrayList<>();
        tagImageListsSize = new ArrayList<>();
        galleryLists = new ArrayList<>();
        selectedSize = (RegularTextView) findViewById(R.id.selectedSize);
        albumname = (BoldTextView) findViewById(R.id.albumname);
        btn_bottom_sheet = (CardView) findViewById(R.id.btn_bottom_sheet);
        db = new DatabaseHelper(PhotosActivity.this);
        galleryRecyclerview = (RecyclerView) findViewById(R.id.gv_folder);
        int_position = getIntent().getIntExtra("value", 0);
        bottom_sheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        btn_bottom = (ImageView) findViewById(R.id.btn_bottom);
        removeAlbum = (BoldButton) findViewById(R.id.removeAlbum);
        addAlbum = (BoldButton) findViewById(R.id.addAlbum);
        addTag = (BoldButton) findViewById(R.id.addTag);
        back = (RegularTextView) findViewById(R.id.back);

        albumname.setText(DashBoardActivity.al_images.get(int_position).getStr_folder());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(PhotosActivity.this, 5);

        galleryRecyclerview.setLayoutManager(mLayoutManager);
        int spanCount = 2;
        int spacing = 8;
        boolean includeEdge = true;
        galleryRecyclerview.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));


         /*adapter = new AllAlbumImageAdapter(this, DashBoardActivity.al_images,int_position);
        galleryRecyclerview.setAdapter(adapter);*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.obj_adapter.notifyDataSetChanged();
                finish();
            }
        });
        btn_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottom_sheet.getVisibility() == View.VISIBLE) {
                    bottom_sheet.setVisibility(View.GONE);
                } else bottom_sheet.setVisibility(View.VISIBLE);
            }
        });
        btn_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(btn_bottom, strings);
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
                Intent intent = new Intent(PhotosActivity.this, AllTagsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DashBoardActivity.obj_adapter.notifyDataSetChanged();

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

    @Override
    protected void onResume() {
        super.onResume();
        try{
            if (refresh){
                refresh = true;
                galleryImages();
            }else {
                refresh = true;
            }
        }catch (Exception e){

        }
    }

    public static void galleryImages() {
        tagImageLists.clear();
        stringArrayList.clear();
        galleryLists.clear();
        galleryListstmp.clear();
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
                galleryAdapter = new GalleryAdapter(dashboard, galleryLists);
                galleryRecyclerview.setAdapter(galleryAdapter);
                try {
                    String size = String.valueOf(galleryLists.size());
                    DashBoardActivity.strings.get(2).setName("All Non Tagged Pics"/* + " (" + size + ")"*/);
                    DashBoardActivity.adapter = new DrawerAdapter(dashboard, DashBoardActivity.strings);
                    DashBoardActivity.navigationDrawerList.setAdapter(DashBoardActivity.adapter);
                    DashBoardActivity.adapter.notifyDataSetChanged();
                } catch (Exception e) {
                }
                try {
                    tagImageListsSize.clear();
                    tagImageListsSize = db.getAllTagsImages();
                    String size = String.valueOf(tagImageListsSize.size());
                    DashBoardActivity.strings.get(1).setName("All Tagged Pics"/* + " (" + size + ")"*/);
                    DashBoardActivity.adapter = new DrawerAdapter(dashboard, DashBoardActivity.strings);
                    DashBoardActivity.navigationDrawerList.setAdapter(DashBoardActivity.adapter);
                    DashBoardActivity.adapter.notifyDataSetChanged();
                } catch (Exception e) {
                }
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
                galleryAdapter = new GalleryAdapter(dashboard, galleryListstmp);
                galleryRecyclerview.setAdapter(galleryAdapter);
                try {
                    String size = String.valueOf(galleryListstmp.size() / 2);
                    DashBoardActivity.strings.get(2).setName("All Non Tagged Pics"/* + " (" + size + ")"*/);
                    DashBoardActivity.adapter = new DrawerAdapter(dashboard, DashBoardActivity.strings);
                    DashBoardActivity.navigationDrawerList.setAdapter(DashBoardActivity.adapter);
                    DashBoardActivity.adapter.notifyDataSetChanged();
                } catch (Exception e) {
                }
                try {
                    tagImageListsSize.clear();
                    tagImageListsSize = db.getAllTagsImages();
                    String size = String.valueOf(tagImageListsSize.size());
                    DashBoardActivity.strings.get(1).setName("All Tagged Pics"/* + " (" + size + ")"*/);
                    DashBoardActivity.adapter = new DrawerAdapter(dashboard, DashBoardActivity.strings);
                    DashBoardActivity.navigationDrawerList.setAdapter(DashBoardActivity.adapter);
                    DashBoardActivity.adapter.notifyDataSetChanged();
                } catch (Exception e) {
                }

            }

        } catch (Exception e) {
        }


    }

    public static void showMenu(final View view, final ArrayList<String> item_status) {
        final PopupMenu menu = new PopupMenu(dashboard, view);
        for (int i = 0; i < item_status.size(); i++) {
            menu.getMenu().add(item_status.get(i));
        }
        menu.show();
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // ((TextView) view).setText(item.getTitle());
                for (int j = 0; j < item_status.size(); j++) {
                    if (item_status.get(j).equals(item.getTitle())) {
                        // ((TextView) view).setTag(item_status.get(j));

                        // tag_Name = view.getTag() != null ? view.getTag().toString() : "";
                        // new ServiceDetailAsync().execute(edt_status.getTag() != null ? edt_status.getTag().toString() : "");
                        if (item_status.get(j).equals("ADD ALBUM")) {

                        } else if (item_status.get(j).equals("ADD TAG")) {
                            alertDialogTag();
                        } /*else if (item_status.get(j).equals("REMOVE ALBUM")) {

                            Intent intent = new Intent(dashboard, AllTagsActivity.class);
                            dashboard.startActivity(intent);
                        }*/


                    }


                }
                return true;
            }
        });
    }


    @SuppressLint("ResourceAsColor")
    public static void alertDialogTag() {
        final Dialog progress_dialog = new android.app.Dialog(dashboard);
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
                    Toast.makeText(dashboard, "ADD Tag Successfully", Toast.LENGTH_LONG).show();

                }
            }
        });
        progress_dialog.show();
    }


}
