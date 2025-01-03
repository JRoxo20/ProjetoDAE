package pt.ipleiria.estg.dei.ei.dae.projetodae.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import jakarta.ejb.EJB;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs.EncomendaBean;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projetodae.security.Authenticated;


import java.util.List;

@Path("encomendas") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class EncomendaService {
    @EJB
    private EncomendaBean encomendaBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/encomendas/”
    public List<EncomendaDTO> getAllEncomendas() {
        return EncomendaDTO.from(encomendaBean.findAll());
    }

    /*@POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Authenticated
    public Response createNewEncomenda (EncomendaDTO encomendaDTO)  {
        encomendaBean.create(
                encomendaDTO.getId(),
                encomendaDTO.getIdCliente()

        );

        Encomenda newEncomenda = encomendaBean.find(encomendaDTO.getId());
        return Response.status(Response.Status.CREATED)
                .entity(encomendaDTO.from(newEncomenda))
                .build();
    }
    */


}
