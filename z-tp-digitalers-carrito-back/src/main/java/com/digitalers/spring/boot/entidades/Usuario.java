package com.digitalers.spring.boot.entidades;

import java.io.Serializable;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 4321965896324353930L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String apellido;

    @Column(unique = true)
    private String email;

    @Column(unique = true, length = 20)
    private String username;

    @Column(length = 60)
    private String password;

    private Boolean enabled;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;
    
    @PrePersist
    public void preGuardar() {
        this.fechaCreacion = LocalDate.now();
    }

    // Va @OneToMany, ya que un cliente tiene muchos carritos, pero un carrito es de un único cliente.
    // Como buena práctica, usamos la carga perezosa.
    // Para que la relación sea en ambos sentidos (bidireccional), usaremos 'mappedBy="cliente"'.
    // mappedBy contiene el nombre del atributo de la contraparte de la relación.
    // Usamos cascada por si guardamos un carrito de cliente que inexistente, entonces crea todo junto.
    // Otro detalle es que la llave foránea estará ubicada en la tabla de carritos.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.ALL)
    // Cuando obtenemos un cliente en Postman, se creará un ciclo infinito.
    // Esto ocurrirá porque traer un cliente implica traer todos sus carritos de compras, pero, además,
    // traer los carritos de compras implica traer al cliente de cada uno de esos carritos de compras
    // (es el propio cliente que estamos consultando). Este ciclo se produce indefinidamente.
    // Usamos @JsonIgnoreProperties para evitar que los carritos nos traigan nuevamente al cliente.
    @JsonIgnoreProperties({"cliente", "hibernateLazyInitializer", "handler"})
    // Inicializamos esta lista desde el constructor de la clase.
    private List<Carrito> carritos;

    /*
     * Un usuario tiene una colección de objetos de tipo Role y un Role puede pertenecer a
     * múltiplesusuarios. Usamos @ManyToMany, ya que es una relación de muchos a muchos.
     */
    /*
     * Vamos a usar una relación en un solo sentido, ya que lo que nos interesa saber es qué roles tiene
     * un usuario (y no al revés). En caso de que, adicionalmente, nos interese saber qué usuarios
     * tienen determinado rol, entonces sí deberíamos hacer la relación bidireccional.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    /*
     * Usamos @JoinTable
     */

    @JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "role_id"})})
    private List<Role> roles;

    public Usuario() {
        this.carritos = new ArrayList<>();
    }

}
