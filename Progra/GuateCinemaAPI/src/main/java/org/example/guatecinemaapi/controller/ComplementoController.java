package org.example.guatecinemaapi.controller;
// Define el paquete al que pertenece esta clase, ayudando a organizar el código dentro del proyecto.

import org.example.guatecinemaapi.model.Complemento;
import org.example.guatecinemaapi.service.ComplementoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// Importa las clases necesarias para manejar la lógica de negocio (Complemento, ComplementoService), las anotaciones de Spring (Autowired, RestController, etc.),
// y el manejo de listas para devolver múltiples elementos.

@RestController
// Indica que esta clase es un controlador REST. Todas las respuestas que maneje estarán en formato JSON.

@RequestMapping("/api/v1/complementos")
// Define la ruta base para todas las peticiones relacionadas con "complementos". Las rutas dentro de esta clase comenzarán con "/api/v1/complementos".

public class ComplementoController {
    // Define la clase pública ComplementoController, que actuará como controlador para las operaciones relacionadas con los complementos.

    @Autowired
    // Inyecta automáticamente una instancia de ComplementoService cuando Spring crea el controlador. Esto permite usar el servicio sin crear una instancia manualmente.

    private ComplementoService complementoService;
    // Declara una variable privada de tipo ComplementoService. Esta instancia permitirá acceder a las operaciones de negocio relacionadas
    // con el modelo de Complemento, como guardar o buscar complementos.

    @GetMapping
    // Define que este método manejará solicitudes HTTP GET a la ruta "/api/v1/complementos".

    public List<Complemento> getAllComplementos() {
        // Método público que devuelve una lista de objetos Complemento. Se ejecuta cuando el cliente hace una solicitud GET.

        return complementoService.findAll();
        // Llama al método findAll() del servicio complementoService para obtener todos los complementos disponibles y los retorna como respuesta.
    }

    @PostMapping
    // Define que este método manejará solicitudes HTTP POST a la ruta "/api/v1/complementos".

    public Complemento createComplemento(@RequestBody Complemento complemento) {
        // Método público que recibe un objeto Complemento desde el cuerpo de la solicitud HTTP (indicado por @RequestBody).
        // Luego guarda este complemento en la base de datos.

        return complementoService.save(complemento);
        // Llama al método save() del servicio complementoService para guardar el complemento en la base de datos, y devuelve el complemento guardado como respuesta.
    }
}
