package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Encomenda;

import java.util.List;

@Stateless
public class EncomendaBean {
    @PersistenceContext
    private EntityManager entityManager;

    // criar encomenda
    public void create(int id, int idCliente) {
        Encomenda encomenda = new Encomenda(id, idCliente);
        if(encomenda == null) {
            throw new RuntimeException("Encomenda with id '" + id + "' not found");
        }
        entityManager.persist(encomenda);

    }


    public List<Encomenda> findAll() {
        return entityManager.createNamedQuery("getAllEncomendas", Encomenda.class).getResultList();
    }

    public Encomenda find(int id) {
        var encomenda = entityManager.find(Encomenda.class, id);
        if (encomenda == null) {
            throw new RuntimeException("encomenda " + id + " not found");
        }
        return encomenda;
    }

}
