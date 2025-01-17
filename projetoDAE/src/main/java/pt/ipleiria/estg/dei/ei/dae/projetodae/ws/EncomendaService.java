package pt.ipleiria.estg.dei.ei.dae.projetodae.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import jakarta.ejb.EJB;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.VolumeDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs.EncomendaBean;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projetodae.security.Authenticated;


import java.util.List;

@Path("encomendas") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class EncomendaService {
    @EJB
    private EncomendaBean encomendaBean;

    @EJB
    private ClientBean clientBean;


    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/encomendas/”
    public List<EncomendaDTO> getAllEncomendas() {
        return EncomendaDTO.from(encomendaBean.findAll());
    }

    @GET
    @Path("{usernamecliente}/myencomendas")
    public Response getAllEncomendasByClient(@PathParam("usernamecliente") String usernameCliente) {
        Client client = clientBean.find(usernameCliente);
        return Response.ok(EncomendaDTO.from(client.getEncomendas())).build();
    }

    @GET
    @Path("{id}")
    public Response getEncomenda(@PathParam("id") Long id) {
        var encomenda = encomendaBean.find(id);
        return Response.ok(EncomendaDTO.from(encomenda)).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewEncomenda (EncomendaDTO encomendaDTO) throws Exception  {
        try {
            encomendaBean.create(
                    encomendaDTO.getId(),
                    encomendaDTO.getUsernameCliente());
            Encomenda newEncomenda = encomendaBean.find(encomendaDTO.getId());
            return Response.status(Response.Status.CREATED)
                    .entity(encomendaDTO.from(newEncomenda))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Encomenda with id: '" + encomendaDTO.getId() + "' already exists")
                    .build();
        }

    }


    @GET
    @Path("{id}/volumes")
    public Response getVolumeProdutos(@PathParam("id") Long id) {
        var encomenda = encomendaBean.findWithVolumes(id);
        return Response.ok(VolumeDTO.from(encomenda.getVolumes())).build();
    }


    /*@POST
    @Path("{id}")
    public Response enrollVolumeInEncomenda(@PathParam("id") Long id, VolumeDTO volumeDTO) {
        encomendaBean.enrollVolumeInEncomenda(id, volumeDTO.getId());
        return Response.ok().build();
    }*/

}
