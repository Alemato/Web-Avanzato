package it.mytutor.api.test;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

@Path("test")
public class RESTTestServer {
    @Context
    private SecurityContext securityContext;

    @GET
    @Consumes("application/json")
    @Produces("text/html")
    @PermitAll
    public String testserverstring() {
        return "<h1 style=\"\n" +
                "    color: red;\n" +
                "    margin: auto;\n" +
                "    width: fit-content;\n" +
                "\">IL SERVER FUNZIONA</h1> <h2 style=\"\n" +
                "    color: red;\n" +
                "    margin: auto;\n" +
                "    width: fit-content;\n" +
                "\">LISTA STATI RICHESTE</h2> <ul style=\"\n" +
                "    margin: 16px auto;\n" +
                "    width: fit-content;\n" +
                "\">\n" +
                "    <li><a href=\"http://localhost:8080/api/test\">200 OK<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/201\">201 CREATE<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/202\">202 ACCEPT<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/204\">204 NO_CONTENT<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/205\">205 RESET_CONTENT<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/206\">206 PARTIAL_CONTENT<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/301\">301 MOVED_PERMANENTLY<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/302\">302 FOUND<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/303\">303 SEE_OTHER<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/304\">304 NOT_MODIFIED<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/305\">305 USE_PROXY<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/307\">307 TEMPORARY_REDIRECT<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/400\">400 BAD_REQUEST<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/401\">401 UNAUTHORIZED<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/402\">402 PAYMENT_REQUIRED<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/403\">403 FORBIDDEN<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/404\">404 NOT_FOUND<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/405\">405 METHOD_NOT_ALLOWED<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/406\">406 NOT_ACCEPTABLE<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/407\">407 PROXY_AUTHENTICATION_REQUIRED<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/408\">408 REQUEST_TIMEOUT<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/409\">409 CONFLICT<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/410\">410 GONE<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/411\">411 LENGTH_REQUIRED<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/412\">412 PRECONDITION_FAILED<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/413\">413 REQUEST_ENTITY_TOO_LARGE<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/414\">414 REQUEST_URI_TOO_LONG<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/415\">415 UNSUPPORTED_MEDIA_TYPE<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/416\">416 REQUESTED_RANGE_NOT_SATISFIABLE<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/417\">417 EXPECTATION_FAILED<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/428\">428 PRECONDITION_REQUIRED<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/429\">429 TOO_MANY_REQUESTS<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/431\">431 REQUEST_HEADER_FIELDS_TOO_LARGE<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/500\">500 INTERNAL_SERVER_ERROR<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/501\">501 NOT_IMPLEMENTED<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/502\">502 BAD_GATEWAY<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/503\">503 SERVICE_UNAVAILABLE<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/504\">504 GATEWAY_TIMEOUT<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/505\">505 HTTP_VERSION_NOT_SUPPORTED<\\a></li>\n" +
                "    <li><a href=\"http://localhost:8080/api/test/511\">511 NETWORK_AUTHENTICATION_REQUIRED<\\a></li>\n" +
                "</ul>" ;
    }


    @GET
    @Path("201")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test201() {
        return Response.status(Status.CREATED).build();
    }

    @GET
    @Path("202")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test202() {
        return Response.status(Status.ACCEPTED).build();
    }


