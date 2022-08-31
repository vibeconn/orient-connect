package com.vibeconn.controllers;

import com.vibeconn.models.hibernate.City;
import com.vibeconn.repositoties.CityRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/city")
public class CityController {

    @Inject
    CityRepository cityRepository;

    @Path("/list")
    @GET
    public String getCities(){
        return cityRepository.findCities().toString();
    }

    @Path("/create")
    @POST
    public City createCity(){
        return cityRepository.createCity();
    }
}
