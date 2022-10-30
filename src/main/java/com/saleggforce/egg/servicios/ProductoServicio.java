package com.saleggforce.egg.servicios;

import com.saleggforce.egg.entidades.Producto;
import com.saleggforce.egg.enums.Marca;
import com.saleggforce.egg.errores.ErrorServicio;
import com.saleggforce.egg.repositorios.ProductoRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServicio {

    private final ProductoRepositorio productoRepositorio;

    @Autowired
    public ProductoServicio(ProductoRepositorio productoRepositorio) {
        this.productoRepositorio = productoRepositorio;
    }

    @Transactional
    public void crearProducto(Producto producto) throws ErrorServicio {
        validarProducto(producto);
        if (producto.getAlta() == null) {
            producto.setAlta(Boolean.TRUE);
        }
        productoRepositorio.save(producto);
    }

    @Transactional
    public void modificarProducto(String id, String fichaTecnica, String foto, Integer precio) throws ErrorServicio {

        Optional<Producto> respuesta = productoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Producto producto = respuesta.get();

            producto.setFichaTecnica(fichaTecnica);
            producto.setPrecio(precio);
            producto.setFoto(foto);

            validarProducto(producto);
            productoRepositorio.save(producto);
        } else {
            throw new ErrorServicio("No se encontro el producto.");
        }
    }

    @Transactional
    public void altaProducto(String id) {
        Optional<Producto> respuesta = productoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Producto producto = respuesta.get();

            producto.setAlta(Boolean.TRUE);
            productoRepositorio.save(producto);
        }
    }

    @Transactional
    public void bajaProducto(String id) {
        Optional< Producto> respuesta = productoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Producto producto = respuesta.get();
            producto.setAlta(Boolean.FALSE);
            productoRepositorio.save(producto);
        }
    }

    @Transactional
    public List<Producto> listarProductos() {
        return productoRepositorio.findAll();
    }

    @Transactional
    public List<Producto> listarProductosPorMarca(Marca marca) {
        return productoRepositorio.findByMarca(marca);
    }

    @Transactional
    public List<Producto> listarProductosPorModelo(String modelo) {
        return productoRepositorio.findByModelo(modelo);
    }

    @Transactional
    public Producto getOne(String id) {
        return productoRepositorio.getOne(id);
    }

    @Transactional
    public List<Producto> listarActivos() {
        return productoRepositorio.findByActivo();
    }
    
    public void validarProducto(Producto producto) throws ErrorServicio {

        if (producto.getModelo().trim().isEmpty()) {
            throw new ErrorServicio("El modelo no puede estar vacío.");
        }
        if (producto.getMarca() == null) {
            throw new ErrorServicio("La marca no puede estar vacio");
        }
        if (producto.getFichaTecnica().trim().isEmpty()) {
            throw new ErrorServicio("La ficha técnica no puede estar vacía.");
        }
        if (producto.getFoto().trim().isEmpty()) {
            throw new ErrorServicio("La foto  no puede estar vacia.");
        }
        if (producto.getPrecio() == null) {
            throw new ErrorServicio("El precio no puede estar vacío.");
        }
    }

}
