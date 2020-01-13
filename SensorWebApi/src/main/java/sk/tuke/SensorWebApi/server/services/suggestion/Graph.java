package sk.tuke.SensorWebApi.server.services.suggestion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.List;

class Graph<V>
{
    private final Logger logger = LoggerFactory.getLogger(Graph.class);

    private Map<V, List<Edge<V>>> nodes = new HashMap<>();
    private List<Edge<V>> edges = new ArrayList<>();

    static class Edge<V>
    {
        V source;
        V destination;
        int weight;

        Edge(V source, V destination, int weight)
        {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        int getWeight() { return weight; }
    }

    void addVertex(V vertex)
    {
        if (nodes.containsKey(vertex)) {
            logger.warn(vertex.toString() + " already exists");
            return;
        }
        nodes.put(vertex, new ArrayList<>());
    }

    void addEdge(V source, V destination, int weight)
    {
        if ( !nodes.containsKey(source) || !nodes.containsKey(destination))
        {
            logger.warn("Source or destination vertex doesn't exits in the graph");
            return;
        }

        Edge<V> edge = new Edge<>(source, destination, weight);
        nodes.get(source).add(edge);
        edges.add(edge);
    }


    Edge<V> pop()
    {
        Optional<Edge<V>> optional = edges.stream().max(Comparator.comparing(Edge::getWeight));

        if (optional.isPresent())
        {
            Edge<V> edge = optional.get();
            nodes.remove(edge.source);

            return edge;
        }
        return null;
    }
}
