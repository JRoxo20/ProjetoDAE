package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.User;
import pt.ipleiria.estg.dei.ei.dae.projetodae.security.Hasher;

import java.util.List;

@Stateless
public class ClientBean {

    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;

    public void create(String username, String name, String email, String password) {
        Client client = new Client(username, name, email,hasher.hash(password));
        em.persist(client);
    }

    public Client findOrFail(String username) {
        Client client = find(username);
        if (client == null) {
            throw new EntityNotFoundException("Client with username '" + username + "' not found.");
        }
        return client;
    }


    public Client find(String username) {
        Client client = em.find(Client.class, username);
        if (client == null) {
            throw new EntityNotFoundException("Client with username '" + username + "' not found.");
        }
        return client;

    }


    public Client findWithEncomendas(String username) {
        Client client = findOrFail(username);
        Hibernate.initialize(client.getEncomendas());
        return client;
    }

}
