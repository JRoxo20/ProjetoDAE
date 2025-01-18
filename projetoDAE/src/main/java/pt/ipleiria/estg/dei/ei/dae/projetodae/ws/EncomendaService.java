package pt.ipleiria.estg.dei.ei.dae.projetodae.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import jakarta.ejb.EJB;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.VolumeDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs.*;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.projetodae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projetodae.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.projetodae.security.Authenticated;


import java.util.List;

@Path("encomendas") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
public class EncomendaService {
    @EJB
    private EncomendaBean encomendaBean;

    @EJB
    private ClientBean clientBean;
    @EJB
    private VolumeBean volumeBean;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private ProdutosNoVolumeBean produtosNoVolumeBean;


    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/encomendas/”
    @RolesAllowed({"Logistica", "Gestor"})
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
    @RolesAllowed("Logistica")
    public Response createNewEncomenda (EncomendaDTO encomendaDTO) throws MyEntityExistsException, MyEntityNotFoundException {
            encomendaBean.create(
                    encomendaDTO.getId(),
                    encomendaDTO.getUsernameCliente());
            Encomenda newEncomenda = encomendaBean.find(encomendaDTO.getId());

            // Verificar se o ID do Volume já existe
            if (encomendaDTO.getVolumes() != null && !encomendaDTO.getVolumes().isEmpty())
            {
                for (VolumeDTO volume : encomendaDTO.getVolumes())
                {
                    if (volumeBean.verifyId(volume.getId()) != null) {
                        return Response.status(Response.Status.CONFLICT)
                                .entity("Volume com ID " + volume.getId() + " já existe.")
                                .build();
                    }

                    // Validar IDs de sensores dentro do Volume
                    if (volume.getSensores() != null && !volume.getSensores().isEmpty()) {
                        for (SensorDTO sensor : volume.getSensores()) {
                            if (sensorBean.verifyId(sensor.getId()) != null) {
                                return Response.status(Response.Status.CONFLICT)
                                        .entity("Sensor com ID " + sensor.getId() + " já existe.")
                                        .build();
                            }
                        }
                    }

                    volumeBean.create(
                            volume.getId(),
                            volume.getTipo_embalagem(),
                            newEncomenda.getId()
                    );

                    if (volume.getProdutos() != null && !volume.getProdutos().isEmpty()) {
                        volume.getProdutos().forEach(produtosNoVolumeDTO -> {
                            try {
                                produtosNoVolumeBean.create(produtosNoVolumeDTO.getId_produto(), produtosNoVolumeDTO.getQuantidade(), volume.getId());
                            } catch (MyEntityExistsException | MyEntityNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }

                    if (volume.getSensores() != null && !volume.getSensores().isEmpty()) {
                        volume.getSensores().forEach(sensor -> sensorBean.create(sensor.getId(), sensor.getTipo(), volume.getId()));
                    }

                    Volume newVolume = volumeBean.find(volume.getId());
                }

            }

            Encomenda encomenda = encomendaBean.findWithVolumes(encomendaDTO.getId());

            return Response.status(Response.Status.CREATED)
                    .entity(EncomendaDTO.fromComVolumes(encomenda))
                    .build();
    }


    @POST
    @Path("{id}")
    @RolesAllowed("Logistica")
    //@Consumes(MediaType.APPLICATION_JSON)
    public Response create (@PathParam("id") Long id, VolumeDTO volumeDTO) throws MyEntityExistsException, MyEntityNotFoundException  {
        // Verificar se o ID do Volume já existe
        if (volumeBean.verifyId(volumeDTO.getId()) != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Volume com ID " + volumeDTO.getId() + " já existe.")
                    .build();
        }

        // Validar IDs de sensores dentro do Volume
        if (volumeDTO.getSensores() != null && !volumeDTO.getSensores().isEmpty()) {
            for (SensorDTO sensor : volumeDTO.getSensores()) {
                if (sensorBean.verifyId(sensor.getId()) != null) {
                    return Response.status(Response.Status.CONFLICT)
                            .entity("Sensor com ID " + sensor.getId() + " já existe.")
                            .build();
                }
            }
        }


        volumeBean.create(
                volumeDTO.getId(),
                volumeDTO.getTipo_embalagem(),
                id
        );

        if (volumeDTO.getProdutos() != null && !volumeDTO.getProdutos().isEmpty()) {
            volumeDTO.getProdutos().forEach(produtosNoVolumeDTO -> {
                try {
                    produtosNoVolumeBean.create(produtosNoVolumeDTO.getId_produto(), produtosNoVolumeDTO.getQuantidade(), volumeDTO.getId());
                } catch (MyEntityExistsException e) {
                    throw new RuntimeException(e);
                } catch (MyEntityNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else {
            throw new MyEntityNotFoundException("Produtos não encontrados");
        }

        if (volumeDTO.getSensores() != null && !volumeDTO.getSensores().isEmpty()) {
            volumeDTO.getSensores().forEach(sensor -> sensorBean.create(sensor.getId(), sensor.getTipo(), volumeDTO.getId()));
        }

        Volume newVolume = volumeBean.find(volumeDTO.getId());

        return Response.status(Response.Status.CREATED)
                .entity(VolumeDTO.fromComProdutos(newVolume))
                .build();
    }

    @GET
    @Path("{id}/volumes")
    public Response getVolumeProdutos(@PathParam("id") Long id) {
        var encomenda = encomendaBean.findWithVolumes(id);
        return Response.ok(VolumeDTO.from(encomenda.getVolumes())).build();
    }


}
