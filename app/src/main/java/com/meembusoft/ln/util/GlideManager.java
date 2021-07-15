package com.meembusoft.ln.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;

public class GlideManager {

    public static void setImage(Context context, ImageView imageView, @DrawableRes int resource, boolean applyCircle) {
        RequestBuilder glide = Glide.with(context).load(resource);
        if (applyCircle) {
            glide.apply(RequestOptions.circleCropTransform()).into(imageView);
        } else {
            glide.into(imageView);
        }
    }

    public static void setImage(Context context, ImageView imageView, Uri uri, boolean applyCircle) {
        RequestBuilder glide = Glide.with(context).load(uri);
        if (applyCircle) {
            glide.apply(RequestOptions.circleCropTransform()).into(imageView);
        } else {
            glide.into(imageView);
        }
    }

    public static void setImage(Context context, ImageView imageView, String url, boolean applyCircle) {
        RequestBuilder glide = Glide.with(context).load(url);
        if (applyCircle) {
            glide.apply(RequestOptions.circleCropTransform()).into(imageView);
        } else {
            glide.into(imageView);
        }
    }
}
