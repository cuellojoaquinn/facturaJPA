package entidades;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Audited
public class Domicilio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombreCalle;

    @Column
    private int numero;

    //No se refleja en las tablas pero si en el flujo del codigo podemos verlo con em.find(Entity, pk)
    @OneToOne(mappedBy = "domicilio") //El objeto dueño de la relacion
    private Cliente cliente;
}
