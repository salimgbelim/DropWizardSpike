package com.saltech;

import com.saltech.resources.ClientResource;
import com.saltech.resources.ContactResource;
import com.sun.jersey.api.client.Client;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
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

    }

    @Override
    public void run(PhoneBookConfiguration phoneBookConfiguration, Environment environment) throws Exception {

        LOGGER.info("Method App@run is called");

        for (int i = 0; i < phoneBookConfiguration.getMessageRepetitions(); i++) {
            System.out.println(phoneBookConfiguration.getMessage());
        }

        registerJDBIAndValidator(phoneBookConfiguration, environment);
        registerJerseyClient(environment);
    }

    private void registerJDBIAndValidator(PhoneBookConfiguration phoneBookConfiguration, Environment environment) throws ClassNotFoundException {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory
                .build(environment, phoneBookConfiguration.getDataSourceFactory(), "mysql");

        Validator validator = environment.getValidator();

        environment.jersey().register(new ContactResource(jdbi, validator));
    }

    private void registerJerseyClient(Environment environment) {
        final Client client = new JerseyClientBuilder(environment).build("REST Client");
        environment.jersey().register(new ClientResource(client));
    }
}
