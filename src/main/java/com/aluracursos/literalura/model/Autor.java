package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="autores")

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @Column(unique = true)
    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeFallecimiento;

    public Autor() {
    }

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Libro> librosDelAutor = new ArrayList<>();

    public void agregarLibro(Libro libro) {
        librosDelAutor.add(libro);
        libro.setAutor(this); // importante para mantener la relación bidireccional
    }

    public Autor (DatosAutor datos){
        this.nombre = datos.nombre();
        this.fechaDeNacimiento = Integer.parseInt(datos.fechaDeNacimiento());
        this.fechaDeFallecimiento = Integer.parseInt(datos.fechaDeFallecimiento());
    }

    @Override
    public String toString() {
        return  "\n"+"===============================" + "\n" +
                "Nombre: " + nombre + "\n" +
                "Fecha De Nacimiento: " + (fechaDeNacimiento != null ? fechaDeNacimiento
                                            : "No disponible") + "\n" +
                "Fecha De Fallecimiento: " + (fechaDeFallecimiento != null ? fechaDeFallecimiento
                                                : "No disponible") + "\n" +
                "Libros del autor: " + librosDelAutor.stream()
                                         .map(Libro::getTitulo)
                                            .toList() + "\n" +
                "===============================" + "\n";
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public Integer getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }
}
