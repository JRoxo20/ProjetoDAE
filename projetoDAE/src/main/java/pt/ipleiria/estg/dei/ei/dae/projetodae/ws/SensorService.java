package pt.ipleiria.estg.dei.ei.dae.projetodae.ws;


import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import jakarta.ejb.EJB;
import jakarta.ws.rs.core.MediaType;

import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs.SensorBean;



@Path("sensors") // relative url web path for this service
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
public class SensorService {
    @EJB
    private SensorBean sensorBean;

    @GET
    @Path("/")
    public Response getAllSensors() {
        return Response.ok(sensorBean.findAll()).build();
    }



}
