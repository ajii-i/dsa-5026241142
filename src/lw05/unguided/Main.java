package lw5.unguided;

import java.util.*;

public class Main {

    static int[][] graph;
    static int size;
    static String[] names;

    static int[] bfsLevel;
    static int[] bfsPrev;
    static int[] dijkstraPrev;

    // Adds a two-way track (complete, do not change)
    static void addEdge(int from, int to, int minutes) {
        graph[from][to] = minutes;
        graph[to][from] = minutes;
    }

    // BFS: finds the route with the fewest stops
    static List<Integer> bfs(int start) {
        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        bfsLevel = new int[size];
        bfsPrev = new int[size];

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int current = queue.remove(); // take the station at the front of the queue
            order.add(current);

            for (int next = 0; next < size; next++) {
                if (graph[current][next] > 0 && !visited.contains(next)) { // next is a neighbour that has not been visited
                    visited.add(next);
                    bfsLevel[next] = bfsLevel[current] + 1; // set the level of next
                    bfsPrev[next] = current; // remember the previous station
                    queue.add(next); // put next at the back of the queue
                }
            }
        }

        return order;
    }

    // Prints stations level by level
    static void printLevels(List<Integer> order) {
        int lastLevel = bfsLevel[order.get(order.size() - 1)];

        for (int level = 0; level <= lastLevel; level++) {
            System.out.println();
            System.out.print("Level " + level + ": ");

            boolean first = true;

            for (int i = 0; i < order.size(); i++) {
                int station = order.get(i);

                if (bfsLevel[station] == level) { // check whether the station belongs to this level
                    if (!first) {
                        System.out.print(", ");
                    }

                    System.out.print(names[station]);
                    first = false;
                }
            }
        }

        System.out.println();
    }

    // Dijkstra: finds the fastest route
    static void dijkstraAlg(int start) {
        int[] dist = new int[size];

        for (int i = 0; i < size; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        dist[start] = 0;

        boolean[] visited = new boolean[size];
        dijkstraPrev = new int[size];

        for (int i = 0; i < size; i++) {
            int current = findTheNextNode(visited, dist);

            if (current == -1) {
                break;
            }

            visited[current] = true; // mark current as visited

            for (int next = 0; next < size; next++) {
                if (graph[current][next] > 0
                        && !visited[next]) {

                    int newDist = dist[current] + graph[current][next];

                    if (newDist < dist[next]) { // check whether the new distance is shorter
                        dist[next] = newDist;
                        dijkstraPrev[next] = current; // remember the previous station
                    }
                }
            }
        }
    }

    // Finds the unvisited station with the smallest distance
    static int findTheNextNode(boolean[] visited, int[] dist) {
        int min = Integer.MAX_VALUE;
        int closest = -1;

        for (int i = 0; i < size; i++) {
            if (!visited[i] && dist[i] < min) { // i is unvisited and closer than min
                min = dist[i];
                closest = i;
            }
        }

        return closest;
    }

    // Rebuilds the route using prev[]
    static List<Integer> buildPath(int current, int[] prev, int start) {
        List<Integer> path;

        if (current == start) {
            path = new ArrayList<>();
        } else {
            path = buildPath(prev[current], prev, start); // go back to the previous station
        }

        path.add(current);
        return path;
    }

    // Calculates the total travel time
    static int pathTime(List<Integer> path) {
        int total = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            total += graph[path.get(i)][path.get(i + 1)]; // add the travel time between two consecutive stations
        }

        return total;
    }

    // Prints the route (complete, do not change)
    static void printPath(List<Integer> path) {
        for (int i = 0; i < path.size(); i++) {
            System.out.print(names[path.get(i)]);

            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    // Prints route information (complete, do not change)
    static void printSummary(String title, List<Integer> path) {
        System.out.println("=== " + title + " ===");
        printPath(path);

        System.out.println("Stops: " + (path.size() - 1)
                + " | Time: " + pathTime(path) + " min");
    }

    public static void main(String[] args) throws java.io.FileNotFoundException {
        int start;
        int destination;

        try (Scanner input = new Scanner(new java.io.File("metro.txt"))) {
            size = input.nextInt();
            names = new String[size];
            graph = new int[size][size];

            for (int i = 0; i < size; i++) {
                names[i] = input.next();
            }

            int trackCount = input.nextInt();
            for (int i = 0; i < trackCount; i++) {
                int from = input.nextInt();
                int to = input.nextInt();
                int minutes = input.nextInt();
                addEdge(from, to, minutes);
            }

            start = input.nextInt();
            destination = input.nextInt();
        }

        List<Integer> order = bfs(start);
        System.out.print("=== Station Levels from " + names[start] + " ===");
        printLevels(order);

        List<Integer> fewestStopsPath = buildPath(destination, bfsPrev, start);
        String endpoints = names[start] + " to " + names[destination];
        printSummary("Fewest Stops Route from " + endpoints, fewestStopsPath);

        dijkstraAlg(start);
        List<Integer> fastestPath = buildPath(destination, dijkstraPrev, start);
        printSummary("Fastest Route from " + endpoints, fastestPath);

        System.out.println("=== Comparison ===");
        if (fewestStopsPath.equals(fastestPath)) {
            System.out.println("Both routes are the same.");
        } else {
            int minutesSaved = pathTime(fewestStopsPath) - pathTime(fastestPath);
            int extraStops = fastestPath.size() - fewestStopsPath.size();
            System.out.println("The fastest route saves " + minutesSaved
                    + " min but needs " + extraStops + " more stops.");
        }
    }
}
