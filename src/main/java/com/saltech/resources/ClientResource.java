package com.saltech.resources;

import com.saltech.representations.Contact;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/client/")
@Produces(MediaType.TEXT_PLAIN)
public class ClientResource {

    private final Client client;

    public ClientResource(Client client) {
        this.client = client;
    }

    @GET
    @Path("showContact")
    public String showContact(@QueryParam("id") int id) {

        WebResource contactResource = client.resource("http://localhost:8080/contact/" + id);

        Contact contact = contactResource.get(Contact.class);

        String output = "ID: " + id
                + "\nFirst name: " + contact.getFirstName()
                + "\nLast name: " + contact.getLastName()
                + "\nPhone: " + contact.getPhone();

        return output;
    }

    @GET
    @Path("newContact")
    public Response newContact(@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("phone") String phone) {

        WebResource contactResource = client.resource("http://localhost:8080/contact");

        ClientResponse response = contactResource
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, new Contact(0, firstName, lastName, phone));

        if (response.getStatus() == 201) {

            return Response.status(302).entity("The contact was created successfully! The new contact can be found at " +
                    response.getHeaders().getFirst("Location")).build();

        }

        return Response.status(422).entity(response.getEntity(String.class)).build();
    }

    @GET
    @Path("updateContact")
    public Response updateContact(@QueryParam("id") int id, @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("phone") String phone) {

        WebResource contactResource = client.resource("http://localhost:8080/contact/" + id);

        ClientResponse response = contactResource
                .type(MediaType.APPLICATION_JSON)
                .put(ClientResponse.class, new Contact(id, firstName, lastName, phone));

        if (response.getStatus() == 200) {
            return Response.status(302).entity("The contact was updated successfully!").build();
        }

        return Response.status(422).entity(response.getEntity(String.class)).build();
    }

    @GET
    @Path("deleteContact")
    public Response deleteContact(@QueryParam("id") int id) {

        WebResource contactResource = client.resource("http://localhost:8080/contact/" + id);

        contactResource.delete();

        return Response.noContent().entity("Contact was deleted!").build();
    }
}

