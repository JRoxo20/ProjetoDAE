
package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.Category;
import pt.ipleiria.estg.dei.ei.dae.projetodae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projetodae.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProductBean {

    @PersistenceContext
    private EntityManager entityManager;


    public void create(String name, String brand, Category category, Double price) throws MyEntityExistsException {
        if (exists(name)) {
            throw new MyEntityExistsException("Product with name: " + name + " already exists");
        }
        try {
            Product product = new Product(name, brand, category, price);
            entityManager.persist(product);
            entityManager.flush();
        } catch (Exception e) {
            throw new RuntimeException("Error creating product");
        }
    }

    private boolean exists(String name) {
        String jpql = "SELECT COUNT(p) FROM Product p WHERE p.name = :name";
        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("name", name)
                .getSingleResult();
        return count > 0;
    }


    public List<Product> findAll() {
        // remember, maps to: “SELECT c FROM Course c ORDER BY c.name”
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public Product find(String name) {
        String jpql = "SELECT p FROM Product p WHERE p.name = :name";
        Product product;
        return product = entityManager.createQuery(jpql, Product.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public Product findById(Long id) throws MyEntityNotFoundException {
        var product = entityManager.find(Product.class, id);
        if (product == null) {
            throw new MyEntityNotFoundException("Product with id: " + id + " not found");
        }
        return product;
    }

    public void delete(long id) throws MyEntityNotFoundException {
        {
            Product product = findById(id);
            if (product == null) {
                throw new MyEntityNotFoundException("Product with id: " + id + " not found");
            }
            entityManager.remove(product);
        }
    }
}

