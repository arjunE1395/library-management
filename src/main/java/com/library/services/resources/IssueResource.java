package com.library.services.resources;

import com.google.inject.Inject;
import com.library.services.core.impl.IssueManagerImpl;
import com.library.services.db.dto.Issue;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/booking")
public class IssueResource {
    @Inject
    private IssueManagerImpl issueManager;

    @POST
    @Path("/issue")
    public Response issueBook(@Valid @NotNull Issue issue) {
        return Response.ok(issueManager.issueBook(issue)).build();
    }

    @DELETE
    @Path("/return/{issue_id}")
    public Response returnBook(@PathParam("issue_id") int issueID) {
        issueManager.returnBook(issueID);
        return Response.ok().build();
    }

    @GET
    @Path("/show/{user_id}")
    public Response issuedUserBooks(@PathParam("user_id") int userID) {
        return Response.ok(issueManager.getBooksIssuedByUser(userID)).build();
    }
}
