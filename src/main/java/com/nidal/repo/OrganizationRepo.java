package com.nidal.repo;

import com.fasterxml.jackson.databind.ser.std.IterableSerializer;
import com.nidal.model.Employee;
import com.nidal.model.Organization;
import org.neo4j.ogm.model.Result;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Nidal on 2017.10.22..
 */
public interface OrganizationRepo extends GraphRepository<Organization> {

    @Query("match ()-[r]-() delete r")
    public void deleteAllRelationships();

    @Query("match (o) delete o")
    public void deleteAllNodes();

    @Query("match (e:Employee) return e.name")
    public Iterable<Map<String, Object>> getEmployeeNodes();

    @Query("match (s:Employee), (e:Employee), p = shortestPath((s)-[*]-(e)) " +
            "where s.name = {start} and e.name = {end} " +
            "return extract (n in nodes(p) | n.name) as names")
    public Iterable<Map<String,String[]>> getShortestPath(@Param("start") String start, @Param("end") String end);
}
