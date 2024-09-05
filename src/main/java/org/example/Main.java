package org.example;

import entidades.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        //Conexion con unidad de persistencia
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceApp");

        //Instancia para utilizarlo
        EntityManager em = emf.createEntityManager();

        try{
            em.getTransaction().begin();

            Categoria perecedero = Categoria.builder().denominacion("Perecederos").build();

            Categoria lacteos = Categoria.builder().denominacion("Lacteos").build();

            Categoria limpieza = Categoria.builder().denominacion("Limpieza").build();

            Articulo leche = Articulo.builder().cantidad(3).denominacion("leche").precio(1000).build();

            Articulo detergente = Articulo.builder().cantidad(4).denominacion("detergente").precio(1000).build();

            leche.getCategorias().add(perecedero);
            leche.getCategorias().add(lacteos);

            lacteos.getArticulos().add(leche);
            perecedero.getArticulos().add(leche);
            detergente.getCategorias().add(limpieza);
            limpieza.getArticulos().add(detergente);


            Factura fac1 = Factura.builder().fecha("04/09/2024").numero(40).build();

            //Atajo para comentar: CTRL+/
            Cliente cli1 = Cliente.builder()
                    .nombre("Joaquin")
                    .apellido("Cuello")
                    .build();

            Domicilio dom1 = Domicilio.builder().nombreCalle("Coronel Rodriguez").numero(232).build();

            cli1.setDomicilio(dom1);
            dom1.setCliente(cli1);  //La bidireccional no se refleja en la tabla

            //em.find(Entidad, id)
            em.persist(cli1);

            DetalleFactura linea1 = DetalleFactura.builder().build();

            linea1.setArticulo(leche);
            linea1.setCantidad(4);
            linea1.setSubtotal(450);

            fac1.getDetalles().add(linea1);

            DetalleFactura linea2 = DetalleFactura.builder().build();

            linea2.setArticulo(detergente);
            linea2.setCantidad(1);
            linea2.setSubtotal(50);

            fac1.getDetalles().add(linea2);

            em.persist(fac1);

            em.flush(); //Limpiar conexion

            em.getTransaction().commit();//Commit del persist
        } catch (Exception e) {
            em.getTransaction().rollback(); //Arregla posible error en base de datos.
            e.printStackTrace();
        }
        em.close();
        emf.close();
    }
}