package za.co.discovery.interstellar.utils;

import org.springframework.stereotype.Component;
import za.co.discovery.interstellar.model.Route;

import java.util.*;

@Component
public class ShortestPathUtil {

    public void calculateShortestPath(HashMap<String, Vertex> vertexMap, String originPlanet, List<Route> routes) {
        Vertex sourceVertex = vertexMap.get(originPlanet);

        for (Route inputRoutes : routes) {
            Vertex vertex = vertexMap.get(inputRoutes.getPlanetOrigin());
            vertex.addNeighbour(new Edge(inputRoutes.getDistance(), vertexMap.get(inputRoutes.getPlanetOrigin()), vertexMap.get(inputRoutes.getPlanetDestination())));
        }

        sourceVertex.setDistance(0);

        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(sourceVertex);
        sourceVertex.setVisited(true);

        while (!priorityQueue.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append(originPlanet);
            Vertex actualVertex = priorityQueue.poll();
            for (Edge edge : actualVertex.getAdjacenciesList()) {

                Vertex v = edge.getTargetVertex();
                if (!v.isVisited()) {
                    double newDistance = actualVertex.getDistance() + edge.getWeight();
                    if (newDistance < v.getDistance()) {
                        priorityQueue.remove(v);
                        v.setDistance(newDistance);
                        v.setPredecessor(actualVertex);
                        priorityQueue.add(v);
                    }
                }
            }
            actualVertex.setVisited(true);
        }
    }

    public static class Vertex implements Comparable<Vertex> {

        private String name;
        private List<Edge> adjacenciesList;
        private boolean visited;
        private Vertex predecessor;
        private double distance = Double.MAX_VALUE;

        public Vertex(String name) {
            this.name = name;
            this.adjacenciesList = new ArrayList<>();
        }

        public void addNeighbour(Edge edge) {
            this.adjacenciesList.add(edge);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Edge> getAdjacenciesList() {
            return adjacenciesList;
        }

        public void setAdjacenciesList(List<Edge> adjacenciesList) {
            this.adjacenciesList = adjacenciesList;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public Vertex getPredecessor() {
            return predecessor;
        }

        public void setPredecessor(Vertex predecessor) {
            this.predecessor = predecessor;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Vertex.class.getSimpleName() + "[", "]")
                    .add("name='" + name + "'")
                    .add("adjacenciesList=" + adjacenciesList)
                    .add("visited=" + visited)
                    .add("predecessor=" + predecessor)
                    .add("distance=" + distance)
                    .toString();
        }

        @Override
        public int compareTo(Vertex otherVertex) {
            return Double.compare(this.distance, otherVertex.getDistance());
        }
    }

    public static class Edge {

        private double weight;
        private Vertex startVertex;
        private Vertex targetVertex;

        public Edge(double weight, Vertex startVertex, Vertex targetVertex) {
            this.weight = weight;
            this.startVertex = startVertex;
            this.targetVertex = targetVertex;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public Vertex getStartVertex() {
            return startVertex;
        }

        public void setStartVertex(Vertex startVertex) {
            this.startVertex = startVertex;
        }

        public Vertex getTargetVertex() {
            return targetVertex;
        }

        public void setTargetVertex(Vertex targetVertex) {
            this.targetVertex = targetVertex;
        }
    }
}
