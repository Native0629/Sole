package com.nav.tagger.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.nav.tagger.Activity.AllTagsActivity;
import com.nav.tagger.Activity.DashBoardActivity;
import com.nav.tagger.Activity.ImageViewSliderActivity;
import com.nav.tagger.Model.AddTagList;
import com.nav.tagger.Model.DraweList;
import com.nav.tagger.Model.GalleryList;
import com.nav.tagger.PhotosActivity;
import com.nav.tagger.R;
import com.nav.tagger.Utils.DatabaseHelper;
import com.nav.tagger.VideoActivity;
import com.nav.tagger.view.BoldTextView;
import com.nav.tagger.view.RegularButton;
import com.nav.tagger.view.RegularEditText;
import com.nav.tagger.view.RegularTextView;

import java.util.ArrayList;

/**
 * Created by navitgupta on 09/06/18.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ItemViewHolder> {

    public static Context context;
    public static ArrayList<GalleryList> items;
    public static DatabaseHelper db;
    public static String tag_Name = "";
    public static PhotosActivity dashBoardActivity;
    public static boolean trigger = false;
    public static boolean triggerLong = false;

    public GalleryAdapter(Context context, ArrayList<GalleryList> items) {
        this.context = context;
        this.items = items;
        db = new DatabaseHelper(context);
        dashBoardActivity = new PhotosActivity();

    }


    @Override
    public GalleryAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_image_row_item, parent, false);
        return new GalleryAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.ItemViewHolder holder, final int position) {

        Glide.with(context).load(items.get(position).getImage())
                .into(holder.galleryImage);
        if (items.get(position).getImage().contains(".mp4")) {
            holder.videoIcon.setVisibility(View.VISIBLE);
        } else if (items.get(position).getImage().contains(".MP4")) {
            holder.videoIcon.setVisibility(View.VISIBLE);
        } else if (items.get(position).getImage().contains(".3gp")) {
            holder.videoIcon.setVisibility(View.VISIBLE);
        } else if (items.get(position).getImage().contains(".3GP")) {
            holder.videoIcon.setVisibility(View.VISIBLE);
        } else if (items.get(position).getImage().contains(".mkv")) {
            holder.videoIcon.setVisibility(View.VISIBLE);
        } else if (items.get(position).getImage().contains(".MKV")) {
            holder.videoIcon.setVisibility(View.VISIBLE);
        } else {
            holder.videoIcon.setVisibility(View.GONE);
        }
        if (items.get(position).getChecked().equals("0")) {
            holder.checkbox.setVisibility(View.GONE);
            holder.checkbox.setImageDrawable(context.getResources().getDrawable(R.drawable.uncheck));
            holder.selectedLayout.setVisibility(View.GONE);
        } else {
            holder.checkbox.setVisibility(View.VISIBLE);
            holder.selectedLayout.setVisibility(View.GONE);
            holder.checkbox.setImageDrawable(context.getResources().getDrawable(R.drawable.checked));
        }
        holder.galleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotosActivity.refresh = false;
                //  if (trigger ==true){
                  /*  if (items.get(position).getChecked().equals("0")){
                        items.get(position).setChecked("1");
                    }else {
                        items.get(position).setChecked("0");
                    }

                    notifyItemChanged(position);*/


                   /* Intent intent = new Intent(context, ImageViewSliderActivity.class);
                    intent.putExtra("tag","1");
                    intent.putExtra("pos",position);
                    context.startActivity(intent);*/
                boolean check = true;
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getChecked().equals("1")) {

                        check = false;
                        break;
                    }
                }
                if (check == false) {
                    trigger = true;
                } else trigger = false;

                if (trigger == true) {
                    if (items.get(position).getChecked().equals("0")) {
                        items.get(position).setChecked("1");
                    } else {
                        items.get(position).setChecked("0");
                    }
                    try{
                        ArrayList <String> strings = new ArrayList<String>();
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).getChecked().equals("1")) {
                                strings.add(items.get(i).getImage());

                            }
                        }
                        if (strings.size()==0){
                            PhotosActivity.selectedSize.setText("");
                        }else {
                            PhotosActivity.selectedSize.setText(String.valueOf(strings.size()));

                        }


                    }catch (Exception e){}
                    notifyItemChanged(position);
                } else {
                    if (items.get(position).getImage().contains(".mp4")) {
                        Intent intent = new Intent(context, VideoActivity.class);
                        intent.putExtra("video", items.get(position).getImage());
                        context.startActivity(intent);
                    } else if (items.get(position).getImage().contains(".MP4")) {
                        Intent intent = new Intent(context, VideoActivity.class);
                        intent.putExtra("video", items.get(position).getImage());
                        context.startActivity(intent);
                    } else if (items.get(position).getImage().contains(".3gp")) {
                        Intent intent = new Intent(context, VideoActivity.class);
                        intent.putExtra("video", items.get(position).getImage());
                        context.startActivity(intent);
                    } else if (items.get(position).getImage().contains(".3GP")) {
                        Intent intent = new Intent(context, VideoActivity.class);
                        intent.putExtra("video", items.get(position).getImage());
                        context.startActivity(intent);
                    } else if (items.get(position).getImage().contains(".mkv")) {
                        Intent intent = new Intent(context, VideoActivity.class);
                        intent.putExtra("video", items.get(position).getImage());
                        context.startActivity(intent);
                    } else if (items.get(position).getImage().contains(".MKV")) {
                        Intent intent = new Intent(context, VideoActivity.class);
                        intent.putExtra("video", items.get(position).getImage());
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, ImageViewSliderActivity.class);
                        intent.putExtra("tag", "6");
                        intent.putExtra("pos", position);
                        context.startActivity(intent);
                    }
                }


            }
        });
        holder.galleryImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (items.get(position).getChecked().equals("0")) {
                    items.get(position).setChecked("1");
                } else {
                    items.get(position).setChecked("0");
                }
                try{
                    ArrayList <String> strings = new ArrayList<String>();
                    for (int i = 0; i < items.size(); i++) {
                        if (items.get(i).getChecked().equals("1")) {
                            strings.add(items.get(i).getImage());

                        }
                    }
                    if (strings.size()==0){
                        PhotosActivity.selectedSize.setText("");
                    }else {
                        PhotosActivity.selectedSize.setText(String.valueOf(strings.size()));

                    }


                }catch (Exception e){}

                notifyItemChanged(position);
               /* if (items.get(position).getChecked().equals("0")){
                    items.get(position).setChecked("1");
                }else {
                    items.get(position).setChecked("0");
                }


               notifyItemChanged(position);*/


                boolean check = true;
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getChecked().equals("1")) {

                        check = false;
                        break;
                    }
                }
                if (check == false) {
                    trigger = true;
                } else trigger = false;
                return false;
            }
        });
        PhotosActivity.btn_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showMenu(PhotosActivity.btn_bottom, PhotosActivity.strings, position);


            }
        });

    }

    public static void showMenu(final View view, final ArrayList<String> item_status, final int position) {
        final PopupMenu menu = new PopupMenu(context, view);
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
                            boolean check = true;
                            for (int i = 0; i < items.size(); i++) {
                                if (items.get(i).getChecked().equals("1")) {

                                    check = false;
                                    break;
                                }
                            }

                            if (check == false) {
                                trigger = false;
                                alertDialog(position);
                            } else {
                                trigger = true;
                                Toast.makeText(context, "Select Your Image for the tag", Toast.LENGTH_LONG).show();
                            }
                        } else if (item_status.get(j).equals("ADD TAG")) {
                            alertDialogTag();
                        } /*else if (item_status.get(j).equals("REMOVE ALBUM")) {

                            Intent intent = new Intent(context, AllTagsActivity.class);
                            context.startActivity(intent);
                        }
*/

                    }


                }
                return true;
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    public static void alertDialogTag() {
        final Dialog progress_dialog = new android.app.Dialog(context);
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
                int pos = 0;
                boolean check = true;
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getChecked().equals("1")) {

                        check = false;
                        break;
                    }
                }

                if (check == false) {
                    trigger = false;
                    if (!tag_name.getText().toString().equals("")) {
                        String[] tag_split = tag_name.getText().toString().split(" ");
                        for (int j = 0; j < tag_split.length; j++) {

                            progress_dialog.dismiss();
                            ArrayList<AddTagList> tag = new ArrayList<>();
                            ArrayList<AddTagList> addTagListstemp = new ArrayList<>();
                            addTagListstemp = db.getAllTags();
                            for (int i = 0; i < addTagListstemp.size(); i++) {
                                if (addTagListstemp.get(i).getTag().equalsIgnoreCase(tag_split[j])) {
                                    tag.add(addTagListstemp.get(i));
                                    pos = i;
                                }
                            }
                            if (tag.size() == 0) {
                                db.insertADDTAG(tag_split[j]);
                                ArrayList<String> strings = new ArrayList<>();
                                strings.clear();
                                for (int i = 0; i < items.size(); i++) {
                                    if (items.get(i).getChecked().equals("1")) {
                                        strings.add(items.get(i).getImage());
                                        db.insertTAGIMAGE(tag_split[j], items.get(i).getImage());
                                        trigger = false;
                                    }
                                }

                            } else {
                                ArrayList<String> strings = new ArrayList<>();
                                strings.clear();
                                for (int i = 0; i < items.size(); i++) {
                                    if (items.get(i).getChecked().equals("1")) {
                                        strings.add(items.get(i).getImage());
                                        db.insertTAGIMAGE(addTagListstemp.get(pos).getTag(), items.get(i).getImage());
                                        trigger = false;
                                    }
                                }
                                pos = -1;

                            }


                        }
                        Toast.makeText(context, "ADD Tag Successfully", Toast.LENGTH_LONG).show();
                        dashBoardActivity.galleryImages();
                    }

                } else {
                    trigger = true;
                    Toast.makeText(context, "Select Your Image for the tag", Toast.LENGTH_LONG).show();
                }


            }
        });
        progress_dialog.show();
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView galleryImage;
        private ImageView checkbox;
        private ImageView videoIcon;
        private LinearLayout selectedLayout;


        public ItemViewHolder(View itemView) {
            super(itemView);
            galleryImage = (ImageView) itemView.findViewById(R.id.galleryImage);
            checkbox = (ImageView) itemView.findViewById(R.id.checkbox);
            videoIcon = (ImageView) itemView.findViewById(R.id.videoIcon);
            selectedLayout = (LinearLayout) itemView.findViewById(R.id.selectedLayout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           /* PhotosActivity.refresh = false;
            Intent intent = new Intent(context, VideoActivity.class);
            intent.putExtra("video", items.get(getPosition()).getImage());
            context.startActivity(intent);*/

        }
    }

    public static void alertDialog(int position) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_tag_image_dialog, null);
        final RegularTextView txt_message = (RegularTextView) view.findViewById(R.id.tag);
        RegularButton btnOk = (RegularButton) view.findViewById(R.id.btnOk);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_message.getText().toString().equals("")) {

                } else {
                    ArrayList<AddTagList> tag = new ArrayList<>();
                    ArrayList<AddTagList> addTagListstemp = new ArrayList<>();
                    addTagListstemp = db.getAllTags();
                    for (int i = 0; i < addTagListstemp.size(); i++) {
                        if (addTagListstemp.get(i).getHide().equals("unhide")) {
                            tag.add(addTagListstemp.get(i));
                        }
                    }

                    if (!(tag.size() == 0)) {
                        dialog.dismiss();
                        ArrayList<String> strings = new ArrayList<>();
                        strings.clear();
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).getChecked().equals("1")) {
                                strings.add(items.get(i).getImage());
                                db.insertTAGIMAGE(tag_Name, items.get(i).getImage());
                                trigger = false;
                            }
                        }
                        dashBoardActivity.galleryImages();
                        if (strings.size() == 0) {

                        } else {
                            // db.insertTAGIMAGE()
                        }
                        DashBoardActivity.bottom_sheet.setVisibility(View.GONE);
                    } else Toast.makeText(context, "First Add your Tag", Toast.LENGTH_LONG).show();
                }


            }
        });
        txt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<AddTagList> strings = new ArrayList<>();
                strings = db.getAllTags();
                if (strings.size() == 0) {
                    Toast.makeText(context, "First Add your Tag", Toast.LENGTH_LONG).show();
                } else
                    showTag(txt_message, strings);
            }
        });
    }

    public static void showTag(final View view, final ArrayList<AddTagList> item_status) {
        final PopupMenu menu = new PopupMenu(context, view);
        for (int i = 0; i < item_status.size(); i++) {
            menu.getMenu().add(item_status.get(i).getTag());
        }
        menu.show();
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((TextView) view).setText(item.getTitle());
                for (int j = 0; j < item_status.size(); j++) {
                    if (item_status.get(j).getTag().equals(item.getTitle())) {
                        ((TextView) view).setTag(item_status.get(j).getTag());

                        tag_Name = view.getTag() != null ? view.getTag().toString() : "";
                        // new ServiceDetailAsync().execute(edt_status.getTag() != null ? edt_status.getTag().toString() : "");
                    }


                }
                return true;
            }
        });
    }

}

