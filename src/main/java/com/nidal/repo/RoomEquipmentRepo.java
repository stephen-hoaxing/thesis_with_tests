package com.nidal.repo;

import com.nidal.model.RoomEquipment;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface RoomEquipmentRepo extends GraphRepository<RoomEquipment> {

    @Query("MATCH ()-[r:HAS_EQUIPMENT]-() delete r")
    public void deleteAllRelationships();

    @Query("MATCH (e:RoomEquipment) delete e")
    public void deleteAllNodes();

}
