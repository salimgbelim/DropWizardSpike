package com.saltech.resources;

import com.saltech.dao.ContactDAO;
import com.saltech.representations.Contact;
import org.skife.jdbi.v2.DBI;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

    private final ContactDAO contactDAO;

    public ContactResource(DBI jdbi) {
        contactDAO = jdbi.onDemand(ContactDAO.class);
    }

    @GET
    @Path("/{id}")
    public Response getContact(@PathParam("id") int id) {

        Contact contact = contactDAO.getContactById(id);

        return Response
                .ok(contact)
                .build();
    }

    @POST
    public Response createContact(Contact contact) throws URISyntaxException {

        int newContactId = contactDAO.createContact(contact.getFirstName(), contact.getLastName(), contact.getPhone());

        URI uriOfCreatedResource = URI.create("/" + String.valueOf(newContactId));

        return Response
                .created(uriOfCreatedResource)
                .build();

    }

    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id) {

        contactDAO.deleteContact(id);

        return Response
                .noContent()
                .build();

    }

    @PUT
    @Path("/{id}")
    public Response updateContact(@PathParam("id") int id,
                                  Contact contact) {

        contactDAO.updateContact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone());

        Contact updateContact = new Contact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone());

        return Response
                .ok(updateContact)
                .build();

    }
}
