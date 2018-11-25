package com.nidal.controllertest;

import com.nidal.PoiService;
import com.nidal.RoomService;
import com.nidal.controller.HomeController;
import com.nidal.model.PointOfInterest;
import com.nidal.model.Room;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Nidal on 2017.11.27..
 */
public class ControllerTest {

    /**
     * servlet api must included to the pom.xml --> THESIS
     */
    @Mock
    private RoomService roomService;

    @Mock
    private PoiService poiService;

    @InjectMocks
    private HomeController homeController;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    public void welcomeScreen() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("test"));
    }

    @Test
    public void roomList() throws Exception {
        List<Room> rooms = new ArrayList<Room>();
        Room r1 = new Room("ROOM1", "Test Room 1", "Test Room");
        Room r2 = new Room("ROOM2", "Test Room 2", "Test Room");
        rooms.add(r1);
        rooms.add(r2);

        when(roomService.getAllRooms()).thenReturn((List) rooms);

        mockMvc.perform(get("/roomlist"))
                .andExpect(status().isOk())
                .andExpect(view().name("details"))
                .andExpect(model().attribute("rooms", hasSize(2)));
    }

    @Test
    public void roomDetails() throws Exception {
        Room room = new Room("ROOM1", "Test Room 1", "Test Room");
        room.setId(Long.parseLong("1"));

        when(roomService.getRoomById(room.getId())).thenReturn((Room) room);

        mockMvc.perform(get("/roominfo?roomid=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("roomdetails"))
                .andExpect(model().attribute("room", room));
    }

    @Test
    public void poiDetails() throws Exception {
        PointOfInterest poi = new PointOfInterest("POI1", "Test Poi");
        poi.setId(Long.parseLong("1"));

        when(poiService.getPoiById(poi.getId())).thenReturn((PointOfInterest) poi);

        mockMvc.perform(get("/poiinfo?poiid=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("poidetails"))
                .andExpect(model().attribute("poi", poi));
    }

    @Test
    public void shortestPath() throws Exception {
        Room start = new Room("S", "Starting Room", "Test Room");
        start.setId(Long.parseLong("10"));

        Room end = new Room("E", "Destination Room", "Test Room");
        end.setId(Long.parseLong("100"));

        PointOfInterest poi1 = new PointOfInterest("P1", "POI1");
        poi1.setId(Long.parseLong("1"));
        PointOfInterest poi2 = new PointOfInterest("P2", "POI2");
        poi2.setId(Long.parseLong("2"));
        PointOfInterest poi3 = new PointOfInterest("P3", "POI3");
        poi3.setId(Long.parseLong("3"));
        PointOfInterest poi4 = new PointOfInterest("P4", "POI4");
        poi4.setId(Long.parseLong("4"));
        PointOfInterest poi5 = new PointOfInterest("P5", "POI5");
        poi5.setId(Long.parseLong("5"));

        List<PointOfInterest> pois1 = new ArrayList<PointOfInterest>();
        pois1.add(poi5);
        pois1.add(poi4);
        pois1.add(poi2);
        poi1.setPois(pois1);

        List<PointOfInterest> pois2 = new ArrayList<PointOfInterest>();
        pois2.add(poi3);
        pois2.add(poi1);
        poi2.setPois(pois2);

        List<PointOfInterest> pois3 = new ArrayList<PointOfInterest>();
        pois3.add(poi4);
        pois3.add(poi2);
        poi3.setPois(pois3);

        List<PointOfInterest> pois4 = new ArrayList<PointOfInterest>();
        pois4.add(poi3);
        pois4.add(poi1);

        poi4.setPois(pois4);

        List<PointOfInterest> pois5 = new ArrayList<PointOfInterest>();
        pois5.add(poi1);
        poi5.setPois(pois5);

        poiService.savePoi(poi1);
        poiService.savePoi(poi2);
        poiService.savePoi(poi3);
        poiService.savePoi(poi4);
        poiService.savePoi(poi5);

        List<PointOfInterest> startPois = new ArrayList<PointOfInterest>();
        startPois.add(poi1);
        startPois.add(poi5);

        List<PointOfInterest> endPois = new ArrayList<PointOfInterest>();
        endPois.add(poi2);
        endPois.add(poi3);

        start.setPois(startPois);
        end.setPois(endPois);

        roomService.saveRoom(start);
        roomService.saveRoom(end);

        String[] stations = new String[] {"Starting Room", "POI1", "POI2", "Destination Room"};
        Map<String, String[]> map = new HashMap<String, String[]>();
        map.put("names", stations);
        List<Map<String, String[]>> expected = new ArrayList<Map<String, String[]>>();
        expected.add(map);

        when(roomService.getShortestPath(start.getId(), end.getId())).thenReturn((Iterable<Map<String,String[]>>) expected);

        mockMvc.perform(get("/getnavigationdetails?start=10&end=100"))
                .andExpect(status().isOk())
                .andExpect(view().name("submittednavigation"))
                .andExpect(model().attribute("stations", hasSize(4)));
    }

    @Test
    public void shortestPathWithDisability() throws Exception {
        Room start = new Room("S", "Starting Room", "Test Room");
        start.setId(Long.parseLong("10"));

        Room end = new Room("E", "Destination Room", "Test Room");
        end.setId(Long.parseLong("100"));

        PointOfInterest poi1 = new PointOfInterest("P1", "POI1");
        poi1.setId(Long.parseLong("1"));
        PointOfInterest poi2 = new PointOfInterest("P2", "POI2");
        poi2.setId(Long.parseLong("2"));
        PointOfInterest poi3 = new PointOfInterest("P3", "POI3");
        poi3.setId(Long.parseLong("3"));
        PointOfInterest poi4 = new PointOfInterest("P4", "POI4");
        poi4.setId(Long.parseLong("4"));
        PointOfInterest poi5 = new PointOfInterest("P5", "POI5");
        poi5.setId(Long.parseLong("5"));

        List<PointOfInterest> pois1 = new ArrayList<PointOfInterest>();
        pois1.add(poi5);
        pois1.add(poi4);
        pois1.add(poi2);
        poi1.setPois(pois1);

        List<PointOfInterest> pois2 = new ArrayList<PointOfInterest>();
        pois2.add(poi3);
        pois2.add(poi1);
        poi2.setPois(pois2);

        List<PointOfInterest> pois3 = new ArrayList<PointOfInterest>();
        pois3.add(poi4);
        pois3.add(poi2);
        poi3.setPois(pois3);

        List<PointOfInterest> pois4 = new ArrayList<PointOfInterest>();
        pois4.add(poi3);
        pois4.add(poi1);

        poi4.setPois(pois4);

        List<PointOfInterest> pois5 = new ArrayList<PointOfInterest>();
        pois5.add(poi1);
        poi5.setPois(pois5);

        poi1.setAccessibleByWheelchair(true);
        poi2.setAccessibleByWheelchair(false);
        poi3.setAccessibleByWheelchair(true);
        poi4.setAccessibleByWheelchair(true);
        poi5.setAccessibleByWheelchair(false);

        poiService.savePoi(poi1);
        poiService.savePoi(poi2);
        poiService.savePoi(poi3);
        poiService.savePoi(poi4);
        poiService.savePoi(poi5);

        List<PointOfInterest> startPois = new ArrayList<PointOfInterest>();
        startPois.add(poi1);
        startPois.add(poi5);

        List<PointOfInterest> endPois = new ArrayList<PointOfInterest>();
        endPois.add(poi2);
        endPois.add(poi3);

        start.setPois(startPois);
        end.setPois(endPois);

        roomService.saveRoom(start);
        roomService.saveRoom(end);

        String[] stations = new String[] {"Starting Room", "POI1", "POI4", "POI3", "Destination Room"};
        Map<String, String[]> map = new HashMap<String, String[]>();
        map.put("names", stations);
        List<Map<String, String[]>> expected = new ArrayList<Map<String, String[]>>();
        expected.add(map);

        when(roomService.getShortestPathWithAccessFeature(start.getId(), end.getId())).thenReturn((Iterable<Map<String,String[]>>) expected);

        mockMvc.perform(get("/getnavigationdetails?start=10&end=100&isWheelchair=true"))
                .andExpect(status().isOk())
                .andExpect(view().name("submittednavigation"))
                .andExpect(model().attribute("stations", hasSize(5)));
    }

    @Test
    public void navigationOption() throws Exception {
        Room start = new Room("S", "Starting Room", "Test Room");
        start.setId(Long.parseLong("10"));

        Room end = new Room("E", "Destination Room", "Test Room");
        end.setId(Long.parseLong("100"));

        PointOfInterest poi1 = new PointOfInterest("P1", "POI1");
        poi1.setId(Long.parseLong("1"));
        PointOfInterest poi2 = new PointOfInterest("P2", "POI2");
        poi2.setId(Long.parseLong("2"));
        PointOfInterest poi3 = new PointOfInterest("P3", "POI3");
        poi3.setId(Long.parseLong("3"));
        PointOfInterest poi4 = new PointOfInterest("P4", "POI4");
        poi4.setId(Long.parseLong("4"));
        PointOfInterest poi5 = new PointOfInterest("P5", "POI5");
        poi5.setId(Long.parseLong("5"));

        List<PointOfInterest> pois1 = new ArrayList<PointOfInterest>();
        pois1.add(poi5);
        pois1.add(poi4);
        pois1.add(poi2);
        poi1.setPois(pois1);

        List<PointOfInterest> pois2 = new ArrayList<PointOfInterest>();
        pois2.add(poi3);
        pois2.add(poi1);
        poi2.setPois(pois2);

        List<PointOfInterest> pois3 = new ArrayList<PointOfInterest>();
        pois3.add(poi4);
        pois3.add(poi2);
        poi3.setPois(pois3);

        List<PointOfInterest> pois4 = new ArrayList<PointOfInterest>();
        pois4.add(poi3);
        pois4.add(poi1);

        poi4.setPois(pois4);

        List<PointOfInterest> pois5 = new ArrayList<PointOfInterest>();
        pois5.add(poi1);
        poi5.setPois(pois5);

        poiService.savePoi(poi1);
        poiService.savePoi(poi2);
        poiService.savePoi(poi3);
        poiService.savePoi(poi4);
        poiService.savePoi(poi5);

        List<PointOfInterest> startPois = new ArrayList<PointOfInterest>();
        startPois.add(poi1);
        startPois.add(poi5);

        List<PointOfInterest> endPois = new ArrayList<PointOfInterest>();
        endPois.add(poi2);
        endPois.add(poi3);

        start.setPois(startPois);
        end.setPois(endPois);

        roomService.saveRoom(start);
        roomService.saveRoom(end);

        List<Room> rooms = new ArrayList<Room>();
        rooms.add(start);
        rooms.add(end);

        List<PointOfInterest> pois = new ArrayList<PointOfInterest>();
        pois.add(poi1);
        pois.add(poi2);
        pois.add(poi3);
        pois.add(poi4);
        pois.add(poi5);

        when(roomService.getAllRooms()).thenReturn((List<Room>) rooms);
        when(poiService.getAllPois()).thenReturn((List<PointOfInterest>) pois);

        mockMvc.perform(get("/navigate"))
                .andExpect(status().isOk())
                .andExpect(view().name("navigation"))
                .andExpect(model().attribute("rooms", hasSize(2)))
                .andExpect(model().attribute("pois", hasSize(5)));
    }

    @Test
    public void simpleTrivialPath() throws Exception {
        Room start = new Room("S", "Starting Room", "Test Room");
        start.setId(Long.parseLong("10"));

        Room end = new Room("E", "Destination Room", "Test Room");
        end.setId(Long.parseLong("100"));

        PointOfInterest poi1 = new PointOfInterest("P1", "POI1");

        poiService.savePoi(poi1);

        List<PointOfInterest> startPois = new ArrayList<PointOfInterest>();
        List<PointOfInterest> endPois = new ArrayList<PointOfInterest>();
        startPois.add(poi1);
        endPois.add(poi1);

        roomService.saveRoom(start);
        roomService.saveRoom(end);

        String[] stations = new String[] {"Starting Room", "POI1", "Destination Room"};
        Map<String, String[]> map = new HashMap<String, String[]>();
        map.put("names", stations);
        List<Map<String, String[]>> expected = new ArrayList<Map<String, String[]>>();
        expected.add(map);

        when(roomService.getShortestPath(start.getId(), end.getId())).thenReturn((Iterable<Map<String,String[]>>) expected);

        mockMvc.perform(get("/getnavigationdetails?start=10&end=100"))
                .andExpect(status().isOk())
                .andExpect(view().name("submittednavigation"))
                .andExpect(model().attribute("stations", hasSize(3)));
    }

    @Test
    public void simpleTrivialPathWithWheelchairAccess() throws Exception {
        Room start = new Room("S", "Starting Room", "Test Room");
        start.setId(Long.parseLong("10"));
        start.setAccessibleByWheelchair(true);

        Room end = new Room("E", "Destination Room", "Test Room");
        end.setId(Long.parseLong("100"));
        end.setAccessibleByWheelchair(true);

        PointOfInterest poi1 = new PointOfInterest("P1", "POI1");
        poi1.setAccessibleByWheelchair(true);

        poiService.savePoi(poi1);

        List<PointOfInterest> startPois = new ArrayList<PointOfInterest>();
        List<PointOfInterest> endPois = new ArrayList<PointOfInterest>();
        startPois.add(poi1);
        endPois.add(poi1);

        roomService.saveRoom(start);
        roomService.saveRoom(end);

        String[] stations = new String[] {"Starting Room", "POI1", "Destination Room"};
        Map<String, String[]> map = new HashMap<String, String[]>();
        map.put("names", stations);
        List<Map<String, String[]>> expected = new ArrayList<Map<String, String[]>>();
        expected.add(map);

        when(roomService.getShortestPath(start.getId(), end.getId())).thenReturn((Iterable<Map<String,String[]>>) expected);

        mockMvc.perform(get("/getnavigationdetails?start=10&end=100"))
                .andExpect(status().isOk())
                .andExpect(view().name("submittednavigation"))
                .andExpect(model().attribute("stations", hasSize(3)));
    }

    @Test
    public void nonExistentPath() throws Exception {
        Room start = new Room("S", "Starting Room", "Test Room");
        start.setId(Long.parseLong("10"));

        Room end = new Room("E", "Destination Room", "Test Room");
        end.setId(Long.parseLong("100"));

        PointOfInterest poi1 = new PointOfInterest("P1", "POI1");
        PointOfInterest poi2 = new PointOfInterest("P2", "POI2");

        List<PointOfInterest> pois1 = new ArrayList<PointOfInterest>();
        pois1.add(poi2);

        List<PointOfInterest> pois2 = new ArrayList<PointOfInterest>();
        pois2.add(poi1);

        poiService.savePoi(poi1);
        poiService.savePoi(poi2);

        List<PointOfInterest> startPois = new ArrayList<PointOfInterest>();
        startPois.add(poi1);
        startPois.add(poi2);

        roomService.saveRoom(start);
        roomService.saveRoom(end);

        String[] stations = new String[] { };
        Map<String, String[]> map = new HashMap<String, String[]>();
        map.put("names", stations);
        List<Map<String, String[]>> expected = new ArrayList<Map<String, String[]>>();
        expected.add(map);

        when(roomService.getShortestPath(start.getId(), end.getId())).thenReturn((Iterable<Map<String,String[]>>) expected);

        mockMvc.perform(get("/getnavigationdetails?start=10&end=100"))
                .andExpect(status().isOk())
                .andExpect(view().name("submittednavigation"))
                .andExpect(model().attribute("stations", hasSize(0)));
    }

    @Test
    public void nonExistentPathWithWheelchairAccess() throws Exception {
        Room start = new Room("S", "Starting Room", "Test Room");
        start.setId(Long.parseLong("10"));

        Room end = new Room("E", "Destination Room", "Test Room");
        end.setId(Long.parseLong("100"));

        PointOfInterest poi1 = new PointOfInterest("P1", "POI1");
        poi1.setId(Long.parseLong("1"));
        PointOfInterest poi2 = new PointOfInterest("P2", "POI2");
        poi2.setId(Long.parseLong("2"));
        PointOfInterest poi3 = new PointOfInterest("P3", "POI3");
        poi3.setId(Long.parseLong("3"));
        PointOfInterest poi4 = new PointOfInterest("P4", "POI4");
        poi4.setId(Long.parseLong("4"));
        PointOfInterest poi5 = new PointOfInterest("P5", "POI5");
        poi5.setId(Long.parseLong("5"));

        List<PointOfInterest> pois1 = new ArrayList<PointOfInterest>();
        pois1.add(poi5);
        pois1.add(poi4);
        pois1.add(poi2);
        poi1.setPois(pois1);

        List<PointOfInterest> pois2 = new ArrayList<PointOfInterest>();
        pois2.add(poi3);
        pois2.add(poi1);
        poi2.setPois(pois2);

        List<PointOfInterest> pois3 = new ArrayList<PointOfInterest>();
        pois3.add(poi4);
        pois3.add(poi2);
        poi3.setPois(pois3);

        List<PointOfInterest> pois4 = new ArrayList<PointOfInterest>();
        pois4.add(poi3);
        pois4.add(poi1);

        poi4.setPois(pois4);

        List<PointOfInterest> pois5 = new ArrayList<PointOfInterest>();
        pois5.add(poi1);
        poi5.setPois(pois5);

        poi1.setAccessibleByWheelchair(false);
        poi2.setAccessibleByWheelchair(false);
        poi3.setAccessibleByWheelchair(false);
        poi4.setAccessibleByWheelchair(false);
        poi5.setAccessibleByWheelchair(false);

        poiService.savePoi(poi1);
        poiService.savePoi(poi2);
        poiService.savePoi(poi3);
        poiService.savePoi(poi4);
        poiService.savePoi(poi5);

        List<PointOfInterest> startPois = new ArrayList<PointOfInterest>();
        startPois.add(poi1);
        startPois.add(poi5);

        List<PointOfInterest> endPois = new ArrayList<PointOfInterest>();
        endPois.add(poi2);
        endPois.add(poi3);

        start.setPois(startPois);
        end.setPois(endPois);

        roomService.saveRoom(start);
        roomService.saveRoom(end);

        String[] stations = new String[] { };
        Map<String, String[]> map = new HashMap<String, String[]>();
        map.put("names", stations);
        List<Map<String, String[]>> expected = new ArrayList<Map<String, String[]>>();
        expected.add(map);

        when(roomService.getShortestPathWithAccessFeature(start.getId(), end.getId())).thenReturn((Iterable<Map<String,String[]>>) expected);

        mockMvc.perform(get("/getnavigationdetails?start=10&end=100"))
                .andExpect(status().isOk())
                .andExpect(view().name("submittednavigation"))
                .andExpect(model().attribute("stations", hasSize(0)));
    }
}
