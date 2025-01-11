
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

    public void create(String name, String brand,int quantity, String category, Double price) {
        if(exists(name)){
            // throw exception
            return;
        }
        try {
            Product product = new Product(name,brand, quantity, category, price);
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

    public void update(Long id,String name,String brand, String category, int quantity, Double price) {

        Product product = entityManager.find(Product.class, findById(id));

        if (product == null) {
            System.err.println("Error_product_not_found: " + product);
            return;
        }
        entityManager.lock(product, LockModeType.OPTIMISTIC);
        product.setName(name);
        product.setBrand(brand);
        product.setCategory(category);
        product.setQuantity(quantity);
        product.setPrice(price);

    }

    // throws exception
    public void delete(long id)  {
        Product product = findById(id);
        entityManager.remove(product);
    }





    // get products from a order
    // get products from volume


}

