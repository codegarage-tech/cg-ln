package com.meembusoft.ln.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.meembusoft.ln.R;
import com.meembusoft.ln.model.colormatchtab.Category;

public class AppUtil {

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
}