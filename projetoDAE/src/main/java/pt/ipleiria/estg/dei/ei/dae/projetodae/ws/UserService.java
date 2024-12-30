package pt.ipleiria.estg.dei.ei.dae.projetodae.ws;

import jakarta.ejb.EJB;
import jakarta.mail.MessagingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.EmailDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs.EmailBean;
import pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.User;
import pt.ipleiria.estg.dei.ei.dae.projetodae.security.Authenticated;

import java.util.List;
@Path("users") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
//@Authenticated
public class UserService {


    @EJB
    private UserBean userBean;
    @EJB
    private EmailBean emailBean;
    @Context
    private SecurityContext securityContext;


    //@GET // means: to call this endpoint, we need to use the HTTP GET method
    //@Path("/") // means: the relative url path is “/api/student/”
    //public List<UserDTO> getAllStudents() {
      //  return UserDTO.from(userBean.findAll());
    //}
    @POST
    @Path("/")
    public Response createNewUser (UserDTO userDTO) {
        try{
            userBean.create(
                    userDTO.getUsername(),
                    userDTO.getName(),
                    userDTO.getEmail(),
                    userDTO.getPassword()
            );

            User newUser = userBean.find(userDTO.getUsername());
            return Response.status(Response.Status.CREATED)
                    .entity(UserDTO.from(newUser))
                    .build();
        }
        catch ( Exception e){
            return Response.status(Response.Status.CONFLICT)
                    .entity("Student with username '" + userDTO.getUsername() + "' already exists")
                    .build();
        }

    }

    @POST
    @Path("/{username}/email")
    public Response sendEmail(@PathParam("username") String username, EmailDTO email)
            throws MessagingException {
        User user = userBean.find(username);
        emailBean.send(user.getEmail(), email.getSubject(), email.getBody());
        return Response.status(Response.Status.OK).entity("E-mail sent").build();
    }
}
