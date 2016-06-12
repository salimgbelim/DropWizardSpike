package com.saltech;

import com.mongodb.Mongo;
import com.saltech.authentication.PhoneBookAuthenticator;
import com.saltech.health.MongoHealthCheck;
import com.saltech.health.NewContactHealthCheck;
import com.saltech.managed.MongoManaged;
import com.saltech.resources.BlogResource;
import com.saltech.resources.ClientResource;
import com.saltech.resources.ContactResource;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Validator;

public class App extends Application<PhoneBookConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<PhoneBookConfiguration> bootstrap) {

        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new AssetsBundle());
    }

    @Override
    public void run(PhoneBookConfiguration phoneBookConfiguration, Environment environment) throws Exception {

        LOGGER.info("Method App@run is called");

        for (int i = 0; i < phoneBookConfiguration.getMessageRepetitions(); i++) {
            System.out.println(phoneBookConfiguration.getMessage());
        }

        final Client client = new JerseyClientBuilder(environment)
                .build("REST Client");

        registerJDBIAndValidator(phoneBookConfiguration, environment);
        registerJerseyClient(client, phoneBookConfiguration, environment);
        registerAuthentication(phoneBookConfiguration, environment);
        registerHealthChecks(client, environment);

        Mongo mongo = new Mongo(phoneBookConfiguration.getMongohost(), phoneBookConfiguration.getMongoport());
        MongoManaged mongoManaged = new MongoManaged(mongo);
        environment.lifecycle().manage(mongoManaged);
        environment.healthChecks().register("Mongo", new MongoHealthCheck(mongo));

        environment.jersey().register(new BlogResource());

    }

    private void registerHealthChecks(Client client, Environment environment) {

        NewContactHealthCheck newContactHealthCheck = new NewContactHealthCheck(client);

        environment.healthChecks().register("New Contact Health Check", newContactHealthCheck);
    }

    private void registerAuthentication(PhoneBookConfiguration phoneBookConfiguration, Environment environment) {

        PhoneBookAuthenticator phoneBookAuthenticator = new PhoneBookAuthenticator(phoneBookConfiguration.getUserName(), phoneBookConfiguration.getPassword());

        BasicAuthProvider basicAuthProvider = new BasicAuthProvider<>(phoneBookAuthenticator, "Web Service Realm");
        environment.jersey().register(basicAuthProvider);

    }

    private void registerJDBIAndValidator(PhoneBookConfiguration phoneBookConfiguration, Environment environment) throws ClassNotFoundException {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory
                .build(environment, phoneBookConfiguration.getDataSourceFactory(), "mysql");

        Validator validator = environment.getValidator();

        environment.jersey().register(new ContactResource(jdbi, validator));
    }

    private void registerJerseyClient(Client client, PhoneBookConfiguration phoneBookConfiguration, Environment environment) {

        HTTPBasicAuthFilter httpBasicAuthFilter = new HTTPBasicAuthFilter(phoneBookConfiguration.getUserName(), phoneBookConfiguration.getPassword());
        client.addFilter(httpBasicAuthFilter);

        environment.jersey().register(new ClientResource(client));
    }
}
