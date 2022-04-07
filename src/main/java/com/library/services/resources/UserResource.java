package com.library.services.resources;

import com.google.inject.Inject;
import com.library.services.core.impl.UserManagerImpl;
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
    private final UserManagerImpl userManager;

    @Inject
    public UserResource(UserManagerImpl userManager) {
        this.userManager = userManager;
    }

    @GET
    @Path("/user/view")
    public Response viewUser(@QueryParam("user_id") String userID) {
        return Response.ok(userManager.viewUser(userID)).build();
    }

    @POST
    @Path("/user/add")
    public Response addUser(@Valid @NotNull User user) {
        return Response.ok(userManager.addUser(user)).build();
    }
}
