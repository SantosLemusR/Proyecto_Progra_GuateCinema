package org.example.guatecinemaapi.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_factura")
public class DetalleFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relación con la factura
    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = false)
    private Factura factura;

    // Relación con el boleto (puedes hacerla obligatoria si así lo prefieres)
    @ManyToOne
    @JoinColumn(name = "boleto_id", nullable = true)
    private Boleto boleto;

    // Relación con el complemento (puedes hacerla obligatoria si así lo prefieres)
    @ManyToOne
    @JoinColumn(name = "complemento_id", nullable = true)
    private Complemento complemento;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private BigDecimal precioUnitario;

    @Column(nullable = false)
    private BigDecimal subtotal;

    // Getters y Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Factura getFactura() {
        return factura;
    }
    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Boleto getBoleto() {
        return boleto;
    }
    public void setBoleto(Boleto boleto) {
        this.boleto = boleto;
    }

    public Complemento getComplemento() {
        return complemento;
    }
    public void setComplemento(Complemento complemento) {
        this.complemento = complemento;
    }

    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }
    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
