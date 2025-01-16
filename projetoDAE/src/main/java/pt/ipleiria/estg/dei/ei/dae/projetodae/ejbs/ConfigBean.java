package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
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

            userBean.create("Vasco2", "Vasco2", "vasco2@gmail.com", "123456");
            clientBean.create("Vasco", "Vasco", "vasco@gmail.com", "123456");

            encomendaBean.create(1L, "Vasco");
            encomendaBean.enrollVolumeInEncomenda(1L, 1L);

            clientBean.create("joao", "joao", "joao@gmail.com", "123456");

            gestorBean.create("admin", "admin", "admin@gmail.com", "123456", true);

            logisticaBean.create("ctt", "ctt", "ctt@gmail.com", "123456");


            sensorBean.create(SensorEstado.ATIVO, SensorType.TEMPERATURA);
            sensorBean.create(SensorEstado.ATIVO, SensorType.HUMIDADE);
            sensorBean.create(SensorEstado.INATIVO, SensorType.GPS);

            dadoBean.create("25", "ta calor", 1L);
            //volumes
            volumeBean.create(1L, "em andamento", "isotérmica", 1L);
            volumeBean.create(2L, "em andamento", "normal", 1L);
            volumeBean.create(3L, "a entregar", "isotérmica", 1L);

            productBean.create("Gelado de morango", "Saboroso", Category.Alimentar, 12.5, 1L);
            productBean.create("Televisão", "XYZ",Category.Eletronico, 112.99, 2L);
            productBean.create("Microondas", "ABC", Category.Eletrodomestico, 20.5, 2L);
        }
    }

