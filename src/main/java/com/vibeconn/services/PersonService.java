package com.vibeconn.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.tx.TxFactory;
import com.vibeconn.mappers.MapToPersonMapper;
import com.vibeconn.models.Person;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.shaded.jackson.core.JsonProcessingException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@ApplicationScoped
public class PersonService {
    @Inject
    TxFactory txFactory;

    ObjectMapper mapper = new ObjectMapper();

    @Inject
    MapToPersonMapper mapToPersonMapper;
    @Inject
    FramedGraph framedGraph;

    public List<? extends Person> getFramedPersons() throws JsonProcessingException {
//        var graphSonMapper = GraphSONMapper.build()
//                .addRegistry(OrientIoRegistry.getInstance())
//                .version(GraphSONVersion.V3_0).create();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
//        mapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES,false);
        List<Person> p1 = new ArrayList<>();
        Iterator<Person> persons = framedGraph.traverse((g)->g.V().elementMap("id","name")).frame(Person.class);
        while (persons.hasNext()){
            p1.add(persons.next());
        }
//        framedGraph.frame(framedGraph.traverse(GraphTraversalSource::V),Person.class);
        System.out.println("time start"+ new Date().getTime());
        return p1;
    }
}
