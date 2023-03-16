package com.lemberski.istio.serviceb;

import static java.lang.String.format;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class Routes {
    
    private static final String HOSTNAME = System.getenv().getOrDefault("HOSTNAME", "localhost");
    private Boolean crash = false;
    private Integer counter = 0;
    
    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        if (crash) {
            throw new RuntimeException("Crash mode is active");
        }
        counter++;
        return format("Service B (%s) - %s", hostname(), counter);
    }

    @GET
    @Path("/crash")
    @Produces(MediaType.TEXT_PLAIN)
    public String crash() {
        crash = true;
        return "Crash mode activated, call 'repair' to deactivate.";
    }

    @GET
    @Path("/repair")
    @Produces(MediaType.TEXT_PLAIN)
    public String repair() {
        crash = false;
        return "Crash mode deactivated.";
    }

    @GET
    @Path("/health")
    @Produces(MediaType.TEXT_PLAIN)
    public String health() {
        return "UP";
    }

    private String hostname() {
        String dash = "-";
        if (HOSTNAME.contains(dash)) {
            return HOSTNAME.substring(HOSTNAME.lastIndexOf(dash) + 1);
        }

        return HOSTNAME;
    }

}
