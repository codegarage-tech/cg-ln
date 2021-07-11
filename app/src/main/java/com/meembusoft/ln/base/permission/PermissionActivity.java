package com.meembusoft.ln.base.permission;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.meembusoft.ln.R;
import com.meembusoft.ln.base.lifecycle.AppLifecycleHandler;
import com.meembusoft.ln.base.lifecycle.AppLifecycleListener;

import java.util.ArrayList;
import java.util.List;

import static com.meembusoft.ln.base.permission.RuntimePermissionManager.REQUEST_CODE_APP_INFO;
import static com.meembusoft.ln.base.permission.RuntimePermissionManager.REQUEST_CODE_PERMISSION;
import static com.meembusoft.ln.base.permission.RuntimePermissionManager.REQUEST_CODE_PERMISSION_DRAW_OVERLAY;
import static com.meembusoft.ln.base.permission.RuntimePermissionManager.REQUEST_CODE_PERMISSION_WRITE_SETTINGS;

public abstract class PermissionActivity extends AppCompatActivity implements AppLifecycleListener {

    public abstract void onAllPermissionsAccepted();

    private enum REQUEST_FROM {ON_CREATE, ON_RESUME, ON_ACTIVITY_RESULT, ON_REQUEST_PERMISSION_RESULT}

    private enum APP_TYPE {SYSTEM, USER}

    private static final String TAG = PermissionActivity.class.getSimpleName();
    // App Runtime Permission Array
    private List<String> RUNTIME_PERMISSIONS = new ArrayList<>();
    private static final String SAVED_INSTANCE_STATE_KEY = "saved_instance_state_key";
    private static final String SAVED_INSTANCE_STATE_VALUE = "saved_instance_state_value";
    private static AlertDialog mPermissionDialog;
    private boolean isPermissionRequested = false, isFirstTimeRuntimePermissionRequested = false;
    private AppLifecycleHandler mLifeCycleHandler;
    // Change app type from here
    private APP_TYPE mAppType = APP_TYPE.USER;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(SAVED_INSTANCE_STATE_KEY, SAVED_INSTANCE_STATE_VALUE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RUNTIME_PERMISSIONS = RuntimePermissionManager.getInstance().getAllPermissions(this);
        // Check for app recreate, savedInstanceState will be null for fresh launch.
        // If activity recreate && not MainActivity instance then exit from that.
//        if (savedInstanceState != null && !(this instanceof MainActivity)) {
//            Log.d(TAG, "onCreate>>activityRecreated & not main activity instance");
//            finish();
//            return;
//        }
        // Request all permission
        Log.d(TAG, "onCreate>>Calling resetAll()");
        resetAll();
        Log.d(TAG, "onCreate>>Requesting permissions from onCreate()");
        requestAllPermissions(REQUEST_FROM.ON_CREATE);
        // Register for App LifeCycle handler
        mLifeCycleHandler = AppLifecycleHandler.getInstance(this);
        registerLifecycleHandler(mLifeCycleHandler);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Request all permission
        Log.d(TAG, "onResume>>Requesting permissions from onResume()");
        requestAllPermissions(REQUEST_FROM.ON_RESUME);
    }

