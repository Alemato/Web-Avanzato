package it.mytutor;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import it.mytutor.business.security.AuthorizationFilter;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.util.logging.*;

@ApplicationPath("api")
public class MyTutorApplication extends ResourceConfig {

    public MyTutorApplication(){

        //Scanning package api per definizione dei componenti REST
        packages("it.mytutor.api");
        packages("it.mytutor.business.security");

        //registrazione per logging delle richieste http
        register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME),
                Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, 10000));

        //registrazione del provider per serializzazione/deserializzazione oggetti in json tramite jackson
        register(JacksonJsonProvider.class);
    }
}
