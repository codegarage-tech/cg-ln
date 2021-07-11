package com.meembusoft.ln.base.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RuntimePermissionManager {

    public static final int REQUEST_CODE_PERMISSION = 42030;
    public static final int REQUEST_CODE_APP_INFO = 42031;
    public static final int REQUEST_CODE_PERMISSION_WRITE_SETTINGS = 42032;
    public static final int REQUEST_CODE_PERMISSION_DRAW_OVERLAY = 42033;

    private HashMap<String, Boolean> mPermissionStatus = null;
    private static RuntimePermissionManager mRuntimePermissionManager = null;
    public static final String TAG = "RuntimePermissionManager";

    private RuntimePermissionManager() {
        mPermissionStatus = new HashMap<>();
        mPermissionStatus.clear();
    }

    public static RuntimePermissionManager getInstance() {
        if (mRuntimePermissionManager == null) {
            mRuntimePermissionManager = new RuntimePermissionManager();
        }
        return mRuntimePermissionManager;
    }

    public void requestRuntimePermission(Activity activity, List<String> permissions) {
        for (String permission : permissions) {
            mPermissionStatus.put(permission, false);
        }
        ActivityCompat.requestPermissions(activity, permissions.toArray(new String[permissions.size()]), REQUEST_CODE_PERMISSION);
    }

    public void requestRuntimePermission(Activity activity, List<String> permissions, int requestCode) {
        for (String permission : permissions) {
            mPermissionStatus.put(permission, false);
        }
        ActivityCompat.requestPermissions(activity, permissions.toArray(new String[permissions.size()]), requestCode);
    }

    public void requestRuntimePermission(Fragment fragment, List<String> permissions, int requestCode) {
        for (String permission : permissions) {
            mPermissionStatus.put(permission, false);
        }
        fragment.requestPermissions(permissions.toArray(new String[permissions.size()]), requestCode);
    }

    public boolean isPermissionRequested(List<String> permissions) {
        for (String permission : permissions) {
            if (mPermissionStatus.containsKey(permission)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getAllPermissions(Context context) {
        ArrayList<String> allPermissions = new ArrayList<String>();
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            allPermissions = new ArrayList<String>(Arrays.asList(pi.requestedPermissions));
        } catch (Exception e) {
            return new ArrayList<String>();
        }
        return allPermissions;
    }

    /***********************
     * Granted permissions *
     ***********************/
    public boolean isPermissionGranted(Context context, String permission) {
        if (!TextUtils.isEmpty(permission)) {
            int permissionStatus = ContextCompat.checkSelfPermission(context, permission);
            if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    public boolean isAllPermissionsGranted(Context context) {
        List<String> permissions = getAllPermissions(context);
        if (permissions != null && permissions.size() > 0) {
            for (int i = 0; i < permissions.size(); i++) {
                int permissionStatus = ContextCompat.checkSelfPermission(context, permissions.get(i));
                if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isAllPermissionsGranted(Context context, String permissions[]) {
        if (permissions != null && permissions.length > 0) {
            for (int i = 0; i < permissions.length; i++) {
                int permissionStatus = ContextCompat.checkSelfPermission(context, permissions[i]);
                if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isAllPermissionsGranted(Context context, List<String> permissions) {
        if (permissions != null && permissions.size() > 0) {
            for (int i = 0; i < permissions.size(); i++) {
                int permissionStatus = ContextCompat.checkSelfPermission(context, permissions.get(i));
                if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }

        return true;
    }

    /**********************
     * Ungranted permissions *
     **********************/
    public List<String> getAllUngrantedPermissions(Activity activity, List<String> permissions) {
        List<String> listPermissionsNeeded = new ArrayList<String>();
        for (int i = 0; i < permissions.size(); i++) {
            int permissionStatus = ContextCompat.checkSelfPermission(activity, permissions.get(i));
            if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permissions.get(i));
            }
        }

        return listPermissionsNeeded;
    }

    public List<String> getAllUngrantedPermissions(Activity activity) {
        List<String> permissions = getAllPermissions(activity);
        List<String> listPermissionsNeeded = new ArrayList<String>();
        for (int i = 0; i < permissions.size(); i++) {
            int permissionStatus = ContextCompat.checkSelfPermission(activity, permissions.get(i));
            if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permissions.get(i));
            }
        }

        return listPermissionsNeeded;
    }

    /**********************
     * Denied permissions *
     **********************/
    public List<String> getAllDeniedPermissions(Activity activity, List<String> permissions) {
        List<String> listPermissionsNeeded = new ArrayList<String>();
        for (int i = 0; i < permissions.size(); i++) {
            int permissionStatus = ContextCompat.checkSelfPermission(activity, permissions.get(i));
            if (permissionStatus != PackageManager.PERMISSION_GRANTED && ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions.get(i))) {
                listPermissionsNeeded.add(permissions.get(i));
            }
        }

        return listPermissionsNeeded;
    }

    public List<String> getAllDeniedPermissions(Activity activity) {
        List<String> permissions = getAllPermissions(activity);
        List<String> listPermissionsNeeded = new ArrayList<String>();
        for (int i = 0; i < permissions.size(); i++) {
            int permissionStatus = ContextCompat.checkSelfPermission(activity, permissions.get(i));
            if (permissionStatus != PackageManager.PERMISSION_GRANTED && ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions.get(i))) {
                listPermissionsNeeded.add(permissions.get(i));
            }
        }

        return listPermissionsNeeded;
    }

    /***************************
     * Never asked permissions *
     ***************************/
    public boolean isNeverAskedPermission(Activity activity, String permission) {
        int permissionStatus = ContextCompat.checkSelfPermission(activity, permission);
        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            return !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
        }
        return false;
    }

    public List<String> getAllNeverAskedPermissions(Activity activity, List<String> permissions) {
        ArrayList<String> neverAskedPermissions = new ArrayList<String>();
        if (permissions != null && permissions.size() > 0) {
            for (int i = 0; i < permissions.size(); i++) {
                if (isNeverAskedPermission(activity, permissions.get(i))) {
                    neverAskedPermissions.add(permissions.get(i));
                }
            }
        }

        return neverAskedPermissions;
    }

    public List<String> getAllNeverAskedPermissions(Activity activity) {
        List<String> permissions = getAllPermissions(activity);
        ArrayList<String> neverAskedPermissions = new ArrayList<String>();
        if (permissions != null && permissions.size() > 0) {
            for (int i = 0; i < permissions.size(); i++) {
                if (isNeverAskedPermission(activity, permissions.get(i))) {
                    neverAskedPermissions.add(permissions.get(i));
                }
            }
        }

        return neverAskedPermissions;
    }

    /*******************************
     * Application detail settings *
     *******************************/
    public void openAppInfo(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", activity.getPackageName(), null));
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivityForResult(intent, REQUEST_CODE_APP_INFO);
    }

//    public void syncPermissionsWithAppInfo(Context context, List<String> permissions) {
////        Logger.d(TAG, "Started syncing permissions with app info");
//        for (String permission : permissions) {
//            int permissionStatus = ContextCompat.checkSelfPermission(context, permission);
//            mPermissionStatus.put(permission, permissionStatus == PackageManager.PERMISSION_GRANTED);
//        }
//
//        for (Map.Entry<String, Boolean> entry : mPermissionStatus.entrySet()) {
////            Logger.d(TAG, "key: " + entry.getKey() + " value: " + entry.getValue());
//        }
//    }

    /******************
     * Write settings *
     ******************/
    public boolean isWriteSettingsPermissionAccepted(Context context) {
        return (Settings.System.canWrite(context.getApplicationContext()));
    }

    public void requestWriteSettingsPermission(Activity activity) {
        Intent intentWriteSettings = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intentWriteSettings, REQUEST_CODE_PERMISSION_WRITE_SETTINGS);
    }

    /****************
     * Draw Overlay *
     ****************/
    public boolean isDrawOverlayPermissionAccepted(Context context) {
        return (Settings.canDrawOverlays(context.getApplicationContext()));
    }

    public void requestDrawOverlayPermission(Activity activity) {
        Intent intentDrawOverlay = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intentDrawOverlay, REQUEST_CODE_PERMISSION_DRAW_OVERLAY);
    }
}