package org.example.guatecinemaapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference; // Asegúrate de importar esta línea
import javax.persistence.*;

@Entity
@Table(name = "asiento")
public class Asiento {

    // Campo id es la clave primaria, generada automáticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relación muchos a uno con la entidad Sala. Cada asiento pertenece a una sala.
    // @JsonBackReference evita recursión infinita cuando se serializan objetos JSON.
    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    @JsonBackReference // Evita la recursión infinita al serializar
    private Sala sala;

    // Campo que representa el número del asiento
    private Integer numero;

    // Enumeración que define el tipo de asiento ('NORMAL', 'VIP', etc.)
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private TipoAsiento tipo; // Enum: 'NORMAL', 'VIP', etc.

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public TipoAsiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoAsiento tipo) {
        this.tipo = tipo;
    }
}
