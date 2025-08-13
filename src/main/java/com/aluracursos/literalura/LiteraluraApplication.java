package com.aluracursos.literalura;

import com.aluracursos.literalura.model.DatosLibro;
import com.aluracursos.literalura.model.DatosResult;
import com.aluracursos.literalura.principal.Principal;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository librorepositorio;
	@Autowired
	private AutorRepository autorRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(librorepositorio, autorRepositorio);
		principal.muestraElMenu();
//		ConsumoAPI consumoAPI = new ConsumoAPI();
//		var json = consumoAPI.obtenerDatos("https://gutendex.com/books/?search=frankenstein");
//		System.out.println(json);
//
//		ConvierteDatos conversor = new ConvierteDatos();
//		var datosResultados = conversor.obtenerDatos(json, DatosResult.class);
//		System.out.println(datosResultados);
//
//		var nombreLibro = "frankestein";
//		Optional<DatosLibro> datosLibro = datosResultados.resultados().stream()
//				.filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
//				.findFirst();

	}
}
