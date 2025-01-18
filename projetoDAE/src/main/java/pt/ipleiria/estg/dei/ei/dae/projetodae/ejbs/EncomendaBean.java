package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
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
            throw new RuntimeException("Encomenda not found");
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
    public List<Encomenda> findAllByClient(Client client) {
        return entityManager.createNamedQuery("getAllEncomendasByClient", Encomenda.class)
                .setParameter("usernameCliente", client.getUsername())
                .getResultList();
    }

    public Encomenda findWithVolumes(Long id){
        var encomenda = this.find(id);
        Hibernate.initialize(encomenda.getVolumes());
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
