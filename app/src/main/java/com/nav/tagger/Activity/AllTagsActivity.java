package com.nav.tagger.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.ColorDrawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.afollestad.materialdialogs.MaterialDialog;
import com.multidots.fingerprintauth.AuthErrorCodes;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;
import com.multidots.fingerprintauth.FingerPrintUtils;
import com.nav.tagger.Model.AddTagList;
import com.nav.tagger.Model.TagImageList;
import com.nav.tagger.R;
import com.nav.tagger.Utils.DatabaseHelper;
import com.nav.tagger.Utils.SessionManager;
import com.nav.tagger.adapter.DrawerAdapter;
import com.nav.tagger.adapter.TagAdapter;
import com.nav.tagger.view.BoldTextView;
import com.nav.tagger.view.RegularButton;
import com.nav.tagger.view.RegularEditText;
import com.nav.tagger.view.RegularTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by navitgupta on 10/06/18.
 */

public class AllTagsActivity extends AppCompatActivity implements FingerPrintAuthCallback {

    private FloatingActionButton addTag;
    public static DatabaseHelper db;
    public static RecyclerView tagList;
    public static ArrayList<AddTagList> addTagLists;
    public static ArrayList<AddTagList> addTagListstemp;
    public static TagAdapter adapter;
    public static AllTagsActivity allTagsActivity;
    private RegularTextView back;
    private ImageView menuSet;
    public static RegularTextView noTag;
    public static RegularTextView title;
    private String hideTag = "";
    public static  int clickpos = 0;
    private TextView mAuthMsgTv;
    private TextView txt_cancil;
    private Button mGoToSettingsBtn;
    public static MaterialDialog dialogFinger;
    public static FingerPrintAuthHelper mFingerPrintAuthHelper;
    private ViewSwitcher mSwitcher;
    public static String fingerPassword ="0";
    private Dialog progress_dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_tag_list);
        allTagsActivity = this;
        clickpos = 0;
        init();
    }

    private void init() {
        addTagLists = new ArrayList<>();
        addTagListstemp = new ArrayList<>();
        tagList = (RecyclerView) findViewById(R.id.tagList);
        menuSet = findViewById(R.id.menuSet);
        back = findViewById(R.id.back);
        noTag = findViewById(R.id.noTag);
        title = findViewById(R.id.title);
        db = new DatabaseHelper(this);
        addTag = (FloatingActionButton) findViewById(R.id.addTag);
        menuSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> strings = new ArrayList<String>();
                strings.add("Show Hidden Tags");
                strings.add("Hide Tags");
                strings.add("Edit");
                strings.add("Delete");
                showMenu(menuSet, strings);
            }
        });
        initializeRecycler();
        addTag.setVisibility(View.GONE);
        addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogTag();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            boolean wrapInScrollView = true;

            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.dialog_finger_setting, null);
