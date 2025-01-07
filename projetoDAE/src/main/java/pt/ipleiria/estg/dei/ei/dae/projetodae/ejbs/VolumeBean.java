package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;

import java.util.List;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager entityManager;


    public void create(Long id, String estado, String tipo_embalagem) {
        var volume = new Volume(id, estado, tipo_embalagem);
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

    public Volume mudarEstado(Long id, String estado)
    {
        var volume = entityManager.find(Volume.class, id);
        if (volume == null) {
            throw new RuntimeException("volume " + id + " not found");
        }
        volume.setEstado(estado);
        entityManager.persist(volume);
        return volume;
    }


    public void create(Long id, String tipo_embalagem) {
        var student = new Volume(id, "Em expedição", tipo_embalagem);
        entityManager.persist(student);
    }
}
