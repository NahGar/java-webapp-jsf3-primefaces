package org.ngarcia.webapp.jsf3.controllers;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.inject.Model;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import org.ngarcia.webapp.jsf3.entities.Producto;
import org.ngarcia.webapp.jsf3.services.ProductoService;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Model //Model equivale a Named + RequestScoped
public class ProductoControllerModal {

   private Producto producto;

   private Long id;

   private List<Producto> listado;

   @Inject
   private ProductoService service;

   @Inject
   private FacesContext facesContext;

   @Inject
   private ResourceBundle bundle;

   private String textoBuscar;

   @PostConstruct
   public void init() {
      this.listado = service.listar();
      producto = new Producto();
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public List<Producto> getListado() {
      return listado;
   }

   public void setListado(List<Producto> listado) {
      this.listado = listado;
   }

   public String getTextoBuscar() {
      return textoBuscar;
   }

   public void setTextoBuscar(String textoBuscar) {
      this.textoBuscar = textoBuscar;
   }

   public Producto getProducto() {
      return producto;
   }

   public void setProducto(Producto producto) {
      this.producto = producto;
   }

   /* Se repite
   @Produces
   @Model
   public String titulo() {
      //return "Hola mundo JavaServer Faces 3.0 desde controller";
      return bundle.getString("producto.index.titulo");
   }
   */

   //@Produces
   //@Model
   public Producto producto() {

      //if (this.producto == null) {
      if (this.producto.isEmpty()) {
         this.producto = new Producto();
         if (this.id != null && this.id > 0) {
            service.porId(id).ifPresent(p -> this.producto = p);
         }
      }
      System.out.println("producto modal: " + producto);
      return this.producto;
   }

   /* Se repite
   @Produces
   @RequestScoped
   @Named("listadoCategorias")
   public List<Categoria> findAllCategorias() {
      return service.listarCategorias();
   }
   */

   public void guardar() {

      System.out.println("GUARDAR MODAL: "+this.producto);

      System.out.println(this.producto);
      if(this.producto.getId() != null && this.producto.getId() > 0) {
         facesContext.addMessage(null,new FacesMessage(
                 String.format(bundle.getString("producto.mensaje.editar"),producto.getNombre())));
      }
      else {
         facesContext.addMessage(null,new FacesMessage(
                 String.format(bundle.getString("producto.mensaje.crear"),producto.getNombre())));
      }

      service.guardar(this.producto);

      this.listado = service.listar();

      producto = new Producto();
   }

   public void editar(Long id) {
      System.out.println("EDITAR MODAL:"+id);
      this.id = id;
      producto();
   }

   //public String eliminar(Producto producto) {
   public void eliminar(Producto producto) {

      service.eliminar(producto.getId());

      facesContext.addMessage(null,new FacesMessage(
              bundle.getString("producto.mensaje.eliminar"),producto.getNombre()));

      this.listado = service.listar();
   }

   public void buscar() {
      this.listado = service.buscarPorNombre(this.textoBuscar);
   }

   public void cerrarDialogo() {
      producto = new Producto();
   }

   public String getDescButton() {

      String idParam = "";

      if(this.id != null) { //lo utiliza el primer botón de Editar (se pierde si hay error de validación)
         idParam = this.id.toString();
      }
      else { //lo utiliza el segundo botón de Editar
         FacesContext facesContext = FacesContext.getCurrentInstance();
         Map<String, String> params = facesContext.getExternalContext()
                 .getRequestParameterMap();
         idParam = params.get("id");
      }

      //System.out.println("ID:"+idParam);

      if (idParam != null && !idParam.isEmpty()) {
         return bundle.getString("producto.boton.editar");
      }
      else {
         return bundle.getString("producto.boton.crear");
      }
   }
}
