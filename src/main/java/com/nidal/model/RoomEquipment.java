package com.nidal.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * Created by Nidal on 2018.11.18..
 */

@NodeEntity
public class RoomEquipment {

    @GraphId
    private Long id;

    private String name;

    @Relationship(type = "HAS_PROPERTY", direction = Relationship.UNDIRECTED)
    private List<EquipmentProperty> equipmentProperties;

    public RoomEquipment() {
    }

    public RoomEquipment(String name, List<EquipmentProperty> equipmentProperties) {
        this.name = name;
        this.equipmentProperties = equipmentProperties;
    }

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

    public List<EquipmentProperty> getEquipmentProperties() {
        return equipmentProperties;
    }

    public void setEquipmentProperties(List<EquipmentProperty> equipmentProperties) {
        this.equipmentProperties = equipmentProperties;
    }
}
