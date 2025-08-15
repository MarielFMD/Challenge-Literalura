package com.aluracursos.literalura.model;

import java.util.Optional;

public class Libro {
    private String titulo;
    private String autor;
    private String idioma;
    private double numeroDeDescargas;

    public Libro (DatosLibro datos){
        this.titulo = datos.titulo();
        this.autor = Optional.ofNullable(datos.autor())
                .filter(a -> !a.isEmpty())
                .map(a -> a.get(0).nombre())
                .orElse("Desconocido");
        this.idioma = Optional.ofNullable(datos.idiomas())
                .filter(i -> !i.isEmpty())
                .map(i -> i.get(0))
                .orElse("Sin datos de idioma");
        this.numeroDeDescargas = Optional.ofNullable(datos.numeroDeDescargas())
                .orElse((double) 0);
    }

    public String toString() {
        return "------------ LIBRO -----------\n" +
                "Titulo: " + titulo + "\n" +
                "Autor: " + autor + "\n" +
                "Idioma: " + idioma + "\n" +
                "N° de descargas: " + numeroDeDescargas+"\n"+
                "******************************"+"\n";
    }
}
