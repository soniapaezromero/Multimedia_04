package com.example.paez_sonia_multimedia.imagenes.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.paez_sonia_multimedia.imagenes.MainActivityImagenes;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ImagenesViewAdapter extends PagerAdapter {
    public  Context context;
    private List<String> imageUrl;
    MainActivityImagenes activityImagenes;

    public ImagenesViewAdapter(Context context, List<String>imageUrl) {
        this.context = context;
        this.imageUrl = imageUrl;
    }



    @Override
    public int getCount() {
        return imageUrl.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position){
            ImageView imageView = new ImageView(context);
            Picasso.get().load(imageUrl.get(position)).into(imageView);
            Log.e("IMGADTDES", imageUrl.get(position));
            container.addView(imageView);
            return imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
