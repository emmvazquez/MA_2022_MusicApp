package com.example.appmusic.entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;


public class Artista {


    private String  nombreGrupo;
    private String  descripcion;
    private Integer generoMusical;
    private String  correoGrupo;
    private String  rutaLogo;

    public Artista() {
        this.nombreGrupo = nombreGrupo;
        this.descripcion = descripcion;
        this.generoMusical = generoMusical;
        this.correoGrupo = correoGrupo;
        this.rutaLogo = rutaLogo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getGeneroMusical() {
        return generoMusical;
    }

    public void setGeneroMusical(Integer generoMusical) {
        this.generoMusical = generoMusical;
    }

    public String getCorreoGrupo() {
        return correoGrupo;
    }

    public void setCorreoGrupo(String correoGrupo) {
        this.correoGrupo = correoGrupo;
    }

    public String getRutaLogo() {
        return rutaLogo;
    }

    public void setRutaLogo(String rutaLogo) {
        this.rutaLogo = rutaLogo;
    }
}
