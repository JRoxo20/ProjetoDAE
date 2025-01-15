package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.util.List;


@Startup
    @Singleton
    public class ConfigBean {
        @EJB
        private UserBean userBean;
        @EJB
        private EncomendaBean encomendaBean;

        @EJB
        private VolumeBean volumeBean;
        @PostConstruct
        public void populateDB() {

            userBean.create("Vasco", "Vasco", "vasco@gmail.com", "123456");

            encomendaBean.create(1L, 1);


            //volumes
            volumeBean.create(1L, "em andamento", "isotérmica", 1L);
            volumeBean.create(2L, "em andamento", "normal", 1L);
            volumeBean.create(3L, "a entregar", "isotérmica", 1L);

            encomendaBean.enrollVolumeInEncomenda(1L, 1L);
        }
    }

