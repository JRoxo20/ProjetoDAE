package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;



import java.io.Serializable;

public class EmailDTO implements Serializable {

    private String body;
    private String subject;


    public String getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public EmailDTO(String body) {
        this.subject = subject;
        this.body = body;
    }

    public EmailDTO() {
    }
}
