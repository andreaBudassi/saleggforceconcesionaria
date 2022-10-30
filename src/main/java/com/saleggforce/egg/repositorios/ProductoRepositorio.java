package com.saleggforce.egg.repositorios;

import com.saleggforce.egg.entidades.Producto;
import com.saleggforce.egg.enums.Marca;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, String> {

    @Query("SELECT p FROM Producto p WHERE p.modelo LIKE %:modelo%")
    public List<Producto> findByModelo(@Param("modelo") String modelo);

    @Query("SELECT p FROM Producto p WHERE p.marca LIKE %:marca%")
    public List<Producto> findByMarca(@Param("marca") Marca marca);

    @Query("SELECT a FROM Producto a WHERE a.alta = true ORDER BY a.modelo")
    public List<Producto> findByActivo();

}
