package com.meembusoft.ln.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meembusoft.ln.R;
import com.meembusoft.ln.base.BaseActivity;
import com.meembusoft.ln.util.AppUtil;
import com.meembusoft.ln.util.OnSingleClickListener;

public class DownloadAppActivity extends BaseActivity {

    // Toolbar
    private TextView tvTitle;
    private LinearLayout llClose;

    // Views elements
    private ImageView ivQRCode;
    private TextView tvPlayStoreUrl;

    @Override
    public int initToolbarLayout() {
        return R.layout.toolbar_base;
    }

    @Override
    public int initScreenLayout() {
        return R.layout.activity_download_app;
    }

    @Override
    public void initIntentData(Bundle savedInstanceState, Intent intent) {

    }

    @Override
    public void initViews() {
        // Toolbar
        tvTitle = findViewById(R.id.tv_title);
        llClose = findViewById(R.id.ll_close);

        ivQRCode = (ImageView) findViewById(R.id.iv_qr_code);
        tvPlayStoreUrl = (TextView) findViewById(R.id.tv_play_store_url);
    }

    @Override
    public void initViewsData(Bundle savedInstanceState) {
        // Toolbar
        tvTitle.setText(R.string.txt_download_app);
        //For marquee address
        AppUtil.applyMarqueeOnTextView(tvTitle);

        setQRCode();
    }

    @Override
    public void initActions(Bundle savedInstanceState) {
        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBackPress();
            }
        });

        tvPlayStoreUrl.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
//                AboutBoxUtils.openApp(getActivity(), AboutConfig.BuildType.GOOGLE, packageName);
            }
        });
    }

    @Override
    public void initActivityOnResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void initBackPress() {
        finish();
    }

    @Override
    public void initDestroyTasks() {

    }

    @Override
    public void onAllPermissionsAccepted() {

    }

    private void setQRCode() {
        String playStoreUrl = AppUtil.getPlayStoreUrl(getActivity());
        AppUtil.generateQRCode(playStoreUrl, ivQRCode, 250, 250);

        tvPlayStoreUrl.setText(playStoreUrl);
        tvPlayStoreUrl.setPaintFlags(tvPlayStoreUrl.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
}