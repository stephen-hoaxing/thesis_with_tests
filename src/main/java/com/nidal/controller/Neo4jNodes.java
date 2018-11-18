package com.nidal.controller;

import com.google.common.collect.Lists;
import com.nidal.RoomService;
import com.nidal.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nidal on 2018.11.18..
 */

@RestController
public class Neo4jNodes {

    @Autowired
    RoomService roomService;

    @RequestMapping(
            value = "getallrooms",
            method = RequestMethod.GET,
            produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
            headers = "Accept=application/json"
    )
    public List<Room> getAllRooms() {
        return Lists.newArrayList(roomService.getAllRooms());
    }

}
