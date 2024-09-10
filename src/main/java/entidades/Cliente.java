package entidades;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Entidad que se tiene que guardar en una bd
@Entity
//Indicar que es uan tabla en base de datos con nombre (toma el de la clase)
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Audited
//Para hacer persistencia tiene que heredar serializable
public class Cliente implements Serializable {
    //Atributo unico. Clave primaria de la tabla
    @Id
    //Autoincrementable y se genera automaticamente. Inicando estrategia de generacion
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Indicando las columnas de la tabla
    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column(unique =true) //No ingresar registros con el mismo dni
    private int dni;


    @OneToOne(cascade = CascadeType.ALL) //Tipo de cascadeo de Cliente a Domicilio
    @JoinColumn(name = "fk_domicilio") //Clave foranea de domicilio
    private Domicilio domicilio;

    @Builder.Default
    @OneToMany(mappedBy = "cliente")
    private List<Factura> facturas = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "categorias")
    private List<Articulo> articulos = new ArrayList<Articulo>();

}
