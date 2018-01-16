package com.nidal.repo;

import com.nidal.model.PointOfInterest;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Nidal on 2017.10.23..
 */
public interface PoiRepo extends GraphRepository<PointOfInterest> {

    @Query("MATCH (p:PointOfInterest) WHERE p.name = {name} RETURN p")
    public PointOfInterest getPoiByName(@Param("name") String name);

    @Query("MATCH ()-[r]-() DELETE r")
    public void deleteAllRelationships();

    @Query("MATCH (p:PointOfInterest) DELETE p")
    public void deleteAllNodes();

}