    @GET
    @Path("204")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test204() {
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("205")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test205() {
        return Response.status(Status.RESET_CONTENT).build();
    }

    @GET
    @Path("206")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test206() {
        return Response.status(Status.PARTIAL_CONTENT).build();
    }

    @GET
    @Path("301")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test301() {
        return Response.status(Status.MOVED_PERMANENTLY).build();
    }

    @GET
    @Path("302")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test302() {
        return Response.status(Status.FOUND).build();
    }

    @GET
    @Path("303")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test303() {
        return Response.status(Status.SEE_OTHER).build();
    }

    @GET
    @Path("304")
    @Consumes("application/json")
    @Produces("text/plain")
    @PermitAll
    public Response test304() {
        return Response.status(Status.NOT_MODIFIED).build();
    }

    @GET
    @Path("305")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test305() {
        return Response.status(Status.USE_PROXY).build();
    }

    @GET
    @Path("307")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test307() {
        return Response.status(Status.TEMPORARY_REDIRECT).build();
    }
    @GET
    @Path("400")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test400() {
        return Response.status(Status.BAD_REQUEST).build();
    }

    @GET
    @Path("401")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test401() {
        return Response.status(Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("402")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test402() {
        return Response.status(Status.PAYMENT_REQUIRED).build();
    }

    @GET
    @Path("403")
    @Consumes("application/json")
    @Produces("text/plain")
    @PermitAll
    public Response test403() {
        return Response.status(Status.FORBIDDEN).build();
    }

    @GET
    @Path("404")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test404() {
        return Response.status(Status.METHOD_NOT_ALLOWED).build();
    }

    @GET
    @Path("405")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test405() {
        return Response.status(Status.NOT_ACCEPTABLE).build();
    }

    @GET
    @Path("406")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test406() {
        return Response.status(Status.PROXY_AUTHENTICATION_REQUIRED).build();
    }

    @GET
    @Path("407")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test407() {
        return Response.status(Status.REQUEST_TIMEOUT).build();
    }

    @GET
    @Path("408")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test408() {
        return Response.status(Status.REQUEST_TIMEOUT).build();
    }

    @GET
    @Path("409")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test409() {
        return Response.status(Status.CONFLICT).build();
    }

    @GET
    @Path("410")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test410() {
        return Response.status(Status.GONE).build();
    }

    @GET
    @Path("411")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test411(){
        return Response.status(Status.LENGTH_REQUIRED).build();
    }

    @GET
    @Path("412")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test412() {
        return Response.status(Status.PRECONDITION_FAILED).build();
    }

    @GET
    @Path("413")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test413() {
        return Response.status(Status.REQUEST_ENTITY_TOO_LARGE).build();
    }

    @GET
    @Path("414")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test414() {
        return Response.status(Status.REQUEST_URI_TOO_LONG).build();
    }

    @GET
    @Path("415")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test415() {
        return Response.status(Status.UNSUPPORTED_MEDIA_TYPE).build();
    }

    @GET
    @Path("416")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test416() {
        return Response.status(Status.REQUESTED_RANGE_NOT_SATISFIABLE).build();
    }

    @GET
    @Path("417")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test417() {
        return Response.status(Status.EXPECTATION_FAILED).build();
    }

    @GET
    @Path("428")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test428() {
        return Response.status(Status.PRECONDITION_REQUIRED).build();
    }

    @GET
    @Path("429")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test429() {
        return Response.status(Status.TOO_MANY_REQUESTS).build();
    }

    @GET
    @Path("431")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test431() {
        return Response.status(Status.REQUEST_HEADER_FIELDS_TOO_LARGE).build();
    }

    @GET
    @Path("500")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test500() {
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("501")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test501() {
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @GET
    @Path("502")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test502() {
        return Response.status(Status.BAD_GATEWAY).build();
    }

    @GET
    @Path("503")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test503() {
        return Response.status(Status.SERVICE_UNAVAILABLE).build();
    }

    @GET
    @Path("504")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test504() {
        return Response.status(Status.GATEWAY_TIMEOUT).build();
    }

    @GET
    @Path("505")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test505() {
        return Response.status(Status.HTTP_VERSION_NOT_SUPPORTED).build();
    }

    @GET
    @Path("511")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response test511() {
        return Response.status(Status.NETWORK_AUTHENTICATION_REQUIRED).build();
    }

    @GET
    @Path("aut")
    @Consumes("application/json")
    @Produces("text/plain")
    @RolesAllowed({"STUDENT"})
    public String aut() {
        System.out.println(securityContext.getUserPrincipal().getName());
        return  "<h1>OK</h1>";
    }
}
