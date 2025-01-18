package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Encomenda;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.VolumeEstado;
import pt.ipleiria.estg.dei.ei.dae.projetodae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projetodae.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private EncomendaBean encomendaBean;

    @EJB
    private ProductBean produtoBean;

    @EJB
    private ClientBean clientBean;


    public void create(Long id, String tipo_embalagem, Long idEncomenda) throws MyEntityExistsException {
        //verifica se ja existe um volume com o id
        var volume = entityManager.find(Volume.class, id);
        if (volume != null) {
            throw new MyEntityExistsException("volume " + id + " already exist");
        }

        var encomenda = encomendaBean.find(idEncomenda);
        var new_volume = new Volume(id, tipo_embalagem, encomenda);
        entityManager.persist(new_volume);
        encomendaBean.enrollVolumeInEncomenda(idEncomenda, new_volume.getId());
    }


    public List<Volume> findAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllVolumes", Volume.class).getResultList();
    }


    public List<Volume> findAllByCliente(String usernameCliente) {
        Client client = clientBean.find(usernameCliente);
        return entityManager.createNamedQuery("getAllVolumesByClient", Volume.class)
                .setParameter("usernameCliente", usernameCliente)
                .getResultList();
    }

    public Volume find(Long id) {
        var volume = entityManager.find(Volume.class, id);
        if (volume == null) {
            throw new RuntimeException("volume " + id + " not found");
        }
        return volume;
    }

    public Volume verifyId(Long id) {
        var volume = entityManager.find(Volume.class, id);
        return volume;
    }

    public Volume mudarEstado(Long id, VolumeEstado estado)
    {
        var volume = entityManager.find(Volume.class, id);
        if (volume == null) {
            throw new RuntimeException("volume " + id + " not found");
        }
        if (volume.getEstado().compareTo(VolumeEstado.Entregue) == 0)
        {
            throw new RuntimeException("volume " + id + " já foi entregue");
        }
        volume.setEstado(estado);
        if (volume.getEstado().compareTo(VolumeEstado.Entregue) == 0)
        {
            volume.setData_entrega(new Date());
        }
        entityManager.persist(volume);
        return volume;
    }

    public Volume findWithSensores(Long id){
        var volume = this.find(id);
        Hibernate.initialize(volume.getSensors());
        return volume;
    }

    public Volume findWithProdutos(Long id){
        var volume = this.find(id);
        Hibernate.initialize(volume.getProdutos());
        return volume;
    }


    /*public void enrollProdutoInVolume(Long produto_id, Long volume_id)
    {
        var volume = find(volume_id);
        if (volume == null) {
            throw new RuntimeException("volume " + volume_id + " not found");
        }

        var produto = find(produto_id);
        if (produto == null) {
            throw new RuntimeException("produto " + produto_id + " not found");
        }

        if (student.getSubjects().contains(subject)) {
            throw new RuntimeException("Student " + username + " is already enrolled in subject "
                    + subject_code);
        }

        student.addSubject(subject);
        subject.addStudent(student);
    }


    public void unrollStudentInSubject(long subjectCode, String studentUsername) {
        var subject = subjectBean.find(subjectCode);
        var student = find(studentUsername);

        Course subjectCourse = subject.getCourse();
        Course studentCourse = student.getCourse();

        if (subjectCourse.equals(studentCourse)) {
            if (!student.getSubjects().contains(subject)) {
                throw new RuntimeException("Student " + studentUsername + " is not enrolled in subject "
                        + subject.getCode());
            }
            student.removeSubject(subject);
            subject.removeStudent(student);
            subject.getCourse().removeStudent(student);
        } else {
            throw new RuntimeException("Student " + studentUsername + " is not enrolled in course "
                    + subjectCourse.getCode());
        }
    }*/

}
