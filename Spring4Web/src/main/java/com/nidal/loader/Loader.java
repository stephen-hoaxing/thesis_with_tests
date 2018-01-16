package com.nidal.loader;

import com.nidal.model.PointOfInterest;
import com.nidal.model.Room;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.nidal.model.PointOfInterest;
import com.nidal.model.Room;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.commons.lang3.StringUtils.*;

/**
 * Created by Nidal on 2017.11.11..
 */
public class Loader {

        public List<Room> rooms;
        public List<PointOfInterest> pois;

        public static String readFile() {
            String result = "";
            try {
                BufferedReader reader = new BufferedReader(new FileReader("c:\\Users\\Nidal\\Desktop\\Owls\\honved_json_final.json"));
                StringBuilder builder = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = reader.readLine();
                }
                result = builder.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        public void Load() {
            String jsonData = readFile();
            JSONObject obj = new JSONObject(jsonData);
            JSONArray arr = obj.getJSONArray("rooms");
            // System.out.println(arr);
            this.pois = new ArrayList<PointOfInterest>();
            this.rooms = new ArrayList<Room>();

            for (int i = 0; i < arr.length(); i++) {
                String id = arr.getJSONObject(i).getString("@id");
                id = id.substring(id.indexOf('#') + 1, id.length());
                JSONArray types = arr.getJSONObject(i).getJSONArray("@type");
                for (int j = 0; j < types.length(); j++) {
                    String type = types.getString(j);
                    type = type.substring(type.indexOf('#') + 1, type.length());
                    // System.out.println(type);
                    if (type.equals("POI")) {
                        // System.out.println(type);
                        if (arr.getJSONObject(i).has("http://www.w3.org/2000/01/rdf-schema#label")) {
                            JSONArray values = arr.getJSONObject(i).getJSONArray("http://www.w3.org/2000/01/rdf-schema#label");
                            for (int k = 0; k < values.length(); k++) {
                                // System.out.println(values.getJSONObject(k).getString("@value"));
                                String value = values.getJSONObject(k).getString("@value");
                                PointOfInterest poi = new PointOfInterest(id, value);
                                if (arr.getJSONObject(i).has("http://lod.nik.uni-obuda.hu/iloc/iloc#hasAccess")) {
                                    poi.setAccessibleByWheelchair(true);
                                } else {
                                    poi.setAccessibleByWheelchair(false);
                                }
                                pois.add(poi);
                            }
                        }
                    } else if (!type.equals("POI") && !type.equals("Elevator") && !type.equals("Entrance")) {
                        if (arr.getJSONObject(i).has("http://www.w3.org/2000/01/rdf-schema#label")) {
                            JSONArray values = arr.getJSONObject(i).getJSONArray("http://www.w3.org/2000/01/rdf-schema#label");
                            for (int k = 0; k < values.length(); k++) {
                                // System.out.println(values.getJSONObject(k).getString("@value"));
                                String value = values.getJSONObject(k).getString("@value");
                                // System.out.println(value);
                                if (type.equals("NamedIndividual") || type.equals("POI")) {
                                    continue;
                                } else {
                                    type = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(type.replaceAll("\\d", "")), " ");
                                    Room room = new Room(id, value, type);
                                    if (arr.getJSONObject(i).has("http://lod.nik.uni-obuda.hu/iloc/iloc#hasAccess")) {
                                        room.setAccessibleByWheelchair(true);
                                    } else {
                                        room.setAccessibleByWheelchair(false);
                                    }
                                    if (!pois.stream().filter(p -> p.getNumber().equals(room.getNumber())).findFirst().isPresent()) {
                                        rooms.add(room);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // POIs should be only: entrance, stairs, hallways

        /*System.out.println("----------------POIS---------------------------");

        for (PointOfInterest poi: pois) {
            System.out.println(poi.getPoiNumber() + ", " + poi.getPoiName());
        }


        System.out.println("----------------ROOMS---------------------------");
        for (Room room : rooms) {
            System.out.println(room.getRoomNumber() + ", " + room.getRoomType() + ", " + room.getRoomName());
        }*/

            for (int i = 0; i < arr.length(); i++) {
                if (arr.getJSONObject(i).has("http://lod.nik.uni-obuda.hu/iloc/iloc#connectsPOI")) {
                    // retrieve the poi which has connected pois
                    String id = arr.getJSONObject(i).getString("@id");
                    String poiid = id.substring(id.indexOf('#') + 1, id.length());
                    PointOfInterest actpoi = pois.stream().filter(x -> x.getNumber().equals(poiid)).findFirst().get();
                    // poi is retrieved

                    // find connectedpois
                    JSONArray connectedpois = arr.getJSONObject(i).getJSONArray("http://lod.nik.uni-obuda.hu/iloc/iloc#connectsPOI");
                    List<PointOfInterest> connpois = new ArrayList<PointOfInterest>();
                    // System.out.println("ACT: " + actpoi.getPoiName());
                    for (int j = 0; j < connectedpois.length(); j++) {
                        String connid = connectedpois.getJSONObject(j).getString("@id");
                        // System.out.println(connid);
                        String realConnid = connid.substring(connid.indexOf('#') + 1, connid.length());
                        // System.out.println(realConnid);
                        PointOfInterest connpoireal = pois.stream().filter(x -> x.getNumber().equals(realConnid)).findAny().get();
                        // System.out.println(connpoireal.getPoiName());
                        connpois.add(connpoireal);
                    }
                    // System.out.println("---------------------------------------");
                /*for (PointOfInterest p : connpois) {
                    System.out.println(p.getPoiName());
                }*/
                    actpoi.setPois(connpois);
                }
            }

        /*System.out.println("------------------------------------------");
        for (PointOfInterest poi : pois) {
            System.out.println(poi.getPoiName());
            if (poi.getPois() != null) {
                for (PointOfInterest p : poi.getPois()) {
                    System.out.println("\t\t - " + p.getPoiName());
                }
            }
        }*/

            for (int i = 0; i < arr.length(); i++) {
                if (arr.getJSONObject(i).has("http://lod.nik.uni-obuda.hu/iloc/iloc#hasPOI")) {
                    // retrieve the room
                    String id = arr.getJSONObject(i).getString("@id");
                    String roomid = id.substring(id.indexOf('#') + 1, id.length());
                    // System.out.println(roomid);
                    Room room = rooms.stream().filter(x -> x.getNumber().equals(roomid)).findFirst().get();
                    // System.out.println(room.getRoomName());
                    // retrieval is done
                    List<PointOfInterest> roompois = new ArrayList<PointOfInterest>();
                    JSONArray haspois = arr.getJSONObject(i).getJSONArray("http://lod.nik.uni-obuda.hu/iloc/iloc#hasPOI");
                    for (int j = 0; j < haspois.length(); j++) {
                        String pid = haspois.getJSONObject(j).getString("@id");
                        String poiid = pid.substring(pid.indexOf('#') + 1, pid.length());
                        // System.out.println(poiid);
                        PointOfInterest actpoi = pois.stream().filter(x -> x.getNumber().equals(poiid)).findFirst().get();
                        roompois.add(actpoi);
                    }
                    room.setPois(roompois);
                }
            }

        /*for (Room room : rooms) {
            System.out.println(room.getRoomName());
            if (room.getPois() != null) {
                for (PointOfInterest poi : room.getPois()) {
                    System.out.println("\t\t - " + poi.getPoiName());
                }
            }
        }*/

            /*for (Room room : rooms) {
                // System.out.println(room.getRoomName());
                for (PointOfInterest poi : room.getPois()) {
                    // System.out.println("\t\t - " + poi.getPoiName());
                }
            }*/
            // System.out.println("\n" + rooms.size());
        }
}
