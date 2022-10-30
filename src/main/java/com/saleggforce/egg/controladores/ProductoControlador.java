package com.saleggforce.egg.controladores;

import com.saleggforce.egg.entidades.Producto;
import com.saleggforce.egg.enums.Marca;
import com.saleggforce.egg.servicios.ProductoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/productos")
public class ProductoControlador {

    private final ProductoServicio productoServicio;

    @Autowired
    public ProductoControlador(ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
    }
//-------------------------------------------------------------------------
//empleado rol admin crea producto
    @GetMapping("/formulario")//crearProducto.html
    public String nuevoProducto(ModelMap model) {
        model.addAttribute("producto", new Producto());
        return "producto/crearProducto";
    }

    @PostMapping("/formulario")
    public String mostrarNuevoproducto(@ModelAttribute Producto producto, ModelMap model) {
        try {
            productoServicio.crearProducto(producto);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/producto/crearProducto";
        }
        return "redirect:/productos";
    }
//--------------------------------------------------------------------------------------------
//empleado rol admin modifica el producto
    @GetMapping("/modificar/{id}")// modificarProducto.html
    public String modificarProducto(@PathVariable String id, ModelMap model) {
        model.put("producto", productoServicio.getOne(id));
        return "producto/modificarProducto";
    }

    @PostMapping("/modificar/{id}")
    public String guardarProducto(@PathVariable String id,@RequestParam String nuevaFichaTecnica,@RequestParam String nuevaFoto,@RequestParam Integer nuevoPrecio,ModelMap model) {
        try {
            productoServicio.modificarProducto(id,nuevaFichaTecnica,nuevaFoto,nuevoPrecio);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/producto/modificarProducto";
        }
        return "redirect:/productos";
    }
//---------------------------------------------------------------------------------------------

    @GetMapping("/baja/{id}")//listaProducto.html
    public String baja(@PathVariable String id, ModelMap model) {
        try {
            productoServicio.bajaProducto(id);
        } catch (Exception e) {
            return "/producto/listaProducto";
        }
        return "redirect:/productos";
    }
//------------------------------------------------------------------------------
    @GetMapping("/alta/{id}")//listaProducto.html
    public String alta(@PathVariable String id, ModelMap model) {

        try {
            productoServicio.altaProducto(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/producto/listaProducto";
        }
        return "redirect:/productos";
    }
//-----------------------------------------------------------------------------
    @GetMapping("/listar")//listaProducto.html
    public String listarProductos(ModelMap modelo) {
        List<Producto> productos = productoServicio.listarProductos();
        modelo.addAttribute("productos", productos);
        return "/producto/listaProducto";
    }
//------------------------------------------------------------------------------
    @GetMapping("/listarPorMarca")//listarPorMarca.html
    public String listarPorMarca(ModelMap model, @RequestParam Marca marca) {
        List<Producto> productosPorMarca = productoServicio.listarProductosPorMarca(marca);
        model.addAttribute("productos", productosPorMarca);
        return "/producto/listarPorMarca";
    }
//-----------------------------------------------------------------------------
    @GetMapping("/listarPorModelo")//formularioCliente.html
    public String listarPorModelo(ModelMap model, @RequestParam String modelo) {
        List<Producto> productosPorModelo = productoServicio.listarProductosPorModelo(modelo);
        model.addAttribute("productos", productosPorModelo);
        return "/cliente/formularioCliente";
    }
//------------------------------------------------------------------------------
    @GetMapping("/listarActivos")//listaProducto.html
    public String listarActivos(ModelMap model) {
        List<Producto> productosActivos = productoServicio.listarActivos();
        model.addAttribute("productos", productosActivos);
        return "/producto/listaProducto";
    }
}
