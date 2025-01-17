package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.Role;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.SensorEstado;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.SensorType;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.Category;

import java.util.List;


@Startup
    @Singleton
    public class ConfigBean {
        @EJB
        private UserBean userBean;
        @EJB
        private ClientBean clientBean;
        @EJB
        private GestorBean gestorBean;
        @EJB
        private LogisticaBean logisticaBean;
        @EJB
        private EncomendaBean encomendaBean;

        @EJB
        private SensorBean sensorBean;

        @EJB
        private DadoBean dadoBean;
        @EJB
        private VolumeBean volumeBean;

        @EJB
        private ProductBean productBean;

        @PostConstruct
        public void populateDB() {

            userBean.create("Vasco2", "Vasco2", "vasco2@gmail.com", "123456", Role.GESTOR);
            clientBean.create("Vasco", "Vasco", "vasco@gmail.com", "123456");

            encomendaBean.create(1L, "Vasco");

            //volumes
            volumeBean.create(1L,  "isotérmica", 1L);
            volumeBean.create(2L,  "normal", 1L);
            volumeBean.create(3L,  "isotérmica", 1L);

            clientBean.create("joao", "joao", "joao@gmail.com", "123456");

            gestorBean.create("admin", "admin", "admin@gmail.com", "123456", true);

            logisticaBean.create("ctt", "ctt", "ctt@gmail.com", "123456");


            dadoBean.create("25", "ta calor", 1L);
            dadoBean.create("23", "ta calor", 1L);
            dadoBean.create("26", "ta calor", 1L);
            //volumes


            sensorBean.create(1l, SensorType.Temperatura, 1L);
            sensorBean.create(2l, SensorType.Pressao, 1L);
            sensorBean.create(3l, SensorType.Gps, 2L);


            productBean.create("Gelado de morango", "Saboroso", Category.Alimentar, 12.5, 1L);
            productBean.create("Televisão", "XYZ",Category.Eletronico, 112.99, 2L);
            productBean.create("Microondas", "ABC", Category.Eletrodomestico, 20.5, 2L);

        }
    }

