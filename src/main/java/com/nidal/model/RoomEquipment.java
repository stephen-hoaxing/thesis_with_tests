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

    private Double height;

    private Double width;

    private Integer quantity;

    public RoomEquipment(String name, Double height, Double width, Integer quantity) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.quantity = quantity;
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

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double widht) {
        this.width = widht;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