    /**
     * Request all permission serially
     */
    private void requestAllPermissions(REQUEST_FROM requestFrom) {
        Log.d(TAG, "requestAllPermissions>>Inside requestAllPermissions()");
        // Notify if all permission is accepted

        if (isAllPermissionsGranted()) {
            Log.d(TAG, "requestAllPermissions>>All permissions are accepted");
            Log.d(TAG, "requestAllPermissions>>Calling resetAll()");
            resetAll();
            if (requestFrom == REQUEST_FROM.ON_ACTIVITY_RESULT || requestFrom == REQUEST_FROM.ON_REQUEST_PERMISSION_RESULT) {
                Log.d(TAG, "requestAllPermissions>>From onActivityResult/onRequestPermissionResult, notifying to the activity");
                onAllPermissionsAccepted();
                return;
            }
        }

        // Request permission
        if (!isPermissionRequested) {
            Log.d(TAG, "requestAllPermissions>>There is no running permission request");
            if (mAppType == APP_TYPE.SYSTEM) {
                // Check Write Settings Permission Granted
                boolean isWriteSettingPermissionGranted = RuntimePermissionManager.getInstance().isWriteSettingsPermissionAccepted(this);
                Log.d(TAG, "requestAllPermissions>>isWriteSettingPermissionGranted: " + isWriteSettingPermissionGranted);
                if (!isWriteSettingPermissionGranted) {
                    // Request Write Settings Permission
                    isPermissionRequested = true;
                    Log.d(TAG, "requestAllPermissions>>Requesting write settings permission");
                    RuntimePermissionManager.getInstance().requestWriteSettingsPermission(this);
                    return;
                } else {
                    Log.d(TAG, "requestAllPermissions>>Write settings permission is accepted");
                }

                // Check Draw Overlay Permission Granted
                boolean isDrawOverlayPermissionGranted = RuntimePermissionManager.getInstance().isDrawOverlayPermissionAccepted(this);
                Log.d(TAG, "requestAllPermissions>>isDrawOverlayPermissionGranted: " + isDrawOverlayPermissionGranted);
                if (!isDrawOverlayPermissionGranted) {
                    // Request Draw Overlay Permission
                    isPermissionRequested = true;
                    Log.d(TAG, "requestAllPermissions>>Requesting draw overlay permission");
                    RuntimePermissionManager.getInstance().requestDrawOverlayPermission(this);
                    return;
                } else {
                    Log.d(TAG, "requestAllPermissions>>Draw overlay permission is accepted");
                }
            }

            // Check All Runtime Permission Granted
            boolean isAllRuntimePermissionsGranted = RuntimePermissionManager.getInstance().isAllPermissionsGranted(this, RUNTIME_PERMISSIONS);
            Log.d(TAG, "requestAllPermissions>>isAllRuntimePermissionsGranted: " + isAllRuntimePermissionsGranted);
            if (!isAllRuntimePermissionsGranted) {
                // Check for all un granted permission
                List<String> unGrantedPermissions = RuntimePermissionManager.getInstance().getAllUngrantedPermissions(this, RUNTIME_PERMISSIONS);
                Log.d(TAG, "requestAllPermissions>>unGrantedPermissions: " + unGrantedPermissions);
                // Check for don't asked again permission
                List<String> neverAskedPermissions = RuntimePermissionManager.getInstance().getAllNeverAskedPermissions(this, RUNTIME_PERMISSIONS);
                Log.d(TAG, "requestAllPermissions>>neverAskedPermissions: " + neverAskedPermissions);

                if (!isFirstTimeRuntimePermissionRequested && (unGrantedPermissions.size() == neverAskedPermissions.size())) {
                    // First time run time permissions are not requested, when ungranted and never asked permissions are same
                    isFirstTimeRuntimePermissionRequested = true;
                    isPermissionRequested = true;
                    Log.d(TAG, "requestAllPermission>>Requesting runtime permissions for the very first time");
                    RuntimePermissionManager.getInstance().requestRuntimePermission(this, RUNTIME_PERMISSIONS);
                } else if (isFirstTimeRuntimePermissionRequested && (unGrantedPermissions.size() <= neverAskedPermissions.size())) {
                    // If first time runtime permissions has requested, also both ungranted and never asked permissions are same.
                    // That means all permissions are never asked permission
                    Log.d(TAG, "requestAllPermissions>>Calling app info dialog");
                    isPermissionRequested = true;
                    showDialogForOpeningAppInfo();
                } else {
                    Log.d(TAG, "requestAllPermissions>>Calling retry permission dialog");
                    isPermissionRequested = true;
                    showRetryPermissionDialog(unGrantedPermissions);
                }
            } else {
                Log.d(TAG, "requestAllPermissions>>All runtime permissions are accepted");
            }
        } else {
            Log.d(TAG, "requestAllPermissions>>Permission request has already performed");
        }
    }

