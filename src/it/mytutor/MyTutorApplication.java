package it.mytutor;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationPath("api")
public class MyTutorApplication extends ResourceConfig {

    public MyTutorApplication(){

        //Scanning package api per definizione dei componenti REST
        packages("it.mytutor.api");

        //registrazione per logging delle richieste http
        register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME),
                Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, 10000));

        //registrazione del provider per serializzazione/deserializzazione oggetti in json tramite jackson
        register(JacksonJsonProvider.class);
    }
}
