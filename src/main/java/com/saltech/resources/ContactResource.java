package com.saltech.resources;

import com.saltech.representations.Contact;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

    @GET
    @Path("/{id}")
    public Response getContact(@PathParam("id") int id) {

        Contact contact = new Contact(id, "John", "Doe", "+1234569");

        return Response
                .ok(contact)
                .build();
    }

    @POST
    public Response createContact(Contact contact) {

        return Response
                .created(null)
                .build();

    }

    @DELETE
    public Response deleteContact(@PathParam("id") int id) {

        return Response
                .noContent()
                .build();

    }

    @PUT
    @Path("/{id}")
    public Response updateContact(@PathParam("id") int id,
                                  Contact contact) {

        Contact updateContact = new Contact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone());

        return Response
                .ok(updateContact)
                .build();

    }
}
