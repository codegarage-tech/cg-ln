package com.reversecoder.attributionpresenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.reversecoder.attributionpresenter.R;
import com.reversecoder.attributionpresenter.model.Attribution;
import com.reversecoder.attributionpresenter.model.LicenseInfo;

import static com.reversecoder.attributionpresenter.util.Constants.INTENT_KEY_ATTRIBUTION;

public class LicenseDetailActivity extends AppCompatActivity {

    Attribution mAttribution;

    // Toolbar
    private TextView tvTitle;
    private LinearLayout llClose;

    // License info
//    TextView tvLicenseName, tvLicenseCopyright, tvLicenseDetail;
    LinearLayout llLicenseDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_detail);

        initView();
    }

    private void initView() {

        Intent intentData = getIntent();
        mAttribution = intentData.getParcelableExtra(INTENT_KEY_ATTRIBUTION);
        Log.d("mAttribution", mAttribution.toString());

        initToolBar();

        llLicenseDetail = (LinearLayout) findViewById(R.id.ll_license_detail);

        if (mAttribution != null) {
            showLicenseDetailView();
        }
    }

    private void initToolBar() {
        // Toolbar
        tvTitle = findViewById(R.id.tv_title);
        llClose = findViewById(R.id.ll_close);

        if (mAttribution != null) {
            tvTitle.setText(mAttribution.getAttributionName());
        }
        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void showLicenseDetailView() {

        llLicenseDetail.removeAllViews();

        for (LicenseInfo licenseInfo : mAttribution.getLicensesInfo()) {

            View inflatedView = LayoutInflater.from(LicenseDetailActivity.this).inflate(R.layout.layout_license_detail, llLicenseDetail, false);

            TextView tvLicenseName = (TextView) inflatedView.findViewById(R.id.tv_license_name);
            TextView tvLicenseCopyright = (TextView) inflatedView.findViewById(R.id.tv_license_copyright);
            TextView tvLicenseDetail = (TextView) inflatedView.findViewById(R.id.tv_license_detail);

            tvLicenseName.setText(licenseInfo.getLicense().getLicenseName());
            tvLicenseCopyright.setText(licenseInfo.getCopyrightNotice());
            tvLicenseDetail.setText(licenseInfo.getLicense().getLicenseText());

            llLicenseDetail.addView(inflatedView);
        }
    }
}
