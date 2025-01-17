package pt.ipleiria.estg.dei.ei.dae.projetodae.ws;


import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import jakarta.ejb.EJB;
import jakarta.ws.rs.core.MediaType;

import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.projetodae.security.Authenticated;

import java.util.List;


@Path("sensors") // relative url web path for this service
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Authenticated
public class SensorService {
    @EJB
    private SensorBean sensorBean;

    @GET
    @Path("/")
    public List<SensorDTO> getAllSensors() {
        return SensorDTO.from(sensorBean.findAll());
    }

    @GET
    @Path("/ativos")
    public List<SensorDTO> getAllAvailableSensors() {
        return SensorDTO.from(sensorBean.getAllAtivos());
    }

    @GET
    @Path("/inativos")
    public List<SensorDTO> getAllUnavailableSensors() {
        return SensorDTO.from(sensorBean.getAllInativos());
    }

    @GET
    @Path("/temperatura")
    public List<SensorDTO> getAllByTemperatura() {
        return SensorDTO.from(sensorBean.getAllByTemperatura());
    }

    @GET
    @Path("/pressao")
    public List<SensorDTO> getAllByPressao() {
        return SensorDTO.from(sensorBean.getAllByPressao());
    }

    @GET
    @Path("/gps")
    public List<SensorDTO> getAllByGps() {
        return SensorDTO.from(sensorBean.getAllByGps());
    }

    @GET
    @Path("/aceleracao")
    public List<SensorDTO> getAllByAceleracao() {
        return SensorDTO.from(sensorBean.getAllByAceleracao());
    }

    @GET
    @Path("/{sensor_id}")
    public SensorDTO getSensor(@PathParam("sensor_id") Long sensor_id) {
        return SensorDTO.from(sensorBean.find(sensor_id));
    }

    @POST
    @Path("/")
    // missing exceptions
    public Response createNewSensor (SensorDTO sensorDTO) throws Exception {
        try{
            sensorBean.create(
                    sensorDTO.getId(),
                    sensorDTO.getTipo(),
                    sensorDTO.getVolume_id()
            );

            Sensor newSensor = sensorBean.find(sensorDTO.getId());
            return Response.status(Response.Status.CREATED)
                    .entity(SensorDTO.from(newSensor))
                    .build();
        }
        catch (Exception e){
            return Response.status(Response.Status.CONFLICT)
                    .entity("Sensor with id: '" + sensorDTO.getId() + "' already exists")
                    .build();
        }
    }

}
