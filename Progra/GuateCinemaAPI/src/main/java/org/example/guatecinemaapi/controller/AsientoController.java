package org.example.guatecinemaapi.controller;

// Define el paquete al que pertenece esta clase, lo que facilita la organización y reutilización del código.

import org.example.guatecinemaapi.model.Asiento;
import org.example.guatecinemaapi.service.AsientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Importa las clases necesarias para manejar la lógica de negocio (Asiento y AsientoService),
// las anotaciones de Spring (Autowired, RestController, etc.) y el manejo de respuestas HTTP (ResponseEntity).

@RestController
// Indica que esta clase es un controlador REST, lo que significa que gestionará solicitudes HTTP y devolverá respuestas en formato JSON.

@RequestMapping("/api/v1/asientos")
// Define la ruta base para este controlador. Todas las rutas dentro de esta clase comenzarán con /api/v1/asientos.

public class AsientoController {
    // Define la clase pública AsientoController, que actuará como controlador para las operaciones relacionadas con los asientos.

    @Autowired
    // La anotación @Autowired inyecta automáticamente una instancia de AsientoService cuando Spring crea la instancia del controlador.

    private AsientoService asientoService;
    // Declara una variable de instancia para el servicio de Asiento. Esta instancia permitirá acceder a las operaciones de negocio
    // sobre los asientos, como guardar, eliminar o consultar asientos.

    @GetMapping
    // Define que este método manejará solicitudes HTTP GET que se hagan a la ruta "/api/v1/asientos".

    public ResponseEntity<List<Asiento>> getAllAsientos() {
        // Método público que devuelve una lista de objetos Asiento envuelta en un ResponseEntity, que permite personalizar la respuesta HTTP.

        List<Asiento> asientos = asientoService.findAllAsientos();
        // Llama al método del servicio asientoService para obtener todos los asientos disponibles.

        return ResponseEntity.ok(asientos);
        // Devuelve la lista de asientos junto con un código de estado 200 (OK) como respuesta HTTP.
    }

    @PostMapping
    // Define que este método manejará solicitudes HTTP POST a la ruta "/api/v1/asientos".

    public ResponseEntity<Asiento> createAsiento(@RequestBody Asiento asiento) {
        // Método público que recibe un objeto Asiento desde el cuerpo de la solicitud HTTP (indicado por @RequestBody)
        // y devuelve el nuevo asiento creado envuelto en un ResponseEntity.

        Asiento nuevoAsiento = asientoService.saveAsiento(asiento);
        // Llama al servicio para guardar el nuevo asiento y almacena el resultado (el asiento recién creado) en la variable nuevoAsiento.

        return ResponseEntity.ok(nuevoAsiento);
        // Devuelve el asiento creado con un código de estado 200 (OK) en la respuesta HTTP.
    }

    @DeleteMapping("/{id}")
    // Define que este método manejará solicitudes HTTP DELETE a la ruta "/api/v1/asientos/{id}", donde {id} es un parámetro.

    public ResponseEntity<Void> deleteAsiento(@PathVariable Integer id) {
        // Método público que elimina un asiento basado en el ID proporcionado en la ruta (indicado por @PathVariable).
        // Retorna un ResponseEntity de tipo Void porque no hay contenido que devolver.

        asientoService.deleteAsiento(id);
        // Llama al servicio para eliminar el asiento con el ID especificado.

        return ResponseEntity.noContent().build();
        // Devuelve un código de estado 204 (No Content) indicando que la eliminación fue exitosa, pero sin contenido en la respuesta.
    }
}
