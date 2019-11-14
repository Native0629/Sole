package com.nav.tagger.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;


import com.afollestad.materialdialogs.MaterialDialog;

import com.nav.tagger.Activity.DashBoardActivity;
import com.nav.tagger.R;
import com.nav.tagger.Utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by obadmin on 19/2/18.
 */

public class KeyboardActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_pin_uncheck1;
    private ImageView img_pin_uncheck2;
    private ImageView img_pin_uncheck3;
    private ImageView img_pin_uncheck4;

    private ImageView img_pin_check1;
    private ImageView img_pin_check2;
    private ImageView img_pin_check3;
    private ImageView img_pin_check4;

    private LinearLayout ll_keyped_key1;
    private LinearLayout ll_keyped_key2;
    private LinearLayout ll_keyped_key3;
    private LinearLayout ll_keyped_key4;
    private LinearLayout ll_keyped_key5;
    private LinearLayout ll_keyped_key6;
    private LinearLayout ll_keyped_key7;
    private LinearLayout ll_keyped_key8;
    private LinearLayout ll_keyped_key9;
    private LinearLayout ll_keyped_key0;
    private LinearLayout ll_img_cancil;

    private ArrayList<String> key;
    private RegularTextView setPinText;
    private RegularTextView changePin;
    ArrayList<String> forgotPin;
    private String cpin = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        init();
    }

    private void init() {
        key = new ArrayList<>();
        forgotPin = new ArrayList<>();

        changePin = (RegularTextView) findViewById(R.id.changePin);
        setPinText = (RegularTextView) findViewById(R.id.setPinText);
        img_pin_uncheck1 = (ImageView) findViewById(R.id.img_pin_uncheck1);
        img_pin_uncheck2 = (ImageView) findViewById(R.id.img_pin_uncheck2);
        img_pin_uncheck3 = (ImageView) findViewById(R.id.img_pin_uncheck3);
        img_pin_uncheck4 = (ImageView) findViewById(R.id.img_pin_uncheck4);

        img_pin_check1 = (ImageView) findViewById(R.id.img_pin_check1);
        img_pin_check2 = (ImageView) findViewById(R.id.img_pin_check2);
        img_pin_check3 = (ImageView) findViewById(R.id.img_pin_check3);
        img_pin_check4 = (ImageView) findViewById(R.id.img_pin_check4);

        ll_keyped_key1 = (LinearLayout) findViewById(R.id.ll_keyped_key1);
        ll_keyped_key1.setOnClickListener(this);
        ll_keyped_key2 = (LinearLayout) findViewById(R.id.ll_keyped_key2);
        ll_keyped_key2.setOnClickListener(this);
        ll_keyped_key3 = (LinearLayout) findViewById(R.id.ll_keyped_key3);
        ll_keyped_key3.setOnClickListener(this);
        ll_keyped_key4 = (LinearLayout) findViewById(R.id.ll_keyped_key4);
        ll_keyped_key4.setOnClickListener(this);
        ll_keyped_key5 = (LinearLayout) findViewById(R.id.ll_keyped_key5);
        ll_keyped_key5.setOnClickListener(this);
        ll_keyped_key6 = (LinearLayout) findViewById(R.id.ll_keyped_key6);
        ll_keyped_key6.setOnClickListener(this);
        ll_keyped_key7 = (LinearLayout) findViewById(R.id.ll_keyped_key7);
        ll_keyped_key7.setOnClickListener(this);
        ll_keyped_key8 = (LinearLayout) findViewById(R.id.ll_keyped_key8);
        ll_keyped_key8.setOnClickListener(this);
        ll_keyped_key9 = (LinearLayout) findViewById(R.id.ll_keyped_key9);
        ll_keyped_key9.setOnClickListener(this);
        ll_keyped_key0 = (LinearLayout) findViewById(R.id.ll_keyped_key0);
        ll_keyped_key0.setOnClickListener(this);
        ll_img_cancil = (LinearLayout) findViewById(R.id.ll_img_cancil);
        ll_img_cancil.setOnClickListener(this);
        boolean wrapInScrollView = true;

        if(SessionManager.getPassword(KeyboardActivity.this).equals("")){
            setPinText.setText("Set Pin");
            changePin.setVisibility(View.GONE);
        }else {
            setPinText.setText("Enter Pin");
            changePin.setVisibility(View.VISIBLE);
        }


        changePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if (img_pin_check4.getVisibility() == View.VISIBLE) {
                    img_pin_check4.setVisibility(View.GONE);
                    img_pin_uncheck4.setVisibility(View.VISIBLE);
               // } else if (img_pin_check3.getVisibility() == View.VISIBLE) {
                    img_pin_check3.setVisibility(View.GONE);
                    img_pin_uncheck3.setVisibility(View.VISIBLE);
                //} else if (img_pin_check2.getVisibility() == View.VISIBLE) {
                    img_pin_check2.setVisibility(View.GONE);
                    img_pin_uncheck2.setVisibility(View.VISIBLE);
               // } else if (img_pin_check1.getVisibility() == View.VISIBLE) {
                    img_pin_check1.setVisibility(View.GONE);
                    img_pin_uncheck1.setVisibility(View.VISIBLE);
               // }

                try {
                    key.remove(key.size() - 1);
                } catch (Exception e) {

                }
                setPinText.setText("Change Pin");
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_img_cancil:
                if (img_pin_check4.getVisibility() == View.VISIBLE) {
                    img_pin_check4.setVisibility(View.GONE);
                    img_pin_uncheck4.setVisibility(View.VISIBLE);
                } else if (img_pin_check3.getVisibility() == View.VISIBLE) {
                    img_pin_check3.setVisibility(View.GONE);
                    img_pin_uncheck3.setVisibility(View.VISIBLE);
                } else if (img_pin_check2.getVisibility() == View.VISIBLE) {
                    img_pin_check2.setVisibility(View.GONE);
                    img_pin_uncheck2.setVisibility(View.VISIBLE);
                } else if (img_pin_check1.getVisibility() == View.VISIBLE) {
                    img_pin_check1.setVisibility(View.GONE);
                    img_pin_uncheck1.setVisibility(View.VISIBLE);
                }

                try {
                    key.remove(key.size() - 1);
                } catch (Exception e) {

                }

                break;

            case R.id.ll_keyped_key1:
                if (key.size() <= 3) {
                    key.add("1");
                } else {

                }
                visiblityValue();


                break;

            case R.id.ll_keyped_key2:

                if (key.size() <= 3) {
                    key.add("2");
                } else {

                }
                visiblityValue();

                break;
            case R.id.ll_keyped_key3:

                if (key.size() <= 3) {
                    key.add("3");
                } else {

                }
                visiblityValue();


                break;

            case R.id.ll_keyped_key4:

                if (key.size() <= 3) {
                    key.add("4");
                } else {

                }
                visiblityValue();


                break;

            case R.id.ll_keyped_key5:
                if (key.size() <= 3) {
                    key.add("5");
                } else {

                }
                visiblityValue();


                break;

            case R.id.ll_keyped_key6:

                if (key.size() <= 3) {
                    key.add("6");
                } else {

                }
                visiblityValue();


                break;

            case R.id.ll_keyped_key7:

                if (key.size() <= 3) {
                    key.add("7");
                } else {

                }
                visiblityValue();


                break;

            case R.id.ll_keyped_key8:

                if (key.size() <= 3) {
                    key.add("8");
                } else {

                }
                visiblityValue();


                break;

            case R.id.ll_keyped_key9:
                if (key.size() <= 3) {
                    key.add("9");
                } else {

                }
                visiblityValue();


                break;

            case R.id.ll_keyped_key0:

                if (key.size() <= 3) {
                    key.add("0");
                } else {

                }
                visiblityValue();

                break;
        }

    }


    public void Progressdialog() {


        System.out.println(key.size());
        StringBuffer numberZero = new StringBuffer();
        for (int i = 0; i < key.size(); i++) {
            numberZero.append(key.get(i));
        }
        if(setPinText.getText().toString().equals("Set Pin")){
            SessionManager.setPassword(KeyboardActivity.this,numberZero.toString());
            Toast.makeText(KeyboardActivity.this,"Pin set Successfully",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(KeyboardActivity.this, DashBoardActivity.class);
            startActivity(intent);
            finish();
        }else if (setPinText.getText().toString().equals("Enter Pin")){
            cpin  = SessionManager.getPassword(KeyboardActivity.this);
            if (cpin.equals(numberZero.toString())) {
                Intent intent = new Intent(KeyboardActivity.this, DashBoardActivity.class);
                startActivity(intent);
                finish();
                // new setPinAsync().execute(numberZero.toString());
            } else {
                Toast.makeText(KeyboardActivity.this,"Wrong Pin",Toast.LENGTH_LONG).show();

            }
        }else if (setPinText.getText().toString().equals("Change Pin")){
            SessionManager.setPassword(KeyboardActivity.this,numberZero.toString());
            Toast.makeText(KeyboardActivity.this,"Pin change Successfully",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(KeyboardActivity.this, DashBoardActivity.class);
            startActivity(intent);
            finish();
        }




    }

    private void visiblityValue() {
        if (img_pin_uncheck1.getVisibility() == View.VISIBLE) {
            img_pin_check1.setVisibility(View.VISIBLE);
            img_pin_uncheck1.setVisibility(View.GONE);
        } else if (img_pin_uncheck2.getVisibility() == View.VISIBLE) {
            img_pin_check2.setVisibility(View.VISIBLE);
            img_pin_uncheck2.setVisibility(View.GONE);
        } else if (img_pin_uncheck3.getVisibility() == View.VISIBLE) {
            img_pin_check3.setVisibility(View.VISIBLE);
            img_pin_uncheck3.setVisibility(View.GONE);
        } else if (img_pin_uncheck4.getVisibility() == View.VISIBLE) {
            img_pin_check4.setVisibility(View.VISIBLE);
            img_pin_uncheck4.setVisibility(View.GONE);
            Progressdialog();
        }
    }


}
