package com.nidal.loader;

import com.nidal.model.PointOfInterest;
import com.nidal.model.Room;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

/**
 * Created by Nidal on 2018.11.22..
 */
public class FileCreator {

    public void CreateXmlFile(List<Room> rooms, List<PointOfInterest> pois) throws ParserConfigurationException, TransformerException, XPathExpressionException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element root = doc.createElementNS("http://graphml.graphdrawing.org/xmlns", "graphml");
        root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        root.setAttribute("xsi:schemaLocation", "http://graphml.graphdrawing.org/xmlns\n" +
                "         http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd");
        doc.appendChild(root);

        Element key1 = doc.createElement("key");
        key1.setAttribute("id", "weight");
        key1.setAttribute("for", "edge");
        key1.setAttribute("attr.name", "weight");
        key1.setAttribute("attr.type", "float");
        root.appendChild(key1);

        Element key2 = doc.createElement("key");
        key2.setAttribute("id", "name");
        key2.setAttribute("for", "node");
        key2.setAttribute("attr.name", "name");
        key2.setAttribute("attr.type", "string");
        root.appendChild(key2);

        Element key3 = doc.createElement("key");
        key3.setAttribute("id", "wheelChair");
        key3.setAttribute("for", "node");
        key3.setAttribute("attr.name", "wheelChair");
        key3.setAttribute("attr.type", "boolean");
        root.appendChild(key3);

        Element key4 = doc.createElement("key");
        key4.setAttribute("id", "id");
        key4.setAttribute("for", "node");
        key4.setAttribute("attr.name", "id");
        key4.setAttribute("attr.type", "long");
        root.appendChild(key4);

        Element graph = doc.createElement("graph");

        // nodes
        rooms.stream().forEach(r -> {
            Element node = doc.createElement("node");
            node.setAttribute("id", r.getId().toString());

            Element data1 = doc.createElement("data");
            data1.setAttribute("key", "id");
            data1.appendChild(doc.createTextNode(r.getId().toString()));

            Element data2 = doc.createElement("data");
            data2.setAttribute("key", "name");
            data2.appendChild(doc.createTextNode(r.getName()));

            Element data3 = doc.createElement("data");
            data3.setAttribute("key", "wheelChair");
            data3.appendChild(doc.createTextNode(Boolean.toString(r.isAccessibleByWheelchair())));

            node.appendChild(data1);
            node.appendChild(data2);
            node.appendChild(data3);

            graph.appendChild(node);
        });

        pois.stream().forEach(p -> {
            Element node = doc.createElement("node");
            node.setAttribute("id", p.getId().toString());

            Element data1 = doc.createElement("data");
            data1.setAttribute("key", "id");
            data1.appendChild(doc.createTextNode(p.getId().toString()));

            Element data2 = doc.createElement("data");
            data2.setAttribute("key", "name");
            data2.appendChild(doc.createTextNode(p.getName()));

            Element data3 = doc.createElement("data");
            data3.setAttribute("key", "wheelChair");
            data3.appendChild(doc.createTextNode(Boolean.toString(p.isAccessibleByWheelchair())));

            node.appendChild(data1);
            node.appendChild(data2);
            node.appendChild(data3);

            graph.appendChild(node);
        });

        // edges
        rooms.stream().forEach(r -> {
            List<PointOfInterest> pointOfInterests = r.getPois();
            pointOfInterests.stream().forEach(p -> {
                int id = 1;
                Element edge = doc.createElement("edge");
                edge.setAttribute("id", String.valueOf(id));
                edge.setAttribute("source", r.getId().toString());
                edge.setAttribute("target", p.getId().toString());
                edge.setAttribute("label", "haspoi");
                Element data = doc.createElement("data");
                data.setAttribute("key", "weight");
                data.appendChild(doc.createTextNode("1"));
                edge.appendChild(data);
                root.appendChild(edge);
                ++id;
            });
        });

        pois.stream().forEach(ps -> {
            List<PointOfInterest> pointOfInterests = ps.getPois();
            pointOfInterests.stream().forEach(p -> {
                int id = Integer.MAX_VALUE;
                Element edge = doc.createElement("edge");
                edge.setAttribute("id", String.valueOf(id));
                edge.setAttribute("source", ps.getId().toString());
                edge.setAttribute("target", p.getId().toString());
                edge.setAttribute("label", "connectspoi");
                Element data = doc.createElement("data");
                data.setAttribute("key", "weight");
                data.appendChild(doc.createTextNode("1"));
                edge.appendChild(data);
                root.appendChild(edge);
                --id;
            });
        });

        root.appendChild(graph);

        XPath xp = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xp.evaluate("//edge", doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i).getAttributes().getNamedItem("id");
            node.setNodeValue(String.valueOf(i));
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("/Users/nidalchalhoub/Downloads/thesis_with_tests/src/main/resources/gremlin.xml"));

        transformer.transform(source, result);

        System.out.println("XML file Creation.");
    }

}
