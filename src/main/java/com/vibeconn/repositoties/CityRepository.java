package com.vibeconn.repositoties;

import com.orientechnologies.orient.core.db.object.ODatabaseObject;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import com.vibeconn.models.hibernate.City;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CityRepository {

    @Inject
    ODatabaseObject dbObject;

    public OResultSet findCities(){
        return dbObject.execute("sql","select * from City");
    }

    public City createCity(){
        City city = dbObject.newInstance(City.class);
        city.setName("Faridabad");
        city = dbObject.save(city);
        return city;
    }
}
