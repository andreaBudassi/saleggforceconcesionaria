package com.saleggforce.egg.repositorios;

import com.saleggforce.egg.entidades.Empleado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepositorio extends JpaRepository<Empleado, String> {

    @Query("SELECT u FROM Empleado u WHERE u.usuario = :usuario")
    public Empleado findByUsuario(@Param("usuario") String usuario);
}
