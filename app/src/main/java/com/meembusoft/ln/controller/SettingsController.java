package com.meembusoft.ln.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.eggheadgames.aboutbox.AboutConfig;
import com.eggheadgames.aboutbox.IAnalytic;
import com.eggheadgames.aboutbox.IDialog;
import com.eggheadgames.aboutbox.activity.AboutActivity;
import com.eggheadgames.aboutbox.listener.LicenseClickListener;
import com.meembusoft.ln.R;
import com.meembusoft.ln.activity.AboutProfileActivity;
import com.meembusoft.ln.activity.AppNotificationsActivity;
import com.meembusoft.ln.activity.DownloadAppActivity;
import com.meembusoft.ln.activity.FavoriteProductActivity;
import com.meembusoft.ln.activity.HomeActivity;
import com.meembusoft.ln.activity.OrdersActivity;
import com.meembusoft.ln.activity.SupportActivity;
import com.meembusoft.ln.enumeration.Language;
import com.meembusoft.ln.util.AppUtil;
import com.meembusoft.ln.util.OnSingleClickListener;
import com.meembusoft.ln.util.SessionUtil;
import com.meembusoft.localemanager.LocaleManager;
import com.meembusoft.localemanager.languagesupport.LanguagesSupport;
import com.reversecoder.attributionpresenter.activity.LicenseActivity;
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
        initAboutPage();
        refreshAccountView();
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
                ((HomeActivity) mActivity).navigateToLogin();
            }
        });

        rlAboutProfile.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                Intent intentAboutProfile = new Intent(mActivity, AboutProfileActivity.class);
                mActivity.startActivity(intentAboutProfile);
            }
        });

        rlOrders.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                Intent intentOrders = new Intent(mActivity, OrdersActivity.class);
                mActivity.startActivity(intentOrders);
            }
        });

        rlFavoriteProduct.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                Intent intentFavorite = new Intent(mActivity, FavoriteProductActivity.class);
                mActivity.startActivity(intentFavorite);
            }
        });

        rlLogout.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                SessionUtil.setUser(mActivity, "");
                refreshAccountView();
                ((HomeActivity) mActivity).refreshUserView();
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
                Intent intentAppNotifications = new Intent(mActivity, AppNotificationsActivity.class);
                mActivity.startActivity(intentAppNotifications);
            }
        });

        rlAboutApp.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                AboutActivity.launch(mActivity);
                AboutActivity.setLicenseListener(new LicenseClickListener() {
                    @Override
                    public void onLicenseClick() {
                        Intent intentLicense = new Intent(mActivity, LicenseActivity.class);
                        mActivity.startActivity(intentLicense);
                    }
                });
            }
        });

        rlDownloadApp.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                Intent intent = new Intent(mActivity, DownloadAppActivity.class);
                mActivity.startActivity(intent);
            }
        });

        rlSupport.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                Intent intentSupport = new Intent(mActivity, SupportActivity.class);
                mActivity.startActivity(intentSupport);
            }
        });
    }

    public void refreshAccountView() {
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
                .setMenuColorResource(R.color.colorPrimaryDark)
                .setSelectedMenuColorResource(R.color.colorPrimary)
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .build();
        mPowerMenu.showAtCenter(parentView);
    }

    /**************************
     * Methods for about page *
     **************************/
    private void initAboutPage() {
        AboutConfig aboutConfig = AboutConfig.getInstance();
        aboutConfig.appName = mActivity.getString(R.string.app_name);
        aboutConfig.appIcon = R.mipmap.ic_launcher;
        aboutConfig.version = AppUtil.getApplicationVersion(mActivity);
        aboutConfig.author = mActivity.getString(R.string.app_author);
        aboutConfig.aboutLabelTitle = mActivity.getString(R.string.mal_title_about);
        aboutConfig.packageName = mActivity.getPackageName();
        aboutConfig.buildType = AboutConfig.BuildType.GOOGLE;

        aboutConfig.facebookUserName = mActivity.getString(R.string.app_publisher_facebook_id);
        aboutConfig.twitterUserName = mActivity.getString(R.string.app_publisher_twitter_id);
        aboutConfig.webHomePage = mActivity.getString(R.string.app_publisher_profile_website);

        // app publisher for "Try Other Apps" item
        aboutConfig.appPublisherId = mActivity.getString(R.string.app_publisher_id);

        // if pages are stored locally, then you need to override aboutConfig.dialog to be able use custom WebView
        aboutConfig.companyHtmlPath = mActivity.getString(R.string.app_publisher_company_html_path);
        aboutConfig.privacyHtmlPath = mActivity.getString(R.string.app_publisher_privacy_html_path);
        aboutConfig.acknowledgmentHtmlPath = mActivity.getString(R.string.app_publisher_acknowledgment_html_path);

        aboutConfig.dialog = new IDialog() {
            @Override
            public void open(AppCompatActivity appCompatActivity, String url, String tag) {
                // handle custom implementations of WebView. It will be called when user click to web items. (Example: "Privacy", "Acknowledgments" and "About")
            }
        };

        aboutConfig.analytics = new IAnalytic() {
            @Override
            public void logUiEvent(String s, String s1) {
                // handle log events.
            }

            @Override
            public void logException(Exception e, boolean b) {
                // handle exception events.
            }
        };
        // set it only if aboutConfig.analytics is defined.
        aboutConfig.logUiEventName = "Log";

        // Contact Support email details
        aboutConfig.emailAddress = mActivity.getString(R.string.app_author_email);
        aboutConfig.emailSubject = "[" + mActivity.getString(R.string.app_name) + "]" + "[" + AppUtil.getApplicationVersion(mActivity) + "]" + " " + mActivity.getString(R.string.app_contact_subject);
        aboutConfig.emailBody = mActivity.getString(R.string.app_contact_body);
    }
}