package pt.ipleiria.estg.dei.ei.dae.projetodae.ws;

import pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs.*;
import pt.ipleiria.estg.dei.ei.dae.projetodae.data.CSVExporter;
import pt.ipleiria.estg.dei.ei.dae.projetodae.data.CSVImporter;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projetodae.security.Authenticated;

import java.io.IOException;
import java.util.List;

@Path("productCSV")
@Produces({MediaType.TEXT_PLAIN})
@Consumes({MediaType.TEXT_PLAIN})
@Authenticated
@RolesAllowed({"Gestor","Logistica"})
public class ProductCSVService {
    @EJB
    ProductBean productBean;

    @GET
    @Path("/products")
    public Response exportProductsAsCSV() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<ProductDTO> productDTOS = ProductDTO.from(productBean.findAll());
        CSVExporter<ProductDTO> exporter = new CSVExporter<ProductDTO>();
        return Response.ok(exporter.generateCSV(productDTOS, new ProductDTO())).build();
    }


    @POST
    @Path("/products")
    @Produces({MediaType.APPLICATION_JSON})
    public Response importProductsFromCsv(CSVDTO csvDTO) {
        try {
            CSVImporter<ProductDTO> csvImporter = new CSVImporter<>();
            List<ProductDTO> listProductDTOS = csvImporter.importManyFromCSV(csvDTO.getCsv(), new ProductDTO());

            for (ProductDTO productDTO : listProductDTOS) {
                productBean.create(productDTO.getName(), productDTO.getBrand(), productDTO.getCategory(), productDTO.getPrice());
            }

            return Response.ok("Products imported successfully").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error processing CSV: " + e.getMessage()).build();
        }
    }

}
