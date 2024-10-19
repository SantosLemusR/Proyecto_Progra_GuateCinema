package org.example.guatecinemaapi.model;

import javax.persistence.*;
@Entity
@Table(name = "boleto")
// La clase Boleto representa la entidad que mapea la tabla "boleto" en la base de datos.
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // El campo "id" es la clave primaria de la tabla y se genera automáticamente.
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "funcion_id", nullable = false)
    // Relación muchos a uno con la entidad Funcion. Un boleto pertenece a una función.
    private Funcion funcion;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    // Relación muchos a uno con la entidad Cliente. Un boleto puede estar asociado con un cliente.
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "asiento_id", nullable = false)
    // Relación muchos a uno con la entidad Asiento. Cada boleto tiene un asiento asignado.
    private Asiento asiento;

    @ManyToOne
    @JoinColumn(name = "complemento_id")
    // Relación muchos a uno con la entidad Complemento. Un boleto puede tener un complemento opcional (ej: comida, bebida).
    private Complemento complemento;

    // Campo que almacena el precio del boleto.
    private double precio;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    // Relación muchos a uno con la entidad Usuario. Un boleto es gestionado por un usuario (quien realiza la venta).
    private Usuario usuario;

    // Getters y Setters para acceder y modificar los atributos privados de la entidad.
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public Complemento getComplemento() {
        return complemento;
    }

    public void setComplemento(Complemento complemento) {
        this.complemento = complemento;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
