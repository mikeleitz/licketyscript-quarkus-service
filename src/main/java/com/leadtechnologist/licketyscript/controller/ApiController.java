package com.leadtechnologist.licketyscript.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * @author leitz@mikeleitz.com
 */
@Slf4j
@Path("/status")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApiController {
    @GET
    public Map<String, String> status() {
        return Map.of("status", "Ready", "version", "0.1");
    }
}
