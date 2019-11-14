package com.nav.tagger.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nav.tagger.Activity.AllTagsActivity;
import com.nav.tagger.Activity.DashBoardActivity;
import com.nav.tagger.Activity.ImageViewSliderActivity;
import com.nav.tagger.Activity.SearchTagImageActivity;
import com.nav.tagger.Model.AddTagList;
import com.nav.tagger.Model.TagImageList;
import com.nav.tagger.PhotosActivity;
import com.nav.tagger.R;
import com.nav.tagger.Utils.DatabaseHelper;
import com.nav.tagger.Utils.SessionManager;
import com.nav.tagger.view.BoldTextView;
import com.nav.tagger.view.RegularButton;
import com.nav.tagger.view.RegularEditText;
import com.nav.tagger.view.RegularTextView;

import java.util.ArrayList;

/**
 * Created by navitgupta on 10/06/18.
 */

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ItemViewHolder> {

    private Context context;
    public static ArrayList<AddTagList> items;
    public static DatabaseHelper db;
    public static String hideTag =  "unhide";
    public static int pos = 0;
    public static Dialog passwordprogress_dialog;


    public TagAdapter(Context context, ArrayList<AddTagList> items) {
        this.context = context;
        this.items = items;
        db = new DatabaseHelper(context);
    }


    @Override
    public TagAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_row_item, parent, false);
        return new TagAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final TagAdapter.ItemViewHolder holder, final int position) {

        if (hideTag.equals("unhide")){
            holder.tagLayout.setVisibility(View.VISIBLE);
            holder.hideIcon.setVisibility(View.GONE);
            holder.unhideIcon.setVisibility(View.VISIBLE);
            holder.tagName.setText(items.get(position).getTag());
        }else  {
            holder.tagLayout.setVisibility(View.VISIBLE);
            holder.hideIcon.setVisibility(View.VISIBLE);
            holder.unhideIcon.setVisibility(View.GONE);
            holder.tagName.setText(items.get(position).getTag());
        }
        holder.edit.setVisibility(View.GONE);
        holder.delete.setVisibility(View.GONE);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogTagEdit(items.get(position).getTag(), position);
            }
        });
        holder.hideIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogunHide(items.get(position).getTag(),position);
            }
        });
        holder.unhideIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogHide(items.get(position).getTag(),position);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialogDelete(items.get(position).getTag(), position);
            }
        });
        if (AllTagsActivity.clickpos==position){
            holder.checkImage.setVisibility(View.GONE);
            holder.uncheckImage.setVisibility(View.VISIBLE);
        }else {
            holder.checkImage.setVisibility(View.VISIBLE);
            holder.uncheckImage.setVisibility(View.GONE);
        }
        holder.checkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllTagsActivity.clickpos = position;
                if (holder.checkImage.getVisibility()== View.VISIBLE){
                    holder.checkImage.setVisibility(View.GONE);
                    holder.uncheckImage.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                }
            }
        });

