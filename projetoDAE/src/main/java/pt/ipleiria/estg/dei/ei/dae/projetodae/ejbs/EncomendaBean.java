package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;

import java.util.List;

@Stateless
public class EncomendaBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private ClientBean clientBean;

    // criar encomenda

    public void create(Long id, String usernameCliente) {
        Client client = clientBean.find(usernameCliente);
        Encomenda encomenda = new Encomenda(id, usernameCliente, client);
        if(encomenda == null) {
            throw new RuntimeException("Encomenda with id '" + id + "' not found");
        }
        entityManager.persist(encomenda);
        client.addEncomenda(encomenda);
    }


    public List<Encomenda> findAll() {
        return entityManager.createNamedQuery("getAllEncomendas", Encomenda.class).getResultList();
    }

    public Encomenda find(Long id) {
        var encomenda = entityManager.find(Encomenda.class, id);
        if (encomenda == null) {
            throw new RuntimeException("encomenda " + id + " not found");
        }
        return encomenda;
    }


    public void enrollVolumeInEncomenda(Long idEncomenda, Long idVolume) {
        var encomenda = find(idEncomenda);
        var volume = entityManager.find(Volume.class, idVolume);
        if (encomenda.getVolumes().contains(volume)) {
            throw new RuntimeException("Volume " + idVolume + " already enrolled in encomenda " + idEncomenda);
        }
        encomenda.addVolume(volume);
    }

}
