package com.library.services.resources;

import com.google.inject.Inject;
import com.library.services.core.impl.BookManagerImpl;
import com.library.services.db.dto.Book;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/admin")
public class BookResource {
    private final BookManagerImpl bookManager;

    @Inject
    public BookResource(BookManagerImpl bookManager){
        this.bookManager = bookManager;
    }

    @GET
    @Path("/book/view")
    public Response viewBook(@QueryParam("book_id") Integer bookID) {
        return Response.ok(bookManager.viewBook(bookID)).build();
    }

    @POST
    @Path("/book/add")
    public Response addBook(@Valid @NotNull Book book) {
        return Response.ok(bookManager.addBook(book)).build();
    }
}
