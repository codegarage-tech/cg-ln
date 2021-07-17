package com.meembusoft.ln.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meembusoft.ln.R;
import com.meembusoft.ln.base.BaseActivity;
import com.meembusoft.ln.util.CookieBarUtil;
import com.meembusoft.ln.util.OnSingleClickListener;
import com.meembusoft.ln.util.ValidationManager;

public class ForgotPasswordActivity extends BaseActivity {

    // Toolbar
    private TextView tvTitle;
    private LinearLayout llClose;

    // View items
    private EditText edtEmailAddress, edtMobileNumber;
    private LinearLayout llSubmitEmailAddress, llSubmitMobileNumber;

    @Override
    public int initToolbarLayout() {
        return R.layout.toolbar_base;
    }

    @Override
    public int initScreenLayout() {
        return R.layout.activity_forgot_password;
    }

    @Override
    public void initIntentData(Bundle savedInstanceState, Intent intent) {

    }

    @Override
    public void initViews() {
        // Toolbar
        tvTitle = findViewById(R.id.tv_title);
        llClose = findViewById(R.id.ll_close);

        // View items
        edtEmailAddress = findViewById(R.id.edt_email_address);
        edtMobileNumber = findViewById(R.id.edt_mobile_number);
        llSubmitEmailAddress = findViewById(R.id.ll_submit_email);
        llSubmitMobileNumber = findViewById(R.id.ll_submit_mobile);
    }

    @Override
    public void initViewsData(Bundle savedInstanceState) {
        // Toolbar
        tvTitle.setText(R.string.txt_forgot_password);
    }

    @Override
    public void initActions(Bundle savedInstanceState) {
        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBackPress();
            }
        });
        llSubmitEmailAddress.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View var1) {
                if (TextUtils.isEmpty(edtEmailAddress.getText().toString())) {
                    CookieBarUtil.showCookieBarWarning(getActivity(), getString(R.string.txt_please_input_an_email_address));
                    return;
                }
                if (!ValidationManager.isValidEmail(edtEmailAddress.getText().toString())) {
                    CookieBarUtil.showCookieBarWarning(getActivity(), getString(R.string.txt_please_input_a_valid_email_address));
                    return;
                }
                finish();
            }
        });
        llSubmitMobileNumber.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View var1) {
                if (TextUtils.isEmpty(edtMobileNumber.getText().toString())) {
                    CookieBarUtil.showCookieBarWarning(getActivity(), getString(R.string.txt_please_input_a_mobile_number));
                    return;
                }
                if (!ValidationManager.isValidBangladeshiMobileNumber(edtMobileNumber.getText().toString())) {
                    CookieBarUtil.showCookieBarWarning(getActivity(), getString(R.string.txt_please_input_a_valid_mobile_number));
                    return;
                }
                finish();
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
}