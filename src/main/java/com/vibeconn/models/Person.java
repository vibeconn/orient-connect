package com.vibeconn.models;

import com.syncleus.ferma.annotations.GraphElement;
import com.syncleus.ferma.ext.AbstractInterceptingVertexFrame;

@GraphElement
public class Person extends AbstractInterceptingVertexFrame {
    private String name;
    private String rid;

    public Person(String name, String rid) {
        this.name = name;
        this.rid = rid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setName(String name) {
        setProperty("name", name);
    }

    public String getName() {
        return getProperty("name");
    }

    public void addFriend(Person person){
        linkOut(person,"FRIEND_OF");
    }

    public void follow(Person otherPerson){
        linkOut(otherPerson,"Follows");
    }
}
