package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Logistica;
import pt.ipleiria.estg.dei.ei.dae.projetodae.security.Hasher;

@Stateless
public class LogisticaBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;

    public void create(String username, String name, String email, String password) {
        Logistica logistica = new Logistica(username, name, email,hasher.hash(password));
        em.persist(logistica);
    }


}
