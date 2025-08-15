package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.Datos;
import com.aluracursos.literalura.model.DatosLibro;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books";
    private final LibroRepository repositorio;//revisar por que me hizo agregar esto intellij
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private String json;

    public Principal(LibroRepository libroRepository) {
        this.repositorio = libroRepository;
    }


    public void mostrarDatosDesdeApi() {

        /*json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);
        */


        while(true){
            mostrarMenu();
            System.out.println("Ingrese el número de opción deseado: ");
            var opcionUsuario = teclado.nextLine();

            switch(opcionUsuario) {
                case "1":
                    buscarPorTitulo();
                    break;
                case "2":
                    listarLibros();
                    break;
                case "3":
                    listarAutores();
                    break;
                case "4":
                    listarAutoresVivos();
                    break;
                case "5":
                    listarPorIdioma();
                    break;
                case "6":
                    System.out.println("Saliendo del sistema. Gracias por utilizar nuestro servicio.");
                    return;
                default:
                    System.out.println("Error. Ingrese una opción válida");
                    System.out.println();
                    continue;

    }   }

    }

    private void listarPorIdioma() {
    }

    private void listarAutoresVivos() {
    }

    private void listarAutores() {
    }

    private void listarLibros() {
    }

    private void mostrarMenu() {
        System.out.println("""
                opciones:
                1-buscar libro por titulo
                2-listar libros registrados
                3-listar autores registrados
                4-listar autores vivos en un año
                5-listar libros por idioma
                6-salir
                """);
    }

    //Busqueda por titulo
    private void buscarPorTitulo() {
        System.out.println("Ingrese el título del libro: ");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()) {

            System.out.println("Libro encontrado");
            Libro libro = new Libro(libroBuscado.get());
            System.out.println(libro);
            //guardar libro en base de datos
            repositorio.save(libro);
        } else {
            System.out.println("Libro no encontrado");
        }
    }
}
