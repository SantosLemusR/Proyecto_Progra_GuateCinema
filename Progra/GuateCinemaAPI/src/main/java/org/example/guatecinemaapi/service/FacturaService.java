package org.example.guatecinemaapi.service;

import org.example.guatecinemaapi.model.DetalleFactura;
import org.example.guatecinemaapi.model.Factura;
import org.example.guatecinemaapi.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    // Método para crear una nueva factura
    @Transactional
    public Factura crearFactura(Integer clienteId, List<DetalleFactura> detalles) {
        // Crear una nueva factura
        Factura factura = new Factura();
        factura.setClienteId(clienteId); // Establecer el ID del cliente
        factura.setDetalles(detalles); // Establecer los detalles de la factura

        // Calcular el total de la factura
        BigDecimal total = detalles.stream()
                .map(detalle -> detalle.getPrecioUnitario().multiply(new BigDecimal(detalle.getCantidad()))) // Calcular subtotal
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sumar subtotales
        factura.setTotal(total); // Establecer el total en la factura

        // Guardar la nueva factura en la base de datos
        return facturaRepository.save(factura);
    }

    // Método para obtener todas las facturas
    public List<Factura> obtenerTodasLasFacturas() {
        return facturaRepository.findAll(); // Retornar todas las facturas
    }
}
