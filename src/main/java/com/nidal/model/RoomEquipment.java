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

    @Relationship(type = "HAS_EQUIPMENT", direction = Relationship.UNDIRECTED)
    private List<RoomEquipment> roomEquipments;

    private String name;

    private Double height;

    private Double widht;

    private Integer quantity;

    public RoomEquipment(String name, Double height, Double widht, Integer quantity) {
        this.name = name;
        this.height = height;
        this.widht = widht;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RoomEquipment> getRoomEquipments() {
        return roomEquipments;
    }

    public void setRoomEquipments(List<RoomEquipment> roomEquipments) {
        this.roomEquipments = roomEquipments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidht() {
        return widht;
    }

    public void setWidht(Double widht) {
        this.widht = widht;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
