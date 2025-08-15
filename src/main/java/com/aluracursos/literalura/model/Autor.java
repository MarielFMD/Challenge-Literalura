package com.aluracursos.literalura.model;

public class Autor {
    String nombre;
    String fechaDeNacimiento;
    String fechaDeFallecimiento;

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
