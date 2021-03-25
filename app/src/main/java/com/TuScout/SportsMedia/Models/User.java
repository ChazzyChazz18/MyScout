package com.TuScout.SportsMedia.Models;

public class User {

    private int idusuario;
    private String nombre;
    private String correo;
    private String imagenPerfil;
    private String clave;
    private String apellido;
    private String edad;
    private String sexo;
    private String deporte;
    private String pais;
    private String peso;
    private String altura;
    private String posicion;
    private String tipousuario;

    public User() {
    }

    public User(int idusuario, String nombre, String correo, String clave, String apellido, String edad, String sexo, String deporte, String pais, String peso, String altura, String posicion, String tipousuario) {
        this.idusuario = idusuario;
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.apellido = apellido;
        this.edad = edad;
        this.sexo = sexo;
        this.deporte = deporte;
        this.pais = pais;
        this.peso = peso;
        this.altura = altura;
        this.posicion = posicion;
        this.tipousuario = tipousuario;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getTipousuario() {
        return tipousuario;
    }

    public void setTipousuario(String tipousuario) {
        this.tipousuario = tipousuario;
    }
}
