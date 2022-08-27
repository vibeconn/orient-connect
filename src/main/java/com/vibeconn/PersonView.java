package com.vibeconn;

import com.fasterxml.jackson.annotation.JsonAlias;

public class PersonView  {

    public PersonView() {
    }

    @JsonAlias("@rid")
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PersonView{" +
                "name='" + name + '\'' +
                '}';
    }
}
