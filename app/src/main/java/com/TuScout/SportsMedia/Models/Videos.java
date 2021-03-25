package com.TuScout.SportsMedia.Models;

public class Videos {

    private int id;
    private String nombre;
    private String descripcion;
    private String videoURL;
    private String categoria;
    private String rating;
    private String fecha;

    public Videos () {

    }

    public Videos (String nombre, String descripcion, String videoURL) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.videoURL = videoURL;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
