package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import jakarta.persistence.Id;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Dado;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.SensorEstado;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.SensorType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DadoDTO {
    @Id
    private Long id;
    private String valor;
    private Date timestamp;
    private String mensagem;


    public DadoDTO(String valor, String mensagem) {
        this.valor = valor;
        this.timestamp = new Date();
        this.mensagem = mensagem;
    }

    public DadoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public static DadoDTO from (Dado dado){
        return new DadoDTO(
                dado.getValor(),
                dado.getMensagem()
        );
    }


    public static List<DadoDTO> from (List < Dado > dadoList) {
        return dadoList.stream().map(DadoDTO::from).collect(Collectors.toList());


    }
}
