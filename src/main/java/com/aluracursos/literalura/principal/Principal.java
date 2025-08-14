package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConvierteDatos;
import com.aluracursos.literalura.service.ConsumoAPI;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner consola = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private String json;
    private LibroRepository libroRepositorio;
    private AutorRepository autorRepositorio;
    private List<Libro> libros;
    private List<Autor> autores;
    private List<String> idiomas;

    public Principal(LibroRepository librorepositorio, AutorRepository autorRepositorio) {
        this.libroRepositorio = librorepositorio;
        this.autorRepositorio = autorRepositorio;
    }


    public void muestraElMenu(){
        var opcion = -1;

        while (opcion != 0){
            var menu = """
                    *************** 📖 LiterAlura 📖 ***************
                               << Catálogo de Libros >>
                    
                    1 - Buscar Libro por título
                    2 - Listar Libros registrados
                    3 - Listar Autores registrados
                    4 - Listar Autores vivos en un determinado año
                    5 - Listar Libros por Idioma
                    0 - Salir
                    """;

            System.out.println(menu);
            System.out.println("Escoge una opción: ");
            opcion = consola.nextInt();
            consola.nextLine();

            switch (opcion){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarPorAutoresEnUnAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la Aplicación...💻");
                    System.out.println("...");
                    System.out.println("Hasta Luego! 🖐️");
                    System.out.println("Fin del Programa. 📚");
                    break;
                default:
                    System.out.println("Opcion Invalida ⛔");
            }

        }
    }

    private void listarLibrosPorIdioma() {
        String buscarIdioma = "";
        System.out.println("Buscas libros en Español o en Inglés? ");
        var idiomaElegido = consola.nextLine().toLowerCase().trim();
        if(idiomaElegido.equals("español")){
            buscarIdioma = "es";
        }
        if (idiomaElegido.equals("ingles")){
            buscarIdioma = "en";
        }
        libros = libroRepositorio.findAllByIdioma(buscarIdioma);
        System.out.println(libros);
    }

    private void listarPorAutoresEnUnAnio() {
        System.out.println("Escribe el año elegido: ");
        var anioElegido = Integer.valueOf(consola.nextLine());
        autores = autorRepositorio.autoresEnUnDeterminadoAnio(anioElegido);
        System.out.println(autores);
    }

    private void listarAutoresRegistrados() {
        autores = autorRepositorio.findAll();
        System.out.println(autores);
    }

    private void listarLibrosRegistrados() {
        libros = libroRepositorio.findAll();
        System.out.println(libros);
    }

    private void buscarLibroPorTitulo() {
        DatosLibro datosLibro = getDatosLibro();
        if(datosLibro != null){
            Libro libro;
            DatosAutor datosAutor;
            if (!datosLibro.autor().isEmpty()) {
                datosAutor = datosLibro.autor().getFirst();
            } else {
                datosAutor = new DatosAutor("Anonimo", "sin registrar", "sin registrar");
            }

            Autor autorExistente = autorRepositorio.findByNombre(datosAutor.nombre());
            if(autorExistente != null){
                libro = new Libro(datosLibro, autorExistente);
            } else {
                Autor nuevoAutor = new Autor(datosAutor);
                libro = new Libro(datosLibro, nuevoAutor);
                autorRepositorio.save(nuevoAutor);
            }
            try{
                libroRepositorio.save(libro);
                System.out.println(libro);
            } catch (Exception e){
                System.out.println("No puedes registrar un mismo libro mas de una vez‼️");
            }

        }else{
            System.out.println("No hemos encontrado el libro en la API. 😓");

        }

    }

    public DatosLibro getDatosLibro(){
        System.out.println("Escriba el nombre del libro que desea buscar: ");
        var nombreLibro = consola.nextLine();
        json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        // System.out.println(json);
        DatosResult datosBusqueda = conversor.obtenerDatos(json, DatosResult.class);
        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();

        if(libroBuscado.isPresent()){
            return libroBuscado.get();
        } else {
            return null;
        }
    }
}


