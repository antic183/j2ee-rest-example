package app;

import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("test")
@Stateless
public class Service {

    @GET
    @Path("info")
    public String showInfo() {
        return "Info ...";
    }

    /**
     * -- handle query params --
     * -> http://......./api/v1/test/queryParams?first=anyString&second=5&data=anyString1&data=anyString2
     * <- first: anyString, second: 5, data[anyString1, anyString2]
     */
    @GET
    @Path("queryParams")
    public Response getUsers(
            @DefaultValue("defaultString") @QueryParam("first") String first,
            @DefaultValue("2") @QueryParam("second") int second,
            @DefaultValue("defaultValue") @QueryParam("data") List<String> data
    ) {
        return Response.status(200).entity("first: " + first + ", second: " + second + ", data" + data.toString()).build();
    }

    /**
     * -- post request without url-parameter -- 
     * request header: "Content-Type:application/json"
     * important: The json data must be sent as a string from the client side --> js: JSON.stringify(jsonData)
     * when you consumes json, you can only return json!
     */
    @POST
    @Path("getPerson/")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Person getPerson(Person p) {
        Person person = new Person("---> " + p.getFirstName(), "---> " + p.getLastName());
        return person;
    }

    // the same with url-parameter
    @POST
    @Path("getPerson/{param}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Person getPerson(Person p, @PathParam("param") String param) {
        Person person = new Person("---> " + p.getFirstName() + "-" + p.getLastName(), param);
        return person;
    }

    /**
     * -- post request with url-parameter -- 
     * request header: "Content-Type:application/x-www-form-urlencoded"
     */
    @POST
    @Path("getPerson/{param}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Person getPerson(String input, @PathParam("param") String param) {
        Person person = new Person(input, param);
        return person;
    }

    // the same without url-parameter
    @POST
    @Path("getPerson/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Person getPerson(String input) {
        Person person = new Person("input = " + input, "input = " + input);
        return person;
    }
}
