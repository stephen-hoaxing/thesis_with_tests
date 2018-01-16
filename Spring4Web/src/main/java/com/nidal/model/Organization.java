package com.nidal.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * Created by Nidal on 2017.10.22..
 */

@NodeEntity
public class Organization {

    @GraphId
    private Long id;

    private String name;

    @Relationship(type = "HAS", direction = Relationship.OUTGOING)
    private List<Employee> employees;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Organization(String name, List<Employee> employees) {
        this.name = name;
        this.employees = employees;
    }
}
