package com.digitalers.spring.boot.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "carritos_items")
public class ItemCarrito implements Serializable {

    private static final long serialVersionUID = -1517512231780271778L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    // La relación es unidireccional y debe estar en esta clase, ya que ItemCarrito contiene al
    // producto, pero no al revés. No tiene sentido consultar un producto para tener información sobre
    // las líneas en la que está, sino que por el contrario, necesitamos la línea para obtener la
    // información del producto.
    // Usamos @ManyToOne, ya que muchas líneas pueden contener el mismo producto y un producto puede
    // estar en muchas líneas.
    @ManyToOne(fetch = FetchType.LAZY)
    // Como ItemFactura es el dueño de la relación con Producto, entonces de forma automática se va a
    // generar la llave foránea "producto" (basado en el nombre del atributo) + "_" + "id" (basado en el
    // nombre del atributo de la clave primaria de la otra clase).
    // Esta llave foránea se crea automáticamente por ser esta clase la dueña de la relación.
    // De todos modos, opcionalmente, podríamos tener el @JoinColumn para especificar de forma explícita
    // la clave foránea.
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Producto producto;


    public ItemCarrito(Integer cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    // Método para calcular el importe (necesitamos tener los productos).
    public BigDecimal getImporte() {
        return this.producto.getPrecio().multiply(BigDecimal.valueOf(this.cantidad));
    }


}
