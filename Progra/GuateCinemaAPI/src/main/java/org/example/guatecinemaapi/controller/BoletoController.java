package org.example.guatecinemaapi.controller;

import org.example.guatecinemaapi.model.Boleto;
import org.example.guatecinemaapi.service.BoletoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boletos")
public class BoletoController {

    // Inyecta la instancia de BoletoService mediante @Autowired
    @Autowired
    private BoletoService boletoService;

    // Método para obtener todos los boletos disponibles
    @GetMapping
    public List<Boleto> getAllBoletos() {
        // Llama al servicio para obtener la lista de boletos
        return boletoService.findAll();
    }

    // Método para crear un nuevo boleto
    @PostMapping
    public Boleto createBoleto(@RequestBody Boleto boleto) {
        // Llama al servicio para guardar el nuevo boleto y devolver el boleto creado
        return boletoService.save(boleto);
    }
}
