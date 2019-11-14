package com.nav.tagger.view;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nav.tagger.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.veinhorn.scrollgalleryview.loader.MediaLoader;

/**
 * Created by Fujitsu on 26-06-2018.
 */

public class PicassoImageLoader implements MediaLoader {
    private String url;
    private Integer thumbnailWidth;
    private Integer thumbnailHeight;

    public PicassoImageLoader(String url) {
        this.url = url;
    }

    public PicassoImageLoader(String url, Integer thumbnailWidth, Integer thumbnailHeight) {
        this.url = url;
        this.thumbnailWidth = thumbnailWidth;
        this.thumbnailHeight = thumbnailHeight;
    }

    @Override
    public boolean isImage() {
        return true;
    }

    @Override
    public void loadMedia(Context context, final ImageView imageView, final MediaLoader.SuccessCallback callback) {
/*
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder_image)
                .into(imageView, new ImageCallback(callback));
*/
        try {
           // imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).load(url)
                    .apply(new RequestOptions().override(640, 840))
                    .into(imageView);

        } catch (Exception e) {
        }


    }

    @Override
    public void loadThumbnail(Context context, final ImageView thumbnailView, final MediaLoader.SuccessCallback callback) {
       /* Picasso.with(context)
                .load(url)
                .resize(thumbnailWidth == null ? 100 : thumbnailWidth,
                        thumbnailHeight == null ? 100 : thumbnailHeight)
                .placeholder(R.drawable.placeholder_image)
                .centerInside()
                .into(thumbnailView, new ImageCallback(callback));*/
        Glide.with(context).load(url)
                .into(thumbnailView);


    }

    private static class ImageCallback implements Callback {
        private final MediaLoader.SuccessCallback callback;

        public ImageCallback(MediaLoader.SuccessCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess() {
            callback.onSuccess();
        }

        @Override
        public void onError() {

        }
    }
}

// compile 'com.veinhorn.scrollgalleryview:library:1.0.8'
// compile "com.squareup.picasso:picasso:2.4.0" //picasso