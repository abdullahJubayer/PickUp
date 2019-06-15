package com.aj.pickup.Model;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;

public class ImageDownloader implements Target {

    private Context context;
    private WeakReference<ContentResolver> resolverWeakReference;
    private String name,desc;

    public ImageDownloader(Context context, ContentResolver resolverWeakReference, String name, String desc) {
        this.context = context;
        this.resolverWeakReference=new WeakReference<ContentResolver>(resolverWeakReference);
        this.name = name;
        this.desc = desc;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ContentResolver resolver=resolverWeakReference.get();
        if (resolver != null){
            MediaStore.Images.Media.insertImage(resolver,bitmap,name,desc);

            Intent intent=new Intent() ;
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            context.startActivity(Intent.createChooser(intent,"View Downloads"));
        }
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
