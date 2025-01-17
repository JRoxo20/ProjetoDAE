package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import jakarta.persistence.Id;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Dado;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.SensorEstado;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.SensorType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SensorDTO {

    @Id
    private Long id;
    private SensorEstado estado;
    private SensorType tipo;
    private Long volume_id;
    //private List<Dado> dados;





    public SensorDTO( Long id, SensorEstado estado, SensorType tipo,/*List<Dado> dados,*/ Long volume_id){

            this.id = id;
            this.estado = estado;
            this.tipo = tipo;
            //this.dados = dados;
            this.volume_id = volume_id;
        }

    public SensorDTO() {
            //this.dados = new ArrayList<>();
        }


        public Long getVolume_id () {
            return volume_id;
        }

        public void setVolume_id (Long volume_id){
            this.volume_id = volume_id;
        }

        public Long getId () {
            return id;
        }

        public void setId (Long id){
            this.id = id;
        }

        public SensorType getTipo () {
            return tipo;
        }

        public void setTipo (SensorType tipo){
            this.tipo = tipo;
        }

    /*public List<Dado> getDados() {
        return dados;
    }

    public void setDados(List<Dado> dados) {
        this.dados = dados;
    }*/

        public SensorEstado getEstado () {
            return estado;
        }

        public void setEstado (SensorEstado estado){
            this.estado = estado;
        }

        public static SensorDTO from (Sensor sensor){
            return new SensorDTO(
                    sensor.getId(),
                    sensor.getEstado(),
                    sensor.getTipo(),
                    //sensor.getDados(),
                    sensor.getVolume().getId()
            );
        }


        public static List<SensorDTO> from (List < Sensor > sensorList) {
            return sensorList.stream().map(SensorDTO::from).collect(Collectors.toList());


        }
    }