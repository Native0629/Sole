package com.nav.tagger.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.mukesh.permissions.AppPermissions;
import com.nav.tagger.R;
import com.nav.tagger.Utils.AppUtills;
import com.nav.tagger.view.RegularTextView;
import java.util.ArrayList;
import java.util.List;


public class SplashActivity extends AppCompatActivity {
    private AppPermissions mRuntimePermission;
    private static final int ALL_REQUEST_CODE = 0;
    private String currentVersion = "";
    private RegularTextView versionText ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mRuntimePermission = new AppPermissions();
        init();
    }

    private void init() {
        versionText = (RegularTextView) findViewById(R.id.versionText);
        try{
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            versionText.setText("Version : " + currentVersion);
        }catch (Exception e){
            versionText.setText("Version : 1.0");
        }
        if (mRuntimePermission.hasPermission(this, AppUtills.ALL_PERMISSIONS)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                    startActivity(intent);
                    finish();

                }
            }, 2000);
        } else {
            mRuntimePermission.requestPermission(this, AppUtills.ALL_PERMISSIONS, ALL_REQUEST_CODE);
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

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);

                break;
        }
    }


}



