package com.nidal.model;

import com.nidal.RoomService;
import com.nidal.loader.Loader;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.io.IoCore;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerFactory;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nidal on 2018.11.18..
 */
public class GremlinRoom {

    @Autowired
    RoomService roomService;

    public void createGraphFromJson() throws IOException {
    }

}
