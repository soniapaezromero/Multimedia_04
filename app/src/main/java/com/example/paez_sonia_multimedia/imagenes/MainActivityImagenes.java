package com.example.paez_sonia_multimedia.imagenes;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.paez_sonia_multimedia.R;
import com.example.paez_sonia_multimedia.databinding.ActivityMainImagenesBinding;
import com.example.paez_sonia_multimedia.imagenes.adapter.ImagenesViewAdapter;
import com.example.paez_sonia_multimedia.imagenes.utilidades.Memoria;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivityImagenes extends AppCompatActivity {
    public  static final String ENLACE="https://soniapaezromero.me/fichero/imagenes.txt";
    private static final int REQUEST_WRITE = 1;
    private ActivityMainImagenesBinding imagenesBinding;
    public static String FICHERO = "errores.txt";
    public static String RUTA = null;
    public static String ERROR;
    public static String WEBS;
    public String[] urls;
    ImagenesViewAdapter viewAdapter;
    public List<String> listaURLs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_imagenes);
        imagenesBinding= ActivityMainImagenesBinding.inflate(getLayoutInflater());
        View view= imagenesBinding.getRoot();
        setContentView(view);
        setTitle("Imagenes");
       descargaOkHTTP(ENLACE);


    }

    //Menu tiene un boton de cerrar que nos lleva a la actividad principal
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cerrar:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public List<String> getListaURLs() {
        return listaURLs;
    }

    public void setListaURLs(List<String> listaURLs) {
        this.listaURLs = listaURLs;
    }

    private void descargaOkHTTP(String uri) {
        URL web = null;
        try {
            web = new URL(uri);
        } catch (MalformedURLException e) {
            mostrarError("Fallo: " + e.getMessage());
        }
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(web)
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
               ERROR=formatter.format(date)+ e.getMessage();
               Log.e("ERROR PRUEBA",ERROR);
               guardarError(ERROR);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
           
                try (ResponseBody responseBody = response.body()) {
                    if (response.isSuccessful()) {
                    String responseData= responseBody.string();

                   urls=responseData.split(",");
                    List<String> urlsLista= new ArrayList<>();
                    for (String s:urls){
                        urlsLista.add(s);
                    }
                       Handler uiHandler = new Handler(Looper.getMainLooper());
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                              cargarViewPager(urlsLista);
                            }
                        });
                    } else {
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                        Date date = new Date(System.currentTimeMillis());
                        ERROR=formatter.format(date) +"Unexpected code: " + response;
                        Log.e( "DESCARGADO",ERROR);
                        guardarError(ERROR);
                    }
                    }
                }
        });

    }
    public  List<String>  crearLista(String foto){
         List <String> lista= new ArrayList<>();
         lista.add(foto);
     return lista;
    }

    private void cargarViewPager(List<String> urls) {
        ViewPager viewPager= findViewById(R.id.view_pager);
        viewAdapter= new ImagenesViewAdapter(MainActivityImagenes.this,urls);
        viewPager.setAdapter(viewAdapter);
    }


    public void guardarError(String ERROR) {

        String permiso = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        // comprobar los permisos
        if (ActivityCompat.checkSelfPermission(this, permiso) != PackageManager.PERMISSION_GRANTED) {
            // pedir los permisos necesarios, porque no están concedidos
            ActivityCompat.requestPermissions(this, new String[]{permiso}, REQUEST_WRITE);
            // Cuando se cierre el cuadro de diálogo se ejecutará onRequestPermissionsResult
        } else {
            // Permisos ya concedidos
            escribirError(ERROR);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        String permiso = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        // chequeo los permisos de nuevo
        switch (requestCode) {
            case REQUEST_WRITE:
                if (ActivityCompat.checkSelfPermission(this, permiso) == PackageManager.PERMISSION_GRANTED) {
                    // permiso concedido
                    escribirError(ERROR);
                } else {
                    // no hay permiso
                    mostrarMensaje("No hay permiso para escribir en la memoria externa");
                }
                break;
        }
    }



    private void escribirError(String ERROR) {

            if(Memoria.disponibleEscritura()){
                try {
                    if (Memoria.escribirExterna(FICHERO, ERROR)){
                        mostrarMensaje("Fichero escrito con éxito.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    mostrarMensaje("Error en la escritura del fichero." + FICHERO + e.getMessage());

                }
            } else{

            }
        }

    private void mostrarError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void mostrarMensaje(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}




