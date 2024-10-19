package org.example.guatecinemaapi.controller;

import org.example.guatecinemaapi.model.Factura;
import org.example.guatecinemaapi.model.FacturaRequest;
import org.example.guatecinemaapi.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    // Método para crear una nueva factura
    @PostMapping
    public ResponseEntity<Factura> crearFactura(@RequestBody FacturaRequest facturaRequest) {
        // Llamar al servicio para crear la factura
        Factura nuevaFactura = facturaService.crearFactura(facturaRequest.getClienteId(), facturaRequest.getDetalles());
        return ResponseEntity.ok(nuevaFactura); // Retornar la nueva factura en la respuesta
    }

    // Método para obtener todas las facturas
    @GetMapping
    public ResponseEntity<List<Factura>> obtenerFacturas() {
        List<Factura> facturas = facturaService.obtenerTodasLasFacturas(); // Obtener todas las facturas
        return ResponseEntity.ok(facturas); // Retornar la lista de facturas
    }
}
