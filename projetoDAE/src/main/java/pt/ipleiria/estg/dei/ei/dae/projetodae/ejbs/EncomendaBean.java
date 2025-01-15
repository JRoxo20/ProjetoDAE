package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;

import java.util.List;

@Stateless
public class EncomendaBean {
    @PersistenceContext
    private EntityManager entityManager;

    // criar encomenda
    public void create(Long id, int idCliente) {
        Encomenda encomenda = new Encomenda(id, idCliente);
        if(encomenda == null) {
            throw new RuntimeException("Encomenda with id '" + id + "' not found");
        }
        entityManager.persist(encomenda);

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
