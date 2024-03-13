/*
 * Copyright (C) 2023 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder;

import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.Dijkstra;
import pathfinder.parser.*;
import graph.*;
import java.util.*;
import java.io.*;


public class CampusMap implements ModelAPI {
    private List<CampusBuilding> campusBuildingList = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
    private List<CampusPath> campusPathList = CampusPathsParser.parseCampusPaths("campus_paths.csv");
    @Override
    public boolean shortNameExists(String shortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        Map<String, String> map = buildingNames();
        return map.containsKey(shortName);
    }

    @Override
    public String longNameForShort(String shortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        Map<String, String> map = buildingNames();
        if (shortNameExists(shortName)) {
            return map.get(shortName);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Map<String, String> buildingNames() {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        Map<String,String> map = new HashMap<>();
        for (CampusBuilding building: campusBuildingList) {
            map.put(building.getShortName(), building.getLongName());
        }
        return map;
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        //get start and end points
        if (!shortNameExists(startShortName) || !shortNameExists(endShortName)) throw new IllegalArgumentException();
        Point startPoint = null;
        Point endPoint = null;
        for (CampusBuilding building: campusBuildingList) {
            if (building.getShortName().equals(startShortName)) {
                startPoint = new Point(building.getX(), building.getY());
            }
            if (building.getShortName().equals(endShortName)) {
                endPoint = new Point(building.getX(), building.getY());
            }
        }

        //building graph
        Graph<Point, Double> graph = new Graph<>();
        for (CampusPath path : campusPathList) { //adding nodes and edges as points for all possible points on campus
            Point firstPoint = new Point(path.getX1(), path.getY1());
            if (!graph.listNodes().contains(firstPoint)){
                graph.addNode(firstPoint);
            }
            Point secondPoint = new Point(path.getX2(), path.getY2());
            if (!graph.listNodes().contains(secondPoint)){
                graph.addNode(secondPoint);
            }
            graph.addEdge(firstPoint, secondPoint, path.getDistance());

        }

        //run dijsktra
        Dijkstra<Point> dijkstra = new Dijkstra<>(graph, startPoint, endPoint);
        return dijkstra.run();
    }

}
