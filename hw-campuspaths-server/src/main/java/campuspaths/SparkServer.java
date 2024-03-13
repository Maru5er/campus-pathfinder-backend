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

package campuspaths;

import campuspaths.utils.CORSFilter;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.Spark;
import spark.Route;
import spark.Response;
import spark.Request;
import com.google.gson.Gson;
import java.util.*;
public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        CampusMap map = new CampusMap();


        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        // TODO: Create all the Spark Java routes you need here.
        //get shortest path return as json
        Spark.get("/get-shortest-path", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String start = request.queryParams("start");
                String end = request.queryParams("end");
                String startShort = "";
                String endShort = "";
                Map<String, String> buildings = map.buildingNames();
                for (Map.Entry<String, String> entry : buildings.entrySet()){
                    if (entry.getValue().equals(start)) {
                        startShort = entry.getKey();
                    }
                    if (entry.getValue().equals(end)){
                        endShort = entry.getKey();
                    }
                }
                Gson gson = new Gson();
                String json = gson.toJson(map.findShortestPath(startShort, endShort));
                return json;
            }
        });

        Spark.get("/get-building-names", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Map<String, String> buildings = map.buildingNames();
                Gson gson = new Gson();
                return gson.toJson(buildings.values());
            }
        });



    }

}
