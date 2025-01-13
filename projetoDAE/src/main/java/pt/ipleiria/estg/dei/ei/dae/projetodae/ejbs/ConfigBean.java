package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;



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
        private VolumeBean volumeBean;
        @PostConstruct
        public void populateDB() {

            userBean.create("Vasco", "Vasco", "vasco@gmail.com", "123456");

            clientBean.create("joao", "joao", "joao@gmail.com", "123456");

            gestorBean.create("admin", "admin", "admin@gmail.com", "123456", true);

            logisticaBean.create("ctt", "ctt", "ctt@gmail.com", "123456");

            encomendaBean.create(1, "joao");


            //volumes
            volumeBean.create(1L, "em andamento", "isotérmica");
            volumeBean.create(2L, "em andamento", "normal");
            volumeBean.create(3L, "a entregar", "isotérmica");
        }
    }

