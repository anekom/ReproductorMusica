package com.rm.reproductormusica;

/**
 * Created by 21543274 on 24/02/2017.
 */
public class Cancion {

    String [] canciones;

    public Cancion(){
        canciones = new String[8];

        canciones[0] = "kiss-i-was-made-for-love-in-you.mp3";
        canciones[1] = "Look at my Horse, my Horse is Amazing.mp3";
        canciones[2] = "MARILYN MANSON -- This Is Halloween.mp3";
        canciones[3] = "SwingLifeAway.mp3";
        canciones[4] = "System Of A Down - Toxicity.mp3";
        canciones[5] = "cartoon022.mp3";
        canciones[6] = "cartoon067.mp3";
        canciones[7] = "cartoon118.mp3";

    }

    public String[] getCanciones() {
        return canciones;
    }

    public void setCanciones(String[] canciones) {
        this.canciones = canciones;
    }
}
