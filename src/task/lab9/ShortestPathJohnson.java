package task.lab9;

public class ShortestPathJohnson {

    private int SOURCE_NODE;
    private int numberOfNodes;
    private int augmentedMatrix[][];
    private int potential[];
    private ShortestPathBellmanFord bellmanFord;
    private ShortestPathDijkstra dijsktraShortesPath;
    private int[][] allPairShortestPath;

    public static final int MAX_VALUE = 999;

    public ShortestPathJohnson(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        augmentedMatrix = new int[numberOfNodes + 2][numberOfNodes + 2];
        SOURCE_NODE = numberOfNodes + 1;
        potential = new int[numberOfNodes + 2];
        bellmanFord = new ShortestPathBellmanFord(numberOfNodes + 1);
        dijsktraShortesPath = new ShortestPathDijkstra(numberOfNodes);
        allPairShortestPath = new int[numberOfNodes + 1][numberOfNodes + 1];
    }

    public void johnsonsAlgorithm(int adjacencyMatrix[][])
    {
        computeAugmentedGraph(adjacencyMatrix);

        bellmanFord.bellmanFordEvaluation(SOURCE_NODE, augmentedMatrix);
        potential = bellmanFord.getDistances();

        int reweightedGraph[][] = reweighGraph(adjacencyMatrix);
        for (int i = 1; i <= numberOfNodes; i++)
        {
            for (int j = 1; j <= numberOfNodes; j++)
            {
                System.out.print(reweightedGraph[i][j] + "\t");
            }
            System.out.println();
        }

        for (int source = 1; source <= numberOfNodes; source++)
        {
            dijsktraShortesPath.dijkstraShortestPath(source, reweightedGraph);
            int[] result = dijsktraShortesPath.getDistances();
            for (int destination = 1; destination <= numberOfNodes; destination++)
            {
                allPairShortestPath[source][destination] = result[destination]
                        + potential[destination] - potential[source];
            }
        }

        System.out.println();
        for (int i = 1; i<= numberOfNodes; i++)
        {
            System.out.print("\t"+i);
        }
        System.out.println();
        for (int source = 1; source <= numberOfNodes; source++)
        {
            System.out.print( source +"\t" );
            for (int destination = 1; destination <= numberOfNodes; destination++)
            {
                System.out.print(allPairShortestPath[source][destination]+ "\t");
            }
            System.out.println();
        }
    }

    private void computeAugmentedGraph(int adjacencyMatrix[][])
    {
        for (int source = 1; source <= numberOfNodes; source++) {
            for (int destination = 1; destination <= numberOfNodes; destination++) {
                augmentedMatrix[source][destination] = adjacencyMatrix[source][destination];
            }
        }

        for (int destination = 1; destination <= numberOfNodes; destination++) {
            augmentedMatrix[SOURCE_NODE][destination] = 0;
        }
    }

    private int[][] reweighGraph(int adjacencyMatrix[][]) {
        int[][] result = new int[numberOfNodes + 1][numberOfNodes + 1];
        for (int source = 1; source <= numberOfNodes; source++)
        {
            for (int destination = 1; destination <= numberOfNodes; destination++)
            {
                result[source][destination] = adjacencyMatrix[source][destination]
                        + potential[source] - potential[destination];
            }
        }
        return result;
    }
}

