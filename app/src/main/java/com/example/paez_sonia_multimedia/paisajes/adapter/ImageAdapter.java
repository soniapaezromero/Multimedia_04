package com.example.paez_sonia_multimedia.paisajes.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.paez_sonia_multimedia.R;

import java.io.InputStream;

import static java.lang.String.*;

public class ImageAdapter extends PagerAdapter {

    private TypedArray images;
    private LayoutInflater layoutInflater;
    MediaPlayer mp ;
    InputStream song;

    public ImageAdapter(Context context) {
        images = context.getResources().obtainTypedArray(R.array.images);
        song=context.getResources().openRawResource(R.raw.icicles);
        layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView = layoutInflater.inflate(R.layout.elemento_imagenes, container, false);

        ImageView image = (ImageView) itemView.findViewById(R.id.item_image);
        image.setImageDrawable(images.getDrawable(position));
        ((ViewGroup) image.getParent()).removeView(image);

        container.addView(image);
        return image;
    }

    @Override
    public int getCount() {
        return images.length();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
