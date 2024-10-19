package org.example.guatecinemaapi.controller;
// Define el paquete al que pertenece esta clase, que ayuda a organizar el código dentro del proyecto.

import org.example.guatecinemaapi.model.Cliente;
import org.example.guatecinemaapi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// Importa las clases necesarias para manejar la lógica de los modelos, repositorios y las anotaciones de Spring para crear controladores RESTful.

@RestController
// Indica que esta clase es un controlador REST, lo que significa que gestionará solicitudes HTTP y devolverá respuestas en formato JSON.

@RequestMapping("/api/clientes")
// Define la ruta base para todas las solicitudes relacionadas con clientes, en este caso será "/api/clientes".

public class ClienteController {
    // Define la clase pública ClienteController, que actuará como controlador para las operaciones relacionadas con los clientes.

    @Autowired
    // Inyecta automáticamente una instancia de ClienteRepository en esta clase para gestionar las operaciones con los datos de los clientes.

    private ClienteRepository clienteRepository;
    // Declara una variable privada de tipo ClienteRepository. Esta instancia permitirá acceder a la base de datos o almacenamiento
    // de los clientes mediante operaciones CRUD (crear, leer, actualizar, eliminar).

    @PostMapping
    // Define que este método manejará las solicitudes HTTP POST que se hagan a la ruta "/api/clientes".

    public Cliente crearCliente(@RequestBody Cliente cliente) {
        // Método público que recibe un objeto Cliente desde el cuerpo de la solicitud HTTP (indicado por @RequestBody)
        // y lo guarda en la base de datos.

        return clienteRepository.save(cliente);
        // Llama al método save del repositorio para guardar el objeto Cliente en la base de datos, y devuelve el objeto guardado.
    }

    @GetMapping("/{id}")
    // Define que este método manejará solicitudes HTTP GET a la ruta "/api/clientes/{id}", donde "{id}" es un parámetro de la URL.

    public ResponseEntity<Cliente> obtenerCliente(@PathVariable Integer id) {
        // Método público que busca un cliente por su ID. El ID es recibido desde la URL, gracias a la anotación @PathVariable.
        // El método devuelve una respuesta HTTP con el cliente si se encuentra, o un código 404 si no existe.

        return clienteRepository.findById(id)
                // Llama al método findById del repositorio para buscar el cliente con el ID especificado.

                .map(ResponseEntity::ok)
                // Si el cliente es encontrado, se envuelve en un ResponseEntity con un código de estado 200 (OK).

                .orElse(ResponseEntity.notFound().build());
        // Si el cliente no se encuentra, se devuelve un ResponseEntity con un código de estado 404 (Not Found).
    }
}
