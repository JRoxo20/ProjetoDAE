package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.DadoDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Dado;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Product;
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
    @EJB
    private DadoBean dadoBean;




    public void create(Long id, SensorType tipo, Long volume_id) {
        var volume = volumeBean.find(volume_id);
        if (volume == null)
        {
            return;
            //throw new MyEntityNotFoundException("volume \"" + volume_id + "\" not found");
        }
        var sensor = new Sensor(id,tipo, volume);
        entityManager.persist(sensor);
        volume.addSensor(sensor);
    }

    public List<Sensor> findAll(){

        return entityManager.createNamedQuery("getAllSensors", Sensor.class).getResultList();
    }

    public List<Sensor> getAllAtivos() {
        return entityManager.createNamedQuery("getSensorByStatus", Sensor.class)
                .setParameter("estado", SensorEstado.ATIVO)
                .getResultList();
    }

    public List<Sensor> getAllInativos() {
        return entityManager.createNamedQuery("getSensorByStatus", Sensor.class)
                .setParameter("estado", SensorEstado.INATIVO)
                .getResultList();
    }

    public List<Sensor> getAllByTemperatura() {
        return entityManager.createNamedQuery("getSensorByType", Sensor.class)
                .setParameter("tipo", SensorType.Temperatura)
                .getResultList();
    }
    public List<Sensor> getAllByPressao() {
        return entityManager.createNamedQuery("getSensorByType", Sensor.class)
                .setParameter("tipo", SensorType.Pressao)
                .getResultList();
    }
    public List<Sensor> getAllByGps() {
        return entityManager.createNamedQuery("getSensorByType", Sensor.class)
                .setParameter("tipo", SensorType.Gps)
                .getResultList();
    }

    public List<Sensor> getAllByAceleracao() {
        return entityManager.createNamedQuery("getSensorByType", Sensor.class)
                .setParameter("tipo", SensorType.Aceleracao)
                .getResultList();
    }

    public List<Sensor> getAllSensorsByClient(String usernameclient) {
        return entityManager.createNamedQuery("getAllSensorsByClient", Sensor.class)
                .setParameter("usernameCliente", usernameclient)
                .getResultList();
    }

    public Sensor find(Long id) {
        var sensor = entityManager.find(Sensor.class, id);
        if (sensor == null) {
            throw new RuntimeException("sensor " + id + " not found");
        }
        return sensor;
    }

    public Sensor verifyId(Long id) {
        var sensor = entityManager.find(Sensor.class, id);
        return sensor;
    }

    public List<Dado> getDados(Long id) {
        var sensor = find(id);
        Hibernate.initialize(sensor.getDados());
        return sensor.getDados();
    }

    //update estado
    /*public Sensor novoDado(Long id, String message, String valor)
    {
        var sensor = entityManager.find(Sensor.class, id);
        if (sensor == null) {
            throw new RuntimeException("sensor " + id + " not found");
        }
        dadoBean.create(valor, message, id);
        entityManager.persist(sensor);
        return sensor;
    }*/



}
