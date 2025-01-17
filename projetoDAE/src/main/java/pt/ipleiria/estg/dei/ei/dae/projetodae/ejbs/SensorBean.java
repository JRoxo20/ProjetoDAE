package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Dado;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.SensorEstado;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.SensorType;

import java.util.List;

@Stateless
public class SensorBean {

    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private VolumeBean volumeBean;

    public void create(SensorEstado estado, SensorType tipo, Long volume_id) {
        var volume = volumeBean.find(volume_id);
        if (volume == null)
        {
            return;
            //throw new MyEntityNotFoundException("volume \"" + volume_id + "\" not found");
        }
        var sensor = new Sensor(estado, tipo, volume);
        entityManager.persist(sensor);

        volume.addSensor(sensor);
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

    public List<Dado> getDados(Long id) {
        var sensor = find(id);
        Hibernate.initialize(sensor.getDados());
        return sensor.getDados();
    }



}