/*
        holder.uncheckImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // clickpos = position;
                if (holder.uncheckImage.getVisibility()== View.VISIBLE){
                    holder.uncheckImage.setVisibility(View.GONE);
                    holder.checkImage.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                }
            }
        });
*/
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RegularTextView tagName;
        private ImageView edit;
        private ImageView checkImage;
        private ImageView uncheckImage;
        private ImageView hideIcon;
        private ImageView unhideIcon;
        private LinearLayout tagLayout;
        private ImageView delete;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tagName = (RegularTextView) itemView.findViewById(R.id.tagName);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            checkImage = (ImageView) itemView.findViewById(R.id.checkImage);
            uncheckImage = (ImageView) itemView.findViewById(R.id.uncheckImage);
            tagLayout = (LinearLayout) itemView.findViewById(R.id.tagLayout);
            unhideIcon = (ImageView) itemView.findViewById(R.id.unhideIcon);
            hideIcon = (ImageView) itemView.findViewById(R.id.hideIcon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, SearchTagImageActivity.class);
            intent.putExtra("tag","2");
            intent.putExtra("tag_name",items.get(getPosition()).getTag());
            context.startActivity(intent);
        }
    }

    @SuppressLint("ResourceAsColor")
    public void alertDialogunHide(final String tag, final int position) {
        final Dialog progress_dialog = new android.app.Dialog(context);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setContentView(R.layout.delete_dialog);
        final Window window = progress_dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(R.color.black_transparent));
        RegularButton delete = (RegularButton) progress_dialog.findViewById(R.id.delete);
        RegularButton cancel_action = (RegularButton) progress_dialog.findViewById(R.id.cancel_action);
        final RegularTextView tag_name = (RegularTextView) progress_dialog.findViewById(R.id.tag_name);
        final BoldTextView expName = (BoldTextView) progress_dialog.findViewById(R.id.expName);
        final RegularTextView tag_nameName = (RegularTextView) progress_dialog.findViewById(R.id.tag_nameName);
        tag_nameName.setText(tag + "?");
        tag_name.setText("Do you want to to UnHide ");
        delete.setText("UnHide");
        expName.setText("");
        //expName.setText("UnHide your Tag");
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_dialog.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tag_name.getText().toString().equals("")) {
                    progress_dialog.dismiss();
                    items.get(position).setHide("unhide");
                    db.updateTAGHIDE(items.get(position));
                    AllTagsActivity.initializeRecyclerHIDEUNHIDE();

                }

            }
        });
        progress_dialog.show();
    }

    @SuppressLint("ResourceAsColor")
    public void alertDialogHide(final String tag, final int position) {
        final Dialog progress_dialog = new android.app.Dialog(context);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setContentView(R.layout.delete_dialog);
        final Window window = progress_dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(R.color.black_transparent));
        RegularButton delete = (RegularButton) progress_dialog.findViewById(R.id.delete);
        RegularButton cancel_action = (RegularButton) progress_dialog.findViewById(R.id.cancel_action);
        final RegularTextView tag_name = (RegularTextView) progress_dialog.findViewById(R.id.tag_name);
        final BoldTextView expName = (BoldTextView) progress_dialog.findViewById(R.id.expName);
        expName.setText("");
        final RegularTextView tag_nameName = (RegularTextView) progress_dialog.findViewById(R.id.tag_nameName);
        tag_nameName.setText(tag + "?");
        tag_name.setText("Do you want to Hide ");
      //  expName.setText("Hide your Tag");
        delete.setText("Hide");
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_dialog.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tag_name.getText().toString().equals("")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        AllTagsActivity.fingerPassword = "1";
                        alertDialogPassword(position);
                        pos = position;
                        AllTagsActivity.dialogFinger.show();
                        AllTagsActivity.mFingerPrintAuthHelper.startAuth();
                    } else {
                        alertDialogPassword(position);
                    }

                  //  alertDialogPassword(position);
                    progress_dialog.dismiss();


                }

            }
        });
        progress_dialog.show();
    }

    @SuppressLint("ResourceAsColor")
    public  void alertDialogPassword(final int position) {
         passwordprogress_dialog = new android.app.Dialog(context);
        passwordprogress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        passwordprogress_dialog.setContentView(R.layout.password_dialog);
        final Window window = passwordprogress_dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(R.color.black_transparent));
        RegularButton save = (RegularButton) passwordprogress_dialog.findViewById(R.id.save);
        RegularButton cancel_action = (RegularButton) passwordprogress_dialog.findViewById(R.id.cancel_action);
        final RegularEditText tag_name = (RegularEditText) passwordprogress_dialog.findViewById(R.id.tag_name);


        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordprogress_dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tag_name.getText().toString().equals(SessionManager.getPassword(context))) {
                    passwordprogress_dialog.dismiss();
                    items.get(position).setHide("hide");
                    db.updateTAGHIDE(items.get(position));
                    AllTagsActivity.initializeRecyclerHIDEUNHIDE();

                }else  Toast.makeText(context,"Old Password is wrong",Toast.LENGTH_LONG).show();


            }
        });
        passwordprogress_dialog.show();
    }


    @SuppressLint("ResourceAsColor")
    public void alertDialogDelete(final String tag, final int position) {
        final Dialog progress_dialog = new android.app.Dialog(context);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setContentView(R.layout.delete_dialog);
        final Window window = progress_dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(R.color.black_transparent));
        RegularButton delete = (RegularButton) progress_dialog.findViewById(R.id.delete);
        RegularButton cancel_action = (RegularButton) progress_dialog.findViewById(R.id.cancel_action);
        final RegularTextView tag_name = (RegularTextView) progress_dialog.findViewById(R.id.tag_name);
        final RegularTextView tag_nameName = (RegularTextView) progress_dialog.findViewById(R.id.tag_nameName);
        tag_nameName.setText(tag + "?");
        tag_name.setText("Do you want to delete" );

        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_dialog.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTagList tagList = items.get(position);

                ArrayList<TagImageList> tagImageList = new ArrayList<>();
                tagImageList = db.getAllTagsImages();
                for (int i = 0; i < tagImageList.size(); i++) {
                    if (tagImageList.get(i).getTag().equals(tag)) {
                        db.deleteTAGIMAGENOTE(tagImageList.get(i));
                    }
                }
                db.deleteNote(tagList);
                AllTagsActivity.initializeRecycler();
                progress_dialog.dismiss();
               // DashBoardActivity.ge();

            }
        });
        progress_dialog.show();
    }

    @SuppressLint("ResourceAsColor")
    public static void alertDialogTagEdit(String tag, final int position) {
        final Dialog progress_dialog = new android.app.Dialog(AllTagsActivity.allTagsActivity);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setContentView(R.layout.add_tag_dialog);
        final Window window = progress_dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(R.color.black_transparent));
        RegularButton save = (RegularButton) progress_dialog.findViewById(R.id.save);
        BoldTextView expName = (BoldTextView) progress_dialog.findViewById(R.id.expName);
        RegularButton cancel_action = (RegularButton) progress_dialog.findViewById(R.id.cancel_action);
        final RegularEditText tag_name = (RegularEditText) progress_dialog.findViewById(R.id.tag_name);
        expName.setText("Edit your Tag");
        tag_name.setText(tag);

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
                    items.get(position).setTag(tag_name.getText().toString());
                    db.updateTAG(items.get(position));
                    AllTagsActivity.initializeRecycler();

                }


            }
        });
        progress_dialog.show();
    }


}
