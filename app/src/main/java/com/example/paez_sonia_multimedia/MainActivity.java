package com.example.paez_sonia_multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.paez_sonia_multimedia.imagenes.MainActivityImagenes;
import com.example.paez_sonia_multimedia.paisajes.MainActivityPaisajes;

public class MainActivity extends AppCompatActivity {
    Button paisajes;
    Button imagenes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paisajes=findViewById(R.id.btpaisajes);
        paisajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento= new Intent(MainActivity.this, MainActivityPaisajes.class);
                startActivity(intento);
            }
        });
        imagenes=findViewById(R.id.btimagenes);
        imagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, MainActivityImagenes.class);
                startActivity(intent);
            }
        });

    }
}