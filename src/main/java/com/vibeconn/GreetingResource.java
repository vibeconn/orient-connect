package com.vibeconn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.tx.Tx;
import com.syncleus.ferma.tx.TxFactory;
import com.vibeconn.models.Person;
import org.apache.tinkerpop.gremlin.orientdb.OrientGraph;
import org.apache.tinkerpop.gremlin.orientdb.executor.OGremlinResultSet;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/hello")
public class GreetingResource {

    @Inject
    OrientGraph graph;
    @Inject
    ODatabaseSession dbSession;
    @Inject
    FramedGraph framedGraph;
    @Inject
    TxFactory txFactory;

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
        System.out.println("start orient vertex"+new Date().getTime());
        OVertex vertex = dbSession.newVertex("Person");
      vertex.setProperty("name","v"+d);
      vertex.save();
      dbSession.close();
        System.out.println("end orient vertex"+new Date().getTime());
     return vertex.getIdentity().toString();
    }

    @POST
    @Path("/framedVertex")
    @Produces(MediaType.TEXT_PLAIN)
    public String addFramedVertex(){
        Date d = new Date();
        Tx tx = txFactory.tx();
        System.out.println("start transaction"+new Date().getTime());
       Person person = tx.getGraph().addFramedVertex(Person.class);
       person.setName("Kanishk"+d);
        tx.commit();
        System.out.println("end transaction"+new Date().getTime());
        return person.getId().toString();
    }

    @POST
    @Path("/framedEdge")
    @Produces(MediaType.TEXT_PLAIN)
    public String addFramedEdge(@QueryParam("from") String from,@QueryParam("to") String to){
        Date d = new Date();
        System.out.println("start transaction"+new Date().getTime());
        Tx tx = txFactory.tx();
        Person person = tx.getGraph().getFramedVertex(Person.class,from);
        Person person2 = tx.getGraph().getFramedVertex(Person.class,to);
        person.follow(person2);
        tx.commit();
        System.out.println("end transaction"+new Date().getTime());
        return person2.getId().toString();
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

    @Path("/framedList")
    @GET
    public List<Map<String,Object>> getFramedPersons(){
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES,false);
        List<Person> persons = new ArrayList<>();
        System.out.println("time start"+ new Date().getTime());
        List<Map<String,Object>> elements = txFactory.tx().getGraph().getRawTraversal().V().project("name").by("name").toList();
//        fg.traverse(graphTraversalSource -> graphTraversalSource.V("58:0")).next(Person.class);
//        Iterator personIterator = fg.traverse(GraphTraversalSource::V).frameExplicit(PersonView.class);
//        while (personIterator.hasNext()){
//            persons.add(personIterator.next());
//        }
        System.out.println("mapping done "+ new Date().getTime());
        return elements;
    }

    @Path("/orientList")
    @GET
    public List<PersonView> getOrientPersons(){
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES,false);
        List<PersonView> persons = new ArrayList<>();
        System.out.println("time start"+ new Date().getTime());

        OResultSet resultSet = dbSession.query("Select * from Person limit 10");
//        resultSet.close();
        System.out.println("db query executed at"+ new Date().getTime());

        while (resultSet.hasNext()) {
                try {
                    persons.add(mapper.readValue(resultSet.next().toJSON(), PersonView.class));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
        }
        resultSet.close();
        System.out.println("mapping done "+ new Date().getTime());
//         resultSet.close();
         return persons;
    }
}

