package com.jeancot.naves.repository;

import com.jeancot.naves.model.Nave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INaveRepository extends JpaRepository<Nave, Integer> {
    //SELECT * FROM Nave c WHERE %c.nombre%;
    List<Nave> findByNombreContainsIgnoreCase(String nombre);
}
