package com.saltech.authentication;

import com.google.common.base.Optional;
import com.saltech.PhoneBookConfiguration;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class PhoneBookAuthenticator implements Authenticator<BasicCredentials, Boolean> {

    private final String userName;
    private final String password;

    public PhoneBookAuthenticator(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
    @Override
    public Optional<Boolean> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {

        if (basicCredentials.getUsername().equals(userName) &&
                basicCredentials.getPassword().equals(password)) {

            return Optional.of(true);
        }

        return Optional.of(false);
    }

}
