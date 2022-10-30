package com.saleggforce.egg.servicios;

import com.saleggforce.egg.entidades.Cliente;
import com.saleggforce.egg.enums.Estado;
import static com.saleggforce.egg.enums.Estado.PENDIENTE;
import com.saleggforce.egg.enums.HorarioContacto;
import com.saleggforce.egg.errores.ErrorServicio;
import com.saleggforce.egg.repositorios.ClienteRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio {

    public final ClienteRepositorio clienteRepositorio;

    @Autowired
    public ClienteServicio(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    @Transactional
    public void crearCliente(Cliente cliente) throws ErrorServicio {
        validarCliente(cliente);
        cliente.setAlta(true);
        cliente.setEstado(PENDIENTE);
        clienteRepositorio.save(cliente);
    }

    public void modificarCliente(String id, String nuevoNombre,
            String nuevoApellido, String nuevoMail, String nuevoTelefono,
            HorarioContacto nuevoHorarioContacto, Estado nuevoEstado) throws ErrorServicio {
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();

            cliente.setNombre(nuevoNombre);
            cliente.setApellido(nuevoApellido);
            cliente.setMail(nuevoMail);
            cliente.setTelefono(nuevoTelefono);
            cliente.setHorarioContacto(nuevoHorarioContacto);
            cliente.setEstado(nuevoEstado);
            validarCliente(cliente);
            clienteRepositorio.save(cliente);
        } else {
            throw new ErrorServicio("No existe el cliente");
        }
    }

    @Transactional
    public Cliente altaCliente(String id) throws ErrorServicio {
        Cliente cliente = clienteRepositorio.getOne(id);
        cliente.setAlta(true);
        return cliente;
    }

    @Transactional
    public Cliente bajaCliente(String id) throws ErrorServicio {
        Cliente cliente = clienteRepositorio.getOne(id);
        cliente.setAlta(false);
        return cliente;
    }

    @Transactional
    public List<Cliente> listarClientes() {
        return clienteRepositorio.findAll();
    }

    //Busca por nombre o apellido
    @Transactional
    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepositorio.findByApellidoOrNombre(nombre);
    }

    public Cliente getOne(String id) {
        return clienteRepositorio.getOne(id);
    }

    private void validarCliente(Cliente cliente) throws ErrorServicio {
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new ErrorServicio("El nombre no debe estar vacio");
        }
        if (cliente.getApellido() == null || cliente.getApellido().trim().isEmpty()) {
            throw new ErrorServicio("El apellido no debe estar vacio");
        }
        if (cliente.getNombre().length() <= 2) {
            throw new ErrorServicio("El Nombre debe tener mas de 3 caracteres");
        }
        if (cliente.getMail() == null || cliente.getMail().isEmpty()) {
            throw new ErrorServicio("El mail no puede ser nulo");
        }
        if (cliente.getTelefono() == null || cliente.getTelefono().isEmpty()) {
            throw new ErrorServicio("El telefono no puede estar vacío");
        }
    }
}
