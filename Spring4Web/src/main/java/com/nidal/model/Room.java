package com.nidal.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * Created by Nidal on 2017.10.23..
 */

@NodeEntity
public class Room {
    @GraphId
    private Long id;

    @Relationship(type = "HAS_POI", direction = Relationship.OUTGOING)
    private List<PointOfInterest> pois;

    private String number;

    private String name;

    private String roomType;

    private boolean isAccessibleByWheelchair;

    public boolean isAccessibleByWheelchair() {
        return isAccessibleByWheelchair;
    }

    public void setAccessibleByWheelchair(boolean accessibleByWheelchair) {
        isAccessibleByWheelchair = accessibleByWheelchair;
    }

    public Room(String number, String name, String roomType) {
        this.number = number;
        this.name = name;
        this.roomType = roomType;
    }

    public Room() {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
