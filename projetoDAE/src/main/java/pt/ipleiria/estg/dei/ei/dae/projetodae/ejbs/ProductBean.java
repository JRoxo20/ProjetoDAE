
package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Product;

import java.util.List;

@Stateless
public class ProductBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(String name, int quantity, String category, Double price) {
        Product product = new Product(name, quantity, category, price);
        entityManager.persist(product);
    }

    public List<Product> findAll(){
        // remember, maps to: “SELECT c FROM Course c ORDER BY c.name”
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }



    // get products from a order
    // get products from volume


}

