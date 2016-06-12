package com.saltech;

import com.google.common.io.Resources;
import com.saltech.representations.Contact;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import java.io.File;

import static org.fest.assertions.api.Assertions.assertThat;

public class AppTest
{

    private Client client;

    private Contact contactForTest = new Contact(0, "Jane", "Doe", "+987654321");

    @ClassRule
    public static final DropwizardAppRule<PhoneBookConfiguration> RULE =
            new DropwizardAppRule<>(App.class,resourceFilePath("config.yml"));

    @Before
    public void SetUp(){
        client = new Client();

        HTTPBasicAuthFilter httpBasicAuthFilter = new HTTPBasicAuthFilter("salim_belim", "password");

        client.addFilter(httpBasicAuthFilter);
    }

    @Test
    public void createAndRetrieveContact(){

        WebResource contactResource = client.resource("http://localhost:8080/contact");
        ClientResponse response = contactResource
                .type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, contactForTest);

        String newContactURL = response.getHeaders().get("Location").get(0);
        WebResource newContactResource = client.resource(newContactURL);
        Contact contact = newContactResource.get(Contact.class);

        assertThat(response.getStatus()).isEqualTo(201);
        assertThat(contact.getFirstName()).isEqualTo(contactForTest.getFirstName());
        assertThat(contact.getLastName()).isEqualTo(contactForTest.getLastName());
        assertThat(contact.getPhone()).isEqualTo(contactForTest.getPhone());

    }

    public static String resourceFilePath(String resourceClassPathLocation) {
        try {
            return new File(Resources.getResource(resourceClassPathLocation).toURI()).getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
