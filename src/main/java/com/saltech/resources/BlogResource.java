package com.saltech.resources;

import com.codahale.metrics.annotation.Timed;
import com.saltech.representations.Blog;
import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.JacksonDBCollection;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/blog")
public class BlogResource {


    private final JacksonDBCollection<Blog, String> collection;

    public BlogResource(JacksonDBCollection<Blog, String> blogs) {
        this.collection = blogs;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public List<Blog> index() {

        DBCursor<Blog> dbCursor = collection.find();
        List<Blog> blogs = new ArrayList<>();

        while (dbCursor.hasNext()) {
            Blog blog = dbCursor.next();
            blogs.add(blog);
        }


        return blogs;

    }

    @Timed
    @POST
    public Response publishNewBlog(@Valid Blog blog) {

        collection.insert(blog);

        return Response.noContent().build();

    }

}


