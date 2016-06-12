package com.saltech.representations;

import net.vz.mongodb.jackson.Id;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import java.util.Date;
import java.util.UUID;

public class Blog {

    private final Date publishedOn = new Date();


    private String id = UUID.randomUUID().toString();

    @Id
    private String _id;

    @NotBlank
    private String title;
    @URL
    @NotBlank
    private String url;

    public Blog() {
    }

    public Blog(String title, String url) {
        super();
        this.title = title;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public Date getPublishedOn() {
        return publishedOn;
    }
}
