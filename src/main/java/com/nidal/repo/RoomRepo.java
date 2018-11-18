package com.nidal.repo;

import com.nidal.model.Room;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Nidal on 2017.10.23..
 */
public interface RoomRepo extends GraphRepository<Room> {

    @Query("MATCH (r:Room) WHERE r.name = {name} RETURN r")
    public Room getRoomByName(@Param("name") String name);

    @Query("MATCH ()-[r]-() DELETE r")
    public void deleteAllRelationships();

    @Query("MATCH (r:Room) DELETE r")
    public void deleteAllNodes();

    @Query("MATCH p = shortestPath((start)-[*]-(end)) " +
            "WHERE id(start) = {start} AND id(end) = {end} " +
            "RETURN EXTRACT (n in nodes(p) | n.name) as names")
    public Iterable<Map<String, String[]>> getShortestPath(@Param("start") Long start, @Param("end") Long end);

    /*@Query("MATCH p = shortestPath((start)-[*]-(end)) " +
            "WHERE id(start) = {start} AND id(end) = {end} " +
            "WITH nodes(p) as nds return nds")
    public Iterable<Map<String, List<Map<String, Object>>>> getShortestPath(@Param("start") Long start, @Param("end") Long end);*/

    @Query("MATCH p = allShortestPaths((start)-[*]-(end))" +
            "WHERE id(start)={start} AND id(end)={end} " +
            "WITH nodes(p) as nds " +
            "WHERE ALL (n in nds WHERE n.isAccessibleByWheelchair=true) " +
            "RETURN EXTRACT (item in nds | item.name) as names LIMIT 1")
    public Iterable<Map<String, String[]>> getShortestPathWithDisability(@Param("start") Long start, @Param("end") Long end);
}
