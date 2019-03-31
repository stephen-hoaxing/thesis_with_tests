package com.nidal.model;

import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.io.IoCore;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Nidal on 2018.11.18..
 */
public class GremlinRoom {

    private Graph graph;

    public void createGraphFromXml() throws IOException {
        InputStream is = new FileInputStream("/Users/nidalchalhoub/Downloads/thesis_with_tests/src/main/resources/gremlin.xml");
        Graph graph = TinkerGraph.open();
        graph.io(IoCore.graphml()).reader().create().readGraph(is, graph);
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }
}
