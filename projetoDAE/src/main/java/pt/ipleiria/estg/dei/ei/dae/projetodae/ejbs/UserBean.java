package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.User;
import pt.ipleiria.estg.dei.ei.dae.projetodae.security.Hasher;

import java.util.List;


@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;
    public User find(String username) {
        var user = em.getReference(User.class, username);
        Hibernate.initialize(user);
        return user;
    }
    public boolean canLogin(String username, String password) {
        var user = find(username);
        return user != null && user.getPassword().equals(hasher.hash(password));
    }


    public void updatePassword(String username, String newPassword) {
        var user = find(username);
        user.setPassword(hasher.hash(newPassword));
        em.merge(user);
    }

    public void create(String username, String name, String email, String password)  {
        if (exists(username)) {
            return;
        }

        User user = new User(username, name, email, hasher.hash(password));
        em.persist(user);
        em.flush();


    }

    private boolean exists(String username) {
        return em.find(User.class, username) != null;

    }

    //public List<User> findAll() {
      //  return em.createNamedQuery("getAllUsers", User.class).getResultList();
    //}
}
