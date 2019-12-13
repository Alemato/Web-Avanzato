package it.mytutor.api;
import it.mytutor.business.impl.SubjectBusiness;
import it.mytutor.business.services.SubjectInterface;
import it.mytutor.domain.Subject;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Path("materie")
public class MaterieRest {

    private SubjectInterface subjectService = new SubjectBusiness();

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getMaterie(SecurityContext ciao, @QueryParam("macro-materia") String macroMateria, @QueryParam("micro-materia") String microMateria){
        List<Subject> subjects = new ArrayList<>();
        // TODO Per riprendere l'email che è utile per tornare l'user tramite una chiamata al DB
        // ciao.getUserPrincipal().getName()
        if (macroMateria == null && microMateria == null){
            subjects = subjectService.findAll();
        } else if (macroMateria != null && microMateria == null){
            subjects = subjectService.findAllByMacroSubject(macroMateria);
        }else if (macroMateria == null){
            subjects = subjectService.findAllByMicroSubject(microMateria);
        }
        return Response.ok(subjects).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response creaMaterie(Subject subject){
        System.out.println(subject);
        subjectService.createSubject(subject);
        System.out.println("nuova subject creata");
        return Response.ok().build();
    }
}
