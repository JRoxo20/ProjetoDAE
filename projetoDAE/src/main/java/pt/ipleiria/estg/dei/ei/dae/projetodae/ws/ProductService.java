package pt.ipleiria.estg.dei.ei.dae.projetodae.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Gestor;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.projetodae.security.Authenticated;

import java.util.List;

@Path("products") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
@RolesAllowed("Gestor")
public class ProductService {

    @EJB
    private ProductBean productBean;

    @Context
    private SecurityContext securityContext;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/products/”
    public List<ProductDTO> getAllProducts() {
        return ProductDTO.from(productBean.findAll());
    }

    @POST
    @Path("/")
    // missing exceptions
    public Response createNewProduct (ProductDTO productDTO) throws Exception {
            productBean.create(
                    productDTO.getName(),
                    productDTO.getBrand(),
                    productDTO.getCategory(),
                    productDTO.getPrice()
                    //productDTO.getVolume_id()
            );

            Product newProduct = productBean.find(productDTO.getName());
            return Response.status(Response.Status.CREATED)
                    .entity(ProductDTO.from(newProduct))
                    .build();

    }
    @GET
    @Path("{id}")
    public Response getProduct(@PathParam("id") Long id) {
        try {
            Product product = productBean.findById(id);
            ProductDTO productDTO = ProductDTO.from(product);
            return Response.ok(productDTO).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Product with ID: " + id + " not found.")
                    .build();
        }
    }
    @DELETE
    @Path("{id}")
    //throws MyEntityNotFoundException
    public Response deleteProduct(@PathParam("id") long id) throws Exception {
        productBean.delete(id);
        return Response.ok().build();
    }


    // update
    // remove
}
