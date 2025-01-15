package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Gestor;
import pt.ipleiria.estg.dei.ei.dae.projetodae.security.Hasher;

import java.util.List;

@Stateless
public class GestorBean {

    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;

    public void create(String username, String name, String email, String password, Boolean isGestor) {
        Gestor gestor = new Gestor(username, name, email,hasher.hash(password), isGestor);
        em.persist(gestor);
    }


}
