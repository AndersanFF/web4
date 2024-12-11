package Servlets;


import DAO.UserFromBD;
import ResourcesForServlets.Users;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Path("/leaderboard")
public class LeaderboardServlet{
    UserFromBD userFromBD = new UserFromBD();
    private static final List<Users> users = new ArrayList<>();

    @POST
    @Path("/addResult")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addResult(
            @FormParam("username") String username,
            @FormParam("passwd") String passwd,
            @FormParam("score") String scoreParam) {

        if (username == null || passwd == null || scoreParam == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Missing parameters\"}")
                    .build();
        }

        int score;
        try {
            score = Integer.parseInt(scoreParam);
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Invalid score format\"}")
                    .build();
        }

        Users user = new Users(username, passwd, score);

        synchronized (users) {
            users.add(user);
        }

        userFromBD.addUser(user);
        System.out.println(user);
        return Response.status(Response.Status.CREATED)
                .entity(user.toJson())
                .build();
    }

    @GET
    @Path("/best")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBestUsers() {
        synchronized (users) {
            if (users.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No users available\"}")
                        .build();
            }


            int maxScore = users.stream()
                    .max(Comparator.comparingInt(Users::getScore))
                    .orElseThrow()
                    .getScore();


            List<Users> bestUsers = users.stream()
                    .filter(user -> user.getScore() == maxScore)
                    .toList();


            return Response.ok(bestUsers).build();
        }
    }
}
