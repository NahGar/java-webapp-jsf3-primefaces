package org.ngarcia.webapp.jsf3;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.*;

@RequestScoped
public class ProducerEntityManager {

   @PersistenceContext(name="ejemploJpa")
   private EntityManager em;

   @Produces
   @RequestScoped
   private EntityManager beanEntityManager() {
      return em;
   }
}
