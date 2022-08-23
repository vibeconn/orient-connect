package com.vibeconn.models;

import com.syncleus.ferma.annotations.GraphElement;
import com.syncleus.ferma.ext.AbstractInterceptingEdgeFrame;

import java.util.Date;

@GraphElement
public class FriendOf extends AbstractInterceptingEdgeFrame {

    public void setLabel(){
        setProperty("label","FRIEND_OF");
    }

    public void setCreated(){
        setProperty("createdOn",new Date().getTime());
    }
}
