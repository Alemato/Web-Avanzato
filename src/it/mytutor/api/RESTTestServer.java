package it.mytutor.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("test")
public class RESTTestServer {
    @GET
    @Consumes("application/json")
    @Produces("text/plain")
    public String testserverstring() {
        return "FUNZIONA" ;
    }

}
