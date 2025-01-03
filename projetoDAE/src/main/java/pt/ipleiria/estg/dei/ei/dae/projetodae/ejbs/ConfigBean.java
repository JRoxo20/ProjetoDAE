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
        private EncomendaBean encomendaBean;

        @PostConstruct
        public void populateDB() {

            userBean.create("Vasco", "Vasco", "vasco@gmail.com", "123456");

            encomendaBean.create(1, 1);
        }
    }

