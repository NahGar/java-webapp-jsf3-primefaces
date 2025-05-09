package org.ngarcia.webapp.jsf3.repositories;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.ngarcia.webapp.jsf3.entities.Producto;

import java.util.List;

@RequestScoped
//public class ProductoRepositoryImpl implements CrudRepository<Producto> {
public class ProductoRepositoryImpl implements ProductoRepository {

   @Inject
   private EntityManager em;

   @Override
   public List<Producto> listar() {
      String sql = "select p from Producto p left outer join fetch p.categoria order by p.nombre";
      return em.createQuery(sql,Producto.class).getResultList();
   }

   @Override
   public Producto porId(Long id) {
      //return em.find(Producto.class,id);
      String sql = "select p from Producto p left outer join fetch p.categoria where p.id=:id";
      return em.createQuery(sql,Producto.class).setParameter("id",id).getSingleResult();
   }

   @Override
   public void guardar(Producto producto) {
      System.out.println("Guardar producto: " + producto);
      if(producto.getId() != null && producto.getId() > 0) {
         em.merge(producto);
      }
      else {
         em.persist(producto);
      }
   }

   @Override
   public void eliminar(Long id) {
      em.remove(porId(id));
   }

   @Override
   public List<Producto> buscarPorNombre(String nombre) {
      String sql = "select p from Producto p left outer join fetch p.categoria where p.nombre like :nombre";
      return em.createQuery(sql,Producto.class).setParameter("nombre","%"+nombre+"%").getResultList();
   }
}
