package com.aluracursos.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name="autores")

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @Column(unique = true)
    private String nombre;
    private String fechaDeNacimiento;
    private String fechaDeFallecimiento;

    public Autor() {
    }

    public Autor (DatosAutor datos){
        this.nombre = datos.nombre();
        this.fechaDeNacimiento = datos.fechaDeNacimiento();
        this.fechaDeFallecimiento = datos.fechaDeFallecimiento();
    }

    @Override
    public String toString() {
        return "Autor{" +
                "nombre='" + nombre + '\'' +
                ", fechaDeNacimiento='" + fechaDeNacimiento + '\'' +
                ", fechaDeFallecimiento='" + fechaDeFallecimiento + '\'' +
                '}';//agregar los libros del autor registrados
    }
}
