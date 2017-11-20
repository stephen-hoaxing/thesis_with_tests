package com.nidal.controller;

import com.nidal.OrganizationService;
import com.nidal.PoiService;
import com.nidal.RoomService;
import com.nidal.loader.Loader;
import com.nidal.model.Employee;
import com.nidal.model.Organization;
import com.nidal.model.PointOfInterest;
import com.nidal.model.Room;
import org.neo4j.ogm.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Nidal on 2017.10.22..
 */

@Controller
public class HomeController {

    @Autowired
    OrganizationService organizationService;

    @Autowired
    RoomService roomService;

    @Autowired
    PoiService poiService;

    @Autowired
    Neo4jOperations ops;

    @RequestMapping(value = "/")
    public ModelAndView home() {
        return new ModelAndView("test");
    }

    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public ModelAndView generateData() {
        roomService.deleteAllRelationships();
        roomService.deleteAllNodes();

        poiService.deleteAllRelationships();
        poiService.deleteAllNodes();

        Loader loader = new Loader();
        loader.Load();

        List<Room> rooms = new ArrayList<Room>();
        List<PointOfInterest> pois = new ArrayList<PointOfInterest>();

        for (Room room : loader.rooms) {
            rooms.add(room);
            roomService.saveRoom(room);
        }

        for (PointOfInterest poi : loader.pois) {
            pois.add(poi);
        }

        ModelAndView model = new ModelAndView("redirect:/roomlist");

        return model;
    }

    @RequestMapping("/roomlist")
    public ModelAndView getRoomList() {
        List<Room> rooms = new ArrayList<Room>();
        List<PointOfInterest> pois = new ArrayList<PointOfInterest>();

        for (Room room : roomService.getAllRooms()) {
            rooms.add(room);
        }

        for (PointOfInterest poi : poiService.getAllPois()) {
            pois.add(poi);
        }

        ModelAndView model = new ModelAndView("details");
        model.addObject("rooms", rooms);
        model.addObject("pois", pois);
        return model;
    }

    @RequestMapping("/poiinfo")
    public ModelAndView getPoiInfo(@RequestParam("poiid") String poiid) {

        ModelAndView model = new ModelAndView("poidetails");
        PointOfInterest poi = poiService.getPoiById(Long.parseLong(poiid));
        model.addObject("poi", poi);

        return model;
    }

    @RequestMapping("/roominfo")
    public ModelAndView getRoomInfo(@RequestParam("roomid") String roomid) {
        /*Iterable<Map<String, String[]>> p = roomService.getShortestPath("Entrance", "Hand Surgery");
        for (Map<String, String[]> act : p) {
            for (Map.Entry<String, String[]> entry : act.entrySet()) {
                String key = entry.getKey();
                String[] arr = entry.getValue();
                for (int i = 0; i < arr.length; i++) {
                    System.out.println(arr[i]);
                }
            }
        }*/

        ModelAndView model = new ModelAndView("roomdetails");
        Room room = roomService.getRoomById(Long.parseLong(roomid));
        model.addObject("room", room);
        return model;
    }

    @RequestMapping("/navigate")
    public ModelAndView navigationAction() {
        ModelAndView model = new ModelAndView("navigation");
        Iterable<Room> rooms = roomService.getAllRooms();
        Iterable<PointOfInterest> pois = poiService.getAllPois();
        model.addObject("pois", pois);
        model.addObject("rooms", rooms);
        return model;
    }

    private void addStationsToList(Iterable<Map<String, String[]>> p, List<String> list) {
        for (Map<String, String[]> act : p) {
            for (Map.Entry<String, String[]> entry : act.entrySet()) {
                String key = entry.getKey();
                String[] arr = entry.getValue();
                for (int i = 0; i < arr.length; i++) {
                    list.add(arr[i]);
                }
            }
        }
    }

    @RequestMapping(value = "/getnavigationdetails", method = RequestMethod.GET)
    public ModelAndView getNavigationDetails(@RequestParam("start") String start, @RequestParam("end") String end, @RequestParam(value = "isWheelchair", required = false) boolean isWheelchair) {
        if (Long.parseLong(start) == Long.parseLong(end)) {
            ModelAndView errorModel = new ModelAndView("errorpage");
            errorModel.addObject("errorMsg", "\"Start\" and \"End\" must be different.");
            return errorModel;
        }
        List<String> stations = new ArrayList<String>();
        if (isWheelchair == true) {
            Iterable<Map<String, String[]>> p = roomService.getShortestPathWithAccessFeature(Long.parseLong(start), Long.parseLong(end));
            addStationsToList(p, stations);
        } else {
            Iterable<Map<String, String[]>> p = roomService.getShortestPath(Long.parseLong(start), Long.parseLong(end));
            addStationsToList(p, stations);
        }
        ModelAndView model = new ModelAndView("submittednavigation");
        model.addObject("stations", stations);
        return model;
    }

}
