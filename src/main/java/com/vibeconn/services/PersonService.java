package com.vibeconn.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syncleus.ferma.tx.TxFactory;
import com.vibeconn.PersonView;
import com.vibeconn.mappers.MapToPersonMapper;
import com.vibeconn.models.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.unfold;

@ApplicationScoped
public class PersonService {
    @Inject
    TxFactory txFactory;

    ObjectMapper mapper = new ObjectMapper();

    @Inject
    MapToPersonMapper mapToPersonMapper;

    public  List<Map<String, Object>> getFramedPersons(){
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES,false);
        List<Person> persons = new ArrayList<>();
        System.out.println("time start"+ new Date().getTime());
        List<Map<String, Object>> gt = txFactory.tx().getGraph().getRawTraversal().V().valueMap("id","name").by(unfold()).project("id","name").by("id").by("name").limit(10).toList();

//        fg.traverse(graphTraversalSource -> graphTraversalSource.V("58:0")).next(Person.class);
//        Iterator personIterator = fg.traverse(GraphTraversalSource::V).frameExplicit(PersonView.class);
//        while (personIterator.hasNext()){
//            persons.add(personIterator.next());
//        }
//        PersonView p = mapToPersonMapper.fromMap(gt.iterator().next());
        System.out.println("mapping done "+ new Date().getTime());
        return gt;
    }
}