/*        RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);*/
            mAuthMsgTv = (TextView) view.findViewById(R.id.auth_message_tv);
            txt_cancil = (TextView) view.findViewById(R.id.txt_cancil);

            mSwitcher = (ViewSwitcher) view.findViewById(R.id.main_switcher);
            mGoToSettingsBtn = (Button) view.findViewById(R.id.go_to_settings_btn);
            MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                    .customView(view, wrapInScrollView);
            dialogFinger = builder.build();
            dialogFinger.setCancelable(false);
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mGoToSettingsBtn.setVisibility(View.VISIBLE);
                    mAuthMsgTv.setText("Scan your finger");
                    mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, this);
                } else {
                    mGoToSettingsBtn.setVisibility(View.GONE);
                    // Toast.makeText(this,"Your device does not support finger print scanner" ,Toast.LENGTH_SHORT).show();
                    mAuthMsgTv.setText("Your device does not support finger print scanner");
                }

            } catch (Exception e) {

            }
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(AllTagsActivity.this, this);
                } else {
                }

            } catch (Exception e) {

            }


            txt_cancil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogFinger.dismiss();
                }
            });
            mGoToSettingsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogFinger.dismiss();
                    FingerPrintUtils.openSecuritySettings(AllTagsActivity.this);
                }
            });

        }
    }

    @SuppressLint("ResourceAsColor")
    public  void alertDialogPassword() {
        progress_dialog = new android.app.Dialog(allTagsActivity);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setContentView(R.layout.password_dialog);
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

                if (tag_name.getText().toString().equals(SessionManager.getPassword(AllTagsActivity.this))) {
                    progress_dialog.dismiss();
                    try {
                        TagAdapter.hideTag = "hide";
                        title.setText("Hidden Tags");
                        initializeRecyclerHIDEUNHIDE();
                    }catch (Exception e){

                    }


                }else  Toast.makeText(allTagsActivity,"Old Password is wrong",Toast.LENGTH_LONG).show();


            }
        });
        progress_dialog.show();
    }


    @SuppressLint("ResourceAsColor")
    public static void alertDialogTag() {
        final Dialog progress_dialog = new android.app.Dialog(allTagsActivity);
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
                    initializeRecycler();

                }


            }
        });
        progress_dialog.show();
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
        if (addTagListstemp.size() == 0) {
            noTag.setVisibility(View.VISIBLE);
            tagList.setVisibility(View.GONE);
        } else {
            noTag.setVisibility(View.GONE);
            tagList.setVisibility(View.VISIBLE);
           Collections.sort(addTagListstemp);
            adapter = new TagAdapter(allTagsActivity, addTagListstemp);
            tagList.setAdapter(adapter);
        }


    }
    public static void initializeRecyclerHIDEUNHIDE() {
        addTagLists.clear();
        addTagListstemp.clear();
        addTagLists = db.getAllTags();
        tagList.setLayoutManager(new LinearLayoutManager(allTagsActivity));
        tagList.setItemAnimator(new DefaultItemAnimator());
        for (int i = 0; i < addTagLists.size(); i++) {
            if (addTagLists.get(i).getHide().equals(TagAdapter.hideTag)){
                addTagListstemp.add(addTagLists.get(i));
            }
        }
        if (addTagListstemp.size() == 0) {
            noTag.setVisibility(View.VISIBLE);
            tagList.setVisibility(View.GONE);
        } else {
            noTag.setVisibility(View.GONE);
            tagList.setVisibility(View.VISIBLE);
            Collections.sort(addTagListstemp);
            adapter = new TagAdapter(allTagsActivity, addTagListstemp);
            tagList.setAdapter(adapter);
        }


    }



    private void showMenu(final View view, final ArrayList<String> item_status) {
        final PopupMenu menu = new PopupMenu(AllTagsActivity.this, view);
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
                        if (item_status.get(j).equals("Hide Tags")) {
                            try{

                                try {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        alertDialogPassword();
                                        AllTagsActivity.fingerPassword = "0";
                                        mFingerPrintAuthHelper.startAuth();
                                        dialogFinger.show();

                                    } else {
                                        alertDialogPassword();
                                    }

                                } catch (Exception e) {

                                }

                                /**/

                            }catch (Exception e){}

                        } else if (item_status.get(j).equals("Show Hidden Tags")) {
                            try {
                                TagAdapter.hideTag = "unhide";
                                //title.setText("All UnHide Tags");
                                initializeRecyclerHIDEUNHIDE();
                            }catch (Exception e){

                            }
                        }else  if (item_status.get(j).equals("Edit")){
                            try{

                                alertDialogTagEdit(addTagListstemp.get(clickpos).getTag(),clickpos);
                            }catch (Exception e){}
                        }else if (item_status.get(j).equals("Delete")){
                            try{
                            alertDialogDelete(addTagListstemp.get(clickpos).getTag(),clickpos);
                            }catch (Exception e){}
                        }
                    }


                }
                return true;
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    public void alertDialogDelete(final String tag, final int position) {
        final Dialog progress_dialog = new android.app.Dialog(allTagsActivity);
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
                AddTagList tagList = addTagListstemp.get(position);

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
                    addTagListstemp.get(position).setTag(tag_name.getText().toString());
                    db.updateTAG(addTagListstemp.get(position));
                    AllTagsActivity.initializeRecycler();

                }


            }
        });
        progress_dialog.show();
    }

    private void alertDialog() {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(AllTagsActivity.this);
        View view = inflater.inflate(R.layout.add_tag_image_dialog, null);
        final RegularTextView hide = (RegularTextView) view.findViewById(R.id.hide);
        final RegularTextView txt_message = (RegularTextView) view.findViewById(R.id.tag);
        RegularButton btnOk = (RegularButton) view.findViewById(R.id.btnOk);
        hide.setText("Hide your Tag");
        MaterialDialog.Builder builder = new MaterialDialog.Builder(AllTagsActivity.this)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hideTag.equals("")) {
                    ArrayList<AddTagList> strings = new ArrayList<>();
                    strings = db.getAllTags();
                    if (strings.size() == 0) {

                    } else {
                        for (int i = 0; i < strings.size(); i++) {
                            if (strings.get(i).getTag().equals(hideTag)) {
                                strings.get(i).setTag("1");
                                //   db.updateTAGHide(strings.get(i));
                                ArrayList<AddTagList> stringss = new ArrayList<>();
                                stringss = db.getAllTags();
                                adapter = new TagAdapter(allTagsActivity, stringss);
                                Collections.sort(stringss);
                                adapter.notifyDataSetChanged();
                                hideTag = "";
                                break;
                            }

                        }
                    }

                }

            }
        });
        txt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<AddTagList> strings = new ArrayList<>();
                strings = db.getAllTags();

                showTag(txt_message, strings);
            }
        });
    }

    private void showTag(final View view, final ArrayList<AddTagList> item_status) {
        final PopupMenu menu = new PopupMenu(AllTagsActivity.this, view);
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
                        hideTag = view.getTag() != null ? view.getTag().toString() : "";

                        // new ServiceDetailAsync().execute(edt_status.getTag() != null ? edt_status.getTag().toString() : "");
                    }


                }
                return true;
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mFingerPrintAuthHelper.stopAuth();
        }
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        mAuthMsgTv.setText("Your device does not have finger print scanner. Please type pim to authenticate.");
        mSwitcher.showNext();
    }

    @Override
    public void onNoFingerPrintRegistered() {
        mAuthMsgTv.setText("There are no finger prints registered on this device. Please register your finger from settings.");
        mGoToSettingsBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBelowMarshmallow() {
        mAuthMsgTv.setText("You are running older version of android that does not support finger print authentication. Please type 1234 to authenticate.");
        mSwitcher.showNext();
    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        // Toast.makeText(MainActivity.this, "Authentication succeeded.", Toast.LENGTH_SHORT).show();
        try{
            progress_dialog.dismiss();
        }catch (Exception e){}
        try{
            AllTagsActivity.dialogFinger.dismiss();
            AllTagsActivity.mFingerPrintAuthHelper.stopAuth();
        }catch (Exception e){}
        try{
            TagAdapter.passwordprogress_dialog.dismiss();
        }catch (Exception e){}
        if (fingerPassword.equals("0")){
            try {
                TagAdapter.hideTag = "hide";
                title.setText("Hidden Tags");
                initializeRecyclerHIDEUNHIDE();
            }catch (Exception e){

            }

        }else {
            TagAdapter.items.get(TagAdapter.pos).setHide("hide");
            db.updateTAGHIDE(TagAdapter.items.get(TagAdapter.pos));
            initializeRecyclerHIDEUNHIDE();
        }
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        switch (errorCode) {
            case AuthErrorCodes.CANNOT_RECOGNIZE_ERROR:
                mAuthMsgTv.setText("Cannot recognize your finger print. Please try again.");
                break;
            case AuthErrorCodes.NON_RECOVERABLE_ERROR:
                mAuthMsgTv.setText("Cannot initialize finger print authentication. Please use pin to authenticate.");
                mSwitcher.showNext();
                break;
            case AuthErrorCodes.RECOVERABLE_ERROR:
                mAuthMsgTv.setText(errorMessage);
                break;
        }
    }
}
