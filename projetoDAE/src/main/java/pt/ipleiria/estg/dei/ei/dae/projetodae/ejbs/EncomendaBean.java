package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.projetodae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projetodae.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class EncomendaBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private ClientBean clientBean;

    // criar encomenda

    public void create(Long id, String usernameCliente) throws MyEntityNotFoundException, MyEntityExistsException{
        Client client = clientBean.find(usernameCliente);
        if (client == null) {
            throw new MyEntityNotFoundException("Client with username '" + usernameCliente + "' not found");
        }

        if(entityManager.find(Encomenda.class, id) != null) {
            throw new MyEntityExistsException("Encomenda with id '" + id + "' already exists");
        }
        Encomenda encomenda = new Encomenda(id, usernameCliente, client);
        if(encomenda == null) {
            throw new MyEntityNotFoundException("Encomenda with id '" + id + "' not found");
        }
        entityManager.persist(encomenda);
        client.addEncomenda(encomenda);
    }


    public List<Encomenda> findAll() {
        return entityManager.createNamedQuery("getAllEncomendas", Encomenda.class).getResultList();
    }

    public Encomenda findOrFail(long id) throws MyEntityNotFoundException{
        Encomenda encomenda = find(id);
        if (encomenda == null) {
            throw new MyEntityNotFoundException("Order with id: " + id + " not found");
        }
        return encomenda;
    }

    public Encomenda find(Long id) {
        return entityManager.find(Encomenda.class, id);
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
