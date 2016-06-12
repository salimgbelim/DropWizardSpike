package com.saltech.authentication;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class PhoneBookAuthenticator implements Authenticator<BasicCredentials, Boolean> {

    @Override
    public Optional<Boolean> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {

        if (basicCredentials.getUsername().equals("salim_belim") &&
                basicCredentials.getPassword().equals("password")) {

            return Optional.of(true);
        }

        return Optional.of(false);
    }

}
