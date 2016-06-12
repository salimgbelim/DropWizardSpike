package com.saltech.resources;

import com.codahale.metrics.annotation.Timed;
import com.saltech.representations.Blog;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/blog")
public class BlogResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public List<Blog> index() {

        Blog blog = new Blog("Day 12: OpenCV--Face Detection for Java Developers",
                "https://www.openshift.com/blogs/day-12-opencv-face-detection-for-java-developers");

        return Arrays.asList(blog);

    }

}


