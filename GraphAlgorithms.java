import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.PriorityQueue;

/**
 * Your implementation of various graph algorithms.
 *
 * @author Nicholas Watney Meyer
 * @version 2.0
 * @userid nmeyer32
 * @GTID 903 444 783
 *
 * Collaborators: Me, myself, and I.
 *
 * Resources: In comparison to the usual, there weren't many resources made available for this.
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {

        if (start == null || graph == null) {

            throw new IllegalArgumentException("Any input is null.");
        }

        if (!graph.getVertices().contains(start)) {

            throw new IllegalArgumentException("Doesn't exist in the graph.");
        }

        Set<Vertex<T>> visitedSet = new HashSet<Vertex<T>>();
        List<Vertex<T>> visitedList = new LinkedList<Vertex<T>>();
        Queue<Vertex<T>> toVisit = new LinkedList<Vertex<T>>();

        visitedSet.add(start);
        visitedList.add(start);
        toVisit.add(start);

        while (!toVisit.isEmpty()) {

            Vertex<T> current = toVisit.remove();
            for (VertexDistance<T> vertexDistance : graph.getAdjList().get(current)) {

                Vertex<T> vertex = vertexDistance.getVertex();

                if (visitedSet.contains(vertex) == false) {

                    visitedSet.add(vertex);
                    visitedList.add(vertex);
                    toVisit.add(vertex);
                }
            }
        }

        return visitedList;
    }


    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * NOTE: You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {

        if (start == null || graph == null) {

            throw new IllegalArgumentException("Any input is null.");
        }

        if (graph.getVertices().contains(start) == false) {

            throw new IllegalArgumentException("Start doesn't exist in the graph.");
        }

        Set<Vertex<T>> visitedSet = new HashSet<Vertex<T>>();
        List<Vertex<T>> visitedList = new LinkedList<Vertex<T>>();

        return hDfs(start, graph, visitedSet, visitedList);

    }

    /**
     * Helper recursive wrapper method that aids the depth first search
     * @param current current vertex kept track of
     * @param graph properties of the graph
     * @param visitedSet set containing all the nodes visited
     * @param visitedList ordered list of vertices visited
     * @param <T> generic data type
     * @return order list of vertices visited
     */
    private static <T> List<Vertex<T>> hDfs(Vertex<T> current, Graph<T> graph,
                                            Set<Vertex<T>> visitedSet, List<Vertex<T>> visitedList) {

        if (!visitedSet.contains(current)) {

            visitedSet.add(current);
            visitedList.add(current);

            for (VertexDistance<T> vertexDistance : graph.getAdjList().get(current)) {

                Vertex<T> vertex = vertexDistance.getVertex();

                if (visitedSet.contains(vertex) == false) {

                    hDfs(vertex, graph, visitedSet, visitedList);
                }
            }
        }

        return visitedList;
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {

        if (start == null || graph == null) {

            throw new IllegalArgumentException("Any input is null.");
        }

        if (graph.getVertices().contains(start) == false) {

            throw new IllegalArgumentException("Start doesn't exist in the graph.");
        }

        Set<Vertex<T>> visitedSet = new HashSet<Vertex<T>>();
        Map<Vertex<T>, Integer> distanceMap = new HashMap<Vertex<T>, Integer>();
        Queue<VertexDistance<T>> priorityQueue = new PriorityQueue<VertexDistance<T>>();

        for (Vertex<T> vertex : graph.getVertices()) {

            distanceMap.put(vertex, Integer.MAX_VALUE);
        }

        priorityQueue.add(new VertexDistance(start, 0));
        int size = graph.getVertices().size();

        while (!priorityQueue.isEmpty() && visitedSet.size() != size) {

            VertexDistance<T> current = priorityQueue.remove();

            if (!visitedSet.contains(current.getVertex())) {

                visitedSet.add(current.getVertex());
                distanceMap.put(current.getVertex(), current.getDistance());

                for (VertexDistance<T> neighbour: graph.getAdjList().get(current.getVertex())) {

                    if (!visitedSet.contains(neighbour.getVertex())) {

                        priorityQueue.add(new VertexDistance(neighbour.getVertex(), current.getDistance()
                                + neighbour.getDistance()));
                    }
                }
            }
        }

        return distanceMap;
    }
}
