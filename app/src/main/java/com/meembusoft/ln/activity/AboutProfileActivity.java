package com.meembusoft.ln.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.dhaval2404.imagepicker.constant.ImageProvider;
import com.github.dhaval2404.imagepicker.listener.DismissListener;
import com.github.florent37.expansionpanel.ExpansionHeader;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.meembusoft.ln.R;
import com.meembusoft.ln.base.BaseActivity;
import com.meembusoft.ln.enumeration.GenderType;
import com.meembusoft.ln.model.User;
import com.meembusoft.ln.util.AppUtil;
import com.meembusoft.ln.util.GlideManager;
import com.meembusoft.ln.util.OnSingleClickListener;
import com.meembusoft.ln.util.SessionUtil;
import com.nex3z.flowlayout.FlowLayout;
import com.nex3z.flowlayout.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class AboutProfileActivity extends BaseActivity {

    // Toolbar
    private TextView tvTitle;
    private LinearLayout llClose, llSignOut;
    private AppCompatCheckBox accbEdit;

    // View items
    private EditText edtFullName, edtMobileNumber, edtPassword, edtEmail, edtOccupation, edtAddress;
    private ImageView ivProfileImage, ivAttachment;
    private LinearLayout llProfileImage;

    // Gender
    private FlowLayout flowLayoutGender;
    private FlowLayoutManager flowLayoutManagerGender;
    private ExpansionHeader expansionHeader;
    private ExpansionLayout expansionLayout;
    private TextView tvSelectedGender;
    private LinearLayout llExpandableHeaderGender;
    private ImageView ivHeaderIndicator;

    // Image picker
    private static final int PROFILE_IMAGE_REQ_CODE = 101;
    private Uri mProfileUri;

    @Override
    public int initToolbarLayout() {
        return R.layout.toolbar_about_profile;
    }

    @Override
    public int initScreenLayout() {
        return R.layout.activity_about_profile;
    }

    @Override
    public void initIntentData(Bundle savedInstanceState, Intent intent) {

    }

    @Override
    public void initViews() {
        // Toolbar
        tvTitle = findViewById(R.id.tv_title);
        llClose = findViewById(R.id.ll_close);
        llSignOut = findViewById(R.id.ll_sign_out);
        accbEdit = findViewById(R.id.cb_edit);

        // View items
        ivProfileImage = findViewById(R.id.iv_profile_image);
        llProfileImage = findViewById(R.id.ll_profile_image);
        ivAttachment = findViewById(R.id.iv_attachment);
        edtFullName = findViewById(R.id.edt_full_name);
        edtMobileNumber = findViewById(R.id.edt_mobile_no);
        edtPassword = findViewById(R.id.edt_password);
        edtEmail = findViewById(R.id.edt_email);
        edtOccupation = findViewById(R.id.edt_occupation);
        edtAddress = findViewById(R.id.edt_address);
        // Gender
        flowLayoutGender = findViewById(R.id.fl_gender);
        expansionHeader = findViewById(R.id.eh_unit);
        expansionLayout = findViewById(R.id.expansionLayout);
        tvSelectedGender = findViewById(R.id.tv_selected_gender);
        llExpandableHeaderGender = findViewById(R.id.ll_expandable_layout);
        ivHeaderIndicator = findViewById(R.id.headerIndicator);
    }

    @Override
    public void initViewsData(Bundle savedInstanceState) {
        // Toolbar
        tvTitle.setText(R.string.txt_about_profile);
        accbEdit.setChecked(false);

        setProfileInformation();
        syncAboutProfile(false);
    }

    @Override
    public void initActions(Bundle savedInstanceState) {
        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBackPress();
            }
        });
        llSignOut.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View var1) {
                setResult(RESULT_OK, getIntent());
                finish();
            }
        });
        accbEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                syncAboutProfile(isChecked);
            }
        });
        ivAttachment.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View var1) {

            }
        });
        llProfileImage.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View var1) {
                ImagePicker.with(getActivity())
                        // Crop Square image
                        .cropSquare()
                        .setImageProviderInterceptor(new Function1<ImageProvider, Unit>() {
                            @Override
                            public Unit invoke(ImageProvider imageProvider) {
                                Log.d("ImagePicker", "Selected ImageProvider: " + imageProvider.toString());
                                return null;
                            }
                        }).setDismissListener(new DismissListener() {
                    @Override
                    public void onDismiss() {
                        Log.d("ImagePicker", "Dialog Dismiss");
                    }
                })
                        // Image resolution will be less than 512 x 512
                        .maxResultSize(200, 200)
                        .start(PROFILE_IMAGE_REQ_CODE);
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

    private void syncAboutProfile(boolean isEditable) {
        // Enable/Disable edittext
        edtFullName.setEnabled(isEditable);
        edtMobileNumber.setEnabled(isEditable);
        edtPassword.setEnabled(isEditable);
        edtEmail.setEnabled(isEditable);
        edtOccupation.setEnabled(isEditable);
        edtAddress.setEnabled(isEditable);
        llExpandableHeaderGender.setEnabled(isEditable);
        // Add/Remove background and change gravity
        if (isEditable) {
            ivAttachment.setVisibility(View.VISIBLE);
            ivHeaderIndicator.setVisibility(View.VISIBLE);
            edtFullName.setGravity(Gravity.CENTER_VERTICAL);
            edtFullName.setBackgroundResource(R.drawable.selector_line_edt);
            edtMobileNumber.setGravity(Gravity.CENTER_VERTICAL);
            edtMobileNumber.setBackgroundResource(R.drawable.selector_line_edt);
            edtPassword.setGravity(Gravity.CENTER_VERTICAL);
            edtPassword.setBackgroundResource(R.drawable.selector_line_edt);
            edtEmail.setGravity(Gravity.CENTER_VERTICAL);
            edtEmail.setBackgroundResource(R.drawable.selector_line_edt);
            edtOccupation.setGravity(Gravity.CENTER_VERTICAL);
            edtOccupation.setBackgroundResource(R.drawable.selector_line_edt);
            edtAddress.setGravity(Gravity.CENTER_VERTICAL);
            edtAddress.setBackgroundResource(R.drawable.selector_line_edt);
        } else {
            ivAttachment.setVisibility(View.GONE);
            ivHeaderIndicator.setVisibility(View.GONE);
            edtFullName.setGravity(Gravity.BOTTOM);
            edtFullName.setBackgroundResource(0);
            edtMobileNumber.setGravity(Gravity.BOTTOM);
            edtMobileNumber.setBackgroundResource(0);
            edtPassword.setGravity(Gravity.BOTTOM);
            edtPassword.setBackgroundResource(0);
            edtEmail.setGravity(Gravity.BOTTOM);
            edtEmail.setBackgroundResource(0);
            edtOccupation.setGravity(Gravity.BOTTOM);
            edtOccupation.setBackgroundResource(0);
            edtAddress.setGravity(Gravity.BOTTOM);
            edtAddress.setBackgroundResource(0);
        }
    }

    private void setProfileInformation() {
        User user = SessionUtil.getUser(getActivity());
        if (user != null) {
            if (TextUtils.isEmpty(user.getUser_image().getUrl())) {
//                ivProfileImage.setImageResource(R.drawable.vector_user);
                GlideManager.setImage(getActivity(), ivProfileImage, R.drawable.vector_user, true);
                AppUtil.applyViewTint(ivProfileImage, R.color.imageTintColor);
            } else {
//                Picasso.get().load(user.getUser_image().getUrl()).into(ivProfileImage);
                GlideManager.setImage(getActivity(), ivProfileImage, user.getUser_image().getUrl(), true);
            }
            edtFullName.setText(user.getUser_name());
            edtMobileNumber.setText(user.getUser_phone());
            edtPassword.setText(user.getUser_password());
            edtEmail.setText(user.getUser_email());
            edtOccupation.setText(user.getUser_occupation());
            edtAddress.setText(user.getUser_address());
            tvSelectedGender.setText(user.getUser_gender());

            // Init gender
            initGender(user);
        }
    }

    private void initGender(User user) {
        List<String> keys = new ArrayList<>();
        GenderType[] genderTypes = GenderType.values();
        for (int i = 0; i < genderTypes.length; i++) {
            keys.add(genderTypes[i].toString());
        }
        if (!keys.isEmpty()) {
            // Remove all previous keys
            if (flowLayoutManagerGender != null) {
                flowLayoutManagerGender.removeAllKeys();
            }

            //Set flow layout with connection key
            flowLayoutManagerGender = new FlowLayoutManager.FlowViewBuilder(getActivity(), flowLayoutGender, keys, new FlowLayoutManager.onFlowViewClick() {
                @Override
                public void flowViewClick(TextView updatedTextView) {
                    List<TextView> selectedGenderKeys = flowLayoutManagerGender.getSelectedFlowViews();
                    String tempSelectedGender = (selectedGenderKeys.size() > 0) ? selectedGenderKeys.get(0).getText().toString() : "";
                    Log.d(TAG, "tempSelectedGender: " + tempSelectedGender);

                    // Update selection
                    tvSelectedGender.setText(tempSelectedGender);

                    // Close expansion layout
                    expansionLayout.collapse(true);
                }
            })
                    .setSingleChoice(true)
                    .build();

            // Select gender for the very first time
            if (!TextUtils.isEmpty(user.getUser_gender())) {
                flowLayoutManagerGender.clickFlowView(user.getUser_gender());
            } else {
                flowLayoutManagerGender.clickFlowView(keys.get(0));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // Uri object will not be null for RESULT_OK
            Uri uri = data.getData();

            switch (requestCode) {
                case PROFILE_IMAGE_REQ_CODE:
                    mProfileUri = uri;
                    GlideManager.setImage(getActivity(), ivProfileImage, uri, true);
                    break;
            }
        }
    }
}