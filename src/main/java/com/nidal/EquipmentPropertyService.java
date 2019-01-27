package com.nidal;

import com.nidal.repo.EquipmentPropertyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipmentPropertyService {

    @Autowired
    private EquipmentPropertyRepo equipmentPropertyRepo;

    @Transactional
    public void deleteAllRelationships() {
        equipmentPropertyRepo.deleteAllRelationships();
    }

    @Transactional
    public void deleteAllNodes() {
        equipmentPropertyRepo.deleteAllNodes();
    }

}
