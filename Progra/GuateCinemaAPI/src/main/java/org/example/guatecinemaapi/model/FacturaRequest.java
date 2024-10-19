package org.example.guatecinemaapi.model;

import java.math.BigDecimal;
import java.util.List;

public class FacturaRequest {

    private Integer clienteId; // ID del cliente
    private List<DetalleFactura> detalles; // Lista de detalles de la factura
    private BigDecimal total; // Total de la factura

    // Constructor
    public FacturaRequest() {
    }

    // Getters y Setters
    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public List<DetalleFactura> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleFactura> detalles) {
        this.detalles = detalles;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
