package com.nidal;

import com.nidal.model.PointOfInterest;
import com.nidal.repo.PoiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Nidal on 2017.10.23..
 */

@Service
public class PoiService {

    @Autowired
    PoiRepo poiRepo;

    @Transactional
    public void deleteAllRelationships() {
        poiRepo.deleteAllRelationships();
    }

    @Transactional
    public void deleteAllNodes() {
        poiRepo.deleteAllNodes();
    }

   @Transactional
    public PointOfInterest getPoiByName(String name) {
        return poiRepo.getPoiByName(name);
    }

    @Transactional
    public PointOfInterest getPoiById(Long id) {
        return poiRepo.findOne(id);
    }

    @Transactional
    public Iterable<PointOfInterest> getAllPois() {
        return poiRepo.findAll();
    }

    @Transactional
    public void savePoi(PointOfInterest poi) { poiRepo.save(poi); }

}
