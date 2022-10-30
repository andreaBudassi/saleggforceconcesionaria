package com.saleggforce.egg.controladores;

import com.saleggforce.egg.entidades.Cliente;
import com.saleggforce.egg.enums.Estado;
import com.saleggforce.egg.enums.HorarioContacto;
import com.saleggforce.egg.servicios.ClienteServicio;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteControlador {

    private final ClienteServicio clienteServicio;

    @Autowired
    public ClienteControlador(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    @GetMapping//cliente/listaCliente
    public String listar(ModelMap model) {
        List<Cliente> clientes = clienteServicio.listarClientes();
        model.addAttribute("clientes", clientes);
        return "cliente/listaCliente";
    }

    @GetMapping("/formulario")//cliente/formularioCliente
    public String nuevoCliente(@RequestParam(required = false) String id, ModelMap model, RedirectAttributes attr) {
        if (id == null) {
            model.addAttribute("cliente", new Cliente());
        } else {
            try {
                Cliente cliente = clienteServicio.getOne(id);
                model.addAttribute("cliente", cliente);
                return "cliente/formularioCliente";
            } catch (Exception e) {
                attr.addFlashAttribute("error", e.getMessage());
                return "cliente/formularioCliente";
            }
        }
        return "cliente/formularioCliente";
    }

    @PostMapping("/formulario")//cliente/formularioCliente
    public String guardarCliente(@ModelAttribute Cliente cliente, ModelMap model, ModelMap modelo) {
        try {
            clienteServicio.crearCliente(cliente);
            model.addAttribute("cliente", cliente);
            modelo.put("exito", "Se envio su solicitud de contacto");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "cliente/formularioCliente";
        }
        return "index";
    }

    @GetMapping("/gestion/{id}")//cliente/gestionCliente
    public String gestion(@PathVariable String id, ModelMap model) {
        return "cliente/gestionCliente";
    }

    @PostMapping("/gestion/{id}")//cliente/gestionCliente
    public String procesarGestion(@PathVariable String id, String nuevoNombre,
            String nuevoApellido, String nuevoMail, String nuevoTelefono,
            HorarioContacto nuevoHorarioContacto, Estado nuevoEstado, ModelMap model) {
        try {
            clienteServicio.modificarCliente(id, nuevoNombre, nuevoApellido, nuevoMail, nuevoTelefono, nuevoHorarioContacto, nuevoEstado);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "cliente/gestionCliente";
        }
        return "";
    }

    @GetMapping("/baja/{id}")//cliente/listaCliente
    public String bajaCliente(@PathVariable String id, ModelMap model) {
        try {
            clienteServicio.bajaCliente(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "cliente/listaCliente";
        }
        return "cliente/listaCliente";
    }

    @GetMapping("/alta/{id}")//cliente/listaCliente
    public String altaCliente(@PathVariable String id, ModelMap model) {
        try {
            clienteServicio.altaCliente(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "cliente/listaCliente";
        }
        return "cliente/listaCliente";
    }

    @GetMapping("/buscar")//en el nav o head
    public String buscar(@RequestParam String nombre, ModelMap model) {
        try {
            clienteServicio.buscarPorNombre(nombre);
        } catch (Exception e) {
            model.put("error", "no se encontro ningun cliente con ese nombre");
            return "cliente/listaCliente";
        }
        return "cliente/listaCliente";
    }

    @PostMapping("/buscar/{id}")
    public String seleccionar(@PathVariable String id, ModelMap model) {
        try {
            clienteServicio.buscarPorNombre(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "cliente/gestionCliente";
    }

}
//clientes/formulario
//metodo nuevoCliente
//
//clientes/gestion{id}
//metodo gestionCliente
//
//clientes/alta/{id}
//metodo altaCliente
//
//clientes/baja/{id}
//metodo bajaCliente
//
//clientes/listar
//metodo listarClientes
//
//clientes/buscar
//metodo buscarPorNombre
