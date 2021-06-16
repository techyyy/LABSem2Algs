package task.lab4;

public class OptimalBST {

    private static double sum(double[] freq, int i, int j) {
        double s = 0;
        for (int k = i; k <= j; k++) {
            if (k >= freq.length)
                continue;
            s += freq[k];
        }
        return s;
    }

    public static double optimalSearchTree(double freq[], int n) {

        double[][] cost = new double[n + 1][n + 1];

        for (int i = 0; i < n; i++) cost[i][i] = freq[i];
        for (int l = 2; l <= n; l++) {
            for (int i = 0; i <= n - l + 1; i++) {
                int j = i + l - 1;
                cost[i][j] = Double.MAX_VALUE;
                for (int r = i; r <= j; r++) {
                    double c = ((r > i) ? cost[i][r - 1] : 0) + ((r < j) ? cost[r + 1][j] : 0) + sum(freq, i, j);
                    if (c < cost[i][j]) cost[i][j] = c;
                }
            }
        }
        return cost[0][n - 1];
    }

    public static void main(String[] args) {
        System.out.println(OptimalBST.optimalSearchTree(new double[]{34, 8, 50}, 3));
    }
}
