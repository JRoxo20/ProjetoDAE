package pt.ipleiria.estg.dei.ei.dae.projetodae.ws;



import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.AuthDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.NewPasswordDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.projetodae.security.Authenticated;
import pt.ipleiria.estg.dei.ei.dae.projetodae.security.Hasher;
import pt.ipleiria.estg.dei.ei.dae.projetodae.security.TokenIssuer;
@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {
    @Inject
    private TokenIssuer issuer;
    @EJB
    private UserBean userBean;

    @Context
    private SecurityContext securityContext;

    private Hasher hasher = new Hasher();
    @POST
    @Path("/login")
    public Response authenticate(@Valid AuthDTO auth) {
        if (userBean.canLogin(auth.getUsername(), auth.getPassword())) {
            String token = issuer.issue(auth.getUsername());
            return Response.ok(token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @PATCH
    @Authenticated
    @Path("/set-password")
    public Response setPassword(@Valid NewPasswordDTO newPasswordDTO) {
        var user = userBean.find(securityContext.getUserPrincipal().getName());
        if (!user.getPassword().equals(hasher.hash(newPasswordDTO.getOldPassword()))){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if(!newPasswordDTO.getNewPassword().equals(newPasswordDTO.getConfirmNewPassword())){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        userBean.updatePassword(user.getUsername(), newPasswordDTO.getNewPassword());
        return Response.ok().build();
    }

    @GET
    @Authenticated
    @Path("/user")
    public Response getAuthenticatedUser() {
        var username = securityContext.getUserPrincipal().getName();
        var user = userBean.find(username);
        return Response.ok(UserDTO.from(user)).build();
    }


}

