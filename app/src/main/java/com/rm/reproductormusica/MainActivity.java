package com.rm.reproductormusica;

import android.app.ListActivity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends ListActivity{

    final int version = Build.VERSION.SDK_INT;

    AssetManager assetManager;
    MediaPlayer mediaPlayer = new MediaPlayer();
    AssetFileDescriptor descriptor;

    Button pause, stop, next;

    int length = 0;
    Cancion cancion = new Cancion();
    boolean reproduciendo = false;
    int posicion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next = (Button) findViewById(R.id.next);
        pause = (Button) findViewById(R.id.pause);
        stop = (Button) findViewById(R.id.stop);

        pause.setClickable(false);
        next.setClickable(false);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, cancion.getCanciones()); // con el 1 o el 2 cambia el formato
        setListAdapter(adapter);

        assetManager = this.getAssets();

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);  // control volumen


    }



    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


        if(mediaPlayer.isPlaying()){
            length = 0;
            mediaPlayer.stop();
        }

        cargarCancion(cancion.getCanciones()[position]);
        mediaPlayer.start();

        pause.setClickable(true);
        next.setClickable(true);
        reproduciendo = true;

        posicion = position;

        escucha();

    }

    private void escucha (){
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                //Toast.makeText(MainActivity.this, "Entra en el complete", Toast.LENGTH_LONG).show();
                continua();
            }
        });
    }

    private void continua (){

        posicion++;
        if(posicion >= cancion.getCanciones().length){

            posicion = 0;
            length = 0;

            mediaPlayer.stop();

            //Toast.makeText(MainActivity.this, "Lista de reproduccion completada", Toast.LENGTH_LONG).show();

        }else{

            cargarCancion(cancion.getCanciones()[posicion]);
            mediaPlayer.start();
            //Toast.makeText(MainActivity.this, "Continuamos", Toast.LENGTH_LONG).show();
            escucha();
        }



    }


    private void cargarCancion(String cancion) {


        mediaPlayer = new MediaPlayer();

        try {
            descriptor = assetManager.openFd(cancion);

            mediaPlayer.setDataSource(
                    descriptor.getFileDescriptor(),
                    descriptor.getStartOffset(),
                    descriptor.getLength());

            mediaPlayer.prepare();


        } catch (IOException e) {
            e.printStackTrace();
        }




    }


    public void next (View view){

        if(mediaPlayer.isPlaying()){
            length = 0;
            mediaPlayer.stop();
        }

        Log.d("length", cancion.getCanciones().length+"");
        Log.d("posicion", posicion+"");
        posicion++;
        Log.d("posicion++", posicion+"");

        if(posicion>cancion.getCanciones().length-1){
            posicion = 0;
            cargarCancion(cancion.getCanciones()[posicion]);
            mediaPlayer.start();
        }else{
            cargarCancion(cancion.getCanciones()[posicion]);
            mediaPlayer.start();
        }



    }


    public void pause (View view){

        Log.d("reproduciendo:", reproduciendo+"");

        if(reproduciendo == false){

            length = mediaPlayer.getCurrentPosition();

            Log.d("length:", length+"");

            mediaPlayer.seekTo(length);
            mediaPlayer.start();

            reproduciendo = true;

            pause.setBackgroundDrawable(getResources().getDrawable(R.drawable.pause_button));


        }else if(reproduciendo == true){

            mediaPlayer.pause();

            reproduciendo = false;

            pause.setBackgroundDrawable(getResources().getDrawable(R.drawable.play_button));

        }

    }



    public void stop (View view){
        length = 0;
        mediaPlayer.stop();

        pause.setBackgroundDrawable(getResources().getDrawable(R.drawable.pause_button));

        pause.setClickable(false);
        next.setClickable(false);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.release(); // libera recursos
    }


}
