package com.meembusoft.ln.base.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.meembusoft.ln.R;
import com.meembusoft.ln.base.BaseAlertDialog;

/**
 * The PermissionDialog class will be used to show permission related dialog
 */

public class PermissionDialog extends BaseAlertDialog {

    private Activity mActivity;
    private String mTitle;
    private String mMessage;
    private DialogInterface.OnClickListener mDialogListener;

    public static PermissionDialog getInstance(Activity context, String title, String message, DialogInterface.OnClickListener dialogListener) {
        return new PermissionDialog(context, title, message, dialogListener);
    }

    public PermissionDialog(Activity context, String title, String message, DialogInterface.OnClickListener dialogListener) {
        super(context);
        this.mActivity = context;
        this.mTitle = title;
        this.mMessage = message;
        this.mDialogListener = dialogListener;
    }

    @Override
    public AlertDialog.Builder initView() {
        return prepareView(mTitle, mMessage, -1, R.string.txt_ok, -1, mDialogListener);
    }
}
