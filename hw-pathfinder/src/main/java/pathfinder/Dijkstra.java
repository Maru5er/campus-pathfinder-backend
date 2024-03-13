package pathfinder;
import graph.*;
import pathfinder.*;
import pathfinder.datastructures.Path;

import java.util.*;

/**
 * Uses Dijkstra's algorithm to find the path with least cost from start to end
 * @param <T> type for node
 */
public class Dijkstra<T> {
    /**
     * RI : this.start and this.dest and this.graph cannot be null. All edge label must be non-negative
     * AF(this) : shortest past (path with least cost) from this.start to this.
     */
    public Graph<T,Double> graph;
    public T start;
    public T dest;
    public PriorityQueue<Path<T>> active;


    /**
     * Constructor for Dijsktra. adds a path from start to itself. Consturcts a priority queue that compares paths by cost
     * @param graph graph passed
     * @param start starting node
     * @param dest destination node
     */
    public Dijkstra(Graph<T,Double> graph, T start, T dest){
        this.graph = graph;
        this.start = start;
        this.dest = dest;
        this.active = new PriorityQueue<Path<T>>(11, new Comparator<Path<T>>() {
            @Override
            public int compare(Path<T> o1, Path<T> o2) {
                return Double.compare(o1.getCost(), o2.getCost());
            }
        });

        this.active.add(new Path<T>(this.start));
    }

    /**
     * Runs Dijkstra's algorithm on graph to find the shortest path from start to dest. Returns null if no path found
     * @return shortest path from start to dest or null if path not found
     */
    public Path<T> run(){
        Set<T> finished = new HashSet<>();
        while (!this.active.isEmpty()){
            Path<T> minPath = active.remove();
            T minDest = minPath.getEnd();

            if (minDest.equals(dest)) {
                return minPath;
            }
            if (finished.contains(minDest)) continue;

            for (Graph<T,Double>.Edge edge : graph.listEdges(minDest)) {
                if (!finished.contains(edge.getChild())) {
                    Path<T> newPath = minPath.extend(edge.getChild(), edge.getEdgeName());
                    this.active.add(newPath);
                }
            }
            finished.add(minDest);
        }
        return null;
    }



}
