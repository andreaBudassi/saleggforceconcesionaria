package com.saleggforce.egg.controladores;

import com.saleggforce.egg.servicios.EmpleadoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortalControlador {

    public final EmpleadoServicio empleadoServicio;

    @Autowired
    public PortalControlador(EmpleadoServicio empleadoServicio) {
        this.empleadoServicio = empleadoServicio;
    }

    //Muestra la pagina de inicio
    @GetMapping("/inicio")
    public String index() {
        return "index.html";
    }

    //Muestra la pagina de login y procesa el logincheck que lleva a una nueva vista
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
        if (error != null) {
            model.put("error", "Usuario o password incorrecto");
        }
        if (logout != null) {
            model.put("logout", "La sesión finalizo con éxito!");
        }
        return "empleados/login";
    }
}
