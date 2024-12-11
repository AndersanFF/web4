package Authu;

import DAO.UserFromBD;
import ResourcesForServlets.Users;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/authu")
public class Authentication {
    UserFromBD userFromBD = new UserFromBD();

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response aunthUser(
            @FormParam("username") String username,
            @FormParam("passwd") String passwd)
    {

        if (username == null || passwd == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Missing parameters\"}")
                    .build();
        }

        Users user = userFromBD.checkUser(username);

        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("User not found")
                    .build();
        }
        else {
            if(user.getPasswd().equals(passwd)) {
                return Response.status(Response.Status.OK)
                        .build();
            }
            else {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Wrong password")
                        .build();
            }
        }

    }

    @POST
    @Path("/reg")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response regUser(@FormParam("username") String username,
                            @FormParam("passwd") String passwd){
        if (username == null || passwd == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Missing parameters\"}")
                    .build();
        }
        Users user = userFromBD.checkUser(username);
        if(user == null) {
            userFromBD.addUser(new Users(username, passwd));
            return Response.status(Response.Status.OK).build();
        }else{
            return Response.status(Response.Status.UNAUTHORIZED).entity("User already exist").build();
        }



    }




}
