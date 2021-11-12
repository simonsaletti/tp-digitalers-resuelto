package com.digitalers.spring.boot.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = 1571296256763560591L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 20)
    private String nombre;

    /*
     * Esta implementación de la relación es innecesaria, pero la hacemos sólo para ver cómo se haría.
     */
    /*
     * La dueña de la relación es la clase Usuario. El rol es la relación inversa. Para indicar eso,
     * usamos mappedBy="roles", donde "roles" es el atributo de la relación en la otra clase (la clase
     * Usuario).
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Usuario> usuarios;
}
