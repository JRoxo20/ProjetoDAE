
package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.Category;

import java.util.List;

@Stateless
public class ProductBean {

    @PersistenceContext
    private EntityManager entityManager;

    private VolumeBean volumeBean;

    public void create(String name, String brand, Category category, Double price, Long volume_id) {
        if(exists(name)){
            // throw exception
            return;
        }
        try {
            //junção com volumes
            var volume = volumeBean.find(volume_id);
            Product product = new Product(name,brand, category, price, volume);
            entityManager.persist(product);
            entityManager.flush();
        } catch (Exception e) {
        // throw exception
        }
    }
    private boolean exists(String name) {
        String jpql = "SELECT COUNT(p) FROM Product p WHERE p.name = :name";
        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("name", name)
                .getSingleResult();
        return count > 0;
    }


    public List<Product> findAll(){
        // remember, maps to: “SELECT c FROM Course c ORDER BY c.name”
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public Product find(String name) {
        var product = entityManager.find(Product.class, name);
        if (product == null) {
            throw new RuntimeException("Product " + name + " not found");
        }
        return product;
    }

    public Product findById(Long id) {
        var product = entityManager.find(Product.class, id);
        if (product == null) {
            throw new RuntimeException("Product with ID: " +  id   +  " not found");
        }
        return product;
    }

    // throws exception
    public void delete(long id)  {
        Product product = findById(id);
        entityManager.remove(product);
    }





    // get products from a order
    // get products from volume


}

