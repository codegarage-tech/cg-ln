package com.meembusoft.ln.controller;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meembusoft.ln.R;
import com.meembusoft.ln.enumeration.Language;
import com.meembusoft.ln.util.OnSingleClickListener;
import com.meembusoft.ln.util.SessionUtil;
import com.meembusoft.localemanager.LocaleManager;
import com.meembusoft.localemanager.languagesupport.LanguagesSupport;
import com.skydoves.flourish.Flourish;
import com.skydoves.flourish.FlourishAnimation;
import com.skydoves.flourish.FlourishOrientation;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

public class SettingsController {

    private ViewGroup parentView;
    private Activity mActivity;

    // Menu
    private Flourish mFlourish;
    private PowerMenu mPowerMenu;

    // View elements
//    private SwitchIconView switchStopNotification;
    private LinearLayout llAccountLoggedIn, llAccountLoggedOut;
    private RelativeLayout rlLoginOrCreateAccount, rlAboutProfile, rlOrders, rlFavoriteProduct, rlLogout, rlStopNotification, rlAppNotifications, rlAboutApp, rlDownloadApp, rlSupport;
    private String mSelectedAppLanguageCode = "";
    private TextView tvAppLanguage;

    public SettingsController(Activity activity, ViewGroup view) {
        mActivity = activity;
        parentView = view;

        initMenu();
        initView();
        initActions();
    }

    private void initMenu() {
        // Set menu view
        mFlourish = new Flourish.Builder(parentView)
                .setFlourishLayout(R.layout.layout_settings)
                .setFlourishAnimation(FlourishAnimation.BOUNCE)
                .setFlourishOrientation(FlourishOrientation.TOP_RIGHT)
                //.setIsShowedOnStart(false)
                .setDuration(800L)
                .build();

        // Set title
        ((TextView) mFlourish.flourishView.findViewById(R.id.tv_title)).setText(R.string.txt_settings);

        mFlourish.flourishView.findViewById(R.id.ll_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFlourish.dismiss();
            }
        });

        mFlourish.flourishView.findViewById(R.id.rl_app_language).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAppLanguages();
            }
        });

        // Set app selected language for the very first time when the menu is initialized
        mSelectedAppLanguageCode = LocaleManager.getSelectedLanguageId(mActivity);
        tvAppLanguage = mFlourish.flourishView.findViewById(R.id.tv_app_language);
        tvAppLanguage.setText(Language.getLanguage(mSelectedAppLanguageCode).getLabel(mActivity));
    }

    public void openSettings() {
        mFlourish.show();
    }

    public boolean closeSettings() {
        if (mPowerMenu != null && mPowerMenu.isShowing()) {
            mPowerMenu.dismiss();
            return true;
        }
        if (mFlourish != null && mFlourish.isShowing()) {
            mFlourish.dismiss();
            return true;
        }

        return false;
    }

    private void initView() {
        llAccountLoggedIn = parentView.findViewById(R.id.ll_account_logged_in);
        llAccountLoggedOut = parentView.findViewById(R.id.ll_account_logged_out);
        rlLoginOrCreateAccount = parentView.findViewById(R.id.rl_login_or_create_account);
        rlAboutProfile = parentView.findViewById(R.id.rl_about_profile);
        rlOrders = parentView.findViewById(R.id.rl_orders);
        rlFavoriteProduct = parentView.findViewById(R.id.rl_favorite_product);
        rlLogout = parentView.findViewById(R.id.rl_logout);
//        switchStopNotification = parentView.findViewById(R.id.switch_stop_notification);
        rlStopNotification = parentView.findViewById(R.id.rl_stop_notification);
        rlAppNotifications = parentView.findViewById(R.id.rl_app_notifications);
        rlAboutApp = parentView.findViewById(R.id.rl_about_app);
        rlDownloadApp = parentView.findViewById(R.id.rl_download_app);
        rlSupport = parentView.findViewById(R.id.rl_support);
    }

    private void initActions() {
        rlLoginOrCreateAccount.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
//                Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
//                getActivity().startActivityForResult(intentLogin, INTENT_KEY_REQUEST_CODE_LOGIN);
            }
        });

        rlAboutProfile.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
