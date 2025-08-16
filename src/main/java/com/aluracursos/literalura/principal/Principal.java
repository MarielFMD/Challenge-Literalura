package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books";
    private final LibroRepository libroRepositorio;//revisar por que me hizo agregar esto intellij
    private final AutorRepository autorRepositorio;
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private String json;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepositorio = libroRepository;
        this.autorRepositorio = autorRepository;
    }


    public void mostrarDatosDesdeApi() {

        /*json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);
        */


        while (true) {
            mostrarMenu();
            System.out.println("Ingrese el número de opción deseado: ");
            var opcionUsuario = teclado.nextLine();

            switch (opcionUsuario) {
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
                    System.out.println("Error. Ingrese una opción válida.\n");
                    System.out.println();
                    continue;

            }
        }

    }

    private void listarPorIdioma() {
        while (true) {
            System.out.println("""
        +-------------------------------------+
        |     SELECCIONE EL IDIOMA A BUSCAR  |
        +-------------------------------------+
        | 1 -> Inglés                          |
        | 2 -> Italiano                        |
        | 3 -> Español                          |
        | 4 -> Francés                          |
        | 5 -> Portugués                        |
        | 0 -> Volver al menú principal        |
        +-------------------------------------+
        """);

            String opcion = teclado.nextLine().trim();
            String idiomaCodigo = "";
            String idiomaNombre = "";

            switch (opcion) {
                case "1" -> {
                    idiomaCodigo = "en";
                    idiomaNombre = "Inglés";
                }
                case "2" -> {
                    idiomaCodigo = "it";
                    idiomaNombre = "Italiano";
                }
                case "3" -> {
                    idiomaCodigo = "es";
                    idiomaNombre = "Español";
                }
                case "4" -> {
                    idiomaCodigo = "fr";
                    idiomaNombre = "Francés";
                }
                case "5" -> {
                    idiomaCodigo = "pt";
                    idiomaNombre = "Portugués";
                }
                case "0" -> {
                    System.out.println("Volviendo al menú principal...\n");
                    return;
                }
                default -> {
                    System.out.println("Opción inválida. Intente nuevamente.\n");
                    continue;
                }
            }

            // Buscar libros
            List<Libro> libros = libroRepositorio.findByIdiomaIgnoreCase(idiomaCodigo);
            if (libros.isEmpty()) {
                System.out.println("No hay libros registrados en " + idiomaNombre + ".\n");
            } else {
                System.out.println("Se encontraron " + libros.size() + " libros en " + idiomaNombre + ":\n");
                libros.forEach(System.out::println);
            }
        }

    }

    private void listarAutoresVivos() {
        try {
            System.out.println("Ingrese un año: \n");
            int anio = teclado.nextInt();
            teclado.nextLine();

            List<Autor> vivos = autorRepositorio
                    .findByFechaDeNacimientoLessThanEqualAndFechaDeFallecimientoIsNullOrFechaDeFallecimientoGreaterThanEqual(anio, anio);

            if (vivos.isEmpty()) {
                System.out.println("No hay autores vivos en el año " + anio + "\n");
            } else {
                System.out.println("Autores vivos en " + anio + ":\n");
                vivos.forEach(System.out::println);
            }
        }
        catch(InputMismatchException e) {
            System.out.println("Error: Debe ingresar un número válido para el año.");
            teclado.nextLine();
        } catch (Exception e) {
            System.out.println("Ocurrió un error al listar los autores: " + e.getMessage());
        }
    }


    private void listarAutores() {
        List<Autor> autoresRegistrados = autorRepositorio.findAll();
        if(autoresRegistrados.isEmpty()){
            System.out.println("No hay ningún libro registrado.\n");
        }else {
            System.out.println(autoresRegistrados);
        }

    }

    private void listarLibros() {
        List<Libro> librosRegistrados = libroRepositorio.findAll();
        if(librosRegistrados.isEmpty()){
            System.out.println("No hay ningún autor registrado.\n");
        }else {
            System.out.println(librosRegistrados);
        }
    }

    private void mostrarMenu() {
        System.out.println("""
            +-------------------------------+
            |        MENÚ DE OPCIONES       |
            +-------------------------------+
            | 1 - Buscar libro por título   |
            | 2 - Listar libros registrados|
            | 3 - Listar autores registrados|
            | 4 - Listar autores vivos en un año |
            | 5 - Listar libros por idioma |
            | 6 - Salir                     |
            +-------------------------------+
            """);
    }


    private void buscarPorTitulo() {
        try {
            System.out.println("Ingrese el título del libro: ");
            var tituloLibro = teclado.nextLine();
            teclado.nextLine();
            if (tituloLibro.isEmpty()) {
                System.out.println("Error: ingrese un título.");
                return;
            }
            var libroEncontrado = libroRepositorio.findByTituloContainingIgnoreCase(tituloLibro);
            if (libroEncontrado != null) {
                System.out.println("El libro ya existe en la base de datos.\n");
            } else {
                var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
                var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
                Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                        .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                        .findFirst();

                //guarda libro nuevo en el repositorio
                if (libroBuscado.isPresent()) {

                    System.out.println("Libro encontrado.\n");
                    Libro libro = new Libro(libroBuscado.get());
                    //si el autor ya está en la base de datos
                    if (libroBuscado.get().autor() != null && !libroBuscado.get().autor().isEmpty()) {
                        DatosAutor datosAutor = libroBuscado.get().autor().get(0);
                        //busca autor en la base de datos
                        Autor autor = autorRepositorio.findByNombre(datosAutor.nombre())
                                .orElseGet(() -> {
                                    Autor aNuevo = new Autor(datosAutor);
                                    autorRepositorio.save(aNuevo);
                                    return aNuevo;
                                });

                        libro.setAutor(autor);
                        autor.agregarLibro(libro);
                        autorRepositorio.save(autor);
                    } else {

                        libroRepositorio.save(libro);
                    }
                    System.out.println(libro);

                } else {
                    System.out.println("Libro no encontrado.\n");
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error al conectar con la API: " + e.getMessage());
        } catch (DataAccessException e) {
            System.out.println("Error al acceder a la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        }
}}
