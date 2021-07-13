package com.meembusoft.ln.util;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.meembusoft.addtocart.AddToCartManager;
import com.meembusoft.addtocart.model.CartItem;
import com.meembusoft.animationmanager.flytocart.CircleAnimationUtil;
import com.meembusoft.ln.R;
import com.meembusoft.ln.activity.CartActivity;
import com.meembusoft.ln.model.Category;

public class AppUtil {

    private static final String TAG = "AppUtil";

    public static String getColor(Context context, @ColorRes int colorRes) {
        return String.format("#%08x", ContextCompat.getColor(context, colorRes) & 0xffffffff);
    }

    public static View getTabView(Context context, Category category) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.item_name);
        TextView textView = v.findViewById(R.id.item_count);
        if (category.getNewItemCount() > 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(category.getNewItemCount() + "");
        } else {
            textView.setVisibility(View.GONE);
        }

        tv.setText(category.getName());
        ImageView img = (ImageView) v.findViewById(R.id.item_icon);

        AppUtil.loadImage(context, img, category.getImage(), false, false, false);

//        if (!category.getName().equalsIgnoreCase("Grocery")) {
//            img.setImageAlpha(0x3F);
//            tv.setTextColor(context.getResources().getColor(R.color.gray_1));
//            textView.setBackgroundResource(R.drawable.circular_badge_grey);
//        }
        return v;
    }

    public static <T> void loadImage(Context context, ImageView imageView, T imageSource,
                                     boolean isGif, boolean isRoundedImage, boolean isPlaceHolder) {
        try {
            RequestManager requestManager = Glide.with(context);
            RequestBuilder requestBuilder = isGif ? requestManager.asGif() : requestManager.asBitmap();

            //Dynamic loading without caching while update need for each time loading
//            requestOptions.signature(new ObjectKey(System.currentTimeMillis()));
            //If Cache needed
//            requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

            //If Cache needed
            RequestOptions requestOptionsCache = new RequestOptions();
            requestOptionsCache.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            requestBuilder.apply(requestOptionsCache);

            //For placeholder
            if (isPlaceHolder) {
                RequestOptions requestOptionsPlaceHolder = new RequestOptions();
                requestOptionsPlaceHolder.placeholder(R.mipmap.ic_launcher);
                requestBuilder.apply(requestOptionsPlaceHolder);
            }

            //For error
            RequestOptions requestOptionsError = new RequestOptions();
            requestOptionsError.error(R.mipmap.ic_launcher);
            requestBuilder.apply(requestOptionsError);

            //For rounded image
            if (isRoundedImage) {
                RequestOptions requestOptionsRounded = new RequestOptions();
                requestOptionsRounded.circleCrop();
                requestOptionsRounded.autoClone();
                requestBuilder.apply(requestOptionsRounded);
            }

            //Generic image source
            T mImageSource = null;
            if (imageSource instanceof String) {
                if (!TextUtils.isEmpty((String) imageSource)) {
                    mImageSource = imageSource;
                }
            } else if (imageSource instanceof Integer) {
                mImageSource = imageSource;
            }
            requestBuilder.load((mImageSource != null) ? mImageSource : R.mipmap.ic_launcher);

            //Load into image view
            requestBuilder.into(imageView);

//            Glide
//                    .with(context)
//                    .asBitmap()
//                    .load((mImageSource != null) ? mImageSource : R.mipmap.ic_launcher)
//                    .apply(requestOptions)
//                    .into(imageView);

        } catch (Exception e) {
        }
    }

    public static void makeFlyAnimation(Activity activity, View sourceView, Bitmap sourceViewBitmap, View destinationView, int timeMilliSecond, Animator.AnimatorListener animatorListener) {

        //Create a copy view
        ImageView animImg = new ImageView(activity);
//        Bitmap bm = ((BitmapDrawable) sourceView.getDrawable()).getBitmap();
        animImg.setImageBitmap(sourceViewBitmap);

        ViewGroup anim_mask_layout = com.meembusoft.animationmanager.flytocart.CircleAnimationUtil.createAnimLayout(activity);
        anim_mask_layout.addView(animImg);

        int[] startXY = new int[2];
        sourceView.getLocationInWindow(startXY);
        final View v = com.meembusoft.animationmanager.flytocart.CircleAnimationUtil.addViewToAnimLayout(activity, animImg, startXY, true);
        if (v == null) {
            return;
        }

        new CircleAnimationUtil().attachActivity(activity)
                .setTargetView(sourceView)
                .setTargetViewCopy(v)
                .setMoveDuration(timeMilliSecond)
                .setDestView(destinationView)
                .setAnimationListener(animatorListener).startAnimation();
    }

    public static <T extends View> void applyViewTint(T view, @ColorRes int colorResource) {
        if (view != null) {
            if (view instanceof ImageView) {
                ((ImageView) view).setColorFilter(ContextCompat.getColor(view.getContext(), colorResource));
            } else {
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP && view instanceof AppCompatButton) {
                    ((AppCompatButton) view).setSupportBackgroundTintList(ContextCompat.getColorStateList(view.getContext().getApplicationContext(), colorResource));
                } else {
                    ViewCompat.setBackgroundTintList(view, ContextCompat.getColorStateList(view.getContext().getApplicationContext(), colorResource));
                }
            }
        }
    }

    public static void navigateToCartScreen(Activity activity) {
        if (AddToCartManager.getInstance().hasCartItem(CartItem.class)) {
            Intent intentCart = new Intent(activity, CartActivity.class);
            activity.startActivity(intentCart);
        } else {
            Toast.makeText(activity, activity.getString(R.string.txt_there_is_no_item_into_cart), Toast.LENGTH_SHORT).show();
        }
    }

    public static void applyStrike(TextView textView, boolean isStrike) {
        if (isStrike) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    public static void applyMarqueeOnTextView(TextView textView) {
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setSingleLine(true);
        textView.setMarqueeRepeatLimit(-1);
        textView.setSelected(true);
    }

    public static void generateQRCode(String text, ImageView imageView, int width, int height) {
        try {
            if (!TextUtils.isEmpty(text)) {
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.encodeBitmap(text, BarcodeFormat.QR_CODE, width, height);
                imageView.setImageBitmap(bitmap);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getPlayStoreUrl(Activity activity) {
        return "https://play.google.com/store/apps/details?id=" + activity.getPackageName();
    }


    /**
     * Get application version.
     *
     * @param context only the application context.
     * @return String the value in string is the application's version name.
     */
    public static String getApplicationVersion(Context context) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return (pInfo != null ? pInfo.versionName : "(unknown)");
    }

    /**
     * Get application name.
     *
     * @param context only the application context.
     * @return String the value in string is the application's name.
     */
    public static String getApplicationName(Context context) {
        final PackageManager pm = context.getPackageManager();
        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo(context.getPackageName(), 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        return (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
    }
}