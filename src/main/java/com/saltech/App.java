package com.saltech;

import com.saltech.resources.ContactResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        environment.jersey().register(new ContactResource());
    }
}
