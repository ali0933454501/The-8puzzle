

import java.util.ArrayList;
import java.util.Random;

public class Board {
    private int  tiles[][];
    Solver s = new Solver();
    Board previous;

    public Board(int[][] tiles) {
        this.tiles = tiles;
    }

    public int dimension() {
        return tiles.length;
    }

    public int hamming() {
        int n = this.dimension();
        int p = 1;
        int inTrueP = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == p++) inTrueP++;
            }
        return n - inTrueP;
    }

    public int manhattan() {
        int n = this.dimension();
        int p = 1;
        int distance = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                int element = tiles[i][j];
                if (element == p++ || element == 0) continue;
                else {
                    int io;
                    int jo;
                    if (element % n == 0) {
                        io = element / n - 1;
                        jo = n - 1;
                    }
                    else {
                        io = element / n;
                        jo = element % n - 1;
                    }
                    distance += Math.abs(j - jo);
                    distance += Math.abs(i - io);
                    // System.out.println(element +" io " + io +"jo "+jo+" distance"+distance);
                    // System.out.println(distance);
                }

            }
        return distance;
    }

    public boolean isGoal() {
        int n = this.dimension();
        int p = 1;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (i == n - 1 && j == n - 1 && tiles[i][j] == 0) break;
                if (tiles[i][j] != p++) return false;
            }
        return true;
    }

    public boolean equals(Board board) {
        if (board == null) return false;
        if (this.dimension() != board.dimension()) return false;
        int n = this.dimension();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != board.tiles[i][j]) return false;
            }
        return true;
    }

    private int getInvCount(int[] arr) {
        int inv_count = 0;
        for (int i = 0; i < 9; i++)
            for (int j = i + 1; j < 9; j++)

                // Value 0 is used for empty space
                if (arr[i] > 0 &&
                        arr[j] > 0 && arr[i] > arr[j])
                    inv_count++;
        return inv_count;
    }

    public boolean isSolvable() {
        int []linearPuzzle;
        linearPuzzle = new int[9];
        int k = 0;

        // Converting 2-D puzzle to linear form
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                linearPuzzle[k++] = this.tiles[i][j];

        // Count inversions in given 8 puzzle
        int invCount = getInvCount(linearPuzzle);

        // return true if inversion count is even.
        return (invCount % 2 == 0);
    }

    private static int[][] copy(int[][] orginal) {
        int n = orginal.length;
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                copy[i][j] = orginal[i][j];
            }
        return copy;
    }

    private static void swap(int[][] a, int i1, int j1, int i2, int j2) {
        int temp = a[i1][j1];
        a[i1][j1] = a[i2][j2];
        a[i2][j2] = temp;
    }


    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        int n = this.dimension();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] == 0) {
                    if (i > 0) {
                        int[][] b1 = copy(this.tiles);
                        swap(b1, i, j, i - 1, j);
                        Board board1 = new Board(b1);
                        neighbors.add(board1);
                    }
                    if (i < n - 1) {
                        int[][] b2 = copy(this.tiles);
                        swap(b2, i, j, i + 1, j);
                        Board board2 = new Board(b2);
                        neighbors.add(board2);
                    }
                    if (j > 0) {
                        int[][] b3 = copy(this.tiles);
                        swap(b3, i, j, i, j - 1);
                        Board board3 = new Board(b3);
                        neighbors.add(board3);
                    }
                    if (j < n - 1) {
                        int[][] b4 = copy(this.tiles);
                        swap(b4, i, j, i, j + 1);
                        Board board4 = new Board(b4);
                        neighbors.add(board4);

                    }
                    break;
                }

            }

        return neighbors;
    }

    public Board twin() {
        int[][] twin = copy(this.tiles);
        Random random = new Random();
        int i1, j1, i2, j2;
        do {
            i1 = random.nextInt(this.tiles.length);
            j1 = random.nextInt(this.tiles.length);
            i2 = random.nextInt(this.tiles.length);
            j2 = random.nextInt(this.tiles.length);
        } while (twin[i1][j1] == 0 || twin[i2][j2] == 0);

        swap(twin, i1, j1, i2, j2);
        return new Board(twin);
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        int n = tiles.length;
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

}

