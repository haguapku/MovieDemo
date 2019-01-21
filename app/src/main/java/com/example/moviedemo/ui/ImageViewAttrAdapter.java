package com.example.moviedemo.ui;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * Author: created by MarkYoung on 18/01/2019 13:02
 */
public class ImageViewAttrAdapter {

    @BindingAdapter({"imageUrl", "placeHolder", "errorImage"})
    public static void loadImage(final ImageView imageView, String url, Drawable holderDrawable, Drawable errorDrawable){
        Glide.with(imageView.getContext())
                .load("https://image.tmdb.org/t/p/w200"+url)
                .apply(new RequestOptions().placeholder(holderDrawable))
                .apply(new RequestOptions().error(errorDrawable))
                .apply(new RequestOptions().centerCrop().transforms(new CenterCrop(),new RoundedCorners(15)))
                .apply(new RequestOptions().override(200,200))
                .into(imageView);
    }
}
