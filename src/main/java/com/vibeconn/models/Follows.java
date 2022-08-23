package com.vibeconn.models;

import com.syncleus.ferma.annotations.GraphElement;
import com.syncleus.ferma.ext.AbstractInterceptingEdgeFrame;

import java.util.Date;

public class Follows extends AbstractInterceptingEdgeFrame {

    public void setLabel(){
        setProperty("label","FOLLOWS");
    }

    public void setCreated(){
        setProperty("createdOn",new Date().getTime());
    }
}
