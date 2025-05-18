public class NQueensCSP {

    static int N; // Number of queens and size of the board (N x N)
    static int[] X; // X[i] represents the column of the queen in row i

    public static void main(String[] args) {
        N = 8; // You can change N to any value >= 4
        X = new int[N + 1]; // 1-based indexing (index 0 not used)

        System.out.println("Solutions for " + N + "-Queens problem:");
        NQueens(1); // Start with the first queen
    }

    // Recursive method to solve N-Queens
    public static void NQueens(int k) {
        for (int i = 1; i <= N; i++) {
            if (Place(k, i)) {
                X[k] = i;
                if (k == N) {
                    printSolution();
                } else {
                    NQueens(k + 1);
                }
            }
        }
    }

    // Check if placing queen k in column i is safe
    public static boolean Place(int k, int i) {
        for (int j = 1; j < k; j++) {
            // Same column or same diagonal check
            if (X[j] == i || Math.abs(X[j] - i) == Math.abs(j - k)) {
                return false;
            }
        }
        return true;
    }

    // Print the board configuration for a valid solution
    public static void printSolution() {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (X[i] == j) {
                    System.out.print(" Q ");
                } else {
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }
        System.out.println("------------------------");
    }
}
