package com.saltech;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;

public class PhoneBookConfiguration extends Configuration {

    @JsonProperty
    @NotEmpty
    private String message;

    @JsonProperty
    @Max(10)
    private int messageRepetitions;

    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty
    @NotEmpty
    private String userName;

    @JsonProperty
    @NotEmpty
    private String password;

    public String getMessage() {
        return message;
    }

    public int getMessageRepetitions() {
        return messageRepetitions;
    }

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
