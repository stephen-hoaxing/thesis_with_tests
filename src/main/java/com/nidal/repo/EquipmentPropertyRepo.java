package com.nidal.repo;

import com.nidal.model.EquipmentProperty;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface EquipmentPropertyRepo extends GraphRepository<EquipmentProperty> {

    @Query("Match ()-[r:HAS_PROPERTY]-() DELETE r")
    void deleteAllRelationships();

    @Query("MATCH (p:EquipmentProperty) DELETE p")
    void deleteAllNodes();

}
