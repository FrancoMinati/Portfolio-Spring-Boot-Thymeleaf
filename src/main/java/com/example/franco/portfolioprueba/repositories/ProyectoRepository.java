package com.example.franco.portfolioprueba.repositories;

import com.example.franco.portfolioprueba.entitites.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    @Query(value = "SELECT * FROM proyecto WHERE proyecto.estado = %ACTIVO%",nativeQuery = true)
    List<Proyecto> getAllByActive();
    @Query(value = "SELECT * FROM proyecto WHERE proyecto.estado = %ACTIVO% AND proyecto.nombre LIKE %:nombre%",nativeQuery = true)
    Optional<Proyecto> getByNombreAndActivo(@Param("nombre") String nombre);

    @Query(value = "SELECT * FROM proyecto WHERE proyecto.id=(SELECT max(proyecto.id)FROM proyecto)",nativeQuery = true)
    Optional<Proyecto> getByLastId();
}
