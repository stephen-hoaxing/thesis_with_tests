package com.nidal.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * Created by Nidal on 2017.10.23..
 */

@NodeEntity
public class PointOfInterest {
    @GraphId
    private Long id;

    @Relationship(type = "CONNECTS_POI", direction = Relationship.UNDIRECTED)
    private List<PointOfInterest> pois;

    private String number;

    private String name;

    private boolean isAccessibleByWheelchair;

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAccessibleByWheelchair() {

        return isAccessibleByWheelchair;
    }

    public void setAccessibleByWheelchair(boolean accessibleByWheelchair) {
        isAccessibleByWheelchair = accessibleByWheelchair;
    }

    public PointOfInterest(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public PointOfInterest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PointOfInterest> getPois() {
        return pois;
    }

    public void setPois(List<PointOfInterest> pois) {
        this.pois = pois;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setPoiName(String name) {
        this.name = name;
    }
}
