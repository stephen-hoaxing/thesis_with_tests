package com.nidal;

import com.nidal.repo.RoomEquipmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomEquipmentService {

    @Autowired
    RoomEquipmentRepo roomEquipmentRepo;

    @Transactional
    public void deleteAllRelationships() {
        roomEquipmentRepo.deleteAllRelationships();
    }

    @Transactional
    public  void deleteAllNodes() {
        roomEquipmentRepo.deleteAllNodes();
    }

}
