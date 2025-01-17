package pt.ipleiria.estg.dei.ei.dae.projetodae.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.ProdutosNoVolumeDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.VolumeDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs.ProdutosNoVolumeBean;
import pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs.VolumeBean;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.projetodae.security.Authenticated;

import java.util.List;

@Path("volumes") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
public class VolumeService {
    @EJB
    private VolumeBean volumeBean;
    @EJB
    private ProdutosNoVolumeBean produtosNoVolumeBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/student/”
    public List<VolumeDTO> getAllVolumes() {
        return VolumeDTO.from(volumeBean.findAll());
    }

    @GET
    @Path("{id}")
    public Response getVolume(@PathParam("id") Long id) {
        var volume = volumeBean.find(id);
        return Response.ok(VolumeDTO.from(volume)).build();
    }

    @GET
    @Path("{id}/sensores")
    public Response getVolumeSensores(@PathParam("id") Long id) {
        var volume = volumeBean.findWithSensores(id);
        return Response.ok(SensorDTO.from(volume.getSensors())).build();
    }

    @GET
    @Path("{id}/produtos")
    public Response getVolumeProdutos(@PathParam("id") Long id) {
        var volume = volumeBean.findWithProdutos(id);
        return Response.ok(ProdutosNoVolumeDTO.from(volume.getProdutos())).build();
    }


    @PATCH
    @Path("{id}/entrega")
    public Response patchEntrega(@PathParam("id") Long id, VolumeDTO volumeDTO) {
        var volume = volumeBean.mudarEstado(id, volumeDTO.getEstado());
        return Response.ok(VolumeDTO.from(volume)).build();
    }

    /*@PATCH
    @Path("{id}/entrega")
    public Response patchEntrega(@PathParam("id") Long id) {
        var volume = volumeBean.mudarEstado(id);
        return Response.ok(VolumeDTO.from(volume)).build();
    }*/


    @POST
    @Path("/")
    //@Consumes(MediaType.APPLICATION_JSON)
    public Response create (VolumeDTO volumeDTO) {
        volumeBean.create(
                volumeDTO.getId(),
                volumeDTO.getTipo_embalagem(),
                volumeDTO.getEncomenda_id()

        );

        if (volumeDTO.getProdutos() != null && !volumeDTO.getProdutos().isEmpty()) {
            volumeDTO.getProdutos().forEach(produtosNoVolumeDTO -> produtosNoVolumeBean.create(produtosNoVolumeDTO.getId_produto(), produtosNoVolumeDTO.getQuantidade(), volumeDTO.getId()));
        }

        Volume newVolume = volumeBean.find(volumeDTO.getId());

        return Response.status(Response.Status.CREATED)
                .entity(VolumeDTO.fromComProdutos(newVolume))
                .build();
    }
    /*@POST
    @Path("/")
    @Authenticated
    @RolesAllowed({"Administrator"})
    //@Consumes(MediaType.APPLICATION_JSON)
    public Response create (CourseDTO courseDTO) throws MyEntityExistsException, MyEntityNotFoundException {
        courseBean.create(
                courseDTO.getCode(),
                courseDTO.getName()
        );

        Course newCourse = courseBean.find(courseDTO.getCode());
        return Response.status(Response.Status.CREATED)
                .entity(CourseDTO.from(newCourse))
                .build();
    }*/
}