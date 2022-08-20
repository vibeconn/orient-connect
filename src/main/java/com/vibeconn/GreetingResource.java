package com.vibeconn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.record.OVertex;
import org.apache.tinkerpop.gremlin.orientdb.OrientGraph;
import org.apache.tinkerpop.gremlin.orientdb.executor.OGremlinResultSet;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/hello")
public class GreetingResource {

    @Inject
    OrientGraph graph;
    @Inject
    ODatabaseSession dbSession;

    @GET
    @Path("/gremlinVertex")
    @Produces(MediaType.TEXT_PLAIN)
    public String addVertex() {
        Date d = new Date();
        System.out.println(graph);
        Vertex vertex = graph.addVertex("Person").property("name","arvind"+d).element();
//        vertex.property("dataCreated",String.valueOf(d.getTime()));
        return "RESTEasy Reactive: Added vertex with id ="+vertex.id();
    }

    @POST
    @Path("/orientVertex")
    @Produces(MediaType.TEXT_PLAIN)
    public String addOrientVertex(){
        Date d = new Date();
        OVertex vertex = dbSession.newVertex("Person");
      vertex.setProperty("name","v"+d);
      vertex.save();
      dbSession.close();
     return vertex.getIdentity().toString();
    }


    ObjectMapper mapper = new ObjectMapper();
    @Path("/list")
    @GET
    public List<Person> getVertices(){
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        List<Person> persons = new ArrayList<>();
        System.out.println("time start"+ new Date().getTime());

        OGremlinResultSet resultSet = graph.executeSql("select * from Person");
        System.out.println("db results fetched "+ new Date().getTime());
        resultSet.stream().forEach(re->{
            try {
                persons.add(mapper.readValue (re.getRawResult().toJSON(),Person.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        System.out.println("mapping done "+ new Date().getTime());
        return persons;
    }
}

