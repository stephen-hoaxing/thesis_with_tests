package com.nidal;

import com.nidal.model.Employee;
import com.nidal.model.Organization;
import com.nidal.repo.OrganizationRepo;
import org.neo4j.ogm.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Nidal on 2017.10.22..
 */

@Service
public class OrganizationService {

    @Autowired
    OrganizationRepo organizationRepo;

    @Autowired
    Neo4jOperations ops;

    @Transactional
    public void saveOrganization(Organization organization) {

        organizationRepo.save(organization);
    }

    @Transactional
    public void deleteAllRelationships() {
        organizationRepo.deleteAllRelationships();
    }

    @Transactional
    public void deleteAllNodes() {
        organizationRepo.deleteAllNodes();
    }

    @Transactional
    public Organization getOrganizationById(Long id) {
        return organizationRepo.findOne(id);
    }

    @Transactional
    public Iterable<Map<String, Object>> getEmployeeNodes() {
        return organizationRepo.getEmployeeNodes();
    }

    @Transactional
    public Iterable<Map<String,String[]>> getShortestPath(String start, String end) {
        return organizationRepo.getShortestPath(start, end);
    }
}
