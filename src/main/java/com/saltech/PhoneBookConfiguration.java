package com.saltech;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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

    @JsonProperty
    @NotEmpty
    private String mongohost;

    @JsonProperty
    @Min(1)
    @Max(65535)
    private int mongoport;

    @JsonProperty
    @NotEmpty
    private String mongodb;

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

    public String getMongohost() {
        return mongohost;
    }

    public int getMongoport() {
        return mongoport;
    }

    public String getMongodb() {
        return mongodb;
    }
}
