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
import com.meembusoft.ln.base.flavor.BuildType;
import com.meembusoft.ln.model.Image;
import com.meembusoft.ln.model.User;
import com.meembusoft.ln.util.CookieBarUtil;
import com.meembusoft.ln.util.OnSingleClickListener;
import com.meembusoft.ln.util.SessionUtil;
import com.meembusoft.ln.util.ValidationManager;
import com.meembusoft.retrofitmanager.APIResponse;

public class SignUpActivity extends BaseActivity {

    // Toolbar
    private TextView tvTitle;
    private LinearLayout llClose;

    // View items
    private EditText edtFullName, edtMobileNumber, edtPassword;
    private LinearLayout llSignUp;
    private TextView tvSignInNow;

    @Override
    public int initToolbarLayout() {
        return R.layout.toolbar_base;
    }

    @Override
    public int initScreenLayout() {
        return R.layout.activity_sign_up;
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
        edtFullName = findViewById(R.id.edt_full_name);
        edtMobileNumber = findViewById(R.id.edt_mobile_number);
        edtPassword = findViewById(R.id.edt_password);
        llSignUp = findViewById(R.id.ll_sign_up);
        tvSignInNow = findViewById(R.id.tv_sign_in_now);
    }

    @Override
    public void initViewsData(Bundle savedInstanceState) {
        // Toolbar
        tvTitle.setText(getString(R.string.txt_sign_up));

        if (BuildType.getBuildType() == BuildType.DEBUG) {
            edtFullName.setText("Md. Rashadul Alam");
            edtMobileNumber.setText("01794620787");
            edtPassword.setText("123456");
        }
    }

    @Override
    public void initActions(Bundle savedInstanceState) {
        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBackPress();
            }
        });
        llSignUp.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View var1) {
                if (TextUtils.isEmpty(edtFullName.getText().toString())) {
                    CookieBarUtil.showCookieBarWarning(getActivity(), getString(R.string.txt_please_input_a_full_name));
                    return;
                }
                if (TextUtils.isEmpty(edtMobileNumber.getText().toString())) {
                    CookieBarUtil.showCookieBarWarning(getActivity(), getString(R.string.txt_please_input_a_mobile_number));
                    return;
                }
                if (!ValidationManager.isValidBangladeshiMobileNumber(edtMobileNumber.getText().toString())) {
                    CookieBarUtil.showCookieBarWarning(getActivity(), getString(R.string.txt_please_input_a_valid_mobile_number));
                    return;
                }
                if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                    CookieBarUtil.showCookieBarWarning(getActivity(), getString(R.string.txt_please_input_a_password));
                    return;
                }

                User user = new User("1", "", edtFullName.getText().toString(), edtMobileNumber.getText().toString(), "", "", edtPassword.getText().toString(), "", 0, "", new Image("https://www.pngfind.com/pngs/m/292-2924858_user-icon-business-man-flat-png-transparent-png.png"));
                SessionUtil.setUser(getActivity(), APIResponse.getJSONStringFromObject(user));
                setResult(RESULT_OK, getIntent());
                finish();
            }
        });
        tvSignInNow.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View var1) {
                Intent intentSignInNow = new Intent(getActivity(), SignInActivity.class);
                startActivity(intentSignInNow);
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