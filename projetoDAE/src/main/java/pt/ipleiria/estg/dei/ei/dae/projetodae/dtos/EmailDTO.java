package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;



import java.io.Serializable;

public class EmailDTO implements Serializable {

    private String body;


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public EmailDTO(String body) {

        this.body = body;
    }

    public EmailDTO() {
    }
}
