package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;

import java.util.Date;
import java.util.List;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager entityManager;


    public void create(Long id, String tipo_embalagem) {
        var volume = new Volume(id, tipo_embalagem);
        entityManager.persist(volume);
    }


    public List<Volume> findAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllVolumes", Volume.class).getResultList();
    }

    public Volume find(Long id) {
        var volume = entityManager.find(Volume.class, id);
        if (volume == null) {
            throw new RuntimeException("volume " + id + " not found");
        }
        return volume;
    }

    public Volume mudarEstado(Long id)
    {
        var volume = entityManager.find(Volume.class, id);
        if (volume == null) {
            throw new RuntimeException("volume " + id + " not found");
        }
        if (volume.getEstado().compareTo("entregue") == 0)
        {
            throw new RuntimeException("volume " + id + " já foi entregue");
        }
        volume.setEstado("entregue");
        volume.setData_entrega(new Date());
        entityManager.persist(volume);
        return volume;
    }

    public Volume findWithSensores(Long id){
        var volume = this.find(id);
        Hibernate.initialize(volume.getSensors());
        return volume;
    }
}
