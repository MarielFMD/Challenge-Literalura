package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name="libros")

public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private long Id;
    @Column(unique = true)//corresponde al atributo titulo
    private String titulo;
    //private String autor;
    private String idioma;
    private double numeroDeDescargas;

    public Libro() {
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro (DatosLibro datos){
        this.titulo = datos.titulo();

        this.idioma = Optional.ofNullable(datos.idiomas())
                .filter(i -> !i.isEmpty())
                .map(i -> i.getFirst())
                .orElse("Sin datos de idioma");

        this.numeroDeDescargas = Optional.ofNullable(datos.numeroDeDescargas())
                .orElse((double) 0);
    }
    public String getTitulo() {
        return titulo;
    }

    public String toString() {
        return  "------------------------------\n"+
                "             LIBRO            \n" +
                "------------------------------\n" +
                "Titulo: " + titulo + "\n" +
                "Autor: " + (autor != null ? autor.getNombre() : "Desconocido") + "\n" +
                "Idioma: " + idioma + "\n" +
                "N° de descargas: " + numeroDeDescargas+"\n"+
                "******************************"+"\n";
    }

}