    /**
     * Show Alert for permission
     */
    private void showDialogForOpeningAppInfo() {
        Log.d(TAG, "showDialogForOpeningAppInfo>>Inside showDialogForOpeningAppInfo()");
        Log.d(TAG, "showDialogForOpeningAppInfo>>Calling removePermissionDialog()");
        removePermissionDialog();
        mPermissionDialog = PermissionDialog.getInstance(this, "", getString(R.string.txt_runtime_permission_dialog),
                (dialogInterface, i) -> {
                    RuntimePermissionManager.getInstance().openAppInfo(this);
                }).initView().show();
    }

    /**
     * Show permission explanation dialog
     */
    private void showRetryPermissionDialog(List<String> unGrantedList) {
        Log.d(TAG, "showRetryPermissionDialog>>Inside showRetryPermissionDialog()");
        Log.d(TAG, "showRetryPermissionDialog>>Calling removePermissionDialog()");
        removePermissionDialog();
        mPermissionDialog = PermissionDialog.getInstance(this, "", getString(R.string.txt_runtime_permission_dialog), (dialogInterface, i) -> {
            // Request permission
            Log.d(TAG, "showRetryPermissionDialog>>Requesting runtime permissions");
            RuntimePermissionManager.getInstance().requestRuntimePermission(this, RUNTIME_PERMISSIONS);
        }).initView().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult>>requestCode: " + requestCode);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                isPermissionRequested = false;
                Log.d(TAG, "onRequestPermissionsResult>>Calling requestAllPermissions()");
                requestAllPermissions(REQUEST_FROM.ON_REQUEST_PERMISSION_RESULT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d(TAG, "onActivityResult>>requestCode: " + requestCode);
        if (requestCode == REQUEST_CODE_APP_INFO ||
                requestCode == REQUEST_CODE_PERMISSION_WRITE_SETTINGS ||
                requestCode == REQUEST_CODE_PERMISSION_DRAW_OVERLAY) {
            // Update flag
            isPermissionRequested = false;
            // Request all permission
            Log.d(TAG, "onActivityResult>>Requesting permissions from onActivityResult()");
            requestAllPermissions(REQUEST_FROM.ON_ACTIVITY_RESULT);
        }
    }

    public boolean isAllPermissionsGranted() {
        if (mAppType == APP_TYPE.SYSTEM) {
            return RuntimePermissionManager.getInstance().isWriteSettingsPermissionAccepted(this) &&
                    RuntimePermissionManager.getInstance().isDrawOverlayPermissionAccepted(this) &&
                    RuntimePermissionManager.getInstance().isAllPermissionsGranted(this, RUNTIME_PERMISSIONS);
        } else {
            return RuntimePermissionManager.getInstance().isAllPermissionsGranted(this, RUNTIME_PERMISSIONS);
        }
    }

    public void removePermissionDialog() {
        Log.d(TAG, "removePermissionDialog>>");
        try {
            if (mPermissionDialog != null) {
                Log.d(TAG, "removePermissionDialog>>mPermissionDialog is not null");
                mPermissionDialog.dismiss();
                mPermissionDialog = null;
            } else {
                Log.d(TAG, "removePermissionDialog>>mPermissionDialog is null");
            }
        } catch (Exception ex) {
            Log.d(TAG, "removePermissionDialog>>Exception: " + ex.getMessage());
        }
    }

    public void resetAll() {
        Log.d(TAG, "resetAll>>");
        Log.d(TAG, "resetAll>>Calling removePermissionDialog()");
        removePermissionDialog();
        isPermissionRequested = false;
        isFirstTimeRuntimePermissionRequested = false;
    }

    @Override
    protected void onDestroy() {
        // Unregister Lifecycle handler
        if (mLifeCycleHandler != null) {
            unregisterComponentCallbacks(mLifeCycleHandler);
        }
        super.onDestroy();
    }

    /**
     * Register callback for determined app in background or foreground
     *
     * @param lifeCycleHandler
     */
    private void registerLifecycleHandler(AppLifecycleHandler lifeCycleHandler) {
        // registerActivityLifecycleCallbacks(lifeCycleHandler);
        registerComponentCallbacks(lifeCycleHandler);
    }

    @Override
    public void onAppBackgrounded() {
        Log.d(TAG, "onAppBackgrounded");
    }

    @Override
    public void onAppForegrounded() {
        Log.d(TAG, "onAppForegrounded");
    }
}