package com.meembusoft.ln.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class SignInActivity extends BaseActivity {

    // Toolbar
    private TextView tvTitle;
    private LinearLayout llClose;

    // View items
    private EditText edtMobileNumber, edtPassword;
    private TextView tvSignUpNow, tvForgotPassword;
    private LinearLayout llSignIn;
    private RelativeLayout rlFacebookLogin, rlGoogleLogin;

    @Override
    public int initToolbarLayout() {
        return R.layout.toolbar_base;
    }

    @Override
    public int initScreenLayout() {
        return R.layout.activity_sign_in;
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
        edtMobileNumber = findViewById(R.id.edt_mobile_number);
        edtPassword = findViewById(R.id.edt_password);
        tvSignUpNow = findViewById(R.id.tv_signup_now);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
        llSignIn = findViewById(R.id.ll_sign_in);
        rlFacebookLogin = findViewById(R.id.rl_fb_login);
        rlGoogleLogin = findViewById(R.id.rl_google_login);
    }

    @Override
    public void initViewsData(Bundle savedInstanceState) {
        // Toolbar
        tvTitle.setText(R.string.txt_sign_in);

        if (BuildType.getBuildType() == BuildType.DEBUG) {
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

        tvSignUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignUp = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intentSignUp);
                finish();
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentForgotPassword = new Intent(getActivity(), ForgotPasswordActivity.class);
                startActivity(intentForgotPassword);
            }
        });

        llSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                User user = new User("1", "", "Md. Rashadul Alam", edtMobileNumber.getText().toString(), "", "", edtPassword.getText().toString(), "", 0, "", new Image("https://www.pngfind.com/pngs/m/292-2924858_user-icon-business-man-flat-png-transparent-png.png"));
                SessionUtil.setUser(getActivity(), APIResponse.getJSONStringFromObject(user));
                finish();
            }
        });

        rlFacebookLogin.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
//                startActivityForResult(SocialLogin.Companion.loginIntent(getActivity(), SocialLogin.LoginType.FB), INTENT_KEY_REQUEST_CODE_LOGIN);
            }
        });

        rlGoogleLogin.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
//                startActivityForResult(SocialLogin.Companion.loginIntent(getActivity(), SocialLogin.LoginType.GOOGLE), INTENT_KEY_REQUEST_CODE_LOGIN);
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