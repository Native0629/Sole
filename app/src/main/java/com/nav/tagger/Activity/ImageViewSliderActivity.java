package com.nav.tagger.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.nav.tagger.MainActivity;
import com.nav.tagger.Model.AddTagList;
import com.nav.tagger.Model.GalleryList;
import com.nav.tagger.Model.TagImageList;
import com.nav.tagger.PhotosActivity;
import com.nav.tagger.R;
import com.nav.tagger.Utils.DatabaseHelper;
import com.nav.tagger.adapter.BottomPagerAdapter;
import com.nav.tagger.adapter.GalleryAdapter;
import com.nav.tagger.adapter.SlidingImage_Adapter;
import com.nav.tagger.view.PicassoImageLoader;
import com.nav.tagger.view.RegularButton;
import com.nav.tagger.view.RegularTextView;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.nav.tagger.view.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.loader.DefaultImageLoader;
import com.veinhorn.scrollgalleryview.loader.DefaultVideoLoader;
import com.veinhorn.scrollgalleryview.loader.MediaLoader;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by navitgupta on 19/06/18.
 */

public class ImageViewSliderActivity extends AppCompatActivity {
    public static ViewPager mPager;
    private static RecyclerView pagerbottom;
    private static ImageView moreItem;
    private static RegularTextView back;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<TagImageList> IMAGES;
    private ArrayList<GalleryList> galleryList;
    public static ArrayList<String> IMAGESs;
    private DatabaseHelper db;
    public static ArrayList<String> stringArrayList;
    public static ArrayList<TagImageList> galleryLists;
    public static ArrayList<TagImageList> galleryListstmp;
    public static ArrayList<TagImageList> tagImageLists;
    private ScrollGalleryView scrollGalleryView;
    private RegularTextView title;
    public static ArrayList<String> moreItemarray;
    public static Toolbar toolbar;
    private Bitmap bitmapOrg =null;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);
        init();
    }

    private void init() {
        IMAGESs = new ArrayList<>();
        moreItemarray = new ArrayList<>();
        tagImageLists = new ArrayList<>();
        galleryLists = new ArrayList<>();
        galleryListstmp = new ArrayList<>();
        stringArrayList = new ArrayList<>();
        tagImageLists.clear();
        galleryListstmp.clear();
        IMAGES = new ArrayList<>();
        galleryList = new ArrayList<>();
//

        db = new DatabaseHelper(this);
        tagImageLists = db.getAllTagsImages();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        moreItem = (ImageView) findViewById(R.id.moreItem);
        title = (RegularTextView) findViewById(R.id.title);
        mPager = (ViewPager) findViewById(R.id.pager);
        back = (RegularTextView) findViewById(R.id.back);
        pagerbottom = (RecyclerView) findViewById(R.id.pagerbottom);
        pagerbottom.setLayoutManager(new LinearLayoutManager(ImageViewSliderActivity.this, LinearLayoutManager.HORIZONTAL, false));
        pagerbottom.setItemAnimator(new DefaultItemAnimator());
        if (getIntent().getStringExtra("tag").equals("0")) {
            IMAGES = db.getAllTagsImages();
            for (int i = 0; i < IMAGES.size(); i++) {
                IMAGESs.add(IMAGES.get(i).getTag_image());
            }

            // mPager.setAdapter(new SlidingImage_Adapter(ImageViewSliderActivity.this,IMAGES));
            //   pagerbottom.setAdapter(new BottomPagerAdapter(ImageViewSliderActivity.this,IMAGES));
        } else if (getIntent().getStringExtra("tag").equals("1")) {
            galleryImagess();

        } else if (getIntent().getStringExtra("tag").equals("5")) {
            IMAGES = SearchTagImageActivity.tagImageListstemp;
            for (int i = 0; i < IMAGES.size(); i++) {
                IMAGESs.add(IMAGES.get(i).getTag_image());
            }

        } else if (getIntent().getStringExtra("tag").equals("6")) {
            try {
                galleryList.clear();
                if (PhotosActivity.tagImageLists.size() == 0) {
                    galleryList = PhotosActivity.galleryLists;
                } else {
                    galleryList = PhotosActivity.galleryListstmp;
                }

                for (int i = 0; i < galleryList.size(); i++) {
                    IMAGESs.add(galleryList.get(i).getImage());
                }
            } catch (Exception e) {

            }


        } else if (getIntent().getStringExtra("tag").equals("2")) {
            title.setText(getIntent().getStringExtra("tag_name"));
            IMAGESs.clear();
            for (int i = 0; i < tagImageLists.size(); i++) {
                if (getIntent().getStringExtra("tag_name").equals(tagImageLists.get(i).getTag())) {
                    IMAGESs.add(tagImageLists.get(i).getTag_image());
                }
            }


        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mPager.setCurrentItem(getIntent().getIntExtra("pos", 0));
        //pagerbottom.setCurrentItem(getIntent().getIntExtra("pos",0));
        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        // indicator.setViewPager(mPager);
        //  indicator.setViewPager(pagerbottom);

        //  final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        //    indicator.setRadius(5 * density);

        //  NUM_PAGES =IMAGES.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
                //  pagerbottom.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
/*
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
*/

        // Pager listener over indicator
       /* indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
*/
        try {
            List<MediaInfo> infos = new ArrayList<>(IMAGESs.size());
            for (String url : IMAGESs)
                infos.add(MediaInfo.mediaLoader(new PicassoImageLoader(url)));

            scrollGalleryView = (ScrollGalleryView) findViewById(R.id.scroll_gallery_view);
            scrollGalleryView
                    .setThumbnailSize(140)
                    .setZoom(true)
                    .hideThumbnailsOnClick(true)
                    .setFragmentManager(getSupportFragmentManager())
                    .addMedia(infos)
                    .setCurrentItem(getIntent().getIntExtra("pos", 0));
        } catch (Exception e) {
        }

        try{

            scrollGalleryView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (toolbar.getVisibility()== View.VISIBLE){
                        toolbar.setVisibility(View.GONE);
                    }else toolbar.setVisibility(View.VISIBLE);
                }
            });
        }catch (Exception e){}


        moreItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moreItemarray.clear();
                moreItemarray.add("Share");
               // moreItemarray.add("Delete");
                moreItemarray.add("Set as wallpaper");
                moreItemarray.add("Detail");
                showMenu(moreItem, moreItemarray);
                 bitmapOrg = null;
                try {
                    bitmapOrg  = MediaStore.Images.Media.getBitmap(getContentResolver(),Uri.fromFile(new File(IMAGESs.get(scrollGalleryView.getCurrentItem())))) ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        detail();
    }

    public  void detail(){
try{

    Uri returnUri = Uri.fromFile(new File(IMAGESs.get(scrollGalleryView.getCurrentItem())));
    Cursor returnCursor =
            getContentResolver().query(returnUri, null, null, null, null);
    /*
     * Get the column indexes of the data in the Cursor,
     * move to the first row in the Cursor, get the data,
     * and display it.
     */
    int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
    int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
    returnCursor.moveToFirst();
    String nameView = (returnCursor.getString(nameIndex));
    String sizeView =nameView + " " + (Long.toString(returnCursor.getLong(sizeIndex)));

}catch (Exception e){
    e.getMessage();
}


    }
    public void showMenu(final View view, final ArrayList<String> item_status) {
        final PopupMenu menu = new PopupMenu(ImageViewSliderActivity.this, view);
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

                        if (item_status.get(j).equals("Share")) {

                            try {
                                Intent share = new Intent(Intent.ACTION_SEND);
                                share.setType("image/jpeg");
                               /* ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                                File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
                                try {
                                    f.createNewFile();
                                    FileOutputStream fo = new FileOutputStream(f);
                                    fo.write(bytes.toByteArray());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }*/
                                share.putExtra(Intent.EXTRA_STREAM, Uri.parse(IMAGESs.get(scrollGalleryView.getCurrentItem())));
                                startActivity(Intent.createChooser(share, "Share"));
                            } catch (Exception e) {
                            }

                        } else if (item_status.get(j).equals("Delete")) {
                            alertDialogDelete(scrollGalleryView.getCurrentItem());
                        } else if (item_status.get(j).equals("Set as wallpaper")) {

                            WallpaperManager myWallpaperManager
                                    = WallpaperManager.getInstance(getApplicationContext());

                            Bitmap bitmap = null;
                            try {
                                bitmap  = MediaStore.Images.Media.getBitmap(getContentResolver(),Uri.fromFile(new File(IMAGESs.get(scrollGalleryView.getCurrentItem())))) ;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                myWallpaperManager.setBitmap(bitmap);
                                Toast.makeText(ImageViewSliderActivity.this,"Set wallpaper successfully",Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }else if (item_status.get(j).equals("Detail")) {

                            alertdialogdetail();
                        }
                    }


                }
                return true;
            }
        });
    }

    public Bitmap getThumbnail(Uri uri) throws FileNotFoundException, IOException {
        InputStream input = this.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > 300) ? (originalSize / 300) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither=true;//optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        input = this.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }
    private Bitmap toBitmap(int image) {
        return ((BitmapDrawable) getResources().getDrawable(image)).getBitmap();
    }
    @SuppressLint("ResourceAsColor")
    public void alertdialogdetail() {
        final Dialog progress_dialog = new android.app.Dialog(ImageViewSliderActivity.this);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setContentView(R.layout.detail_dialog);
        final Window window = progress_dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(R.color.black_transparent));
        RegularButton ok = (RegularButton) progress_dialog.findViewById(R.id.btnOk);
        final RegularTextView size = (RegularTextView) progress_dialog.findViewById(R.id.size);
        final RegularTextView path = (RegularTextView) progress_dialog.findViewById(R.id.path);
        path.setText("Path : " +IMAGESs.get(scrollGalleryView.getCurrentItem()));
        try{



            Bitmap bitmap = bitmapOrg;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] imageInByte = stream.toByteArray();
            long lengthbmp = imageInByte.length;
            size.setText("Size : " + String.valueOf(lengthbmp/1024) + " KB");
        }catch (Exception  e){}
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_dialog.dismiss();
            }
        });

        progress_dialog.show();
    }

    @SuppressLint("ResourceAsColor")
    public void alertDialogDelete(final int position) {
        final Dialog progress_dialog = new android.app.Dialog(ImageViewSliderActivity.this);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setContentView(R.layout.delete_dialog);
        final Window window = progress_dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(R.color.black_transparent));
        RegularButton delete = (RegularButton) progress_dialog.findViewById(R.id.delete);
        RegularButton cancel_action = (RegularButton) progress_dialog.findViewById(R.id.cancel_action);
        final RegularTextView tag_name = (RegularTextView) progress_dialog.findViewById(R.id.tag_name);
        tag_name.setText("Are you sure yo want to delete " + IMAGESs.get(position) + "?.");

        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_dialog.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // DashBoardActivity.ge();

            }
        });
        progress_dialog.show();
    }


    public void galleryImages() {
        tagImageLists.clear();
        stringArrayList.clear();
        galleryListstmp.clear();
        IMAGESs.clear();
        tagImageLists = db.getAllTagsImages();
        stringArrayList = getAllShownImagesPath(ImageViewSliderActivity.this);
        try {
            for (int i = 0; i < stringArrayList.size(); i++) {
                galleryLists.add(new TagImageList(2, "", stringArrayList.get(i), ""));
            }

        } catch (Exception e) {
        }

        try {
            if (tagImageLists.size() == 0) {
               /* galleryAdapter = new GalleryAdapter(dashboard, galleryLists);
                galleryRecyclerview.setAdapter(galleryAdapter);*/
                for (int i = 0; i < galleryLists.size(); i++) {
                    IMAGESs.add(galleryLists.get(i).getTag_image());
                }
                // mPager.setAdapter(new SlidingImage_Adapter(ImageViewSliderActivity.this,galleryLists));
                //  pagerbottom.setAdapter(new BottomPagerAdapter(ImageViewSliderActivity.this,galleryLists));
            } else {
                boolean add = false;
                for (int i = 0; i < galleryLists.size(); i++) {
                    for (int j = 0; j < tagImageLists.size(); j++) {
                        if (galleryLists.get(i).getTag_image().equals(tagImageLists.get(j).getTag_image())) {
                            add = true;
                            break;
                        } else {
                            add = false;

                        }
                    }
                    if (add == false) {
                        galleryListstmp.add(new TagImageList(3, "", galleryLists.get(i).getTag_image(), "0"));
                    }
                }
                for (int i = 0; i < galleryListstmp.size(); i++) {
                    IMAGESs.add(galleryListstmp.get(i).getTag_image());
                }
                ////   mPager.setAdapter(new SlidingImage_Adapter(ImageViewSliderActivity.this,galleryListstmp));
                //    pagerbottom.setAdapter(new BottomPagerAdapter(ImageViewSliderActivity.this,galleryListstmp));

            }

        } catch (Exception e) {
        }


    }

    public static void galleryImagess() {
        IMAGESs.clear();
        PhotosActivity.tagImageLists.clear();
        PhotosActivity.stringArrayList.clear();
        PhotosActivity.galleryLists.clear();
        PhotosActivity.galleryListstmp.clear();
        PhotosActivity.tagImageLists = PhotosActivity.db.getAllTagsImages();
        for (int i = 0; i < DashBoardActivity.al_images.get(PhotosActivity.int_position).getAl_imagepath().size(); i++) {
            PhotosActivity.stringArrayList.add(DashBoardActivity.al_images.get(PhotosActivity.int_position).getAl_imagepath().get(i));
        }

        try {
            for (int i = 0; i < PhotosActivity.stringArrayList.size(); i++) {
                PhotosActivity.galleryLists.add(new GalleryList(PhotosActivity.stringArrayList.get(i), "0", "0"));
            }

        } catch (Exception e) {
        }

        try {
            if (PhotosActivity.tagImageLists.size() == 0) {
                // galleryAdapter = new GalleryAdapter(PhotosActivity.dashboard, PhotosActivity.galleryLists);
                // galleryRecyclerview.setAdapter(galleryAdapter);
                for (int i = 0; i < PhotosActivity.galleryLists.size(); i++) {
                    IMAGESs.add(PhotosActivity.galleryLists.get(i).getImage());
                }
            } else {
                boolean add = false;
                for (int i = 0; i < PhotosActivity.galleryLists.size(); i++) {
                    for (int j = 0; j < PhotosActivity.tagImageLists.size(); j++) {
                        if (PhotosActivity.galleryLists.get(i).getImage().equals(PhotosActivity.tagImageLists.get(j).getTag_image())) {
                            add = true;
                            break;
                        } else {
                            add = false;

                        }
                    }
                    if (add == false) {
                        PhotosActivity.galleryListstmp.add(new GalleryList(PhotosActivity.galleryLists.get(i).getImage(), "0", "0"));
                    }
                }
                for (int i = 0; i < PhotosActivity.galleryListstmp.size(); i++) {
                    IMAGESs.add(PhotosActivity.galleryListstmp.get(i).getImage());
                }

                //    galleryAdapter = new GalleryAdapter(dashboard, galleryListstmp);
                //  galleryRecyclerview.setAdapter(galleryAdapter);

            }

        } catch (Exception e) {
        }


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


}
