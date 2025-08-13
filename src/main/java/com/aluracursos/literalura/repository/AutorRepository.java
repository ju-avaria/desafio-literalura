package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Autor findByNombre(String nombre);



    @Query(value = "SELECT * FROM autores WHERE :anioElegido BETWEEN CAST(fecha_nacimiento AS INTEGER) AND CAST(fecha_fallecimiento AS INTEGER)", nativeQuery = true)
    List<Autor> autoresEnUnDeterminadoAnio(Integer anioElegido);
}
