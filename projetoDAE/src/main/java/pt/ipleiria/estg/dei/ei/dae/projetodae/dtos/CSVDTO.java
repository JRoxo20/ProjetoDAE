package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import java.io.Serializable;

public class CSVDTO implements Serializable {

        String csv;

        public CSVDTO() {
        }

        public CSVDTO(String csv) {
            this.csv = csv;
        }

        public String getCsv() {
            return csv;
        }

        public void setCsv(String csv) {
            this.csv = csv;
        }
    }

}
