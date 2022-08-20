package com.vibeconn;

import com.fasterxml.jackson.annotation.JsonProperty;

class Person {
    private String name;
    @JsonProperty("@rid")
    private String rid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
}
