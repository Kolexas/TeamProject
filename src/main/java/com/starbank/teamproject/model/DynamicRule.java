package com.starbank.teamproject.model;

import java.io.Serializable;


public class DynamicRule implements Serializable {

    private String query;

    private String[] arguments;

    private boolean negate;


    public DynamicRule(String query, String[] arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }

    public DynamicRule() {

    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }
}
