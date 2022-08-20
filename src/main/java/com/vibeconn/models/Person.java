package com.vibeconn.models;

import com.syncleus.ferma.AbstractVertexFrame;
import com.syncleus.ferma.annotations.GraphElement;
import com.syncleus.ferma.ext.AbstractInterceptingVertexFrame;

@GraphElement
public class Person extends AbstractInterceptingVertexFrame {
    private String name;
    private String rid;


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
}
