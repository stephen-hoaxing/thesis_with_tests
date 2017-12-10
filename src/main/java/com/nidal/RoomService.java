package com.nidal;

import com.nidal.model.Room;
import com.nidal.repo.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Nidal on 2017.10.23..
 */

@Service
public class RoomService {

    @Autowired
    RoomRepo roomRepo;

    @Transactional
    public void deleteAllRelationships() {
        roomRepo.deleteAllRelationships();
    }

    @Transactional
    public void deleteAllNodes() {
        roomRepo.deleteAllNodes();
    }

    @Transactional
    public void saveRoom(Room room) {
        roomRepo.save(room);
    }

    /*@Transactional
    public Iterable<Map<String,String[]>> getShortestPath(Long start, Long end) {
        return roomRepo.getShortestPath(start, end);
    }*/

    @Transactional
    public Iterable<Map<String, String[]>> getShortestPath(Long start, Long end) {
        return roomRepo.getShortestPath(start, end);
    }

    @Transactional
    public Iterable<Map<String, String[]>> getShortestPathWithAccessFeature(Long start, Long end) {
        return roomRepo.getShortestPathWithDisability(start, end);
    }

    @Transactional
    public Room getRoomByName(String name) {
        return roomRepo.getRoomByName(name);
    }

    @Transactional
    public Room getRoomById(Long id) {
        return roomRepo.findOne(id);
    }

    @Transactional
    public Iterable<Room> getAllRooms() {
        return roomRepo.findAll();
    }

}
