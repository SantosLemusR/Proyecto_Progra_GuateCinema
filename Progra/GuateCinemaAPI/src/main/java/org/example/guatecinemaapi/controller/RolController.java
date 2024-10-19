package org.example.guatecinemaapi.controller;

import org.example.guatecinemaapi.model.Rol;
import org.example.guatecinemaapi.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
// Controlador para manejar las operaciones CRUD relacionadas con los roles.
public class RolController {

    @Autowired
    private RolService rolService; // Servicio que contiene la lógica para gestionar roles.

    // Obtener todos los roles
    @GetMapping
    public ResponseEntity<List<Rol>> getAllRoles() {
        // Llama al servicio para obtener la lista de todos los roles.
        return ResponseEntity.ok(rolService.getAllRoles());
        // Retorna una respuesta con el estado 200 (OK) y la lista de roles en el cuerpo de la respuesta.
    }

    // Crear un nuevo rol
    @PostMapping
    public ResponseEntity<Rol> createRol(@RequestBody Rol rol) {
        // Llama al servicio para crear un nuevo rol, pasando el objeto Rol recibido en la solicitud.
        Rol nuevoRol = rolService.createRol(rol);
        // Retorna una respuesta con el estado 200 (OK) y el nuevo rol creado en el cuerpo de la respuesta.
        return ResponseEntity.ok(nuevoRol);
    }
}
