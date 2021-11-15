package com.digitalers.spring.boot.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Entity
@Table(name = "carritos")
@Data
public class Carrito implements Serializable {

    private static final long serialVersionUID = -4264349326417219354L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    // Un cliente pude tener muchos carritos, pero un carrito tiene un solo cliente.
    // Con @ManyToOne decimos que muchos carritos están asociados a un cliente.
    // Adicionalmente, como buena práctica, establecemos la carga en perezosa.
    @ManyToOne(fetch = FetchType.LAZY)
    // Con respecto a la llave foránea (el @JoinColumn), por defecto será "usuario_id", ya que toma el
    // nombre del atributo y le concatena el id.
    // Si queremos modificar el nombre de la relación, entonces sí usamos @JoinColumn.
    // ponemos igual el nombre por defecto, a pesar de que sea redundante.
    @JoinColumn(name = "usuario_id")
    // Cuando obtenemos un carrito en Postman, se creará un ciclo infinito.
    // Esto ocurrirá porque traer un carrito implica traer su cliente, pero, además,
    // traer su cliente implica traer a sus carritos de compra. Este ciclo se produce indefinidamente.
    // Usamos @JsonIgnoreProperties para evitar que los clientes nos traigan nuevamente sus carritos.
    @JsonIgnoreProperties({"carritos", "hibernateLazyInitializer", "handler"})
    private Usuario usuario;

    // Un carrito puede tener muchos items (líneas), pero un item pertenece a un único carrito.
    // Usamos cascada por si, por ejemplo, queremos borrar un carrito. Entonces se borran todas sus
    // líneas. Del mismo modo, si queremos guardar un carrito con todas sus líneas, automáticamente
    // persiste primero el carrito y luego las líneas.
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // La clave foránea estará en la tabla cuya entidad es ItemCarrito. Como ésta no es una relación
    // bidireccional, no tenemos un atributo llamado "carrito" en la clase ItemCarrito, por lo tanto,
    // esta llave foránea no se crea de forma automática. Debemos usar obligatoriamente @JoinColumn.
    @JoinColumn(name = "carrito_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<ItemCarrito> itemsCarrito;

    public Carrito() {
        this.itemsCarrito = new ArrayList<>();
    }

    public Carrito(Usuario usuario, List<ItemCarrito> itemsCarrito) {
        this.usuario = usuario;
        this.itemsCarrito = itemsCarrito;
    }

    // Establecemos la fecha cuando guardamos el carrito.
    @PrePersist
    public void preGuardar() {
        this.fechaCreacion = LocalDate.now();
    }

    // Método para calcular el total de la factura.
    public BigDecimal getTotal() {
        return this.itemsCarrito.stream().map(item -> item.getImporte()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
