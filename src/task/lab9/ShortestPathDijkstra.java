package task.lab9;

class ShortestPathDijkstra {
    private boolean settled[];
    private boolean unsettled[];
    private int distances[];
    private int adjacencymatrix[][];
    private int numberofvertices;

    public static final int MAX_VALUE = 999;

    public ShortestPathDijkstra(int numberOfVertices) {
        this.numberofvertices = numberOfVertices;
    }

    public void dijkstraShortestPath(int source, int[][] adjacencyMatrix) {

        this.settled = new boolean[numberofvertices + 1];
        this.unsettled = new boolean[numberofvertices + 1];
        this.distances = new int[numberofvertices + 1];
        this.adjacencymatrix = new int[numberofvertices + 1][numberofvertices + 1];

        int evaluationnode;
        for (int vertex = 1; vertex <= numberofvertices; vertex++) {
            distances[vertex] = MAX_VALUE;
        }

        for (int sourcevertex = 1; sourcevertex <= numberofvertices; sourcevertex++) {
            for (int destinationvertex = 1; destinationvertex <= numberofvertices; destinationvertex++) {
                this.adjacencymatrix[sourcevertex][destinationvertex] = adjacencyMatrix[sourcevertex][destinationvertex];
            }
        }

        unsettled[source] = true;
        distances[source] = 0;
        while (getUnsettledCount(unsettled) != 0) {
            evaluationnode = getNodeWithMinimumDistanceFromUnsettled(unsettled);
            unsettled[evaluationnode] = false;
            settled[evaluationnode] = true;
            evaluateNeighbours(evaluationnode);
        }
    }

    public int getUnsettledCount(boolean unsettled[]) {
        int count = 0;
        for (int vertex = 1; vertex <= numberofvertices; vertex++) {
            if (unsettled[vertex] == true) {
                count++;
            }
        }
        return count;
    }

    public int getNodeWithMinimumDistanceFromUnsettled(boolean unsettled[]) {
        int min = MAX_VALUE;
        int node = 0;
        for (int vertex = 1; vertex <= numberofvertices; vertex++) {
            if (unsettled[vertex] == true && distances[vertex] < min) {
                node = vertex;
                min = distances[vertex];
            }
        }
        return node;
    }

    public void evaluateNeighbours(int evaluationNode) {
        int edgeDistance = -1;
        int newDistance = -1;

        for (int destinationNode = 1; destinationNode <= numberofvertices; destinationNode++) {
            if (!settled[destinationNode]) {
                if (adjacencymatrix[evaluationNode][destinationNode] != MAX_VALUE) {
                    edgeDistance = adjacencymatrix[evaluationNode][destinationNode];
                    newDistance = distances[evaluationNode] + edgeDistance;
                    if (newDistance < distances[destinationNode]) {
                        distances[destinationNode] = newDistance;
                    }
                    unsettled[destinationNode] = true;
                }
            }
        }
    }

    public int[] getDistances() {
        return distances;
    }
}