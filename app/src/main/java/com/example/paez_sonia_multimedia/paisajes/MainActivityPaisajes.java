package com.example.paez_sonia_multimedia.paisajes;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.paez_sonia_multimedia.R;
import com.example.paez_sonia_multimedia.databinding.ActivityMainPaisajesBinding;
import com.example.paez_sonia_multimedia.paisajes.adapter.ImageAdapter;

import java.io.IOException;


public class MainActivityPaisajes extends AppCompatActivity implements View.OnClickListener{
  private ActivityMainPaisajesBinding paisajesBinding;
    private ViewPager viewPager;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_paisajes);
        paisajesBinding=ActivityMainPaisajesBinding.inflate(getLayoutInflater());
        View view= paisajesBinding.getRoot();
        setContentView(view);
        paisajesBinding.buttonpaisaje.setOnClickListener(this);
        paisajesBinding.buttonValorar.setOnClickListener(this);
        paisajesBinding.layoutsegundaria.setVisibility(View.INVISIBLE);
        paisajesBinding.textview.setVisibility(View.INVISIBLE);
        paisajesBinding.buttonValorar.setVisibility(View.INVISIBLE);


    }


    @Override
    public void onClick(View v) {

        if(v== paisajesBinding.buttonpaisaje){
            paisajesBinding.titulo.setVisibility(View.VISIBLE);
            Animation animacion = AnimationUtils.loadAnimation(this,R.anim.animacion);
            paisajesBinding.titulo.startAnimation(animacion);
            mp=MediaPlayer.create(this,R.raw.spokes);
            mp.start();
            new Handler().postDelayed(()->{
                paisajesBinding.titulo.clearAnimation();
                mp.stop();
              flipperImages();
            }, 5000);

        }
        if(v== paisajesBinding.buttonValorar){
            mp.stop();
            paisajesBinding.titulo.setVisibility(View.VISIBLE);
            paisajesBinding.buttonpaisaje.setVisibility(View.VISIBLE);
            paisajesBinding.layoutsegundaria.setVisibility(View.INVISIBLE);
            paisajesBinding.textview.setVisibility(View.INVISIBLE);
            paisajesBinding.valor.setVisibility(View.INVISIBLE);
            paisajesBinding.buttonValorar.setVisibility(View.INVISIBLE);

        }


    }

    private void flipperImages() {
        paisajesBinding.titulo.setVisibility(View.INVISIBLE);
        paisajesBinding.buttonpaisaje.setVisibility(View.INVISIBLE);
        paisajesBinding.layoutsegundaria.setVisibility(View.VISIBLE);
        paisajesBinding.textview.setVisibility(View.VISIBLE);
        paisajesBinding.valor.setVisibility(View.VISIBLE);
        paisajesBinding.buttonValorar.setVisibility(View.VISIBLE);
        ImageAdapter imageAdapter = new ImageAdapter(this);
        viewPager = findViewById(R.id.viewflipper);
        viewPager.setAdapter(imageAdapter);
        mp = MediaPlayer.create(getApplicationContext(), R.raw.icicles);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                mp.start();

            }

            @Override
            public void onPageSelected(int position) {

                Log.e("onPageSelected","aqui poner musica");

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mp.stop();
                try {
                    mp.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mp.start();

                Log.e("onPageScrollStateChan","aqui poner musica");
            }
        });


    }

}