//                Intent intentAboutProfile = new Intent(getActivity(), AboutProfileActivity.class);
//                getActivity().startActivity(intentAboutProfile);
            }
        });

        rlOrders.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
//                Intent intentOrders = new Intent(getActivity(), OrdersActivity.class);
//                getActivity().startActivity(intentOrders);
            }
        });

        rlFavoriteProduct.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
//                Intent intentFavorite = new Intent(getActivity(), FavoriteProductActivity.class);
//                getActivity().startActivity(intentFavorite);
            }
        });

        rlLogout.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
//                ((HomeActivity) getActivity()).doLogout(NavigationId.SETTINGS);
//                refreshAccountView();
            }
        });

        rlStopNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                boolean stopNotificationStatus = FcmManager.getInstance().isAppNotificationStopped();
//                switchStopNotification.setIconEnabled(stopNotificationStatus);
//                FcmManager.getInstance().stopAppNotification(!stopNotificationStatus);
            }
        });

        rlAppNotifications.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
//                Intent intentAppNotifications = new Intent(getActivity(), AppNotificationsActivity.class);
//                getActivity().startActivity(intentAppNotifications);
            }
        });

        rlAboutApp.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
//                AboutActivity.launch(getActivity());
//                AboutActivity.setLicenseListener(new LicenseClickListener() {
//                    @Override
//                    public void onLicenseClick() {
//                        Intent intentLicense = new Intent(getActivity(), LicenseActivity.class);
//                        startActivity(intentLicense);
//                    }
//                });
            }
        });

        rlDownloadApp.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
//                Intent intent = new Intent(getActivity(), DownloadAppActivity.class);
//                startActivity(intent);
            }
        });

        rlSupport.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
//                Intent intentOnlineSupport = new Intent(getActivity(), OnlineSupportActivity.class);
//                startActivity(intentOnlineSupport);
            }
        });
    }

    private void refreshAccountView() {
        if (SessionUtil.getUser(mActivity) != null) {
            llAccountLoggedIn.setVisibility(View.VISIBLE);
            llAccountLoggedOut.setVisibility(View.GONE);
        } else {
            llAccountLoggedIn.setVisibility(View.GONE);
            llAccountLoggedOut.setVisibility(View.VISIBLE);
        }
    }

    private void refreshNotificationView() {
//        switchStopNotification.setIconEnabled(!FcmManager.getInstance().isAppNotificationStopped());
    }

    private void showAppLanguages() {
        OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
            @Override
            public void onItemClick(int position, PowerMenuItem item) {
                for (Language language : Language.values()) {
                    if (language.getLabel(mActivity).equalsIgnoreCase(item.getTitle().toString())) {
                        if (!mSelectedAppLanguageCode.equalsIgnoreCase(language.getLanguageCode())) {
                            LocaleManager.setLocale(mActivity, language.getLanguageCode());
                            mSelectedAppLanguageCode = language.getLanguageCode();
                            tvAppLanguage.setText(language.getLabel(mActivity));
                        }
                    }
                }

                mPowerMenu.setSelectedPosition(position);
                mPowerMenu.dismiss();
            }
        };
        mPowerMenu = new PowerMenu.Builder(mActivity)
                .addItem(new PowerMenuItem(Language.ENGLISH.getLabel(mActivity), mSelectedAppLanguageCode.equalsIgnoreCase(LanguagesSupport.Language.ENGLISH)))
                .addItem(new PowerMenuItem(Language.BENGALI.getLabel(mActivity), mSelectedAppLanguageCode.equalsIgnoreCase(LanguagesSupport.Language.BENGALI)))
                .setWidth(450)
                .setHeight(400)
                .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT) // Animation start point (TOP | LEFT).
                .setMenuRadius(10f) // sets the corner radius.
                .setMenuShadow(10f) // sets the shadow.
                .setTextColorResource(R.color.paragraphTextColor)
                .setTextSize(16)
                .setTextGravity(Gravity.CENTER)
                .setTextTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD))
                .setSelectedTextColorResource(R.color.titleTextColor)
                .setMenuColorResource(R.color.colorPrimary)
                .setSelectedMenuColorResource(R.color.yellow90)
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .build();
        mPowerMenu.showAtCenter(parentView);
    }
}