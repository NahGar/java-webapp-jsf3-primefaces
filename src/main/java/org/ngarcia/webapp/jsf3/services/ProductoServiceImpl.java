package org.ngarcia.webapp.jsf3.services;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.ngarcia.webapp.jsf3.entities.Categoria;
import org.ngarcia.webapp.jsf3.entities.Producto;
import org.ngarcia.webapp.jsf3.repositories.CrudRepository;
import org.ngarcia.webapp.jsf3.repositories.ProductoRepository;

import java.util.List;
import java.util.Optional;

@Stateless
public class ProductoServiceImpl implements ProductoService {

   @Inject
   //private CrudRepository<Producto> repository;
   private ProductoRepository repository;

   @Inject
   private CrudRepository<Categoria> repositoryCategoria;

   @Override
   public List<Producto> listar() {
      return repository.listar();
   }

   @Override
   public Optional<Producto> porId(Long id) {
      return Optional.ofNullable(repository.porId(id));
   }

   @Override
   public void guardar(Producto producto) {
      repository.guardar(producto);
   }

   @Override
   public void eliminar(Long id) {
      repository.eliminar(id);
   }

   @Override
   public List<Categoria> listarCategorias() {
      return repositoryCategoria.listar();
   }

   @Override
   public Optional<Categoria> porIdCategoria(Long id) {
      return Optional.ofNullable(repositoryCategoria.porId(id));
   }

   @Override
   public List<Producto> buscarPorNombre(String nombre) {
      return repository.buscarPorNombre(nombre);
   }
}
