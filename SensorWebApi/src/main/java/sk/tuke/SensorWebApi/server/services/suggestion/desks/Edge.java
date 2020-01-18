package sk.tuke.SensorWebApi.server.services.suggestion.desks;

class Edge<V> implements Comparable<Edge>
{
    private V source;
    private V destination;
    private int weight;

    Edge(V source, V destination, int weight)
    {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    int getWeight() { return weight; }

    V getSource() {
        return source;
    }

    public void setSource(V source) {
        this.source = source;
    }

    V getDestination() {
        return destination;
    }

    public void setDestination(V destination) {
        this.destination = destination;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    @Override
    public int compareTo(Edge edge) {
        return Integer.valueOf(this.weight).compareTo(edge.getWeight());
    }
}
