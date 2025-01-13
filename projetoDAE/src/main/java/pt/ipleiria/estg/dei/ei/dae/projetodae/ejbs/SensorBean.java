package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;

import java.util.List;

@Stateless
public class SensorBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(long id, String estado) {
        var sensor = new Sensor(id, estado);
        entityManager.persist(sensor);
    }

    public List<Sensor> findAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllSensores", Sensor.class).getResultList();
    }

    public Sensor find(Long id) {
        var sensor = entityManager.find(Sensor.class, id);
        if (sensor == null) {
            throw new RuntimeException("sensor " + id + " not found");
        }
        return sensor;
    }



}
