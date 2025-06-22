package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.DatosLibro;
import com.aluracursos.literalura.service.ConvierteDatos;
import com.aluracursos.literalura.service.CosumoAPI;

import java.util.Scanner;

public class Principal {
    private Scanner consola = new Scanner(System.in);
    private CosumoAPI consumoApi = new CosumoAPI();
    private final String URL_BASE = "https://gutendex.com/books";
    private ConvierteDatos conversor = new ConvierteDatos();
    



    public void muestraElMenu(){
        var opcion = -1;

        while (opcion != 0){
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por Idioma
                    0 - Salir
                    """;

            System.out.println(menu);
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
                    System.out.println("Cerrando la Aplicacion...");
                    System.out.println("...");
                    System.out.println("Hasta Luego!");
                    System.out.println("Fin del Programa.");
                    break;
                default:
                    System.out.println("Opcion Invalida");
            }

        }
    }

    private void listarLibrosPorIdioma() {
    }

    private void listarPorAutoresEnUnAnio() {
    }

    private void listarAutoresRegistrados() {
    }

    private void listarLibrosRegistrados() {
    }

    private void buscarLibroPorTitulo() {
    }

    public DatosLibro getDatosLibro(){
        System.out.println("Escriba el nombre del libro que desea buscar: ");
        var nombreLibro = consola.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        System.out.println(json);
        DatosLibro datos = conversor.obtenerDatos(json, DatosLibro.class);
        return datos;
    }
}


