package com.library.services.resources;

import com.library.services.core.managers.UserManager;
import com.library.services.db.dto.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/admin")
public class UserResource {
    private final UserManager userManager;

    public UserResource(UserManager userManager) {
        this.userManager = userManager;
    }

    @GET
    @Path("/user/view")
    public Response viewUser(@QueryParam("user_id") Integer userID) {
        return Response.ok(userManager.viewUser(userID)).build();
    }

    @POST
    @Path("/user/add")
    public Response addUser(@Valid @NotNull User user) {
        return Response.ok(userManager.addUser(user)).build();
    }
}
