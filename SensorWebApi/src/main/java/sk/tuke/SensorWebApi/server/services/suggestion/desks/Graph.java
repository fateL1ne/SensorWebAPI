package sk.tuke.SensorWebApi.server.services.suggestion.desks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.List;

class Graph<V>
{
    private final Logger logger = LoggerFactory.getLogger(Graph.class);
    private Map<V, List<Edge<V>>> nodes = new HashMap<>();

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
        if ( !nodes.containsKey(source) || !nodes.containsKey(destination)) {
            logger.warn("Source or destination vertex doesn't exits in the graph");
            return;
        }

        Edge<V> edge = new Edge<>(source, destination, weight);

        nodes.get(source).add(edge);
    }

    void deleteVertex(V v)
    {
        if (v == null) return;

        List<Edge<V>> dVertex = nodes.remove(v);
        String msg = (dVertex != null) ? "Deleted vertex " + v.toString() : "Vertex " + v.toString() + " doesn't exists";
        logger.info(msg);
    }


    void deleteEdge(Edge<V> edge)
    {
        if (edge == null) return;

        deleteVertex(edge.getDestination());
        deleteVertex(edge.getSource());

    }



    Edge<V> pop()
    {
        Edge<V> hEdge = new Edge<>(null, null, 0x00);
        Edge<V> cEdge;

        if (nodes.size() <= 1) {
            return null;
        }

        for (V vertex : nodes.keySet())
        {
            if (nodes.get(vertex).isEmpty()) {
                continue;
            }

            cEdge = getHighestEdge(nodes.get(vertex));

            if (hEdge.getWeight() < cEdge.getWeight()) {
                hEdge = cEdge;
            }
        }

        deleteEdge(hEdge);
        stringify();

        return hEdge;
    }


    void stringify()
    {
        for (V v : nodes.keySet())
        {
            long deskID = ((GraphVertex)v).getDesk().getId();
            nodes.get(v).forEach( edge -> System.out.println(deskID +  " ---> " + ((GraphVertex)edge.getDestination()).getDesk().getId()));
        }
    }

    private Edge<V> getHighestEdge(List<Edge<V>> edges)
    {
        Optional<Edge<V>> optional = edges.stream().max(Comparator.comparing(Edge::getWeight));

        return optional.orElse(null);
    }
}
